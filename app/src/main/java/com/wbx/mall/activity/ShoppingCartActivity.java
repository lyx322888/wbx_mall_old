package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ShoppingCartAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.ShopCart;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.iosdialog.AlertDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ShoppingCartActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.trolley_recycle_view)
    RecyclerView mRecyclerView;
    public List<ShopCart> mDataList = new ArrayList<>();
    private ShoppingCartAdapter mAdapter;
    private boolean isPhysical;
    @Bind(R.id.refresh_layout)
    public PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.edit_tv)
    TextView editTv;
    private boolean isEdit;
    @Bind(R.id.delete_cart_btn)
    Button deleteCarBtn;
    private String ids;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void initPresenter() {
        mRefreshLayout.setCanLoadMore(false);
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShoppingCartAdapter(mDataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void fillData() {
        getServiceData();
    }

    private void getServiceData() {
        new MyHttp().doPost(Api.getDefault().indexTrolleyInfo(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.finishRefresh();
                if ("暂无数据".equals(result.getString("msg"))) {
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    mRefreshLayout.buttonClickNullData(ShoppingCartActivity.this, "getServiceData");
                    return;
                }
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                mDataList.clear();
                mDataList.addAll(JSONArray.parseArray(result.getString("data"), ShopCart.class));
                for (ShopCart shopCart : mDataList) {
                    for (GoodsInfo2 goodsInfo : shopCart.getGoods_cart()) {
                        if (goodsInfo.getSelected() == 0) {
                            shopCart.setCheck(false);
                            break;
                        } else {
                            shopCart.setCheck(true);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void setListener() {
        mRefreshLayout.setRefreshListener(this);
        mAdapter.setOnItemClickListener(R.id.submit_btn, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ShopCart shopCart = mAdapter.getItem(position);
                isPhysical = shopCart.getGrade_id() != 15;
                String cartGoodsJson = createGoodsJson(!isPhysical, shopCart);
                if (TextUtils.isEmpty(cartGoodsJson)) {
                    Toast.makeText(mContext, "请选中要结算的商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingDialog.showDialogForLoading(ShoppingCartActivity.this);
                new MyHttp().doPost(isPhysical ? Api.getDefault().createOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), cartGoodsJson, "") : Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), cartGoodsJson), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        String orderId = result.getJSONObject("data").getString("order_id");
                        Intent intent = new Intent(mContext, SubmitOrderActivity.class);
                        intent.putExtra("isPhysical", isPhysical);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("isBook", false);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        });
    }

    private void getSelectCartId() {
        List<String> cartIdList = new ArrayList<>();
        for (ShopCart shopCart : mDataList) {
            for (GoodsInfo2 goodsInfo : shopCart.getGoods_cart()) {
                if (goodsInfo.getSelected() == 1) {
                    cartIdList.add(goodsInfo.getCart_id());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : cartIdList) {
            sb.append(s);
            sb.append(",");
        }
        String s = sb.toString();
        if (!TextUtils.isEmpty(s)) {
            ids = s.substring(0, s.length() - 1);
        }
    }


    private String createGoodsJson(boolean isVegetable, ShopCart shopCart) {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        for (GoodsInfo2 goodsInfo : shopCart.getGoods_cart()) {
            if (goodsInfo.getSelected() == 1) {
                OrderBean orderBean = new OrderBean();
                if (isVegetable) {
                    orderBean.setProduct_id(goodsInfo.getGoods_id());
                } else {
                    orderBean.setGoods_id(goodsInfo.getGoods_id());
                }
                orderBean.setAttr_id(goodsInfo.getAttr_id());
                orderBean.setNum(goodsInfo.getNum());
                orderBean.setNature(goodsInfo.getSelected_nature_ids());
                lstGoods.add(orderBean);
            }
        }
        if (lstGoods.size() > 0) {
            hashMap.put(String.valueOf(shopCart.getShop_id()), lstGoods);
            return new Gson().toJson(hashMap);
        } else {
            return null;
        }
    }

    @Override
    public void refresh() {
        mDataList.clear();
        getServiceData();
    }

    @Override
    public void loadMore() {

    }

    @OnClick({R.id.rl_right, R.id.delete_cart_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_right:
                edit();
                break;
            case R.id.delete_cart_btn:
                delete();
                break;
        }
    }

    private void delete() {
        getSelectCartId();
        new AlertDialog(this).builder()
                .setTitle("提示")
                .setMsg("删除购物车商品？")
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyHttp().doPost(Api.getDefault().deleteCartInfo(SPUtils.getSharedStringData(ShoppingCartActivity.this, AppConfig.LOGIN_TOKEN), ids), new HttpListener() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                isEdit = false;
                                editTv.setText("编辑");
                                deleteCarBtn.setVisibility(View.GONE);
                                mDataList.clear();
                                getServiceData();
                            }

                            @Override
                            public void onError(int code) {

                            }
                        });
                    }
                }).show();
    }

    private void edit() {
        isEdit = !isEdit;
        if (isEdit) {
            editTv.setText("取消");
            deleteCarBtn.setVisibility(View.VISIBLE);
        } else {
            editTv.setText("编辑");
            deleteCarBtn.setVisibility(View.GONE);
        }
    }
}

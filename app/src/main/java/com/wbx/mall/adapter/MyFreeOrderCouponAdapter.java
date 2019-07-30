package com.wbx.mall.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.MyBuyGoodsListBean;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFreeOrderCouponAdapter extends BaseQuickAdapter<MyBuyGoodsListBean, BaseViewHolder> {
    private Activity mActivity;

    public MyFreeOrderCouponAdapter(@Nullable List<MyBuyGoodsListBean> data, Activity mActivity) {
        super(R.layout.item_my_free_order_coupon, data);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyBuyGoodsListBean item) {
        GlideUtils.showSmallPic(mContext, (ImageView) helper.getView(R.id.iv_shop), item.getLogo());
        TextView tvShopName = helper.getView(R.id.tv_shop_name);
        if (!TextUtils.isEmpty(item.getShop_name())) {
            tvShopName.setText(item.getShop_name());
        } else {
            tvShopName.setText("");
        }
        RecyclerView rvGoods = helper.getView(R.id.rv_goods);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        MyFreeOrderCouponGoodsAdapter goodsAdapter = new MyFreeOrderCouponGoodsAdapter(item.getGoods());
        rvGoods.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //免单
                    case R.id.tv_free_bt:
                        toBuyGoods(item.getGoods().get(position), item.getShop_id(), true);
                        break;
                    //购买
                    case R.id.tv_buy_goods:
                        toBuyGoods(item.getGoods().get(position), item.getShop_id(), false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void toBuyGoods(MyBuyGoodsListBean.GoodsBean goods, int shopId, boolean isFree) {
        final boolean isPhysical = goods.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET;
        String cartGoodsJson = createGoodsJson(isPhysical, goods, shopId, isFree);
        LoadingDialog.showDialogForLoading(mActivity);
        new MyHttp().doPost(isPhysical ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), cartGoodsJson) : Api.getDefault().createOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), cartGoodsJson, ""), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(mContext, SubmitOrderActivity.class);
                intent.putExtra("isPhysical", isPhysical);
                intent.putExtra("orderId", orderId);
                intent.putExtra("isBook", false);
                mActivity.startActivity(intent);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private String createGoodsJson(boolean isVegetable, MyBuyGoodsListBean.GoodsBean goodsBean, int shopId, boolean isFree) {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        if (isVegetable) {
            orderBean.setProduct_id(String.valueOf(goodsBean.getGoods_id()));
        } else {
            orderBean.setGoods_id(String.valueOf(goodsBean.getGoods_id()));
        }
        if (isFree) {
            orderBean.setActivity_type(3);
        } else {
            orderBean.setActivity_type(0);
        }
        orderBean.setNum(1);
        lstGoods.add(orderBean);
        if (lstGoods.size() > 0) {
            hashMap.put(String.valueOf(shopId), lstGoods);
            return new Gson().toJson(hashMap);
        } else {
            return null;
        }
    }
}

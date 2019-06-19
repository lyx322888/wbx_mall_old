package com.wbx.mall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wbx.mall.R;
import com.wbx.mall.activity.CommentActivity;
import com.wbx.mall.activity.ConsumeFreeActivity;
import com.wbx.mall.activity.ShareFreeActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.adapter.MyFreeOrderAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.MyFreeOrderBean;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

public class MyFreeOrderFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener{
    private static final String POSITION = "POSITION";
    @Bind(R.id.srl)
    SmartRefreshLayout srl;
    @Bind(R.id.rv_free_activity)
    RecyclerView rvFreeActivity;
    private List<MyFreeOrderBean> lstData = new ArrayList<>();
    private MyFreeOrderAdapter adapter;
    private int currentPage = 1;
    private MyHttp myHttp;
    private int type;

    public MyFreeOrderFragment() {
    }

    public static MyFreeOrderFragment newInstance(int position) {
        MyFreeOrderFragment fragment = new MyFreeOrderFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(POSITION);
            switch (position) {
                case 0:
                    //全部
                    type = 88;
                    break;
                case 1:
                    //进行中
                    type = 0;
                    break;
                case 2:
                    //成功
                    type = 1;
                    break;
                case 3:
                    //失败
                    type = 2;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_free_order;
    }

    @Override
    public void initPresenter() {
        myHttp = new MyHttp();
    }

    @Override
    protected void initView() {
        rvFreeActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyFreeOrderAdapter(lstData);
        rvFreeActivity.setAdapter(adapter);
    }

    @Override
    protected void fillData() {
        onRefresh(srl);
    }

    @Override
    protected void bindEvent() {
        srl.setOnLoadMoreListener(this);
        srl.setOnRefreshListener(this);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyFreeOrderBean data = lstData.get(position);
                switch (view.getId()) {
                    case R.id.rl_goods:
                        if (data.getActivity_type() == 1) {
                            ConsumeFreeActivity.actionStart(getContext(), data.getActivity_id(), data.getGoods_id(), data.getGrade_id());
                        } else {
                            ShareFreeActivity.actionStart(getContext(), data.getActivity_id(), data.getGoods_id(), data.getGrade_id());
                        }
                        break;
                    case R.id.tv_free_gain:
                        freeGain(data);
                        break;
                    case R.id.tv_free_again:
                        if (data.getActivity_type() == 1) {
                            ConsumeFreeActivity.actionStart(getContext(), data.getGoods_id(), data.getGrade_id());
                        } else {
                            ShareFreeActivity.actionStart(getContext(), data.getGoods_id(), data.getGrade_id());
                        }
                        break;
                    case R.id.tv_invite:
                        inviteFriend(data);
                        break;
                    case R.id.tv_comment:
                        Intent intent = new Intent(getContext(), CommentActivity.class);
                        intent.putExtra("activityId", data.getActivity_id());
                        intent.putExtra("physical", data.getGrade_id() != AppConfig.StoreType.VEGETABLE_MARKET);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void inviteFriend(MyFreeOrderBean data) {
        String path = "pages/index/freesheet/freesheet?activity_id=" + data.getActivity_id() + "&sponsor_user_id=" + LoginUtil.getUserInfo().getUser_id() + "&goods_id=" + data.getGoods_id() + "&grade_id=" + data.getGrade_id() + "&shop_id=" + data.getShop_id() + "&activitytype=" + (data.getActivity_type() == 1 ? "consume" : "share");
        ShareUtils.getInstance().shareMiniProgram(getContext(), "您的好友已经在附近实体店免单中，快去瞧瞧吧！！！", "", data.getPhoto(), path, "www.wbx365.com");
    }

    private void freeGain(final MyFreeOrderBean data) {
        if (data.getIs_gain() == 1) {
            ToastUitl.showLong("您已领取过了");
            return;
        }
        LoadingDialog.showDialogForLoading(getActivity(), "领取中...", false);
        new MyHttp().doPost(data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), createGoodsJson(data)) : Api.getDefault().createOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), createGoodsJson(data), null), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                intent.putExtra("isPhysical", data.getGrade_id() != AppConfig.StoreType.VEGETABLE_MARKET);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private String createGoodsJson(MyFreeOrderBean data) {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        if (data.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET) {
            orderBean.setProduct_id(data.getGoods_id());
        } else {
            orderBean.setGoods_id(data.getGoods_id());
        }
        orderBean.setActivity_type(data.getActivity_type());
        orderBean.setNum(1);
        lstGoods.add(orderBean);
        hashMap.put(data.getShop_id(), lstGoods);
        return new Gson().toJson(hashMap);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        srl.setEnableLoadMore(true);
        currentPage = 1;
        loadData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        loadData();
    }

    private void loadData() {
        LoadingDialog.showDialogForLoading(getActivity());
        myHttp.doPost(Api.getDefault().getMyFreeActivity(LoginUtil.getLoginToken(), type, currentPage, 10), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                srl.finishRefresh();
                srl.finishLoadMore();
                List<MyFreeOrderBean> data = JSONArray.parseArray(result.getString("data"), MyFreeOrderBean.class);
                if (data != null) {
                    if (data.size() < 10) {
                        srl.setEnableLoadMore(false);
                    }
                    if (currentPage == 1) {
                        lstData.clear();
                    }
                    lstData.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    srl.setEnableLoadMore(false);
                }
            }

            @Override
            public void onError(int code) {
                srl.finishRefresh();
                srl.finishLoadMoreWithNoMoreData();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTimer();
    }
}

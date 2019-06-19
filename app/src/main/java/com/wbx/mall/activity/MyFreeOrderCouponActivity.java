package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wbx.mall.R;
import com.wbx.mall.adapter.MyFreeOrderCouponAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.MyBuyGoodsListBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MyFreeOrderCouponActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {
    @Bind(R.id.srl)
    SmartRefreshLayout srl;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<MyBuyGoodsListBean> lstData = new ArrayList<>();
    private MyHttp myHttp;
    private int currentPage = 1;
    private MyFreeOrderCouponAdapter adapter;
    private SkeletonScreen skeletonScreen;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyFreeOrderCouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_free_order_coupon;
    }

    @Override
    public void initPresenter() {
        myHttp = new MyHttp();
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyFreeOrderCouponAdapter(lstData, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void fillData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh(srl);
    }

    @Override
    public void setListener() {
        srl.setOnLoadMoreListener(this);
        srl.setOnRefreshListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        srl.setEnableLoadMore(true);
        currentPage = 1;
        loadData();
    }

    private void loadData() {
        LoadingDialog.showDialogForLoading(this);
        myHttp.doPost(Api.getDefault().getMyAccumulateGoodsList(LoginUtil.getLoginToken(), currentPage, 10), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                srl.finishRefresh();
                srl.finishLoadMore();
                List<MyBuyGoodsListBean> data = JSONArray.parseArray(result.getString("data"), MyBuyGoodsListBean.class);
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
                    skeletonScreen =
                            //绑定当前列表
                            Skeleton.bind(recyclerView)
                                    .adapter(adapter).shimmer(false)
                                    .count(1)
                                    .load(R.layout.item_nodata)
                                    .show();
                }
            }

            @Override
            public void onError(int code) {
                srl.finishRefresh();
                srl.finishLoadMoreWithNoMoreData();
            }
        });
    }
}

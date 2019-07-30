package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.adapter.MyFreeOrderCouponAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.MyBuyGoodsListBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MyFreeOrderCouponActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.srl)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private MyFreeOrderCouponAdapter adapter;
    private List<MyBuyGoodsListBean> lstData = new ArrayList<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;


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
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new MyFreeOrderCouponAdapter(lstData, this);
        adapter.bindToRecyclerView(recyclerView);
        mRefreshLayout.setRefreshListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (lstData != null && lstData.size() != 0) {
                    MyBuyGoodsListBean bean = lstData.get(position);
                    StoreDetailActivity.actionStart(MyFreeOrderCouponActivity.this, bean.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(bean.getShop_id()));
                }
            }
        });
    }

    @Override
    public void fillData() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        loadData();
    }

    @Override
    public void setListener() {

    }

    private void loadData() {
        new MyHttp().doPost(Api.getDefault().getMyAccumulateGoodsList(LoginUtil.getLoginToken(), pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<MyBuyGoodsListBean> data = JSONArray.parseArray(result.getString("data"), MyBuyGoodsListBean.class);
                if (data != null) {
                    mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                    if (pageNum == AppConfig.pageNum) {
                        lstData.clear();
                    }
                    if (data.size() < pageSize) {
                        canLoadMore = false;
                    }
                    lstData.addAll(data);
                    adapter.notifyDataSetChanged();
                } else if (lstData.size() == 0) {
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    mRefreshLayout.buttonClickNullData(MyFreeOrderCouponActivity.this, "loadData");
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(MyFreeOrderCouponActivity.this, "loadData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(MyFreeOrderCouponActivity.this, "loadData");
                    }
                } else {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        canLoadMore = false;
                    }
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        loadData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        loadData();
    }
}

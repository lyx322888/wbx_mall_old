package com.wbx.mall.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.MyFreeOrderCouponAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.MyBuyGoodsListBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class FreeFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.srl)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<MyBuyGoodsListBean> lstData = new ArrayList<>();
    private MyHttp myHttp = new MyHttp();
    private MyFreeOrderCouponAdapter adapter;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_free_order_coupon;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MyFreeOrderCouponAdapter(lstData, getActivity());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (lstData != null && lstData.size() != 0) {
                    MyBuyGoodsListBean bean = lstData.get(position);
                    StoreDetailActivity.actionStart(getActivity(), bean.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(bean.getShop_id()));
                }
            }
        });
        mRefreshLayout.setRefreshListener(this);
    }

    @Override
    protected void fillData() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        loadData();
    }

    @Override
    protected void bindEvent() {

    }

    private void loadData() {
        myHttp.doPost(Api.getDefault().getMyAccumulateGoodsList(LoginUtil.getLoginToken(), pageNum, pageSize), new HttpListener() {
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
                    mRefreshLayout.buttonClickNullData(FreeFragment.this, "loadData");
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(FreeFragment.this, "loadData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(FreeFragment.this, "loadData");
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

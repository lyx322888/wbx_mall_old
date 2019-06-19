package com.wbx.mall.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.WithdrawRecordAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.WithdrawRecordBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的提现
 */
public class MyWithdrawFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.balance_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private WithdrawRecordAdapter mAdapter;
    private List<WithdrawRecordBean> lstData = new ArrayList<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;//控制下次是否加载更多

    public MyWithdrawFragment() {
    }

    public static MyWithdrawFragment newInstance() {
        MyWithdrawFragment fragment = new MyWithdrawFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_withdraw;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WithdrawRecordAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void fillData() {
        new MyHttp().doPost(Api.getDefault().getCashLogList(LoginUtil.getLoginToken(), pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<WithdrawRecordBean> dataList = JSONArray.parseArray(result.getString("data"), WithdrawRecordBean.class);
                if (null == dataList) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    lstData.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明 下次已经没有数据了
                    canLoadMore = false;
                }
                lstData.addAll(dataList);
                mAdapter.update(lstData);
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(MyWithdrawFragment.this, "fillData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(MyWithdrawFragment.this, "fillData");
                    } else {

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
    protected void bindEvent() {
        mRefreshLayout.setRefreshListener(this);
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        fillData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        fillData();
    }
}

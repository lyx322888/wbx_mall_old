package com.wbx.mall.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.LogAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.LogInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * 我的提现
 */
public class ConsumptionRecordFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.balance_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private LogAdapter mAdapter;
    private List<LogInfo> logInfoList = new ArrayList<>();
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;//控制下次是否加载更多

    public ConsumptionRecordFragment() {
    }

    public static ConsumptionRecordFragment newInstance() {
        ConsumptionRecordFragment fragment = new ConsumptionRecordFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_consumption_record;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LogAdapter(logInfoList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mParams.put("login_token", SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN));
        mParams.put("num", pageSize);
    }

    @Override
    protected void fillData() {
        mParams.put("page", pageNum);
        new MyHttp().doPost(Api.getDefault().getBalanceLog(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<LogInfo> dataList = JSONArray.parseArray(result.getString("data"), LogInfo.class);
                if (null == dataList) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    logInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                logInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(ConsumptionRecordFragment.this, "fillData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(ConsumptionRecordFragment.this, "fillData");
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

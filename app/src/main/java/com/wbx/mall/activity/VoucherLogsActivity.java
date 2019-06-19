package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.VoucherLogsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.VoucherLogsInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/11/20.
 * 代金券明细
 */

public class VoucherLogsActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.balance_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private VoucherLogsAdapter mAdapter;
    private List<VoucherLogsInfo> logInfoList = new ArrayList<>();
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;//控制下次是否加载更多

    @Override
    public int getLayoutId() {
        return R.layout.activity_voucher_logs;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new VoucherLogsAdapter(logInfoList, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
    }

    @Override
    public void fillData() {
        new MyHttp().doPost(Api.getDefault().getVoucherLogs(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                List<VoucherLogsInfo> dataList = JSONArray.parseArray(result.getString("data"), VoucherLogsInfo.class);
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
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                logInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(VoucherLogsActivity.this, "fillData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(VoucherLogsActivity.this, "fillData");
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
    public void setListener() {
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

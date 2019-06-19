package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.IntelligentPayListAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.IntelligentPayBean;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class IntelligentPayListActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout refreshLayout;
    private IntelligentPayListAdapter mAdapter;
    private List<IntelligentPayBean> lstData = new ArrayList<>();
    private int currentPage = 1;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, IntelligentPayListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_intelligent_pay_list;
    }

    @Override
    public void initPresenter() {
        refreshLayout.setCanLoadMore(false);
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IntelligentPayListAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void fillData() {
        refreshLayout.showView(ViewStatus.LOADING_STATUS);
        loadData(false);
    }

    @Override
    public void setListener() {
        refreshLayout.setRefreshListener(this);
    }

    @Override
    public void refresh() {
        loadData(false);
    }

    private void loadData(final boolean isLoadMore) {
        if (isLoadMore) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        new MyHttp().doPost(Api.getDefault().getIntelligentPayList(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), currentPage, 10), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (isLoadMore) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                }
                List<IntelligentPayBean> data = JSONArray.parseArray(result.getString("data"), IntelligentPayBean.class);
                if (!isLoadMore && (data == null || data.size() == 0)) {
                    refreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    refreshLayout.buttonClickNullData(IntelligentPayListActivity.this, "refresh");
                    return;
                }
                if (null != refreshLayout) {
                    refreshLayout.showView(ViewStatus.CONTENT_STATUS);
                }
                if (!isLoadMore) {
                    lstData.clear();
                }
                lstData.addAll(data);
                mAdapter.update(lstData);
            }

            @Override
            public void onError(int code) {
                if (isLoadMore) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                }
                if (code == AppConfig.ERROR_STATE.NULLDATA && refreshLayout != null) {
                    if (isLoadMore) {
                        refreshLayout.setCanLoadMore(false);
                    } else {
                        refreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        refreshLayout.buttonClickNullData(IntelligentPayListActivity.this, "refresh");
                    }
                } else {
                    refreshLayout.showView(ViewStatus.ERROR_STATUS);
                    refreshLayout.buttonClickError(IntelligentPayListActivity.this, "refresh");
                }
            }
        });
    }

    @Override
    public void loadMore() {
        loadData(true);
    }
}

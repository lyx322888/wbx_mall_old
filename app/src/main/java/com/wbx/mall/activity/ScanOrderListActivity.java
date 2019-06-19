package com.wbx.mall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.MainActivity;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ScanOrderListAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.ScanOrderDetailBean;
import com.wbx.mall.common.ActivityManager;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ScanOrderListActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout refreshLayout;
    private ScanOrderListAdapter adapter;
    private int pageNum = AppConfig.pageNum;
    private boolean canLoadMore = true;//控制下次是否加载更多
    private List<ScanOrderDetailBean> lstData = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScanOrderListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_order_list;
    }

    @Override
    public void initPresenter() {
        for (Activity activity : ActivityManager.getAllActivity()) {
            if (activity instanceof MainActivity || activity instanceof ScanOrderListActivity) {
            } else {
                activity.finish();
            }
        }
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ScanOrderListAdapter(this);
        recyclerView.setAdapter(adapter);
        refreshLayout.showView(ViewStatus.LOADING_STATUS);
        refreshLayout.setRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void loadData() {
        new MyHttp().doPost(Api.getDefault().getScanOrderList(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), pageNum, AppConfig.pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                List<ScanOrderDetailBean> data = JSONArray.parseArray(result.getString("data"), ScanOrderDetailBean.class);
                if ("暂无数据".equals(result.getString("msg"))) {
                    if (pageNum == AppConfig.pageNum) {
                        refreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        refreshLayout.buttonClickNullData(ScanOrderListActivity.this, "loadData");
                    }
                    canLoadMore = false;
                    return;
                }
                refreshLayout.showView(ViewStatus.CONTENT_STATUS);
                lstData.addAll(data);
                adapter.update(lstData);
                if (data.size() < AppConfig.pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void fillData() {
    }

    @Override
    public void setListener() {

    }

    @Override
    public void refresh() {
        lstData.clear();
        pageNum = 1;
        canLoadMore = true;
        loadData();
    }

    @Override
    public void loadMore() {
        if (!canLoadMore) {
            refreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        pageNum++;
        loadData();
    }
}

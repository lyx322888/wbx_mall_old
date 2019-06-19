package com.wbx.mall.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.SoftwareAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.SoftwareBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MySoftwareFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.ptrl)
    PullToRefreshLayout ptrl;
    private MyHttp myHttp;
    private int currentPage = 1;
    private static final String POSITION = "POSITION";
    private int position;
    private List<SoftwareBean> lstData = new ArrayList<>();
    private SoftwareAdapter adapter;
    private boolean canLoadMore = true;

    public MySoftwareFragment() {

    }

    public static MySoftwareFragment newInstance(int position) {
        MySoftwareFragment fragment = new MySoftwareFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_software;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        ptrl.setRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SoftwareAdapter(getContext(), lstData);
        recyclerView.setAdapter(adapter);
        adapter.update(lstData);
    }

    @Override
    protected void fillData() {
        myHttp = new MyHttp();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void refresh() {
        currentPage = 1;
        loadData();
    }

    @Override
    public void loadMore() {
        if (!canLoadMore) {
            ptrl.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        currentPage++;
        loadData();
    }

    private void loadData() {

        LoadingDialog.showDialogForLoading(getActivity());
        myHttp.doPost(Api.getDefault().getMySoftware(LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
                if ("暂无数据".equals(result.getString("msg")) && currentPage == 1) {
                    ptrl.showView(ViewStatus.EMPTY_STATUS);
                    ptrl.buttonClickNullData(MySoftwareFragment.this, "loadData");
                    return;
                }
                List<SoftwareBean> softListBeans = JSONArray.parseArray(result.getString("data"), SoftwareBean.class);
                if (softListBeans.size() < 10) {
                    canLoadMore = false;
                }
                if (currentPage == 1) {
                    lstData.clear();
                }
                lstData.addAll(softListBeans);
                adapter.update(lstData);
            }

            @Override
            public void onError(int code) {
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
                if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                    ptrl.showView(ViewStatus.ERROR_STATUS);
                    ptrl.buttonClickError(MySoftwareFragment.this, "loadData");
                }
            }
        });
    }
}

package com.wbx.mall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.MyClientAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.ClientInfo;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;


/**
 * 我的客户
 */

public class ClientFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private boolean canLoadMore = true;
    public static final String POSITION = "position";
    private int anInt;
    private List<ClientInfo> clientInfoList = new ArrayList<>();
    private MyClientAdapter mAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;

    public static Fragment newInstance(int position) {
        ClientFragment tabFragment = new ClientFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        anInt = getArguments().getInt(POSITION);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyClientAdapter(clientInfoList, getActivity());
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void fillData() {
        getClientData();
    }

    private void getClientData() {
        mParams.put("login_token", SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN));
        mParams.put("type", anInt + 1);
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().myClient(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<ClientInfo> dataList = JSONArray.parseArray(result.getString("data"), ClientInfo.class);
                if (null == dataList) {
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    mRefreshLayout.buttonClickNullData(ClientFragment.this, "getClientData");
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    clientInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                clientInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(ClientFragment.this, "getClientData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(ClientFragment.this, "getClientData");
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
        getClientData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        getClientData();
    }
}

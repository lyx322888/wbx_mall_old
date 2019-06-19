package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.IntegralExchangeRecordAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.IntergalExchangeOrderDetailBean;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/16.
 * 兑换记录
 */

public class IntegralExchangeRecordActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.record_rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.record_refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> mParams = new HashMap<>();
    private List<IntergalExchangeOrderDetailBean> intergalExchangeOrderDetailBeanList = new ArrayList<>();
    private boolean canLoadMore = true;
    private IntegralExchangeRecordAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral_exchange_record;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        mAdapter = new IntegralExchangeRecordAdapter(intergalExchangeOrderDetailBeanList, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                IntergalExchangeOrderDetailBean intergalExchangeOrderDetailBean = intergalExchangeOrderDetailBeanList.get(position);
                IntegralExchangeOrderDetailActivity.actionStart(IntegralExchangeRecordActivity.this, intergalExchangeOrderDetailBean);

            }
        });
    }

    @Override
    public void fillData() {
        mParams.put("login_token", SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN));
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getIntegralGoodsList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                List<IntergalExchangeOrderDetailBean> lstData = JSONArray.parseArray(result.getString("data"), IntergalExchangeOrderDetailBean.class);
                if (null == lstData) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    intergalExchangeOrderDetailBeanList.clear();
                }
                if (lstData.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                intergalExchangeOrderDetailBeanList.addAll(lstData);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(IntegralExchangeRecordActivity.this, "fillData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(IntegralExchangeRecordActivity.this, "fillData");
                    }
                } else {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        canLoadMore = false;
                    }
                }
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
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        pageNum++;
        fillData();
    }
}

package com.wbx.mall.module.find.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.model.LatLng;
import com.wbx.mall.R;
import com.wbx.mall.module.find.adapter.BusinessCircleAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.BusinessCircleBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 发现页
 */

public class FindFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.img_empty)
    ImageView mImgEmpty;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.find_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.scrollView)
    MyScrollview mScrollview;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private BusinessCircleAdapter businessCircleAdapter;
    private boolean canLoadMore;
    private List<BusinessCircleBean> mFindList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_find;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        businessCircleAdapter = new BusinessCircleAdapter(getActivity(), mFindList);
        businessCircleAdapter.setCurrentLatLng(new LatLng(mLocationInfo.getLat(), mLocationInfo.getLng()));
        mRecyclerView.setAdapter(businessCircleAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRefreshLayout.setRefreshListener(this);

        mScrollview.setScrollViewListener(new MyScrollview.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollview scrollView, int x, int y, int oldx, int oldy) {
                if (null != mTitleLayout) {
                    if (y <= mTitleLayout.getMeasuredHeight()) {
                        float persent = y * 1.5f / (mTitleLayout.getTop() + mTitleLayout.getMeasuredHeight());
                        int alpha = (int) (255 * persent);
                        int color = Color.argb(alpha, 255, 255, 255);
                        if (alpha <= 255) {
                            mTitleLayout.setBackgroundColor(color);
                        }
                    } else {
                        int color = Color.argb(255, 255, 255, 255);
                        mTitleLayout.setBackgroundColor(color);
                    }
                }
            }
        });
    }

    @Override
    protected void fillData() {
        refresh();
    }

    @Override
    protected void bindEvent() {

    }

    private void getDiscoveryList() {
        new MyHttp().doPost(Api.getDefault().getDiscoveryList(mLocationInfo.getName(), mLocationInfo.getLat(), mLocationInfo.getLng(), LoginUtil.getLoginToken(), pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<BusinessCircleBean> data = JSONArray.parseArray(result.getString("data"), BusinessCircleBean.class);
                if (data == null) {
                    mImgEmpty.setVisibility(View.VISIBLE);
                    canLoadMore = false;
                } else {
                    mImgEmpty.setVisibility(View.GONE);
                    if (pageNum == AppConfig.pageNum) {
                        mFindList.clear();
                    }
                    if (data.size() < pageSize) {
                        //说明下次已经没有数据了
                        canLoadMore = false;
                    }
                    mFindList.addAll(data);
                    businessCircleAdapter.notifyDataSetChanged();
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NULLDATA) {
                    mFindList.clear();
                    businessCircleAdapter.notifyDataSetChanged();
                    showShortToast("暂无数据");
                    mImgEmpty.setVisibility(View.VISIBLE);
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
        getDiscoveryList();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        getDiscoveryList();
    }
}
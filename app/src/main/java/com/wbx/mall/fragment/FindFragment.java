package com.wbx.mall.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.model.LatLng;
import com.wbx.mall.R;
import com.wbx.mall.adapter.BusinessCircleAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.BusinessCircleBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.widget.DropZoomScrollView;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2018/1/22.
 */

public class FindFragment extends BaseFragment {
    @Bind(R.id.head_zoom_scroll_view)
    DropZoomScrollView headZoomScrollView;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    private MyHttp myHttp;
    private BusinessCircleAdapter businessCircleAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_find;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void fillData() {
        LoadingDialog.showDialogForLoading(getActivity(), "加载中...", false);
        addHead();
        myHttp = new MyHttp();
        addBusinessCircle();
        getDiscoveryList();
    }

    private void addHead() {
        llContainer.removeAllViews();
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.find_fragment_header, null);
        llContainer.addView(head);
        headZoomScrollView.setHeaderView(head);
    }

    private void getDiscoveryList() {
        LoadingDialog.showDialogForLoading(getActivity(), "加载中...", false);
        myHttp.doPost(Api.getDefault().getDiscoveryList(mLocationInfo.getName(), mLocationInfo.getLat(), mLocationInfo.getLng(), LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<BusinessCircleBean> data = JSONArray.parseArray(result.getString("data"), BusinessCircleBean.class);
                if (data == null) {
                    llEmpty.setVisibility(View.VISIBLE);
                    businessCircleAdapter.update(new ArrayList<BusinessCircleBean>());
                } else {
                    llEmpty.setVisibility(View.GONE);
                    businessCircleAdapter.update(data);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void addBusinessCircle() {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        businessCircleAdapter = new BusinessCircleAdapter(getActivity());
        businessCircleAdapter.setCurrentLatLng(new LatLng(mLocationInfo.getLat(), mLocationInfo.getLng()));
        recyclerView.setAdapter(businessCircleAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        llContainer.addView(recyclerView);
    }

    @Override
    protected void bindEvent() {
        headZoomScrollView.setOnRefreshListener(new DropZoomScrollView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDiscoveryList();
            }
        });
        headZoomScrollView.setOnScrollListener(new DropZoomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (null != mTitleLayout) {
                    if (scrollY <= mTitleLayout.getMeasuredHeight()) {
                        float persent = scrollY * 1.5f / (mTitleLayout.getTop() + mTitleLayout.getMeasuredHeight());
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
}

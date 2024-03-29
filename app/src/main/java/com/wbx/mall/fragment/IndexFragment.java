package com.wbx.mall.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.CookBookActivity;
import com.wbx.mall.activity.FoodsActivity;
import com.wbx.mall.module.mine.ui.IntegralMallActivity;
import com.wbx.mall.module.mine.ui.IntelligentServiceActivity;
import com.wbx.mall.module.mine.ui.MessageCenterActivity;
import com.wbx.mall.activity.MarketActivity;
import com.wbx.mall.activity.SearchActivity;
import com.wbx.mall.activity.SelectAddressActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.ShopGoodsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.service.LocationService;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by wushenghui on 2018/1/9.
 */
public class IndexFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.index_refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.scroll_view)
    MyScrollview mScrollView;
    @Bind(R.id.title_view)
    LinearLayout mTitleView;
    @Bind(R.id.tv_address)
    TextView cityTv;
    @Bind(R.id.index_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.has_message_tv)
    TextView unReadMsgTv;
    @Bind(R.id.index_fragment)
    FrameLayout layout;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> mParams = new HashMap<>();
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private boolean canLoadMore = true;
    private ShopGoodsAdapter mAdapter;
    private MyReceiver refreshHasLocationReceiver;
    private Intent intent;
    private Dialog mLoadingDialog;
    private MyHttp myHttp;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index;
    }

    @Override
    public void initPresenter() {
        showLoadingDialog("定位中...");
        myHttp = new MyHttp();
        IntentFilter filter = new IntentFilter("refreshHasLocation");
        refreshHasLocationReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshHasLocationReceiver, filter);
        intent = new Intent(getActivity(), LocationService.class);
        try {
            getActivity().startService(intent);
            // getActivity().bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        } catch (IllegalStateException e) {
        }
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ShopGoodsAdapter(shopInfoList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void fillData() {
        IntentFilter filter = new IntentFilter("refreshHasLocation");
        refreshHasLocationReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshHasLocationReceiver, filter);
        intent = new Intent(getActivity(), LocationService.class);
        try {
            getActivity().startService(intent);
        } catch (IllegalStateException e) {
        }
    }

    private void startGetData() {
        mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        mParams.put("city_name", mLocationInfo.getName());
        mParams.put("lat", mLocationInfo.getLat());
        mParams.put("lng", mLocationInfo.getLng());
        cityTv.setText(mLocationInfo.getAddressName());
        getServiceData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(refreshHasLocationReceiver);
        getActivity().stopService(intent);
    }

    private void getServiceData() {
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        LoadingDialog.showDialogForLoading(getActivity());
        new MyHttp().doPost(Api.getDefault().getIndexShopInfo2(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (null != mRefreshLayout) {
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadMore();
                }
                if ("暂无数据".equals(result.getString("msg")) && pageNum == AppConfig.pageNum) {
                    shopInfoList.clear();
                    mAdapter.notifyDataSetChanged();
                    canLoadMore = false;
                    return;
                }
                List<ShopInfo2> dataList = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                if (null == dataList) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    shopInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                shopInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
                mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        ShopInfo2 shopInfo = shopInfoList.get(position);
                        StoreDetailActivity.actionStart(getContext(), shopInfo.getGrade_id()== AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopInfo.getShop_id()));
                    }
                });
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void bindEvent() {
        mRefreshLayout.setRefreshListener(this);
        mScrollView.setScrollViewListener(new MyScrollview.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollview scrollView, int l, int scrollY, int oldl, int oldt) {
                if (null != mTitleView) {
                    if (scrollY <= mTitleView.getMeasuredHeight()) {
                        float persent = scrollY * 1.5f / (mTitleView.getTop() + mTitleView.getMeasuredHeight());
                        int alpha = (int) (255 * persent);
                        int color = Color.argb(alpha, 6, 193, 174);
                        if (alpha <= 255) {
                            mTitleView.setBackgroundColor(color);
                        }
                    } else {
                        int color = Color.argb(255, 6, 193, 174);
                        mTitleView.setBackgroundColor(color);
                    }
                }
            }
        });
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        getServiceData();
    }

    @Override
    public void loadMore() {
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        pageNum++;
        getServiceData();
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("refreshHasLocation".equals(intent.getAction())) {
                dismissDialog();
                pageNum = AppConfig.pageNum;
                canLoadMore = true;
                startGetData();
            }
        }
    }



    @OnClick({R.id.fragment_index_search_tv, R.id.tv_address, R.id.rl_right, R.id.service_im, R.id.tv_snacks, R.id.index_buy_tv, R.id.tv_foods, R.id.index_country_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_index_search_tv:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_address:
                startActivityForResult(new Intent(getActivity(), SelectAddressActivity.class), 0);
                break;
            case R.id.rl_right:
                if (LoginUtil.isLogin()) {
                    MessageCenterActivity.actionStart(getActivity());
                } else {
                    LoginUtil.login();
                }
                break;
            case R.id.service_im:
                startActivity(new Intent(getActivity(), IntelligentServiceActivity.class));
                break;
            case R.id.tv_snacks:
                startActivity(new Intent(getActivity(), CookBookActivity.class));
                break;
            case R.id.index_buy_tv:
                getActivity().startActivity(new Intent(getActivity(), MarketActivity.class));
                break;
            case R.id.tv_foods:
                getActivity().startActivity(new Intent(getActivity(), FoodsActivity.class));
                break;
            case R.id.index_country_tv:
                //积分商城
                getActivity().startActivity(new Intent(getActivity(), IntegralMallActivity.class));
                break;
        }
    }

    private void showLoadingDialog(String message) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loading, null);
        TextView loadingText = view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(message);
        ImageView loadingIm = view.findViewById(R.id.loading_im);
        AnimationDrawable mAnim = (AnimationDrawable) loadingIm.getDrawable();
        mAnim.start();
        mLoadingDialog = new Dialog(getActivity(), R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
    }

    private void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}

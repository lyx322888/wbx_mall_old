package com.wbx.mall.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunfusheng.marqueeview.MarqueeView;
import com.wbx.mall.R;
import com.wbx.mall.activity.NearbyStoreActivity;
import com.wbx.mall.activity.SearchActivity;
import com.wbx.mall.activity.SelectAddressActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.ShopGoodsAdapter;
import com.wbx.mall.adapter.VisitShopAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.IndexCountBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.VisitShopBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.IndexCouponDialog;
import com.wbx.mall.module.mine.ui.IntelligentServiceActivity;
import com.wbx.mall.module.mine.ui.MessageCenterActivity;
import com.wbx.mall.presenter.VisitShopPresenterImp;
import com.wbx.mall.service.LocationService;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.TimeUtil;
import com.wbx.mall.view.VisitShopView;
import com.wbx.mall.widget.CircleImageView;
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
public class IndexFragment02 extends BaseFragment implements BaseRefreshListener, VisitShopView {
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
    @Bind(R.id.iv_activity_user)
    CircleImageView img_user;
    @Bind(R.id.tv_activity)
    TextView tv_user;
    @Bind(R.id.recycler_shops_visited)
    RecyclerView mShopVisited;
    @Bind({R.id.tv_history})
    TextView mTvHistory;
    @Bind(R.id.marqueeView)
    MarqueeView marqueeView;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> mParams = new HashMap<>();
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private boolean canLoadMore = true;
    private ShopGoodsAdapter mAdapter;
    private MyReceiver refreshHasLocationReceiver;
    private Intent intent;
    private Dialog mLoadingDialog;
    private IndexCountBean indexCountBean;
    private IndexCouponDialog indexCouponDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index02;
    }

    @Override
    public void initPresenter() {
//        getCountData();
        showLoadingDialog("定位中...");
        SPUtils.remove(getActivity(), "city_name_select");
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
        VisitShopPresenterImp presenterImp = new VisitShopPresenterImp(this);
        presenterImp.getVisitShop(LoginUtil.getLoginToken(), AppConfig.pageNum, AppConfig.pageSize, mLocationInfo.getLat() + "", mLocationInfo.getLng() + "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mShopVisited.setLayoutManager(layoutManager);
        mShopVisited.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ShopGoodsAdapter(shopInfoList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ShopInfo2 shopInfo = shopInfoList.get(position);
                StoreDetailActivity.actionStart(getActivity(), shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopInfo.getShop_id()));
//                        DetailActivity.actionStart(getActivity(), shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopInfo.getShop_id()));
            }
        });
    }

    @Override
    protected void fillData() {
        IntentFilter filter = new IntentFilter("refreshHasLocation");
        refreshHasLocationReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshHasLocationReceiver, filter);
        getIndexCountData();
        getUnreadNum();
    }

    /**
     * 获取首页滚动订单数据
     */
    private void getIndexCountData() {
        new MyHttp().doPost(Api.getDefault().getIndexCountData(LoginUtil.getLoginToken(), mLocationInfo.getCity_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                indexCountBean = result.getObject("data", IndexCountBean.class);
                List<String> list = new ArrayList<>();
                for (IndexCountBean.OrderBean bean : indexCountBean.getActivity_user()) {
                    String time = TimeUtil.getfriendlyTime(bean.getCreate_time() * 1000);
                    list.add((TextUtils.isEmpty(bean.getNickname()) ? "匿名" : bean.getNickname()) + "  " + time + "购买了" + "  " + bean.getTitle());
                }
                marqueeView.startWithList(list);
                String time = TimeUtil.getfriendlyTime(indexCountBean.getOrder().getCreate_time() * 1000);
                tv_user.setText(indexCountBean.getOrder().getNickname() + time + "购买了" + indexCountBean.getOrder().getTitle());
                GlideUtils.showSmallPic(getContext(), img_user, indexCountBean.getOrder().getFace());
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    /**
     * 获取首页未读消息数量
     */
    private void getUnreadNum() {
        if (LoginUtil.isLogin()) {
            new MyHttp().doPost(Api.getDefault().getUnreadSystemMessageNum(LoginUtil.getLoginToken()), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    int messageUnreadCount = 0;
                    if (result.getJSONObject("data") != null && result.getJSONObject("data").getInteger("count") > 0) {
                        messageUnreadCount = result.getJSONObject("data").getInteger("count");
                    }
                    if (messageUnreadCount > 0) {
                        unReadMsgTv.setVisibility(View.VISIBLE);
                    } else {
                        unReadMsgTv.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(int code) {

                }
            });
        }
    }


    private void startGetData() {
        mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        mParams.put("city_name", mLocationInfo.getName());
        mParams.put("lat", mLocationInfo.getLat());
        mParams.put("lng", mLocationInfo.getLng());
        cityTv.setText(mLocationInfo.getAddressName());
        getServiceData();
        if (indexCouponDialog == null) {
            indexCouponDialog = new IndexCouponDialog(getActivity());
        }
        indexCouponDialog.show();
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
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        marqueeView.startFlipping();
    }

    @Override
    public void onPause() {
        super.onPause();
        marqueeView.stopFlipping();
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

    @Override
    public void getVisitShop(final VisitShopBean shopBean) {
        final List<VisitShopBean.DataBean> data = shopBean.getData();
        if (data == null || data.size() == 0) {
            mTvHistory.setVisibility(View.GONE);
            mShopVisited.setVisibility(View.GONE);
        } else {
            mTvHistory.setVisibility(View.VISIBLE);
            mShopVisited.setVisibility(View.VISIBLE);
            VisitShopAdapter adapter = new VisitShopAdapter(data, getContext());
            mShopVisited.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
                @Override
                public void onItemClicked(View view, int position) {
                    StoreDetailActivity.actionStart(getActivity(), data.get(position).getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(data.get(position).getShop_id()));
                }
            });
        }
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

    @OnClick({R.id.fragment_index_search_tv, R.id.tv_fruits, R.id.tv_market, R.id.tv_foods, R.id.tv_drinks, R.id.tv_snacks, R.id.tv_address, R.id.rl_right, R.id.service_im})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_index_search_tv:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_fruits:
//                startActivity(new Intent(getActivity(), FruitsActivity.class));
                NearbyStoreActivity.startNearbyStore(getActivity(), 0);
                break;
            case R.id.tv_market:
//                startActivity(new Intent(getActivity(), MarketActivity.class));
                NearbyStoreActivity.startNearbyStore(getActivity(), 1);
                break;
            case R.id.tv_foods:
//                startActivity(new Intent(getActivity(), FoodsActivity.class));
                NearbyStoreActivity.startNearbyStore(getActivity(), 2);
                break;
            case R.id.tv_drinks:
//                startActivity(new Intent(getActivity(), SnacksActivity.class));
                NearbyStoreActivity.startNearbyStore(getActivity(), 3);
                break;
            case R.id.tv_snacks:
//                startActivity(new Intent(getActivity(), SnacksActivity.class));
                NearbyStoreActivity.startNearbyStore(getActivity(), 4);
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
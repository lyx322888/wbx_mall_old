package com.wbx.mall.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.wbx.mall.R;
import com.wbx.mall.activity.BuyActivity;
import com.wbx.mall.activity.DrinksActivity;
import com.wbx.mall.activity.FoodActivity;
import com.wbx.mall.activity.FoodsxActivity;
import com.wbx.mall.activity.IntelligentServiceActivity;
import com.wbx.mall.activity.MessageCenterActivity;
import com.wbx.mall.activity.NearByActivity;
import com.wbx.mall.activity.SearchActivity;
import com.wbx.mall.activity.SelectAddressActivity;
import com.wbx.mall.activity.SpecialtyActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.HomeShufflingAdapter;
import com.wbx.mall.adapter.ShopGoodsAdapter;
import com.wbx.mall.adapter.VisitShopAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.baserx.NewRxBus;
import com.wbx.mall.bean.HomeShufflingData;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.VisitShopBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.presenter.HomeShufflingPresenterImp;
import com.wbx.mall.presenter.VisitShopPresenterImp;
import com.wbx.mall.service.LocationService02;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.view.HomeShufflingView;
import com.wbx.mall.view.VisitShopView;
import com.wbx.mall.widget.AutoPollRecyclerView;
import com.wbx.mall.widget.CircleImageView;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.ScrollSpeedLinearLayoutManger;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wushenghui on 2018/1/9.
 */
public class IndexFragment02 extends BaseFragment implements BaseRefreshListener, HomeShufflingView, VisitShopView {
    @Bind(R.id.index_refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.scroll_view)
    MyScrollview mScrollView;
    @Bind(R.id.title_view)
    LinearLayout mTitleView;
    @Bind(R.id.index_choose_tv)
    TextView cityTv;
    @Bind(R.id.index_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.has_message_tv)
    TextView unReadMsgTv;
    @Bind(R.id.recycler_polling)
    AutoPollRecyclerView shufflingRecycler;
    @Bind(R.id.iv_activity_user)
    CircleImageView img_user;
    @Bind(R.id.tv_activity)
    TextView tv_user;
    @Bind(R.id.ll_fragment02)
    LinearLayout ll_fragment02;
    @Bind(R.id.recycler_shops_visited)
    RecyclerView mShopVisited;
    @Bind({R.id.tv_history})
    TextView mTvHistory;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> mParams = new HashMap<>();
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private boolean canLoadMore = true;
    private ShopGoodsAdapter mAdapter;
    private MyReceiver refreshHasLocationReceiver;
    private MyReceiver refreshUIReceiver;
    private Intent intent;
    private Dialog mLoadingDialog;
    private MyHttp myHttp;
    private HomeShufflingPresenterImp shufflingPresenterImp;
    private LinearLayoutManager shopLayoutManager;
    private List<HomeShufflingData.DataBean.ActivityUserBean> data = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index02;
    }

    @Override
    public void initPresenter() {
//        getCountData();
        showLoadingDialog("定位中...");
//        Log.e("TAG",LoginUtil.getLoginToken());
        Youhui();
        SPUtils.remove(getActivity(), "city_name_select");
        myHttp = new MyHttp();
        IntentFilter filter = new IntentFilter("refreshHasLocation");
        refreshHasLocationReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshHasLocationReceiver, filter);
        intent = new Intent(getActivity(), LocationService02.class);
        try {
            getActivity().startService(intent);
            // getActivity().bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        } catch (IllegalStateException e) {
        }


    }


    @Override
    protected void initView() {

        shufflingPresenterImp = new HomeShufflingPresenterImp(this);
        shufflingPresenterImp.getHomeShuffling(LoginUtil.getLoginToken(), mLocationInfo.getCity_id());
        ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        shufflingRecycler.setLayoutManager(layoutManager);// 布局管理器。

        VisitShopPresenterImp presenterImp = new VisitShopPresenterImp(this);
        presenterImp.getVisitShop(LoginUtil.getLoginToken(), AppConfig.pageNum, AppConfig.pageSize, mLocationInfo.getLat() + "", mLocationInfo.getLng() + "");
        mShopVisited.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));

        if (LoginUtil.isLogin()) {
            shufflingRecycler.start();
        } else {
            shufflingRecycler.stop();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShopGoodsAdapter(shopInfoList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        ll_fragment02.getBackground().setAlpha(200);
    }

    @Override
    protected void fillData() {

        IntentFilter filtermsg = new IntentFilter(AppConfig.REFRESH_UI);
        refreshUIReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshUIReceiver, filtermsg);
        IntentFilter filter = new IntentFilter("refreshHasLocation");
        refreshHasLocationReceiver = new MyReceiver();
        getActivity().registerReceiver(refreshHasLocationReceiver, filter);
       /* intent = new Intent(getActivity(), LocationService02.class);
        try {
            getActivity().startService(intent);
        } catch (IllegalStateException e) {
        }*/
        Subscription subscribe = NewRxBus.getInstance().tObservable(NewFreeInfoBean.class)
                .subscribe(new Action1<NewFreeInfoBean>() {
                    @Override
                    public void call(NewFreeInfoBean newFreeInfoBean) {
                        if (null != mAdapter) {
                            int showIndex = shopLayoutManager.findFirstVisibleItemPosition();
                            boolean isFirst = mAdapter.isFirstsFreeInfo();
                            mAdapter.setFreeInfo(newFreeInfoBean);
                            if (isFirst) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.notifyItemChanged(showIndex, "refresh");
                            }
                        }
                    }
                });
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
        getActivity().unregisterReceiver(refreshUIReceiver);
        getActivity().stopService(intent);


    }


//    private void getCountData() {
//        new MyHttp().doPost(Api.getDefault().getIndexCountData(), new HttpListener() {
//            @Override
//            public void onSuccess(JSONObject result) {
//                Log.e("轮播图", "");
//                if ("暂无数据".equals(result.getString("msg")) && pageNum == AppConfig.pageNum) {
//                    return;
//                }
//
//                List<HomeShufflingData> dataList = JSONArray.parseArray(result.getString("data"), HomeShufflingData.class);
//
//                if (null == dataList) {
//                    return;
//                }
//            }
//
//            @Override
//            public void onError(int code) {
//                Log.e("error==", "" + code);
//
//            }
//        });
//    }

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
                        SPUtils.put("shopInfo_goods", shopInfo.getShop_id() + "", getActivity());
                        StoreDetailActivity.actionStart(getActivity(), shopInfo.getGrade_id(), String.valueOf(shopInfo.getShop_id()));
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
        getUnreadNum();
    }

    private void getUnreadNum() {
        if (LoginUtil.isLogin()) {
            new MyHttp().doPost(Api.getDefault().getUnreadSystemMessageNum(LoginUtil.getLoginToken()), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    int messageUnreadCount = 0;
                    int chatUnreadCount = 0;
                    if (result.getJSONObject("data") != null && result.getJSONObject("data").getInteger("count") > 0) {
                        messageUnreadCount = result.getJSONObject("data").getInteger("count");
                    }
                    EMChatManager emChatManager = EMClient.getInstance().chatManager();
                    if (null != emChatManager) {
                        chatUnreadCount = emChatManager.getUnreadMessageCount();
                    }
                    if (messageUnreadCount + chatUnreadCount > 0) {
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

    @Override
    public void onPause() {
        super.onPause();
//        shufflingRecycler.stop();
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
    public void getHomeShuffling(HomeShufflingData homeShufflingData) {
        data.addAll(homeShufflingData.getData().getActivity_user());
        HomeShufflingAdapter shufflingAdapter = new HomeShufflingAdapter(getActivity(), data);
        shufflingRecycler.setAdapter(shufflingAdapter);
//        shufflingAdapter.notifyDataSetChanged();
        int size = homeShufflingData.getData().getActivity_user().size() - 1;
        if (homeShufflingData.getData().getActivity_user().size() == 0) {
            ll_fragment02.setVisibility(View.GONE);
        }
        tv_user.setText(homeShufflingData.getData().getActivity_user().get(size).getNickname() + "刚刚购买了" + homeShufflingData.getData().getActivity_user().get(size).getTitle());
        GlideUtils.showSmallPic(getContext(), img_user, homeShufflingData.getData().getActivity_user().get(size).getFace());
    }

    @Override
    public void getVisitShop(final VisitShopBean shopBean) {
        final List<VisitShopBean.DataBean> data = shopBean.getData();
        if (data == null || data.size() == 0) {
            mTvHistory.setVisibility(View.GONE);
            mShopVisited.setVisibility(View.GONE);
        } else {
            VisitShopAdapter adapter = new VisitShopAdapter(data, getContext());
            mShopVisited.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
                @Override
                public void onItemClicked(View view, int position) {
                    StoreDetailActivity.actionStart(getActivity(), data.get(position).getGrade_id(), String.valueOf(data.get(position).getShop_id()));
                }
            });
        }
    }


    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "refreshHasLocation":
                    dismissDialog();
                    pageNum = AppConfig.pageNum;
                    canLoadMore = true;
                    startGetData();
                    break;
                case AppConfig.REFRESH_UI:
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            unReadMsgTv.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
            }
        }
    }

    @OnClick({R.id.fragment_index_search_tv, R.id.my_foodsx_tv, R.id.index_choose_tv, R.id.rl_right, R.id.service_im, R.id.my_xq_tv, R.id.index_buy_tv, R.id.index_nearby_tv, R.id.index_country_tv, R.id.index_my_free})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_index_search_tv:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.index_choose_tv:
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
            case R.id.my_xq_tv:
//                startActivity(new Intent(getActivity(), CookBookActivity.class));
                startActivity(new Intent(getActivity(), SpecialtyActivity.class));
                break;
            case R.id.index_buy_tv:
                getActivity().startActivity(new Intent(getActivity(), NearByActivity.class));
                break;
            case R.id.index_nearby_tv:
                getActivity().startActivity(new Intent(getActivity(), FoodActivity.class));
                break;
            case R.id.index_country_tv:
//                //积分商城
//                getActivity().startActivity(new Intent(getActivity(), IntegralMallActivity.class));
                getActivity().startActivity(new Intent(getActivity(), DrinksActivity.class));
                break;
            case R.id.my_foodsx_tv:
                getActivity().startActivity(new Intent(getActivity(), FoodsxActivity.class));
                break;

            case R.id.index_my_free:
//                if (LoginUtil.isLogin()) {
//                    startActivity(new Intent(getActivity(), MyFreeOrderCouponActivity.class));
//                } else {
//                    LoginUtil.login();
//                }
                getActivity().startActivity(new Intent(getActivity(), BuyActivity.class));

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

    private void Youhui() {
        CouponFragment couponFragment = new CouponFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.index2_fragment, couponFragment).commit();
    }

    private void dismissDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
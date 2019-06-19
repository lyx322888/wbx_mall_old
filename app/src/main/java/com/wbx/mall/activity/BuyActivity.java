package com.wbx.mall.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ShopAdapter;
import com.wbx.mall.adapter.TypeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.bean.BusinessInfo;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.TypeInfo;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.expandtabview.ExpandTabView;
import com.wbx.mall.widget.expandtabview.ViewLeft;
import com.wbx.mall.widget.expandtabview.ViewMiddle;
import com.wbx.mall.widget.expandtabview.ViewRight;
import com.wbx.mall.widget.filter.interfaces.OnFilterDoneListener;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class BuyActivity extends BaseActivity implements BaseRefreshListener, OnFilterDoneListener {
    @Bind(R.id.title_image)
    ImageView titleImage;
    @Bind(R.id.rl_left)
    RelativeLayout rlLeft;
    @Bind(R.id.type_recycler_view)
    RecyclerView mTypeRecyclerView;
    @Bind(R.id.shop_recycler_view)
    RecyclerView shopRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.scroll_view)
    MyScrollview mScrollView;
    private MyReceiver receiver;
    private List<TypeInfo> typeInfoList = new ArrayList<>();
    private TypeAdapter mTypeAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private boolean canLoadMore = true;
    private ShopAdapter mShopAdapter;

    @Bind(R.id.expand_view)
    ExpandTabView mExpandView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;


    @Override
    public int getLayoutId() {
        return R.layout.activity_buy;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
//        boolean isNeedBack = false;
//        if (getArguments() != null) {
//            isNeedBack = getArguments().getBoolean("isNeedBack", false);
//        }
//        if (isNeedBack) {
//            rlLeft.setVisibility(View.VISIBLE);
//        } else {
//            rlLeft.setVisibility(View.INVISIBLE);
//        }
        mTypeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        titleImage.setImageResource(R.drawable.buy_title);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void fillData() {
        //广播接受者实例
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshHasLocation");
        this.registerReceiver(receiver, intentFilter);
        mParams.put("city_name", mLocationInfo.getName());
        mParams.put("lat", mLocationInfo.getLat());
        mParams.put("lng", mLocationInfo.getLng());
        TypedArray ar = getResources().obtainTypedArray(R.array.market_type_src);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        String[] stringArray = getResources().getStringArray(R.array.market_type_str);
        for (int i = 0; i < stringArray.length; i++) {
            TypeInfo type = new TypeInfo();
            type.setName(stringArray[i]);
            type.setSrcScore(resIds[i]);
            typeInfoList.add(type);
        }
        mTypeAdapter = new TypeAdapter(typeInfoList, this);
        mTypeRecyclerView.setAdapter(mTypeAdapter);
        mShopAdapter = new ShopAdapter(shopInfoList, this);
        shopRecyclerView.setAdapter(mShopAdapter);
        getShopList();

        viewLeft = new ViewLeft(this);
        mViewArray.add(viewLeft);
        viewMiddle = new ViewMiddle(this);
        mViewArray.add(viewMiddle);
        viewRight = new ViewRight(this);
        mViewArray.add(viewRight);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("选择分类");
        mTextArray.add("选择地区");
        mTextArray.add("选择排序");
        mExpandView.setValue(mTextArray, mViewArray);
        viewLeft.setData(typeInfoList);
        getBusinessAreaData();
        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
            @Override
            public void getValue(int distance, String showText) {
                onRefresh(viewLeft, showText, distance);
            }
        });
        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
            @Override
            public void getValue(int distance, String showText) {
                onRefresh(viewRight, showText, distance);
            }
        });

        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
            @Override
            public void getValue(String showText, int businessId, int areaId) {
                onRefresh(viewMiddle, showText, businessId, areaId);
            }
        });
        List<String> orderByList = new ArrayList<>();
        orderByList.add("智能排序");
        orderByList.add("起送价最低");
        orderByList.add("送货最快");
        orderByList.add("距离最近");
        orderByList.add("销量最高");
        viewRight.setData(orderByList);
    }
    private void getBusinessAreaData() {
        new MyHttp().doPost(Api.getDefault().getScreenArea(mLocationInfo.getName()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<BusinessInfo> data = JSONArray.parseArray(result.getString("data"), BusinessInfo.class);
                viewMiddle.setData(data);
            }

            @Override
            public void onError(int code) {

            }
        });
    }
    private void getShopList() {
//        LoadingDialog.showDialogForLoading(getActivity(),"加载中...",true);
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getBuyShopList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<ShopInfo2> dataList = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                if (null == dataList) {
                    SkeletonScreen skeletonScreen =
                            //绑定当前列表
                            Skeleton.bind(shopRecyclerView)
                                    .adapter(mShopAdapter).shimmer(false)
                                    .count(1)
                                    .load(R.layout.item_nodata)
                                    .show();
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    shopInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                shopInfoList.addAll(dataList);
                mShopAdapter.notifyDataSetChanged();
                mShopAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        if (position + 1 > shopInfoList.size()) {
                            return;
                        }
                        ShopInfo2 shopInfo = shopInfoList.get(position);
                        StoreDetailActivity.actionStart(mContext, shopInfo.getGrade_id(), String.valueOf(shopInfo.getShop_id()));
                    }
                });
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NULLDATA) {
                    shopInfoList.clear();
                    mShopAdapter.notifyDataSetChanged();
                    showShortToast("暂无数据");
                }
            }
        });
    }

    protected void bindEvent() {
        mTypeAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                mScrollView.smoothScrollTo(0, mExpandView.getTop());
                shopInfoList.clear();
                mParams.put("cate_id", position + 1);
                mExpandView.setTitle(typeInfoList.get(position).getCate_id() != 0 ? typeInfoList.get(position).getCate_name() : typeInfoList.get(position).getName(), 0);
                getShopList();
            }
        });
        mRefreshLayout.setRefreshListener(this);
        mShopAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (position + 1 > shopInfoList.size()) {
                    return;
                }
                ShopInfo2 shopInfo = shopInfoList.get(position);
                StoreDetailActivity.actionStart(mContext, shopInfo.getGrade_id(), String.valueOf(shopInfo.getShop_id()));
            }
        });
    }
    @Override
    public void setListener() {

    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {

    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        getShopList();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        getShopList();
    }
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            canLoadMore = true;
            pageNum = AppConfig.pageNum;
            shopInfoList.clear();
            mShopAdapter.notifyDataSetChanged();
            LocationInfo location = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
            mParams.put("city_name", location.getName());
            getShopList();
            getBusinessAreaData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }
    @OnClick({R.id.rl_left, R.id.search_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_left:
                this.finish();
                break;
            case R.id.search_rl:
                startActivity(new Intent(this, MarketSearchActivity.class));
                break;
        }
    }
    private void onRefresh(View view, String text, int businessId, int areaId) {
        mExpandView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !mExpandView.getTitle(position).equals(text)) {
            mExpandView.setTitle(text, position);
        }
        mParams.put("area_id", areaId);
        mParams.put("business_id", businessId);
        pageNum = AppConfig.pageNum;
        getBusinessAreaData();
        getShopList();

    }

    private void onRefresh(View view, String showText, int cateId) {
        mExpandView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !mExpandView.getTitle(position).equals(showText)) {
            mExpandView.setTitle(showText, position);
        }
        if (view == viewLeft) {
            mParams.put("cate_id", cateId + 1);

        } else {
            mParams.put("order", cateId);
        }
        pageNum = AppConfig.pageNum;
        getShopList();
    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }
}

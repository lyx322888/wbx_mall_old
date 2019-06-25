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
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.MyScrollview;
import com.wbx.mall.widget.expandtabview.ExpandTabView;
import com.wbx.mall.widget.expandtabview.ViewLeft;
import com.wbx.mall.widget.expandtabview.ViewMiddle;
import com.wbx.mall.widget.expandtabview.ViewRight;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class FoodsxActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.title_image)
    ImageView titleImage;
    @Bind(R.id.type_recycler_view)
    RecyclerView mTypeRecyclerView;
    @Bind(R.id.shop_recycler_view)
    RecyclerView shopRecyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;

    private List<TypeInfo> typeInfoList = new ArrayList<>();
    private TypeAdapter mTypeAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private boolean canLoadMore = true;
    private ShopAdapter mShopAdapter;
    private MyReceiver receiver;

    @Bind(R.id.expand_view)
    ExpandTabView mExpandView;
    @Bind(R.id.scroll_view)
    MyScrollview mScrollView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;
    @Bind(R.id.banner_im)
    ImageView mBannerIm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_foodsx;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mTypeRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
//        titleImage.setImageResource(R.drawable.nearby_title);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void fillData() {
        mBannerIm.setImageResource(R.drawable.banner_foods);
        //广播接受者实例
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshHasLocation");
        mContext.registerReceiver(receiver, intentFilter);
        if (mLocationInfo != null) {
            mParams.put("city_name", mLocationInfo.getName());
            mParams.put("lat", mLocationInfo.getLat());
            mParams.put("lng", mLocationInfo.getLng());
        }
        TypedArray ar = getResources().obtainTypedArray(R.array.shop_type_src);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        String[] stringArray = getResources().getStringArray(R.array.shop_type_str);
        for (int i = 0; i < stringArray.length; i++) {
            TypeInfo type = new TypeInfo();
            type.setName(stringArray[i]);
            type.setSrcScore(resIds[i]);
            typeInfoList.add(type);
        }
        mTypeAdapter = new TypeAdapter(typeInfoList, mContext);
        mTypeRecyclerView.setAdapter(mTypeAdapter);
        mShopAdapter = new ShopAdapter(shopInfoList, mContext);
        shopRecyclerView.setAdapter(mShopAdapter);
        getShopList();


        viewLeft = new ViewLeft(mContext);
        mViewArray.add(viewLeft);
        viewMiddle = new ViewMiddle(mContext);
        mViewArray.add(viewMiddle);
        viewRight = new ViewRight(mContext);
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
//        orderByList.add("销量最高");
        viewRight.setData(orderByList);
    }

    private void getBusinessAreaData() {
        LocationInfo location = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        if (location == null) {
            ToastUitl.showShort("请先定位");
            return;
        }
        new MyHttp().doPost(Api.getDefault().getScreenArea(location.getName()), new HttpListener() {
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
//        LoadingDialog.showDialogForLoading(mContext,"加载中...",true);
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        mParams.put("cate_id", 21);
        new MyHttp().doPost(Api.getDefault().getNearByShopList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
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
                shopInfoList.addAll(dataList);
                mShopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NULLDATA) {
                    shopInfoList.clear();
                    mShopAdapter.notifyDataSetChanged();
                    showShortToast("暂无数据");
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void setListener() {
        mTypeAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                mScrollView.smoothScrollTo(0, mExpandView.getTop());
                shopInfoList.clear();
                mExpandView.setTitle(typeInfoList.get(position).getName(), 0);
                mParams.put("cate_id", position + 1);
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(receiver);
    }

    @OnClick({R.id.search_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                startActivity(new Intent(mContext, SearchActivity.class));
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

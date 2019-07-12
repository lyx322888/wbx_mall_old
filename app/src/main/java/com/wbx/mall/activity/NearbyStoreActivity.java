package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ShopGoodsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 首页附近店铺
 */
public class NearbyStoreActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.search_rl)
    RelativeLayout searchRl;
    @Bind(R.id.title_view)
    RelativeLayout titleView;
    @Bind(R.id.banner_im)
    ImageView bannerIm;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout refreshLayout;
    @Bind(R.id.img_empty_data)
    ImageView bgEmptyView;
    private int[] mBannerID = {R.drawable.bg_banner6, R.drawable.bg_banner2, R.drawable.bg_banner5, R.drawable.bg_banner4, R.drawable.banner_techan};
    private String[] mCateID = {"15", "19", "20", "23", "22"};
    private String[] mTitle = {"果蔬生鲜", "超市便利", "餐饮美食", "甜点饮品", "特产零食"};
    public static final String STORE_INDEX = "StoreIndex";
    private boolean canLoadMore = true;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> mParams = new HashMap<>();
    private List<ShopInfo2> shopInfoList = new ArrayList<>();
    private ShopGoodsAdapter mShopAdapter;


    public static void startNearbyStore(Context context, int index) {
        Intent intent = new Intent(context, NearbyStoreActivity.class);
        intent.putExtra(STORE_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_nearby_store;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        int mStoreIndex = getIntent().getIntExtra(STORE_INDEX, 0);//页面类型
        tvTitle.setText(mTitle[mStoreIndex]);
        bannerIm.setImageResource(mBannerID[mStoreIndex]);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        refreshLayout.setRefreshListener(this);
        mShopAdapter = new ShopGoodsAdapter(shopInfoList, this);
        recyclerView.setAdapter(mShopAdapter);
        if (mLocationInfo != null) {
            mParams.put("city_name", mLocationInfo.getName());
            mParams.put("lat", mLocationInfo.getLat());
            mParams.put("lng", mLocationInfo.getLng());
        }
        mParams.put("grade_id", mCateID[mStoreIndex]);
        mShopAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ShopInfo2 shopInfo = shopInfoList.get(position);
                SPUtils.put("shopInfo_goods", shopInfo.getShop_id() + "", NearbyStoreActivity.this);
                StoreDetailActivity.actionStart(NearbyStoreActivity.this, shopInfo.getGrade_id()== AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopInfo.getShop_id()));
            }
        });
    }

    @Override
    public void fillData() {
        getShopList();
    }

    @Override
    public void setListener() {

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
            refreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        getShopList();
    }

    @OnClick({R.id.search_rl})
    public void onClick(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void getShopList() {
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getIndexShopInfo2(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                List<ShopInfo2> dataList = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                if (null == dataList) {
                    bgEmptyView.setVisibility(View.VISIBLE);
                    canLoadMore = false;
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    shopInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    canLoadMore = false;
                }
                bgEmptyView.setVisibility(View.GONE);
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
                bgEmptyView.setVisibility(View.VISIBLE);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }
}

package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ShopAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/5/25.
 * 菜市场搜索
 */

public class MarketSearchActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @Bind(R.id.recycler_view_search_result)
    RecyclerView rvSearchResult;
    @Bind(R.id.ptrl)
    PullToRefreshLayout ptrl;
    private List<ShopInfo2> storeInfoList = new ArrayList<>();
    private ShopAdapter mStoreAdapter;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;
    private HashMap<String, Object> mParams = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_market_search;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        initSearchResultRecyclerView();
        initSearchEditText();
    }

    private void initSearchEditText() {
        etSearchKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    search(s.toString());
                }
            }
        });
        etSearchKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(etSearchKeyword.getText().toString())) {
                        ToastUitl.showShort("请输入关键字");
                    } else {
                        search(etSearchKeyword.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initSearchResultRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvSearchResult.setLayoutManager(linearLayoutManager);
        mStoreAdapter = new ShopAdapter(storeInfoList, mActivity);
        rvSearchResult.setAdapter(mStoreAdapter);
    }

    @Override
    public void fillData() {
        mParams.put("city_name", mLocationInfo.getName());
        mParams.put("lat", mLocationInfo.getLat());
        mParams.put("lng", mLocationInfo.getLng());
        mParams.put("num", pageSize);
    }

    private void search(String keyword) {
        pageNum = AppConfig.pageNum;
        canLoadMore = true;
        mParams.put("keyword", keyword);
        getServiceData();
    }

    private void getServiceData() {
        mParams.put("page", pageNum);
        new MyHttp().doPost(Api.getDefault().getBuyShopList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<ShopInfo2> dataList = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
                if (null == dataList && pageNum == AppConfig.pageNum) {
                    //无数据情况下
                    ptrl.showView(ViewStatus.EMPTY_STATUS);
                    ptrl.buttonClickNullData(MarketSearchActivity.this, "getServiceData");
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    storeInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                storeInfoList.addAll(dataList);
                mStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
                if (code == AppConfig.ERROR_STATE.NULLDATA) {
                    //无数据情况下
                    if (pageNum == AppConfig.pageNum) {
                        ptrl.showView(ViewStatus.EMPTY_STATUS);
                        ptrl.buttonClickNullData(MarketSearchActivity.this, "getServiceData");
                    } else {
                        canLoadMore = false;
                        showShortToast("没有更多数据了");
                    }
                } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                    ptrl.showView(ViewStatus.ERROR_STATUS);
                    ptrl.buttonClickError(MarketSearchActivity.this, "getServiceData");
                }
            }
        });
    }

    @Override
    public void setListener() {
        ptrl.setRefreshListener(this);
        mStoreAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ShopInfo2 shopInfo = mStoreAdapter.getItem(position);
                StoreDetailActivity.actionStart(mContext, shopInfo.getGrade_id()== AppConfig.StoreType.VEGETABLE_MARKET, String.valueOf(shopInfo.getShop_id()));
            }
        });
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        storeInfoList.clear();
        getServiceData();
    }

    @Override
    public void loadMore() {
        if (!canLoadMore) {
            ptrl.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        pageNum++;
        getServiceData();
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        finish();
    }
}
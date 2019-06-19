package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.SearchHistoryAdapter;
import com.wbx.mall.adapter.ShopGoodsAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.SearchInfo;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.utils.SearchHistoryDB;
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
 * Created by wushenghui on 2017/7/18.
 */

public class SearchActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @Bind(R.id.recycler_view_search_history)
    RecyclerView rvSearchHistory;
    @Bind(R.id.search_underline)
    View searchUnderline;
    @Bind(R.id.ptrl)
    PullToRefreshLayout ptrl;
    @Bind(R.id.recycler_view_search_result)
    RecyclerView rvSearchResult;
    @Bind(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private HashMap<String, Object> params = new HashMap<>();
    //历史搜索记录
    private ArrayList<SearchInfo> lstSearchHistory = new ArrayList<>();
    private int searchType = AppConfig.SearchType.GOODS;

    private List<ShopInfo2> lstSearchResult = new ArrayList<>();
    private boolean canLoadMore = true;//控制下次是否加载更多
    private ShopGoodsAdapter mAdapter;
    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchHistoryDB searchHistoryDB;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {
        tvSearchType.setText(searchType == AppConfig.SearchType.GOODS ? "商品" : "店铺");
    }

    @Override
    public void initView() {
        initSearchHistoryRecyclerView();
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
                    search(s.toString(), searchType);
                } else {
                    llSearchHistory.setVisibility(View.VISIBLE);
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
                        search(etSearchKeyword.getText().toString(), searchType);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void search(String keyword, int searchType) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        String key = keyword.replaceAll("'", "");
        searchHistoryDB.insertHistory(key, searchType);
        setAllHistorys();
        llSearchHistory.setVisibility(View.GONE);
        pageNum = AppConfig.pageNum;
        canLoadMore = true;
        params.put("type", searchType);
        params.put("keyword", keyword);
        getServiceData();
    }

    private void initSearchResultRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        rvSearchResult.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopGoodsAdapter(lstSearchResult, mActivity);
        rvSearchResult.setAdapter(mAdapter);
    }

    private void initSearchHistoryRecyclerView() {
        //初始化recyclerView
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(this));//list类型
        searchHistoryAdapter = new SearchHistoryAdapter(this, lstSearchHistory);
        rvSearchHistory.setAdapter(searchHistoryAdapter);
        searchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(String keyword, int searchType) {
                search(keyword, searchType);
                KeyBordUtil.hideSoftKeyboard(etSearchKeyword);
            }

            @Override
            public void onItemClick(String keyword) {

            }

            @Override
            public void onItemDeleteClick(String keyword, int position) {
                searchHistoryDB.deleteHistory(keyword);
                lstSearchHistory.remove(position);
                checkHistorySize();
                searchHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void fillData() {
        //实例化数据库
        searchHistoryDB = new SearchHistoryDB(this, SearchHistoryDB.DB_NAME, null, 1);
        setAllHistorys();
        params.put("city_name", mLocationInfo.getName());
        params.put("lat", mLocationInfo.getLat());
        params.put("lng", mLocationInfo.getLng());
        params.put("type", searchType);
        params.put("keyword", "");
        params.put("page", pageNum);
        params.put("num", pageSize);
    }

    private void setAllHistorys() {
        lstSearchHistory.clear();
        lstSearchHistory.addAll(searchHistoryDB.queryAllHistory());
        checkHistorySize();
        searchHistoryAdapter.notifyDataSetChanged();
    }

    private void checkHistorySize() {
        if (lstSearchHistory.size() < 1) {
            searchUnderline.setVisibility(View.GONE);
        } else {
            searchUnderline.setVisibility(View.VISIBLE);
        }
    }

    private void getServiceData() {
        new MyHttp().doPost(Api.getDefault().indexSearch(params), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
                ptrl.showView(ViewStatus.CONTENT_STATUS);
                List<ShopInfo2> dataList = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                if (null == dataList && pageNum == AppConfig.pageNum) {
                    //无数据情况下
                    ptrl.showView(ViewStatus.EMPTY_STATUS);
                    ptrl.buttonClickNullData(SearchActivity.this, "getServiceData");
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    lstSearchResult.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                lstSearchResult.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        ptrl.showView(ViewStatus.EMPTY_STATUS);
                        ptrl.buttonClickNullData(SearchActivity.this, "getServiceData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        ptrl.showView(ViewStatus.ERROR_STATUS);
                        ptrl.buttonClickError(SearchActivity.this, "getServiceData");
                    }
                } else {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        canLoadMore = false;
                    }
                }
                ptrl.finishRefresh();
                ptrl.finishLoadMore();
            }
        });
    }

    @Override
    public void setListener() {
        ptrl.setRefreshListener(this);
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ShopInfo2 shopInfo = mAdapter.getItem(position);
                StoreDetailActivity.actionStart(mContext, shopInfo.getGrade_id(), String.valueOf(shopInfo.getShop_id()));
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
            ptrl.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        pageNum++;
        params.put("page", pageNum);
        getServiceData();
    }

    @OnClick({R.id.tv_search_type, R.id.tv_cancel, R.id.tv_clear_search_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_type:
                showPopupWin(view);
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_clear_search_history:
                clearSearchHistory();
                break;
        }
    }


    private void showPopupWin(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_search_type_view, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
        contentView.findViewById(R.id.pop_type_store_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = AppConfig.SearchType.STORE;
                tvSearchType.setText("店铺");
                popupWindow.dismiss();
                search(etSearchKeyword.getText().toString(), searchType);
            }
        });
        contentView.findViewById(R.id.pop_type_goods_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = AppConfig.SearchType.GOODS;
                tvSearchType.setText("商品");
                popupWindow.dismiss();
                search(etSearchKeyword.getText().toString(), searchType);
            }
        });
    }


    private void clearSearchHistory() {
        searchHistoryDB.deleteAllHistory();
        lstSearchHistory.clear();
        searchUnderline.setVisibility(View.GONE);
        searchHistoryAdapter.notifyDataSetChanged();
    }
}
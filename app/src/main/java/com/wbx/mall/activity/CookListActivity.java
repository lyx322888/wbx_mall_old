package com.wbx.mall.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.CookListAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.CookBookDetailInfo;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/8/24.
 */

public class CookListActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.cook_refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.show_view_btn)
    Button showViewBtn;
    @Bind(R.id.title_search_edit)
    EditText searchEdit;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.cook_list_recycler_view)
    RecyclerView cookListRv;
    private boolean isShow = true;
    public static final long DURATION = 200;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private List<CookBookDetailInfo> cookBookDetailInfos = new ArrayList<>();
    private String cookClassId;
    private CookListAdapter mAdapter;
    private boolean canLoadMore = true;
    private boolean isIdSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cook_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mAdapter = new CookListAdapter(cookBookDetailInfos, mContext);
        cookListRv.setLayoutManager(new LinearLayoutManager(mContext));
        cookListRv.setAdapter(mAdapter);
    }

    @Override
    public void fillData() {
        cookClassId = getIntent().getStringExtra("cookClassId");
        if (null == cookClassId) return;
        isIdSearch = true;
        getData(isIdSearch);
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
    }

    private void refreshData() {
        getData(true);
    }

    private void getData(boolean isSearchToId) {
        if (isSearchToId) {
            mParams.put("classid", cookClassId);
        } else {
            mParams.put("keyword", cookClassId);
        }
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(isSearchToId ? Api.getDefault().searchCookForId(mParams) : Api.getDefault().searchCook(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (pageNum == AppConfig.pageNum && result.getJSONObject("data") == null) {
                    //无数据情况下
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    mRefreshLayout.buttonClickNullData(CookListActivity.this, "refreshData");
                    return;
                }
                List<CookBookDetailInfo> dataList = JSONArray.parseArray(result.getJSONObject("data").getJSONObject("result").getString("list"), CookBookDetailInfo.class);
                if (null == dataList) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    cookBookDetailInfos.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                cookBookDetailInfos.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(CookListActivity.this, "refreshData");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(CookListActivity.this, "refreshData");
                    } else {

                    }
                } else {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        canLoadMore = false;
                    }
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener(R.id.root_view, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                CookBookDetailInfo item = mAdapter.getItem(position);
                Intent intent = new Intent(mContext, CookBookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRefreshLayout.setRefreshListener(this);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
                    pageNum = AppConfig.pageNum;
                    isIdSearch = false;
                    KeyBordUtil.hideSoftKeyboard(searchEdit);
                    canLoadMore = true;
                    cookClassId = textView.getText().toString();
                    getData(false);
                }
                return false;
            }
        });

        showViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShow = !isShow;
                if (isShow) {
                    showViewBtn.setText("取消");
                    actionOtherVisible(true, searchEdit, searchEdit);
                    KeyBordUtil.showSoftKeyboard(searchEdit);
                } else {
                    showViewBtn.setText("搜索");
                    actionOtherVisible(false, searchEdit, searchEdit);
                    KeyBordUtil.hideSoftKeyboard(searchEdit);
                }
            }
        });
    }


    @SuppressLint("NewApi")
    private void actionOtherVisible(final boolean isShow, final View triggerView, final View animView) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (isShow) {
                animView.setVisibility(View.VISIBLE);
            } else {
                animView.setVisibility(View.INVISIBLE);
            }
            return;
        }

        /**
         * 计算 triggerView 的中心位置
         */
        int[] tvLocation = new int[2];
        triggerView.getLocationInWindow(tvLocation);
        int tvX = tvLocation[0] + triggerView.getWidth() / 2;
        int tvY = tvLocation[1] + triggerView.getHeight() / 2;

        /**
         * 计算 animView 的中心位置
         */
        int[] avLocation = new int[2];
        animView.getLocationInWindow(avLocation);
        int avX = avLocation[0] + animView.getWidth() / 2;
        int avY = avLocation[1] + animView.getHeight() / 2;

        int rippleW = tvX < avX ? animView.getWidth() - tvX : tvX - avLocation[0];
        int rippleH = tvY < avY ? animView.getHeight() - tvY : tvY - avLocation[1];

        float maxRadius = (float) Math.sqrt(rippleW * rippleW + rippleH * rippleH);
        float startRadius;
        float endRadius;

        if (isShow) {
            startRadius = 0;
            endRadius = maxRadius;
        } else {
            startRadius = maxRadius;
            endRadius = 0;
        }

        Animator anim = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius);
        animView.setVisibility(View.VISIBLE);
        anim.setDuration(DURATION);
        anim.setInterpolator(new DecelerateInterpolator());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isShow) {
                    animView.setVisibility(View.VISIBLE);

                } else {
                    animView.setVisibility(View.INVISIBLE);

                }
            }
        });

        anim.start();
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
        getData(isIdSearch);
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        getData(isIdSearch);
    }
}

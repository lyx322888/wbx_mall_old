package com.wbx.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.CommentAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CommentInfo;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/7/15.
 * 查看更多评价
 */

public class CommentMoreActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    CommentAdapter mAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;
    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private int storeId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_more;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        storeId = getIntent().getIntExtra("storeId", 0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommentAdapter(commentInfoList, mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void fillData() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        mParams.put("shop_id", storeId);
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getCommentList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                List<CommentInfo> dataList = JSONArray.parseArray(result.getString("data"), CommentInfo.class);
                if (null == dataList) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    commentInfoList.clear();
                }
                if (dataList.size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                commentInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.NULLDATA) {
                    mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                }

            }
        });

    }

    @Override
    public void setListener() {
        mRefreshLayout.setRefreshListener(this);
    }

    @Override
    public void refresh() {
        pageNum = AppConfig.pageNum;
        canLoadMore = true;
        fillData();
    }

    @Override
    public void loadMore() {
        pageNum++;
        if (!canLoadMore) {
            mRefreshLayout.finishLoadMore();
            showShortToast("没有更多数据了");
            return;
        }
        fillData();
    }
}

package com.wbx.mall.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.adapter.ShopCommentAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.CommentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

public class ShopCommentFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String SHOP_ID = "SHOP_ID";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private String shopId;
    ShopCommentAdapter mAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private List<CommentInfo> commentInfoList = new ArrayList<>();

    public ShopCommentFragment() {
    }

    public static ShopCommentFragment newInstance(String shopId) {
        ShopCommentFragment fragment = new ShopCommentFragment();
        Bundle args = new Bundle();
        args.putString(SHOP_ID, shopId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getString(SHOP_ID);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shop_comment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShopCommentAdapter(commentInfoList);
        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEmptyView(R.layout.layout_empty_comment);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void fillData() {
        mParams.put("shop_id", shopId);
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        new MyHttp().doPost(Api.getDefault().getCommentList(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                List<CommentInfo> dataList = JSONArray.parseArray(result.getString("data"), CommentInfo.class);
                if (null == dataList) {
                    mAdapter.loadMoreEnd();
                    return;
                }
                mAdapter.loadMoreComplete();
                commentInfoList.addAll(dataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        fillData();
    }
}

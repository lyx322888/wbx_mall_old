package com.wbx.mall.module.mine.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.IntegralExchangeRecordActivity;
import com.wbx.mall.activity.SubmitIntegralExchangeActivity;
import com.wbx.mall.adapter.IntegralMallAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.bean.IntegralGoodsBean;
import com.wbx.mall.bean.IntegralGoodsDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wushenghui on 2017/8/11.
 * 积分商城
 */
public class IntegralMallActivity extends BaseActivity implements BaseRefreshListener {
    @Bind(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private List<IntegralGoodsBean.GoodsBean> lstData = new ArrayList<>();
    private IntegralMallAdapter mAdapter;
    private boolean canLoadMore = true;//控制下次是否加载更多
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    private IntegralGoodsBean data;

    @Override
    public int getLayoutId() {
        return R.layout.activity_integral_mall;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = DisplayUtil.dip2px(1);
                outRect.right = DisplayUtil.dip2px(1);
            }
        });
        mAdapter = new IntegralMallAdapter(lstData, mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        LoadingDialog.showDialogForLoading(this, "加载中...", true);
        new MyHttp().doPost(Api.getDefault().getIntegralGoods(LoginUtil.getLoginToken(), pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                data = result.getObject("data", IntegralGoodsBean.class);
                tvIntegral.setText(String.valueOf(data.getIntegral()));
                if (null == data.getGoods()) {
                    return;
                }
                if (pageNum == AppConfig.pageNum) {
                    lstData.clear();
                }
                if (data.getGoods().size() < pageSize) {
                    //说明下次已经没有数据了
                    canLoadMore = false;
                }
                lstData.addAll(data.getGoods());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void fillData() {
    }

    @Override
    public void setListener() {
        mRefreshLayout.setRefreshListener(this);
//        mAdapter.setOnItemClickListener(R.id.ll_root, new BaseAdapter.ItemClickListener() {
//            @Override
//            public void onItemClicked(View view, int position) {
//                IntegralGoodsBean.GoodsBean item = mAdapter.getItem(position);
//                IntegralGoodsDetailActivity.actionStart(mContext, item.getGoods_id(), data.getIntegral());
//            }
//        });
        mAdapter.setOnItemClickListener(R.id.btn_exchange, new BaseAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                IntegralGoodsBean.GoodsBean item = mAdapter.getItem(position);
                if (item.getIntegral() > data.getIntegral()) {
                    showShortToast("抱歉，您当前的积分不够兑换此商品哦。");
                    return;
                }
                IntegralGoodsDetailBean integralGoodsDetailBean = new IntegralGoodsDetailBean();
                integralGoodsDetailBean.setFace_pic(item.getFace_pic());
                integralGoodsDetailBean.setGoods_id(item.getGoods_id());
                integralGoodsDetailBean.setTitle(item.getTitle());
                integralGoodsDetailBean.setIntegral(item.getIntegral());
                SubmitIntegralExchangeActivity.actionStart(IntegralMallActivity.this, integralGoodsDetailBean, 1);
            }
        });
    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = 1;
        loadData();
    }

    @Override
    public void loadMore() {
        if (!canLoadMore) {
            ToastUitl.showShort("没有更多数据");
            mRefreshLayout.finishLoadMore();
            return;
        }
        pageNum++;
        loadData();
    }

    @OnClick(R.id.tv_exchange_record)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exchange_record:
                startActivity(new Intent(mContext, IntegralExchangeRecordActivity.class));
                break;
        }
    }
}

package com.wbx.mall.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbx.mall.R;
import com.wbx.mall.activity.DetailActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.GoodsAdapter;
import com.wbx.mall.adapter.GoodsTypeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.CateInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.dialog.ProductDetailDialog;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.AddWidget;
import com.wbx.mall.widget.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class Goods2Fragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String SHOP_ID = "shopId";
    private static final String IS_VEGETABLE_MARKET = "IsVegetableMarket";
    private String mShopId;
    private String mCateId = "0";
    private boolean canLoadMore = false;
    private boolean isVM;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.recycler_classify)
    RecyclerView mClassifyView;
    @Bind(R.id.recycler_goods)
    RecyclerView mGoodView;
    private List<CateInfo> mClassifyList = new ArrayList<>();
    private List<GoodsInfo2> mGoodList = new ArrayList<>();
    private GoodsTypeAdapter mClassifyAdapter;
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private GoodsAdapter mGoodAdapter;

    public Goods2Fragment() {
    }

    public static Goods2Fragment newInstance(String shopId, boolean isVM) {
        Goods2Fragment fragment = new Goods2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOP_ID, shopId);
        bundle.putBoolean(IS_VEGETABLE_MARKET, isVM);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_goods2;
    }

    @Override
    public void initPresenter() {
        if (getArguments() != null) {
            mShopId = getArguments().getString(SHOP_ID);
            isVM = getArguments().getBoolean(IS_VEGETABLE_MARKET);
        }
    }

    @Override
    protected void initView() {
        View footView = new View(getActivity());
        footView.setMinimumHeight(DisplayUtil.dip2px(50));
        mClassifyView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mClassifyView.setHasFixedSize(true);
        mClassifyView.addItemDecoration(new SimpleDividerDecoration(getActivity()));
        mClassifyAdapter = new GoodsTypeAdapter(mClassifyList);
        mClassifyAdapter.addFooterView(footView);
        mClassifyAdapter.bindToRecyclerView(mClassifyView);
        mClassifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mCateId.equals(mClassifyList.get(position).getCate_id())) {
                    mCateId = mClassifyList.get(position).getCate_id();
                    mClassifyAdapter.setItemCheck(position);
                    pageNum = AppConfig.pageNum;
                    mGoodList.clear();
                    mGoodAdapter.notifyDataSetChanged();
                    getGoodsList();
                }
            }
        });
        mGoodView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoodView.setHasFixedSize(true);
        mGoodAdapter = new GoodsAdapter(mGoodList,(DetailActivity) getActivity(), true);
        mGoodAdapter.bindToRecyclerView(mGoodView);
        mGoodAdapter.setOnLoadMoreListener(this, mGoodView);
        mGoodAdapter.setEmptyView(R.layout.layout_empty_comment);
        View footView2 = new View(getActivity());
        footView2.setMinimumHeight(DisplayUtil.dip2px(50));
        mGoodAdapter.setFooterView(footView2);
        mGoodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showGoodsDetail(position);
//                switch (view.getId()) {
//                    case R.id.root_view:
//                         showGoodsDetail(position);
//                        break;
//                    case R.id.tv_free_gain:
////                        freeGain(position);
//                        break;
//                }
            }
        });
    }

    @Override
    protected void fillData() {
        getClassify();
        getGoodsList();
    }

    @Override
    protected void bindEvent() {
    }

    /**
     * 获取左侧分类
     */
    private void getClassify() {
        new MyHttp().doPost(Api.getDefault().getListCate(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), mShopId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mClassifyList.addAll(JSONArray.parseArray(result.getString("data"), CateInfo.class));
                mClassifyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    /**
     * 获取商品数据
     */
    private void getGoodsList() {
        new MyHttp().doPost(Api.getDefault().getListGoods(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), mCateId, mShopId, pageNum, pageSize), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                if (result.getString("msg").equals("暂无数据")) {
                    canLoadMore = false;
                    mGoodAdapter.loadMoreEnd();
                } else {
                    List<GoodsInfo2> bean = JSONArray.parseArray(result.getString("data"), GoodsInfo2.class);
                    if (bean.size() < pageSize) {
                        canLoadMore = false;
                        mGoodAdapter.loadMoreEnd();
                    }
                    canLoadMore = true;
                    mGoodList.addAll(bean);
                    mGoodAdapter.notifyDataSetChanged();
                    mGoodAdapter.loadMoreComplete();
                }
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        if (canLoadMore) {
            pageNum++;
            getGoodsList();
        }
    }

    private void showGoodsDetail(int position) {
        GoodsInfo2 goodInfo = mGoodList.get(position);
        ProductDetailDialog detailDialog = new ProductDetailDialog(getActivity(), goodInfo);
        detailDialog.show();
//        if (isVM) {
//        } else {
//            Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
//            intent.putExtra("goodsId", goodInfo.getGoods_id());
//            intent.putExtra("shopId", goodInfo.getShop_id());
//            intent.putExtra("goodsInfo", goodInfo);
//            startActivityForResult(intent, StoreDetailActivity.REQUEST_CODE_GOODS_DETAIL);
//        }
    }
}

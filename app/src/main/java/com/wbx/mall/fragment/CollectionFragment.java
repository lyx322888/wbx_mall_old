package com.wbx.mall.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.adapter.CollectGoodsAdapter;
import com.wbx.mall.adapter.CollectionShopAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseAdapter;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.iosdialog.AlertDialog;
import com.wbx.mall.widget.refresh.BaseRefreshListener;
import com.wbx.mall.widget.refresh.PullToRefreshLayout;
import com.wbx.mall.widget.refresh.ViewStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wushenghui on 2017/5/13.
 */

public class CollectionFragment extends BaseFragment implements BaseRefreshListener {
    @Bind(R.id.refresh_layout)
    PullToRefreshLayout mRefreshLayout;
    @Bind(R.id.collection_recycler_view)
    RecyclerView mRecyclerView;
    private int mPosition;
    private List<ShopInfo2> storeInfoList = new ArrayList<>();
    private List<GoodsInfo2> goodsInfoList = new ArrayList<>();
    private BaseAdapter mAdapter;
    private HashMap<String, Object> mParams = new HashMap<>();
    private int pageNum = AppConfig.pageNum;
    private int pageSize = AppConfig.pageSize;
    private boolean canLoadMore = true;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void fillData() {
        mPosition = getArguments().getInt("position");
        //获得收藏
        mParams.put("login_token", SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN));
        mParams.put("page", pageNum);
        mParams.put("num", pageSize);
        if (mPosition == 0) {
            mAdapter = new CollectGoodsAdapter(goodsInfoList, getActivity());
        } else {
            mAdapter = new CollectionShopAdapter(storeInfoList, getActivity());
        }
        mRecyclerView.setAdapter(mAdapter);
        getCollectionInfo();
    }

    private void getCollectionInfo() {
        mRefreshLayout.showView(ViewStatus.LOADING_STATUS);
        new MyHttp().doPost(mPosition == 0 ? Api.getDefault().getLikeGoods(mParams) : Api.getDefault().getLikeStores(mParams), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                if (mPosition == 0) {
                    List<GoodsInfo2> goodsInfos = JSONArray.parseArray(result.getString("data"), GoodsInfo2.class);
                    if (null == goodsInfos) {
                        return;
                    }
                    if (pageNum == AppConfig.pageNum) {
                        goodsInfoList.clear();
                    }
                    if (goodsInfos.size() < pageSize) {
                        //说明下次已经没有数据了
                        canLoadMore = false;
                    }
                    goodsInfoList.addAll(goodsInfos);


                } else {
                    List<ShopInfo2> shopInfos = JSONArray.parseArray(result.getString("data"), ShopInfo2.class);
                    if (null == shopInfos) {
                        return;
                    }
                    if (pageNum == AppConfig.pageNum) {
                        storeInfoList.clear();
                    }
                    if (shopInfos.size() < pageSize) {
                        //说明下次已经没有数据了
                        canLoadMore = false;
                    }
                    storeInfoList.addAll(shopInfos);
                }
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                mAdapter.notifyDataSetChanged();
                mAdapter.setOnItemClickListener(R.id.collection_into_store_btn, new BaseAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        //进入店铺
                        ShopInfo2 shopInfo = storeInfoList.get(position);
                        StoreDetailActivity.actionStart(getContext(), shopInfo.getGrade_id(), String.valueOf(shopInfo.getShop_id()));
                    }
                });
                mAdapter.setOnItemClickListener(R.id.collection_cancel_concern_btn, new BaseAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, final int position) {
                        new AlertDialog(getActivity()).builder()
                                .setTitle("提示")
                                .setMsg("取消收藏")
                                .setNegativeButton("再想想", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        cancelGoods(position);
                                    }
                                }).show();
                    }
                });

                mAdapter.setOnItemClickListener(R.id.cancel_collect_btn, new BaseAdapter.ItemClickListener() {
                    @Override
                    public void onItemClicked(View view, final int position) {
                        new AlertDialog(getActivity()).builder()
                                .setTitle("提示")
                                .setMsg("取消收藏")
                                .setNegativeButton("再想想", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        cancelCollectProduct(position);

                                    }
                                }).show();
                    }
                });

            }

            @Override
            public void onError(int code) {
                if (pageNum == AppConfig.pageNum) {
                    if (code == AppConfig.ERROR_STATE.NULLDATA) {
                        //无数据情况下
                        mRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                        mRefreshLayout.buttonClickNullData(CollectionFragment.this, "getCollectionInfo");
                    } else if (code == AppConfig.ERROR_STATE.NO_NETWORK || code == AppConfig.ERROR_STATE.SERVICE_ERROR) {
                        mRefreshLayout.showView(ViewStatus.ERROR_STATUS);
                        mRefreshLayout.buttonClickError(CollectionFragment.this, "getCollectionInfo");
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

    private void cancelCollectProduct(final int position) {
        new MyHttp().doPost(Api.getDefault().cancelCollectProduct(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), goodsInfoList.get(position).getFavorites_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                //一定要这三个方法都写 不然会出错
                goodsInfoList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, goodsInfoList.size());
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    @Override
    protected void bindEvent() {
        mRefreshLayout.setRefreshListener(this);


    }

    //取消收藏的店铺
    private void cancelGoods(final int position) {
        new MyHttp().doPost(Api.getDefault().cancelLikeStores(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), storeInfoList.get(position).getShop_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                //一定要这三个方法都写 不然会出错
                storeInfoList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, storeInfoList.size());
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    @Override
    public void refresh() {
        canLoadMore = true;
        pageNum = AppConfig.pageNum;
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

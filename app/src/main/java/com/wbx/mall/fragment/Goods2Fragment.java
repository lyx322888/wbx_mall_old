package com.wbx.mall.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.activity.FreeActivityDetailActivity;
import com.wbx.mall.activity.GoodsDetailActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.adapter.GoodsAdapter;
import com.wbx.mall.adapter.GoodsTypeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseFragment;
import com.wbx.mall.bean.CateInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.SpecInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.KeyBordUtil;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

import static android.view.View.GONE;

public class Goods2Fragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String SHOP_ID = "shopId";
    private static final String IS_VEGETABLE_MARKET = "IsVegetableMarket";
    private static final String SHOPINFO = "shopInfo";
    private String mShopId;
    private String mCateId = "0";
    private ShopInfo2 mShopInfo;//店铺信息
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

    public static Fragment newInstance(String shopId, boolean isVM, ShopInfo2 detail) {
        Goods2Fragment fragment = new Goods2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOP_ID, shopId);
        bundle.putBoolean(IS_VEGETABLE_MARKET, isVM);
        bundle.putSerializable(SHOPINFO, detail);
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
            mShopInfo = (ShopInfo2) getArguments().getSerializable(SHOPINFO);
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
        mGoodAdapter = new GoodsAdapter(mGoodList, (StoreDetailActivity) getActivity(), true);
        mGoodAdapter.bindToRecyclerView(mGoodView);
        mGoodAdapter.setOnLoadMoreListener(this, mGoodView);
        mGoodAdapter.setEmptyView(R.layout.layout_empty_date);
        View footView2 = new View(getActivity());
        footView2.setMinimumHeight(DisplayUtil.dip2px(50));
        mGoodAdapter.setFooterView(footView2);
        mGoodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.goods_pic_im:
                        GoodsInfo2 info2 = mGoodList.get(position);
                        if (info2.getIs_share_free() == 1) {
                            FreeActivityDetailActivity.actionStart(getActivity(), info2.getShop_id() + "", info2.getGoods_id() + "", info2.getGrade_id());
                        } else {
                            showGoodsDetail(info2);
                        }
                        break;
                    case R.id.tv_free_gain:
                        freeGain(position);
                        break;
                }
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
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    search(textView.getText().toString());
                    KeyBordUtil.hideSoftKeyboard(etSearch);
                }
                return false;
            }
        });
    }

    private void search(String toString) {
        keyWord = toString;
        mGoodList.clear();
        mGoodAdapter.notifyDataSetChanged();
        getGoodsList();
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

    private String keyWord;

    /**
     * 获取商品数据
     */
    private void getGoodsList() {
        new MyHttp().doPost(Api.getDefault().getListGoods(SPUtils.getSharedStringData(getActivity(), AppConfig.LOGIN_TOKEN), mCateId, mShopId, pageNum, pageSize, keyWord), new HttpListener() {
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
                    if (isVM) {
                        if (bean.size() != 0) {//菜市场数据模型转换为实体店
                            for (GoodsInfo2 goodsInfo : bean) {
                                goodsInfo.setGoods_id(goodsInfo.getProduct_id());
                                goodsInfo.setTitle(goodsInfo.getProduct_name());
                                goodsInfo.setIntro(goodsInfo.getDesc());
                                goodsInfo.setShopcate_id(goodsInfo.getCate_id());
                                if (goodsInfo.getGoods_attr() != null) {
                                    for (SpecInfo specInfo : goodsInfo.getGoods_attr()) {
                                        specInfo.setMall_price(specInfo.getPrice());
                                    }
                                }
                            }
                        }
                    }
                    canLoadMore = true;
                    mGoodList.addAll(bean);
                    if (list != null && list.size() != 0) {
                        updateShopCartGoods(list);
                    } else {
                        mGoodAdapter.notifyDataSetChanged();
                    }
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

    private void showGoodsDetail(GoodsInfo2 info2) {
        if (isVM) {  //菜市场
            View inflate = getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(inflate);
            final AlertDialog dialog = builder.create();
            ViewPager vpPhoto = inflate.findViewById(R.id.view_pager);
            final TextView tvCount = inflate.findViewById(R.id.tv_count);
            final ArrayList<String> lstPhoto = new ArrayList<>();
            if (info2.getGoods_photo() == null || info2.getGoods_photo().size() == 0) {
                lstPhoto.add(info2.getPhoto());
            } else {
                lstPhoto.addAll(info2.getGoods_photo());
            }
            tvCount.setText("1/" + lstPhoto.size());
            vpPhoto.setAdapter(new PagerAdapter() {

                @Override
                public int getCount() {
                    return lstPhoto.size();
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    String path = lstPhoto.get(position);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(getContext()).load(path).error(R.drawable.loading_logo).into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }
            });
            vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tvCount.setText(position + 1 + "/" + lstPhoto.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            TextView tvName = inflate.findViewById(R.id.tv_name);
            TextView tvSoldNum = inflate.findViewById(R.id.tv_sold_num);
            TextView tvMemberPrice = inflate.findViewById(R.id.tv_member_price);
            TextView tvPrice = inflate.findViewById(R.id.tv_price);
//            AddWidget addWidget = inflate.findViewById(R.id.addwidget);
            tvName.setText(info2.getProduct_name());
            if (LoginUtil.isLogin() && LoginUtil.getUserInfo().getIs_salesman() == 1) {
                tvSoldNum.setVisibility(View.VISIBLE);
                tvSoldNum.setText(String.format("销量: %d份", info2.getSold_num()));
            } else {
                tvSoldNum.setVisibility(GONE);
            }
            if (info2.getIs_shop_member() == 1) {
                tvMemberPrice.setText(String.format("%.2f", info2.getShop_member_price() / 100.00));
            } else {
                tvMemberPrice.setVisibility(GONE);
            }
            tvPrice.setText(String.format("¥%.2f", info2.getPrice() / 100.00));
//            if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
//                addWidget.setVisibility(GONE);
//            } else {
//                addWidget.setVisibility(View.VISIBLE);
//                addWidget.setData(((AddWidget) mGoodAdapter.getViewByPosition(position, R.id.addwidget)).getOnAddClick(), goodsInfo);
//            }
            dialog.show();
        } else {
            Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
            intent.putExtra("goodsId", info2.getGoods_id());
            intent.putExtra("shopId", mShopId);
            intent.putExtra("goodsInfo", info2);
            intent.putExtra("bookId", "");
            intent.putExtra("isShopMemberUser", mShopInfo.getIs_shop_member_user() == 1);
            startActivityForResult(intent, StoreDetailActivity.REQUEST_CODE_GOODS_DETAIL);
        }
    }

    private void freeGain(int position) {
        GoodsInfo2 goodsInfo = mGoodAdapter.getItem(position);
        LoadingDialog.showDialogForLoading(getActivity(), "领取中...", false);
        String goodsJson = createGoodsJson(goodsInfo);
        new MyHttp().doPost(isVM ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), goodsJson)
                        : Api.getDefault().createOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), goodsJson, null),
                new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        String orderId = result.getJSONObject("data").getString("order_id");
                        Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                        intent.putExtra("isPhysical", isVM);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int code) {
                    }
                });
    }

    private String createGoodsJson(GoodsInfo2 data) {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        if (isVM) {
            orderBean.setProduct_id(data.getGoods_id());
        } else {
            orderBean.setGoods_id(data.getGoods_id());
        }
        orderBean.setActivity_type(3);
        orderBean.setNum(1);
        lstGoods.add(orderBean);
        hashMap.put(String.valueOf(mShopId), lstGoods);
        return new Gson().toJson(hashMap);
    }

    private List<GoodsInfo2> list = new ArrayList<>();

    public void updateShopCartGoods(List<GoodsInfo2> lstShopCartGoods) {
        this.list = lstShopCartGoods;
        for (GoodsInfo2 lstShopCartGood : lstShopCartGoods) {
            //避免单规格单属性的情况下由于lstGoods中商品与lstShopCartGoods中的对应商品是同一个对象，所以清除数量时导致lstShopCartGoods中对应的对象的数量也清除
            lstShopCartGood.getCacheHmBuyNum().putAll(lstShopCartGood.getHmBuyNum());
        }
        for (GoodsInfo2 goodsInfo : mGoodList) {
            goodsInfo.getHmBuyNum().clear();
            for (GoodsInfo2 shopCartGoods : lstShopCartGoods) {
                if (!TextUtils.isEmpty(goodsInfo.getGoods_id()) && goodsInfo.getGoods_id().equals(shopCartGoods.getGoods_id())) {
                    HashMap<String, Integer> hmBuyNum = goodsInfo.getHmBuyNum();
                    hmBuyNum.putAll(shopCartGoods.getCacheHmBuyNum());
                }
            }
        }
        mGoodAdapter.notifyDataSetChanged();
    }
}

package com.wbx.mall.widget;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.activity.GoodsDetailActivity;
import com.wbx.mall.activity.StoreDetailActivity;
import com.wbx.mall.activity.SubmitOrderActivity;
import com.wbx.mall.adapter.GoodsAdapter;
import com.wbx.mall.adapter.GoodsTypeAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.CateInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.SPUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopGoodsContainer extends LinearLayout {
    RecyclerView recyclerViewType;
    RecyclerView recyclerViewGoods;
    FrameLayout frameLayout;
    //    ImageView img_fenl;
    public GoodsTypeAdapter typeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<CateInfo> lstMenu;
    private List<GoodsInfo2> lstGoods;
    private Context mContext;
    public GoodsAdapter goodsAdapter;
    private WeakReference<StoreDetailActivity> mActivityManager;
    private ShopInfo2 shopInfo;

    public ShopGoodsContainer(Context context) {
        super(context);
    }

    public ShopGoodsContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate(mContext, R.layout.view_listcontainer, this);
        frameLayout = findViewById(R.id.shop_goods_fl);
        recyclerViewType = findViewById(R.id.recycler_view_type);
        recyclerViewGoods = findViewById(R.id.recycler_view_goods);
//        img_fenl=findViewById(R.id.img_fenl);
        recyclerViewType.setLayoutManager(new LinearLayoutManager(mContext));
        typeAdapter = new GoodsTypeAdapter(lstMenu);
        View view = new View(mContext);

        view.setMinimumHeight(DisplayUtil.dip2px(50));
        typeAdapter.addFooterView(view);
        typeAdapter.bindToRecyclerView(recyclerViewType);
        recyclerViewType.addItemDecoration(new SimpleDividerDecoration(mContext));
        ((DefaultItemAnimator) recyclerViewType.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerViewType.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                typeAdapter.setItemCheck(position);
                CateInfo item = typeAdapter.getItem(position);
                if (position == 0) {
                    goodsAdapter.setNewData(lstGoods);
                } else if ("1".equals(item.getCate_id())) {
                    //得到属于秒杀的z
                    List<GoodsInfo2> secKillGoodsInfo = new ArrayList<>();
                    for (GoodsInfo2 goodsInfo : lstGoods) {
                        if (goodsInfo.getIs_seckill() == 1) {
                            secKillGoodsInfo.add(goodsInfo);
                        }
                    }
                    goodsAdapter.setNewData(secKillGoodsInfo);
                } else if ("2".equals(item.getCate_id())) {
                    //得到属于促销的
                    List<GoodsInfo2> proGoodsInfo = new ArrayList<>();
                    for (GoodsInfo2 goodsInfo : lstGoods) {
                        if (goodsInfo.getSales_promotion_is() == 1) {
                            proGoodsInfo.add(goodsInfo);
                        }
                    }
                    goodsAdapter.setNewData(proGoodsInfo);
                } else {
                    List<GoodsInfo2> showGoodsInfo = new ArrayList<>();
                    for (GoodsInfo2 goodsInfo : lstGoods) {
                        if (lstMenu.get(position).getCate_id().equals(goodsInfo.getCate_id())) {
                            showGoodsInfo.add(goodsInfo);
                        }
                    }
                    goodsAdapter.setNewData(showGoodsInfo);
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewGoods.setLayoutManager(linearLayoutManager);
        ((DefaultItemAnimator) recyclerViewGoods.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void setData(StoreDetailActivity activity, ShopInfo2 shopInfo) {
        mActivityManager = new WeakReference<>(activity);
        this.shopInfo = shopInfo;
    }

    public void setGoodsAdapter(boolean isFoodStreet, boolean isShopMemberUser, AddWidget.OnAddClick onAddClick) {
        if (isFoodStreet) {
            goodsAdapter = new GoodsAdapter( lstGoods, onAddClick, isShopMemberUser);
        } else {
            goodsAdapter = new GoodsAdapter( lstGoods, onAddClick, isShopMemberUser);
        }
        View view = new View(mContext);
        view.setMinimumHeight(DisplayUtil.dip2px(50));
        goodsAdapter.addFooterView(view);
        goodsAdapter.bindToRecyclerView(recyclerViewGoods);
        goodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.goods_pic_im:
                        showGoodsDetail(position);
                        break;
                    case R.id.tv_free_gain:
                        freeGain(position);
                        break;
                }
            }
        });
    }

    private void freeGain(int position) {
        LoadingDialog.showDialogForLoading(mActivityManager.get(), "领取中...", false);
        String goodsJson = createGoodsJson(goodsAdapter.getItem(position));
        new MyHttp().doPost(shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), goodsJson) : Api.getDefault().createOrder(SPUtils.getSharedStringData(getContext(), AppConfig.LOGIN_TOKEN), goodsJson, null), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                intent.putExtra("isPhysical", shopInfo.getGrade_id() != AppConfig.StoreType.VEGETABLE_MARKET);
                intent.putExtra("orderId", orderId);
                mActivityManager.get().startActivity(intent);
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
        if (shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET) {
            orderBean.setProduct_id(data.getGoods_id());
        } else {
            orderBean.setGoods_id(data.getGoods_id());
        }
        orderBean.setActivity_type(3);
        orderBean.setNum(1);
        lstGoods.add(orderBean);
        hashMap.put(String.valueOf(shopInfo.getShop_id()), lstGoods);
        return new Gson().toJson(hashMap);
    }

    private void showGoodsDetail(final int position) {
        GoodsInfo2 goodsInfo = goodsAdapter.getItem(position);
        if (shopInfo.getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET) {
            //菜市场
            View inflate = mActivityManager.get().getLayoutInflater().inflate(R.layout.dialog_product_detail, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivityManager.get());
            builder.setView(inflate);
            final AlertDialog dialog = builder.create();
            ViewPager vpPhoto = inflate.findViewById(R.id.view_pager);
            final TextView tvCount = inflate.findViewById(R.id.tv_count);
            final ArrayList<String> lstPhoto = new ArrayList<>();
            if (goodsInfo.getGoods_photo() == null || goodsInfo.getGoods_photo().size() == 0) {
                lstPhoto.add(goodsInfo.getPhoto());
            } else {
                lstPhoto.addAll(goodsInfo.getGoods_photo());
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
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    String path = lstPhoto.get(position);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(mContext).load(path).error(R.drawable.loading_logo).into(imageView);
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
            AddWidget addWidget = inflate.findViewById(R.id.addwidget);
            tvName.setText(goodsInfo.getProduct_name());
            if (LoginUtil.isLogin() && LoginUtil.getUserInfo().getIs_salesman() == 1) {
                tvSoldNum.setVisibility(View.VISIBLE);
                tvSoldNum.setText(String.format("销量: %d份", goodsInfo.getSold_num()));
            } else {
                tvSoldNum.setVisibility(View.GONE);
            }
            if (goodsInfo.getIs_shop_member() == 1) {
                tvMemberPrice.setText(String.format("%.2f", goodsInfo.getShop_member_price() / 100.00));
            } else {
                tvMemberPrice.setVisibility(GONE);
            }
            tvPrice.setText(String.format("¥%.2f", goodsInfo.getPrice() / 100.00));
            if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
                addWidget.setVisibility(View.GONE);
            } else {
                addWidget.setVisibility(View.VISIBLE);
                addWidget.setData(((AddWidget) goodsAdapter.getViewByPosition(position, R.id.addwidget)).getOnAddClick(), goodsInfo);
            }
            dialog.show();
        } else {
            Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
            intent.putExtra("goodsId", goodsInfo.getGoods_id());
            intent.putExtra("shopId", shopInfo.getShop_id());
            intent.putExtra("goodsInfo", goodsInfo);
            intent.putExtra("bookId", mActivityManager.get().bookSeatId);
            intent.putExtra("isShopMemberUser", shopInfo.getIs_shop_member_user() == 1);
            mActivityManager.get().startActivityForResult(intent, StoreDetailActivity.REQUEST_CODE_GOODS_DETAIL);
        }
    }

    public void setMenu(List<CateInfo> lstMenu) {
        this.lstMenu = lstMenu;
    }

    public void setGoods(List<GoodsInfo2> lstGoods) {
        this.lstGoods = lstGoods;
    }

}

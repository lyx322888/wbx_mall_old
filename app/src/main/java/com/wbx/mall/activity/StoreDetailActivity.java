package com.wbx.mall.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.adapter.NatureAdapter;
import com.wbx.mall.adapter.ShopCarAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CouponInfo;
import com.wbx.mall.bean.FullInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.RandomRedPacketBean;
import com.wbx.mall.bean.ShopCart;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.bean.SpecInfo;
import com.wbx.mall.bean.StoreDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.RedPacketFragment;
import com.wbx.mall.dialog.ShopCouponDialog;
import com.wbx.mall.dialog.ShopDiscountDialog;
import com.wbx.mall.fragment.Goods2Fragment;
import com.wbx.mall.fragment.MerchantInfoFragment;
import com.wbx.mall.fragment.ShopCommentFragment;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.widget.AddWidget;
import com.wbx.mall.widget.DragImageView;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.ShopCarView;
import com.wbx.mall.widget.ShopInfoContainer;
import com.wbx.mall.widget.flowLayout.BaseTagAdapter;
import com.wbx.mall.widget.flowLayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.View.GONE;


public class StoreDetailActivity extends BaseActivity implements AddWidget.OnAddClick, ShopCouponDialog.DialogListener {
    public static final String IS_VEGETABLE_MARKET = "IS_VEGETABLE_MARKET";
    public static final String STORE_ID = "STORE_ID";
    public static final String GOODS_ID = "GOODS_ID";
    public static final int REQUEST_CODE_BOOK = 1008;//预定
    public static final int REQUEST_CODE_GOODS_DETAIL = 1009;//商品详情
    @Bind(R.id.btn_ensure_order)
    TextView btnEnsureOrder;
    private String[] title = {"热卖", "评论", "商家"};
    @Bind(R.id.shop_info_container)
    ShopInfoContainer shopInfoContainer;
    @Bind(R.id.shop_car_view)
    ShopCarView shopCarView;
    @Bind(R.id.ll_container_coupon)
    LinearLayout llContainerCoupoon;
    @Bind(R.id.tv_member_condition)
    TextView tvMemberCondition;
    @Bind(R.id.tv_discount)
    TextView tvDisCount;
    @Bind(R.id.tv_discount_more)
    TextView tvDisCountMore;
    @Bind(R.id.root_view)
    CoordinatorLayout rootView;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.iv_book)
    DragImageView ivBook;
    @Bind(R.id.blackview)
    View blackView;
    @Bind(R.id.new_shop_recycler)
    RecyclerView newRecycler;
    @Bind(R.id.goods_free)
    RelativeLayout mRelative;
    public String mStoreId;
    private boolean isVM;//是否菜市场
    public String bookSeatId = "";
    private BottomSheetBehavior shopCarDetailBehavior;
    public ShopCarAdapter shopCarAdapter;
    private ArrayList<GoodsInfo2> lstSelectedGoods = new ArrayList<>();
    private SpecInfo selectSpec;//选中的规格Id
    private LinkedHashMap<String, GoodsInfo2.Nature_attr> hmSelectNature;//选中的规格属性
    private int shopCartTotalNum;
    public StoreDetailBean storeDetailBean;
    private ShopCouponDialog couponDialog;//领取优惠券弹窗
    private ShopDiscountDialog shopDiscountDialog;//优惠活动弹窗
    private String mStrDiscount;//优惠活动文字

    public static void actionStart(Context context, boolean isVegetable, String storeId) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra(IS_VEGETABLE_MARKET, isVegetable);
        intent.putExtra(STORE_ID, storeId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initBehavior();
    }

    private void initBehavior() {
        shopCarDetailBehavior = BottomSheetBehavior.from(findViewById(R.id.shop_car_detail_container));
        shopCarDetailBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    blackView.setVisibility(GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                blackView.setVisibility(View.VISIBLE);
                blackView.setAlpha(slideOffset);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newRecycler.setLayoutManager(manager);
    }

    @Override
    public void fillData() {
        mStoreId = getIntent().getStringExtra(STORE_ID);
        isVM = getIntent().getBooleanExtra(IS_VEGETABLE_MARKET, false);
        LoadingDialog.showDialogForLoading(this);
        new MyHttp().doPost(isVM ? Api.getDefault().getVegetableShopInfo(mStoreId, SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)) :
                Api.getDefault().getShopInfo(mStoreId, SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
//                storeDetailBean = result.getObject("data", StoreDetailBean.class);
                convertToSameModel(result);
                getShopCarInfo();
                updateUI(result);
                getRandomRedPacket();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private void convertToSameModel(JSONObject result) {
//        if (storeDetailBean.getSeckill_goods().size() == 0) {
//            mRelative.setVisibility(View.GONE);
//        } else {
//            mRelative.setVisibility(View.VISIBLE);
//            GoodsFreeAdapter adapter = new GoodsFreeAdapter(R.layout.layout_goodsfree, storeDetailBean.getSeckill_goods());
//            newRecycler.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
        storeDetailBean = result.getObject("data", StoreDetailBean.class);
        if (isVM) {//菜市场数据模型转换为实体店
            storeDetailBean.setDetail(result.getJSONObject("data").getObject("shop_detail", ShopInfo2.class));
//            storeDetailBean.setCate(JSONArray.parseArray(result.getJSONObject("data").getString("menu"), CateInfo.class));
        }
    }

    private void getRandomRedPacket() {
        if (LoginUtil.isLogin()) {
            new MyHttp().doPost(Api.getDefault().getRandomRedPacket(LoginUtil.getLoginToken(), storeDetailBean.getDetail().getShop_id() + ""), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    RandomRedPacketBean data = result.getObject("data", RandomRedPacketBean.class);
                    if (data != null) {
                        RedPacketFragment redPacketFragment = RedPacketFragment.newInstance(data, storeDetailBean.getDetail().getShop_id());
                        redPacketFragment.show(getSupportFragmentManager(), "");
                        redPacketFragment.setOnReceiveSuccessListener(new RedPacketFragment.OnReceiveSuccessListener() {
                            @Override
                            public void onSuccess() {
                                shopInfoContainer.ivCollect.setImageResource(R.drawable.already);
                            }
                        });
                    }
                }

                @Override
                public void onError(int code) {

                }
            });
        }
    }

    private void updateUI(JSONObject result) {
        shopInfoContainer.updateUI(this, storeDetailBean.getDetail());
        if (storeDetailBean.getDetail().getGrade_id() == AppConfig.StoreType.FOOD_STREET && storeDetailBean.getDetail().getIs_subscribe() == 1) {
            ivBook.setVisibility(GONE);
        }
        shopCarView.setShopInfo(storeDetailBean.getDetail());
        if (storeDetailBean.getDetail().getConsumption_money() == 0) {
            ((View) tvMemberCondition.getParent()).setVisibility(GONE);
        } else {
            ((View) tvMemberCondition.getParent()).setVisibility(View.VISIBLE);
            tvMemberCondition.setText(String.format("消费满%.2f元", storeDetailBean.getDetail().getConsumption_money() / 100.00));
        }
        addCoupon();
        addFullReduce();
        initViewPager();
    }

    //获取购物车内容
    private void getShopCarInfo() {
        initShopCartDetail();
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            return;
        }
        new MyHttp().doPost(Api.getDefault().getCartInfo(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), storeDetailBean.getDetail().getShop_id()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                shopCartTotalNum = 0;
                lstSelectedGoods.clear();
                if (!TextUtils.isEmpty(result.getString("data"))) {
                    List<ShopCart> dataList = JSONArray.parseArray(result.getString("data"), ShopCart.class);
                    List<GoodsInfo2> goods_cart = dataList.get(0).getGoods_cart();
                    for (GoodsInfo2 goodsInfo : goods_cart) {
                        if (!"0".equals(goodsInfo.getAttr_id())) {
                            goodsInfo.setIs_attr(1);
                        }
                        goodsInfo.setShopcate_id(goodsInfo.getCate_id());
                        if (goodsInfo.getIs_shop_member() == 1) {
                            goodsInfo.setShop_member_price(goodsInfo.getPrice());
                            goodsInfo.setPrice(goodsInfo.getOriginal_price());
                        }
                        StringBuilder sbNature = new StringBuilder();
                        if (goodsInfo.getSelected_nature_ids() != null && goodsInfo.getSelected_nature_ids().size() > 0) {
                            for (String s : goodsInfo.getSelected_nature_ids()) {
                                sbNature.append(s);
                                sbNature.append("+");
                            }
                        }
                        if (sbNature.toString().length() > 0) {
                            sbNature.deleteCharAt(sbNature.toString().length() - 1);
                        }
                        goodsInfo.getHmBuyNum().put(goodsInfo.getGoods_id() + "," + ("0".equals(goodsInfo.getAttr_id()) ? "" : goodsInfo.getAttr_id()) + "," + sbNature.toString(), goodsInfo.getNum());
                    }
                    lstSelectedGoods.addAll(goods_cart);
                    updateShopCart(null);
                }
            }

            @Override
            public void onError(int code) {
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private List<Fragment> fragmentList;

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(Goods2Fragment.newInstance(mStoreId, isVM, storeDetailBean.getDetail()));
        fragmentList.add(ShopCommentFragment.newInstance(mStoreId));
        fragmentList.add(MerchantInfoFragment.newInstance(storeDetailBean.getDetail()));
        ShopDetailPagerAdapter pagerAdapter = new ShopDetailPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    shopCarView.setVisibility(View.VISIBLE);
                } else {
                    shopCarView.setVisibility(GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取满减字段
     */
    private void addFullReduce() {
        List<FullInfo> fullInfoList = storeDetailBean.getFull_money_reduce();
        if (fullInfoList == null || fullInfoList.size() == 0) {
            ((View) tvDisCount.getParent()).setVisibility(GONE);
            return;
        }
        ((View) tvDisCount.getParent()).setVisibility(View.VISIBLE);
        StringBuilder sbFullReduce = new StringBuilder();
        for (int i = 0; i < fullInfoList.size(); i++) {
            sbFullReduce.append(String.format("满%.2f元减%.2f元", fullInfoList.get(i).getFull_money() / 100.00, fullInfoList.get(i).getReduce_money() / 100.00));
            if (i != fullInfoList.size() - 1) {
                sbFullReduce.append(",");
            }
        }
        mStrDiscount = sbFullReduce.toString();
        tvDisCount.setText(mStrDiscount);
        tvDisCountMore.setText(fullInfoList.size() + "个优惠");
    }

    private void addCoupon() {
        List<CouponInfo> list = storeDetailBean.getCoupon().getList();
        if (list == null || list.size() == 0) {
            ((View) llContainerCoupoon.getParent()).setVisibility(GONE);
            return;
        }
        updateCoupon();
    }

    private void updateCoupon() {
        llContainerCoupoon.removeAllViews();
        for (CouponInfo couponInfo : storeDetailBean.getCoupon().getList()) {
            View layout = LayoutInflater.from(this).inflate(R.layout.item_shop_coupon, null);
            TextView tvCoupon = layout.findViewById(R.id.tv_coupon);
            if (couponInfo.getIs_receive() == 1) {
                tvCoupon.setText(String.format("已领¥%.2f", couponInfo.getMoney() / 100.00));
                tvCoupon.setTextColor(Color.parseColor("#BDBCBC"));
                tvCoupon.setBackgroundResource(R.drawable.bg_coupon_received);
            } else {
                tvCoupon.setText(String.format("领取¥%.2f", couponInfo.getMoney() / 100.00));
                tvCoupon.setTextColor(Color.parseColor("#FF5757"));
                tvCoupon.setBackgroundResource(R.drawable.bg_coupon);
            }
            llContainerCoupoon.addView(layout);
        }
    }

    private void initShopCartDetail() {
        shopCarView.setBehavior(shopCarDetailBehavior);
        shopCarView.setOnGoodsClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginUtil.isLogin()) {
                    LoginUtil.login();
                    return;
                }
                if (shopCarAdapter == null || shopCarAdapter.getItemCount() == 0) {
                    return;
                }
                shopCarAdapter.notifyDataSetChanged();
                if (shopCarDetailBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    shopCarDetailBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    shopCarDetailBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        RecyclerView rvShopCartDetail = findViewById(R.id.recycler_view_shop_car_detail);
        rvShopCartDetail.setLayoutManager(new LinearLayoutManager(mContext));
        ((DefaultItemAnimator) rvShopCartDetail.getItemAnimator()).setSupportsChangeAnimations(false);
        shopCarAdapter = new ShopCarAdapter(lstSelectedGoods);
        shopCarAdapter.setIsShopMemberUser(storeDetailBean.getDetail().getIs_shop_member_user() == 1);
        shopCarAdapter.bindToRecyclerView(rvShopCartDetail);
        shopCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_reduce_goods) {
                    GoodsInfo2 goodsInfo = lstSelectedGoods.get(position);
                    if (goodsInfo.getHmBuyNum().keySet().iterator().hasNext()) {
                        String key = goodsInfo.getHmBuyNum().keySet().iterator().next();
                        Integer buyNum = goodsInfo.getHmBuyNum().get(key);
                        goodsInfo.getHmBuyNum().put(key, buyNum - 1);
                        addGoodsToServer(goodsInfo);
                        updateShopCart(goodsInfo);
                        if ((buyNum - 1) == 0) {
                            shopCarAdapter.notifyItemRemoved(position);
                        } else {
                            shopCarAdapter.notifyItemChanged(position);
                        }
                    }
                } else if (view.getId() == R.id.iv_add_goods) {
                    GoodsInfo2 goodsInfo = lstSelectedGoods.get(position);
                    int currentAttrBuyNum = 0;
                    for (GoodsInfo2 selectGoods : lstSelectedGoods) {
                        if (goodsInfo.getGoods_id().equals(selectGoods.getGoods_id()) && ((TextUtils.isEmpty(goodsInfo.getAttr_id()) && TextUtils.isEmpty(selectGoods.getAttr_id())) || (!TextUtils.isEmpty(goodsInfo.getAttr_id()) && goodsInfo.getAttr_id().equals(selectGoods.getAttr_id())))) {
                            //同一个商品并且同一个规格
                            currentAttrBuyNum += selectGoods.getHmBuyNum().get(selectGoods.getHmBuyNum().keySet().iterator().next());
                        }
                    }
                    if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                        //秒杀商品
                        if (currentAttrBuyNum + 1 > goodsInfo.getLimitations_num()) {
                            Toast.makeText(mContext, "不能超过限购数量", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (currentAttrBuyNum + 1 > goodsInfo.getInventory_num()) {
                            Toast.makeText(mContext, "库存不足", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String key = goodsInfo.getHmBuyNum().keySet().iterator().next();
                    Integer buyNum = goodsInfo.getHmBuyNum().get(key);
                    goodsInfo.getHmBuyNum().put(key, buyNum + 1);
                    addGoodsToServer(goodsInfo);
                    updateShopCart(goodsInfo);
                    shopCarAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.iv_book, R.id.iv_intelligent_pay, R.id.ll_container_coupon, R.id.tv_discount_more, R.id.tv_clear_shop_car, R.id.btn_ensure_order, R.id.blackview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_book:
                book();
                break;
            case R.id.iv_intelligent_pay:
                intelligentPay();
                break;
            case R.id.ll_container_coupon:
                if (couponDialog == null) {
                    couponDialog = new ShopCouponDialog(StoreDetailActivity.this, storeDetailBean.getCoupon().getList(), this);
                }
                couponDialog.show();
                break;
            case R.id.tv_discount_more:
                if (shopDiscountDialog == null) {
                    shopDiscountDialog = new ShopDiscountDialog(StoreDetailActivity.this, mStrDiscount);
                }
                shopDiscountDialog.show();
                break;
            case R.id.tv_clear_shop_car:
                clearShopCart();
                break;
            case R.id.btn_ensure_order:
                ensureOrder();
                break;
            case R.id.blackview:
                closePopWindow();
                break;
        }
    }

    private void ensureOrder() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
            return;
        }
        if (lstSelectedGoods.size() == 0) {
            Toast.makeText(mContext, "请选择商品", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.showDialogForLoading(this, "下单中...", false);
        new MyHttp().doPost(isVM
                        ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), createGoodsJson())
                        : Api.getDefault().createOrder(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), createGoodsJson(), bookSeatId),
                new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        String orderId = result.getJSONObject("data").getString("order_id");
                        Intent intent = new Intent(mActivity, SubmitOrderActivity.class);
                        intent.putExtra("isPhysical", isVM);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("isBook", !TextUtils.isEmpty(bookSeatId));
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int code) {
                    }
                });
    }

    private String createGoodsJson() {
        HashMap<String, List<OrderBean>> hashMap = new HashMap<>();
        ArrayList<OrderBean> lstGoods = new ArrayList<>();
        for (GoodsInfo2 lstSelectedGood : lstSelectedGoods) {
            OrderBean orderBean = new OrderBean();
            if (storeDetailBean.getDetail().getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET) {
                orderBean.setProduct_id(lstSelectedGood.getGoods_id());
            } else {
                orderBean.setGoods_id(lstSelectedGood.getGoods_id());
            }
            orderBean.setAttr_id(lstSelectedGood.getAttr_id());
            Iterator<String> iterator = lstSelectedGood.getHmBuyNum().keySet().iterator();
            if (iterator.hasNext()) {
                String key = iterator.next();
                orderBean.setNum(lstSelectedGood.getHmBuyNum().get(key));
                ArrayList<String> lstNature = new ArrayList<>();
                String[] split = key.split(",");
                if (split.length >= 3) {
                    String[] split1 = split[2].split("\\+");
                    if (split1.length > 0) {
                        for (String s : split1) {
                            lstNature.add(s);
                        }
                    }
                    orderBean.setNature(lstNature);
                }
                lstGoods.add(orderBean);
            }
        }
        hashMap.put(String.valueOf(storeDetailBean.getDetail().getShop_id()), lstGoods);
        return new Gson().toJson(hashMap);
    }

    private void clearShopCart() {
        new com.wbx.mall.widget.iosdialog.AlertDialog(mContext).builder()
                .setTitle("提示")
                .setMsg("删除本店购物车所有商品？")
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyHttp().doPost(Api.getDefault().deleteShopCartInfo(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN), storeDetailBean.getDetail().getShop_id()), new HttpListener() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                lstSelectedGoods.clear();
                                updateShopCart(null);
                                shopCarAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(int code) {

                            }
                        });
                    }
                }).show();
    }

    private void intelligentPay() {
        if (storeDetailBean == null || storeDetailBean.getDetail() == null) {
            return;
        }
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
        } else {
            IntelligentPayActivity.actionStart(this, String.valueOf(storeDetailBean.getDetail().getShop_id()));
        }
    }

    private void book() {
        if (storeDetailBean == null || storeDetailBean.getDetail() == null) {
            return;
        }
        Intent intentBookSeat = new Intent(mContext, BookSeatActivity.class);
        intentBookSeat.putExtra("shop_id", storeDetailBean.getDetail().getShop_id());
        startActivityForResult(intentBookSeat, REQUEST_CODE_BOOK);
    }

    @Override
    public void onAddClick(View view, GoodsInfo2 goodsInfo) {
        addTvAnim(view, shopCarView.carLoc, this, rootView);
        addGoodsToServer(goodsInfo);
        updateShopCart(goodsInfo);
    }

    @Override
    public void onSubClick(GoodsInfo2 goodsInfo) {
        addGoodsToServer(goodsInfo);
        updateShopCart(goodsInfo);
    }

    @Override
    public void onClickSpecs(final GoodsInfo2 goodsInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View inflate = getLayoutInflater().inflate(R.layout.dialog_product_spec, null);
        builder.setView(inflate);
        TextView tvName = inflate.findViewById(R.id.product_name_tv);
        RecyclerView rvNature = inflate.findViewById(R.id.recycler_view);
        tvName.setText(goodsInfo.getTitle());
        rvNature.setLayoutManager(new LinearLayoutManager(mContext));
        NatureAdapter natureAdapter = new NatureAdapter(R.layout.item_nature, goodsInfo.getNature());
        natureAdapter.setOnNatureChangeListener(new NatureAdapter.OnNatureChangeListener() {
            @Override
            public void onNatureChange(LinkedHashMap<String, GoodsInfo2.Nature_attr> selectNature) {
                hmSelectNature = selectNature;
                changeSpec(goodsInfo, inflate);
            }
        });
        rvNature.setAdapter(natureAdapter);
        hmSelectNature = new LinkedHashMap<>();
        if (goodsInfo.getIs_attr() == 1) {
            View specHead = getLayoutInflater().inflate(R.layout.item_nature, null);
            TagFlowLayout tagFlowLayout = specHead.findViewById(R.id.laybelLayout);
            natureAdapter.addHeaderView(specHead);
            BaseTagAdapter<SpecInfo> baseTagAdapter = new BaseTagAdapter<SpecInfo>(mContext, goodsInfo.getGoods_attr()) {
                @Override
                public void setText(TextView textView, int position, SpecInfo specInfo) {
                    if (specInfo.getIs_seckill() == 1) {
                        textView.setText(Html.fromHtml("<font color=#ff0000>(秒)</font>" + specInfo.getAttr_name()));
                    } else {
                        textView.setText(specInfo.getAttr_name());
                    }
                }
            };
            tagFlowLayout.setAdapter(baseTagAdapter);
            tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (selectPosSet.size() == 0) {
                        return;
                    }
                    SpecInfo specInfo = goodsInfo.getGoods_attr().get(selectPosSet.iterator().next());
                    selectSpec = specInfo;
                    changeSpec(goodsInfo, inflate);
                }
            });
        }
        //默认选中第一个
        if (goodsInfo.getIs_attr() == 1 && (goodsInfo.getGoods_attr() != null && goodsInfo.getGoods_attr().size() > 0)) {
            selectSpec = goodsInfo.getGoods_attr().get(0);
        }
        if (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0) {
            for (GoodsInfo2.Nature nature : goodsInfo.getNature()) {
                hmSelectNature.put(nature.getItem_id(), nature.getNature_arr().get(0));
            }
        }
        changeSpec(goodsInfo, inflate);
        final AlertDialog specDialog = builder.create();
        if (!specDialog.isShowing()) {
            specDialog.show();
        }
        inflate.findViewById(R.id.dialog_fragment_goods_dismiss_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.spec_add_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentSpecBuyNum = 0;
                if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey(goodsInfo))) {
                    currentSpecBuyNum = goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey(goodsInfo));
                }
                GoodsInfo2 goods = new GoodsInfo2();
                if (goodsInfo.getIs_attr() == 1) {
                    int currentAttrTotalBuyNum = 0;
                    Iterator<String> iterator = goodsInfo.getHmBuyNum().keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        String[] split = key.split(",");
                        if (split.length >= 2 && selectSpec.getAttr_id().equals(split[1])) {//当前规格（非规格属性）各种组合的总数量
                            currentAttrTotalBuyNum += goodsInfo.getHmBuyNum().get(key);
                        }
                    }
                    if (selectSpec.getIs_seckill() == 1 && selectSpec.getLimitations_num() != 0) {
                        //秒杀商品
                        if (currentAttrTotalBuyNum + 1 > selectSpec.getLimitations_num()) {
                            showShortToast("不能超过当前规格限购数量");
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (currentAttrTotalBuyNum + 1 > selectSpec.getInventory_num()) {
                            showShortToast("当前规格库存不足");
                            return;
                        }
                    }
                    int money = 0;
                    if (selectSpec.getIs_seckill() == 1) {
                        money = selectSpec.getSeckill_price();
                    } else if (selectSpec.getSales_promotion_is() == 1) {
                        money = selectSpec.getSales_promotion_price();
                    } else if (selectSpec.getMall_price() != 0) {
                        money = selectSpec.getMall_price();
                    }
                    goods.setPrice(money);
                    goods.setAttr_id(selectSpec.getAttr_id());
                    goods.setCasing_price(selectSpec.getCasing_price());
                    goods.setIs_attr(1);
                    goods.setAttr_name(selectSpec.getAttr_name());
                    if (goodsInfo.getIs_seckill() == 1) {
                        goods.setLimitations_num(selectSpec.getLimitations_num());
                        goods.setIs_seckill(1);
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        goods.setIs_use_num(1);
                        goods.setInventory_num(selectSpec.getInventory_num());
                    }
                    goods.setIs_shop_member(selectSpec.getIs_shop_member());
                    goods.setShop_member_price(selectSpec.getShop_member_price());
                } else {
                    int currentGoodsTotalBuyNum = 0;
                    Iterator<String> iterator = goodsInfo.getHmBuyNum().keySet().iterator();
                    while (iterator.hasNext()) {
                        currentGoodsTotalBuyNum += goodsInfo.getHmBuyNum().get(iterator.next());
                    }
                    if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                        //秒杀商品
                        if (currentGoodsTotalBuyNum + 1 > goodsInfo.getLimitations_num()) {
                            showShortToast("不能超过限购数量");
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (currentGoodsTotalBuyNum + 1 > goodsInfo.getInventory_num()) {
                            showShortToast("库存不足");
                            return;
                        }
                    }
                    goods.setPrice(goodsInfo.getPrice());
                    goods.setCasing_price(goodsInfo.getCasing_price());
                    goods.setLimitations_num(goodsInfo.getLimitations_num());
                    goods.setIs_seckill(goodsInfo.getIs_seckill());
                    goods.setIs_use_num(goodsInfo.getIs_use_num());
                    goods.setInventory_num(goodsInfo.getInventory_num());
                    goods.setIs_shop_member(goodsInfo.getIs_shop_member());
                    goods.setShop_member_price(goodsInfo.getShop_member_price());
                }
                goods.setGoods_id(goodsInfo.getGoods_id());
                goods.setPhoto(goodsInfo.getPhoto());
                goods.setTitle(goodsInfo.getTitle());
                goods.setShopcate_id(goodsInfo.getShopcate_id());
                goods.setIs_attr(goodsInfo.getIs_attr());
                goods.setNature(goodsInfo.getNature());
                StringBuilder sbNature = new StringBuilder();
                for (String key : hmSelectNature.keySet()) {
                    sbNature.append(hmSelectNature.get(key).getNature_name());
                    sbNature.append("+");
                }
                if (sbNature.length() > 0) {
                    sbNature.deleteCharAt(sbNature.toString().length() - 1);
                }
                goods.setNature_name(sbNature.toString());
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put(getGoodsCurrentSelectedSpecKey(goodsInfo), currentSpecBuyNum + 1);
                goods.setHmBuyNum(hashMap);
                goodsInfo.getHmBuyNum().putAll(hashMap);
                changeSpec(goodsInfo, inflate);
                addGoodsToServer(goods);
                updateShopCart(goods);
            }
        });
        inflate.findViewById(R.id.spec_sub_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentSpecBuyNum = 0;
                if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey(goodsInfo))) {
                    currentSpecBuyNum = goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey(goodsInfo));
                }
                if (currentSpecBuyNum == 0) {
                    showShortToast("购买数量不能小于0");
                    return;
                }
                GoodsInfo2 goods = new GoodsInfo2();
                if (goodsInfo.getIs_attr() == 1) {
                    int money = 0;
                    if (selectSpec.getIs_seckill() == 1) {
                        money = selectSpec.getSeckill_price();
                    } else if (selectSpec.getSales_promotion_is() == 1) {
                        money = selectSpec.getSales_promotion_price();
                    } else if (selectSpec.getMall_price() != 0) {
                        money = selectSpec.getMall_price();
                    }
                    goods.setPrice(money);
                    goods.setAttr_id(selectSpec.getAttr_id());
                    goods.setCasing_price(selectSpec.getCasing_price());
                    goods.setIs_attr(1);
                    goods.setAttr_name(selectSpec.getAttr_name());
                    if (goodsInfo.getIs_seckill() == 1) {
                        goods.setLimitations_num(selectSpec.getLimitations_num());
                        goods.setIs_seckill(1);
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        goods.setIs_use_num(1);
                        goods.setInventory_num(selectSpec.getInventory_num());
                    }
                    goods.setIs_shop_member(selectSpec.getIs_shop_member());
                    goods.setShop_member_price(selectSpec.getShop_member_price());
                } else {
                    goods.setCasing_price(goodsInfo.getCasing_price());
                    goods.setLimitations_num(goodsInfo.getLimitations_num());
                    goods.setIs_seckill(goodsInfo.getIs_seckill());
                    goods.setIs_use_num(goodsInfo.getIs_use_num());
                    goods.setInventory_num(goodsInfo.getInventory_num());
                    goods.setIs_shop_member(goodsInfo.getIs_shop_member());
                    goods.setShop_member_price(goodsInfo.getShop_member_price());
                }
                goods.setGoods_id(goodsInfo.getGoods_id());
                goods.setPhoto(goodsInfo.getPhoto());
                goods.setTitle(goodsInfo.getTitle());
                goods.setShopcate_id(goodsInfo.getShopcate_id());
                goods.setIs_attr(goodsInfo.getIs_attr());
                goods.setNature(goodsInfo.getNature());
                StringBuilder sbNature = new StringBuilder();
                for (String key : hmSelectNature.keySet()) {
                    sbNature.append(hmSelectNature.get(key).getNature_name());
                    sbNature.append("+");
                }
                if (sbNature.length() > 0) {
                    sbNature.deleteCharAt(sbNature.toString().length() - 1);
                }
                goods.setNature_name(sbNature.toString());
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put(getGoodsCurrentSelectedSpecKey(goodsInfo), currentSpecBuyNum - 1);
                goods.setHmBuyNum(hashMap);
                goodsInfo.getHmBuyNum().putAll(hashMap);
                changeSpec(goodsInfo, inflate);
                addGoodsToServer(goods);
                updateShopCart(goods);
            }
        });
        specDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                selectSpec = null;
                hmSelectNature = null;
            }
        });
    }

    private void changeSpec(GoodsInfo2 goodsInfo, View inflate) {
        TextView tvMemberPrice = inflate.findViewById(R.id.tv_member_price);
        TextView tvPrice = inflate.findViewById(R.id.price_tv);
        TextView tvSpec = inflate.findViewById(R.id.tv_spec);
        TextView tvBuyNum = inflate.findViewById(R.id.spec_buy_num_tv);
        if (goodsInfo.getIs_attr() == 1) {
            double money = 0;
            if (selectSpec.getIs_seckill() == 1) {
                money = selectSpec.getSeckill_price() / 100.00;
            } else if (selectSpec.getSales_promotion_is() == 1) {
                money = selectSpec.getSales_promotion_price() / 100.00;
            } else if (selectSpec.getMall_price() != 0) {
                money = selectSpec.getMall_price() / 100.00;
            }
            if (selectSpec.getIs_shop_member() == 1) {
                tvMemberPrice.setVisibility(View.VISIBLE);
                tvMemberPrice.setText(String.format("会员价¥ %.2f", selectSpec.getShop_member_price() / 100.00));
                if (storeDetailBean.getDetail().getIs_shop_member_user() == 1) {
                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvPrice.getPaint().setFlags(0);
                }
            } else {
                tvMemberPrice.setVisibility(GONE);
                tvPrice.getPaint().setFlags(0);
            }
            tvPrice.setText(String.format("¥ %.2f", money));
        } else {
            if (goodsInfo.getIs_shop_member() == 1) {
                tvMemberPrice.setVisibility(View.VISIBLE);
                tvMemberPrice.setText(String.format("会员价¥ %.2f", goodsInfo.getShop_member_price() / 100.00));
                if (storeDetailBean.getDetail().getIs_shop_member_user() == 1) {
                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvPrice.getPaint().setFlags(0);
                }
            } else {
                tvMemberPrice.setVisibility(GONE);
                tvPrice.getPaint().setFlags(0);
            }
            tvPrice.setText(String.format("¥ %.2f", goodsInfo.getPrice() / 100.00));
        }
        StringBuilder sbSpec = new StringBuilder();
        sbSpec.append("(");
        if (selectSpec != null) {
            sbSpec.append(selectSpec.getAttr_name());
            sbSpec.append("+");
        }
        if (hmSelectNature != null && hmSelectNature.size() > 0) {
            for (String key : hmSelectNature.keySet()) {
                sbSpec.append(hmSelectNature.get(key).getNature_name());
                sbSpec.append("+");
            }
        }
        sbSpec.deleteCharAt(sbSpec.length() - 1);
        sbSpec.append(")");
        tvSpec.setText(sbSpec.toString());
        if (goodsInfo.getHmBuyNum().containsKey(getGoodsCurrentSelectedSpecKey(goodsInfo))) {
            tvBuyNum.setText("" + goodsInfo.getHmBuyNum().get(getGoodsCurrentSelectedSpecKey(goodsInfo)));
        } else {
            tvBuyNum.setText("0");
        }
    }

    public String getGoodsCurrentSelectedSpecKey(GoodsInfo2 goodsInfo) {
        StringBuilder natureId = new StringBuilder();
        if (hmSelectNature != null && hmSelectNature.size() > 0) {
            for (String key : hmSelectNature.keySet()) {
                natureId.append(hmSelectNature.get(key).getNature_id());
                natureId.append("+");
            }
            natureId.deleteCharAt(natureId.length() - 1);
        }
        return goodsInfo.getGoods_id() + "," + (selectSpec != null ? selectSpec.getAttr_id() : "") + "," + natureId.toString();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void addGoodsToServer(GoodsInfo2 goodsInfo) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("login_token", SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN));
        params.put("goods_id", goodsInfo.getGoods_id());
        if (goodsInfo.getIs_attr() == 1) {
            params.put("attr_id", goodsInfo.getAttr_id());
        }
        params.put("type", storeDetailBean.getDetail().getGrade_id() == AppConfig.StoreType.VEGETABLE_MARKET ? 1 : 2);
        params.put("shop_id", storeDetailBean.getDetail().getShop_id());
        params.put("num", goodsInfo.getHmBuyNum().keySet().iterator().hasNext() ? goodsInfo.getHmBuyNum().get(goodsInfo.getHmBuyNum().keySet().iterator().next()) : 0);
        ArrayList<String> lstNature = new ArrayList<>();
        if (hmSelectNature != null) {
            //通过多规格弹窗的方式更改数量
            for (String s : hmSelectNature.keySet()) {
                lstNature.add(hmSelectNature.get(s).getNature_id());
            }
        } else if (goodsInfo.getSelected_nature_ids() != null && goodsInfo.getSelected_nature_ids().size() > 0) {
            //在购物车中直接更改数量
            for (String key : goodsInfo.getHmBuyNum().keySet()) {
                String[] split = key.split(",");
                if (split.length >= 3) {
                    String natureId = split[2];
                    String[] split1 = natureId.split("\\+");
                    for (String s : split1) {
                        lstNature.add(s);
                    }
                }
            }
        }
        if (lstNature.size() > 0) {
            params.put("nature", new Gson().toJson(lstNature));
        }
        new MyHttp().doPost(Api.getDefault().addCart(params), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private boolean isContain = false;

    public void updateShopCart(GoodsInfo2 goodsInfo) {
        if (goodsInfo != null) {
            isContain = false;
            String buyNumKey = goodsInfo.getHmBuyNum().keySet().iterator().next();
            for (GoodsInfo2 selectedGood : lstSelectedGoods) {
                Iterator<String> iterator = selectedGood.getHmBuyNum().keySet().iterator();
                if (iterator.hasNext()) {
                    String key = iterator.next();
                    if (buyNumKey.equals(key)) {
                        if (goodsInfo.getHmBuyNum().get(buyNumKey) == 0) {
                            lstSelectedGoods.remove(selectedGood);
                        } else {
                            selectedGood.setHmBuyNum(goodsInfo.getHmBuyNum());
                        }
                        isContain = true;
                        break;
                    }
                }
            }
            if (!isContain && goodsInfo.getHmBuyNum().get(buyNumKey) != 0) {
                lstSelectedGoods.add(goodsInfo);
            }
        }
//        goodsFragment.updateMenuNum(lstSelectedGoods);
        shopCartTotalNum = 0;
        float shopCartTotalPrice = 0.00f;
        for (GoodsInfo2 selectGoods : lstSelectedGoods) {
            Iterator<String> iterator = selectGoods.getHmBuyNum().keySet().iterator();
            if (iterator.hasNext()) {
                String buyNumKey = iterator.next();
                shopCartTotalNum += selectGoods.getHmBuyNum().get(buyNumKey);
                if (storeDetailBean.getDetail().getIs_shop_member_user() == 1 && selectGoods.getIs_shop_member() == 1) {
                    shopCartTotalPrice += (float) selectGoods.getShop_member_price() / 100 * selectGoods.getHmBuyNum().get(buyNumKey);
                } else {
                    shopCartTotalPrice += (float) selectGoods.getPrice() / 100 * selectGoods.getHmBuyNum().get(buyNumKey);
                }
            }
        }
        shopCarView.showBadge(shopCartTotalNum);
        shopCarView.updateAmount(shopCartTotalPrice, !TextUtils.isEmpty(bookSeatId));
        if (fragmentList.size() != 0) {
            Goods2Fragment fragment = (Goods2Fragment) fragmentList.get(0);
            fragment.updateShopCartGoods(lstSelectedGoods);
        }
    }

    @Override
    public void ListClick(List<CouponInfo> info) {
        storeDetailBean.getCoupon().setList(info);
        updateCoupon();
    }

    public class ShopDetailPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        ShopDetailPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_BOOK:
                    bookSeatId = data.getStringExtra("bookSeatId");
                    break;
                case REQUEST_CODE_GOODS_DETAIL:
                    getShopCarInfo();
                    break;
            }
        }
    }

    public void addTvAnim(View fromView, int[] carLoc, Context context, final CoordinatorLayout rootview) {
        int[] addLoc = new int[2];
        fromView.getLocationInWindow(addLoc);

        Path path = new Path();
        path.moveTo(addLoc[0], addLoc[1]);
        path.quadTo(carLoc[0], addLoc[1] - 200, carLoc[0], carLoc[1]);

        final TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.circle_app_color);
        textView.setText("1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(fromView.getWidth(), fromView.getHeight());
        rootview.addView(textView, lp);
        ViewAnimator.animate(textView).path(path).accelerate().duration(500).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootview.removeView(textView);
            }
        }).start();
    }

    private void closePopWindow() {
        if (shopCarDetailBehavior != null) {
            shopCarDetailBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}

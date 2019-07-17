package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wbx.mall.R;
import com.wbx.mall.adapter.GoodsFreeAdapter;
import com.wbx.mall.adapter.ShopCarAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.CouponInfo;
import com.wbx.mall.bean.FullInfo;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.bean.RandomRedPacketBean;
import com.wbx.mall.bean.ShopCart;
import com.wbx.mall.bean.StoreDetailBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.RedPacketFragment;
import com.wbx.mall.dialog.ShopCarPopup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * 新版店铺详情
 */
public class DetailActivity extends BaseActivity implements AddWidget.OnAddClick, ShopCouponDialog.DialogListener {
    @Bind(R.id.root_view)
    CoordinatorLayout rootView;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.goods_free)
    RelativeLayout mRelative;
    @Bind(R.id.new_shop_recycler)
    RecyclerView newRecycler;
    @Bind(R.id.shop_info_container)
    ShopInfoContainer shopInfoContainer;
    @Bind(R.id.iv_book)
    DragImageView ivBook;
    @Bind(R.id.tv_discount)
    TextView tvDisCount;
    @Bind(R.id.tv_discount_more)
    TextView tvDisCountMore;
    @Bind(R.id.ll_container_coupon)
    LinearLayout llContainerCoupon;
    @Bind(R.id.shop_car_view)
    ShopCarView shopCarView;
//    @Bind(R.id.recycler_view_shop_car_detail)
//    RecyclerView mShopCarList;

    private static final String IS_VEGETABLE_MARKET = "IsVegetableMarket";
    private static final String STORE_ID = "StoreId";
    public static final int REQUEST_CODE_BOOK = 1008;//预定
    private boolean isVM;//是否菜市场
    private String mStoreId;//店铺id
    private String[] title = {"热卖", "评论", "商家"};
    public StoreDetailBean mStoreInfo;
    private ShopCouponDialog couponDialog;//领取优惠券弹窗
    private ShopDiscountDialog shopDiscountDialog;//优惠活动弹窗
    private String mStrDiscount;//优惠活动文字
    private List<Fragment> fragmentList;
    private List<GoodsInfo2> mSelectList = new ArrayList<>();
    private ShopCarAdapter shopCarAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    public static void actionStart(Context context, boolean isVegetable, String storeId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(IS_VEGETABLE_MARKET, isVegetable);
        intent.putExtra(STORE_ID, storeId);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        isVM = getIntent().getBooleanExtra(IS_VEGETABLE_MARKET, false);
        mStoreId = getIntent().getStringExtra(STORE_ID);
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(Goods2Fragment.newInstance(mStoreId, isVM));
        fragmentList.add(ShopCommentFragment.newInstance(mStoreId));
        fragmentList.add(MerchantInfoFragment.newInstance(mStoreInfo.getDetail()));
        ShopDetailPagerAdapter pagerAdapter = new ShopDetailPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    /**
     * 获取数据
     */
    @Override
    public void fillData() {
        LoadingDialog.showDialogForLoading(this);
        new MyHttp().doPost(Api.getDefault().getShopInfo(mStoreId, SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_TOKEN)), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mStoreInfo = result.getObject("data", StoreDetailBean.class);
                updateUI();
                getShopCarInfo();
                getRandomRedPacket();
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    private void updateUI() {
        if (mStoreInfo.getSeckill_goods().size() == 0) {
            mRelative.setVisibility(GONE);
        } else {
            mRelative.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            newRecycler.setLayoutManager(manager);
            GoodsFreeAdapter adapter = new GoodsFreeAdapter(R.layout.layout_goodsfree, mStoreInfo.getSeckill_goods());
            newRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        shopInfoContainer.updateUI(this, mStoreInfo.getDetail());
        if (mStoreInfo.getDetail().getGrade_id() == AppConfig.StoreType.FOOD_STREET && mStoreInfo.getDetail().getIs_subscribe() == 1) {
            ivBook.setVisibility(View.VISIBLE);
        }
        shopCarView.setShopInfo(mStoreInfo.getDetail());
        initShopCar();
        addCoupon();
        addFullReduce();
        initViewPager();
    }

    /**
     * 获取满减字段
     */
    private void addFullReduce() {
        List<FullInfo> fullInfoList = mStoreInfo.getFull_money_reduce();
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

    /**
     * 添加头部优惠券领取布局
     */
    private void addCoupon() {
        List<CouponInfo> list = mStoreInfo.getCoupon().getList();
        if (list == null || list.size() == 0) {
            ((View) llContainerCoupon.getParent()).setVisibility(GONE);
            return;
        }
        updateCoupon();
    }

    /**
     * 更新头部优惠券领取布局
     */
    private void updateCoupon() {
        llContainerCoupon.removeAllViews();
        for (CouponInfo couponInfo : mStoreInfo.getCoupon().getList()) {
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
            llContainerCoupon.addView(layout);
        }
    }

    /**
     * 获取随机红包
     */
    private void getRandomRedPacket() {
        if (LoginUtil.isLogin()) {
            new MyHttp().doPost(Api.getDefault().getRandomRedPacket(LoginUtil.getLoginToken(), mStoreId), new HttpListener() {
                @Override
                public void onSuccess(JSONObject result) {
                    RandomRedPacketBean data = result.getObject("data", RandomRedPacketBean.class);
                    if (data != null) {
                        RedPacketFragment redPacketFragment = RedPacketFragment.newInstance(data, mStoreInfo.getDetail().getShop_id());
                        redPacketFragment.show(getSupportFragmentManager(), "");
                        redPacketFragment.setOnReceiveSuccessListener(new RedPacketFragment.OnReceiveSuccessListener() {
                            @Override
                            public void onSuccess() {
                                shopInfoContainer.updateCollect();
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

    @Override
    public void setListener() {

    }

    @Override
    public void onAddClick(View view, GoodsInfo2 goodsInfo) {
    }

    @Override
    public void onSubClick(GoodsInfo2 goodsInfo) {
    }

    @Override
    public void onClickSpecs(GoodsInfo2 goodsInfo) {
    }

    @Override
    public void ListClick(List<CouponInfo> couponInfoList) {
        mStoreInfo.getCoupon().setList(couponInfoList);
        updateCoupon();
    }

    /**
     * viewpager适配器
     */
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

    @OnClick({R.id.iv_book, R.id.iv_intelligent_pay, R.id.ll_container_coupon, R.id.tv_discount_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_book:
                toBook();
                break;
            case R.id.ll_container_coupon:
                if (couponDialog == null) {
                    couponDialog = new ShopCouponDialog(DetailActivity.this, mStoreInfo.getCoupon().getList(), this);
                }
                couponDialog.show();
                break;
            case R.id.tv_discount_more:
                if (shopDiscountDialog == null) {
                    shopDiscountDialog = new ShopDiscountDialog(DetailActivity.this, mStrDiscount);
                }
                shopDiscountDialog.show();
                break;
            case R.id.iv_intelligent_pay:
                intelligentPay();
                break;
        }
    }

    /**
     * 到店付款
     */
    private void intelligentPay() {
        if (TextUtils.isEmpty(mStoreId)) {
            return;
        }
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra("isMainTo", true);
            startActivity(intent);
        } else {
            IntelligentPayActivity.actionStart(this, String.valueOf(mStoreId));
        }
    }

    /**
     * 预定
     */
    private void toBook() {
        if (TextUtils.isEmpty(mStoreId)) {
            return;
        }
        Intent intentBookSeat = new Intent(mContext, BookSeatActivity.class);
        intentBookSeat.putExtra("shop_id", mStoreId);
        startActivityForResult(intentBookSeat, REQUEST_CODE_BOOK);
    }

    private void addGoodsToServer(GoodsInfo2 goodsInfo) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("login_token", SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN));
        params.put("goods_id", goodsInfo.getGoods_id());
        if (goodsInfo.getIs_attr() == 1) {
            params.put("attr_id", goodsInfo.getAttr_id());
        }
        params.put("type", isVM ? 1 : 2);
        params.put("shop_id", mStoreId);
        params.put("num", 1);//购买数量
        ArrayList<String> lstNature = new ArrayList<>();
//        if (hmSelectNature != null) {
//            //通过多规格弹窗的方式更改数量
//            for (String s : hmSelectNature.keySet()) {
//                lstNature.add(hmSelectNature.get(s).getNature_id());
//            }
//        } else if (goodsInfo.getSelected_nature_ids() != null && goodsInfo.getSelected_nature_ids().size() > 0) {
//            //在购物车中直接更改数量
//            for (String key : goodsInfo.getHmBuyNum().keySet()) {
//                String[] split = key.split(",");
//                if (split.length >= 3) {
//                    String natureId = split[2];
//                    String[] split1 = natureId.split("\\+");
//                    for (String s : split1) {
//                        lstNature.add(s);
//                    }
//                }
//            }
//        }
//        if (lstNature.size() > 0) {
//            params.put("nature", new Gson().toJson(lstNature));
//        }
        new MyHttp().doPost(Api.getDefault().addCart(params), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onError(int code) {
            }
        });
    }

    /**
     * 请求购物车信息
     */
    private void getShopCarInfo() {
        if (!SPUtils.getSharedBooleanData(mActivity, AppConfig.LOGIN_STATE)) {
            return;
        }
        new MyHttp().doPost(Api.getDefault().getCartInfo(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), mStoreId), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                mSelectList.clear();
                if (!TextUtils.isEmpty(result.getString("data"))) {
                    List<ShopCart> dataList = JSONArray.parseArray(result.getString("data"), ShopCart.class);
                    List<GoodsInfo2> goods_cart = dataList.get(0).getGoods_cart();
                    mSelectList.addAll(goods_cart);
//                    updateShopCart(null);
                }
            }

            @Override
            public void onError(int code) {
                LoadingDialog.cancelDialogForLoading();
            }
        });

    }

    private void initShopCar() {
//        mShopCarList.setLayoutManager(new LinearLayoutManager(this));
//        mShopCarList.setHasFixedSize(true);
//        shopCarAdapter = new ShopCarAdapter(mSelectList);
//        shopCarAdapter.bindToRecyclerView(mShopCarList);
        shopCarView.setOnGoodsClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopCarPopup shopCarPopup = new ShopCarPopup(DetailActivity.this);
                shopCarPopup.setList(mSelectList);
                shopCarPopup.showAsDropDown(mTabLayout);
            }
        });
    }
}
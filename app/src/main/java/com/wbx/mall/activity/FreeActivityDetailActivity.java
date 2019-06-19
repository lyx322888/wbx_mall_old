package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.adapter.UpFreeInfoAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.baserx.NewRxBus;
import com.wbx.mall.bean.FreeGoodsDetailBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.bean.NewFreeInfoBean2;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.PollingBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.presenter.UpFreeInfoPresenterImp;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.UpFreeInfoUtils;
import com.wbx.mall.view.UpFreeInfoView;
import com.wbx.mall.widget.IndexAvatar;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;


public class FreeActivityDetailActivity extends BaseActivity implements UpFreeInfoView {
    @Bind(R.id.iv_goods)
    ImageView ivGoods;
    @Bind(R.id.iv_consume_free_rule)
    ImageView ivConsumeFreeRule;
    @Bind(R.id.tv_free_rule)
    TextView tvFreeRule;
    @Bind(R.id.iv_shop)
    IndexAvatar ivShop;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_announce)
    TextView tvAnnounce;
    @Bind(R.id.iv_identify)
    ImageView ivIdentify;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_free_number)
    TextView tvFreeNumber;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.tv_share_rule)
    TextView tvShareRule;
    @Bind(R.id.ll_share_free_rule)
    LinearLayout llShareFreeRule;
    @Bind(R.id.ll_consume_free_rule)
    LinearLayout llConsumeFreeRule;
    //    @Bind(R.id.rv_free_record)
//    ScrollRecycleView rvFreeRecord;
//    @Bind(R.id.rv_user_share)
//    RecyclerView rvUserShare;
    @Bind(R.id.scroll_view)
    NestedScrollView scrollView;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.bar_1)
    View bar1;
    @Bind(R.id.bar_2)
    View bar2;
    @Bind(R.id.bar_3)
    View bar3;
    @Bind(R.id.tv_price_1)
    TextView tvPrice1;
    @Bind(R.id.tv_buy_type_1)
    TextView tvBuyType1;
    @Bind(R.id.tv_price_2)
    TextView tvPrice2;
    @Bind(R.id.tv_buy_type_2)
    TextView tvBuyType2;
    @Bind(R.id.tv_price_3)
    TextView tvPrice3;
    @Bind(R.id.tv_buy_type_3)
    TextView tvBuyType3;
    //    @Bind(R.id.layout_free_record)
//    View freeInfoView;
    @Bind(R.id.layout_free_record)
    RecyclerView freeInfoView;
    private String goodsId;
    private int gradeId;
    private MyHttp myHttp;
    private FreeGoodsDetailBean data;
    private String shopId;
    private UpFreeInfoPresenterImp upFreeInfoPresenterImp;
//    private ScrollRecycleView rvFreeRecord;
//    private RecyclerView rvUserShare;
    public static void actionStart(Context context, String shopId, String goodsId, int gradeId) {
        Intent intent = new Intent(context, FreeActivityDetailActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("gradeId", gradeId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_free_detail;
    }

    @Override
    public void initPresenter() {
        shopId = getIntent().getStringExtra("shopId");
        goodsId = getIntent().getStringExtra("goodsId");
        gradeId = getIntent().getIntExtra("gradeId", 0);
        myHttp = new MyHttp();
    }

    @Override
    public void initView() {
        scrollView.setNestedScrollingEnabled(false);
        freeInfoView.setLayoutManager(new LinearLayoutManager(mContext));
        upFreeInfoPresenterImp = new UpFreeInfoPresenterImp(this);
        upFreeInfoPresenterImp.getUpFreeInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        PollingBean pollingBean = new PollingBean();
        pollingBean.setPlay(true);
        NewRxBus.getInstance().post(pollingBean);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PollingBean pollingBean = new PollingBean();
        pollingBean.setPlay(false);
        NewRxBus.getInstance().post(pollingBean);
    }

    @Override
    public void fillData() {
        LocationInfo locationInfo = mLocationInfo = (LocationInfo) BaseApplication.getInstance().readObject(AppConfig.LOCATION_DATA);
        LoadingDialog.showDialogForLoading(this);
        myHttp.doPost(Api.getDefault().getFreeGoodsDetail(goodsId, gradeId, locationInfo.getLat(), locationInfo.getLng(), LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", FreeGoodsDetailBean.class);
                updateUI();
            }

            @Override
            public void onError(int code) {

            }
        });
        Subscription subscribe = NewRxBus.getInstance().tObservable(NewFreeInfoBean.class)
                .subscribe(new Action1<NewFreeInfoBean>() {
                    @Override
                    public void call(NewFreeInfoBean newFreeInfoBean) {
                        UpFreeInfoUtils.upUI(newFreeInfoBean, freeInfoView, FreeActivityDetailActivity.this);
                    }
                });
        addSubscription(this, subscribe);
    }

    private void updateUI() {
        GlideUtils.showMediumPic(this, ivShop, data.getShop().getLogo());
        tvDistance.setText(data.getShop().getDistance());
        tvShopName.setText(data.getShop().getShop_name());
        ivIdentify.setVisibility(data.getShop().getIs_renzheng() == 1 ? View.VISIBLE : View.GONE);
        tvAnnounce.setText(data.getShop().getNotice());
        GlideUtils.showBigPic(this, ivGoods, data.getFree_goods().getPhoto());
        if (data.getFree_goods().getIs_consume_free() == 1) {
            tvPrice.setText(String.format("%.2f", data.getFree_goods().getConsume_free_price() / 100.00));
        } else if (data.getFree_goods().getIs_share_free() == 1) {
            tvPrice.setText(String.format("%.2f", data.getFree_goods().getShare_free_price() / 100.00));
        }
        if (data.getFree_goods().getIs_consume_free() == 1) {
            ivConsumeFreeRule.setVisibility(View.VISIBLE);
            tvFreeRule.setVisibility(View.VISIBLE);
            tvFreeRule.setText(String.format("%d人消费1人免单", data.getFree_goods().getConsume_free_amount()));
        } else {
            ivConsumeFreeRule.setVisibility(View.GONE);
            tvFreeRule.setVisibility(View.GONE);
        }
        tvFreeNumber.setText(String.format("已有%d人免单成功", data.getFree_goods().getFree_goods_all_users()));
        tvGoodsName.setText(data.getFree_goods().getTitle());
        if (data.getFree_goods().getIs_share_free() == 1) {
            llShareFreeRule.setVisibility(View.VISIBLE);
        } else {
            llShareFreeRule.setVisibility(View.GONE);
        }
        if (data.getFree_goods().getIs_consume_free() == 1) {
            llConsumeFreeRule.setVisibility(View.VISIBLE);
        } else {
            llConsumeFreeRule.setVisibility(View.GONE);
        }
        tvPrice1.setText(String.format("%.2f", data.getFree_goods().getPrice() / 100.00));
        if (data.getFree_goods().getIs_consume_free() == 1 && data.getFree_goods().getIs_share_free() == 1) {
            tvPrice2.setText("¥0");
            tvPrice3.setText(String.format("¥%.2f", data.getFree_goods().getConsume_free_price() / 100.000));
            tvBuyType2.setText("分享" + data.getFree_goods().getShare_free_amount() + "人免单");
            tvBuyType3.setText(String.format("%d人消费1人免单", data.getFree_goods().getConsume_free_amount()));
        } else {
            bar2.setVisibility(View.GONE);
            tvPrice2.setVisibility(View.GONE);
            tvBuyType2.setVisibility(View.GONE);
            if (data.getFree_goods().getIs_consume_free() == 1) {
                tvPrice3.setText(String.format("¥%.2f", data.getFree_goods().getConsume_free_price() / 100.000));
                tvBuyType3.setText(String.format("%d人消费1人免单", data.getFree_goods().getConsume_free_amount()));
            } else {
                //分享免单
                tvPrice3.setText("¥0");
                tvBuyType3.setText("分享" + data.getFree_goods().getShare_free_amount() + "人免单");
            }
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_close, R.id.tv_home, R.id.iv_shop, R.id.tv_share_rule, R.id.tv_consume_rule, R.id.bar_1, R.id.bar_2, R.id.bar_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_shop:
                StoreDetailActivity.actionStart(this, gradeId, shopId);
                break;
            case R.id.tv_share_rule:
                break;
            case R.id.tv_consume_rule:
                break;
            case R.id.bar_1:
                directlyBuy();
                break;
            case R.id.bar_2:
                ShareFreeActivity.actionStart(this, goodsId, gradeId);
                break;
            case R.id.bar_3:
                if (data != null) {
                    if (data.getFree_goods().getIs_consume_free() != 1) {
                        ShareFreeActivity.actionStart(this, goodsId, gradeId);
                    } else {
                        ConsumeFreeActivity.actionStart(this, goodsId, gradeId);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 单独购买
     */
    private void directlyBuy() {
        if (!SPUtils.getSharedBooleanData(this, AppConfig.LOGIN_STATE)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        LoadingDialog.showDialogForLoading(this, "下单中...", false);
        new MyHttp().doPost(gradeId == AppConfig.StoreType.VEGETABLE_MARKET ? Api.getDefault().createVegetableOrder(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), createGoodsJson()) : Api.getDefault().createOrder(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), createGoodsJson(), null), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(FreeActivityDetailActivity.this, SubmitOrderActivity.class);
                intent.putExtra("isPhysical", gradeId != AppConfig.StoreType.VEGETABLE_MARKET);
                intent.putExtra("orderId", orderId);
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
        OrderBean orderBean = new OrderBean();
        if (gradeId == AppConfig.StoreType.VEGETABLE_MARKET) {
            orderBean.setProduct_id(goodsId);
        } else {
            orderBean.setGoods_id(goodsId);
        }
        orderBean.setNum(1);
//                ArrayList<String> lstNature = new ArrayList<>();
//                String[] split = key.split(",");
//                if (split.length >= 3) {
//                    String[] split1 = split[2].split("\\+");
//                    if (split1.length > 0) {
//                        for (String s : split1) {
//                            lstNature.add(s);
//                        }
//                    }
//                    orderBean.setNature(lstNature);
//                }
        lstGoods.add(orderBean);
        hashMap.put(shopId, lstGoods);
        return new Gson().toJson(hashMap);
    }


    @Override
    public void getUpFreeInfo(NewFreeInfoBean2 newFreeInfoBean2) {
        List<NewFreeInfoBean2.DataBean> dataBeanList=new ArrayList<>();
        dataBeanList.add(newFreeInfoBean2.getData());
        UpFreeInfoAdapter upFreeInfoAdapter = new UpFreeInfoAdapter(dataBeanList);
        freeInfoView.setAdapter(upFreeInfoAdapter);
    }
}

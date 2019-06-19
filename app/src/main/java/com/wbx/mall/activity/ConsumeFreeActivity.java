package com.wbx.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wbx.mall.R;
import com.wbx.mall.adapter.FreeActivityParticipantAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.base.BaseApplication;
import com.wbx.mall.baserx.NewRxBus;
import com.wbx.mall.bean.ConsumeFreeGoodsDetailBean;
import com.wbx.mall.bean.LocationInfo;
import com.wbx.mall.bean.NewFreeInfoBean;
import com.wbx.mall.bean.OrderBean;
import com.wbx.mall.bean.PollingBean;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.dialog.ShareFreeActivityDialog;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.SPUtils;
import com.wbx.mall.utils.ShareUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.CircleImageView;
import com.wbx.mall.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class ConsumeFreeActivity extends BaseActivity {
    @Bind(R.id.rv_participant)
    RecyclerView rvParticipant;
    @Bind(R.id.tv_see_all)
    TextView tvSeeAll;
    @Bind(R.id.iv_shop)
    ImageView ivShop;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.iv_identify)
    ImageView ivIdentify;
    @Bind(R.id.tv_announce)
    TextView tvAnnounce;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.iv_goods)
    ImageView ivGoods;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_consume_need_num)
    TextView tvConsumeNeedNum;
    @Bind(R.id.tv_consume_remain_num)
    TextView tvConsumeRemainNum;
    @Bind(R.id.tv_hour)
    TextView tvHour;
    @Bind(R.id.tv_minute)
    TextView tvMinute;
    @Bind(R.id.tv_second)
    TextView tvSecond;
    @Bind(R.id.iv_follow)
    ImageView ivFollow;
    @Bind(R.id.tv_follow)
    TextView tvFollow;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    @Bind(R.id.tv_order)
    TextView tvOrder;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_consume_free_head_title)
    TextView tvFreeHeadTitle;
    @Bind(R.id.tv_consume_free_head_time)
    TextView tvFreeHeadTime;
    @Bind(R.id.ll_count_down_time)
    LinearLayout llCountDownTime;
    @Bind(R.id.cv_consume_free_head_face)
    CircleImageView cvFreeHeadFace;
    @Bind(R.id.view_1)
    LinearLayout llHeadView;
    private FreeActivityParticipantAdapter participantAdapter;
    private List<ConsumeFreeGoodsDetailBean.ActivityUsers> lstParticipant = new ArrayList<>();
    private String goodsId;
    private int gradeId;
    private MyHttp myHttp;
    private ConsumeFreeGoodsDetailBean data;
    private CountDownTimer timer;
    private boolean isPaySuccess = false;

    public static void actionStart(Context context, String goodsId, int gradeId) {
        actionStart(context, null, goodsId, gradeId);
    }

    public static void actionStart(Context context, String activityId, String goodsId, int gradeId) {
        Intent intent = new Intent(context, ConsumeFreeActivity.class);
        intent.putExtra("activityId", activityId);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("gradeId", gradeId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_consume_free;
    }

    @Override
    public void initPresenter() {
        goodsId = getIntent().getStringExtra("goodsId");
        gradeId = getIntent().getIntExtra("gradeId", 0);
        myHttp = new MyHttp();
    }

    @Override
    public void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvParticipant.setLayoutManager(gridLayoutManager);
        participantAdapter = new FreeActivityParticipantAdapter(lstParticipant);
        rvParticipant.setAdapter(participantAdapter);
        rvParticipant.setNestedScrollingEnabled(false);
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
        myHttp.doPost(Api.getDefault().getConsumeFreeGoodsDetail(getIntent().getStringExtra("activityId"), goodsId, gradeId, locationInfo.getLat(), locationInfo.getLng(), LoginUtil.getLoginToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", ConsumeFreeGoodsDetailBean.class);
                updateUI();
            }

            @Override
            public void onError(int code) {
                if (code == AppConfig.ERROR_STATE.ACTIVITY_FINISH) {
                    ToastUitl.showLong("该活动已结束");
                    finish();
                }
            }
        });
        Subscription subscribe = NewRxBus.getInstance().tObservable(NewFreeInfoBean.class)
                .subscribe(new Action1<NewFreeInfoBean>() {
                    @Override
                    public void call(NewFreeInfoBean newFreeInfoBean) {
                        upHeadFreeUi(newFreeInfoBean);
                    }
                });
        addSubscription(this, subscribe);
    }

    private void upHeadFreeUi(NewFreeInfoBean newFreeInfoBean) {
        if (newFreeInfoBean.getSuccess_activity().getCount_success_activity_user() > 0) {
            if (llHeadView.getVisibility() == View.INVISIBLE) {
                llHeadView.setVisibility(View.VISIBLE);
            }
            NewFreeInfoBean.SuccessActivityBeanX.SuccessActivityBean successActivityBean = newFreeInfoBean.getSuccess_activity().getSuccess_activity().get(0);
            tvFreeHeadTime.setText(successActivityBean.getTime_ago());
            tvFreeHeadTitle.setText(String.format("%1$s获取了%2$s", successActivityBean.getNickname(), successActivityBean.getTitle()));
            GlideUtils.showSmallPic(ConsumeFreeActivity.this, cvFreeHeadFace, successActivityBean.getPhoto());
        }
    }

    private void updateUI() {
        GlideUtils.showMediumPic(this, ivShop, data.getShop().getLogo());
        tvDistance.setText(data.getShop().getDistance());
        tvShopName.setText(data.getShop().getShop_name());
        ivIdentify.setVisibility(data.getShop().getIs_renzheng() == 1 ? View.VISIBLE : View.GONE);
        tvAnnounce.setText(data.getShop().getNotice());
        tvGoodsName.setText(data.getGoods().getTitle());
        GlideUtils.showBigPic(this, ivGoods, data.getGoods().getPhoto());
        tvPrice.setText(String.format("%.2f元", data.getGoods().getPrice() / 100.000));
        tvConsumeNeedNum.setText(data.getUser_free_activity().getNeed_participants() + "人消费");
        tvConsumeRemainNum.setText(String.valueOf(data.getUser_free_activity().getSurplus_users()));
        if (data.getUser_free_activity().getForever() == 1) {
            tvTime.setText("活动不限时");
            llCountDownTime.setVisibility(View.GONE);
        } else {
            countDown(data.getUser_free_activity().getSurplus_time());
        }
        if (data.getUser_free_activity().getNeed_participants() <= 5) {
            tvSeeAll.setVisibility(View.GONE);
        }
        //isPay 0未支付 1已支付
        isPaySuccess = data.getUser_free_activity().getIs_pay() != 0;
        if (data.getUser_free_activity().getIs_pay() == 0) {
            tvOrder.setText("立即下单");
        } else {
            tvOrder.setText("立即分享");
        }
        showAllParticipant();
        tvFollow.setText("关注" + data.getShop().getShop_name());
        ivFollow.setSelected(true);
        tvHint.setText("您有" + Math.round(100.0 / data.getUser_free_activity().getNeed_participants()) + "%机会赢免单，下单就有机会！");
    }

    private boolean isShowAllParticipant = false;

    private void showAllParticipant() {
        if (isShowAllParticipant) {
            tvSeeAll.setText("收起全部");
            Drawable drawable = getResources().getDrawable(R.drawable.icon_arrow_up);
            drawable.setBounds(0, 0, DisplayUtil.dip2px(10), DisplayUtil.dip2px(5));
            tvSeeAll.setCompoundDrawables(null, null, drawable, null);
            lstParticipant.clear();
            lstParticipant.addAll(data.getActivity_users());
            for (int i = 0; i < data.getUser_free_activity().getNeed_participants() - data.getActivity_users().size(); i++) {
                ConsumeFreeGoodsDetailBean.ActivityUsers activityUsers = new ConsumeFreeGoodsDetailBean.ActivityUsers();
                lstParticipant.add(activityUsers);
            }
            participantAdapter.notifyDataSetChanged();
        } else {
            tvSeeAll.setText("查看全部");
            Drawable drawable = getResources().getDrawable(R.drawable.icon_arrow_down);
            drawable.setBounds(0, 0, DisplayUtil.dip2px(10), DisplayUtil.dip2px(5));
            tvSeeAll.setCompoundDrawables(null, null, drawable, null);
            lstParticipant.clear();
            if (data.getUser_free_activity().getNeed_participants() <= 5) {
                lstParticipant.addAll(data.getActivity_users());
                for (int i = 0; i < data.getUser_free_activity().getNeed_participants() - data.getActivity_users().size(); i++) {
                    ConsumeFreeGoodsDetailBean.ActivityUsers activityUsers = new ConsumeFreeGoodsDetailBean.ActivityUsers();
                    lstParticipant.add(activityUsers);
                }
            } else {
                if (data.getActivity_users().size() <= 5) {
                    lstParticipant.addAll(data.getActivity_users());
                    for (int i = 0; i < 5 - data.getActivity_users().size(); i++) {
                        ConsumeFreeGoodsDetailBean.ActivityUsers activityUsers = new ConsumeFreeGoodsDetailBean.ActivityUsers();
                        lstParticipant.add(activityUsers);
                    }
                } else {
                    for (int i = 0; i < data.getActivity_users().size(); i++) {
                        if (i < 5) {
                            lstParticipant.add(data.getActivity_users().get(i));
                        }
                    }
                }
            }
            participantAdapter.notifyDataSetChanged();
        }

    }

    private void countDown(long remainSecond) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(remainSecond * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvHour.setText(DateUtil.getHourNoDay((int) (millisUntilFinished / 1000)));
                tvMinute.setText(DateUtil.getMinute((int) (millisUntilFinished / 1000)));
                tvSecond.setText(DateUtil.getSecond((int) (millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                tvHour.setText("00");
                tvMinute.setText("00");
                tvSecond.setText("00");
            }
        };
        timer.start();
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @OnClick({R.id.tv_enter_shop, R.id.iv_open_store, R.id.tv_see_all, R.id.tv_consume_rule, R.id.iv_follow, R.id.tv_follow, R.id.ll_order_right_now})
    public void onViewClicked(View view) {
        if (data == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_enter_shop:
                StoreDetailActivity.actionStart(this, gradeId, String.valueOf(data.getShop().getShop_id()));
                break;
            case R.id.iv_open_store:
                InviteActivity.actionStart(this, "http://www.wbx365.com/wbxwaphome/purchase/index.html", false);
                break;
            case R.id.tv_see_all:
                isShowAllParticipant = !isShowAllParticipant;
                showAllParticipant();
                break;
            case R.id.tv_consume_rule:
                break;
            case R.id.iv_follow:
            case R.id.tv_follow:
                follow();
                break;
            case R.id.ll_order_right_now:
                next();
                break;
        }
    }

    private void next() {
        if (isPaySuccess) {
            String path = "pages/index/freesheet/freesheet?activity_id=" + data.getUser_free_activity().getId() + "&sponsor_user_id=" + LoginUtil.getUserInfo().getUser_id() + "&goods_id=" + goodsId + "&grade_id=" + gradeId + "&shop_id=" + data.getShop().getShop_id() + "&activitytype=consume";
            ShareUtils.getInstance().shareMiniProgram(this, "您的好友已经在附近实体店免单中，快去瞧瞧吧！！！", "", data.getGoods().getPhoto(), path, "www.wbx365.com");
        } else {
            if (!SPUtils.getSharedBooleanData(this, AppConfig.LOGIN_STATE)) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            if (ivFollow.isSelected() && data.getShop().getIs_favorites() != 1) {
                LoadingDialog.showDialogForLoading(this, "下单中...", false);
                myHttp.doPost(Api.getDefault().followStore(data.getShop().getShop_id(), LoginUtil.getLoginToken()), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        order();
                    }

                    @Override
                    public void onError(int code) {
                        order();
                    }
                });
            } else {
                order();
            }
        }
    }

    private void order() {
        LoadingDialog.showDialogForLoading(this, "下单中...", false);
        new MyHttp().doPost(gradeId == AppConfig.StoreType.VEGETABLE_MARKET ? Api.getDefault().createVegetableFreeAcitivityOrder(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), createGoodsJson(), data.getUser_free_activity().getId()) : Api.getDefault().createFreeActivityOrder(SPUtils.getSharedStringData(this, AppConfig.LOGIN_TOKEN), createGoodsJson(), data.getUser_free_activity().getId()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                String orderId = result.getJSONObject("data").getString("order_id");
                Intent intent = new Intent(ConsumeFreeActivity.this, SubmitOrderActivity.class);
                intent.putExtra("isPhysical", gradeId != AppConfig.StoreType.VEGETABLE_MARKET);
                intent.putExtra("orderId", orderId);
                intent.putExtra("isFromFreeActivity", true);
                startActivityForResult(intent, SubmitOrderActivity.REQUEST_FREE_ACTIVITY_PAY);
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
        lstGoods.add(orderBean);
        hashMap.put(String.valueOf(data.getShop().getShop_id()), lstGoods);
        return new Gson().toJson(hashMap);
    }

    private void follow() {
        if (ivFollow.isSelected()) {
            ivFollow.setSelected(false);
        } else {
            ivFollow.setSelected(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SubmitOrderActivity.REQUEST_FREE_ACTIVITY_PAY:
                paySuccess();
                break;
        }
    }

    private void paySuccess() {
        ShareFreeActivityDialog.newInstance(data.getUser_free_activity().getId(), goodsId, data.getShop().getShop_id(), gradeId, data.getGoods().getPhoto()).show(getSupportFragmentManager(), "");
        tvOrder.setText("立即分享");
        isPaySuccess = true;
    }
}

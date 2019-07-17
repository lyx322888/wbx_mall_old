package com.wbx.mall.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wbx.mall.R;
import com.wbx.mall.adapter.OrderTrackAdapter;
import com.wbx.mall.api.Api;
import com.wbx.mall.api.HttpListener;
import com.wbx.mall.api.MyHttp;
import com.wbx.mall.base.BaseActivity;
import com.wbx.mall.bean.OrderDetailBean;
import com.wbx.mall.bean.OrderTrackBean;
import com.wbx.mall.bean.RefundInfo;
import com.wbx.mall.common.LoginUtil;
import com.wbx.mall.utils.DateUtil;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.utils.GlideUtils;
import com.wbx.mall.utils.ToastUitl;
import com.wbx.mall.widget.CustomDragExpandLayout;
import com.wbx.mall.widget.LoadingDialog;
import com.wbx.mall.widget.OrderScrollView;
import com.wbx.mall.widget.iosdialog.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.cover)
    View cover;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.scroll_view)
    OrderScrollView scrollView;
    @Bind(R.id.drag_expand_layout)
    CustomDragExpandLayout dragExpandLayout;
    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    @Bind(R.id.recycler_view_order_track)
    RecyclerView rvOrderTrack;
    @Bind(R.id.ll_order_track)
    LinearLayout llOrderTrack;
    @Bind(R.id.ll_send_by_merchant)
    LinearLayout llSendByMerchant;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_send_fee)
    TextView tvSendFee;
    @Bind(R.id.tv_packing_fee)
    TextView tvPackingFee;
    @Bind(R.id.tv_coupon)
    TextView tvCoupon;
    @Bind(R.id.tv_bonus)
    TextView tvBonus;
    @Bind(R.id.tv_full_reduce_fee)
    TextView tvFullReduceFee;
    @Bind(R.id.tv_red_packet)
    TextView tvRedPacket;
    @Bind(R.id.tv_discount_fee)
    TextView tvDiscountFee;
    @Bind(R.id.tv_actual_pay_fee)
    TextView tvActualPayFee;
    @Bind(R.id.tv_driver_name)
    TextView tvDriverName;
    @Bind(R.id.tv_send_type)
    TextView tvSendType;
    @Bind(R.id.tv_send_time)
    TextView tvSendTime;
    @Bind(R.id.tv_send_info)
    TextView tvSendInfo;
    @Bind(R.id.tv_send_address)
    TextView tvSendAddress;
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.tv_pay_type)
    TextView tvPayType;
    @Bind(R.id.iv_chat)
    ImageView ivChat;
    @Bind(R.id.iv_call_phone)
    ImageView ivCallPhone;
    @Bind(R.id.tv_estimate_time)
    TextView tvEstimateTime;
    @Bind(R.id.tv_status_intro)
    TextView tvStatusIntro;
    @Bind(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @Bind(R.id.tv_apply_refund)
    TextView tvApplyRefund;
    @Bind(R.id.tv_contact_driver)
    TextView tvContactDriver;
    @Bind(R.id.tv_cancel_refund)
    TextView tvCancelRefund;
    @Bind(R.id.tv_apply_service)
    TextView tvApplyService;
    @Bind(R.id.tv_pay_now)
    TextView tvPayNow;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.tv_confirm_receive)
    TextView tvConfirmReceive;
    @Bind(R.id.tv_refund_detail)
    TextView tvRefundDetail;
    @Bind(R.id.tv_delete_order)
    TextView tvDeleteOrder;
    @Bind(R.id.tv_order_state2)
    TextView tvOrderState2;
    private AMap map;
    private CountDownTimer countDownTimer;
    private MyHttp http;
    private int order_type;
    private String orderId;
    private OrderDetailBean data;
    private OrderTrackAdapter orderTrackAdapter;
    private ArrayList<OrderTrackBean> lstOrderTrack = new ArrayList<>();
    private int markerNum = 0;

    public static void actionStart(Context context, String orderId, boolean isVegetable) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("isVegetable", isVegetable);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initPresenter() {
        http = new MyHttp();
        orderId = getIntent().getStringExtra("orderId");
        order_type = getIntent().getBooleanExtra("isVegetable", false) ? 1 : 2;
    }

    @Override
    public void initView() {
        initMap();
        initOrderTrackRecyclerView();
    }

    /**
     * 切换到商家发货的状态
     */
    private void switchToSendByMerchantState() {
        tvOrderState2.setVisibility(View.VISIBLE);
        scrollView.setIsSendByMerchant(true);
        llSendByMerchant.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(llSendByMerchant, "alpha", 0, 1);
        alpha.setDuration(300);
        alpha.start();
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(dragExpandLayout, "y", dragExpandLayout.getY(), DisplayUtil.dip2px(150));
        yAnimator.setDuration(300);
        yAnimator.start();
    }

    private void initMap() {
        mapView.onCreate(mSavedInstanceState);
        if (map == null) {
            map = mapView.getMap();
            UiSettings uiSettings = map.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
            uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        }
    }

    private void initOrderTrackRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvOrderTrack.setLayoutManager(layoutManager);
        orderTrackAdapter = new OrderTrackAdapter(this, lstOrderTrack);
        rvOrderTrack.setAdapter(orderTrackAdapter);
    }

    @Override
    public void fillData() {
    }

    private void updateUI(final OrderDetailBean data) {
        if (data.getOrder_type() == 2) {//实体店4表示待退款，5表示已退款，转换成与菜市场一致
            switch (data.getStatus()) {
                case 4:
                    data.setStatus(3);
                    break;
                case 5:
                    data.setStatus(4);
                    break;
            }
        }
        tvCancelOrder.setVisibility(View.GONE);
        tvPayNow.setVisibility(View.GONE);
        tvApplyRefund.setVisibility(View.GONE);
        tvContactDriver.setVisibility(View.GONE);
        tvRefundDetail.setVisibility(View.GONE);
        tvApplyService.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        tvCancelRefund.setVisibility(View.GONE);
        tvConfirmReceive.setVisibility(View.GONE);
        tvDeleteOrder.setVisibility(View.GONE);
        switch (data.getStatus()) {
            case 0:
                //待付款
                tvCancelOrder.setVisibility(View.VISIBLE);
                tvPayNow.setVisibility(View.VISIBLE);
                break;
            case 1:
                //待发货
                tvApplyRefund.setVisibility(View.VISIBLE);
                break;
            case 2:
                //待收货
                tvApplyRefund.setVisibility(View.VISIBLE);
                tvConfirmReceive.setVisibility(View.VISIBLE);
                if (data.getIs_fengniao() == 1 || data.getIs_dada() == 1) {
                    tvContactDriver.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                //待退款
                tvCancelRefund.setVisibility(View.VISIBLE);
                tvRefundDetail.setVisibility(View.VISIBLE);
                break;
            case 4:
                //已退款
                tvRefundDetail.setVisibility(View.VISIBLE);
                break;
            case 8:
                //已完成
                tvApplyService.setVisibility(View.VISIBLE);
                tvComment.setVisibility(View.VISIBLE);
                tvDeleteOrder.setVisibility(View.VISIBLE);
                break;
        }
        tvOrderState2.setVisibility(View.GONE);
        //默认显示地图
        scrollView.setIsSendByMerchant(false);
        llSendByMerchant.setVisibility(View.GONE);
        llSendByMerchant.setAlpha(0);

        if (data.getStatus() == 0 || data.getStatus() == 1 || data.getStatus() == 2) {//待付款/待发货/蜂鸟或达达配送
            map.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    View infoWindow = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.layout_order_info_window, null);
                    ImageView ivHead = infoWindow.findViewById(R.id.iv_head);
                    TextView tvStatus = infoWindow.findViewById(R.id.tv_status);
                    TextView tvCountDown = infoWindow.findViewById(R.id.tv_count_down);
                    switch (data.getStatus()) {
                        case 0:
                            //待付款
                            GlideUtils.showMediumPic(OrderDetailActivity.this, ivHead, userInfo.getFace());
                            tvStatus.setText("等待支付");
                            countDown(tvCountDown);
                            break;
                        case 1:
                            //待发货
                            GlideUtils.showMediumPic(OrderDetailActivity.this, ivHead, data.getShop_logo());
                            tvStatus.setText("商家已接单");
                            tvCountDown.setVisibility(View.GONE);
                            break;
                        case 2:
                            //待收货
                            ivHead.setVisibility(View.GONE);
                            if (data.getIs_dada() == 1) {
                                tvStatus.setText(data.getDada().getStatusMsg());
                            } else {
                                tvStatus.setText("骑手正在送货");
                            }
                            tvCountDown.setVisibility(View.GONE);
                            break;
                    }
                    return infoWindow;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });
            map.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    getOrderTrack();
                }
            });
            map.moveCamera(CameraUpdateFactory.zoomTo(12));
            map.clear();//删除所有marker
            if (data.getStatus() == 0) {//待付款
                LatLng latLng = new LatLng(data.getAddress().getLat(), data.getAddress().getLng());
                map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 17, 0, 0)));
                final Marker marker = map.addMarker(new MarkerOptions().position(latLng).setInfoWindowOffset(0, DisplayUtil.dip2px(10)).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_order_marker))).setFlat(true));
                marker.showInfoWindow();
            } else if (data.getStatus() == 1) {//待发货
                LatLng latLng = new LatLng(data.getLat(), data.getLng());
                map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 17, 0, 0)));
                final Marker marker = map.addMarker(new MarkerOptions().position(latLng).setInfoWindowOffset(0, DisplayUtil.dip2px(10)).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_order_marker))).setFlat(true));
                marker.showInfoWindow();
            } else if (data.getStatus() == 2) {//待收货
                if (data.getIs_dada() == 1 || data.getIs_fengniao() == 1) {//达达配或蜂鸟配送
                    final LatLng userLatLng = new LatLng(data.getAddress().getLat(), data.getAddress().getLng());
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(userLatLng, 17, 0, 0)));
                    final View userHead = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.layout_order_head_info_window, null);
                    final ImageView ivUserHead = userHead.findViewById(R.id.iv_head);
                    markerNum = 0;
                    Glide.with(this).load(userInfo.getFace()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivUserHead.setImageBitmap(resource);
                            map.addMarker(new MarkerOptions().position(userLatLng).icon(BitmapDescriptorFactory.fromView(userHead)).setFlat(true));
                            markerNum++;
                            if (markerNum >= 2) {
                                addSendMarker(data.getIs_dada() == 1);
                            }
                        }
                    });

                    final LatLng merchantLatLng = new LatLng(data.getLat(), data.getLng());
                    final View merchantHead = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.layout_order_head_info_window, null);
                    final ImageView ivMerchantHead = merchantHead.findViewById(R.id.iv_head);
                    GlideUtils.showMediumPic(OrderDetailActivity.this, ivMerchantHead, data.getShop_logo());
                    Glide.with(this).load(data.getShop_logo()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivMerchantHead.setImageBitmap(resource);
                            map.addMarker(new MarkerOptions().position(merchantLatLng).icon(BitmapDescriptorFactory.fromView(merchantHead)).setFlat(true));
                            markerNum++;
                            if (markerNum >= 2) {
                                addSendMarker(data.getIs_dada() == 1);
                            }
                        }
                    });
                } else {
                    switchToSendByMerchantState();
                }
            }
        } else {
            switchToSendByMerchantState();
        }
        ((View) tvEstimateTime.getParent()).setVisibility(View.VISIBLE);
        tvEstimateTime.setText(data.getExpected_delivery_time());
        switch (data.getStatus()) {
            case 0:
                //待付款
                tvOrderState.setText("等待支付");
                tvOrderState2.setText("等待支付");
                tvStatusIntro.setVisibility(View.GONE);
                break;
            case 1:
                //待发货
                tvOrderState.setText("商家已接单");
                tvOrderState2.setText("商家已接单");
                tvStatusIntro.setVisibility(View.GONE);
                break;
            case 2:
                //待收货
                tvOrderState.setText("商家正在配送");
                tvOrderState2.setText("商家正在配送");
                if (data.getIs_fengniao() == 1) {
                    tvStatusIntro.setVisibility(View.VISIBLE);
                    tvStatusIntro.setText("由蜂鸟配送");
                } else if (data.getIs_dada() == 1) {
                    tvStatusIntro.setVisibility(View.VISIBLE);
                    tvStatusIntro.setText("由达达配送");
                } else {
                    tvStatusIntro.setVisibility(View.GONE);
                }
                break;
            case 3:
                //待退款
                tvOrderState.setText("订单待退款");
                tvOrderState2.setText("订单待退款");
                tvStatusIntro.setVisibility(View.VISIBLE);
                tvStatusIntro.setText("请您耐心等待商家确认");
                ((View) tvEstimateTime.getParent()).setVisibility(View.GONE);
                break;
            case 4:
                //已退款
                tvOrderState.setText("退款完成");
                tvOrderState2.setText("退款完成");
                tvStatusIntro.setVisibility(View.VISIBLE);
                tvStatusIntro.setText("3-5个工作日到账");
                ((View) tvEstimateTime.getParent()).setVisibility(View.GONE);
                break;
            case 8:
                //已完成
                tvOrderState.setText("订单已完成");
                tvOrderState2.setText("订单已完成");
                tvStatusIntro.setVisibility(View.GONE);
                ((View) tvEstimateTime.getParent()).setVisibility(View.GONE);
                break;
        }
        tvShopName.setText(data.getShop_name());
        if (data.getStatus() == 0) {
            ivChat.setVisibility(View.GONE);
            ivCallPhone.setVisibility(View.GONE);
        }
        addOrderGoods(data.getGoods());
        if (data.getExpress_price() == 0) {
            ((View) tvSendFee.getParent()).setVisibility(View.GONE);
        } else {
            tvSendFee.setText(String.format("¥%.2f", data.getExpress_price() / 100.000));
        }
        if (data.getCasing_price() == 0) {
            ((View) tvPackingFee.getParent()).setVisibility(View.GONE);
        } else {
            tvPackingFee.setText(String.format("¥ %.2f", data.getCasing_price() / 100.00));
        }
        if (data.getCoupon_money() == 0) {
            ((View) tvCoupon.getParent()).setVisibility(View.GONE);
        } else {
            tvCoupon.setText(String.format("-¥%.2f", data.getCoupon_money() / 100.00));
        }
        if (data.getUser_subsidy_money() == 0) {
            ((View) tvBonus.getParent()).setVisibility(View.GONE);
        } else {
            tvBonus.setText(String.format("-¥%.2f", data.getUser_subsidy_money() / 100.00));
        }
        if (data.getFull_money_reduce() == 0) {
            ((View) tvFullReduceFee.getParent()).setVisibility(View.GONE);
        } else {
            tvFullReduceFee.setText(String.format("-¥%.2f", data.getFull_money_reduce() / 100.00));
        }
        if (data.getRed_packet_money() == 0) {
            ((View) tvRedPacket.getParent()).setVisibility(View.GONE);
        } else {
            tvRedPacket.setText(String.format("-¥%.2f", data.getRed_packet_money() / 100.00));
        }
        tvDiscountFee.setText(String.format("-¥%.2f", data.getDiscounts_all_money() / 100.00));
        tvActualPayFee.setText(String.format("%.2f", data.getNeed_pay() / 100.00));
        if (data.getIs_fengniao() == 1 || data.getIs_dada() == 1) {
            ((View) tvDriverName.getParent()).setVisibility(View.VISIBLE);
        } else {
            ((View) tvDriverName.getParent()).setVisibility(View.GONE);
        }
        tvDriverName.setText(data.getIs_dada() == 1 ? data.getDm_name() : data.getCarrier_driver_name());
        tvSendType.setText(data.getIs_dada() == 1 ? "达达配送" : (data.getIs_fengniao() == 1 ? "蜂鸟快递" : "商家自配"));
        tvSendInfo.setText(data.getAddress().getXm() + "  " + data.getAddress().getTel());
        tvSendAddress.setText(data.getAddress().getInfo());
        tvOrderId.setText(data.getOrder_id());
        tvOrderTime.setText(DateUtil.formatDateAndTime3(data.getCreate_time()));
        tvPayType.setText(TextUtils.isEmpty(data.getPay_type()) ? "在线支付" : data.getPay_type());
    }

    /**
     * 增加配送员marker
     */
    private void addSendMarker(boolean isDaDa) {
        if (isDaDa) {
            if (data.getDada() != null && !TextUtils.isEmpty(data.getDada().getTransporterLat()) && !TextUtils.isEmpty(data.getDada().getTransporterLng())) {
                LatLng dadaLatLng = new LatLng(Double.valueOf(data.getDada().getTransporterLat()), Double.valueOf(data.getDada().getTransporterLng()));
                map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(dadaLatLng, 17, 0, 0)));
                final Marker daDaMarker = map.addMarker(new MarkerOptions().position(dadaLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_driver)).setFlat(true));
                daDaMarker.showInfoWindow();
            }
        } else {
            if (data.getFengniao() != null && data.getFengniao().size() > 0) {
                OrderDetailBean.FengNiaoBean fengNiaoBean = data.getFengniao().get(data.getFengniao().size() - 1);
                LatLng fengniaoLatLng = new LatLng(fengNiaoBean.getLat(), fengNiaoBean.getLng());
                map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(fengniaoLatLng, 17, 0, 0)));
                final Marker fengniaoMarker = map.addMarker(new MarkerOptions().position(fengniaoLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_driver)).setFlat(true));
                fengniaoMarker.showInfoWindow();
            }
        }
    }

    private void getOrderTrack() {
        LoadingDialog.showDialogForLoading(this);
        http.doPost(Api.getDefault().getOrderTrack(LoginUtil.getLoginToken(), orderId, data.getOrder_type()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                llOrderTrack.setVisibility(View.VISIBLE);
                List<OrderTrackBean> data = JSONArray.parseArray(result.getString("data"), OrderTrackBean.class);
                lstOrderTrack.clear();
                lstOrderTrack.addAll(data);
                orderTrackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void countDown(final TextView tvCountDown) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(data.getOver_order_time() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(DateUtil.getMinuteAndSecond(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                ToastUitl.showLong("支付超时，订单已自动取消");
                finish();
            }
        };
        countDownTimer.start();
    }

    ArrayList<LinearLayout> lstGoods = new ArrayList<>();

    private void addOrderGoods(List<OrderDetailBean.GoodsBean> goods) {
        if (goods == null || goods.size() == 0) {
            return;
        }
        llContainer.removeAllViews();
        lstGoods.clear();
        for (OrderDetailBean.GoodsBean good : goods) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_order_detail_goods, null);
            ImageView ivGoods = layout.findViewById(R.id.iv_goods);
            TextView tvGoodsName = layout.findViewById(R.id.tv_name);
            TextView tvAttr = layout.findViewById(R.id.tv_attr);
            TextView tvGoodsNum = layout.findViewById(R.id.tv_num);
            TextView tvGoodsPrice = layout.findViewById(R.id.tv_price);
            GlideUtils.showSmallPic(this, ivGoods, good.getPhoto());
            tvGoodsName.setText(good.getTitle());
            if (!TextUtils.isEmpty(good.getAttr_name()) || !TextUtils.isEmpty(good.getNature_name())) {
                tvAttr.setVisibility(View.VISIBLE);
                StringBuilder sb = new StringBuilder();
                if (!TextUtils.isEmpty(good.getAttr_name())) {
                    sb.append(good.getAttr_name());
                }
                if (!TextUtils.isEmpty(good.getAttr_name()) && !TextUtils.isEmpty(good.getNature_name())) {
                    sb.append("+");
                }
                if (!TextUtils.isEmpty(good.getNature_name())) {
                    sb.append(good.getNature_name());
                }
                tvAttr.setText(sb.toString());
            } else {
                tvAttr.setVisibility(View.GONE);
            }
            tvGoodsNum.setText("x" + good.getNum());
            tvGoodsPrice.setText(String.format("¥%.2f", good.getTotal_price() / 100.00));
            lstGoods.add(layout);
            llContainer.addView(layout);
        }
        if (lstGoods.size() > 3) {
            for (int i = 0; i < lstGoods.size(); i++) {
                if (i >= 3) {
                    lstGoods.get(i).setVisibility(View.GONE);
                }
            }
            View inflate = LayoutInflater.from(this).inflate(R.layout.layout_toggle_expand, null);
            final LinearLayout llToggle = inflate.findViewById(R.id.ll_toggle);
            final TextView tvToggle = inflate.findViewById(R.id.tv_toggle);
            final ImageView ivToggle = inflate.findViewById(R.id.iv_toggle);
            llToggle.setTag(false);
            llToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llToggle.setTag(!(boolean) llToggle.getTag());
                    if ((boolean) llToggle.getTag()) {//展开
                        for (int i = 0; i < lstGoods.size(); i++) {
                            if (i >= 3) {
                                lstGoods.get(i).setVisibility(View.VISIBLE);
                            }
                        }
                        tvToggle.setText("点击收起");
                        ivToggle.setImageResource(R.drawable.icon_arrow_up);
                    } else {
                        for (int i = 0; i < lstGoods.size(); i++) {
                            if (i >= 3) {
                                lstGoods.get(i).setVisibility(View.GONE);
                            }
                        }
                        tvToggle.setText("展开更多");
                        ivToggle.setImageResource(R.drawable.icon_arrow_down);
                    }
                }
            });
            llContainer.addView(inflate);
        }
    }

    @Override
    public void setListener() {
        dragExpandLayout.setOnDistanceToTopChangeListener(new CustomDragExpandLayout.OnToTopDistanceChangeListener() {
            @Override
            public void onToTopDistanceRatioChange(float ratio) {
                cover.setAlpha(1 - ratio);
                tvOrderState.setAlpha(1 - ratio);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        loadData();
    }

    private void loadData() {
        LoadingDialog.showDialogForLoading(this);
        http.doPost(Api.getDefault().getOrderDetail(LoginUtil.getLoginToken(), orderId, order_type), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                data = result.getObject("data", OrderDetailBean.class);
                updateUI(data);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.iv_back, R.id.tv_order_state, R.id.tv_order_state2, R.id.tv_shop_name, R.id.iv_refresh, R.id.cover_order_track, R.id.iv_close_order_track, R.id.iv_chat, R.id.iv_call_phone, R.id.tv_call_driver, R.id.tv_cancel_order, R.id.tv_apply_refund, R.id.tv_contact_driver, R.id.tv_cancel_refund, R.id.tv_apply_service, R.id.tv_pay_now, R.id.tv_comment, R.id.tv_confirm_receive, R.id.tv_refund_detail, R.id.tv_delete_order})
    public void onViewClicked(View view) {
        if (data == null && view.getId() != R.id.iv_back) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_shop_name:
                StoreDetailActivity.actionStart(this, data.getOrder_type() == 1, data.getShop_id());
                break;
            case R.id.iv_chat:
                chat();
                break;
            case R.id.iv_refresh:
                loadData();
                break;
            case R.id.iv_call_phone:
                callPhone(data.getTel());
                break;
            case R.id.tv_call_driver:
                callPhone(data.getIs_dada() == 1 ? data.getDm_mobile() : data.getCarrier_driver_phone());
                break;
            case R.id.tv_order_state:
            case R.id.tv_order_state2:
                getOrderTrack();
                break;
            case R.id.cover_order_track:
            case R.id.iv_close_order_track:
                llOrderTrack.setVisibility(View.GONE);
                break;
            case R.id.tv_cancel_order:
                cancelOrder();
                break;
            case R.id.tv_apply_refund:
                applyRefund();
                break;
            case R.id.tv_contact_driver:
                callPhone(data.getIs_dada() == 1 ? data.getDm_mobile() : data.getCarrier_driver_phone());
                break;
            case R.id.tv_cancel_refund:
                cancelRefund();
                break;
            case R.id.tv_apply_service:
                applyService();
                break;
            case R.id.tv_pay_now:
                pay();
                break;
            case R.id.tv_comment:
                comment();
                break;
            case R.id.tv_confirm_receive:
                confirmReceive();
                break;
            case R.id.tv_refund_detail:
                RefundProgressActivity.actionStart(mContext, data.getOrder_id(), data.getOrder_type());
                break;
            case R.id.tv_delete_order:
                deleteOrder();
                break;
        }
    }

    private void deleteOrder() {
        orderOperation(Api.getDefault().deleteOrder(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), "确认删除订单?");
    }

    private void confirmReceive() {
        orderOperation(Api.getDefault().confirmReceive(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), "确认收货?");
    }

    private void cancelRefund() {
        orderOperation(Api.getDefault().cancelRefund(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), "确认取消退款?");
    }

    private void comment() {
        if (data.getStatus() == 4) {//已退款
            Toast.makeText(mContext, "已退款不能评价", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, CommentActivity.class);
        intent.putExtra("orderId", data.getOrder_id());
        intent.putExtra("physical", data.getOrder_type() == 2);
        mContext.startActivity(intent);
    }

    private void applyService() {
        if (data.getStatus() == 4) {//已退款
            Toast.makeText(mContext, "已退款不能申请售后", Toast.LENGTH_SHORT).show();
            return;
        }
        final HashMap<String, Object> mOrderParams = new HashMap<>();
        mOrderParams.put("login_token", LoginUtil.getLoginToken());
        mOrderParams.put("order_id", data.getOrder_id());
        final List<RefundInfo> refundInfoList = new ArrayList<>();
        refundInfoList.add(new RefundInfo("操作有误（商品、地址等选错）"));
        refundInfoList.add(new RefundInfo("重复下单/误下单"));
        refundInfoList.add(new RefundInfo("其他渠道价格更低"));
        refundInfoList.add(new RefundInfo("不想买了"));
        refundInfoList.add(new RefundInfo("其他原因"));
        OptionsPickerView catePickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mOrderParams.put("message", refundInfoList.get(options1).getPickerViewText());
                mOrderParams.put("type", data.getOrder_type());
                new MyHttp().doPost(Api.getDefault().applyService(mOrderParams), new HttpListener() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                        loadData();
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
            }
        }).build();
        catePickerView.setPicker(refundInfoList);//添加数据
        catePickerView.show();
    }

    private void applyRefund() {
        orderOperation(Api.getDefault().applyRefund(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), "确认申请退款?");
    }

    private void pay() {
        Intent intent = new Intent(mContext, SubmitOrderActivity.class);
        intent.putExtra("orderId", data.getOrder_id());
        intent.putExtra("isPhysical", data.getOrder_type() == 2);
        mContext.startActivity(intent);
    }

    private void cancelOrder() {
        new AlertDialog(mContext).builder()
                .setTitle("提示")
                .setMsg("确认取消订单")
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyHttp().doPost(Api.getDefault().cancelOrder(LoginUtil.getLoginToken(), data.getOrder_id(), data.getOrder_type()), new HttpListener() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onError(int code) {

                            }
                        });
                    }
                }).show();
    }

    /**
     * 订单操作统一处理
     */
    private void orderOperation(final Observable<JSONObject> observable, String hint) {
        new AlertDialog(mContext).builder()
                .setTitle("提示")
                .setMsg(hint)
                .setNegativeButton("再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyHttp().doPost(observable, new HttpListener() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Toast.makeText(mContext, result.getString("msg"), Toast.LENGTH_SHORT).show();
                                loadData();
                            }

                            @Override
                            public void onError(int code) {

                            }
                        });
                    }
                }).show();
    }

    private void callPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("抱歉，暂未获取到联系方式");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void chat() {
        ToastUitl.showShort("抱歉，该商家暂时无法在线聊天！");
    }
}
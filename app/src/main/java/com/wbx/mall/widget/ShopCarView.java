package com.wbx.mall.widget;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.bean.ShopInfo2;
import com.wbx.mall.utils.DisplayUtil;

public class ShopCarView extends FrameLayout {
    private TextView tvEnsureOrder, tvTotalPrice;
    public ImageView iv_shop_car;
    public TextView car_badge;
    private BottomSheetBehavior behavior;
    public View shoprl;
    public int[] carLoc;
    private ShopInfo2 shopInfo;
    private TextView tvNoSelect;
    private LinearLayout llSelected;
    private TextView tvSendStartPrice;

    public void setBehavior(final BottomSheetBehavior behavior) {
        this.behavior = behavior;
    }

    public ShopCarView(@NonNull Context context) {
        super(context);
    }

    public ShopCarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (iv_shop_car == null) {
            iv_shop_car = findViewById(R.id.iv_shop_car);
            tvNoSelect = findViewById(R.id.car_nonselect);
            llSelected = findViewById(R.id.amount_container);
            car_badge = findViewById(R.id.car_badge);
            tvEnsureOrder = findViewById(R.id.btn_ensure_order);
            tvTotalPrice = findViewById(R.id.tv_total_price);
            tvSendStartPrice = findViewById(R.id.tv_send_start_price);
            shoprl = findViewById(R.id.car_rl);
            carLoc = new int[2];
            iv_shop_car.getLocationInWindow(carLoc);
            carLoc[0] = carLoc[0] + iv_shop_car.getWidth() / 2 - DisplayUtil.dip2px(10);
        }
    }

    public void setOnGoodsClickListener(OnClickListener onGoodsClickListener) {
        shoprl.setOnClickListener(onGoodsClickListener);
    }

    public void setShopInfo(ShopInfo2 shopInfo) {
        this.shopInfo = shopInfo;
        tvNoSelect.setText(String.format("购物车是空的(%.2f起送)", shopInfo.getSince_money() / 100.00));
        tvSendStartPrice.setText(String.format("(%.2f元起送,3公里内配送费%.2f元)", shopInfo.getSince_money() / 100.00, shopInfo.getLogistics() / 100.00));
    }

    public void updateAmount(float totalPrice, boolean isBook) {
        if (shopInfo == null) {
            return;
        }
        if (isBook) {
            tvSendStartPrice.setVisibility(GONE);
        } else {
            tvSendStartPrice.setVisibility(VISIBLE);
        }
        if (totalPrice == 0) {
            //未选商品
            tvNoSelect.setVisibility(View.VISIBLE);
            llSelected.setVisibility(View.GONE);
            tvEnsureOrder.setEnabled(false);
            tvEnsureOrder.setTextColor(Color.parseColor("#a8a8a8"));
            ViewCompat.setBackgroundTintList(tvEnsureOrder, getResources().getColorStateList(R.color.bg_cant_order));
            if (behavior != null) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        } else {
            if (isBook || totalPrice >= shopInfo.getSince_money() / 100.00) {
                tvNoSelect.setVisibility(View.GONE);
                llSelected.setVisibility(View.VISIBLE);
                tvEnsureOrder.setEnabled(true);
                tvEnsureOrder.setTextColor(Color.WHITE);
                tvEnsureOrder.setBackgroundTintList(null);
                ViewCompat.setBackgroundTintList(tvEnsureOrder, null);
            } else {
                //少于起送价
                tvNoSelect.setVisibility(View.GONE);
                llSelected.setVisibility(View.VISIBLE);
                tvEnsureOrder.setEnabled(false);
                tvEnsureOrder.setTextColor(Color.parseColor("#a8a8a8"));
                ViewCompat.setBackgroundTintList(tvEnsureOrder, getResources().getColorStateList(R.color.bg_cant_order));
            }
        }
        tvTotalPrice.setText(String.format("¥%.2f", totalPrice));
    }

    public void showBadge(int total) {
        if (total > 0) {
            car_badge.setVisibility(View.VISIBLE);
            car_badge.setText(total + "");
        } else {
            car_badge.setVisibility(View.INVISIBLE);
        }
    }
}

package com.wbx.mall.behaviors;


import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.wbx.mall.R;
import com.wbx.mall.utils.DisplayUtil;
import com.wbx.mall.widget.ShopInfoContainer;

import java.lang.ref.WeakReference;

public class ShopContainerBehavior extends CoordinatorLayout.Behavior<ShopInfoContainer> {
    private Context mContext;
    private int iconHeight, iconWidth;
    private int totalRange;
    private float startX, startY;
    private float distanceX, distanceY;//要移动的总长度和高度
    private View shopInfoContainer, iv_shop;
    private WeakReference<ShopInfoContainer> mContainer;
    private View iv_shop_bg, cover, tvFare;
    private int statusBarHeight;
    private int acBarHeight;
    private int shopBgMoveDistance;

    public ShopContainerBehavior() {
        super();
    }

    public ShopContainerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ShopInfoContainer child, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, child, layoutDirection);
        if (mContainer == null) {
            mContainer = new WeakReference<>(child);
            shopInfoContainer = child.findViewById(R.id.ll_shop_info);
            iv_shop = child.findViewById(R.id.iv_shop);
            iv_shop_bg = child.findViewById(R.id.iv_shop_bg);
            cover = child.findViewById(R.id.cover);
            tvFare = child.findViewById(R.id.tv_fare);
        }
        return handled;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ShopInfoContainer child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, ShopInfoContainer child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) dependency;
            totalRange = appBarLayout.getTotalScrollRange();
            if (iconHeight > 0) {
                float dTop = dependency.getTop();
                //移动的比例
                float dt = -dTop / totalRange;
                shopInfoContainer.setX(startX - distanceX * dt);
                shopInfoContainer.setY(startY - distanceY * dt);
                child.setWgAlpha(1 - dt * 2);
                //图标
                iv_shop.setY(startY + dTop / 4);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv_shop.getLayoutParams();
                lp.width = iconWidth + (int) dTop / 2;
                lp.height = iconHeight + (int) dTop / 2;
                iv_shop.setLayoutParams(lp);
                //背景
                iv_shop_bg.setTranslationY(-dt * shopBgMoveDistance);
                //遮盖层
                cover.setTranslationY(-dt * shopBgMoveDistance);
                tvFare.setTranslationY(-dt * shopBgMoveDistance);
            } else {
                shouldInitProperties();
            }
        }
        return true;
    }


    private void shouldInitProperties() {
        startX = shopInfoContainer.getX();
        startY = shopInfoContainer.getY();
        statusBarHeight = DisplayUtil.dip2px(25);
        acBarHeight = DisplayUtil.dip2px(56);
        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            acBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }
        distanceX = startX - (iv_shop.getX() + iv_shop.getWidth() / 2);
        distanceY = startY - (statusBarHeight + acBarHeight / 2 - getContainer().tvShopName.getHeight() / 2);
        iconHeight = iv_shop.getHeight();
        iconWidth = iv_shop.getWidth();
        shopBgMoveDistance = iv_shop_bg.getHeight() - statusBarHeight - acBarHeight;
    }

    private ShopInfoContainer getContainer() {
        return mContainer.get();
    }
}
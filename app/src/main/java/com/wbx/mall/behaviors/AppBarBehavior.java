package com.wbx.mall.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.wbx.mall.R;

/**
 * Created by Administrator on 2016/12/19.
 */
public final class AppBarBehavior extends AppBarLayout.Behavior {

    public AppBarBehavior() {
    }

    public AppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        //购物车详情和优惠券详情的滚动不要引起标题栏的折叠与展开
        if (target.getId() == R.id.recycler_view_shop_car_detail || target.getId() == R.id.recycler_view_shop_coupon) {
            return false;
        }
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }
}

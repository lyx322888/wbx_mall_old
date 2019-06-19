package com.wbx.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.wbx.mall.utils.DisplayUtil;

/**
 * 直接设置店铺详情商品recyclerview高度，避免初次加载时item太多导致测试太久ANR
 *
 * @author Zero
 * @date 2018/8/14
 */
public class ShopRecyclerView extends RecyclerView {
    private int goodsRecyclerViewHeight;

    public ShopRecyclerView(Context context) {
        super(context);
    }

    public ShopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int statusBarHeight = DisplayUtil.dip2px(25);
        int acBarHeight = DisplayUtil.dip2px(56);
        int tabTabHeight = DisplayUtil.dip2px(49);
        int searchHeight = DisplayUtil.dip2px(46);
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            acBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        //屏幕高度减去statusbar、ActionBar、标签页切换栏以及搜索的高度
        goodsRecyclerViewHeight = screenHeight - statusBarHeight - acBarHeight - tabTabHeight - searchHeight;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(goodsRecyclerViewHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }
}

package com.wbx.mall.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.wbx.mall.utils.DisplayUtil;


public class MaxHeightRecyclerView extends RecyclerView {
    private int max_height = 300;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        max_height = DisplayUtil.dip2px(max_height);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        final int mode = MeasureSpec.getMode(heightSpec);
        final int height = MeasureSpec.getSize(heightSpec);
        if (max_height >= 0 && (mode == MeasureSpec.UNSPECIFIED || height > max_height)) {
            heightSpec = MeasureSpec.makeMeasureSpec(max_height, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    /**
     * Sets the maximum height for this recycler view.
     */
    public void setMaxHeight(int maxHeight) {
        if (max_height != maxHeight) {
            max_height = maxHeight;
            requestLayout();
        }
    }
}

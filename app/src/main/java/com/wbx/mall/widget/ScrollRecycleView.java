package com.wbx.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollRecycleView extends RecyclerView {
    private boolean canScroll = true;

    public ScrollRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_UP) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            getParent().requestDisallowInterceptTouchEvent(canScroll);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}

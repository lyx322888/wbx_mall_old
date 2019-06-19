package com.wbx.mall.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.wbx.mall.R;
import com.wbx.mall.utils.DisplayUtil;

/**
 * @author Zero
 * @date 2018/9/11
 */
public class CustomDragExpandLayout extends FrameLayout {
    private static final int DEFAULT_EXPAND_MARGIN_TOP = DisplayUtil.dip2px(100);
    private static final int DEFAULT_SHRINK_MARGIN_TOP = DisplayUtil.dip2px(400);
    private int expandMarginTop = DEFAULT_EXPAND_MARGIN_TOP;//展开时离顶部的距离
    private int shrinkMarginTop = DEFAULT_SHRINK_MARGIN_TOP;//收缩时离顶部的距离
    private int scaledTouchSlop;//滑动的最小距离
    private float actionDownY = 0;//按下时的y坐标
    private float currentY = 0;//当前手指的y坐标
    private float viewYUnTouch = shrinkMarginTop;//未触摸时的控件y坐标
    private OnToTopDistanceChangeListener listener;

    public CustomDragExpandLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomDragExpandLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomDragExpandLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomDragExpandLayout);
            expandMarginTop = typedArray.getDimensionPixelSize(R.styleable.CustomDragExpandLayout_expand_margin_top, DEFAULT_EXPAND_MARGIN_TOP);
            shrinkMarginTop = typedArray.getDimensionPixelSize(R.styleable.CustomDragExpandLayout_shrink_margin_top, DEFAULT_SHRINK_MARGIN_TOP);
            typedArray.recycle();
        }
    }

    public int getExpandMarginTop() {
        return expandMarginTop;
    }

    public void updateActionDownY(float actionDownY) {
        this.actionDownY = actionDownY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = event.getRawY();
                float distance = currentY - actionDownY;
                if (Math.abs(distance) > scaledTouchSlop) {//大于最小滑动距离再处理滑动事件，降低消耗
                    float v = viewYUnTouch + distance;
                    if (v < expandMarginTop) {//不能超过顶部固定的位置
                        setY(expandMarginTop);
                    } else {
                        setY(v);
                    }
                }
                if (listener != null) {
                    listener.onToTopDistanceRatioChange((getY() - expandMarginTop) / (shrinkMarginTop - expandMarginTop));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getY() > expandMarginTop && getY() < shrinkMarginTop) {//松开时如果在顶部固定位置和底部固定位置之间则如果是上滑则滑动到顶部固定位置否则滑动到底部固定位置
                    float transY;
                    if (currentY - actionDownY > 0) {
                        //下滑
                        transY = shrinkMarginTop;
                    } else {
                        transY = expandMarginTop;
                    }
                    ObjectAnimator yAnimator = ObjectAnimator.ofFloat(this, "y", getY(), transY);
                    yAnimator.setDuration(300);
                    if (listener != null) {
                        yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                listener.onToTopDistanceRatioChange((getY() - expandMarginTop) / (shrinkMarginTop - expandMarginTop));
                            }
                        });
                    }
                    yAnimator.start();
                    viewYUnTouch = transY;
                } else if (getY() > shrinkMarginTop) {//超出底部固定位置则自动上移到底部固定位置
                    ObjectAnimator yAnimator = ObjectAnimator.ofFloat(this, "y", getY(), shrinkMarginTop);
                    yAnimator.setDuration(300);
                    if (listener != null) {
                        yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                listener.onToTopDistanceRatioChange((getY() - expandMarginTop) / (shrinkMarginTop - expandMarginTop));
                            }
                        });
                    }
                    yAnimator.start();
                    viewYUnTouch = shrinkMarginTop;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //默认收缩，高度需为展开时的高度，这样展开后scrollview滑动
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(DisplayUtil.getScreenHeight(getContext()) - expandMarginTop, MeasureSpec.EXACTLY));
    }

    public void setOnDistanceToTopChangeListener(OnToTopDistanceChangeListener listener) {
        this.listener = listener;
    }

    public void setMarginTop(int marginTop) {
        setY(DisplayUtil.dip2px(marginTop));
    }

    /**
     * 距离顶部固定位置的距离变化监听器
     */
    public interface OnToTopDistanceChangeListener {
        /**
         * @param ratio 距离顶部固定位置的距离与顶部固定位置到底部固定位置距离的比例
         */
        void onToTopDistanceRatioChange(float ratio);
    }
}
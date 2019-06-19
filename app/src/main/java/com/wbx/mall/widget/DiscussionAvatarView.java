package com.wbx.mall.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyphenate.util.DensityUtil;
import com.wbx.mall.R;
import com.wbx.mall.utils.GlideUtil;

import java.util.ArrayList;

public class DiscussionAvatarView extends ViewGroup {
    /**
     * 头像的半径
     */
    private int mRadius;
    /**
     * 头像间的距离
     */
    private float mSpace;
    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 是否最后一个显示完全
     */
    private boolean mIsLastComplete;
    /**
     * 最大头像数目
     */
    private int mMaxCount;
    /**
     * 当前移动的偏移量
     */
    private int mCurrentOffset;
    /**
     * 移动的属性动画
     */
    private ValueAnimator animator;
    /**
     * 是否显示动画效果
     */
    private boolean mIsShowAnimation;
    private boolean mIsShowFrame;
    private int mFrameColor;

    public DiscussionAvatarView(Context context) {
        this(context, null, 0);
    }

    public DiscussionAvatarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscussionAvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DiscussionAvatarView);
        if (array != null) {
            int radius = array.getInteger(R.styleable.DiscussionAvatarView_radiusss, 13);
            mSpace = array.getFloat(R.styleable.DiscussionAvatarView_space, (float) 0.5);
            mMaxCount = array.getInteger(R.styleable.DiscussionAvatarView_maxCount, 6);
            mIsLastComplete = array.getBoolean(R.styleable.DiscussionAvatarView_isLastComplete, true);
            mIsShowAnimation = array.getBoolean(R.styleable.DiscussionAvatarView_isShowAnimation, true);

            mRadius = DensityUtil.dip2px(context, radius);

            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heiMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int heiMode = MeasureSpec.getMode(heightMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int widMeasure = MeasureSpec.getSize(heightMeasureSpec);

        int wid = 0;
        int hei = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.width = 2 * mRadius;
            lp.height = lp.width;
            child.setLayoutParams(lp);
            // 测量子View的宽和高,系统提供的measureChild
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth();
            // 子View占据的高度
            int childHeight = child.getMeasuredHeight();

            if (i < mMaxCount) {
                if (i == 0) {
                    wid = wid + childWidth;
                } else {
                    wid = (int) (wid + childWidth * mSpace);
                }
            }
            hei = Math.max(hei, childHeight);
        }
        //如果是exactly使用测量宽和高，否则使用自己设置的宽和高
        setMeasuredDimension((widMode == MeasureSpec.EXACTLY) ? widMeasure : wid,
                (heiMode == MeasureSpec.EXACTLY) ? heiMeasure : hei);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        int left = -mCurrentOffset;
        int top = 0;
        int right = -mCurrentOffset;
        for (int i = 0; i < count; i++) {
            View child;
            if (mIsLastComplete) {
                child = getChildAt(i);
            } else {
                child = getChildAt(count - i - 1);
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (i == 0) {
                right = right + childWidth;
            } else {
                right = (int) (right + childWidth * mSpace);
            }
            child.layout(left, top, right, childHeight);
            left = (int) (left + childWidth * mSpace);

        }
    }
    public void initDatas(ArrayList<String> list) {
        if (list == null) {
            return;
        }
        removeAllViews();
        int size = list.size();
        mMaxCount = size;
        for (int i = 0; i < size; i++) {
            ImageView iv = (ImageView) mInflater.inflate(R.layout.avatar, this, false);
            if (mIsLastComplete) {
                GlideUtil.loadCircleImageView(mContext, list.get(i), iv, mIsShowFrame, mFrameColor);
            } else {
                GlideUtil.loadCircleImageView(mContext, list.get(size - i - 1), iv, mIsShowFrame, mFrameColor);
            }
            this.addView(iv);
        }
    }
    /**
     * 设置最大头像数
     *
     * @param count
     */
    public void setMaxCount(int count) {
        this.mMaxCount = count;
        int childCount = getChildCount();
        if (childCount > mMaxCount) {
            for (int i = 0; i < childCount - mMaxCount; i++) {
                if (mIsLastComplete) {
                    removeViewAt(0);
                } else {
                    int currentCount = getChildCount();
                    removeViewAt(currentCount - 1);
                }
            }
        }
    }
}

package com.wbx.mall.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author Zero
 * @date 2018/9/11
 */
public class OrderScrollView extends NestedScrollView {
    private CustomDragExpandLayout customDragExpandLayout;
    private float actionDownY = 0;//按下的y坐标
    private static final int NO_SPECIFY = 0;//未指定
    private static final int UN_DISPATCH = 1;//不指定
    private static final int DISPATCHH = 2;//指定
    private int specifyTag;//判断是否需要该事件传递给父view的标志
    private boolean cantScrollDown = true;//不能下滑的状态
    private int scaledTouchSlop;//滑动的最小距离
    private boolean isSendByMerchant = false;

    public OrderScrollView(Context context) {
        super(context);
        init(context);
    }

    public OrderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setIsSendByMerchant(boolean isSendByMerchant) {
        this.isSendByMerchant = isSendByMerchant;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (customDragExpandLayout == null && getParent() instanceof CustomDragExpandLayout) {
            customDragExpandLayout = (CustomDragExpandLayout) getParent();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSendByMerchant) {
            return super.onTouchEvent(ev);
        } else {
            if (customDragExpandLayout != null) {
                int[] positionn = new int[]{0, 0};
                getLocationOnScreen(positionn);
                if (positionn[1] > customDragExpandLayout.getExpandMarginTop()) {
                    //未滑动到顶部固定的位置则事件交由父类处理
                    dispatchToParentView(ev);
                    return true;
                }
                //滑动到顶部固定位置且下滑则事件交由父类处理
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        actionDownY = ev.getRawY();
                        specifyTag = NO_SPECIFY;
                        customDragExpandLayout.updateActionDownY(actionDownY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (specifyTag == NO_SPECIFY) {
                            if (Math.abs(ev.getRawY() - actionDownY) > scaledTouchSlop) {//判断是否开始滑动
                                if (ev.getRawY() > actionDownY && cantScrollDown) {//scrollView滑动到不能下滑的状态时用户要继续滑动则下滑父类
                                    specifyTag = DISPATCHH;
                                } else {
                                    specifyTag = UN_DISPATCH;
                                }
                            }
                        }
                        if (specifyTag == DISPATCHH) {//down事件由该view消耗掉，后续的move事件便会直接交给该view，所有需传递
                            dispatchToParentView(ev);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (specifyTag == DISPATCHH) {//down事件由该view消耗掉，后续的down事件便会直接交给该view，所有需传递
                            dispatchToParentView(ev);
                            specifyTag = NO_SPECIFY;
                            return true;
                        }
                        specifyTag = NO_SPECIFY;
                        break;
                }
                return super.onTouchEvent(ev);
            } else {
                return super.onTouchEvent(ev);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        cantScrollDown = t == 0;
    }

    private void dispatchToParentView(MotionEvent ev) {
        customDragExpandLayout.onTouchEvent(ev);
    }
}

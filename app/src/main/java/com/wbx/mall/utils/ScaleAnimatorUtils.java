package com.wbx.mall.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by wushenghui on 2017/10/17.
 */

public class ScaleAnimatorUtils {
    /**
     * 设置缩放动画
     *
     * @param view
     */
    public static void setScalse(View view) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1.2f, 1f, 0.5f, 0.7f,1f);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1.2f, 1f, 0.5f, 0.7f,1f);
        set.play(animator_x).with(animator_y);
        set.setDuration(500);
        set.start();
    }
}

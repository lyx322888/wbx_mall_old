package com.wbx.mall.widget;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.wbx.mall.R;
import com.wbx.mall.activity.LoginActivity;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.GoodsInfo2;
import com.wbx.mall.utils.SPUtils;

public class AddWidget extends FrameLayout {
    private View sub;
    private TextView tv_count;
    private int count;
    private AddButton addbutton;
    private boolean sub_anim, circle_anim;
    private GoodsInfo2 goodsInfo;

    public interface OnAddClick {

        void onAddClick(View view, GoodsInfo2 goodsInfo);

        void onSubClick(GoodsInfo2 goodsInfo);

        void onClickSpecs(GoodsInfo2 goodsInfo);
    }

    private OnAddClick onAddClick;

    public AddWidget(@NonNull Context context) {
        super(context);
    }

    public AddWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_addwidget, this);
        addbutton = findViewById(R.id.addbutton);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AddWidget);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.AddWidget_circle_anim:
                    circle_anim = a.getBoolean(R.styleable.AddWidget_circle_anim, false);
                    break;
                case R.styleable.AddWidget_sub_anim:
                    sub_anim = a.getBoolean(R.styleable.AddWidget_sub_anim, false);
                    break;
            }

        }
        a.recycle();
        sub = findViewById(R.id.iv_sub);
        tv_count = findViewById(R.id.tv_count);
        addbutton.setAnimListner(new AddButton.AnimListner() {
            @Override
            public void onStop() {
                if (!SPUtils.getSharedBooleanData(getContext(), AppConfig.LOGIN_STATE)) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra("isMainTo", true);
                    getContext().startActivity(intent);
                    return;
                }
                if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
                    if (onAddClick != null) {
                        onAddClick.onClickSpecs(goodsInfo);
                    }
                } else {
                    if (goodsInfo.getIs_seckill() == 1 && goodsInfo.getLimitations_num() != 0) {
                        //秒杀商品
                        if (count + 1 > goodsInfo.getLimitations_num()) {
                            Toast.makeText(getContext(), "不能超过限购数量", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (goodsInfo.getIs_use_num() == 1) {
                        //开启库存
                        if (count + 1 > goodsInfo.getInventory_num()) {
                            Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (count == 0) {
                        ViewAnimator.animate(sub)
                                .translationX(addbutton.getLeft() - sub.getLeft(), 0)
                                .rotation(360)
                                .alpha(0, 255)
                                .duration(300)
                                .interpolator(new DecelerateInterpolator())
                                .andAnimate(tv_count)
                                .translationX(addbutton.getLeft() - tv_count.getLeft(), 0)
                                .rotation(360)
                                .alpha(0, 255)
                                .interpolator(new DecelerateInterpolator())
                                .duration(300)
                                .start();
                    }
                    count++;
                    tv_count.setText(count + "");
                    goodsInfo.getHmBuyNum().put(goodsInfo.getGoods_id() + ",,", count);
                    if (onAddClick != null) {
                        onAddClick.onAddClick(addbutton, goodsInfo);
                    }
                }
            }
        });
        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsInfo.getIs_attr() == 1 || (goodsInfo.getNature() != null && goodsInfo.getNature().size() > 0)) {
                    if (onAddClick != null) {
                        onAddClick.onClickSpecs(goodsInfo);
                    }
                } else {
                    if (count == 0) {
                        return;
                    }
                    if (count == 1 && sub_anim) {
                        subAnim();
                    }
                    count--;
                    tv_count.setText(count + "");
                    goodsInfo.getHmBuyNum().put(goodsInfo.getGoods_id() + ",,", count);
                    if (onAddClick != null) {
                        onAddClick.onSubClick(goodsInfo);
                    }
                }
            }
        });
    }

    private void subAnim() {
        ViewAnimator.animate(sub)
                .translationX(0, addbutton.getLeft() - sub.getLeft())
                .rotation(-360)
                .alpha(255, 0)
                .duration(300)
                .interpolator(new AccelerateInterpolator())
                .andAnimate(tv_count)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        if (circle_anim) {
                            addbutton.expendAnim();
                        }
                    }
                })
                .translationX(0, addbutton.getLeft() - tv_count.getLeft())
                .rotation(-360)
                .alpha(255, 0)
                .interpolator(new AccelerateInterpolator())
                .duration(300)
                .start();
    }

    public void setData(OnAddClick onAddClick, GoodsInfo2 goodsInfo) {
        this.goodsInfo = goodsInfo;
        this.onAddClick = onAddClick;
        count = 0;
        for (String s : goodsInfo.getHmBuyNum().keySet()) {
            count += goodsInfo.getHmBuyNum().get(s);
        }
        if (count == 0) {
            sub.setAlpha(0);
            tv_count.setAlpha(0);
        } else {
            sub.setAlpha(1f);
            tv_count.setAlpha(1f);
            tv_count.setText(count + "");
        }
    }

    public void setState(long count) {
        addbutton.setState(count > 0);
    }

    public void expendAdd(int count) {
        this.count = count;
        tv_count.setText(count == 0 ? "1" : count + "");
        if (count == 0) {
            subAnim();
        }
    }

    public OnAddClick getOnAddClick() {
        return onAddClick;
    }
}

package com.wbx.mall.widget.refresh.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wbx.mall.R;


/**

 */
public class HeadRefreshView extends FrameLayout implements HeadView {

    private TextView tv;
    private ImageView arrow;
    private ProgressBar progressBar;
    private AnimationDrawable animationDrawable;

    public HeadRefreshView(Context context) {
        this(context,null);
    }

    public HeadRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_header,null);
        addView(view);
        tv = view.findViewById(R.id.header_tv);
        arrow = view.findViewById(R.id.header_arrow);
        animationDrawable = (AnimationDrawable) arrow.getDrawable();
        progressBar = view.findViewById(R.id.header_progress);
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {
        float s = progress / all;
//        if (s >= 0.9f){
//            arrow.setRotation(180);
//        }else{
//            arrow.setRotation(0);
//        }
        if (progress >= all-10){
            tv.setText("松开刷新");
        }else{
            tv.setText("下拉加载");
        }
    }

    @Override
    public void finishing(float progress, float all) {

    }

    @Override
    public void loading() {
        animationDrawable.start();
//        arrow.setVisibility(GONE);
//        progressBar.setVisibility(VISIBLE);
//        tv.setText("刷新中...");
    }

    @Override
    public void normal() {
        arrow.setVisibility(VISIBLE);
//        progressBar.setVisibility(GONE);
//        tv.setText("下拉刷新");
    }

    @Override
    public View getView() {
        return this;
    }
}

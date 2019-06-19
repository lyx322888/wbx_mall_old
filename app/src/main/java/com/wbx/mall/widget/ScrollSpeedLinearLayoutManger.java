package com.wbx.mall.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class ScrollSpeedLinearLayoutManger extends LinearLayoutManager {

    private float MILLISECONDS_PER_INCH = 0.03f;

    private Context contxt;

    public ScrollSpeedLinearLayoutManger(Context context) {

        super(context);

        this.contxt = context;

    }

    @Override

    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {

                return ScrollSpeedLinearLayoutManger.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override

            protected float calculateSpeedPerPixel

                    (DisplayMetrics displayMetrics) {

                setSpeedSlow();

                return MILLISECONDS_PER_INCH / displayMetrics.density;

            }

        };
        linearSmoothScroller.setTargetPosition(position);

        startSmoothScroll(linearSmoothScroller);
    }

    public void setSpeedSlow() {


        MILLISECONDS_PER_INCH = contxt.getResources().getDisplayMetrics().density * 3f;

    }

    public void setSpeedFast() {

        MILLISECONDS_PER_INCH = contxt.getResources().getDisplayMetrics().density * 0.03f;

    }


}

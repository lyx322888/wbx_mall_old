package com.wbx.mall.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.wbx.mall.R;

public class IndexAvatar extends AppCompatImageView {
    private float leftTopRadius;
    private float rightTopRadius;
    private float rightBottomRadius;
    private float leftBottomRadius;
    private int width;
    private int height;
    private Paint paint;

    public IndexAvatar(Context context) {
        this(context, null);
    }

    public IndexAvatar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexAvatar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexAvatar);
            rightTopRadius = typedArray.getDimension(R.styleable.IndexAvatar_right_top_radius, 0);
            leftTopRadius = typedArray.getDimension(R.styleable.IndexAvatar_left_top_radius, 0);
            rightBottomRadius = typedArray.getDimension(R.styleable.IndexAvatar_right_bottom_radius, 0);
            leftBottomRadius = typedArray.getDimension(R.styleable.IndexAvatar_left_bottom_radius, 0);
            typedArray.recycle();
        }
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.app_color));
        paint.setAlpha(180);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(leftTopRadius, 0);
        path.lineTo(width - rightTopRadius, 0);
        path.quadTo(width, 0, width, rightTopRadius);
        path.lineTo(width, height - rightBottomRadius);
        path.quadTo(width, height, width - rightBottomRadius, height);
        path.lineTo(leftBottomRadius, height);
        path.quadTo(0, height, 0, height - leftBottomRadius);
        path.lineTo(0, leftTopRadius);
        path.quadTo(0, 0, leftTopRadius, 0);
        canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.drawRect(0, height - height / 4, width, height, paint);
    }
}

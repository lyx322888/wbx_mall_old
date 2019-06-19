package com.wbx.mall.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wbx.mall.R;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class GuaGuaKa extends View {
    /**
     * 绘制线条的Paint,即用户手指绘制Path
     */
    private Paint mPaint = new Paint();
    /**
     * 记录用户绘制的Path
     */
    private Path mPath = new Path();
    /**
     * 内存中创建的Canvas
     */
    private Canvas mCanvas;
    /**
     * mCanvas绘制内容在其上
     */
    private Bitmap mBitmap;

    /**
     * ------------------------以下是奖区的一些变量
     */
    // private Bitmap mBackBitmap;
    private boolean isComplete;

    private int mLastX;
    private int mLastY;

    private OnGuaGuaCompleteListener completeListener;

    public GuaGuaKa(Context context) {
        super(context);
    }

    public GuaGuaKa(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuaGuaKa(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GuaGuaKa(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPath = new Path();
        // 设置画笔
        // mPaint.setAlpha(0);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角连接
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 线段首尾圆角突出
        // 设置画笔宽度
        mPaint.setStrokeWidth(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        // 初始化bitmap
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // 绘制遮盖层
        // mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mPaint.setStyle(Paint.Style.FILL);
//        mCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30,
//                mPaint);
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.cover_guaka), null, new RectF(0, 0, width, height), null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    /**
     * 绘制线条
     */
    private void drawPath() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx > 3 || dy > 3)
                    mPath.lineTo(x, y);

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
        }

        invalidate();
        return true;
    }

    public void setCompleteListener(OnGuaGuaCompleteListener listener) {
        this.completeListener = listener;
    }

    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {

            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap bitmap = mBitmap;

            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e("TAG", percent + "");

                if (percent > 20) {
                    Log.e("TAG", "清除区域达到20%，下面自动清除");
                    isComplete = true;
                    if (completeListener != null) {
                        completeListener.onSuccess();
                        completeListener = null;
                    }
                    postInvalidate();
                }
            }
        }

    };

    public interface OnGuaGuaCompleteListener {
        void onSuccess();
    }
}

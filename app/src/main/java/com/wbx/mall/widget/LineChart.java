package com.wbx.mall.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 折线图
 * Created by Zero on 2018/4/2 0002.
 */

public class LineChart extends View {
    /**
     * 分红
     */
    private Path shareOutBounsPath;
    /**
     * 提成
     */
    private Path widthdrawPath;
    /**
     * 指示器
     */
    private Path indexPath;
    /**
     * 坐标文字
     */
    private Paint coordinateTextPaint;
    /**
     * 背景线条
     */
    private Paint linePaint;
    /**
     * 当前选中的提成和分红金额
     */
    private Paint indexMoneyPaint;
    /**
     * 块状指示器
     */
    private Paint indexBlockPaint;
    private float[] yCoordinateText = new float[5];
    private String[] xCoordinateText = {"03-10", "03-11", "03-12", "03-13", "03-14", "03-15", "03-16"};
    private Float[] widthdrawMoney = {2000.00f, 3000.00f, 9000.00f, 8000.00f, 7000.00f, 5000.00f, 6000.00f};
    private Float[] shareOutBounsMoney = {6000.00f, 3000.00f, 8000.00f, 8000.00f, 7000.00f, 3000.00f, 7000.00f};
    private int totalWidth;
    private int totalHeight;
    private int chartHeight;
    private int chartWidth;
    private Path backgroundPath;
    private int intervalWidth;
    private int intervalHeight;
    private int[] topPosition;
    private int currentIndex = 3;
    private int[] blockSize = {20, 20};
    /**
     * 是否来自用户点击，用户只需重绘指示线和底部指示文字
     */
    private boolean isFromTouch = false;
    private DecimalFormat decimalFormat = new DecimalFormat("##0.00");

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        shareOutBounsPath = new Path();
        widthdrawPath = new Path();
        indexPath = new Path();
        backgroundPath = new Path();
        coordinateTextPaint = new Paint();
        coordinateTextPaint.setColor(Color.parseColor("#aaaaaa"));
        coordinateTextPaint.setStyle(Paint.Style.FILL);
        coordinateTextPaint.setTextSize(30);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        indexMoneyPaint = new Paint();
        indexMoneyPaint.setAntiAlias(true);
        indexMoneyPaint.setStyle(Paint.Style.FILL);
        indexMoneyPaint.setTextSize(35);
        indexMoneyPaint.setColor(Color.BLACK);
        indexBlockPaint = new Paint();
        indexBlockPaint.setStyle(Paint.Style.FILL);
        sortData();
    }

    private void sortData() {
        float min = widthdrawMoney[0];
        float max = widthdrawMoney[0];
        for (float i : widthdrawMoney) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        for (float i : shareOutBounsMoney) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        if (max == 0) {
            max = 8000.00f;
        }
        float per = (max - min) / 4;
        for (int i = 0; i < 5; i++) {
            yCoordinateText[i] = min + per * i;
        }
    }

    public void setData(List<Float> lstWidthdrawMoney, List<Float> lstShareOutBoundsMoney, List<String> lstDate) {
        widthdrawMoney = lstWidthdrawMoney.toArray(new Float[0]);
        shareOutBounsMoney = lstShareOutBoundsMoney.toArray(new Float[0]);
        xCoordinateText = lstDate.toArray(new String[0]);
        isFromTouch = false;
        sortData();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalWidth = getMeasuredWidth();
        totalHeight = getMeasuredHeight();
        chartHeight = totalHeight / 5 * 3;
        chartWidth = totalWidth / (xCoordinateText.length + 3) * xCoordinateText.length;
        intervalWidth = chartWidth / (xCoordinateText.length - 1);
        intervalHeight = chartHeight / yCoordinateText.length;
        topPosition = new int[]{intervalWidth + 50, 50};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFromTouch) {
            drawIndexLine(canvas);
            drawIndexMoney(canvas);
        } else {
            drawBackGroundLine(canvas);
            drawCoordinateText(canvas);
            drawWithdrawLine(canvas);
            drawShareOutBounsLine(canvas);
            drawIndexLine(canvas);
            drawIndexMoney(canvas);
        }
    }

    /**
     * 绘制当前选中的提成和分红金额
     *
     * @param canvas
     */
    private void drawIndexMoney(Canvas canvas) {
        int textTop = totalHeight - (totalHeight - (topPosition[1] + chartHeight)) / 2;
        int textXCenter = topPosition[0] + chartWidth / 2;
        String widthdrawText = String.format("提成¥%.2f", widthdrawMoney[currentIndex]);
        String shareOutBounsText = String.format("分红¥%.2f", shareOutBounsMoney[currentIndex]);
        Rect rect = new Rect();
        indexMoneyPaint.getTextBounds(widthdrawText, 0, widthdrawText.length(), rect);
        //绘制提成文字
        int[] widthdrawTextPosition = {textXCenter - rect.width() - 50, textTop + 30};
        canvas.drawText(widthdrawText, widthdrawTextPosition[0], widthdrawTextPosition[1], indexMoneyPaint);

        //绘制提成颜色块
        indexBlockPaint.setColor(Color.parseColor("#1fb9a8"));
        Rect widthdrawBlock = new Rect(widthdrawTextPosition[0] - blockSize[0] - 10, textTop + rect.height() / 2 - blockSize[1] / 2,
                widthdrawTextPosition[0] - 10, textTop + rect.height() / 2 + blockSize[1] / 2);
        canvas.drawRect(widthdrawBlock, indexBlockPaint);

        //绘制分红颜色块
        indexBlockPaint.setColor(Color.parseColor("#e73552"));
        Rect shareOutBounsBlock = new Rect(textXCenter + 50, textTop + rect.height() / 2 - blockSize[1] / 2,
                textXCenter + 50 + blockSize[0], textTop + rect.height() / 2 + blockSize[1] / 2);
        canvas.drawRect(shareOutBounsBlock, indexBlockPaint);

        //绘制分红文字
        canvas.drawText(shareOutBounsText, textXCenter + 50 + blockSize[0] + 10, textTop + 30, indexMoneyPaint);
    }

    /**
     * 绘制指示线条
     *
     * @param canvas
     */
    private void drawIndexLine(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#ee7c23"));
        indexPath.reset();
        indexPath.moveTo(topPosition[0] + intervalWidth * currentIndex, topPosition[1]);
        indexPath.lineTo(topPosition[0] + intervalWidth * currentIndex, topPosition[1] + chartHeight);
        canvas.drawPath(indexPath, linePaint);
    }

    /**
     * 绘制分红折线图
     *
     * @param canvas
     */
    private void drawShareOutBounsLine(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#e73552"));
        shareOutBounsPath.reset();
        float min = yCoordinateText[0];
        for (float i : yCoordinateText) {
            min = Math.min(min, i);
        }
        float moneyRange = yCoordinateText[yCoordinateText.length - 1] - yCoordinateText[0];
        int chartUsefulHeight = chartHeight / yCoordinateText.length * (yCoordinateText.length - 1);
        //每块钱所占的高度
        float heightPerMoney = moneyRange / chartUsefulHeight;
        for (int i = 0; i < shareOutBounsMoney.length; i++) {
            float money = shareOutBounsMoney[i];
            if (i == 0) {
                shareOutBounsPath.moveTo(topPosition[0], topPosition[1] + chartHeight - (money - min) / heightPerMoney);
            } else {
                shareOutBounsPath.lineTo(topPosition[0] + i * intervalWidth, topPosition[1] + chartHeight - (money - min) / heightPerMoney);
            }
        }
        canvas.drawPath(shareOutBounsPath, linePaint);
    }

    /**
     * 绘制提现折线图
     *
     * @param canvas
     */
    private void drawWithdrawLine(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#1fb9a8"));
        widthdrawPath.reset();
        float min = yCoordinateText[0];
        for (float i : yCoordinateText) {
            min = Math.min(min, i);
        }
        float moneyRange = yCoordinateText[yCoordinateText.length - 1] - yCoordinateText[0];
        int chartUsefulHeight = chartHeight / yCoordinateText.length * (yCoordinateText.length - 1);
        //每块钱所占的高度
        float heightPerMoney = moneyRange / chartUsefulHeight;
        for (int i = 0; i < widthdrawMoney.length; i++) {
            float money = widthdrawMoney[i];
            if (i == 0) {
                widthdrawPath.moveTo(topPosition[0], topPosition[1] + chartHeight - (money - min) / heightPerMoney);
            } else {
                widthdrawPath.lineTo(topPosition[0] + i * intervalWidth, topPosition[1] + chartHeight - (money - min) / heightPerMoney);
            }
        }
        canvas.drawPath(widthdrawPath, linePaint);
    }

    /**
     * 绘制x/y坐标文本
     *
     * @param canvas
     */
    private void drawCoordinateText(Canvas canvas) {
        Rect rect = new Rect();
        for (int i = 0; i < xCoordinateText.length; i++) {
            String text = xCoordinateText[i];
            coordinateTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, topPosition[0] + intervalWidth * i - rect.right / 2, topPosition[1] + chartHeight + 50, coordinateTextPaint);
        }
        for (int i = 0; i < yCoordinateText.length; i++) {
            String text = String.format("%.2f", yCoordinateText[i]);
            coordinateTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, topPosition[0] - rect.right - 50, topPosition[1] + chartHeight - intervalHeight * i - rect.bottom / 2, coordinateTextPaint);
        }
    }

    /**
     * 绘制图标背景线
     *
     * @param canvas
     */
    private void drawBackGroundLine(Canvas canvas) {
        linePaint.setColor(Color.parseColor("#ebf0f4"));
        backgroundPath.reset();
        backgroundPath.moveTo(topPosition[0], topPosition[1]);
        //绘制竖线
        for (int i = 0; i < xCoordinateText.length; i++) {
            backgroundPath.lineTo(topPosition[0] + intervalWidth * i, topPosition[1] + chartHeight);
            backgroundPath.moveTo(topPosition[0] + intervalWidth * (i + 1), topPosition[1]);
        }
        backgroundPath.moveTo(topPosition[0], topPosition[1]);
        //绘制横线
        for (int i = 0; i < yCoordinateText.length + 1; i++) {
            backgroundPath.lineTo(topPosition[0] + chartWidth, topPosition[1] + intervalHeight * i);
            backgroundPath.moveTo(topPosition[0], topPosition[1] + intervalHeight * (i + 1));
        }
        canvas.drawPath(backgroundPath, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (x > topPosition[0] && y > topPosition[1]) {
                for (int i = 0; i < xCoordinateText.length; i++) {
                    if (x >= topPosition[0] + intervalWidth * i && x < topPosition[0] + intervalWidth * (i + 1)) {
                        currentIndex = i;
                        invalidate();
                        break;
                    }
                }
            }
        }
        return true;
    }
}

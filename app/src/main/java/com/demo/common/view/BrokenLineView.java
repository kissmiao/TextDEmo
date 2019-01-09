package com.demo.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.demo.R;

/**
 * Created by Administrator on 2016/11/9.
 */
public class BrokenLineView extends View {
    private DisplayMetrics dm;
    private int mWidth;
    private int mHeight;
    private Paint linePaint;//画线的画笔
    private Paint circlePaint;
    private Paint textPaint;
    private Paint mBigCirclePaint1;
    private Paint mBigCirclePaint2;
    private Paint roundRectPaint;
    private Paint threeLinePaint;
    private Paint whiteTextPaint;
    private int mItemWidth;//屏幕宽度的1/7
    private int middleHeight;


    private final static int mCircleradius = 6;

    private float[] circleX;
    private float[] circleY;
    private int position = 0;

    public BrokenLineView(Context context) {
        super(context);
        init();
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrokenLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        dm = getResources().getDisplayMetrics();
        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels;
        mItemWidth = mWidth / 7;
        middleHeight = mHeight / 2;

        circleX = new float[]{mItemWidth, mItemWidth * 2, mItemWidth * 3,
                mItemWidth * 4, mItemWidth * 5, mItemWidth * 6};

        circleY = new float[]{middleHeight - 30, middleHeight - 40, middleHeight - 10,
                middleHeight - 40, middleHeight - 30, middleHeight - 60};


        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3);
        linePaint.setAntiAlias(true);//抗锯齿功能


        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);  //设置画笔颜色
        circlePaint.setStyle(Paint.Style.STROKE);//设置填充样式
        circlePaint.setStrokeWidth(3);//设置画笔宽度
        circlePaint.setAntiAlias(true);

        mBigCirclePaint1 = new Paint();
        mBigCirclePaint1.setColor(Color.RED);  //设置画笔颜色
        mBigCirclePaint1.setStyle(Paint.Style.STROKE);//设置填充样式
        mBigCirclePaint1.setStrokeWidth(6);//设置画笔宽度
        mBigCirclePaint1.setAntiAlias(true);

        mBigCirclePaint2 = new Paint();
        mBigCirclePaint2.setColor(getResources().getColor(R.color.pink));  //设置画笔颜色
        mBigCirclePaint2.setStyle(Paint.Style.STROKE);//设置填充样式
        mBigCirclePaint2.setStrokeWidth(6);//设置画笔宽度
        mBigCirclePaint2.setAntiAlias(true);


        textPaint = new Paint();
        textPaint.setColor(Color.RED);  //设置画笔颜色
        textPaint.setStrokeWidth(5);//设置画笔宽度
        textPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        textPaint.setTextSize(25);//设置文字大小
        textPaint.setAntiAlias(true);

        roundRectPaint = new Paint();
        roundRectPaint.setColor(Color.RED);  //设置画笔颜色
        roundRectPaint.setStrokeWidth(15);
        roundRectPaint.setStyle(Paint.Style.FILL);
        roundRectPaint.setAntiAlias(true);

        threeLinePaint = new Paint();
        threeLinePaint.setStrokeWidth(3);
        threeLinePaint.setColor(Color.RED);
        threeLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        threeLinePaint.setAntiAlias(true);//抗锯齿

        whiteTextPaint = new Paint();
        whiteTextPaint.setStrokeWidth(3);
        whiteTextPaint.setColor(Color.WHITE);
        whiteTextPaint.setTextSize(25);
        whiteTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画圆,然后画线
        canvas.drawCircle(circleX[position], circleY[position], 9, mBigCirclePaint1);
        canvas.drawCircle(circleX[position], circleY[position], 12, mBigCirclePaint2);

        canvas.drawCircle(circleX[0], circleY[0], mCircleradius, circlePaint);
        canvas.drawCircle(circleX[1], circleY[1], mCircleradius, circlePaint);
        canvas.drawCircle(circleX[2], circleY[2], mCircleradius, circlePaint);
        canvas.drawCircle(circleX[3], circleY[3], mCircleradius, circlePaint);
        canvas.drawCircle(circleX[4], circleY[4], mCircleradius, circlePaint);
        canvas.drawCircle(circleX[5], circleY[5], mCircleradius, circlePaint);

        float[] pts = {mItemWidth + 6, middleHeight - 30, mItemWidth * 2 - 6, middleHeight - 40,
                mItemWidth * 2 + 6, middleHeight - 40, mItemWidth * 3 - 6, middleHeight - 10,
                mItemWidth * 3 + 6, middleHeight - 10, mItemWidth * 4 - 6, middleHeight - 40,
                mItemWidth * 4 + 6, middleHeight - 40, mItemWidth * 5 - 6, middleHeight - 30,
                mItemWidth * 5 + 6, middleHeight - 30, mItemWidth * 6 - 6, middleHeight - 60};
        canvas.drawLines(pts, linePaint);


        int sizeWidth = (int) getTextWidth("750", 25);
        int sizeHeight = (int) getTextHeight("750", 25);
        //减去文字长度,减圆的半径圆的宽度,文字宽度
        canvas.drawText("750", mItemWidth - sizeWidth - 12 - 5, middleHeight - 150, textPaint);
        canvas.drawText("700", mItemWidth - sizeWidth - 17 - 5, middleHeight + 30, textPaint);

        //画三角形
        Path path = new Path();
        //它的左边距在第一个圆心的的左20,在圆心上15+自己三角形高度
        path.moveTo(circleX[position] - 10, circleY[position] - 15 - 10);
        //它的右边距在第一个圆心的的右边20
        path.lineTo(circleX[position] + 10, circleY[position] - 15 - 10);
        //三角形最下面的点在圆心的上面移动15
        path.lineTo(circleX[position], circleY[position] - 15);
        canvas.drawPath(path, threeLinePaint);


        //画圆角矩形
        // 参数4:circleY[0] - 15 - 10,这个是根据三角形上面那条边的一样的
        // 参数2:circleY[0] - 5 - 30 - 55,在下边的位置基础上向上55
        RectF rect = new RectF(circleX[position] - 55, circleY[position] - 15 - 10 - 55, circleX[position] + 55, circleY[position] - 15 - 10);
        canvas.drawRoundRect(rect, 10, 10, roundRectPaint);
        int startWidth = (int) (circleX[position] - sizeWidth / 2);

        int topH = (int) (circleY[position] - 15 - 10 - 55);
        int bottomH = (int) (circleY[position] - 15 - 10);

        int startHeight = ((topH) + ((bottomH) - (topH)) / 2) - sizeHeight / 2;
        canvas.drawText("109", startWidth, startHeight, whiteTextPaint);
    }

    //第一个参数是要计算的字符串，第二个参数是字提大小
    public static float getTextWidth(String text, float size) {
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(size);
        return FontPaint.measureText(text);
    }


    public static float getTextHeight(String text, float size) {
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(size);
        return FontPaint.ascent() + FontPaint.descent();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获得当前按下的 X轴坐标

                int downX = (int) ev.getRawX();
                int a = inPosition(downX);
                position = a;
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                int b = inPosition(moveX);
                position = b;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private int inPosition(int x) {
        int position = 0;
        if (x >= circleX[0] + mItemWidth / 2 && x < circleX[0] + mItemWidth / 2) {
            position = 0;
        } else if (x >= circleX[1] - mItemWidth / 2 && x < circleX[1] + mItemWidth / 2) {
            position = 1;
        } else if (x >= circleX[2] - mItemWidth / 2 && x < circleX[2] + mItemWidth / 2) {
            position = 2;
        } else if (x >= circleX[3] - mItemWidth / 2 && x < circleX[3] + mItemWidth / 2) {
            position = 3;
        } else if (x >= circleX[4] - mItemWidth / 2 && x < circleX[4] + mItemWidth / 2) {
            position = 4;
        } else if (x >= circleX[5] - mItemWidth / 2) {
            position = 5;
        }
        return position;
    }


}

package com.demo.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.demo.R;
import com.demo.common.util.UIHelper;

public class SpeedView extends View {

    private Context mContext;
    private DisplayMetrics dm;
    private int width;
    private int height;


    private Paint mArcPaint;//最上面弧线的画笔
    private Paint mMoneyPaint;
    private Paint mFlagPaint;
    private RectF mArcRect;
    private int mThemeColor;
    private int monryColor;

    private Bitmap bitmap;
    private Paint bitmapPaint;//画线的画笔


    private int moneyTextX;
    private int moneyTextY;

    private int flagX;
    private int flagY;


    private int wheelWidth;
    private int wheelHeight;


    private Matrix matrix;

    /**
     * 圆弧画笔宽度
     */
    private int arcPaintWidth = 60;
    private int moneyPaintWidth = 10;

    private double startAngle = 0d;

    private Bitmap bm1;

    public SpeedView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    public SpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

    }

    private void init() {
        initPaint();
        initRect();
    }


    private void initPaint() {

        dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;


        Log.i("LOG", "======" + width);

        mThemeColor = Color.parseColor("#2EC3FD");
        monryColor = Color.parseColor("#484848");

        //圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(mThemeColor);//画笔颜色
        mArcPaint.setAntiAlias(true);//抗锯齿
        mArcPaint.setStyle(Paint.Style.STROKE);//空心
        mArcPaint.setDither(true);//防抖动
        mArcPaint.setStrokeWidth(arcPaintWidth);//画笔宽度


        mMoneyPaint = new Paint();
        mMoneyPaint.setColor(monryColor);//画笔颜色
        mMoneyPaint.setAntiAlias(true);//抗锯齿
        mMoneyPaint.setDither(true);//防抖动
        mMoneyPaint.setStrokeWidth(moneyPaintWidth);//画笔宽度
        mMoneyPaint.setTextAlign(Paint.Align.CENTER);
        mMoneyPaint.setTextSize(UIHelper.dip2px(mContext, 20));


        mFlagPaint = new Paint();
        mFlagPaint.setColor(monryColor);//画笔颜色
        mFlagPaint.setAntiAlias(true);//抗锯齿
        mFlagPaint.setDither(true);//防抖动
        mFlagPaint.setStrokeWidth(10);//画笔宽度
        mFlagPaint.setTextAlign(Paint.Align.CENTER);
        mFlagPaint.setTextSize(UIHelper.dip2px(mContext, 10));


        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);//抗锯齿功能

    }

    private int middle;

    private void initRect() {


        mArcRect = new RectF();

        mArcRect.left = width / 4;//左边起点为屏幕宽度的1/4位置

        mArcRect.top = arcPaintWidth + UIHelper.dip2px(mContext, 50);//距离上面为一个画笔的宽度
        mArcRect.right = width - width / 4;//距离右边为屏幕的1/4
        float mArcRectBottom = width / 2 + arcPaintWidth + UIHelper.dip2px(mContext, 50);
        mArcRect.bottom = mArcRectBottom;//形成一个圆，右边减去左边就是圆的直径，再加上画笔的宽度

        middle = (int) mArcRectBottom - width / 4;

        moneyTextX = width / 2;
        moneyTextY = (int) (mArcRectBottom / 2);

        flagX = moneyTextX;
        flagY = moneyTextY;

        //  bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.jingbi);
        //  mBitWidth = bitmap.getWidth();
        //   mBitHeight = bitmap.getHeight();

        //  初始化矩阵
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        //   matrix.setRotate((float) 10, width / 2, height / 2);

        wheelWidth = width / 2;
        wheelHeight = middle;


    }


    //    mCanvas.drawBitmap(bitmap,m,bitmapPaint);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆弧
        canvas.drawArc(mArcRect, 180, 180, false, mArcPaint);



        canvas.drawText("1200" + "", moneyTextX, moneyTextY, mMoneyPaint);
        canvas.drawText("￥" + "", flagX, flagY, mFlagPaint);
        //  canvas.drawLine(width / 2-1,height / 2-1,width / 2+1,height / 2+1,mArcPaint);
        //  canvas.drawBitmap(bitmap, matrix, bitmapPaint);
        canvas.save();
        //设置旋转角度和旋转圆心
        canvas.rotate((float) startAngle, width / 2, middle);
        //前面两个是指针头，后面两个是在圆心的针尾，当mHeight/2的时候刚好是在圆心，加上的是超出的后面部分
        canvas.drawBitmap(bitmap, width / 10, width / 4 + UIHelper.dip2px(mContext, 50), bitmapPaint);

        canvas.restore();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //get the start angle for the current move event 获取当前移动事件的开始角度


                startAngle = getAngle(event.getX(), event.getY());
                Log.i("LOG", "-----startAngle------" + startAngle);
                break;

            case MotionEvent.ACTION_MOVE:
                //get the current angle for the current move event
                //    获取当前移动事件的当前角度
                double currentAngle = getAngle(event.getX(), event.getY());
                //    Log.i("LOG", "***currentAngle**" + currentAngle);
                //rotate the wheel by the difference
                Log.i("LOG", "-----startAngle------" + startAngle);
                // 设置当前角度成为下一个动作的开始角度
                startAngle = currentAngle;

                postInvalidate();

                break;


            case MotionEvent.ACTION_UP:
              /*  //get the total angle rotated in 360 degrees
                totalRotation = totalRotation % 360;

                //represent total rotation in positive value
                if (totalRotation < 0) {
                    totalRotation = 360 + totalRotation;
                }

                //calculate the no of divs the rotation has crossed
                int no_of_divs_crossed = (int) ((totalRotation) / divAngle);

                //calculate current top
                top = (divCount + top - no_of_divs_crossed) % divCount;

                //for next rotation, the initial total rotation will be the no of degrees
                // inside the current top
                totalRotation = totalRotation % divAngle;

                //snapping to the top's center
                if (snapToCenterFlag) {

                    //calculate the angle to be rotated to reach the top's center.
                    double leftover = divAngle / 2 - totalRotation;

                    rotateWheel((float) (leftover));

                    //re-initialize total rotation
                    totalRotation = divAngle / 2;
                }

                //set the currently selected option
                if (top == 0) {
                    selectedPosition = divCount - 1;//loop around the array
                } else {
                    selectedPosition = top - 1;
                }

                if (wheelChangeListener != null) {
                    wheelChangeListener.onSelectionChange(selectedPosition);
                }*/

                break;
        }

        return true;
    }

    private double getAngle(double x, double y) {
        Log.i("LOG", "============================================");
        Log.i("LOG", "wheelWidth=" + wheelWidth + "wheelHeight=" + wheelHeight);
        Log.i("LOG", "1===========" + x + "==y=" + y);
//        x = x - (wheelWidth / 2d);
//        y = wheelHeight - y - (wheelHeight / 2d);


        x = x - wheelWidth;
        y = y - wheelHeight;

        Log.i("LOG", "==x=" + x + "==y=" + y);
        Log.i("LOG", "第几象限：" + getQuadrant(x, y));
        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }


    private static int getQuadrant(double x, double y) {
        if (x >= 0) {

            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }


}

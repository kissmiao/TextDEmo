package com.commonadapter.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Calendar;

/**
 * Created by wanghongliang on 16/3/18.
 * 钟
 */
public class Clock extends View {
    private DisplayMetrics dm;
    private int mWidth;
    private int mHeight;

    private Paint paintHour;
    private Paint paintMinute;
    private Paint paintSec;

    private Canvas canvas;

    private Calendar mCalendar; //用于获取当前时间
    public static final int NEED_INVALIDATE = 0X23;
    //每隔一秒，在handler中调用一次重新绘制方法
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NEED_INVALIDATE:
                    mCalendar = Calendar.getInstance();
                    invalidate();//告诉UI主线程重新绘制
                    handler.sendEmptyMessageDelayed(NEED_INVALIDATE, 1000);
                    break;
                default:
                    break;
            }
        }
    };

    public Clock(Context context) {
        super(context);
        dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mCalendar = Calendar.getInstance();
        handler.sendEmptyMessage(NEED_INVALIDATE);//向handler发送一个消息，让它开启重绘
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Clock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建一个画笔
        Paint paintCircle = new Paint();
        paintCircle.setColor(Color.BLACK);//设置颜色
        paintCircle.setStyle(Paint.Style.STROKE);//空心||实心
        paintCircle.setAntiAlias(true);//画笔锯齿效果
        paintCircle.setStrokeWidth(5);//空心边框的宽度
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paintCircle);

        Paint paintDegree = new Paint();
        paintDegree.setColor(Color.BLACK);
        paintCircle.setStrokeWidth(3);
        canvas.save();
        canvas.rotate(6, mWidth / 2, mHeight / 2);

        for (int i = 1; i <= 60; i++) {

            if (i == 5 || i == 10 || i == 15 || i == 20 || i == 25 || i == 30 || i == 35 || i == 40 || i == 45 || i == 50 || i == 55 || i == 60) {
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                //画线 当圆在屏幕正中间的时候，线的起始位置X坐标为屏幕横方向中间，Y为屏幕竖方向的一半再减去圆的半径
                //终点X与起点相同，终点Y则是加上相应的长度，加的长度就是线的长度
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);
                //设置值
                String degree = String.valueOf(i);
                canvas.drawText(setNbumer(degree),
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 90,
                        paintDegree);
            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(15);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30,
                        paintDegree);
                String degree = String.valueOf(i);

               /* canvas.drawText(degree,
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);*/
            }
            //旋转，6度数，旋转中心坐标
            canvas.rotate(6, mWidth / 2, mHeight / 2);
        }
        //设置宽度和颜色
        paintHour = new Paint();
        //设置指针颜色
        paintHour.setColor(Color.BLACK);
        //设置指针宽度
        paintHour.setStrokeWidth(20);

        paintMinute = new Paint();
        paintMinute.setColor(Color.BLACK);
        paintMinute.setStrokeWidth(10);

        paintSec = new Paint();
        paintSec.setColor(Color.BLACK);
        paintSec.setStrokeWidth(5);

        int minute = mCalendar.get(Calendar.MINUTE);//得到当前分钟数
        int hour = mCalendar.get(Calendar.HOUR);//得到当前小时数
        int sec = mCalendar.get(Calendar.SECOND);//得到当前秒数

        float hourDegree = (hour * 60 + minute) / 12f / 60 * 360;//得到时钟旋转的角度
        canvas.save();
        //设置旋转角度和旋转圆心
        canvas.rotate(hourDegree, mWidth / 2, mHeight / 2);
        //前面两个是指针头，后面两个是在圆心的针尾，当mHeight/2的时候刚好是在圆心，加上的是超出的后面部分
        canvas.drawLine(mWidth / 2, mHeight / 2 - 200, mWidth / 2, mHeight / 2 + 20, paintHour);
        canvas.restore();

        float minuteDegree = minute / 60f * 360;//得到分针旋转的角度
        canvas.save();
        canvas.rotate(minuteDegree, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2 - 250, mWidth / 2, mHeight / 2 + 30, paintMinute);
        canvas.restore();

        float secDegree = sec / 60f * 360;//得到秒针旋转的角度
        canvas.save();
        canvas.rotate(secDegree, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2 - 270, mWidth / 2, mHeight / 2 + 40, paintSec);
        canvas.restore();

    }

    String setNbumer(String number) {
        String numbers = null;
        switch (number) {
            case "5":
                numbers = "1";
                break;
            case "10":
                numbers = "2";
                break;
            case "15":
                numbers = "3";
                break;
            case "20":
                numbers = "4";
                break;
            case "25":
                numbers = "5";
                break;
            case "30":
                numbers = "6";
                break;
            case "35":
                numbers = "7";
                break;
            case "40":
                numbers = "8";
                break;
            case "45":
                numbers = "9";
                break;
            case "50":
                numbers = "10";
                break;
            case "55":
                numbers = "11";
                break;
            case "60":
                numbers = "12";
                break;
        }
        return numbers;

    }

}
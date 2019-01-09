package com.demo.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/5.
 * <p/>
 * 用几个点生成二阶贝塞尔曲线
 * <p/>
 * 获取贝塞尔曲线上的点
 */
public class MView3 extends View {
    private Point startPoint;
    private Point endPoint;
    // 辅助点
    private Point assistPoint;
    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;
    private float a[] =new float[2];


    public MView3(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        startPoint = new Point(300, 300);
        assistPoint = new Point(100, 600);
        endPoint = new Point(300, 900);
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);


    }

    public MView3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        // 重置路径
        mPath.reset();
        // 起点
 /*       mPath.moveTo(100,300);
        mPath.quadTo(200,200,300,300);
        mPath.quadTo(400,400,500,300);*/

        mPath.moveTo(100,300);
        mPath.rQuadTo(100,-100,200,0);
        mPath.rQuadTo(100,100,200,0);

        //三节贝塞尔曲线很难预料是什么样子
     /*   mPath.moveTo(100,300);
        mPath.cubicTo(200,100,300,300,400,300);*/
        // rCubicTo

        canvas.drawPath(mPath, mPaint);

    }


}

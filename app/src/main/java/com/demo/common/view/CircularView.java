package com.demo.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by whl on 16/9/5.
 */
public class CircularView extends View {

    private Paint mArcPaint;


    public CircularView(Context context) {
        super(context);
        init();
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mArcPaint= new Paint();
        mArcPaint.setColor(Color.RED);//画笔颜色
        mArcPaint.setAntiAlias(true);//抗锯齿
        mArcPaint.setStyle(Paint.Style.STROKE);//空心
        mArcPaint.setDither(true);//防抖动
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);//在画笔的连接处是圆滑的
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);//在画笔的起始处是圆滑的
        mArcPaint.setPathEffect(new CornerPathEffect(10));//画笔效果mPaint = new Paint();



    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(200f,200f,200f,200f,0,360,true,mArcPaint);
    }
}

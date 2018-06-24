package com.commonadapter.common.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by whl on 16/6/30.
 */
public class Mview extends View {

    private Paint mPaint;
    private Path mPath;
    //一个波的波长
    private int mItemWaveLength = 400;
    private int dx;

    public Mview(Context context) {
        super(context);
    }

    public Mview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public Mview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    //rQuadTo这个是以moveTo点的坐标为相对点
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength / 2;
        //起始位置向左移动有个波长
        mPath.moveTo(-mItemWaveLength + dx, originY);

        //i<=getWidth()+mItemWaveLength   最后画的波长超过屏幕有个波长
        //Y坐标为—50，就是说以前一个点的Y坐标0为起始点，向上移动50
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }

        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }

    ValueAnimator animator;

    public void startAnim() {

        animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);//设置循环次数
        animator.setInterpolator(new LinearInterpolator());//设置动画差值器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }


    public void stop() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }
}

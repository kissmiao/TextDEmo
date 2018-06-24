package com.commonadapter.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by whl on 16/7/13.
 */
public class QQView2 extends View {

    private View mView;


    private Paint mPaint;
    private int mThemeColor;
    private RectF mArcRect;
    private int mArcCenterX;
    private int mArcCenterY;
    private int mWidth;//自定义View宽
    private int mHeight;//自定义View高
    private float mArcWidth;
    private float percent = 0;
    private int width;
    private int height;
    private DisplayMetrics dm;
    private float mRatio;

    private BarAnimation animation;

    public QQView2(Context context) {
        super(context);
        onMeasureS("构造1");
        init();
    }

    public QQView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        onMeasureS("构造2");
        init();
    }

    public QQView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onMeasureS("构造3");
        init();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onMeasureS("onFinishInflate");
    }
    @Override
    protected void onAttachedToWindow() {
        onMeasureS("onAttachedToWindow");
        super.onAttachedToWindow();

    }


    private void init() {
        mRatio = 450.f / 525.f;
        dm = getResources().getDisplayMetrics();

        width = dm.widthPixels;
        height = dm.heightPixels;

        mThemeColor = Color.parseColor("#2EC3FD");
        mPaint = new Paint();
        mPaint.setColor(mThemeColor);//画笔颜色
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        mPaint.setDither(true);//防抖动
        mPaint.setStrokeJoin(Paint.Join.ROUND);//在画笔的连接处是圆滑的
        mPaint.setStrokeCap(Paint.Cap.ROUND);//在画笔的起始处是圆滑的
        mPaint.setPathEffect(new CornerPathEffect(10));//画笔效果

    /*    ValueAnimator percentAnimator = ValueAnimator.ofFloat(0, 1);
        percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                Log.i("LOG", "===" + percent);
                postInvalidate();
            }
        });

        percentAnimator.setDuration(2000);
        percentAnimator.setStartDelay(100);
        percentAnimator.start();
*/

        startAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        onMeasureS("onSizeChanged");
        mWidth = w;
        mHeight = h;

        mArcCenterX = (int) (mWidth / 2.f);
        mArcCenterY = (int) (160.f / 525.f * mHeight);
        mArcRect = new RectF();
        mArcRect.left = mArcCenterX - 125.f / 450.f * mWidth;
        mArcRect.top = mArcCenterY - 125.f / 525.f * mHeight;
        mArcRect.right = mArcCenterX + 125.f / 450.f * mWidth;
        mArcRect.bottom = mArcCenterY + 125.f / 525.f * mHeight;
        mArcWidth = 20.f / 450.f * mWidth;
        mPaint.setStrokeWidth(mArcWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        onMeasureS("onMeasure");
        int defaultWidth = Integer.MAX_VALUE;
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //  int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //  int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = defaultWidth;
        }
        int defaultHeight = (int) (width * 1.f / mRatio);
        height = defaultHeight;
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onMeasureS("onDraw");
        //3.绘制圆弧
        canvas.drawArc(mArcRect, 120, 300 * per, false, mPaint);
    }



    public void startAnim() {
        animation = new BarAnimation();
        animation.setDuration(2000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setStartOffset(100);
        startAnimation(animation);
    }

    private float per = 0;

    /*   这是自定义动画 applyTransformation的interpolatedTime字段是从0开始增长到1,然后调用
       postInvalidate刷新界面    */
    public class BarAnimation extends Animation {

        public BarAnimation() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            per = interpolatedTime;
                postInvalidate();
           // invalidate();
        }

    }



    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        onMeasureS("onWindowFocusChanged");
    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        onMeasureS("onWindowVisibilityChanged");

    }

    @Override
    protected void onDetachedFromWindow() {
        onMeasureS("onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    private void onMeasureS(String tag) {
        Log.i("LOG", "-------"+tag + "-----measureHeight:" + getMeasuredHeight() + ";measureWidth:" + getMeasuredWidth());

    }
}

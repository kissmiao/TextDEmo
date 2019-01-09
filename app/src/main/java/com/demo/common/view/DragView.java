package com.demo.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/6/7.
 */
public class DragView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;
    private DisplayMetrics dm;
    int mCircleX;
    int mCircleY;
    float mRadius;
    int mWidth;
    int mHeight;
    RectF mArcRectf;
    private String mShowtext = "测试";
    int mShowtextSize = 50;
    Paint mCirclePaint, mArcPaint1, mArcPaint2, mTextPaint;
    //这个值是占总圆环的大小
    float mArcSize1 = 60;
    float mArcSize2 = 40;
    Context mContext;

    private Matrix matrix;
    private double totalRotation;
    private int wheelHeight, wheelWidth;

    public DragView(Context context) {
        super(context);
        init(context);
    }


    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //  初始化矩阵
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }

        dm = getResources().getDisplayMetrics();
        mScroller = new Scroller(context);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;

        mRadius = (float) (mWidth * 0.5 / 2);
        //这是控制圆环的位置


        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLACK);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mShowtextSize);

        mArcPaint1 = new Paint();
        mArcPaint1.setStyle(Paint.Style.STROKE);
        mArcPaint1.setStrokeWidth(40);
        mArcPaint1.setColor(0x880000FF);
        mArcPaint1.setAntiAlias(true);//画笔锯齿效果

        mArcPaint2 = new Paint();
        mArcPaint2.setStyle(Paint.Style.STROKE);
        mArcPaint2.setStrokeWidth(40);
        mArcPaint2.setColor(Color.BLACK);
        mArcPaint2.setAntiAlias(true);//画笔锯齿效果

      /*  wheelWidth = ((mWidth / 2) + 300) - ((mWidth / 2) - 300);
        wheelHeight = ((mHeight / 2) + 300) - ((mHeight / 2) - 300);
        Log.i("LOG", "wheelWidth=" + wheelWidth + "wheelHeight=" + wheelHeight);
*/
        startAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private double startAngle;

    private double getAngle(double x, double y) {
        x = x - (wheelWidth / 2d);
        y = wheelHeight - y - (wheelHeight / 2d);

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

    private void rotateWheel(float degrees) {
        //      DragView.this.setImageMatrix(matrix);
        DragView.this.setRotation(degrees);
        //   setRotationRight
        //add the rotation to the total rotation
        //增加旋转到总旋转
        totalRotation = totalRotation + degrees;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (wheelHeight == 0 || wheelWidth == 0) {
            Log.i("LOG", "w:" + w + "h:" + h);
            wheelWidth = w;
            wheelHeight = h;
            mCircleX =  wheelWidth/2;
            mCircleY = wheelHeight/2;

            Log.i("LOG", "mCircleX:" + mCircleX + "mCircleY" + mCircleY);
            mArcRectf = new RectF((float) mCircleX - (wheelHeight / 2), (float) mCircleY - (wheelHeight / 2), mCircleX + (wheelHeight / 2), (float) mCircleY + (wheelHeight / 2));
        }
    }


    /*
    //拖动圆,然后圆能够回来
    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = x - lastX;
                int offY = y - lastY;

                ((View) getParent()).scrollBy(-offX, -offY);
                break;

            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();
                //开启滑动,让其回到原点
                mScroller.startScroll(viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(), -viewGroup.getScrollY());
                break;
        }
        return true;
    }*/


    //该方法在onDraw()方法中会被回调
   /* public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {

            ((View) getParent()).scrollTo(mScroller.getCurrX(),
                    mScroller.getCurrY());
        }
        invalidate();//必须要调用
    }*/


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("LOG", "mCircleX+++++++++++++++" + mCircleX);
        if (per > 0f && per <= 1.0f) {
            //圆
            canvas.drawCircle(mCircleX, mCircleY, mRadius, mCirclePaint);

            // 360 * mArcSize1 / 100 这个圆环在整个圆环中的大小，  progress1这个是指当前圆环画到什么地方

            float progress1 = 360 * mArcSize1 / 100 * per;

            float progress2 = 360 * mArcSize2 / 100 * per;

            if (progress1 > 0) {
                canvas.drawArc(mArcRectf, 0, progress1, false, mArcPaint1);
                //第2个圆环以第一个圆环的结束地方为起点画环
                canvas.drawArc(mArcRectf, progress1, progress2, false, mArcPaint2);
            }
            //文字
            canvas.drawText(mShowtext, 0, mShowtext.length(), mCircleX, mCircleY, mTextPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //get the start angle for the current move event 获取当前移动事件的开始角度
                Log.i("LOG", "event.getX()" + event.getX());
                Log.i("LOG", "event.getY()" + event.getY());
                startAngle = getAngle(event.getX(), event.getY());


                break;
            case MotionEvent.ACTION_MOVE:
                //get the current angle for the current move event
                //    获取当前移动事件的当前角度
                double currentAngle = getAngle(event.getX(), event.getY());
                Log.i("LOG", "***currentAngle**" + currentAngle);
                //rotate the wheel by the difference
                rotateWheel((float) (startAngle - currentAngle));

                // 设置当前角度成为下一个动作的开始角度
                startAngle = currentAngle;
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }

    public void startAnim() {
        BarAnimation animation = new BarAnimation();
        animation.setDuration(2000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setStartOffset(100);
        startAnimation(animation);
    }

    private float per = 0;

    /*   这是自定义动画 applyTransformation的interpolatedTime字段是从0开始增长到1,然后调用
       postInvalidate刷新界面    */
    public class BarAnimation extends Animation {
        /**
         * Initializes expand collapse animation, has two types, collapse (1)
         * and expand (0).
         * <p/>
         * The view to animate
         * The type of animation: 0 will expand from gone and 0 size
         * to visible and layout size defined in xml. 1 will collapse
         * view and set to gone
         */
        public BarAnimation() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            per = interpolatedTime;
            postInvalidate();
        }

    }
}

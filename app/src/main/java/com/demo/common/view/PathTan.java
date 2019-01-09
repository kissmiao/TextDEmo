package com.demo.common.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/7/26.
 * http://blog.csdn.net/zl1911/article/details/7506589
 * http://blog.csdn.net/eclipsexys/article/details/51992473
 * http://www.cnblogs.com/xirihanlin/archive/2009/07/24/1530246.html
 * http://www.2cto.com/kf/201503/380377.html
 * http://blog.csdn.net/leehong2005/article/details/9127095
 * http://tongdandanrrr.iteye.com/blog/2018794
 */
public class PathTan extends View {

    private Path mPath;
    private Paint mPaint;
    private float[] pos;
    private float[] tan;

    float currentValue = 0;
    private PathMeasure mMeasure;


    private   float[] coords;

    public PathTan(Context context) {
        super(context);
    }

    public PathTan(Context context, AttributeSet attrs) {
        super(context, attrs);
        startAnim();
    }

    public PathTan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        mMeasure = new PathMeasure();
        mPath.addCircle(0, 0, 200, Path.Direction.CW);//一个半径为200的圆
        mMeasure.setPath(mPath, false);//将Path和PathMeasure进行绑定 false:Path最终是否需要闭合
        pos = new float[2];//{0.0,0.0}
        tan = new float[2];//{0.0,0.0}

        float[] coords = new float[] {0f, 0f};


    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LOG"," mMeasure.getLength():"+ mMeasure.getLength());
        //计算坐标
        mMeasure.getPosTan(mMeasure.getLength() * currentValue,pos ,tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // atan2 返回当前点的运动轨迹的斜率  Math.PI 圆的周长与直径之比 π
        Log.i("LOG","degrees:"+degrees);
        canvas.save();
        canvas.translate(400,400);
        canvas.drawPath(mPath,mPaint);

        canvas.drawCircle(pos[0], pos[1], 10, mPaint);  //画一个小圆
        canvas.rotate(degrees); //旋转画布
        canvas.drawLine(0, -200, 300, -200, mPaint);//画一个切线
        canvas.restore();
    }

    private void  startAnim(){

        ValueAnimator valueanim= ValueAnimator.ofFloat(0,1);
        valueanim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue=   (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueanim.setDuration(2000);
        valueanim.setInterpolator(new LinearInterpolator());
        valueanim.setRepeatCount(ValueAnimator.INFINITE);
        valueanim.start();

    }

}

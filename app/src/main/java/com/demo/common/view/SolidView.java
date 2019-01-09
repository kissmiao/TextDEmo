package com.demo.common.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.demo.R;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SolidView extends View {
    private Paint mPaint, mPaint2, mBitPaint;
    private float dx = 0;
    private int width;
    private int height;
    private DisplayMetrics dm;
    private Bitmap BmpDST;
    private Drawable drawabler;

    private int bitmapWidth;

    public SolidView(Context context) {
        super(context);
        init();
    }

    public SolidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SolidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /*    float radius：用来定义模糊半径，同样是高斯模糊算法。
        Blur style：发光样式，有内发光、外发光、和内外发光，分别对应：Blur.INNER(内发光)、Blur.SOLID(外发光)、
        Blur.NORMAL(内外发光)、Blur.OUTER(仅发光部分可见)，这几个模式，后面我们会逐个来展示用法。*/
    private void init() {
        dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;


     /*   BitmapFactory.Options options =  new  BitmapFactory.Options();
        options.inSampleSize = 2;  //图片大小是原图的1/2*/
        BmpDST = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, null);

        Matrix matrix = new Matrix();
        matrix.postScale(2.0f, 2.0f); //长和宽放大缩小的比例
        BmpDST = Bitmap.createBitmap(BmpDST, 0, 0, BmpDST.getWidth(), BmpDST.getHeight(), matrix, true);

        bitmapWidth=BmpDST.getHeight();

        //将Drawable 转 Bitmap
      /*  Drawable drawabler=getResources().getDrawable(R.mipmap.ic_launcher);
        drawabler.setBounds(50,50,50,50);
        BitmapDrawable bd = (BitmapDrawable) drawabler;
        BmpDST = bd.getBitmap();*/


        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mBitPaint = new Paint();

        mPaint2 = new Paint();
        mPaint2.setColor(Color.WHITE);
        mPaint2.setStrokeWidth(20);
        mPaint2.setTextSize(60);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LOG", "1");
        float radius = 150 * dx;
        mPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER));

       //  canvas.drawCircle(width/2, height/2, 100, mPaint);
        canvas.drawRect(width / 2 - 150, height / 2 - 150, width / 2 + 150, height / 2 + 150, mPaint);
        // canvas.drawText("SUPERCELL", width / 2 - 150, height/2, mPaint2);
        canvas.drawBitmap(BmpDST, (width  - bitmapWidth)/2, (height-bitmapWidth)/2, mBitPaint);


    }

    ValueAnimator animator;

    public void StartAnim() {
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(0);//设置循环次数,设置0则不循环，显示一遍就停止
        animator.setInterpolator(new LinearInterpolator());//设置动画差值器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                dx = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }


}

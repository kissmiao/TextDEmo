package com.demo.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.demo.R;
import com.demo.common.util.UIHelper;

public class SildeView extends View {


    private Context mContext;

    private DisplayMetrics dm;
    private int mWidth;
    private int mHeight;


    private Bitmap bitmap;
    private Paint bitmapPaint;//画线的画笔
    private Rect mDestRect;
    private int mBitWidth;
    private int mBitHeight;

    private int position;
    private int top = 30;


    private Paint linePaint;

    private Paint textPaint;

    private Path path1;
    private Path path2;


    private int lift;
    private int right;


    private int bitmapLift;
    private int bitmapRight;


    private int liftStartX;
    private int liftStopX;

    private int rightStartX;
    private int rightStopX;


    private int rightoveindex;

    private int liftMoveindex = 20;


    int money = 8888;


    public SildeView(Context context) {
        super(context);
        mContext = context;
        init();
        initLint();
    }

    public SildeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        initLint();
    }

    public SildeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initLint();
    }


    private void init() {

//30

        dm = getResources().getDisplayMetrics();
        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels;

        lift = UIHelper.dip2px(mContext, 50);
        right = mWidth - UIHelper.dip2px(mContext, 50);


        Log.i("Log", "right:============" + right);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.jingbi);
        mBitWidth = bitmap.getWidth();
        mBitHeight = bitmap.getHeight();
        mDestRect = new Rect(0, 0, mBitWidth, mBitHeight);
        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);//抗锯齿功能


        bitmapLift = lift - (mBitWidth / 2);
        bitmapRight = right - (mBitWidth / 2);

        position = bitmapLift;
    }

    private void initLint() {

        linePaint = new Paint();
        linePaint.setColor(Color.RED);  //设置画笔颜色
        linePaint.setStrokeWidth(15);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAntiAlias(true);


        textPaint = new Paint();
        textPaint.setColor(Color.RED);  //设置画笔颜色
        textPaint.setTextSize(UIHelper.sp2px(mContext, 12));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sizeWidth = (int) getTextWidth(money + "", UIHelper.sp2px(mContext, 12));


        canvas.drawText(money + "", lift - (sizeWidth / 2), 50 - liftMoveindex, textPaint);

        canvas.drawBitmap(bitmap, position, UIHelper.dip2px(mContext, top), bitmapPaint);

        canvas.drawLine(lift, 90 - liftMoveindex, lift, 120 - liftMoveindex, linePaint);

        canvas.drawLine(right, 90 - rightoveindex, right, 120 - rightoveindex, linePaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获得当前按下的 X轴坐标

                int downX = (int) ev.getRawX();
                //   int a = inPosition(downX);
                //   position = a;


                position = downX;


                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                // int b = inPosition(moveX);
                //position = b;

                if (moveX < bitmapLift) {
                    moveX = bitmapLift;
                }

                if (moveX > bitmapRight) {
                    moveX = bitmapRight;
                }

                position = moveX;

                int shiftRight = Math.abs(position - bitmapRight);

                int shiftLift = Math.abs(position - bitmapLift);

                if (shiftRight <= 200) {
                    rightoveindex = (200 - shiftRight) / 10;
                }

                if (shiftLift <= 200) {
                    liftMoveindex = (200 - shiftLift) / 10;
                }


                Log.i("Log", "position:" + position + "    lift：" + lift + "    shiftLift：===" + shiftLift);

                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
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
}

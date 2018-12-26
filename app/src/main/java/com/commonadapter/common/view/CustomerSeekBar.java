package com.commonadapter.common.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.commonadapter.R;
import com.commonadapter.common.util.UIHelper;


public class CustomerSeekBar extends View {
    private static int START_TOP = 20;
    /**
     * 竖线的高度
     */
    private static int LINE_HEIGHT = 6;
    /**
     * 竖线与顶部的距离
     */
    private static int LINE_PADDING_TOP = 5;
    /**
     * 竖线与底部的距离
     */
    private static int LINE_PADDING_BOTTOM = 6;

    private Context mContext;

    private DisplayMetrics dm;
    private int mWidth;
    private int mHeight;


    private Bitmap bitmap;
    private Paint bitmapPaint;//画线的画笔
    private int mBitWidth;
    private int mBitHeight;

    private int position = -1;
    private int top = 10;


    private Paint linePaint;
    private Paint linePaint2;

    private Paint txtPaint;


    private int left;
    private int right;

    /**
     * 金币按钮左边起始位置
     */
    private int bitmapLift;
    /**
     * 金币按钮右边起始位置
     */
    private int bitmapRight;


    private int liftStartX;
    private int liftStopX;

    private int rightStartX;
    private int rightStopX;


    private int rightoveindex;

    private int liftMoveindex = 0;
    private String floor = "1000";
    private String roof = "2500";
    private int floor_width;
    private int floor_height;
    private int roof_width;
    private int roof_height;

    private int floor_startX;
    private int floor_startY;
    private int roof_startX;
    private int roof_startY;
    private int line_startX_One;
    private int line_startY_One;
    private int line_stopY_One;
    private int line_startX_Two;
    private int line_startY_Two;
    private int line_stopY_Two;
    private int bitmap_startY;


    public CustomerSeekBar(Context context, String floor, String roof) {
        super(context);
        mContext = context;
        this.floor = floor;
        this.roof = roof;
        initPaint();
        initDistance();

    }

    public CustomerSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
        initDistance();

    }

    public CustomerSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
        initDistance();

    }


    private void initDistance() {

        dm = getResources().getDisplayMetrics();
        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels - UIHelper.dip2px(mContext, 30);

        left = UIHelper.dip2px(mContext, 55);
        right = mWidth - UIHelper.dip2px(mContext, 55);

        Log.i("Log", "right--:============" + right);

        /*计算文字位置*/
        floor_startX = left;
        floor_startY = UIHelper.dp2px(mContext, 20);
        roof_startX = right;
        roof_startY = UIHelper.dp2px(mContext, 20);
        floor_width = (int) UIHelper.getTextWidth(floor, UIHelper.sp2px(mContext, 11));//最低金额文字宽度
        floor_height = (int) UIHelper.getTextHeight(floor, UIHelper.sp2px(mContext, 11));//最低金额文字高度
        roof_width = (int) UIHelper.getTextWidth(roof, UIHelper.sp2px(mContext, 11));//最高金额文字宽度
        roof_height = (int) UIHelper.getTextHeight(roof, UIHelper.sp2px(mContext, 11));//最高金额文字高度
        /*计算竖线位置*/
        line_startX_One = left + floor_width / 2 - UIHelper.dp2px(mContext, 1);
        //  line_startY_One = floor_height + UIHelper.dp2px(mContext, START_TOP+LINE_PADDING_TOP);
        line_startY_One = floor_startY;


        line_stopY_One = floor_height + UIHelper.dp2px(mContext, START_TOP + LINE_PADDING_TOP + LINE_HEIGHT);

        line_startX_Two = right + roof_width / 2 - UIHelper.dp2px(mContext, 1);
        // line_startY_Two = roof_height + UIHelper.dp2px(mContext, START_TOP + LINE_PADDING_TOP);
        line_startY_Two = roof_startY;
        line_stopY_Two = roof_height + UIHelper.dp2px(mContext, START_TOP + LINE_PADDING_TOP + LINE_HEIGHT);


        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.jingbi);
        mBitWidth = bitmap.getWidth();
        mBitHeight = bitmap.getHeight();

        bitmapLift = left - mBitWidth / 2;
        bitmapRight = right - mBitWidth / 2;

        bitmap_startY = roof_height + UIHelper.dp2px(mContext, LINE_HEIGHT + LINE_PADDING_TOP + LINE_PADDING_BOTTOM + 20);
        position = bitmapRight;//设置小球初始位置

    }

    private void initPaint() {


        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);//抗锯齿功能

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#FF979797"));  //设置画笔颜色
        linePaint.setStrokeWidth(UIHelper.dp2px(mContext, 2));
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAntiAlias(true);


        linePaint2 = new Paint();
        linePaint2.setColor(Color.parseColor("#FF979797"));  //设置画笔颜色
        linePaint2.setStrokeWidth(UIHelper.dp2px(mContext, 2));
        linePaint2.setStyle(Paint.Style.FILL);
        linePaint2.setAntiAlias(true);

        txtPaint = new Paint();
        txtPaint.setColor(Color.parseColor("#FFC0C9D6"));
        txtPaint.setTextSize(UIHelper.sp2px(mContext, 11));
        txtPaint.setAntiAlias(true);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(floor, floor_startX, floor_startY, txtPaint);//绘制最低金额


        canvas.drawText(roof, roof_startX, roof_startY, txtPaint);//绘制最高金额

        canvas.drawLine(line_startX_One, line_startY_One - liftMoveindex, line_startX_One, line_stopY_One - liftMoveindex, linePaint);//绘制左边竖线


        canvas.drawLine(line_startX_Two, line_startY_Two - rightoveindex, line_startX_Two, line_stopY_Two - rightoveindex, linePaint);

        canvas.drawBitmap(bitmap, position, bitmap_startY, bitmapPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获得当前按下的 X轴坐标
                int downX = (int) ev.getRawX();

                if (downX < bitmapLift) {
                    downX = bitmapLift;
                }

                if (downX > bitmapRight) {
                    downX = bitmapRight;
                }
                position = downX;
                invalidate();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();

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


                Log.i("Log", "position:" + position + "    lift：" + left + "    shiftLift：===" + shiftLift);

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 设置最高金额和最低金额
     *
     * @param floor
     * @param roof
     */
    public void setMoney(int floor, int roof) {
        floor = floor;
        roof = roof;
    }
}


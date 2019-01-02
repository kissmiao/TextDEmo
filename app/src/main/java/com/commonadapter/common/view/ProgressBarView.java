package com.commonadapter.common.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.commonadapter.R;
import com.commonadapter.common.util.UIHelper;

import okhttp3.HttpUrl;

public class ProgressBarView extends View {


    private Context mContext;
    /**
     * 进度条所占用的角度
     */
    private static final int ARC_FULL_DEGREE = 180;
    //  private static final int ARC_FULL_DEGREE = 270;

    private static final int perScale = 500;
    /**
     * 弧线的宽度
     */
    private int STROKE_WIDTH;
    /**
     * 中间圆形宽高 组件的宽，高
     */
    private int sWidth, sHeight;

    // private int width, height,
    /**
     * 进度条最大值,最小值和当前进度值
     */
    private float max, small, progress;


    /**
     * 是否允许拖动进度条
     */
    private boolean draggingEnabled = false;
    /**
     * 绘制弧线的矩形区域
     */
    private RectF circleRectF, zhizhenRectF;
    /**
     * 绘制弧线的画笔
     */
    private Paint progressPaint;
    /**
     * 绘制文字的画笔
     */
    private Paint textPaint;
    /**
     * 绘制当前进度值的画笔
     */
    private Paint thumbPaint;
    /**
     * 圆弧的半径
     */
    private int circleRadius;
    /**
     * 圆弧圆心位置
     */
    private int centerX, centerY;
    private int upBtCenterX, upBtCenterY, downBtCenterx, downBtCenterY;//控制按钮的坐标
    private Bitmap zhizhen;
    private Matrix matrix;//矩阵--控制指针图片的动画


    private Matrix matrix2;
    /**
     * 指针圆心
     */
//    private float circleRectFCenterWidth;
//    private float circleRectFCenterHeight;
    /**
     * 圆弧上渐变色的颜色值
     */
    private final int[] colors = {Color.parseColor("#FFF68F"), Color.parseColor("#FFE700"), Color.parseColor("#FFD700"), Color.parseColor("#FFC700"), Color.parseColor("#FFB700"), Color.parseColor("#FFA700"), Color.parseColor("#FF9700"), Color.parseColor("#FF7F00")};
    private float[] position;
    private int buttonRadius;
    private float newProgress;

    private Bitmap bitmap;

    private Paint textRangePaint;

    private Paint moneyFlag;


    private int textSize;
    private int moneyTextSize;

    private int marginTop;


    private String currentSelectedMoney = null;

    private boolean isShowToMoney = false;


    public interface onMoneyClickListener {
        void onMoneyListener(String money);

    }

    public onMoneyClickListener mOnMoneyClickListener;

    public void setDraggingEnabled(boolean draggingEnabled) {
        this.draggingEnabled = draggingEnabled;
    }

    public void setMoneyClickListener(onMoneyClickListener onMoneyClickListener) {
        mOnMoneyClickListener = onMoneyClickListener;
    }


    public ProgressBarView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    private void init() {
        textSize = UIHelper.sp2px(mContext, 12);
        moneyTextSize = UIHelper.sp2px(mContext, 30);

        marginTop = UIHelper.Dp2Px(mContext, 5);

        position = new float[colors.length];
        zhizhen = BitmapFactory.decodeResource(getResources(), R.mipmap.zhizhen1);
        matrix = new Matrix();
        matrix2 = new Matrix();
        Log.e("init", "测试1");
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);


        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(moneyTextSize);


        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow);
        //使用自定义字体  
//        textPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fangz.ttf"));

        textRangePaint = new Paint();
        textRangePaint.setColor(Color.parseColor("#C0C9D6"));
        textRangePaint.setAntiAlias(true);
        textRangePaint.setTextSize(UIHelper.sp2px(mContext, 12));

        moneyFlag = new Paint();
        moneyFlag.setColor(Color.BLACK);
        moneyFlag.setAntiAlias(true);
        moneyFlag.setTextSize(textSize);


    }

    public float getProgress() {
        return progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (sWidth == 0 || sHeight == 0) {
            sWidth = MeasureSpec.getSize(widthMeasureSpec);

            Log.i("LOG", "sWidth=======" + sWidth);
            sHeight = MeasureSpec.getSize(heightMeasureSpec);
            //  width = zhizhen.getWidth();
            //  height = zhizhen.getHeight();


            //   System.out.println("width=" + width + " ; height=" + height);

            //计算圆弧半径和圆心点  
            //  circleRadius = Math.min(width, height)*2/3 ;

            //圆弧半径为屏幕宽度的1/4
            circleRadius = sWidth / 4;

            Log.i("LOG", "circleRadius=======" + circleRadius);
            //圆弧的宽度为13dp
            STROKE_WIDTH = UIHelper.dip2px(mContext, 13);  //圆弧描边的宽度


            Log.i("LOG", "STROKE_WIDTH=======" + STROKE_WIDTH);
            //  circleRadius -= STROKE_WIDTH;


            centerX = sWidth / 2;
            centerY = centerX - 100;//向上移动一百
            // ratio = height/width;

            //圆弧所在矩形区域  
            circleRectF = new RectF();
            circleRectF.left = centerX - circleRadius;
            circleRectF.top = centerY - circleRadius;
            circleRectF.right = centerX + circleRadius;
            circleRectF.bottom = centerY + circleRadius;


            Log.i("LOG", " circleRectF.left=======" + circleRectF.left);
            //指针所在区域
            zhizhenRectF = new RectF();
            zhizhenRectF.left = centerX - circleRadius / 1.6f;
            zhizhenRectF.top = centerY - circleRadius / 1.6f;
            zhizhenRectF.right = centerX + circleRadius / 1.6f;
            zhizhenRectF.bottom = centerY + circleRadius / 1.6f;

            //   circleRectFCenterWidth = (circleRectF.right + circleRectF.left) / 2;
            //  circleRectFCenterHeight = (circleRectF.bottom + circleRectF.top) / 2;

        }
    }


    private Rect textBounds = new Rect();


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //    float start = 90 + ((360 - ARC_FULL_DEGREE) >> 1); //进度条起始点
        float start = 180;
        float sweep1 = ARC_FULL_DEGREE * (progress / max); //进度划过的角度
        float sweep2 = ARC_FULL_DEGREE - sweep1; //剩余的角度
        //滑动度数  90
        float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);

        //计算在圆弧上的坐标
        float thumbX = centerX - circleRadius * (float) Math.sin(progressRadians);
        float thumbY = centerY + circleRadius * (float) Math.cos(progressRadians);

        //绘制起始位置小圆形  
        progressPaint.setColor(Color.WHITE);
        progressPaint.setStrokeWidth(0);
        progressPaint.setStyle(Paint.Style.FILL);
        float radians = (float) (((360.0f - ARC_FULL_DEGREE) / 2) / 180 * Math.PI);
        float startX = centerX - circleRadius * (float) Math.sin(radians);
        float startY = centerY + circleRadius * (float) Math.cos(radians);
        System.out.println("startX=" + startX + ";startY=" + startY);
        canvas.drawCircle(startX, startY, STROKE_WIDTH / 2, progressPaint);

        progressPaint.setStrokeWidth(STROKE_WIDTH);
        progressPaint.setStyle(Paint.Style.STROKE);//设置空心
        progressPaint.setColor(Color.parseColor("#31BAA4"));
        canvas.drawArc(circleRectF, start, sweep1, false, progressPaint);


        //红色绘制进度条背景
        //   linearGradient = null;
        progressPaint.setShader(null);
        progressPaint.setColor(Color.parseColor("#C6EFE8"));
        canvas.drawArc(circleRectF, start + sweep1, sweep2, false, progressPaint);


        String textMoneyFlag = "￥";
        float textMoneyFlagWidth = UIHelper.getTextWidth(textMoneyFlag, textSize);

        float proportion = progress / max;//在总大小中计算比例

        float disparity = max - small;//计算弧线所代表的长度

        float count = disparity * proportion + small;//计算弧线每个点所在1000-2000中所对应的值


        //如在范围 1000到2000中，每500一个刻度，  1000-1250 对应显示1000，  1250-1750对应显示1500，1750-2000对应显示2000
        int currentIndex = (int) (count / perScale);
        float left = Math.abs(count - currentIndex * perScale);
        float right = Math.abs(count - (currentIndex + 1) * perScale);
        if (left > right) {
            float currentProgress = (currentIndex + 1) * perScale;
            currentSelectedMoney = (int) (currentProgress) + "";
        } else {
            float currentProgress = currentIndex * perScale;
            currentSelectedMoney = (int) (currentProgress) + "";
        }

        if (!isShowToMoney) {
            currentSelectedMoney = (int) max + "";
        }

        if (mOnMoneyClickListener != null) {
            mOnMoneyClickListener.onMoneyListener(currentSelectedMoney);
        }
        float moneyTextHeight = UIHelper.getTextHeight(currentSelectedMoney, moneyTextSize);
        float moneyTextWidtht = UIHelper.getTextWidth(currentSelectedMoney, moneyTextSize);

        int margin = UIHelper.dip2px(mContext, 3);
        float flagex = centerX - (textMoneyFlagWidth + moneyTextWidtht) / 2 - margin;
        canvas.drawText(textMoneyFlag, flagex, centerY - UIHelper.dp2px(mContext, 20), moneyFlag);
        canvas.drawText(currentSelectedMoney, centerX - moneyTextWidtht / 2 + margin, centerY - UIHelper.dp2px(mContext, 20), textPaint);


        String cycleText = "借款周期: 7天";
        float cycleTextWidtht = UIHelper.getTextWidth(cycleText, textSize);
        canvas.drawText("借款周期: 7天", centerX - cycleTextWidtht / 2, centerY + 10, textRangePaint);


        if (isShowToMoney) {

            matrix2.reset();
            matrix2.postTranslate(centerX - circleRadius - bitmap.getWidth()-UIHelper.dp2px(mContext,2), centerY +bitmap.getHeight()/2);

            matrix2.preRotate(-89);
            int r = (int) (progressRadians * (180 / Math.PI)) + 269;
            matrix2.postRotate((float) (r), centerX, centerY);
            canvas.drawBitmap(bitmap, matrix2, progressPaint);


            float textHeight = UIHelper.getTextHeight("0", textSize);
            float smallTextWidth = UIHelper.getTextWidth((int) small + "", UIHelper.sp2px(mContext, 12));
            float maxTextWidth = UIHelper.getTextWidth((int) max + "", UIHelper.sp2px(mContext, 12));

            canvas.drawText((int) small + "", centerX - circleRadius - smallTextWidth / 2, centerY - textHeight + marginTop, textRangePaint);
            canvas.drawText((int) max + "", centerX + circleRadius - maxTextWidth / 2, centerY - textHeight + marginTop, textRangePaint);

        }


    }


    private boolean isDragging = false;


    public boolean onTouchEvent(@NonNull MotionEvent event) {

        //只有一个money不让点击
        if (!draggingEnabled || !isShowToMoney) {
            return super.onTouchEvent(event);
        }
        Log.e("onTouchEvent", "测试3");

        //处理拖动事件  
        float currentX = event.getX();
        float currentY = event.getY();


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //判断是否在进度条thumb位置  
                if (checkOnArc(currentX, currentY)) {
                    newProgress = calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max;
                    setProgressSync(newProgress);
                    isDragging = true;
                } else if (checkOnButtonUp(currentX, currentY)) {
                    // TODO Auto-generated method stub
                    setProgress(progress + 10);
                    isDragging = false;
                } else if (checkOnButtonDwon(currentX, currentY)) {
                    setProgress(progress - 10);
                    isDragging = false;
                }

                break;


            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    //判断拖动时是否移出去了  
                    if (checkOnArc(currentX, currentY)) {
                        setProgressSync(calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max);
                    } else {
                        isDragging = false;
                    }
                }
                break;


            case MotionEvent.ACTION_UP:
                isDragging = false;
                break;
        }


        return true;
    }


    private float calDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private boolean checkOnButtonUp(float currentX, float currentY) {
        float distance = calDistance(currentX, currentY, upBtCenterX, upBtCenterY);
        return distance < 1.5 * buttonRadius;
    }

    private boolean checkOnButtonDwon(float currentX, float currentY) {
        float distance = calDistance(currentX, currentY, downBtCenterx, downBtCenterY);
        return distance < 1.5 * buttonRadius;
    }

    /**
     * 判断该点是否在弧线上（附近）
     * 两点间的距离在大于弧线半径-5小于弧线半径+图片高度+5
     * (degree >= -8 && degree <= ARC_FULL_DEGREE + 8) 旋转的角度在-8度到188度
     */
    private boolean checkOnArc(float currentX, float currentY) {

        float distance = calDistance(currentX, currentY, centerX, centerY);
        float degree = calDegreeByPosition(currentX, currentY);
        return distance >circleRadius-bitmap.getHeight() && distance < circleRadius+bitmap.getHeight()
                && (degree >= -8 && degree <= ARC_FULL_DEGREE + 8);
    }

    /**
     * 根据当前位置，计算出进度条已经转过的角度。
     */
    private float calDegreeByPosition(float currentX, float currentY) {
        float a1 = (float) (Math.atan(1.0f * (centerX - currentX) / (currentY - centerY)) / Math.PI * 180);
        if (currentY < centerY) {
            a1 += 180;
        } else if (currentY > centerY && currentX > centerX) {
            a1 += 360;
        }

        return a1 - (360 - ARC_FULL_DEGREE) / 2;
    }


    public void setMoney(int small, int max) {
        this.max = max;
        this.small = small;
        progress = max;
        if (max == small) {
            isShowToMoney = false;
        } else {
            isShowToMoney = true;
        }
        invalidate();
    }


    public void setProgress(float progress) {
        final float validProgress = checkProgress(progress);
        //动画切换进度值  
        new Thread(new Runnable() {
            @Override
            public void run() {
                float oldProgress = ProgressBarView.this.progress;
                for (int i = 1; i <= 100; i++) {
                    ProgressBarView.this.progress = oldProgress + (validProgress - oldProgress) * (1.0f * i / 100);
                    postInvalidate();
                    SystemClock.sleep(3);
                }
            }
        }).start();
    }

    public void setProgressSync(float progress) {
        this.progress = checkProgress(progress);
        invalidate();
    }


    //保证progress的值位于[0,max]  
    private float checkProgress(float progress) {
        if (progress < 0) {
            return 0;
        }
        return progress > max ? max : progress;
    }


}
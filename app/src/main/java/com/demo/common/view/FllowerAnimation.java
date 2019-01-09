package com.demo.common.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.demo.R;
import com.demo.common.bean.Fllower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/26.
 * http://blog.csdn.net/zhangli_/article/details/50706167
 * http://www.jianshu.com/p/610a46fabd1d
 * http://blog.csdn.net/tianjian4592/article/details/47067161
 */
public class FllowerAnimation extends View implements ValueAnimator.AnimatorUpdateListener {

    /**
     * 动画改变的属性值
     */
    private float phase1 = 0f;
    private float phase2 = 0f;
    private float phase3 = 0f;

    /**
     * 小球集合
     */
    private List<Fllower> fllowers1 = new ArrayList<Fllower>();


    /**
     * 动画播放的时间
     */
    private int time = 4000;
    /**
     * 动画间隔
     */
    private int delay = 500;


    public int startX = 100;
    public int startY = 500;

    public void setStartX(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public FllowerAnimation(Context context,int startX, int startY) {
        super(context);
        setStartX(startX,startY);
        init(context);
    }
    public FllowerAnimation(Context context) {
        super(context);

    }

    public FllowerAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FllowerAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        //  height = (int) (wm.getDefaultDisplay().getHeight() * 3 / 2f);
        //  height =wm.getDefaultDisplay().getHeight()*4/5;
        height = startY;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);


        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(2);
        mPaint2.setColor(Color.RED);
        mPaint2.setStyle(Paint.Style.STROKE);


        pathMeasure = new PathMeasure();

        builderFollower(fllowerCount, fllowers1);
    }

    /**
     * 宽度
     */
    private int width = 0;
    /**
     * 高度
     */
    private int height = 0;

    /**
     * 曲线高度个数分割
     */
    private int quadCount = 10;
    /**
     * 曲度
     */
    private float intensity = 0.2f;

    /**
     * 第一批个数
     */
    private int fllowerCount = 1;

    /**
     * 创建花
     */
    private void builderFollower(int count, List<Fllower> fllowers) {
      /*  int max = (int) (width * 3 / 4f);
        int min = (int) (width / 4f);*/

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            //      int s = random.nextInt(max) % (max - min + 1) + min;//产生随机数控制起始点的X坐标
            int s=startX;
            Path path = new Path();
            CPoint CPoint = new CPoint(s, 0);//用S确定爱心的其起始位置
            List<CPoint> points = builderPath(CPoint);
            drawFllowerPath(path, points);
            Fllower fllower = new Fllower();
            fllower.setPath(path);
            fllowers.add(fllower);
        }

    }

    /**
     * 画曲线
     *
     * @param path
     * @param points
     */

    private void drawFllowerPath(Path path, List<CPoint> points) {
        if (points.size() > 1) {
            for (int j = 0; j < points.size(); j++) {

                CPoint point = points.get(j);

                if (j == 0) {
                    CPoint next = points.get(j + 1);
                    point.dx = ((next.x - point.x) * intensity);
                    point.dy = ((next.y - point.y) * intensity);
                } else if (j == points.size() - 1) {
                    CPoint prev = points.get(j - 1);
                    point.dx = ((point.x - prev.x) * intensity);
                    point.dy = ((point.y - prev.y) * intensity);
                } else {
                    CPoint next = points.get(j + 1);
                    CPoint prev = points.get(j - 1);
                    point.dx = ((next.x - prev.x) * intensity);
                    point.dy = ((next.y - prev.y) * intensity);
                }

                if (j == 0) {
                    path.moveTo(point.x, point.y);
                } else {
                    CPoint prev = points.get(j - 1);
                    path.cubicTo(prev.x + prev.dx, (prev.y + prev.dy),
                            point.x - point.dx, (point.y - point.dy),
                            point.x, point.y);
                }

            }
        }
    }


    /**
     * 曲线摇摆的幅度
     * applyDimension:把对应的值转化为实际屏幕上的点值，也就是像素值
     */
    private int range = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

    /**
     * 画路径
     *
     * @param point
     * @return
     */
    private List<CPoint> builderPath(CPoint point) {
        List<CPoint> points = new ArrayList<CPoint>();
        Random random = new Random();
        for (int i = 0; i < quadCount; i++) {
            if (i == 0) {
                points.add(point);
            } else if (i == 9) {
                CPoint tmp = new CPoint(0, 0);
                tmp.x = startX;
                tmp.y = height;
                points.add(tmp);
            } else {
                CPoint tmp = new CPoint(0, 0);
                //random.nextInt(100)
                if (i % 2 == 0) { //产生一个0-100的随机数，
                    tmp.x = point.x + random.nextInt(range);
                } else {
                    tmp.x = point.x - random.nextInt(range);
                }
                tmp.y = (int) (height / (float) quadCount * i);

                points.add(tmp);
            }
        }
        return points;
    }

    /**
     * 画笔
     */
    private Paint mPaint, mPaint2;

    /**
     * 测量路径的坐标位置
     */
    private PathMeasure pathMeasure = null;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFllower(canvas, fllowers1);
    }


    /**
     * @param canvas
     * @param fllowers
     */
    private void drawFllower(Canvas canvas, List<Fllower> fllowers) {
        for (Fllower fllower : fllowers) {
            float[] pos = new float[2];
            //这个打开就能看到画出出来的路径
           //     canvas.drawPath(fllower.getPath(), mPaint);//根据之前的path画路径
            pathMeasure.setPath(fllower.getPath(), false);
            pathMeasure.getPosTan(pathMeasure.getLength() * fllower.getValue(), pos, null);


            //画圆是让这个点为圆心开始画，画矩形是让这个点作为左上角
            Bitmap bitmap = null;
            bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.click_a_like_on)).getBitmap();
            int bitheight=  bitmap.getHeight()/2;
            int bitwidth= bitmap.getWidth()/2;
            canvas.drawBitmap(bitmap, pos[0]-bitwidth, pos[1]-bitheight, null);
            //canvas.drawCircle(pos[0], pos[1], 20, mPaint2);//draw圆
        }
    }

    /**
     * 调节1.2f这个值会让花的起始点不一样 ，越设置的大出现的越慢
     */

    public void startAnimation() {
        ObjectAnimator mAnimator1 = ObjectAnimator.ofFloat(this, "phase1", 1f, 0);
        mAnimator1.setDuration(time);
        mAnimator1.addUpdateListener(this);
        mAnimator1.start();
        mAnimator1.setInterpolator(new LinearInterpolator());

    }


    /**
     * 跟新小球的位置
     *
     * @param value
     * @param fllowers
     */
    private void updateValue(float value, List<Fllower> fllowers) {
        for (Fllower fllower : fllowers) {
            fllower.setValue(value);
        }
    }

    /**
     * 动画改变回调  在这里只改变属性，在onDraw中沿着路径走
     */
    @Override
    public void onAnimationUpdate(ValueAnimator arg0) {
        //得到一个从0f到1f的一个值
        updateValue(getPhase1(), fllowers1);

        this.setAlpha((float)arg0.getAnimatedValue());
        invalidate();
    }

    public float getPhase1() {
        return phase1;
    }

    public void setPhase1(float phase1) {
        this.phase1 = phase1;
    }

    public float getPhase2() {
        return phase2;
    }

    public void setPhase2(float phase2) {
        this.phase2 = phase2;
    }

    public float getPhase3() {
        return phase3;
    }

    public void setPhase3(float phase3) {
        this.phase3 = phase3;
    }

    private String tag = this.getClass().getSimpleName();

    private class CPoint {

        public float x = 0f;
        public float y = 0f;

        /**
         * x-axis distance
         */
        public float dx = 0f;

        /**
         * y-axis distance
         */
        public float dy = 0f;

        public CPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}

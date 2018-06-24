package com.commonadapter.common.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MViewGroup2 extends ViewGroup {
    private int leftBorder;
    private int rightBorder;

    private float mLastXIntercept = 0;
    private float mLastYIntercept = 0;

    private float mLastX=0;
    private float mLastY=0;

    private int mTouchSlop;

    private Scroller mScroller;

    public MViewGroup2(Context context) {
        super(context);  init(context);
        init(context);
    }

    public MViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            scrollTo(currX, currY);
            postInvalidate();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    private void init(Context context){
        ViewConfiguration configuration = ViewConfiguration.get(context);

        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mScroller = new Scroller(context);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       /* int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int cl = lp.leftMargin;
            int ct = lp.topMargin;
            int cr = cl + childWidth;
            int cb = ct + childHeight;
            child.layout(cl, ct, cr, cb);
        }*/

        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 在水平方向上对子控件进行布局
                childView.layout(i * getMeasuredWidth(), 0, i * getMeasuredWidth() + childView.getMeasuredWidth() + getPaddingLeft(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = 0;
            rightBorder = getChildCount() * getMeasuredWidth();
        }
    }


   @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float xIntercept = ev.getX();
        float yIntercept = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = xIntercept - mLastXIntercept;
                float y = yIntercept - mLastYIntercept;
                if(Math.abs(x)>Math.abs(y)&& Math.abs(x)>mTouchSlop){
                    intercept = true;
                }else {

                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
              intercept = false;
                break;
            default:
                break;
        }
        mLastX = xIntercept;
        mLastY = yIntercept;
        mLastXIntercept = xIntercept;
        mLastYIntercept = yIntercept;
        return intercept;
    }

    /**
     * 滑动过程中getScrollX()是不断累加的
     * @param event
     * @return
     */



/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xTouch-mLastX;
                float deltaY = yTouch-mLastY;
                float scrollByStart = deltaX;
                if (getScrollX() - deltaX < leftBorder) {
                    scrollByStart = getScrollX()-leftBorder;
                } else if (getScrollX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart = rightBorder-getWidth()-getScrollX();
                }
                scrollBy((int) -scrollByStart, 0);
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                scrollTo(getScrollX()+dx,0);
                break;
            default:
                break;
        }
        mLastX = xTouch;
        mLastY = yTouch;
        return super.onTouchEvent(event);
    }*/


/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //控制手指拖动的时候位置变化
                float deltaX = xTouch-mLastX;
                float deltaY = yTouch-mLastY;
                float scrollByStart = deltaX;
                if (getScrollX() - deltaX < leftBorder) {
                    scrollByStart = getScrollX()-leftBorder;
                } else if (getScrollX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart = rightBorder-getWidth()-getScrollX();
                }
                scrollBy((int) -scrollByStart, 0);
                break;

            case MotionEvent.ACTION_UP:
                //控制手指放开时剩下的View位置移动
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，使用startScroll方法，对其进行初始化
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
            default:
                break;
        }
        mLastX = xTouch;
        mLastY = yTouch;
        return super.onTouchEvent(event);
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xTouch = event.getX();
        float yTouch = event.getY();

        Log.i("LOG","event.getX"+event.getX());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xTouch-mLastX;
                float deltaY = yTouch-mLastY;
                float scrollByStart = deltaX;
                //如果超出边界，则把滑动距离缩小到1/3
                Log.i("LOG","getScrollX()"+getScrollX());
                if (getScrollX() - deltaX < leftBorder) {
                    scrollByStart = deltaX/3;
                } else if (getScrollX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart = deltaX/3;
                }
                scrollBy((int) -scrollByStart, 0);
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex =(getScrollX() + getWidth() / 2) / getWidth();
                //如果超过右边界，则回弹到最后一个View
                if (targetIndex>getChildCount()-1){
                    targetIndex = getChildCount()-1;
                    //如果超过左边界，则回弹到第一个View
                }else if (targetIndex<0){
                    targetIndex =0;
                }
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，使用startScroll方法，对其进行初始化
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
            default:
                break;
        }
        mLastX = xTouch;
        mLastY = yTouch;
        return super.onTouchEvent(event);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

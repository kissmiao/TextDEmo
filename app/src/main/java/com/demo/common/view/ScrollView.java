package com.demo.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ScrollView extends HorizontalScrollView {
    //按下的x坐标，点击事件距离控件左边的距离
    private int downX;
    private View mChild;
    private int mFistWidch;
    private DisplayMetrics dm;
    private int mWidth;

    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChild = getChildAt(0);
        mFistWidch = mChild.getMeasuredWidth();

    }




    //HorizontalScrollView类的onTouchEvent()返回为true,
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int newPosition = getScrollX();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获得当前按下的 X 轴坐标
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                int a = (int) ev.getX() - downX;

                if (a > 0 && getScrollX() == 0) {
                    Log.i("LOG", "getScrollX()" + getScrollX());
                    return false;
                }
                int scrollWidth = getScrollX() + mWidth;

                Log.i("LOG", " 按下坐标 " + downX + " 滑动左右值 " + a);
                if (a < 0 && scrollWidth == mFistWidch) {
                    Log.i("LOG", "scrollWidth" + scrollWidth);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }


}

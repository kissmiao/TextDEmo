package com.commonadapter.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ScrollRelativeLayout extends RelativeLayout {
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public ScrollRelativeLayout(Context context) {
        super(context);
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = X;
                mLastY = Y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = X - mLastX;
                int offY = Y - mLastY;

                ((View) getParent()).scrollBy(-offX, 0);

                break;

            case MotionEvent.ACTION_UP:
                break;

        }

        return true;
    }

}

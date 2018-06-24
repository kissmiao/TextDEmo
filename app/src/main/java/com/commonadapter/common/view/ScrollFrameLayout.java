package com.commonadapter.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ScrollFrameLayout extends FrameLayout {

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    private RelativeLayout holder;

    public ScrollFrameLayout(Context context) {
        super(context);
    }

    public ScrollFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ScrollFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}

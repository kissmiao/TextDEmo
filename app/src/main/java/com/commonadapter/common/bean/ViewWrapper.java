package com.commonadapter.common.bean;

import android.view.View;

/**
 * Created by Administrator on 2016/9/12.
 */
public class ViewWrapper {

    View mTargetView;

    public ViewWrapper(View v) {
        mTargetView = v;
    }

    public void setWidth(int width) {
        mTargetView.getLayoutParams().width = width;
        mTargetView.requestLayout();
    }

    // for view's width
    public int getWidth() {
        int width = mTargetView.getLayoutParams().width;
        return width;
    }

    // for view's height
    public void setHeight(int height) {
        mTargetView.getLayoutParams().height = height;
        mTargetView.requestLayout();
    }

    public int getHeight() {
        int height = mTargetView.getLayoutParams().height;
        return height;
    }
}

package com.demo.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ScrollView2 extends View {


    public ScrollView2(Context context) {
        super(context);
    }

    public ScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    //在一个自定义View中实现该方法，方法名可以自定义
    public void smoothScrollTo(int destX,int destY){

    }
}

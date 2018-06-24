package com.commonadapter.common.view;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {

/*
     position 有一下几个区间:
     [-∞ , -1)  :
     表示左边 的View 且已经看不到了
     [-1 ,   0]  :
     表示左边的 View ,且可以看见
     ( 0 ,   1]  :
     表示右边的VIew , 且可以看见了
     ( 1 , -∞)  :
     表示右边的 View 且已经看不见了

    a 是第一页
    b 是第二页
    当前页为 a, 当  a  向左滑动,  直到滑到 b 时:
    a 的position变化是  [-1 ,   0]   由  0  慢慢变到 -1
    b 的position变化是  ( 0 ,   1]   由  1  慢慢变到  0
    当前页为b,  当 b 向右滑动, 直到滑到a 时:
    a 的position变化是  [-1 ,   0]   由  -1  慢慢变到 0
    b 的position变化是  ( 0 ,   1]   由   0   慢慢变到 1

*/

    @Override

    public void transformPage(View view, float position) {
        Log.i("LOG", "*" + position);

        if (position <= -1) {   // [-Infinity,-1)
            ViewHelper.setScaleX(view, (float) 0.9);
            ViewHelper.setScaleY(view, (float) 0.9);
            ViewHelper.setAlpha(view, (float) 0.3);
        } else if (position < 0) {   // [-1,0]
            ViewHelper.setScaleX(view, (float) (1 + (position) / 10));
            ViewHelper.setScaleY(view, (float) (1 + (position) / 10));
            ViewHelper.setAlpha(view, (float) (1 + position * 0.7));
        } else if (position < 1) {
            ViewHelper.setScaleX(view, (float) (1 - (position) / 10));
            ViewHelper.setScaleY(view, (float) (1 - (position) / 10));
            ViewHelper.setAlpha(view, (float) (1 - position * 0.7));
        } else {
            ViewHelper.setScaleX(view, (float) 0.9);
            ViewHelper.setScaleY(view, (float) 0.9);
            ViewHelper.setAlpha(view, (float) 0.3);
        }
    }
}

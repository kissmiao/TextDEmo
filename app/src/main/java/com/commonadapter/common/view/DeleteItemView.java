package com.commonadapter.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/6/17.
 */
public class DeleteItemView extends RelativeLayout{
   private int downX;

    public DeleteItemView(Context context) {
        super(context);
    }

    public DeleteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeleteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获得当前按下的 X轴坐标
                downX = (int)ev.getX() ;
                break;
            case MotionEvent. ACTION_MOVE:
                int a= (int)ev.getX()- downX;

                Log. i("LOG", "按下坐标"+ downX+" 滑动左右值 "+a);
                break;
            case MotionEvent. ACTION_UP:
                break;
        }
        return  super.onTouchEvent(ev);
    }


}

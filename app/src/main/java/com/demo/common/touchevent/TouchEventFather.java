package com.demo.common.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchEventFather extends LinearLayout {

    public TouchEventFather(Context context) {
        super(context);
    }

    public TouchEventFather(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * false 直接交给Activity 处理
     * true，事件会分发给当前 View 并由 dispatchTouchEvent 方法进行消费，同时事件会停止向下传递；
     * super 事件会自动的分发给当前 View 的 onInterceptTouchEvent 方法。
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("sunzn", "===F=== dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
       //      return false;
    }


    /**
     * 返回 super.onInterceptTouchEvent(ev)和返回false一样，继承viewGroup会将事件分发到下面的子view，
     * 返回true 对事件进行拦截，下面的子View不管有没有拦截或监听都将获取不到事件，并且事件会传递到当前控件的onTouchEvent()处理
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i("sunzn", "===F=== onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
       return super.onInterceptTouchEvent(ev);
        //	return true;
    }

    /**
     * 回了 false，那么这个事件会从当前 View 向上传递，并且都是由上层 View 的 onTouchEvent 来接收，如果传递到上面的 onTouchEvent 也返回 false，这个事件就会“消失”，而且接收不到下一次事件。
     * 如果返回了 true 则会接收并消费该事件。
     * 如果返回 super.onTouchEvent(ev) 默认处理事件的逻辑和返回 false 时相同。
     *
     * @param ev
     * @return
     */
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("sunzn", "===F===  onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

}

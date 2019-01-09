package com.demo.common.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchEventChilds extends View {

    public TouchEventChilds(Context context) {
        super(context);
    }

    public TouchEventChilds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * false  当前不关心此次事件，让事件由此向上传递
     * true，事件会分发给当前 View 并由 dispatchTouchEvent 方法进行消费，同时事件会停止向下传递；
     * super 事件会自动的分发给当前 View 的 onTouchEvent 方法。
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("sunzn", "===C=== dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
      //  return super.dispatchTouchEvent(ev);
		return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("sunzn", "===C=== onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        Toast.makeText(getContext(),"ChildView onTouchEvent",Toast.LENGTH_SHORT).show();
        //   return super.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

}

package com.demo.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/7.
 */
public class Mview2 extends TextView {

    private Bitmap bitmap;
    private Paint paint;
    private Canvas mCanvas;

    public Mview2(Context context) {
        super(context);
    }

    public Mview2(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        bitmap = Bitmap.createBitmap(500 ,500 , Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);

    }

    public Mview2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //绘制视图自身
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* paint.setTextSize(130);
        mCanvas.drawText("启舰大SB", 0, 100, paint);
        canvas.drawBitmap(bitmap, 0, 100, paint);*/
    }

    @Override
    public boolean isFocused() {

        return true;

    }
    //绘制子视图
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}

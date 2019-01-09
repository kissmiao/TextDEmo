package com.demo.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.demo.common.util.Utils;

/**
 * Created by whl on 2016/12/5.
 */
public class ColumnView extends View {
    private Paint textPaint;
    private Paint mColumn1;
    private Paint mColumn2;
    private Paint mColumn3;

    private Context mContext;
    private DisplayMetrics dm;
    private int width;

    private int mColumnWidth;
    //40dp
    private int mColumn1_Left;


    private int mColumn1_height;

    public ColumnView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /* Paint.Style.FILL    :填充内部
     Paint.Style.FILL_AND_STROKE  ：填充内部和描边
     Paint.Style.STROKE  ：仅描边*/
    private void init() {
        dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;

        mColumnWidth = Utils.Dp2Px(mContext, 10);

        mColumn1_Left = (width - mColumnWidth * 3) / 4;
        mColumn1_height = Utils.Dp2Px(mContext, 40);

        textPaint = new Paint();
        textPaint.setStrokeWidth(3);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);

        mColumn1 = new Paint();
        mColumn1.setAntiAlias(true);
        mColumn1.setStyle(Paint.Style.FILL);
        mColumn1.setColor(Color.RED);

        mColumn2 = new Paint();
        mColumn2.setAntiAlias(true);
        mColumn2.setColor(Color.BLUE);
        mColumn2.setStyle(Paint.Style.FILL);

        mColumn3 = new Paint();
        mColumn3.setAntiAlias(true);
        mColumn3.setColor(Color.GREEN);
        mColumn3.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect rect1 = new Rect(mColumn1_Left, 0, mColumn1_Left + mColumnWidth, Utils.Dp2Px(mContext, 60));
        canvas.drawRect(rect1, mColumn1);

        Rect rect2 = new Rect(mColumn1_Left * 2 + mColumnWidth, Utils.Dp2Px(mContext, 20), mColumn1_Left * 2 + mColumnWidth * 2, Utils.Dp2Px(mContext, 60));
        canvas.drawRect(rect2, mColumn2);

        Rect rect3 = new Rect(mColumn1_Left * 3 + mColumnWidth * 2, Utils.Dp2Px(mContext, 20), mColumn1_Left * 3 + mColumnWidth * 3, Utils.Dp2Px(mContext, 60));
        canvas.drawRect(rect3, mColumn3);

     //   canvas.drawT

    }
}

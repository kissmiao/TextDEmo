package com.demo.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import com.demo.R;

/**
 * Created by whl on 2016/12/22.
 */
public class BiemapView extends View {
    private float width;
    private float height;

    public BiemapView(Context context) {
        super(context);
    }

    public BiemapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BiemapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }

    private Bitmap getBitmapOnResource() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.a, options);
    }
}

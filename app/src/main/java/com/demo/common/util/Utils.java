package com.demo.common.util;

import android.content.Context;

/**
 * Created by whl on 2016/12/5.
 */
public class Utils {
    /**
     * dip转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}

package com.demo.common.BaseAdapter;

import android.content.Context;

import java.util.List;

/**
 * Created by wanghongliang on 16/3/22.
 */
public abstract class EnhancedQuickAdapter<T> extends QuickAdapter<T> {

    protected EnhancedQuickAdapter(Context context, int[] mLayoutResArrays) {
        super(context, mLayoutResArrays);
    }

    protected EnhancedQuickAdapter(Context context, int[] mLayoutResArrays, List<T> data) {
        super(context, mLayoutResArrays, data);
    }
   
}

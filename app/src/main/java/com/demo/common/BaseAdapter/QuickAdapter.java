package com.demo.common.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wanghongliang on 16/3/22.
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, ViewHolderHelper> {

    protected QuickAdapter(Context context, int[] mLayoutResArrays) {
        super(context, mLayoutResArrays);
    }

    protected QuickAdapter(Context context, int[] mLayoutResArrays, List<T> data) {
        super(context, mLayoutResArrays, data);
    }

    @Override
    protected ViewHolderHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
        return ViewHolderHelper.get(mContext, convertView, parent, this.mLayoutResArrays[getItemViewType(position)], position);
    }

}

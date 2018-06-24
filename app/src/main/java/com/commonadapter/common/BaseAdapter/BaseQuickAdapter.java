package com.commonadapter.common.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongliang on 16/3/22.
 * 方法基本分为两类，一类是BaseAdapter中需要实现的方法；另一类用于操作我们的data。
 */
public abstract class BaseQuickAdapter<T, H extends ViewHolderHelper> extends BaseAdapter {

    protected List<T> mData;
    protected final int[] mLayoutResArrays;
    protected final Context mContext;

    protected BaseQuickAdapter(Context context, int[] mLayoutResArrays) {
        this(context, mLayoutResArrays, null);
    }

    protected BaseQuickAdapter(Context context, int[] mLayoutResArrays, List<T> data) {
        this.mContext = context;
        this.mLayoutResArrays = mLayoutResArrays;
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    }

    @Override
    public int getCount() {
        if (this.mData == null) {
            return 0;
        }
        return this.mData.size();
    }

    @Override
    public T getItem(int position) {
        if (position > this.mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return this.mLayoutResArrays.length;
    }

    //默认返回的是item数组中的第一个
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final H helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        convert(helper, item);
        return helper.getView();
    }


    protected abstract void convert(H helper, T item);

    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

    public void addData(T data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    public void deleteData() {
        int count = getCount() - 1;
        mData.remove(count);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

}

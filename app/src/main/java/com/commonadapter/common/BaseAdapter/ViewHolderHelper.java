package com.commonadapter.common.BaseAdapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wanghongliang on 16/3/22.
 * 充当了我们的ViewHolder角色，保存convertView中子View的引用，和convertView通过tag关联；
 * 提供了一堆辅助方法，用于为View赋值和设置事件。
 */
public class ViewHolderHelper {
    private SparseArray<View> mViews;
    private SparseArray<View> mconvertViews;
    private Context mContext;
    private int position;
    private View mConvertView;
    public int layoutId;

    //构造方法设置的Tag,取的时候先判断是否为空，为空则调用构造方式new一个，不为空则取出Tag
    public ViewHolderHelper(Context context, ViewGroup parent, int layoutId, int position) {
        this.mContext = context;
        this.position = position;
        this.mViews = new SparseArray<View>();
        this.mconvertViews = new SparseArray<View>();
        this.mConvertView = getConvertView(context, parent, layoutId, position);
        this.layoutId = layoutId;
        mConvertView.setTag(this);
    }


    private <T extends View> T getConvertView(Context context, ViewGroup parent, int layoutId, int position) {
        View view = mconvertViews.get(layoutId);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            mconvertViews.put(layoutId, view);
        }
        return (T) view;
    }

    public static ViewHolderHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolderHelper(context, parent, layoutId, position);
        }
        ViewHolderHelper existingHelper = (ViewHolderHelper) convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }

    public View getView() {
        return this.mConvertView;
    }

    public int getPosition() {
        return this.position;
    }

    public <T extends View> T retrieveView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public ViewHolderHelper setText(int viewId, String value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }
}

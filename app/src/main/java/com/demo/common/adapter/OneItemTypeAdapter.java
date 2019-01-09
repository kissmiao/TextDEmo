package com.demo.common.adapter;

import android.content.Context;

import com.demo.R;
import com.demo.common.BaseAdapter.EnhancedQuickAdapter;
import com.demo.common.BaseAdapter.ViewHolderHelper;
import com.demo.common.bean.Bean;

import java.util.List;

/**
 * Created by wanghongliang on 16/3/22.
 */
public class OneItemTypeAdapter extends EnhancedQuickAdapter<Bean> {

    public OneItemTypeAdapter(Context context, int[] mLayoutResArrays) {
        super(context, mLayoutResArrays);
    }

    public OneItemTypeAdapter(Context context, int[] mLayoutResArrays, List<Bean> data) {
        super(context, mLayoutResArrays, data);
    }

    @Override
    protected void convert(ViewHolderHelper helper, Bean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_describe, item.getDesc());
        helper.setText(R.id.tv_phone, item.getPhone());
        helper.setText(R.id.tv_time, item.getTime());
    }

}

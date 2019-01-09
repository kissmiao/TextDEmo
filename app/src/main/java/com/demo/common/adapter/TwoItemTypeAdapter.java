package com.demo.common.adapter;

import android.content.Context;

import com.demo.R;
import com.demo.common.BaseAdapter.EnhancedQuickAdapter;
import com.demo.common.BaseAdapter.ViewHolderHelper;
import com.demo.common.bean.ChatMessage;

import java.util.List;

/**
 * Created by wanghongliang on 16/3/22.
 */
public class TwoItemTypeAdapter extends EnhancedQuickAdapter<ChatMessage> {

    public TwoItemTypeAdapter(Context context, int[] mLayoutResArrays) {
        super(context, mLayoutResArrays);
    }

    public TwoItemTypeAdapter(Context context, int[] mLayoutResArrays, List<ChatMessage> data) {
        super(context, mLayoutResArrays, data);
    }

    @Override
    protected void convert(ViewHolderHelper helper, ChatMessage item) {
        switch (helper.layoutId) {
            case R.layout.main_chat_from_msg:
                helper.setText(R.id.chat_from_content, item.getContent());
                helper.setText(R.id.chat_from_name, item.getName());
                //         helper.setImageResource(R.id.chat_from_icon, item.getIcon());
                break;
            case R.layout.main_chat_send_msg:
                helper.setText(R.id.chat_send_content, item.getContent());
                helper.setText(R.id.chat_send_name, item.getName());
                //        helper.setImageResource(R.id.chat_send_icon, item.getIcon());
                break;


        }


    }


}

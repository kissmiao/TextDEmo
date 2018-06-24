package com.commonadapter.common.adapter;

import android.content.Context;

import com.commonadapter.R;
import com.commonadapter.common.baseAdapter2.CommonRecyclerViewAdapter;
import com.commonadapter.common.baseAdapter2.CommonRecyclerViewHolder;
import com.commonadapter.common.bean.ChatMessage;

import java.util.List;

/**
 * Created by whl on 2016/12/15.
 */
public class RecycleViewAdapter extends CommonRecyclerViewAdapter<ChatMessage> {
    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public RecycleViewAdapter(Context context, List<ChatMessage> data) {
        super(context, data);
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, ChatMessage entity, int position) {
        h.setText(R.id.chat_from_content, entity.getContent());
        h.setText(R.id.chat_from_name, entity.getName());
    }

    @Override
    public int getLayoutViewId(int viewType) {

        return R.layout.main_chat_from_msg;
    }
}

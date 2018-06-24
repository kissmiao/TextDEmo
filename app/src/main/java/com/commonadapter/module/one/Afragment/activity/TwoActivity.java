package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

import com.commonadapter.R;
import com.commonadapter.common.adapter.TwoItemTypeAdapter;
import com.commonadapter.common.bean.ChatMessage;

import java.util.ArrayList;


/**
 * Created by wanghongliang on 16/3/22.
 */
public class TwoActivity extends Activity {

    private ListView mListView;
    private ArrayList<ChatMessage> mDatas = new ArrayList<ChatMessage>();
    private TwoItemTypeAdapter mAdapter;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_item_tow);
        initDatas();
        mListView = (ListView) findViewById(R.id.id_lv);


        WindowManager wm = this.getWindowManager();

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        int layoutResArrays[] = {R.layout.main_chat_from_msg, R.layout.main_chat_send_msg};

        TwoItemTypeAdapter twoItemTypeAdapter = new TwoItemTypeAdapter(TwoActivity.this, layoutResArrays, mDatas) {

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                //这里对应的0和1是数组的下标
                if (mDatas.get(position).isComMeg()) {
                    return ChatMessage.RECIEVE_MSG;
                }
                return ChatMessage.SEND_MSG;
            }
        };
        mListView.setAdapter(twoItemTypeAdapter);

        mListView.post(new Runnable() {

            @Override
            public void run() {
                onMeasureButton();
            }
        });
    }

    public void onMeasureButton() {
        int w = mListView.getMeasuredWidth();
        int h = mListView.getHeight();
        Log.i("LOG", "==sssss=" + h);



    }

    private void initDatas() {
        ChatMessage msg = null;
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);

        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.renma, "renma", "where are you ",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.mipmap.xiaohei, "xiaohei", "where are you ",
                null, false);
        mDatas.add(msg);
    }
}

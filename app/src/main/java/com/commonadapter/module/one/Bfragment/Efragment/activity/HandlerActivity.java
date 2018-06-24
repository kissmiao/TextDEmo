package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.commonadapter.R;


/**
 * Created by whl on 2016/11/27.
 */
public class HandlerActivity extends Activity {
    private TextView tv_handler_text;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_handler_text.setText(msg.arg1 + "");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_handler);
        tv_handler_text = (TextView) findViewById(R.id.tv_handler_text);
        initThread();

    }


    private void initThread() {

        new Thread() {
            @Override
            public void run() {

                while (!stopThread) {
                    int i = 0;
                    i++;
                    Message message = new Message();
                    message.arg1 = i;
                    mHandler.sendMessage(message);
                    //  tv_handler_text.setText(i + "");
                }

            }
        }.start();
    }

    private boolean stopThread = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread = false;
    }
}

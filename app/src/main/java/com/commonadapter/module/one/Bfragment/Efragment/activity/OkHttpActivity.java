package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.commonadapter.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.SimpleMsg;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by whl on 2017/4/16.
 */
public class OkHttpActivity extends Activity {
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok);
        dataTextView = (TextView) findViewById(R.id.data);

        //  onOkHttpGet();
        String url = "http://www.kuaidi100.com/query";
        //  http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
        Map map = new HashMap();
        map.put("type", "yuantong");
        map.put("postid", "885195255151132621");

        OkHttpUtils.post().url(url).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, SimpleMsg e, int id) {

                Toast.makeText(getApplicationContext(), "请求失败" + e.getErrMsg(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(String response, int id) {

                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                dataTextView.setText(response);
            }
        });


        //  MyDialog


    }


    public void sendFailResultCallback(final Call call, final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
                    Toast.makeText(getApplicationContext(), "网络超时", Toast.LENGTH_SHORT).show();
                } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}

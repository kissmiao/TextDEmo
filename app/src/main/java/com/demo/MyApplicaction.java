package com.demo;

import android.content.Context;

import com.base.BaseApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MyApplicaction extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplicationContext();
        //配置https
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //配置https
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置Ok
                .build();


        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getContext() {
        return mContext;
    }
}

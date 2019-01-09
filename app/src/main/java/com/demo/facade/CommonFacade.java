package com.demo.facade;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * 通用的facade服务类
 *
 * @author aeeiko
 */
public class CommonFacade {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 6, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());

    private static CommonFacade commonFacade;

    public static CommonFacade getInstance() {
        if (commonFacade == null) {
            commonFacade = new CommonFacade();
        }
        return commonFacade;
    }

    /***
     * 执行的方法
     *
     * @param action       事件url
     * @param map          参数map
     * @param viewCallBack view层需要实现的回调
     */
    public void exec(String action, HashMap<String, String> map, ViewCallBack viewCallBack) {
        MTask task = new MTask(action, map, viewCallBack);
        //这一个方法可以让AsyncTask在3.0版本上并行
        task.executeOnExecutor(executor);
        if (viewCallBack != null) {
            viewCallBack.onStart();
        }

    }

    /***
     * 同步接口调用（直接返回服务数据，如果有异常返回空字符串）
     *
     * @param action
     * @param map
     * @return
     */
    public String execSync(String action, HashMap<String, String> map) {
        try {
            // 有结果直接返回到ui线程去处理
            String responseBody = ApiAccessor.getHttpResponse(action, map);
            return responseBody;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // 有异常返回的ui层处理
            return "";
        }
    }

    /***
     * 异步线程
     *
     * @author aeeiko
     */
    public class MTask extends AsyncTask<Object, Object, Object> {
        String action;
        HashMap<String, String> map;
        ViewCallBack viewCallBack;

        public MTask(String action, HashMap<String, String> map, ViewCallBack viewCallBack) {
            // TODO Auto-generated constructor stub
            this.action = action;
            this.map = map;
            this.viewCallBack = viewCallBack == null ? ViewCallBack.CALLBACK_DEFAULT : viewCallBack;
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            try {
                // 有结果直接返回到ui线程去处理
                String responseBody = ApiAccessor.getHttpResponse(action, map);
                return responseBody;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // 有异常返回的ui层处理
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            viewCallBack.onFinish();
            try {
                // 处理上面的结果，这里处理各种异常情况
                if (result instanceof String) {
                    JSONObject jo_main = new JSONObject(result.toString());
                    if (jo_main.optInt("code", -100) == 0) {
                        Object o = viewCallBack.parseNetworkResponse(jo_main);
                        viewCallBack.onSuccess(o);
                    } else {
                        viewCallBack.onFailed(new SimpleMsg(jo_main.optInt("code"), jo_main.optString("msg")));
                    }
                } else if (result instanceof UnknownHostException || result instanceof ConnectException) {
                    viewCallBack.onFailed(new SimpleMsg(-100, "网络未连接"));
                } else if (result instanceof SocketTimeoutException) {
                    viewCallBack.onFailed(new SimpleMsg(-110, "网络超时"));
                }
            } catch (Exception ex) {
                // 在callback的success回调里面出错也会在这里捕捉，所以尽量ui层的代码写在onsuccess里面
                viewCallBack.onFailed(new SimpleMsg(-110, "未知错误"));
            }
        }
    }
}

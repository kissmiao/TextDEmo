package com.commonadapter.facade;

import org.json.JSONObject;

import java.io.IOException;


/***
 * view层回调类
 *
 * @param <T>
 * @author aeeiko
 */
public abstract class ViewCallBack<T> {

    /***
     * 流程开始
     */
    public void onStart() {
    }


    /***
     * 成功返回
     *
     * @param t
     */
    public void onSuccess(T t) {
    }


    /***
     * 失败返回
     *
     * @param simleMsg
     */
    public void onFailed(SimpleMsg simleMsg) {
    }


    /***
     * 在onSuccess和onFailed之前执行，用于销毁在onstart里面启动的组件
     */
    public void onFinish() {
    }



    public abstract T parseNetworkResponse(JSONObject jo_main) throws IOException;

    public static ViewCallBack CALLBACK_DEFAULT = new ViewCallBack() {


        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(Object o) {

        }

        @Override
        public void onFailed(SimpleMsg simleMsg) {

        }

        @Override
        public void onFinish() {

        }

        @Override
        public Object parseNetworkResponse(JSONObject jo_main) throws IOException {
            return null;
        }


    };
}

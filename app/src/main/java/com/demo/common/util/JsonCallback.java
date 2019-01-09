package com.demo.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by wanghongliang on 16/3/7.
 */
public abstract class JsonCallback extends Callback<JSONObject> {
    @Override
    public JSONObject parseNetworkResponse(Response response, int id) throws Exception {
        return JSON.parseObject(response.body().string());
    }
}
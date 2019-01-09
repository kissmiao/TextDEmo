package com.demo.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/12.
 */
public abstract class BeanCallBack<T> extends ViewCallBack<T>{
    @Override
    public T parseNetworkResponse(JSONObject jo_main) throws IOException {
       T baseBean = JSON.parseObject(jo_main.toString(), getReturnType());
        return baseBean;
    }

    public abstract TypeReference<T> getReturnType();
}

package com.commonadapter.common.bean;

/**
 * Created by whl on 2016/12/21.
 */
public class BaseBean<T> {


    private T mBean;



    public BaseBean(T bean) {

        this.mBean = bean;
    }

    public BaseBean() {

    }

    public T getBean() {
        return mBean;
    }

}

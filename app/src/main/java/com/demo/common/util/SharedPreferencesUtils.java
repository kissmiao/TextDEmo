package com.demo.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import com.demo.MyApplicaction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * @author xugy
 * @创建时间 2014-6-18 上午10:44:11
 */
public class SharedPreferencesUtils {


    public enum SpKey {

        TOKENID("tokenid"),USERNAME("username");

        private String value;

        SpKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    public enum SpKeytype {

        String(""), Integer(0), Boolean(false), Float(0.0f);

        private Object value;

        SpKeytype(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }


    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void setParam(SpKey key, Object object) {

        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = MyApplicaction.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key.getValue(), (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key.getValue(), (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key.getValue(), (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key.getValue(), (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key.getValue(), (Long) object);
        }
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(SpKey key,
                                  SpKeytype defaultObject) {
        String type = defaultObject.getValue().getClass().getSimpleName();
        SharedPreferences sp = MyApplicaction.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key.getValue(), (String) defaultObject.getValue());
        } else if ("Integer".equals(type)) {
            return sp.getInt(key.getValue(), (Integer) defaultObject.getValue());
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key.getValue(), (Boolean) defaultObject.getValue());
        } else if ("Float".equals(type)) {
            return sp.getFloat(key.getValue(), (Float) defaultObject.getValue());
        } else if ("Long".equals(type)) {
            return sp.getLong(key.getValue(), (Long) defaultObject.getValue());
        }
        return null;
    }

    /*
     * 保存对象
     */
    public static void setObject(String key, Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 将对象Product保存在ObjectOutputStream对象中
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            SharedPreferences sp_store_object = MyApplicaction.getContext().getSharedPreferences(
                    FILE_NAME, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp_store_object.edit();
            // 将对象Product转换成byte数组，将其进行base64编码
            String productBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            // 编码后的字符串保存在base64.xml文件中
            editor.putString(key, productBase64);
            editor.commit();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取保存的对象
     */
    public static Object getObject(String key) {
        Object object = null;
        try {
            SharedPreferences sp_read_object = MyApplicaction.getContext().getSharedPreferences(
                    FILE_NAME, Activity.MODE_PRIVATE);
            // 从base64.xml文件中读取Product对象的base64格式字符串
            String base64Product = sp_read_object.getString(key, "");
            // 将base64格式字符串还原成byte数组
            if (!base64Product.equals("")) {
                byte[] productBytes = Base64.decode(base64Product.getBytes(),
                        Base64.DEFAULT);
                ByteArrayInputStream bais = new ByteArrayInputStream(
                        productBytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                object = ois.readObject();
                // 将byte数组转换成product对象
                ois.close();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}

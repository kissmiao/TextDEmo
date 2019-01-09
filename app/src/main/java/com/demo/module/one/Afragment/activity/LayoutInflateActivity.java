package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.R;


/**
 * Created by whl on 2016/12/11.
 */
public class LayoutInflateActivity extends Activity {
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layoutinflate);

        mLinearLayout = (LinearLayout) findViewById(R.id.main_layout);


        //root 为null的情况下都没有子布局的父布局都不起作用

        //子布局的父布局是有作用的,
       // View view = getLayoutInflater().inflate(R.layout.activity_popw, mLinearLayout, false);
        //没有使用添加View的父布局  root 为null的情况下都没有子布局的父布局都不起作用 ,boolean 设置 true false 或则不设置都是不起作用的
        //   View view = getLayoutInflater().inflate(R.layout.activity_popw, null, false);
        //没有使用添加View的父布局  root 为null的情况下都没有子布局的父布局都不起作用
         //   View view = getLayoutInflater().inflate(R.layout.activity_popw, null, true);
        //崩溃
        //   View view = getLayoutInflater().inflate(R.layout.activity_popw, mLinearLayout,true);


        //button设置宽度无效
        //    View view1 = LayoutInflater.from(this).inflate(R.layout.item_button, null, false);
        //   button设置宽度无效
        //   View view1 = LayoutInflater.from(this).inflate(R.layout.item_button, null, true);
        //  button设置宽度有效
            View view1 = LayoutInflater.from(this).inflate(R.layout.item_button, mLinearLayout, false);
        //崩溃
        //    View view1 = LayoutInflater.from(this).inflate(R.layout.item_button, mLinearLayout, true);
        //崩溃 猜测boolean 默认true
        //  View view1 = LayoutInflater.from(this).inflate(R.layout.item_button, mLinearLayout);
        mLinearLayout.addView(view1);
    }

}

package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;

import com.demo.common.view.MView3;


/**
 * Created by Administrator on 2016/8/8.
 */
public class CurveActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MView3(CurveActivity.this));

    }
}

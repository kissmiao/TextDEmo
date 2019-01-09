package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;

import com.demo.R;
import com.demo.common.view.Mview;


/**
 * Created by whl on 16/6/30.
 */
public class MyView extends Activity {


private Mview mview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myview);
        mview= (Mview) findViewById(R.id.myview);
        mview.startAnim();
    }


}

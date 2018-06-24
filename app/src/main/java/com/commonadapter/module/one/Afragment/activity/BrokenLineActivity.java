package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;

import com.commonadapter.common.view.BrokenLineView;

/**
 * Created by Administrator on 2016/11/9.
 */
public class BrokenLineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new BrokenLineView(BrokenLineActivity.this));
    }
}

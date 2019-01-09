package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.demo.common.view.Clock;

/**
 * Created by wanghongliang on 16/3/18.
 */
public class ClockActivity extends Activity {

   private Clock clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 把Activity的标题去掉
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(new Clock(ClockActivity.this));
    }

}

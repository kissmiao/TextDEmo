package com.commonadapter.module.tow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.commonadapter.R;
import com.commonadapter.common.view.ProgressBarView;


public class SpeedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s);
        ProgressBarView view = (ProgressBarView) findViewById(R.id.progress);
        view.setDraggingEnabled(true);
        view.setMax(1000);
    }

}

package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.demo.R;
import com.demo.common.util.MyDialog;

/**
 * Created by whl on 2017/3/19.
 */
public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popw);

        findViewById(R.id.bt_popw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog dialog = new MyDialog(DialogActivity.this);
                dialog.show();

            }
        });

    }
}

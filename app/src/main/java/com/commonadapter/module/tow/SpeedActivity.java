package com.commonadapter.module.tow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.commonadapter.R;
import com.commonadapter.common.view.ProgressBarView;


public class SpeedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s);
        ProgressBarView view = (ProgressBarView) findViewById(R.id.progress);
        view.setDraggingEnabled(true);

        view.setMoney(500, 1500);

        view.setMoneyClickListener(new ProgressBarView.onMoneyClickListener() {
            @Override
            public void onMoneyListener(String money) {
                Toast.makeText(SpeedActivity.this, "money" + money, Toast.LENGTH_SHORT).show();
            }
        });
    }


}

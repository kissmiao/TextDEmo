package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.commonadapter.R;
import com.commonadapter.common.util.PopupWindowSignUpData;

/**
 * Created by whl on 16/9/27.
 */
public class PopwActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popw);
        findViewById(R.id.bt_popw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWin();
            }
        });


    }
    private void showPopupWin() {
        if (null == mEvent) {
            mEvent = new PopupWindowSignUpData(this);
        }
        mEvent.showPopupWindow();
    }
    private PopupWindowSignUpData mEvent;

}

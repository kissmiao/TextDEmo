package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.commonadapter.R;
import com.commonadapter.common.view.QQView2;

/**
 * Created by Administrator on 2016/7/12.
 */
public class QQHealthActivity extends Activity {

    private QQView2 view;
    private Button me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LOG", "******onCreate********");

        setContentView(R.layout.layout_qqhealth);
      /*  me = (Button) findViewById(R.id.bt_me);
        view = (QQView2) findViewById(R.id.qq_view2);*/

       /* me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 70, 0, 0);
              //  view.setLayoutParams(params);
                      view.requestLayout();
            }
        });*/

    }

}

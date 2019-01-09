package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.R;

/**
 * Created by wanghongliang on 16/3/27.
 */
public class TransverseScrollView extends Activity {

    private LinearLayout ll_moveview;
    private WindowManager wm;
    private int witch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_viewpage);
        ll_moveview = (LinearLayout) findViewById(R.id.ll_moveview);
        wm = this.getWindowManager() ;
        witch = wm.getDefaultDisplay().getWidth() ;
    }

    @Override
    protected void onStart() {
        super .onStart();

        for ( int i = 0 ; i < 15 ; i++) {
            final TextView textView = new TextView(this) ;
            textView.setGravity(Gravity. CENTER);
            textView.setBackgroundColor(getResources().getColor( R.color.colorAccent ) );
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT ) ;
            p. height=200;
            textView.setText(i + "*****");
            textView.setLayoutParams(p) ;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("LOG","------"+textView.getText());
                }
            });
            ll_moveview .addView(textView);


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        Log.i("LOG","------------");
        return super.onTouchEvent(event);
    }
}

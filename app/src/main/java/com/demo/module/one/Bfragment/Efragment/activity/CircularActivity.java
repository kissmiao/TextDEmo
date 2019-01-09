package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.demo.R;
import com.demo.common.bean.KeyboardUtil;

/**
 * Created by whl on 16/9/5.
 */
public class CircularActivity extends Activity {
    private Context ctx;
    private Activity act;
    private EditText edit;
    private EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_circular);

        ctx = this;
        act = this;

        edit = (EditText) this.findViewById(R.id.edit);
        edit.setInputType(InputType.TYPE_NULL);

        edit1 = (EditText) this.findViewById(R.id.edit1);

        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new KeyboardUtil(act, ctx, edit).showKeyboard();
                return false;
            }
        });

        edit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inputback = edit1.getInputType();
                edit1.setInputType(InputType.TYPE_NULL);
                new KeyboardUtil(act, ctx, edit1).showKeyboard();
                edit1.setInputType(inputback);
                return false;
            }
        });
    }
}

package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.demo.R;
import com.demo.common.bean.BaseBean;
import com.demo.common.bean.Bean;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by whl on 2016/12/22.
 */
public class EventBusActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_bus);
        findViewById(R.id.bt_event_bus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean bean = new Bean();
                bean.setTitle("c测试");

                EventBus.getDefault().post(new BaseBean<Bean>());
            }
        });

    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(R.anim.slide_out_top,0);
    }
}

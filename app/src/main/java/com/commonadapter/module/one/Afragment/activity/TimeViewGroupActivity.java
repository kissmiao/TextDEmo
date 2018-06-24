package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.commonadapter.R;
import com.commonadapter.common.util.DateUtil;
import com.commonadapter.common.view.TimeViewGroup;

import java.util.Date;

/**
 * Created by whl on 2017/5/22.
 */
public class TimeViewGroupActivity extends Activity {

    private TextView tv_details_time;
    private TextView tv_details_time_selected;
    private TimeViewGroup ll_time_view_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initView();
    }

    private void initView() {
        tv_details_time = (TextView) findViewById(R.id.tv_details_time);
        tv_details_time_selected = (TextView) findViewById(R.id.tv_details_time_selected);
        ll_time_view_group = (TimeViewGroup) findViewById(R.id.ll_time_view_group);
        ll_time_view_group.setOnSelectTime(new TimeViewGroup.OnSelectTime() {
            @Override
            public void onSelectTimeClick(Date date) {
                DateUtil.formatTheDateToMM_dd(date, 1);
                Toast.makeText(TimeViewGroupActivity.this, DateUtil.formatTheDateToMM_dd(date, 1), Toast.LENGTH_SHORT).show();

            }
        }, new Date());
    }
}

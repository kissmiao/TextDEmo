package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.R;

/**
 * Created by Administrator on 2016/8/29.
 */
public class DrawableActivity extends Activity {
    private TextView tv;//根据不同选项所要变更的文本控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawable);
       /* tv = (TextView) this.findViewById(R.id.tvSex);
        //根据ID找到RadioGroup实例
        MyRadioGroup group = (MyRadioGroup) this.findViewById(R.id.radioGroup);
        //绑定一个匿名监听器
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) DrawableActivity.this.findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                tv.setText("您的性别是：" + rb.getText());
            }
        });*/
    }
}

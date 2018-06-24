package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.view.WheelMenu;

/**
 * Created by wanghongliang on 16/3/27.
 */
public class MenuActivity extends Activity{
    private WheelMenu wheelMenu;
    private TextView selectedPositionText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        wheelMenu = (WheelMenu) findViewById(R.id.wheelMenu);

        wheelMenu.setDivCount(12);
        wheelMenu.setWheelImage(R.mipmap.wheel);

        selectedPositionText = (TextView) findViewById(R.id.selected_position_text);
        selectedPositionText.setText("selected: " + (wheelMenu.getSelectedPosition() + 1));

        wheelMenu.setWheelChangeListener(new WheelMenu.WheelChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                selectedPositionText.setText("selected: " + (selectedPosition + 1));
            }
        });

    }
}

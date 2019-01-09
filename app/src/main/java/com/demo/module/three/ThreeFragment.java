package com.demo.module.three;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.R;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener {

    private View view;
    /**
     * 短信
     */
    private Button mBtSms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_three, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mBtSms = (Button) view.findViewById(R.id.bt_sms);
        mBtSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sms:
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);

                break;
        }
    }
}

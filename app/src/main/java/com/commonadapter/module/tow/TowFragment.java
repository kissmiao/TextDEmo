package com.commonadapter.module.tow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.commonadapter.R;
import com.commonadapter.common.bean.Bean;
import com.commonadapter.module.one.Afragment.activity.OneActivity;

/**
 * Created by Administrator on 2016/7/20.
 */
public class TowFragment extends Fragment implements View.OnClickListener {

    private View view;
    /**
     * 传值到HomeActivity
     */
    private Button mBtIntent;
    /**
     * 刻度滑动
     */
    private Button mBtSlide;
    /**
     * 进度
     */
    private Button mBtSpeed;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tow, null);
        initView(view);
        return view;

    }

    private void initView(View view) {

        mBtIntent = (Button) view.findViewById(R.id.bt_intent);
        mBtIntent.setOnClickListener(this);
        mBtSlide = (Button) view.findViewById(R.id.bt_slide);
        mBtSlide.setOnClickListener(this);

        mBtSpeed = (Button) view.findViewById(R.id.bt_speed);
        mBtSpeed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_intent:
                Intent in = new Intent(getActivity(), OneActivity.class);

                Bean bean = new Bean();
                bean.setTitle("jack");
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", bean);
                in.putExtras(bundle);
                startActivity(in);
                break;
            case R.id.bt_slide:
                intent = new Intent(getActivity(), SlideActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_speed:
                intent = new Intent(getActivity(), SpeedActivity.class);
                startActivity(intent);
            break;
        }
    }
}

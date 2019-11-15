package com.modulea;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.base.BaseFragment;
import com.modulea.activity.NewActivity;

public class LeftFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    protected View initView(View parent) {
        init(parent);
        return parent;
    }

    private void init(View parent) {

        parent.findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), NewActivity.class));
            }
        });

    }


}

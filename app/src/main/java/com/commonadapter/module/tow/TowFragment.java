package com.commonadapter.module.tow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonadapter.R;
import com.commonadapter.common.bean.Bean;
import com.commonadapter.module.one.Afragment.activity.OneActivity;

/**
 * Created by Administrator on 2016/7/20.
 */
public class TowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tow, null);
        initView(view);
        return view;

    }

    private void initView(View view) {
//        Bundle bundle = getArguments();
//         int id = bundle.getInt("id");
//        Log.i("LOG", "Id" + id);

        view.findViewById(R.id.bt_intent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), OneActivity.class);

                Bean bean=new Bean();
                bean.setTitle("jack");
                Bundle bundle=new Bundle();
                bundle.putParcelable("bean", bean);
                in.putExtras(bundle);
                startActivity(in);

            }
        });

    }

}

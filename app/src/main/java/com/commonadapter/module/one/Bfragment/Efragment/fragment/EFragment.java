package com.commonadapter.module.one.Bfragment.Efragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.commonadapter.R;
import com.commonadapter.module.one.Bfragment.Efragment.activity.Circular2Activity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.CircularActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.CurveActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.DialogActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.DrawableActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.HandlerActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.ImageViewGroupActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.ItemViewGroupActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.ListViewActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.MyView;
import com.commonadapter.module.one.Bfragment.Efragment.activity.OkHttpActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.PopwActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.QQHealthActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.RecycleViewActivity;
import com.commonadapter.module.one.Bfragment.Efragment.activity.RecycleViewPage;
import com.commonadapter.module.one.Bfragment.Efragment.activity.SolidViewActivity;

/**
 * Created by Administrator on 2016/6/28.
 */
public class EFragment extends Fragment {
    private Button mViewl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.efragment_layout, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mViewl = (Button) view.findViewById(R.id.bt_view);
        mViewl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyView.class);
                startActivity(intent);
            }
        });


        view.findViewById(R.id.bt_solid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SolidViewActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.qq_health).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QQHealthActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemViewGroupActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_curve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurveActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_drawable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawableActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_image_viewgroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageViewGroupActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_circular).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CircularActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_circular2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Circular2Activity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_listView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListViewActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_recycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecycleViewActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_viewpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecycleViewPage.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_popw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopwActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HandlerActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),DialogActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),OkHttpActivity.class);
                startActivity(intent);
            }
        });


    }

}

package com.demo.module.one.Afragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.R;
import com.demo.common.bean.BaseBean;
import com.demo.common.bean.Bean;
import com.demo.common.pickerview.DataActivity;
import com.demo.common.touchevent.TouchEventActivity;
import com.demo.module.one.Afragment.activity.BrokenLineActivity;
import com.demo.module.one.Afragment.activity.ClockActivity;
import com.demo.module.one.Afragment.activity.ColumnActivity;
import com.demo.module.one.Afragment.activity.DeleteView;
import com.demo.module.one.Afragment.activity.DrawCircleActivity;
import com.demo.module.one.Afragment.activity.EventBusActivity;
import com.demo.module.one.Afragment.activity.LayoutInflateActivity;
import com.demo.module.one.Afragment.activity.MenuActivity;
import com.demo.module.one.Afragment.activity.MoveView;
import com.demo.module.one.Afragment.activity.OneActivity;
import com.demo.module.one.Afragment.activity.RecycleViewListActivity;
import com.demo.module.one.Afragment.activity.RemoveSideslipActivity;
import com.demo.module.one.Afragment.activity.ScrollActivity;
import com.demo.module.one.Afragment.activity.TimeViewGroupActivity;
import com.demo.module.one.Afragment.activity.TransverseScrollView;
import com.demo.module.one.Afragment.activity.TwoActivity;
import com.demo.module.one.Afragment.activity.ViewPagerActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2016/6/28.
 */
public class AFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        view.findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), OneActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        view.findViewById(R.id.btn_two).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), TwoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.btn_loadeLoade).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DrawCircleActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.view_page).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.home_viewpage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), TransverseScrollView.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.clock).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ClockActivity.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MenuActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.moveview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MoveView.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.loaderimage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DeleteView.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.touch).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), TouchEventActivity.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.remove_sideslip).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), RemoveSideslipActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.scroll).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScrollActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.broken_line).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), BrokenLineActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.column).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ColumnActivity.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DataActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.bt_LayoutInflate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), LayoutInflateActivity.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.bt_recycle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), RecycleViewListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.event_bus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EventBusActivity.class);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_in_bottom,0);
//            }
//        });
//
//        view.findViewById(R.id.bt_time).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),TimeViewGroupActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    public void onEventMainThread(BaseBean<Bean> event) {

        Log.i("LOG", "===AFragment===" + event.getBean().getTitle());

        //  Toast.makeText(this, event.getBean().getTitle()+"", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}

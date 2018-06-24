package com.commonadapter.module.one.Bfragment.Dfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.view.ScrollView;

/**
 * Created by Administrator on 2016/6/28.
 */
public class DFargment extends Fragment {
    private LinearLayout ll_moveview;
    private WindowManager wm;
    private int witch;
    private ScrollView scrollView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dfragment_layout, null);
        initView(view);
        return view;
    }


    private void initView(View view){
        ll_moveview = (LinearLayout) view.findViewById(R.id.ll_moveview);
        wm = getActivity().getWindowManager() ;
        witch = wm.getDefaultDisplay().getWidth() ;
        scrollView= (ScrollView) view.findViewById(R.id.scrollview);
        for ( int i = 0 ; i < 15 ; i++) {
            TextView textView = new TextView(getActivity()) ;
            textView.setGravity(Gravity. CENTER);
            textView.setBackgroundColor(getResources().getColor( R.color.colorAccent ) );
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams. WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ) ;
            p. width=witch /3;
            textView.setText(i + "*****");
            textView.setLayoutParams(p) ;
            ll_moveview .addView(textView);
        }


    }


}

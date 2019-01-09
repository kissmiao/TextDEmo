package com.demo.module.one.Bfragment.Efragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.demo.R;
import com.demo.common.adapter.RecyclePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whl on 16/9/19.
 */
public class RecycleViewPage extends Activity implements  ViewPager.OnPageChangeListener {
    private ViewPager vp_view_page;
    private List<View> mViewList=new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_page);

        vp_view_page = (ViewPager) findViewById(R.id.vp_view_page);

        View view1 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null, false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null, false);
        View view3 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null, false);
        View view4 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null, false);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);

        RecyclePageAdapter recyclePageAdapter=new RecyclePageAdapter(this,mViewList);

        vp_view_page.setAdapter(recyclePageAdapter);
        vp_view_page.setOnPageChangeListener(this);
        vp_view_page.setCurrentItem(0);


    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

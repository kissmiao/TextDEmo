package com.demo.module.one.Bfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.demo.R;
import com.demo.module.one.Bfragment.Dfragment.DFargment;
import com.demo.module.one.Bfragment.Efragment.fragment.EFragment;
import com.demo.module.one.Bfragment.Efragment.fragment.EFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class BFragment extends Fragment {
    private ViewPager vp_fragment_event;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        List<String> mTitleList = new ArrayList<>();
        mTitleList.add("热门微博");
        mTitleList.add("热门话题");


        vp_fragment_event = (ViewPager) view.findViewById(R.id.vp_fragment_event);
        List<Fragment> list = new ArrayList<>();
        list.add(new DFargment());
        list.add(new EFragment());
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), list, mTitleList);
        vp_fragment_event.setAdapter(adapter);


        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vp_fragment_event);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private class PageAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        private List<String> titles;

        public PageAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
            super(fm);
            this.list = list;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}

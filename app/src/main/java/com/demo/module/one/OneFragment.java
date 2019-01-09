package com.demo.module.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.demo.R;
import com.demo.module.one.Bfragment.BFragment;
import com.demo.module.one.Afragment.fragment.AFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class OneFragment extends Fragment  implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private RadioGroup host;
    private int currentItem = 0;

    private String TAG = "EventDistribute";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_one,null,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        host = (RadioGroup) view.findViewById(R.id.rg_host);
        host.setOnCheckedChangeListener(this);

        viewPager = (ViewPager) view.findViewById(R.id.vp_event);
        viewPager.setOffscreenPageLimit(1);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AFragment());
        fragmentList.add(new BFragment());

        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bt_product:
                if (currentItem != 0) {
                    viewPager.setCurrentItem(0, true);
                }
                break;
            case R.id.bt_ranking:
                if (currentItem != 1) {
                    viewPager.setCurrentItem(1, true);
                }
                break;
            default:
                break;
        }
    }


    private class PageAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public PageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.list = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    //viewPage 当前位置 position
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                currentItem = 0;
                host.check(R.id.bt_product);
                break;
            case 1:
                currentItem = 1;
                host.check(R.id.bt_ranking);
                break;
            default:
                break;
        }
    }


    // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
    @Override
    public void onPageScrollStateChanged(int state) {

    }


}

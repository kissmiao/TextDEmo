package com.modulea;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.ARouterManager;
import com.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterManager.AFragment)
public class AFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private RadioGroup host;
    private int currentItem = 0;

    private String TAG = "EventDistribute";
    /**
     * 产品
     */
    private RadioButton mBtProduct;
    /**
     * 排行
     */
    private RadioButton mBtRanking;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }

    @Override
    protected View initView(View parent) {
        initViews(parent);
        return parent;
    }


    private void initViews(View view) {
        host = (RadioGroup) view.findViewById(R.id.rg_host);
        host.setOnCheckedChangeListener(this);

        mBtProduct = (RadioButton) view.findViewById(R.id.bt_product);
        mBtRanking = (RadioButton) view.findViewById(R.id.bt_ranking);

        viewPager = (ViewPager) view.findViewById(R.id.vp_event);
        viewPager.setOffscreenPageLimit(1);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LeftFragment());
        fragmentList.add(new RightFragment());

        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mBtProduct.getId()) {
            if (currentItem != 0) {
                viewPager.setCurrentItem(0, true);
            }
        }
        if (checkedId == mBtRanking.getId()) {
            if (currentItem != 1) {
                viewPager.setCurrentItem(1, true);
            }
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

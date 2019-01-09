package com.demo;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.ARouterManager;
import com.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Fragment mFragmentTab01;
    private Fragment mFragmentTab02;
    private Fragment mFragmentTab03;

    private int mCurrentTabIndex = 0;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    private ImageView mIvTab1;
    /**
     * 首页
     */
    private TextView mTvTab1;
    private LinearLayout mLayoutTab1;
    private ImageView mIvTab2;
    /**
     * 会员
     */
    private TextView mTvTab2;
    private LinearLayout mLayoutTab2;
    private ImageView mIvTab3;
    /**
     * 我
     */
    private TextView mTvTab3;
    private LinearLayout mLayoutTab3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        initView();
        initData();
    }


    private void initData() {
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        mIvTab1.setSelected(true);
        mTvTab1.setSelected(true);
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int index) {
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mIvTab1.setSelected(true);
                mTvTab1.setSelected(true);

                if (mFragmentTab01 == null) {
                    //  如果Fragment为空，则创建一个并添加到界面上
                    mFragmentTab01 = (Fragment) ARouter.getInstance().build(ARouterManager.AFragment).navigation();
                    transaction.add(R.id.id_content, mFragmentTab01);
                } else {
                    // 如果Fragment不为空，则直接将它显示出来
                    transaction.show(mFragmentTab01);
                }
                mCurrentTabIndex = 0;
                break;
            case 1:
                mIvTab2.setSelected(true);
                mTvTab2.setSelected(true);
                if (mFragmentTab02 == null) {
                    mFragmentTab02 = (Fragment) ARouter.getInstance().build(ARouterManager.BFragment).navigation();
                    transaction.add(R.id.id_content, mFragmentTab02);
                } else {
                    transaction.show(mFragmentTab02);
                }
                mCurrentTabIndex = 1;
                break;
            case 2:
                mIvTab3.setSelected(true);
                mTvTab3.setSelected(true);
                if (mFragmentTab03 == null) {
                    mFragmentTab03 = (Fragment) ARouter.getInstance().build(ARouterManager.CFragment).navigation();

                    transaction.add(R.id.id_content, mFragmentTab03);
                } else {
                    transaction.show(mFragmentTab03);
                }
                mCurrentTabIndex = 2;
                break;
        }
        transaction.commit();
    }


    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn() {
        mIvTab1.setSelected(false);
        mTvTab1.setSelected(false);
        mIvTab2.setSelected(false);
        mTvTab2.setSelected(false);
        mIvTab3.setSelected(false);
        mTvTab3.setSelected(false);
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragmentTab01 != null) {
            transaction.hide(mFragmentTab01);
        }
        if (mFragmentTab02 != null) {
            transaction.hide(mFragmentTab02);
        }
        if (mFragmentTab03 != null) {
            transaction.hide(mFragmentTab03);
        }


    }


    private void initView() {
        mIvTab1 = (ImageView) findViewById(R.id.iv_tab1);
        mTvTab1 = (TextView) findViewById(R.id.tv_tab1);
        mLayoutTab1 = (LinearLayout) findViewById(R.id.layout_tab1);
        mLayoutTab1.setOnClickListener(this);

        mIvTab2 = (ImageView) findViewById(R.id.iv_tab2);
        mTvTab2 = (TextView) findViewById(R.id.tv_tab2);
        mLayoutTab2 = (LinearLayout) findViewById(R.id.layout_tab2);
        mLayoutTab2.setOnClickListener(this);

        mIvTab3 = (ImageView) findViewById(R.id.iv_tab3);
        mTvTab3 = (TextView) findViewById(R.id.tv_tab3);
        mLayoutTab3 = (LinearLayout) findViewById(R.id.layout_tab3);
        mLayoutTab3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_tab1:
                setTabSelection(0);
                break;
            case R.id.layout_tab2:
                setTabSelection(1);
                break;
            case R.id.layout_tab3:
                setTabSelection(2);
                break;
        }
    }


}

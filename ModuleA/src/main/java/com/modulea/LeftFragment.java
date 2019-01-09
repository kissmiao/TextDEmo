package com.modulea;

import android.view.View;
import android.widget.Button;

import com.base.BaseFragment;

public class LeftFragment extends BaseFragment  {
    private View view;
    /**
     * One
     */
    private Button mBtnOne;
    /**
     * two
     */
    private Button mBtnTwo;
    /**
     * 点赞
     */
    private Button mBtnLoadeLoade;
    /**
     * viewPage
     */
    private Button mViewPage;
    /**
     * 横向滑动
     */
    private Button mHomeViewpage;
    /**
     * clock
     */
    private Button mClock;
    /**
     * menu
     */
    private Button mMenu;
    /**
     * moveview
     */
    private Button mMoveview;
    /**
     * 删除Veiw
     */
    private Button mLoaderimage;
    /**
     * touch
     */
    private Button mTouch;
    /**
     * removeSideslipActivity
     */
    private Button mRemoveSideslip;
    /**
     * scroll
     */
    private Button mScroll;
    /**
     * 折线
     */
    private Button mBrokenLine;
    /**
     * 柱状图
     */
    private Button mColumn;
    /**
     * 时间选择器
     */
    private Button mTime;
    /**
     * LayoutInflate
     */
    private Button mBtLayoutInflate;
    /**
     * recycle
     */
    private Button mBtRecycle;
    /**
     * eventBus
     */
    private Button mEventBus;
    /**
     * 时间
     */
    private Button mBtTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    protected View initView(View parent) {

        return parent;
    }


}

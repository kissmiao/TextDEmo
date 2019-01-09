package com.modulea;

import android.view.View;

import com.base.BaseFragment;

public class LeftFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    protected View initView(View parent) {
        return parent;
    }
}

package com.moduleb;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.ARouterManager;
import com.base.BaseFragment;

@Route(path = ARouterManager.BFragment)
public class BFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    @Override
    protected View initView(View parent) {
        return parent;
    }
}

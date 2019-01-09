package com.modulec;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.ARouterManager;
import com.base.BaseFragment;

@Route(path = ARouterManager.CFragment)
public class CFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }

    @Override
    protected View initView(View parent) {
        return parent;
    }
}

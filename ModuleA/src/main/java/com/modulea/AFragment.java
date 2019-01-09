package com.modulea;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.ARouterManager;
import com.base.BaseFragment;
@Route(path = ARouterManager.AFragment)
public class AFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }

    @Override
    protected View initView(View parent) {
        return parent;
    }
}

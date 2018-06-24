package com.commonadapter.module;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.module.five.FiveFragment;
import com.commonadapter.module.four.FourFragment;
import com.commonadapter.module.one.OneFragment;
import com.commonadapter.module.three.ThreeFragment;
import com.commonadapter.module.tow.TowFragment;

/**
 * Created by Administrator on 2016/7/20.
 */
public class HomeActivity extends FragmentActivity {
    private LayoutInflater layoutInflater;
    private FragmentTabHost tabHost;
    private Class fragmentArray[] = {OneFragment.class, TowFragment.class, ThreeFragment.class, FourFragment.class, FiveFragment.class};
    private int mImageViewArray[] = {R.drawable.tab_wallet_btn, R.drawable.tab_current_btn,
            R.drawable.tab_terminal_btn, R.drawable.tab_financial_circles_btn, R.drawable.tab_mine_btn};
    private String[] tabName = new String[]{"首页", "玩赚", "投资", "发现", "我的"};


    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        initView();
    }

    private void initView() {
        layoutInflater = this.getLayoutInflater();
        tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < fragmentArray.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(i + "").setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, fragmentArray[i], null);
        }
        tabHost.getTabWidget().setDividerDrawable(
                new ColorDrawable(getResources().getColor(R.color.transparent_color)));
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
            final int index = i;
            tabHost.getTabWidget().getChildAt(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    tabHost.setCurrentTab(index);
                }
            });
        }
//        mFragmentManager=getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        TowFragment towFragment = new TowFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", 10010);
//        towFragment.setArguments(bundle);
//        mFragmentTransaction.add(towFragment,"towFragment");
//        mFragmentTransaction.commit();
    }

    private View getTabItemView(final int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView name = (TextView) view.findViewById(R.id.tabname);
        imageView.setImageResource(mImageViewArray[index]);
        name.setText(tabName[index]);
        return view;
    }
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(R.anim.slide_out_top,0);
    }


}

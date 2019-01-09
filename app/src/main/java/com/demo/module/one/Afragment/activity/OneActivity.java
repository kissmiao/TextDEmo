package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.demo.R;
import com.demo.common.adapter.OneItemTypeAdapter;
import com.demo.common.bean.Bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanghongliang on 16/3/22.
 */
public class OneActivity extends Activity implements View.OnClickListener {
    private ListView mListView;
    private OneItemTypeAdapter oneItemTypeAdapter;
    private List<Bean> mDatas = new ArrayList<Bean>();
    private Button delete, add;
    private LinearLayout ll_main;
    int height;
    int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        delete = (Button) findViewById(R.id.delete);
        add = (Button) findViewById(R.id.add);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.id_lv);
        int layoutResArrays[] = {R.layout.item_list};
        oneItemTypeAdapter = new OneItemTypeAdapter(OneActivity.this, layoutResArrays, mDatas);
        mListView.setAdapter(oneItemTypeAdapter);

        //当button的底部在整个屏幕的3/4以下的时候则将button固定在屏幕的3/4地方
        //什么时候所有的view已经测量完成?

        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        initDatas();

        //从ToWFragment 传递过来
        Bean bean = (Bean) getIntent().getParcelableExtra("bean");
        if (bean != null) {
            Log.i("LOG", "=====" + bean.getTitle());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.post(new Runnable() {
            @Override
            public void run() {
                onMeasureButton();
            }
        });

    }

    private void initDatas() {
        Bean bean = null;
        bean = new Bean("美女一只", "周三早上捡到妹子一只，在食堂二楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("美女一捆", "周三早上捡到妹子一捆，在食堂三楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("比卡丘一个", "周三早上捡到比卡丘一个，在食堂一楼", "10086", "20130240122");
        mDatas.add(bean);
        bean = new Bean("汉子一火车", "周三早上捡到xxxxxxxxxx，在xxx", "10086",
                "20130240122");
        mDatas.add(bean);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.smoothScrollToPositionFromTop(position, 0, 500);
            }
        });


    }


    /**
     * 可以为normal (正序）,reverse(倒序）,random(随机顺序）
     */
    private void startLayoutAnim() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(1);
        //为ListView设置LayoutAnimationController属性；
        mListView.setLayoutAnimation(lac);
    }

    int i = 1;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.add:
                Bean bean = new Bean("汉子一火车", "周三早上捡到xxxxxxxxxx，在xxx", i++ + "",
                        "20130240122");
                oneItemTypeAdapter.addData(bean);
                mListView.post(new Runnable() {

                    @Override
                    public void run() {
                        onMeasureButton();
                    }
                });


                break;
            case R.id.delete:
                oneItemTypeAdapter.deleteData();
                mListView.post(new Runnable() {

                    @Override
                    public void run() {
                        onMeasureButton();
                    }
                });

                break;

        }
    }

    public void onMeasureButton() {
        int h = getListViewHeight(mListView);
        if (h > height * 8 / 9) {
            Log.i("LOG", "大于8/9---" + h);
            //设置listView的高度
            RelativeLayout.LayoutParams list_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            list_params.height = height * 8 / 9;
            list_params.width = width;
            mListView.setLayoutParams(list_params);


            //设置button在固定的位置
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, height * 8 / 9, 0, 0);
            ll_main.setLayoutParams(params);

        } else {

            Log.i("LOG", "小于8/9---" + h);
            //设置button在固定的位置
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, h, 0, 0);
            ll_main.setLayoutParams(params);

        }
    }


    public int getListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        return totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
    }

}

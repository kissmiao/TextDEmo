package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongliang on 16/3/27.
 *
 * 最主要的两点
 * 1、将适配器中getCount()的值设置为较大的值
 * 2、onPageSelected中参数position， position % imagelist.size();得到滑动后的下标
 */
public class ViewPagerActivity extends Activity implements ViewPager.OnPageChangeListener {
    private List<ImageView> imagelist;
    private TextView tv;
    private LinearLayout ll;
    private int preEnablePositon = 0; // 前一个被选中的点的索引位置 默认情况下为0
    private String[] imagemiaoshu = {"巩俐不低俗，我就不能低俗", "朴树又回来了，再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀"};
    private ViewPager viewPager;
    private boolean isStop = false;  //是否停止子线程  不会停止

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_page_layout);
        init();
        // 开启线程无限自动移动
        Thread myThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    //每个两秒钟发一条消息到主线程，更新viewpager界面
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // 此方法在主线程中执行
                            //获取当前页再加1
                            int newindex = viewPager.getCurrentItem() + 1;
                            viewPager.setCurrentItem(newindex);
                        }
                    });
                }
            }
        });
           myThread.start(); // 用来更细致的划分  比如页面失去焦点时候停止子线程恢复焦点时再开启
    }


    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ll = (LinearLayout) findViewById(R.id.ll_point_group);
        tv = (TextView) findViewById(R.id.tv_image_miaoshu);

        imagelist = new ArrayList<ImageView>();
        int[] imageIDs = {R.mipmap.a, R.mipmap.b, R.mipmap.c,
                R.mipmap.d, R.mipmap.e,};

        ImageView iv;
        View view;
        LinearLayout.LayoutParams params;
        for (int id : imageIDs) {
            iv = new ImageView(this);
            iv.setBackgroundResource(id);
            imagelist.add(iv);

            // 每循环一次添加一个点到现形布局中
            view = new View(this);
            view.setBackgroundResource(R.drawable.point_background);
            params = new LinearLayout.LayoutParams(5, 5);
            params.leftMargin = 5;
            view.setEnabled(false);
            view.setLayoutParams(params);
            ll.addView(view); // 向线性布局中添加“点”
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);

        // 初始化图片描述和哪一个点被选中
        tv.setText(imagemiaoshu[0]);
        ll.getChildAt(0).setEnabled(true);

/*        // 初始化viewpager的默认position.MAX_value的一半
        int index = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imagelist.size());
        Log.i("LOG", "----" + index);*/

        viewPager.setCurrentItem(0); // 设置当前viewpager选中的pager页
        // ，会触发OnPageChangeListener中的onPageSelected方法

    }


    class MyAdapter extends PagerAdapter {

        /**
         * 销毁对象
         *
         * @param position 将要被销毁对象的索引位置
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imagelist.get(position % imagelist.size()));
        }

        /**
         * 初始化一个对象
         *
         * @param position 将要被创建的对象的索引位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 先把对象添加到viewpager中，再返回当前对象
            container.addView(imagelist.get(position % imagelist.size()));
            return imagelist.get(position % imagelist.size());
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        /**
         * 复用对象 true 复用对象 false 用的是object
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        Log.i("LOG", "position:" + position);
        // 取余后的索引
        int newPositon = position % imagelist.size();
        Log.i("LOG", "newPositon:" + newPositon);

        // 根据索引设置图片的描述
        tv.setText(imagemiaoshu[newPositon]);
        // 根据索引设置那个点被选中
        ll.getChildAt(newPositon).setEnabled(true);
        // 把上一个点设置为被选中
        ll.getChildAt(preEnablePositon).setEnabled(false);
        preEnablePositon = newPositon;

    }


    @Override
    protected void onDestroy() {
        isStop = true;
        super.onDestroy();
    }


}

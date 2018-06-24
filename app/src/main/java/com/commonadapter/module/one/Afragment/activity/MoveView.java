package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.commonadapter.R;
import com.commonadapter.common.adapter.MineDecorateProcessAdapter;
import com.commonadapter.common.view.DensityUtils;
import com.commonadapter.common.view.ScalePageTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongliang on 16/3/27.
 */
public class MoveView extends Activity {
    private WindowManager wm;
    private ViewPager vp_decorate_progress;
    private RelativeLayout layout_vp;
    private int witch;
    private int currentPage = 0;
    private int[] img_res = {R.mipmap.ic_stage_zhuangxiuzhunbei
            , R.mipmap.ic_stage_hetongqianyue
            , R.mipmap.ic_stage_zhuangxiukaogong
            , R.mipmap.ic_stage_shuidianshigong
            , R.mipmap.ic_stage_nimushigong
            , R.mipmap.ic_stage_youqishigong
            , R.mipmap.ic_stage_jungongyanshou
            , R.mipmap.ic_stage_shouhoubaozhang};
    private List<View> pages;
    private MineDecorateProcessAdapter adapter;

    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moveview_layout);
        wm = this.getWindowManager();
        witch = wm.getDefaultDisplay().getWidth();

        layout_vp = (RelativeLayout) findViewById(R.id.layout_vp);
        vp_decorate_progress = (ViewPager) findViewById(R.id.vp_decorate_progress);
        int width = witch / 2;
        int height = witch / 2;

        vp_decorate_progress.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        vp_decorate_progress.setPageTransformer(true,
                new ScalePageTransformer());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
        param.setMargins(0, DensityUtils.dp2px(this, 49), 0, 0);
        layout_vp.setLayoutParams(param);
        layout_vp.setGravity(Gravity.CENTER);

        layout_vp.setOnTouchListener(new View.OnTouchListener() {
            float fistx = 0;
            float fisty = 0;
            float lastx = 0;
            float lasty = 0;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        fistx = event.getX();
                        fisty = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        lastx = event.getX();
                        lasty = event.getY();
                        if (Math.abs(fistx - lastx) < 5 && Math.abs(fisty - lasty) < 5) {
                            //当点击左边或者右边的View的时候，横纵坐标位移小于5，直接改变当前position
                            if (lastx < witch / 4) {
                                vp_decorate_progress.setCurrentItem(currentPage - 1);
                            } else if (lastx > witch * 3 / 4) {
                                vp_decorate_progress.setCurrentItem(currentPage + 1);
                            }
                        }
                        //返回这个是将dangqianView的事件分发给ViewPage,实现ViewPage的setOnPageChangeListener方法后面由它处理
                        return vp_decorate_progress.dispatchTouchEvent(event);
                    case MotionEvent.ACTION_CANCEL:
                           //当手指在屏幕上移动直接移动出屏幕的时候触发
                        lastx = event.getX();
                        lasty = event.getY();
                        if (Math.abs(fistx - lastx) < 5 && Math.abs(fisty - lasty) < 5) {
                            if (lasty < witch / 4) {
                                vp_decorate_progress.setCurrentItem(currentPage - 1);
                            } else if (lasty > witch * 3 / 4) {
                                vp_decorate_progress.setCurrentItem(currentPage + 1);
                            }
                        }
                        return vp_decorate_progress.dispatchTouchEvent(event);
                    default:
                        break;
                }
                return vp_decorate_progress.dispatchTouchEvent(event);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        // TODO Auto-generated method stub
        pages = new ArrayList<View>();
        adapter = new MineDecorateProcessAdapter();
        for (int i = 0; i < 8; i++) {
            ImageView img = new ImageView(this);
            img.setTag(i);

            img.setImageResource(img_res[i]);
            img.setPadding(0, 40, 0, 40);
            pages.add(img);
        }
        adapter.setPages(pages);
        vp_decorate_progress.setAdapter(adapter);
        vp_decorate_progress.setOffscreenPageLimit(pages.size());
        try {
            Field leftEdgeField = vp_decorate_progress.getClass()
                        .getDeclaredField("mLeftEdge");
                Field rightEdgeField = vp_decorate_progress.getClass()
                        .getDeclaredField("mRightEdge");
                if (leftEdgeField != null && rightEdgeField != null) {
                    leftEdgeField.setAccessible(true);
                    rightEdgeField.setAccessible(true);
                    leftEdge = (EdgeEffectCompat) leftEdgeField
                        .get(vp_decorate_progress);
                rightEdge = (EdgeEffectCompat) rightEdgeField
                        .get(vp_decorate_progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        vp_decorate_progress
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int arg0) {
                        // TODO Auto-generated method stub
                        currentPage = arg0;
                        adapter.setCurrentPosition(arg0);

                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                        // TODO Auto-generated method stub
                        if (leftEdge != null && rightEdge != null) {
                            leftEdge.finish();
                            rightEdge.finish();
                            leftEdge.setSize(0, 0);
                            rightEdge.setSize(0, 0);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                        // TODO Auto-generated method stub

                    }
                });
    }
}

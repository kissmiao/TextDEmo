package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.view.DragView;
import com.commonadapter.common.view.FllowerAnimation;


/**
 * Created by wanghongliang on 16/3/23.
 */
public class DrawCircleActivity extends Activity {
    private DragView iv_loadeimage;
    private ImageView iv_zan;
    private FllowerAnimation view_xin;
    private DisplayMetrics dm;
    private int mWidth, mHeight;
    private RelativeLayout ll_dianzan;
    private TextView tv_loadimage_text;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;
    //与UI线程管理的handler
    private Handler mHandler = new Handler();
    private static final int MSG_UPDATE_INFO = 0x110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadimage_layout);
        tv_loadimage_text = (TextView) findViewById(R.id.tv_loadimage_text);

        view_xin = new FllowerAnimation(this);
        dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;

        iv_zan = (ImageView) findViewById(R.id.iv_zan);
        ll_dianzan = (RelativeLayout) findViewById(R.id.rl_dianzan);

        ViewTreeObserver vto2 = iv_zan.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_zan.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = iv_zan.getHeight() / 2;
                int width = iv_zan.getWidth() / 2;
                int left = (int) iv_zan.getX() + width;
                int top = (int) iv_zan.getY() + height;
                view_xin.setStartX(left, top);
                view_xin.init(DrawCircleActivity.this);
                view_xin.requestLayout();
            }
        });

        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int height = iv_zan.getHeight() / 2;

                int width = iv_zan.getWidth() / 2;
                int left = (int) iv_zan.getX() + width;
                int top = (int) iv_zan.getY() + height;
                view_xin = new FllowerAnimation(DrawCircleActivity.this, left, top);
                ll_dianzan.addView(view_xin);
                view_xin.startAnimation();
            }
        });

        initBackThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

    }

    private void initBackThread() {
        //这一个HandlerThread运行在主线程,会得到一个主线程loop
        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();

        //这里是使用HandlerThread的looper来实现的handler
        //getMainLooper()来创建一个与主线程绑定的handler
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                checkForUpdate();
                if (isUpdateInfo) {
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 1000);
                }
            }
        };
    }

    private void checkForUpdate() {
        try {
            Thread.sleep(1000);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    tv_loadimage_text.setText(Html.fromHtml(result));
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }


}

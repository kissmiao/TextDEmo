package com.demo.module.one.Afragment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.R;
import com.demo.common.view.ListViewCompat;
import com.demo.common.view.SlideView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class RemoveSideslipActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener,
        SlideView.OnSlideListener {


    /**使用SlideView将要显示的Item和滑动出的删除View包裹在一起，包裹的时机是Adapter的getView方法中。
     * 首先 SlideView判空，然后new一个，在获得ItemVIew,将ItemView add到SlideView的一个LinearLayout中
     * 点击事件的监听发生在listView中，在ListView中监听onTouchEvent(),根据坐标获得点击的item数据，并在Item中获得数据所相对应的
     * SlideView，SlideView不等于空 则slideView.onRequireTouchEvent(event)方法传递事件
     *
     */


    /**
     * 如果是以普通的方式去实现
     * <p/>
     * ListView中包含一个子View,在上下滚动的过程中，事件是由ListView处理，当左右滑动的过程中是由ListView的子View处理，
     */

    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_removesideslip);
        initView();
    }

    private void initView() {
        mListView = (ListViewCompat) findViewById(R.id.list);

        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.mipmap.default_qq_avatar;
                item.title = "腾讯新闻";
                item.msg = "青岛爆炸满月：大量鱼虾死亡";
                item.time = "晚上18:18";
            } else {
                item.iconRes = R.mipmap.wechat_icon;
                item.title = "微信团队";
                item.msg = "欢迎你使用微信";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        mListView.setAdapter(new SlideAdapter());
        mListView.setOnItemClickListener(this);
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(RemoveSideslipActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(RemoveSideslipActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(RemoveSideslipActivity.this);

            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "onClick v=" + v);
        }
    }
}

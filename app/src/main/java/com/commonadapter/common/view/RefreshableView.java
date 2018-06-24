package com.commonadapter.common.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.adapter.RecyclePageAdapter;
import com.commonadapter.common.anim.PullViewHolderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 可进行下拉刷新的自定义控件。
 *
 * @author guolin
 */
public class RefreshableView extends LinearLayout implements OnTouchListener {

    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;

    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;

    /**
     * 下拉头部回滚的速度
     */
    public static final int SCROLL_SPEED = -20;

    /**
     * 一分钟的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;

    /**
     * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
     */
    private static final String UPDATED_AT = "updated_at";

    /**
     * 下拉刷新的回调接口
     */
    private PullToRefreshListener mListener;

    /**
     * 用于存储上次更新时间
     */
    private SharedPreferences preferences;

    /**
     * 下拉头的View
     */
    private View header;

    /**
     * 需要去下拉刷新的ListView
     */
    private RecyclerView recyclerView;


    /**
     * 下拉头的布局参数
     */
    private MarginLayoutParams headerLayoutParams;

    /**
     * 上次更新时间的毫秒值
     */
    private long lastUpdateTime;

    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private int currentStatus = STATUS_REFRESH_FINISHED;
    ;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private int lastStatus = currentStatus;

    /**
     * 手指按下时的屏幕纵坐标
     */
    private float yDown;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
     */
    private boolean ableToPull;


    /**
     * 下拉头中的ViewPage
     */
    private View ll_item_pull_view_page;

    /**
     * 下拉头中的title
     */

    private TextView tv_pull_title;

    /**
     * 下拉头中的money
     */

    private TextView tv_pull_money;


    private Context mContext;
    private List<View> mViewList = new ArrayList<View>();
    private ViewPager vp_item_pull_label;
    private LinearLayout ll_item_pull_point_group;

    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        vp_item_pull_label = (ViewPager) header.findViewById(R.id.vp_item_pull_label);
        ll_item_pull_point_group = (LinearLayout) header.findViewById(R.id.ll_item_pull_point_group);

        setOrientation(VERTICAL);
        addView(header, 0);
        init();
    }

    private void init() {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_viewpage, null);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_viewpage, null);
        View view3 = LayoutInflater.from(mContext).inflate(R.layout.item_viewpage, null);
        View view4 = LayoutInflater.from(mContext).inflate(R.layout.item_viewpage, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);

        RecyclePageAdapter adapter = new RecyclePageAdapter(mContext, mViewList);
        vp_item_pull_label.setAdapter(adapter);
        vp_item_pull_label.setOffscreenPageLimit(4);
        vp_item_pull_label.setCurrentItem(0);

        //添加下面的小圆点
        ImageView iv;
        View view;
        LinearLayout.LayoutParams params;

        for (int i = 0; i < mViewList.size(); i++) {
            // 每循环一次添加一个点到现形布局中
            view = new View(mContext);
            view.setBackgroundResource(R.drawable.point_background);
            params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 20;
            view.setEnabled(false);
            view.setLayoutParams(params);
            ll_item_pull_point_group.addView(view); // 向线性布局中添加“点”
        }
        ll_item_pull_point_group.getChildAt(0).setEnabled(true);

        //viewPage添加监听
        vp_item_pull_label.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public int preEnablePositon = 0; // 前一个被选中的点的索引位置 默认情况下为0

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 取余后的索引
                int newPositon = position % mViewList.size();
                // 根据索引设置那个点被选中
                ll_item_pull_point_group.getChildAt(newPositon).setEnabled(true);
                // 把上一个点设置为被选中
                ll_item_pull_point_group.getChildAt(preEnablePositon).setEnabled(false);
                preEnablePositon = newPositon;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


//316

    /**
     * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            hideHeaderHeight = -header.getHeight();
            Log.i("LOG", "hideHeaderHeight======" + hideHeaderHeight);

            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();

            ll_item_pull_view_page = header.findViewById(R.id.ll_item_pull_view_page);

            tv_pull_title = (TextView) header.findViewById(R.id.tv_pull_title);
            tv_pull_money = (TextView) header.findViewById(R.id.tv_pull_money);

            headerLayoutParams.topMargin = hideHeaderHeight;
            recyclerView = (RecyclerView) getChildAt(1);
            recyclerView.setOnTouchListener(this);
            loadOnce = true;
        }
    }

    /**
     * 当ListView被触摸时调用，其中处理了各种下拉刷新的具体逻辑。
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        setIsAbleToPull(event);
        if (ableToPull) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float yMove = event.getRawY();
                    int distance = (int) (yMove - yDown);
                    Log.i("LOG", "distance====" + distance);

                    // 如果手指是下滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件
                    if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
                        return false;
                    }
                    if (distance < touchSlop) {
                        return false;
                    }
                    if (currentStatus != STATUS_REFRESHING) {
                        Log.i("LOG", "headerLayoutParams.topMargin" + headerLayoutParams.topMargin);

                        if (headerLayoutParams.topMargin > 0) {
                            //header出现了, 回弹.刷新在隐藏
                            currentStatus = STATUS_RELEASE_TO_REFRESH;
                            return false;
                        } else {
                            //整个header没有拉出来,直接回弹回去
                            currentStatus = STATUS_PULL_TO_REFRESH;
                        }
                        // 通过偏移下拉头的topMargin值，来实现下拉效,滑动到2倍的高度就不在滑动

                        headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;

                        header.setLayoutParams(headerLayoutParams);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                        // 松手时如果是释放立即刷新状态，就去调用正在刷新的任务


                        int topMargin = headerLayoutParams.topMargin;


                        Log.i("LOG", "topMargin1:====" + topMargin);
                        while (true) {
                            topMargin = topMargin + SCROLL_SPEED;
                            Log.i("LOG", "topMargin2:====" + topMargin);

                            if (topMargin <= 0) {
                                topMargin = 0;
                                break;
                            }
                            headerLayoutParams.topMargin = topMargin;
                            header.setLayoutParams(headerLayoutParams);
                            //控制回弹时间
                        }
                        currentStatus = STATUS_REFRESHING;
                        headerLayoutParams.topMargin = 0;
                        header.setLayoutParams(headerLayoutParams);

                        if (mListener != null) {
                            mListener.onRefresh(header, ll_item_pull_view_page);
                        }
                        //   new RefreshingTask().execute();
                    } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
                        // 松手时如果是下拉状态，就去调用隐藏下拉头的任务
                        new HideHeaderTask().execute();
                    }
                    break;
            }
            // 时刻记得更新下拉头中的信息
            if (currentStatus == STATUS_PULL_TO_REFRESH
                    || currentStatus == STATUS_RELEASE_TO_REFRESH) {
                //     updateHeaderView();
                // 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
                recyclerView.setPressed(false);
                recyclerView.setFocusable(false);
                recyclerView.setFocusableInTouchMode(false);
                lastStatus = currentStatus;
                // 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
                return true;
            }
        }
        return false;
    }

    /**
     * 给下拉刷新控件注册一个监听器。
     *
     * @param listener 监听器的实现。
     * @param id       为了防止不同界面的下拉刷新在上次更新时间上互相有冲突， 请不同界面在注册下拉刷新监听器时一定要传入不同的id。
     */
    public void setOnRefreshListener(PullToRefreshListener listener, int id) {
        mListener = listener;
        mId = id;
    }

    /**
     * 当所有的刷新逻辑完成后，记录调用一下，否则你的ListView将一直处于正在刷新状态。
     */
    public void finishRefreshing() {
        currentStatus = STATUS_REFRESH_FINISHED;
        preferences.edit().putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
        new HideHeaderTask().execute();
    }

    /**
     * 根据当前ListView的滚动状态来设定 {@link #ableToPull}
     * 的值，每次都需要在onTouch中第一个执行，这样可以判断出当前应该是滚动ListView，还是应该进行下拉。
     *
     * @param event
     */
    private void setIsAbleToPull(MotionEvent event) {
        View firstChild = recyclerView.getChildAt(0);
        if (firstChild != null) {
            //	int firstVisiblePos = recyclerView.getFirstVisiblePosition();
            int firstVisiblePos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
                if (!ableToPull) {
                    yDown = event.getRawY();
                }
                // 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新
                ableToPull = true;
            } else {
                if (headerLayoutParams.topMargin != hideHeaderHeight) {
                    headerLayoutParams.topMargin = hideHeaderHeight;
                    header.setLayoutParams(headerLayoutParams);
                }
                ableToPull = false;
            }
        } else {
            // 如果ListView中没有元素，也应该允许下拉刷新
            ableToPull = true;
        }
    }


    /**
     * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。
     *
     * @author guolin
     */
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;


            Log.i("LOG", "topMargin1:====" + topMargin);
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                Log.i("LOG", "topMargin2:====" + topMargin);

                if (topMargin <= 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                //控制回弹时间
            }
            currentStatus = STATUS_REFRESHING;
            publishProgress(0);

            if (mListener != null) {
                mListener.onRefresh(header, ll_item_pull_view_page);
            }
            return null;
        }

        //更新UI
        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);
            PullViewHolderUtil.openH(header, ll_item_pull_view_page, true);
        }

    }

    /**
     * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
     *
     * @author guolin
     */
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= hideHeaderHeight) {
                    topMargin = hideHeaderHeight;
                    break;
                }
                publishProgress(topMargin);
                sleep(10);
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            headerLayoutParams.topMargin = topMargin[0];
            header.setLayoutParams(headerLayoutParams);


        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            headerLayoutParams.topMargin = topMargin;
            header.setLayoutParams(headerLayoutParams);
            currentStatus = STATUS_REFRESH_FINISHED;
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param time 指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。
     *
     * @author guolin
     */
    public interface PullToRefreshListener {

        /**
         * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
         */
        void onRefresh(View header, View view);

    }

}

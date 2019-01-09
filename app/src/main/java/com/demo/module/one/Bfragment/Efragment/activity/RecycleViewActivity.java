package com.demo.module.one.Bfragment.Efragment.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.R;
import com.demo.common.adapter.MainAdapter;
import com.demo.common.adapter.MyPagerAdapter;
import com.demo.common.anim.ExpandableViewHoldersUtil;
import com.demo.common.anim.PullViewHolderUtil;
import com.demo.common.bean.KeyboardUtil2;
import com.demo.common.bean.RecyBean;
import com.demo.common.view.DividerItemDecoration;
import com.demo.common.view.RefreshableView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by whl on 16/9/12.
 */
public class RecycleViewActivity extends Activity implements View.OnClickListener {
    private MainAdapter adapter;
    private RecyclerView rv_recyclerview;
    private RelativeLayout rl_list_context;
    private TextView tv_text;
    private int statusBarHeight;
    private int itemHeight;
    private WindowManager wm;
    private int mHeight;
    private int textViewHeight;
    private int moveHeight;
    private boolean tag = false;
    private ExpandableViewHoldersUtil.KeepOneH<MainAdapter.MainViewHolder> keepOne;
    private MainAdapter.MainViewHolder mainViewHolder;
    private int position;
    private List<RecyBean> mDatas = new ArrayList<RecyBean>();
    private List<View> mViewList = new ArrayList<View>();
    private KeyboardUtil2 keyboardUtil;
    private View keyboard_view;

    private ImageView iv_keyboard_reduce;
    private ImageView iv_keyboard_plus;
    private TextView tv_keyboard_money_income;
    private TextView tv_keyboard_money_expenditure;
    private ImageView iv_keyboard_delete;
    private ImageView iv_keyboard_complete;

    private RefreshableView refreshableView;

    private boolean PULL = false;
    private boolean TOUCH = true;

    //记录是下拉的还是点击的
    private boolean isPullOrTouch = false;





    private ViewPager vp_for_days;
    private ArrayList<View> viewsForDays=new  ArrayList<View>();
    private int defaultItem=99;
    private int maxListViewHeight=0;
    private float viewpagerItemHeight=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycleview);

        initKeyboard();

        statusBarHeight = getStatusBarHeight(this);
        wm = this.getWindowManager();
        mHeight = wm.getDefaultDisplay().getHeight();
        itemHeight = Dp2Px(RecycleViewActivity.this, 50);
        textViewHeight = Dp2Px(RecycleViewActivity.this, 100);
        rl_list_context = (RelativeLayout) findViewById(R.id.rl_rect_context);
        tv_text = (TextView) findViewById(R.id.tv_text);
        initViewPage();
        init();
        initData();

    }

    private ViewPager.OnPageChangeListener pageChangeListenerForVpDays;

    private  void initViewPage(){

        for (int i = 0; i < 3; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_ac_main, null);
            viewsForDays.add(view);
        }
        MyPagerAdapter adapterForTheOtherOne = new MyPagerAdapter(viewsForDays);
        vp_for_days = (ViewPager) findViewById(R.id.vp_for_days_ac_main);
        vp_for_days.setAdapter(adapterForTheOtherOne);
        vp_for_days.setCurrentItem(defaultItem);


        pageChangeListenerForVpDays = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int currentItem) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };
        vp_for_days.addOnPageChangeListener(pageChangeListenerForVpDays);
    }



    private void  initData(){

        rv_recyclerview = (RecyclerView) viewsForDays.get(0).findViewById(R.id.rl_item_ac_main);
        rv_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recyclerview.setLayoutManager(layoutManager);
        adapter = new MainAdapter(this, mDatas, mViewList);
        rv_recyclerview.setAdapter(adapter);

        adapter.setOnItemClickListener(new MainAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MainAdapter.MainViewHolder mainViewHolder, ExpandableViewHoldersUtil.KeepOneH<MainAdapter.MainViewHolder> keepOne, int position) {
                tag = keepOne.getOpenTag();

                maxListViewHeight=rv_recyclerview.getMeasuredHeight();
//                Log.i("log","firstHeight+"+rv_recyclerview.getHeight()+"---"+rv_recyclerview.getBottom());
                if (!tag) {
                    money_edit_text = mainViewHolder.tv_item_money;
                    RecycleViewActivity.this.keepOne = keepOne;
                    RecycleViewActivity.this.mainViewHolder = mainViewHolder;
                    RecycleViewActivity.this.position = position;
                    keepOne.toggle(mainViewHolder);
                    viewpagerItemHeight=mViewList.get(0).getMeasuredHeight()/1.5f;//viewpageritem的高度
                    int Pos[] = {-1, -1}; //保存当前坐标的数组
                    view.getLocationOnScreen(Pos); //获取选中的 Item 在屏幕中的位置，以左上角为原点 (0, 0)
                    int OldListY = (int) Pos[1]; //我们只取 Y 坐标就行了
                    moveHeight = OldListY - statusBarHeight - 30;
                    doPlaySequentiallyAnimator(position);
                    showKeyboard(TOUCH);
                }
            }
        });

        refreshableView = (RefreshableView) viewsForDays.get(0).findViewById(R.id.refreshableView);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh(View header, View view) {
                //从接口返回一个View
                ll_item_pull_view_page = view;
                mHeader=header;
                money_edit_text = (TextView) findViewById(R.id.tv_pull_money);
                money_edit_text.setText("0.00");
                PullViewHolderUtil.openH(header, view, true);
                viewpagerItemHeight = mViewList.get(0).getMeasuredHeight() / 1.5f;// viewpageritem的高度
                doPlaySequentiallyAnimator(0);
                showKeyboard(PULL);
            }
        }, 0);


    }



    /**
     * 下拉显示出的控件
     */
    private View ll_item_pull_view_page;

    private View  mHeader;


    /**
     * 隐藏下拉出的控件
     */
    public void hidePullView() {
        int visibility = ll_item_pull_view_page.getVisibility();
        if (visibility == View.VISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
            ll_item_pull_view_page.startAnimation(anim);
            ll_item_pull_view_page.setVisibility(View.GONE);
        }
    }


    private void initKeyboard() {
        keyboard_view = findViewById(R.id.keyboard_view);

        keyboard_view.findViewById(R.id.tv_keyboard_spot).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_zero).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_one).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_tow).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_three).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_four).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_five).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_sex).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_seven).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_eight).setOnClickListener(this);
        keyboard_view.findViewById(R.id.tv_keyboard_nine).setOnClickListener(this);

        iv_keyboard_reduce = (ImageView) keyboard_view.findViewById(R.id.iv_keyboard_reduce);
        iv_keyboard_plus = (ImageView) keyboard_view.findViewById(R.id.iv_keyboard_plus);
        tv_keyboard_money_income = (TextView) keyboard_view.findViewById(R.id.tv_keyboard_money_income);
        tv_keyboard_money_expenditure = (TextView) keyboard_view.findViewById(R.id.tv_keyboard_money_expenditure);
        iv_keyboard_delete = (ImageView) keyboard_view.findViewById(R.id.iv_keyboard_delete);
        iv_keyboard_complete = (ImageView) keyboard_view.findViewById(R.id.iv_keyboard_complete);

        iv_keyboard_reduce.setOnClickListener(this);
        iv_keyboard_plus.setOnClickListener(this);
        tv_keyboard_money_income.setOnClickListener(this);
        tv_keyboard_money_expenditure.setOnClickListener(this);
        iv_keyboard_delete.setOnClickListener(this);
        iv_keyboard_complete.setOnClickListener(this);

    }

    private void init() {
        RecyBean recyBean = null;
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "电影", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "社交", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "社交", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "一般", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);
        recyBean = new RecyBean(12, "交通", "10086.00");
        mDatas.add(recyBean);


        View view1 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.item_viewpage, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
    }

    public void verticalRun(final View view, int height, int moveHeight) {
        ValueAnimator animator = ValueAnimator.ofFloat(height, moveHeight);
        animator.setTarget(view);
        animator.setDuration(600).start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    //控制是否需要finish当界面,当有View是展开的状态则不需关闭
    private boolean BACK_FINISH = true;

    @Override
    public void onBackPressed() {

        if (ll_item_pull_view_page != null) {
            int visibility = ll_item_pull_view_page.getVisibility();
            BACK_FINISH = true;
            if (visibility == View.VISIBLE) {
                //  hidePullView();

                PullViewHolderUtil.closeH(mHeader, ll_item_pull_view_page, true);
                refreshableView.finishRefreshing();

                hideKeyboard();
                backPlaySequentiallyAnimator();
                BACK_FINISH = false;

                RecyBean recyBean = null;
                recyBean = new RecyBean(12, "音乐", "00099");
                adapter.addItem(recyBean, 0);

            }
        }

        if (keepOne != null) {
            tag = keepOne.getOpenTag();
            BACK_FINISH = true;
            //如果是展开状态并且软件盘是弹出，则关闭软件盘关闭展开
            if (tag) {
//                rl_list_context.setScrollY(0);


//                RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams1.height=maxListViewHeight;
//
//                layoutParams1.addRule(RelativeLayout.BELOW,R.id.tv_text);
//                vp_for_days.setLayoutParams(layoutParams1);
                onKeyDownListener();
                BACK_FINISH = false;
            }
        }
        if (BACK_FINISH) {
            finish();
        }
    }


    /**
     * dip转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void doPlaySequentiallyAnimator(final int position) {

        ObjectAnimator rl_list_contextY = ObjectAnimator.ofFloat(rl_list_context, "translationY", 0, -viewpagerItemHeight);
        //  ObjectAnimator rl_list_recycle = ObjectAnimator.ofFloat(refreshableView, "translationY", 0, -190);
        //     ObjectAnimator rl_list_contextHeight = ObjectAnimator.ofInt(new ViewWrapper(rl_list_context), "height", mHeight, a);
        rl_list_contextY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v= -(float) animation.getAnimatedValue()/viewpagerItemHeight;

                if(position>0){

                    int item=rv_recyclerview.getChildAt(1).getTop()-rv_recyclerview.getChildAt(0).getHeight();


                    vp_for_days.scrollTo(0,
                            (int)
                                    (((rv_recyclerview.getChildAt(0).getHeight()+item)*position+
                                            rv_recyclerview.getChildAt(0).getTop())* v)+10);
                }else{
                    vp_for_days.scrollTo(0, (int) ((rv_recyclerview.getChildAt(0).getHeight()*position)*v));
                }
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rl_list_contextY);
        animatorSet.setDuration(600);
        animatorSet.start();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        FrameLayout.LayoutParams layoutParams1=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if(position>0){
            int item=rv_recyclerview.getChildAt(1).getTop()-rv_recyclerview.getChildAt(0).getHeight();
            layoutParams1.height=(displayMetrics.heightPixels-getStatusBarHeight(RecycleViewActivity.this)-vp_for_days.getTop())
                    +(int) viewpagerItemHeight*2+((rv_recyclerview.getChildAt(0).getHeight()+item)*position);
        }else{
            layoutParams1.height=(displayMetrics.heightPixels-getStatusBarHeight(RecycleViewActivity.this)-vp_for_days.getTop())+(int) viewpagerItemHeight*2+(rv_recyclerview.getChildAt(0).getHeight()*position);
        }


        Log.i("log","firstHeight---+"+rv_recyclerview.getHeight()+"---"+rv_recyclerview.getBottom());
        Log.i("log","rl_list_context---+"+rl_list_context.getHeight());

        rl_list_context.setLayoutParams(layoutParams1);
        rl_list_context.requestLayout();


    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            float v= (float) msg.obj;
            vp_for_days.scrollTo(0, (int) ((rv_recyclerview.getChildAt(0).getHeight()*position)*( v/viewpagerItemHeight)));
//        rv_recyclerview.scrollTo(0, rv_recyclerview.getChildAt(position).getTop());

//        rl_list_context.setScrollY((int) viewpagerItemHeight);
//        rv_recyclerview.smoothScrollBy(0, rv_recyclerview.getChildAt(position).getTop());
//        if(position>0){
//            vp_for_days.scrollTo(0,rv_recyclerview.getChildAt(0).getHeight()*position);
//        }else{
//            vp_for_days.scrollTo(0,rv_recyclerview.getChildAt(0).getHeight()*position+10);
//        }
//
//        getHandler.sendEmptyMessageDelayed(0,300);

        }
    };
    Handler getHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            rv_recyclerview.requestLayout();
//            vp_for_days.requestLayout();
            DisplayMetrics displayMetrics=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            FrameLayout.LayoutParams layoutParams1=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams1.height=(displayMetrics.heightPixels-getStatusBarHeight(RecycleViewActivity.this)-vp_for_days.getTop())+(int) viewpagerItemHeight*2+(rv_recyclerview.getChildAt(0).getHeight()*position);

            Log.i("log","firstHeight---+"+rv_recyclerview.getHeight()+"---"+rv_recyclerview.getBottom());
            Log.i("log","rl_list_context---+"+rl_list_context.getHeight());

            rl_list_context.setLayoutParams(layoutParams1);
            rl_list_context.requestLayout();
            Log.i("log","---+"+vp_for_days.getHeight());
//            Log.i("log","-- layoutParams1.height-+"+ layoutParams1.height);
        }
    };
    private void backPlaySequentiallyAnimator() {
        int a = mHeight + textViewHeight + position * itemHeight;
        int b = adapter.getItemCount() * itemHeight + textViewHeight * 2;

        ObjectAnimator rl_list_contextY = ObjectAnimator.ofFloat(rl_list_context, "translationY", -viewpagerItemHeight, 0);
        //    ObjectAnimator rl_list_recycle = ObjectAnimator.ofFloat(refreshableView, "translationY", -190, 0);
        //   ObjectAnimator rl_list_contextHeight = ObjectAnimator.ofInt(new ViewWrapper(rl_list_context), "height", a, mHeight);
        rl_list_contextY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v= -(float) animation.getAnimatedValue();
                Log.i("log","==="+v);
                vp_for_days.scrollTo(0, (int) ((rv_recyclerview.getChildAt(0).getHeight()*position)*(v/viewpagerItemHeight)));

            }
        });
        rl_list_contextY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                DisplayMetrics displayMetrics=new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                FrameLayout.LayoutParams layoutParams1=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                layoutParams1.height=displayMetrics.heightPixels-getStatusBarHeight(RecycleViewActivity.this)-vp_for_days.getTop()+(int)viewpagerItemHeight+10;

                rl_list_context.setLayoutParams(layoutParams1);
//                rl_list_context.requestLayout();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rl_list_contextY);
        animatorSet.setDuration(600);
        animatorSet.start();
    }

    /**
     * 软键盘隐藏
     */
    public void hideKeyboard() {
        int visibility = keyboard_view.getVisibility();
        if (visibility == View.VISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
            keyboard_view.setVisibility(View.INVISIBLE);
            keyboard_view.startAnimation(anim);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("LOG","------------");
        return super.onTouchEvent(event);
    }


    /**
     * 软键盘展示
     */
    public void showKeyboard(boolean flag) {
        int visibility = keyboard_view.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
            keyboard_view.startAnimation(anim);
            keyboard_view.setVisibility(View.VISIBLE);
        }
        if (flag) {
            tv_keyboard_money_income.setVisibility(View.GONE);
            iv_keyboard_reduce.setVisibility(View.VISIBLE);
            iv_keyboard_plus.setVisibility(View.VISIBLE);
            iv_keyboard_complete.setImageResource(R.mipmap.keyboard_determine);
        } else {
            tv_keyboard_money_expenditure.setVisibility(View.VISIBLE);
            tv_keyboard_money_income.setVisibility(View.GONE);
            iv_keyboard_reduce.setVisibility(View.GONE);
            iv_keyboard_plus.setVisibility(View.GONE);
            iv_keyboard_complete.setImageResource(R.mipmap.cancel);
        }
        isPullOrTouch = flag;

    }

    private TextView money_edit_text;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_keyboard_spot:
                onTextChange(KEY_SPOT);
                break;
            case R.id.tv_keyboard_zero:
                onTextChange("0");
                break;
            case R.id.tv_keyboard_one:
                onTextChange("1");
                break;
            case R.id.tv_keyboard_tow:
                onTextChange("2");
                break;
            case R.id.tv_keyboard_three:
                onTextChange("3");
                break;
            case R.id.tv_keyboard_four:
                onTextChange("4");
                break;
            case R.id.tv_keyboard_five:
                onTextChange("5");
                break;
            case R.id.tv_keyboard_sex:
                onTextChange("6");
                break;
            case R.id.tv_keyboard_seven:
                onTextChange("7");
                break;
            case R.id.tv_keyboard_eight:
                onTextChange("8");
                break;
            case R.id.tv_keyboard_nine:
                onTextChange("9");
                break;
            case R.id.iv_keyboard_reduce:  //减
                onTextChange(KEY_REDUCE);
                break;
            case R.id.iv_keyboard_plus:   //加
                onTextChange(KEY_PLUS);
                break;
            case R.id.tv_keyboard_money_income: //收入
                onTextChange(KEY_MONEY_INCOME);
                break;
            case R.id.tv_keyboard_money_expenditure://支出
                onTextChange(KEY_MONEY_EXPENDITURE);
                break;
            case R.id.iv_keyboard_delete:
                onTextChange(KEY_DELETE);
                break;
            case R.id.iv_keyboard_complete:
                onTextChange(KEY_COMPLETE);
                break;
        }
    }

    private static String KEY_SPOT = ".";
    private static String KEY_REDUCE = "-";
    private static String KEY_PLUS = "+";
    private static String KEY_MONEY_INCOME = "I";
    private static String KEY_MONEY_EXPENDITURE = "E";
    private static String KEY_DELETE = "D";
    private static String KEY_COMPLETE = "C";

    //每一个数字的集合
    private List<String> dataList = new ArrayList<String>();
    //当前输入的数字
    private String allChangData;


    private float count = 0.f;
    private String geData;
    private boolean flage = false;

    //TextView获取的值
    private String data;
    //TextView最后一个字符
    private String cutdata;

    private void onTextChange(String changData) {
        data = money_edit_text.getText().toString();
        //最后一个字符
        cutdata = data.substring(data.length() - 1);

        if (changData.equals(KEY_REDUCE)) {//减
            //最后一个是"-"号不做操作
            if (cutdata.equals(KEY_REDUCE)) {
                return;
            }
            flage = true;
            //如果最后一个是+号,则加号改-号
            if (cutdata.equals(KEY_PLUS)) {
                money_edit_text.setText(data.substring(0, data.length() - 1) + KEY_REDUCE);
            } else {
                money_edit_text.setText(data + KEY_REDUCE);
            }
            iv_keyboard_complete.setImageResource(R.mipmap.equal);

        } else if (changData.equals(KEY_PLUS)) {//加
            //最后一个是"+"号不做操作
            if (cutdata.equals(KEY_PLUS)) {
                return;
            }
            flage = true;
            //如果最后一个是-号,则加号改+号
            if (cutdata.equals(KEY_REDUCE)) {

                money_edit_text.setText(data.substring(0, data.length() - 1) + KEY_PLUS);
            } else {
                money_edit_text.setText(data + KEY_PLUS);
            }
            iv_keyboard_complete.setImageResource(R.mipmap.equal);

        } else if (changData.equals(KEY_MONEY_INCOME)) {//收入

            int visibility = tv_keyboard_money_income.getVisibility();
            if (visibility == View.VISIBLE) {

                tv_keyboard_money_income.setVisibility(View.GONE);
                tv_keyboard_money_expenditure.setVisibility(View.VISIBLE);
            }

        } else if (changData.equals(KEY_MONEY_EXPENDITURE)) {//支出
            int visibility = tv_keyboard_money_expenditure.getVisibility();
            if (visibility == View.VISIBLE) {
                tv_keyboard_money_income.setVisibility(View.VISIBLE);
                tv_keyboard_money_expenditure.setVisibility(View.GONE);
            }

        } else if (changData.equals(KEY_DELETE)) {
            if (data.length() == 1) {
                //如果减到最后则至0️,修改俩个图标
                money_edit_text.setText(0 + "");

                iv_keyboard_complete.setImageResource(R.mipmap.cancel);
                tv_keyboard_money_expenditure.setVisibility(View.VISIBLE);
                iv_keyboard_reduce.setVisibility(View.GONE);
                iv_keyboard_plus.setVisibility(View.GONE);
            } else {
                money_edit_text.setText(data.substring(0, data.length() - 1));
            }


            //如果最后一个是加号或者减号则该表图标
            if (cutdata.equals(KEY_REDUCE) || cutdata.equals(KEY_PLUS)) {
                iv_keyboard_complete.setImageResource(R.mipmap.keyboard_determine);
            }

        } else if (changData.equals(KEY_COMPLETE)) {//确定和等于

            //为true则是点击展开的
            if (isPullOrTouch) {
                onTouchComplete();
            } else {
                hidePullView();
                refreshableView.finishRefreshing();
                hideKeyboard();
                backPlaySequentiallyAnimator();
            }


        } else if (changData.equals(KEY_SPOT)) {//输入点
            if (flage) {
                if (cutdata.equals(KEY_SPOT) || cutdata.equals(KEY_REDUCE) || cutdata.equals(KEY_PLUS)) {
                    return;
                }
                money_edit_text.setText(data + changData);
            }

        } else {//输入的是数字

            //默认是不能直接输入,当是pull的时候则可以直接输入
            if (flage) {
                money_edit_text.setText(data + changData);
            }
            //下拉后按数字
            if (!isPullOrTouch) {
                cleanTextView();
                data = money_edit_text.getText().toString();
                money_edit_text.setText(data + changData);
                iv_keyboard_complete.setImageResource(R.mipmap.keyboard_determine);
                hideMoneyType();
                showReduceAndPlus();
            }
        }
    }


    private void hideMoneyType() {
        int expenditureVisibility = tv_keyboard_money_expenditure.getVisibility();
        if (expenditureVisibility == View.VISIBLE) {
            tv_keyboard_money_expenditure.setVisibility(View.GONE);
        }

        int incomeVisibility = tv_keyboard_money_income.getVisibility();
        if (incomeVisibility == View.VISIBLE) {
            tv_keyboard_money_income.setVisibility(View.GONE);
        }
    }

    private void showReduceAndPlus() {

        int reduce = iv_keyboard_reduce.getVisibility();
        int plus = iv_keyboard_plus.getVisibility();

        if (reduce == View.GONE) {
            iv_keyboard_reduce.setVisibility(View.VISIBLE);
        }
        if (plus == View.GONE) {
            iv_keyboard_plus.setVisibility(View.VISIBLE);
        }


    }


    private void cleanTextView() {
        String textData = money_edit_text.getText().toString();
        if (textData.equals("0.00") || textData.equals("0")) {
            money_edit_text.setText("");
        }
    }


    //点击软键盘确定
    private void onTouchComplete() {
        //这里获取是防止点开后直接关闭
        data = money_edit_text.getText().toString();
        //最后一个字符
        cutdata = data.substring(data.length() - 1);
        //如果里面有加号或者减号则是计算值,否则关闭
        if (data.contains(KEY_REDUCE) || data.contains(KEY_PLUS)) {
            onCalculationNumber();
        } else {
            //避免删除一个数后就关闭
            DecimalFormat df = new DecimalFormat("0.00");
            String a = df.format(Float.valueOf(data));
            money_edit_text.setText(a);
            hideKeyboardandView();
        }

    }

    private void onCalculationNumber() {
        //如果最后一个是加减则删除,避免输入一个符号就点确定
        if (cutdata.equals(KEY_REDUCE) || cutdata.equals(KEY_PLUS)) {
            geData = money_edit_text.getText().toString() + "0.00";
        } else {
            geData = money_edit_text.getText().toString();
        }
        ArrayList result = getStringList(geData);  //String转换为List
        result = getPostOrder(result);   //中缀变后缀
        Float i = calculate(result);

        DecimalFormat df = new DecimalFormat("0.00");
        String datas = df.format(i) + "";
        //如果值为负数则至为0;
        char a = datas.charAt(0);
        if (a == '-') {
            datas = "0.00";
        }
        money_edit_text.setText(datas);
        iv_keyboard_complete.setImageResource(R.mipmap.keyboard_determine);
        flage = false;
    }

    private void hideKeyboardandView() {
        //关闭软键盘界面
        tv_keyboard_money_income.setVisibility(View.GONE);
        iv_keyboard_reduce.setVisibility(View.VISIBLE);
        iv_keyboard_plus.setVisibility(View.VISIBLE);
        iv_keyboard_complete.setImageResource(R.mipmap.keyboard_determine);
        keepOne.toggle(mainViewHolder);
        backPlaySequentiallyAnimator();
        hideKeyboard();
    }


    private void onKeyDownListener() {
        //这里获取是防止点开后直接关闭
        data = money_edit_text.getText().toString();
        //最后一个字符
        cutdata = data.substring(data.length() - 1);
        //如果里面有加号或者减号则是计算值,否则关闭
        if (data.contains(KEY_REDUCE) || data.contains(KEY_PLUS)) {
            onCalculationNumber();
        } else {
            //避免删除一个数后就关闭
            DecimalFormat df = new DecimalFormat("0.00");
            String a = df.format(Float.valueOf(data));
            money_edit_text.setText(a);
        }

        hideKeyboardandView();
    }


    /**
     * 将中缀表达式转化为后缀表达式
     *
     * @param inOrderList
     * @return
     */
    public ArrayList<String> getPostOrder(ArrayList<String> inOrderList) {

        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if (Character.isDigit(inOrderList.get(i).charAt(0))) {
                result.add(inOrderList.get(i));
            } else {
                switch (inOrderList.get(i).charAt(0)) {
                    case '(':
                        stack.push(inOrderList.get(i));
                        break;
                    case ')':
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))) {
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * 计算后缀表达式
     *
     * @param postOrder
     * @return
     */
    public Float calculate(ArrayList<String> postOrder) {
        Stack stack = new Stack();
        for (int i = 0; i < postOrder.size(); i++) {
            char b = postOrder.get(i).charAt(0);
            if (Character.isDigit(postOrder.get(i).charAt(0))) {
                String a = postOrder.get(i);
                stack.push(Float.parseFloat(postOrder.get(i)));
            } else {
                Float back = (Float) stack.pop();
                Float front = (Float) stack.pop();
                Float res = 0.f;
                switch (postOrder.get(i).charAt(0)) {
                    case '+':
                        res = front + back;
                        break;
                    case '-':
                        res = front - back;
                        break;
                    case '*':
                        res = front * back;
                        break;
                    case '/':
                        res = front / back;
                        break;
                }
                stack.push(res);
            }
        }
        return (Float) stack.pop();
    }

    /**
     * 比较运算符等级
     *
     * @param peek
     * @param cur
     * @return
     */
    public static boolean compare(String peek, String cur) {
        if ("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转化成List
     *
     * @param str
     * @return
     */
    public ArrayList<String> getStringList(String str) {
        ArrayList<String> result = new ArrayList<String>();
        String num = "";
        for (int i = 0; i < str.length(); i++) {

            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.') {
                num = num + str.charAt(i);
                str.charAt(i);
            } else {
                if (num != "") {
                    result.add(num);
                }
                result.add(str.charAt(i) + "");
                num = "";
            }
        }
        if (num != "") {
            result.add(num);
        }
        return result;
    }




}


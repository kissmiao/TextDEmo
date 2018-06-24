package com.commonadapter.module.one.Bfragment.Efragment.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.commonadapter.common.adapter.ListItemAdapter;
import com.commonadapter.R;
import com.commonadapter.common.bean.ListItem;
import com.commonadapter.common.bean.ViewExpandAnimation;
import com.commonadapter.common.bean.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaowenzheng
 */
public class ListViewActivity extends AppCompatActivity {

    private ListView mListView;

    private List<ListItem> mList;

    private ListItemAdapter mListAdapter;

    private ListItemAdapter.ViewHolder mLastViewTag = null;

    private TextView tv_text;

    private RelativeLayout rl_list_context;


    private boolean tag = false;
    private int textViewHeight;
    private int itemHeight;
    int statusBarHeight;
    private WindowManager wm;
    private int mHeight;
    private Button bt_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        bt_listView = (Button) findViewById(R.id.bt_listView);

        mListView = (ListView) findViewById(R.id.list_view);
        tv_text = (TextView) findViewById(R.id.tv_text);
        rl_list_context = (RelativeLayout) findViewById(R.id.rl_list_context);
        init();
        itemHeight = Dp2Px(ListViewActivity.this, 50);
        textViewHeight = Dp2Px(ListViewActivity.this, 100);
        statusBarHeight = getStatusBarHeight(this);


        View v = getWindow().findViewById(Window.ID_ANDROID_CONTENT);///获得根视图
        int top2 = v.getTop();///状态栏标题栏的总高度,所以标题栏的高度为top2-top
        Log.i("LOG", "**height***" + statusBarHeight + "top2" + top2);

        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentTop - statusBarHeight;

        wm = this.getWindowManager();
        mHeight = wm.getDefaultDisplay().getHeight();

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

    public void init() {

        mList = new ArrayList<ListItem>();

        List<String> tempArray1 = new ArrayList<String>();
        tempArray1.add("第1条");


        List<String> tempArray2 = new ArrayList<String>();
        tempArray2.add("第2条");


        List<String> tempArray3 = new ArrayList<String>();
        tempArray3.add("第3条");


        for (int i = 0; i < 20; i++) {
            ListItem item = new ListItem();

            if (i == 0) {
                item.setChildrenList(tempArray1);
            } else if (i == 1) {
                item.setChildrenList(tempArray2);
            } else if (i == 2) {
                item.setChildrenList(tempArray3);
            } else {
                item.setChildrenList(tempArray1);
            }

            mList.add(item);
        }

        mListAdapter = new ListItemAdapter(this, mList);
        mListView.setAdapter(mListAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                //添加刷新会导致动画卡动
                mListAdapter.setPosition(position);

                //true 则不为空，返回就关闭
                if (mLastViewTag != null) {
                    return;
                }
                tag = true;
                mParent = parent;
                //记录当前item的Tag
                mLastViewTag = (ListItemAdapter.ViewHolder) view.getTag();


                //展开当前点击的item
                final View childrenLayout = view.findViewById(R.id.child_layout);
                ViewExpandAnimation expandAnimation = new ViewExpandAnimation(childrenLayout);
                childrenLayout.startAnimation(expandAnimation);
                //      moveHeight = itemHeight * position + textViewHeight;
                int Pos[] = {-1, -1}; //保存当前坐标的数组
                view.getLocationOnScreen(Pos); //获取选中的 Item 在屏幕中的位置，以左上角为原点 (0, 0)
                int OldListY = (int) Pos[1]; //我们只取 Y 坐标就行了
                Log.i("LOG", "**OldListY**" + OldListY);
                moveHeight = OldListY - statusBarHeight - itemHeight;

                Log.i("LOG", "**lp.height **---------------------*" + OldListY + "----------" + moveHeight);

             //   verticalRun(rl_list_context, 0,-moveHeight );
                doPlaySequentiallyAnimator();



            }
        });
    }

    private int moveHeight;
    private AdapterView<?> mParent;

    public void onRecoveryView(AdapterView<?> parent) {
        //将上一个展开的view 收缩
        if (mLastViewTag != null) {
            View previousView = parent.findViewWithTag(mLastViewTag);
            if (previousView != null) {
                View childrenView = previousView.findViewById(R.id.child_layout);
                if (childrenView != null && (childrenView.getVisibility() != View.GONE)) {
                    mListAdapter.setPosition(-1);
                    //点开的子View回缩
                    childrenView.startAnimation(new ViewExpandAnimation(childrenView));
                    //整个View向下滑动
                  //    verticalRun(rl_list_context, -moveHeight, 0);
                    backPlaySequentiallyAnimator();
                    //从新设置rl的高度,
              /*      ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) rl_list_context.getLayoutParams();
                    lp.height = mHeight;
                    rl_list_context.setLayoutParams(lp);*/
                    mLastViewTag = null;
                    tag = false;
                }
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (tag) {
                    onRecoveryView(mParent);
                } else {
                    finish();
                }
            }
        }
        return false;
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

    public void verticalRun2(final View view, final int height, final int moveHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(height, moveHeight);
        animator.setTarget(view);
        animator.setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();

                Integer anim = (Integer) animation.getAnimatedValue();
                lp.height = anim;
                view.requestLayout();
            }
        });
    }

    private void doPlaySequentiallyAnimator() {
        ObjectAnimator rl_list_contextY = ObjectAnimator.ofFloat(rl_list_context, "translationY", 0, -moveHeight);
        ObjectAnimator rl_list_contextHeight = ObjectAnimator.ofInt(new ViewWrapper(rl_list_context), "height", mHeight, mListView.getCount() * itemHeight + textViewHeight * 3);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rl_list_contextY,rl_list_contextHeight);
        animatorSet.setDuration(600);
        animatorSet.start();
    }



    private void backPlaySequentiallyAnimator() {
        ObjectAnimator rl_list_contextY = ObjectAnimator.ofFloat(rl_list_context, "translationY", -moveHeight, 0);
        ObjectAnimator rl_list_contextHeight = ObjectAnimator.ofInt(new ViewWrapper(rl_list_context), "height",mListView.getCount() * itemHeight + textViewHeight * 3, mHeight);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rl_list_contextY,rl_list_contextHeight);
        animatorSet.setDuration(600);
        animatorSet.start();
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


}

package com.commonadapter.common.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonadapter.R;
import com.commonadapter.common.util.DateUtil;
import com.commonadapter.common.util.UIHelper;

import java.util.Date;

/**
 * Created by whl on 2017/5/22.
 */
public class TimeViewGroup extends LinearLayout implements View.OnClickListener {

    private TextView tv_details_time;
    private TextView tv_details_time_selected;

    private Date mDate;

    private Context mContext;

    public TimeViewGroup(Context context) {
        super(context);

        initView(context);
    }

    public TimeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TimeViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.time_item, null);
        addView(myView);

        tv_details_time = (TextView) findViewById(R.id.tv_details_time);
        tv_details_time_selected = (TextView) findViewById(R.id.tv_details_time_selected);
        tv_details_time_selected.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_details_time_selected:
                if (DateUtil.isToday(mDate)) {
                    this.mDate = DateUtil.getUpDay(new Date());
                } else {
                    this.mDate = new Date();
                }
                onSelectedTimeAnimStart(tv_details_time, tv_details_time_selected);
                mOnSelectTime.onSelectTimeClick(mDate);
                break;
        }
    }


    private void onSelectedTimeAnimStart(final TextView todayTime, final TextView selecteTime) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(todayTime, "alpha", 1, 0);
        ObjectAnimator alpha_animator = ObjectAnimator.ofFloat(selecteTime, "alpha", 1, 0);
        ObjectAnimator translationX_animator = ObjectAnimator.ofFloat(selecteTime, "translationX", 0, UIHelper.Dp2Px(mContext, -200));
        AnimatorSet animatorSet = new AnimatorSet();
        //动画一起执行
        animatorSet.playTogether(animator, alpha_animator, translationX_animator);
        animatorSet.setDuration(500);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onTimeSelected(false, mDate, tv_details_time, tv_details_time_selected);
                onSelectedTimeAnimStartEnd(todayTime, selecteTime);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void onSelectedTimeAnimStartEnd(TextView todayTime, TextView selecteTime) {
        //显示——透明——显示
        ObjectAnimator animator = ObjectAnimator.ofFloat(todayTime, "alpha", 0, 1);
        animator.setDuration(100);
        animator.start();
        //显示—不显示
        ObjectAnimator alpha_animator = ObjectAnimator.ofFloat(selecteTime, "alpha", 0, 1);
        //像左移动
        ObjectAnimator translationX_animator = ObjectAnimator.ofFloat(selecteTime, "translationX", UIHelper.Dp2Px(mContext, -200), 0);
        AnimatorSet animatorSet = new AnimatorSet();
        //先后执行
        animatorSet.playSequentially(translationX_animator, alpha_animator);
        animatorSet.setDuration(200);
        animatorSet.start();

    }

    /**
     * @param isSelected
     * @param date         当前时间
     * @param todayTime
     * @param electionTime 点击的是时间控件
     */
    private void onTimeSelected(boolean isSelected, Date date, TextView todayTime, TextView electionTime) {
        //判断是否是今天
        if (isSelected) {
            if (DateUtil.isToday(date)) {
                todayTime.setText("今天");
                electionTime.setText("昨天?");
            } else {
                todayTime.setText(DateUtil.formatTheDateToMM_dd(date, 1));
                electionTime.setText("今天?");
            }
        } else {
            if (DateUtil.isToday(date)) {
                todayTime.setText("今天");
                electionTime.setText("昨天?");
            } else {
                todayTime.setText("昨天");
                electionTime.setText("今天?");
            }
        }
    }


    private OnSelectTime mOnSelectTime;

    public interface OnSelectTime {
        void onSelectTimeClick(Date date);
    }

    public void setOnSelectTime(OnSelectTime onSelectTime, Date date) {
        this.mOnSelectTime = onSelectTime;
        this.mDate = date;
    }


}

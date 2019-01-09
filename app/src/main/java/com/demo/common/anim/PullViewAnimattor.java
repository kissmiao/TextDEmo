package com.demo.common.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by whl on 16/10/4.
 */
public class PullViewAnimattor {

    public static class LayoutParamsAnimatorListener extends AnimatorListenerAdapter {
        private final View _view;
        private final int _paramsWidth;
        private final int _paramsHeight;

        public LayoutParamsAnimatorListener(View view, int paramsWidth, int paramsHeight) {
            _view = view;
            _paramsWidth = paramsWidth;
            _paramsHeight = paramsHeight;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            final ViewGroup.LayoutParams params = _view.getLayoutParams();
            params.width = _paramsWidth;
            params.height = _paramsHeight;
            _view.setLayoutParams(params);
        }
    }

    //获得一个View的高度变化动画
    public static Animator ofItemViewHeight(View view) {
        View parent = (View) view.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");

        int start = view.getMeasuredHeight();
        view.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = view.getMeasuredHeight();

        final Animator animator = LayoutAnimator.ofHeight(view, start, end);

        animator.addListener(new LayoutParamsAnimatorListener(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return animator;
    }

}

package com.demo.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/30.
 */
public class MViewGroup3 extends ViewGroup {
    private DisplayMetrics dm;
    private int mWidth, mHeight;


    public MViewGroup3(Context context) {
        super(context);
    }

    public MViewGroup3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
    }

    public MViewGroup3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int cCount = getChildCount();

        int width = 0;
        int height = 0;

        if (cCount == 1) {
            width = mWidth / 2;
            height = (int) (width * 1.5);
        } else if (cCount == 4) {
            width = mWidth / 3 * 2;
            height = width;
        } else {

            /**
             * 记录每一行的宽度，width不断取最大宽度
             */
            int lineWidth = 0;
            /**
             * 每一行的高度，累加至height
             */
            int lineHeight = 0;

            // 遍历每个子元素
            for (int i = 0; i < cCount; i++) {
                // 当前子空间实际占据的宽度
                int childWidth = mWidth / 3;
                // 当前子空间实际占据的高度
                int childHeight = mWidth / 3;
                /**
                 * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
                 */
                if (lineWidth + childWidth > mWidth) {
                    width = Math.max(lineWidth, childWidth);// 取最大的
                    lineWidth = childWidth; // 重新开启新行，开始记录
                    // 叠加当前高度，
                    height += lineHeight;
                    // 开启记录下一行的高度
                    lineHeight = childHeight;
                } else
                // 否则累加值lineWidth,lineHeight取最大高度
                {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
                if (i == cCount - 1) {
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
            }
        }

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 存储每一行所有的childView
        mAllViews.clear();
        mLineHeight.clear();
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;

        if (cCount == 1) {
            View child = getChildAt(0);
            child.layout(0, 0, mWidth / 2, (int) (mWidth / 2 * 1.5));
        } else {
            int viewGroupWidth;
            if (cCount == 4) {
                viewGroupWidth = mWidth / 3 * 2;
            } else {
                viewGroupWidth = mWidth;
            }

            for (int i = 0; i < cCount; i++) {
                View child = getChildAt(i);
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                int childWidth = mWidth / 3;

                int childHeight = mWidth / 3;

                // 如果已经需要换行
                if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > viewGroupWidth) {
                    // 记录这一行所有的View以及最大高度
                    mLineHeight.add(lineHeight);
                    // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                    mAllViews.add(lineViews);
                    lineWidth = 0;// 重置行宽
                    lineViews = new ArrayList<View>();
                }
                /**
                 * 如果不需要换行，则累加
                 */
                lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                        + lp.bottomMargin);
                lineViews.add(child);
            }
            // 记录最后一行
            mLineHeight.add(lineHeight);
            mAllViews.add(lineViews);

            int left = 0;
            int top = 0;
            // 得到总行数
            int lineNums = mAllViews.size();
            for (int i = 0; i < lineNums; i++) {
                // 每一行的所有的views
                lineViews = mAllViews.get(i);
                // 当前行的最大高度
                lineHeight = mLineHeight.get(i);


                // 遍历当前行所有的View
                for (int j = 0; j < lineViews.size(); j++) {
                    View child = lineViews.get(j);
                    if (child.getVisibility() == View.GONE) {
                        continue;
                    }
                    MarginLayoutParams lp = (MarginLayoutParams) child
                            .getLayoutParams();

                    //计算childView的left,top,right,bottom
                    int lc = left + lp.leftMargin;
                    int tc = top + lp.topMargin;
                    int rc = lc + mWidth / 3;
                    int bc = tc + mWidth / 3;


                    child.layout(lc, tc, rc, bc);

                    left += mWidth / 3 + lp.rightMargin
                            + lp.leftMargin;
                }
                left = 0;
                top += lineHeight;
            }
        }
    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(), p);
    }

/*
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    // 继承自margin，支持子视图android:layout_margin属性
    public static class LayoutParams extends MarginLayoutParams {


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }


        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }*/


}

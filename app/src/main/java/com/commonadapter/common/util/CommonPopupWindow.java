package com.commonadapter.common.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by whl on 16/9/25.
 */
public class CommonPopupWindow extends PopupWindow {
    private Context mContext;
    private LayoutInflater inflater;
    public View view;


    public CommonPopupWindow(Context context, int layout, int animationStyle) {
        this(context, null, layout, animationStyle);

    }


    public CommonPopupWindow(Context context, AttributeSet attrs, int layout, int animationStyle) {
        this(context, attrs, 0, layout, animationStyle);

    }

    public CommonPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int popupwindowLayout, int animationStyle) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(popupwindowLayout, null);
        setCommonPopupWindow(view, animationStyle);
    }


    public void setCommonPopupWindow(View view,
                                     int animationStyle) {
        this.view = view;
        // 重新编排布局，重新分配空间
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 重新编排布局，重新分配空间
        // this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 设置ApplayPopupWindow的View
        this.setContentView(view);
        // 设置ApplayPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置ApplayPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置ApplayPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(animationStyle);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x40000000);
        // 设置ApplayPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
        this.update();

    }

}

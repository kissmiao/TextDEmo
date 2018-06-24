package com.commonadapter.common.bean;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.commonadapter.R;

import java.lang.reflect.Method;

/**
 * Created by whl on 16/9/6.
 * http://blog.csdn.net/dgs960825/article/details/50344743
 */
public class KeyboardUtil2 {

    private Context mContext;
    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private TextView mTextView;
    /**
     * 省份简称键盘
     */
    private Keyboard province_keyboard;
    /**
     * 数字与大写字母键盘
     */
    private Keyboard number_keyboar;

    /**
     * 软键盘切换判断
     */
    private boolean isChange = true;
    /**
     * 判定是否是中文的正则表达式 [\\u4e00-\\u9fa5]判断一个中文 [\\u4e00-\\u9fa5]+多个中文
     */
    private String reg = "[\\u4e00-\\u9fa5]";

    public KeyboardUtil2(Activity activity, TextView textView, boolean tag) {
        mActivity = activity;
        mContext = (Context) activity;
        mTextView = textView;
        province_keyboard = new Keyboard(mContext, R.xml.symbols2);
        number_keyboar = new Keyboard(mContext, R.xml.symbols);
        mKeyboardView = (KeyboardView) activity
                .findViewById(R.id.keyboard_view);
        if (tag) {
            mKeyboardView.setKeyboard(number_keyboar);
        } else {
            mKeyboardView.setKeyboard(province_keyboard);
        }
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            String data = mTextView.getText().toString();

            if (primaryCode == 57419) {//减ss

            } else if (primaryCode == 57421) {//加
                //最后一位是加或则减则不操作

            } else if (primaryCode == -1) {// 完成
                hideKeyboard();
            } else if (primaryCode == -3) {//删除键

                if (data != null && data.length() > 0) {
                    //有值的时候删除
                    changeKeyboard(false);
                }else{
                    //没有了则切换
                    changeKeyboard(false);
                }
            } else {
                //输入其他数字的的则在后面累加
                mTextView.setText(data + primaryCode);
            }
        }
    };

    /**
     * 按切换键时切换软键盘
     */
    public void changeKeyboard() {
        if (isChange) {
            mKeyboardView.setKeyboard(number_keyboar);
        } else {
            mKeyboardView.setKeyboard(province_keyboard);
        }
        isChange = !isChange;
    }

    /**
     * 指定切换软键盘
     */
    public void changeKeyboard(boolean isnumber) {
        if (isnumber) {
            mKeyboardView.setKeyboard(number_keyboar);
        } else {
            mKeyboardView.setKeyboard(province_keyboard);
        }
    }

    /**
     * 软键盘展示状态
     */
    public boolean isShow() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /**
     * 软键盘展示
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.slide_in_bottom);
            mKeyboardView.startAnimation(anim);
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 软键盘隐藏
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.slide_out_bottom);
            mKeyboardView.setVisibility(View.INVISIBLE);
            mKeyboardView.startAnimation(anim);

        }
    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod() {
        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            //   mEdit.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                //   setShowSoftInputOnFocus.invoke(mEdit, false);
            } catch (NoSuchMethodException e) {
                //     mEdit.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        }
    }
}

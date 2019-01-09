package com.demo.common.util;

import android.app.Activity;
import android.view.Gravity;

import com.demo.R;


/**
 * Created by whl on 16/9/25.
 */
public class PopupWindowSignUpData {


   private CommonPopupWindow mApplayPopupWindow;
    private Activity mActivity;

    public void showPopupWindow() {

        if (mApplayPopupWindow == null) {
            // 实例化SelectPicPopupWindow
            mApplayPopupWindow = new CommonPopupWindow(mActivity,R.layout.dd_sign_up_popupwindow, R.style._AnimBottom);
        }
        // 显示窗口
        mApplayPopupWindow.showAtLocation(mActivity.findViewById(R.id.ll_popw), Gravity.BOTTOM | Gravity.CENTER, 0,
                0); // 设置layout在PopupWindow中显示的位置


       /* MyTextView people_num = (MyTextView) mApplayPopupWindow.view.findViewById(R.id.people_num);
        Button mApplay = (Button) mApplayPopupWindow.view.findViewById(R.id.btn_applay);
        final EditText mEt_name = (EditText) mApplayPopupWindow.view.findViewById(R.id.et_name);
        final EditText mEt_tel = (EditText) mApplayPopupWindow.view.findViewById(R.id.et_tel);*/
    }

    public PopupWindowSignUpData(Activity activity) {
        this.mActivity = activity;
    }



}

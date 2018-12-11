package com.commonadapter.module.three;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonadapter.R;
import com.commonadapter.common.util.SMSMethod;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class MessageActivity extends Activity implements View.OnClickListener {

    /**
     * 短信
     */
    private Button mBtSms;

    private String[] mPermission = new String[]{
            Manifest.permission.SEND_SMS,

    };

    public static final int REQUEST_CODE_SETTING = 300;

    private static final int REQUEST_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        //  requestPermission();
        onshowDialog();
       // handler.sendMessage()
        new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        handler.sendMessage(new Message());



    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }


    };


    //显示遮罩


    private void onshowDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog_style);
        View view = LayoutInflater.from(this).inflate(R.layout.mask1, null);

        final LinearLayout ll_mask1 = view.findViewById(R.id.ll_mask1);
        final LinearLayout ll_mask2 = view.findViewById(R.id.ll_mask2);
        final LinearLayout ll_mask3 = view.findViewById(R.id.ll_mask3);
        Button button = view.findViewById(R.id.bt_mask1);
        button.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View v) {
                i++;
                if (i == 1) {
                    ll_mask1.setVisibility(View.GONE);
                    ll_mask2.setVisibility(View.VISIBLE);
                    //显示第2个
                } else if (i == 2) {
                    ll_mask2.setVisibility(View.GONE);
                    ll_mask3.setVisibility(View.VISIBLE);
                    //显示第3个
                } else if (i == 3) {
                    dialog.dismiss();
                }
            }
        });

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    private void initView() {
        mBtSms = (Button) findViewById(R.id.bt_sms);
        mBtSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sms:

                //  sendSMS("10086", "11");

                sendMultipartTextMessage();
                break;
        }
    }


    @TargetApi(23)
    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(REQUEST_PERMISSION_CODE)
                .permission(mPermission)
                .callback(mPermissionListener)
                .start();
    }


    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            //                AndPermission.defaultSettingDialog(WelcomeActivity.this, REQUEST_CODE_SETTING)
//                        .setTitle("权限申请失败")
//                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                        .setPositiveButton("好，去设置")
//                        .show();
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(MessageActivity.this, REQUEST_CODE_SETTING).show();
        }
    };


    public void sendSMS(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(MessageActivity.this, "cccc", Toast.LENGTH_LONG).show();
    }


    public void sendTextMessage() {
        SMSMethod.getInstance(this).SendMessage("10086", "11");
    }

    public void sendMultipartTextMessage() {
        SMSMethod.getInstance(this).SendMessage2("10086", "11");
    }

    @Override
    protected void onPause() {
        SMSMethod.getInstance(this).unregisterReceiver();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

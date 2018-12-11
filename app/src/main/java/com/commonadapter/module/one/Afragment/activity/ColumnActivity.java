package com.commonadapter.module.one.Afragment.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.commonadapter.R;

/**
 * Created by whl on 2016/12/5.
 */
public class ColumnActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);

        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle bundle = appInfo.metaData;
        int a = bundle.getInt("APPID");
        Toast.makeText(this, a + "", Toast.LENGTH_LONG).show();
        //  Log.i(TAG, "bundle.getString(jerey) : " + bundle.getString("jerey"));
        //String s = getMetaDataStringApplication("APPID", "====");
        // Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


    /**
     * 根据key从Application中返回的Bundle中获取value
     *
     * @param key
     * @param defValue
     * @return
     */
    private String getMetaDataStringApplication(String key, String defValue) {
        Bundle bundle = getAppMetaDataBundle(getPackageManager(), getPackageName());
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        return defValue;
    }

    /**
     * 获取Application中的meta-data.
     *
     * @param packageManager
     * @param packageName
     * @return
     */
    private Bundle getAppMetaDataBundle(PackageManager packageManager,
                                        String packageName) {
        Bundle bundle = null;
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            bundle = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
        }
        return bundle;
    }


}
package com.dove.androidutils.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dove.androidutils.Utils;

import java.util.Locale;

/**
 * Created by dove on 16/7/13.
 */
public class AppUtils extends Utils{



    /**
     * 获取 version code
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageInfo pi=getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取version Name
     * @return
     */
    public static String getVersionName() {
        try {
            PackageInfo pi=getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0.8.0";
        }
    }


    /**
     * 修改 locale
     * 可用于语言版本的修改
     * 本地化
     * @param locale
     */
    public static void changeLocale(Locale locale) {
        Context appContext = getContext().getApplicationContext();
        Resources resources = appContext.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取sim卡信息
     * @return
     */
    public static String getSimOperator() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm.getSimOperator();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 判断sim卡信息是否为空
     * 部分机型会返回空值
     * @param operator
     * @return
     */
    private static boolean isOperatorEmpty(String operator) {
        if (operator == null) {
            return true;
        }

        if (operator.equals("") || operator.toLowerCase(Locale.US).contains("null")) {
            return true;
        }

        if (operator.equals("") ) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为中国的 sim 卡
     * @return
     */
    public static boolean isChinaSimCard() {
        String mcc = getSimOperator();
        if (isOperatorEmpty(mcc)) {
            return true;
        } else {
            return mcc.startsWith("460");
        }
    }

    /**
     * 获取 status bar 的高度
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

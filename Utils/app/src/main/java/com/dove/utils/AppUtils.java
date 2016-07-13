package com.dove.utils;

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

import java.util.Locale;

/**
 * Created by dove on 16/7/13.
 */
public class AppUtils {




    /**
     * 获取 version code
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取version Name
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0.8.0";
        }
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param editText
     */
    public static void hideSoftInput(final Context context, final EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 展示软键盘
     * @param context
     * @param editText
     */
    public static void showSoftInput(Context context,EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * 获取 window 宽度
     * @param context
     * @return
     */
    public static float getScreenWidth (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    /**
     * 获取 window 高度
     * @param context
     * @return
     */
    public static float getScreenHeight (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    /**
     * 修改 locale
     * @param context
     * @param locale
     */
    public static void changeLocale(Context context,Locale locale) {
        Context appContext = context.getApplicationContext();
        Resources resources = appContext.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取sim卡信息
     * @param c
     * @return
     */
    public static String getSimOperator(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
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
     * @param c
     * @return
     */
    public static boolean isChinaSimCard(Context c) {
        String mcc = getSimOperator(c);
        if (isOperatorEmpty(mcc)) {
            return true;
        } else {
            return mcc.startsWith("460");
        }
    }

    /**
     * 获取 status bar 的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

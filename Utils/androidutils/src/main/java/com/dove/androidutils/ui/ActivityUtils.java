package com.dove.androidutils.ui;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.dove.androidutils.Utils;
import com.dove.androidutils.common.AppUtils;
import com.dove.androidutils.common.CommonUtils;

/**
 * Created by dove on 16/7/13.
 */
public class ActivityUtils extends Utils{

    /**
     * 判断是否处于全屏状态
     */
    private static boolean isFull = false;

    /**
     * 进入全屏
     * 全局设置
     * @param activity
     */
    public static void setFullScreen(@NonNull Activity activity){
        if (!isFull) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            isFull = true;
        }
    }

    /**
     * 退出全屏
     * 全局设置
     * @param activity
     */
    public static void quitFullScreen(@NonNull Activity activity){
        if (isFull) {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            isFull = false;
        }
    }

    /**
     * 通过修改外层高度,来动态修改 toolbar 高度,
     * 用于适配 noactionbar 主题下,
     * toolbar 在4.4和5.0显式效果不一样
     * @param activity
     * @param view 必须是包裹 toolbar 的外层的第一层,例如 AppBarLayout
     */
    public static void changeBarHeight(@NonNull Activity activity,View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = CommonUtils.dpToPx(56, activity.getResources()) + AppUtils.getStatusBarHeight();
            view.requestLayout();
            view.invalidate();
        }
    }
}

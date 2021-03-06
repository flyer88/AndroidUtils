package com.dove.androidutils.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.WindowManager;

import com.dove.androidutils.Utils;

/**
 * Created by dove on 16/7/14.
 * 屏幕相关的工具,一般都是全局的设置
 */
public class ScreenUtils extends Utils{



    /**
     * 转换 dp 为 px
     * @param dp
     * @return
     */
    public static int dpToPx(float dp){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
        return (int) px;
    }

    /**
     * px 转为 dp
     * @param px
     * @return
     */
    public static int pxToDp(float px){
        float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,px,getContext().getResources().getDisplayMetrics());
        return (int) dp;
    }


    /**
     * 获取 window 宽度
     * @return
     */
    public static float getScreenWidth () {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    /**
     * 获取 window 高度
     * @return
     */
    public static float getScreenHeight () {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


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

}

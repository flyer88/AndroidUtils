package com.dove.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by dove on 16/7/13.
 */
public class ActivityUtils {

    private static boolean isFull = false;

    public static void setFullScreen(Activity activity){
        if (!isFull) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            isFull = true;
        }
    }

    public static void quitFullScreen(Activity activity){
        if (isFull) {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            isFull = false;
        }
    }

    public static void changeBarHeight(Activity activity,View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = CommonUtils.dpToPx(56, activity.getResources()) + AppUtils.getStatusBarHeight(activity.getApplicationContext());
            view.requestLayout();
            view.invalidate();
        }
    }
}

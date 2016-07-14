package com.dove.androidutils.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dove.androidutils.Utils;

/**
 * Created by dove on 16/7/14.
 */
public class ViewUtils extends Utils{


    /**
     * 隐藏软键盘
     * @param editText
     */
    public static void hideSoftInput( @NonNull EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 展示软键盘
     * @param editText
     */
    public static void showSoftInput(@NonNull EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * 通过修改外层高度,来动态修改 toolbar 高度,
     * 用于适配 noactionbar 主题下,
     * toolbar 在4.4和5.0显式效果不一样
     * @param activity
     * @param view 必须是包裹 toolbar 的外层的第一层,例如 AppBarLayout
     */
    public static void changeBarHeight(@NonNull Activity activity, View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.getLayoutParams().height = ScreenUtils.dpToPx(56, activity.getResources()) + getStatusBarHeight();
            view.requestLayout();
            view.invalidate();
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

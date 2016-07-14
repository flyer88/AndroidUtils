package com.dove.androidutils.ui;

import android.content.Context;
import android.support.annotation.NonNull;
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
}

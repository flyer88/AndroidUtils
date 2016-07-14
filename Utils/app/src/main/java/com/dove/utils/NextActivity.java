package com.dove.utils;

import android.app.Activity;
import android.os.Bundle;

import com.dove.androidutils.common.CommonUtils;
import com.dove.androidutils.ui.ScreenUtils;

/**
 * Created by dove on 16/7/13.
 */
public class NextActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.getScreenHeight();
    }
}

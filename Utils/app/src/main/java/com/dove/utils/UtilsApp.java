package com.dove.utils;

import android.app.Application;

import com.dove.androidutils.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by dove on 16/7/13.
 */
public class UtilsApp extends Application{

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
        Utils.initialized(this);
    }
}

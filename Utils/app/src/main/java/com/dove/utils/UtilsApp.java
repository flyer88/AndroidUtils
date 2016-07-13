package com.dove.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dove on 16/7/13.
 */
public class UtilsApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}

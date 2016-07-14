package com.dove.androidutils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by dove on 16/7/13.
 * 入口
 * 在 Application 中进行 initialized
 */
public class Utils {
    private static Context sContext;

    public static void initialized(@NonNull Context context){
        sContext = context;
    }

    protected static Context getContext(){
        return sContext;
    }


}

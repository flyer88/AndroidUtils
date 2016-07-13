package com.dove.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by dove on 16/7/13.
 */
public class Utils {
    private static Context sContext;

    public static void initialized(@NonNull Context context){
        sContext = context;
    }

}

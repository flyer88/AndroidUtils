package com.dove.androidutils.data;

import android.os.Environment;

import com.dove.androidutils.Utils;

import java.io.File;

/**
 * Created by dove on 16/7/13.
 */
public class FileUtils extends Utils{

    /**
     * 获取 download 路径
     * @return
     */
    public static String getDownLoadRootPath(){
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "CloudBox" + File.separator);
        if (!root.exists()) {
            root.mkdirs();
        }
        return root.getPath();
    }


}

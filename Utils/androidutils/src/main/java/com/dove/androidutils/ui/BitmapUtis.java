package com.dove.androidutils.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.dove.androidutils.Utils;
import com.dove.androidutils.common.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dove on 16/7/13.
 */
public class BitmapUtis extends Utils{

    /**
     * 把 drawable 转换成 bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(@NonNull Drawable drawable, Bitmap.Config config) {

        Bitmap bitmap = null;
        int w = CommonUtils.dpToPx(100,getContext().getResources());
        int h = CommonUtils.dpToPx(100,getContext().getResources());
        if (config == null) {
            config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                            Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        }
        bitmap = Bitmap.createBitmap(w,h,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * BitMap转换字符串
     * @param bitmap
     * @return
     */

    public static String bitmapToString(@NonNull Bitmap bitmap) {

        String stringMap = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        stringMap = Base64.encodeToString(bytes, Base64.DEFAULT);

        return stringMap;
    }

    /**
     * 字符串转换BitMap
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(@NonNull String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 文件形式的压缩,字节码输出
     * @param bitmap 需要压缩的图片
     * @param maxSize 压缩图片不超过maxSize
     * @return
     */
    public static ByteArrayOutputStream compressBitmap(@NonNull Bitmap bitmap,long maxSize) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            int options = 90;
            while (baos.toByteArray().length > maxSize) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
                options -= 10;
            }
            byte[] bts = baos.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
            baos.close();

            return baos;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 把 bitmap 保存到文件中
     * @param bitmap
     * @param sdImageMainDirectory
     * @param percent
     */
    public static void saveBitmapToFile(@NonNull Bitmap bitmap,@NonNull File sdImageMainDirectory, float percent) {
        if (bitmap == null){
            return;
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                (int) CommonUtils.getScreenWidth(),(int) (CommonUtils.getScreenHeight() * percent));
        try {
            FileOutputStream out = new FileOutputStream(sdImageMainDirectory);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 8, out);
            out.flush();
            out.close();
            //upload file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /**
     * 将两个 bitmap map 左右合并
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2Bitmap(@NonNull Bitmap first,@NonNull Bitmap second) {
        int width =first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }
}

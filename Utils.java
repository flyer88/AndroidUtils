import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.AttrRes;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    /**
     * 转换 dp 为 px
     * @param dp
     * @param resources
     * @return
     */
	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}

    /**
     * 计算当前 view 到屏幕顶部的距离
     * @param myView
     * @return
     */
	public static int getRelativeTop(View myView) {
	    if(myView.getId() == android.R.id.content)
	        return myView.getTop();
	    else
	        return myView.getTop() + getRelativeTop((View) myView.getParent());
	}

    /**
     *
     * @param myView
     * @return
     */
	public static int getRelativeLeft(View myView) {
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

    /**
     * 获取 version code
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取version Name
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0.8.0";
        }
    }

    /**
     * 判断是否为邮箱
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        Matcher m = p.matcher(strEmail);
        boolean b = m.matches();
        return b;
    }


    /**
     * BitMap转换字符串
     * @param bitmap
     * @return
     */

    public static String bitmapToString(Bitmap bitmap) {

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
    public static Bitmap stringToBitmap(String string) {

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
     * 装换 uri 为路径
     * @param uri
     * @param context
     * @return
     */
    public static String convertMediaContentUriToPath(Uri uri, Context context) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    /**
     * 转换 file 为 uri
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        if (imageFile == null){
            return null;
        }
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param editText
     */
    public static void hideSoftInput(final Context context, final EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 展示软键盘
     * @param context
     * @param editText
     */
    public static void showSoftInput(Context context,EditText editText){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     *
     * @param bitmap
     * @param maxSize 压缩图片不超过maxSize
     * @return
     */
    public static ByteArrayOutputStream compressBitmap(Bitmap bitmap,long maxSize) {
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
     * 进行 md5 加密
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void hideStatusBar(Activity activity, ActionBar actionBar){
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public static void showStatusBar(Activity activity,ActionBar actionBar){
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * 获取 status bar 的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 通过设置背景色来
     * 设置 status bar 的颜色
     * 会导致过度重绘
     * @param activity
     * @param color
     */
    public static void setContentViewStatusBarPaddingOverKitKat(Activity activity,@AnyRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View contentView = activity.findViewById(android.R.id.content);
            contentView.setBackgroundColor(getColorByAttr(activity,color));
            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
    }


    /**
     * 获取 window 宽度
     * @param context
     * @return
     */
    public static float getScreenWidth (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    /**
     * 获取 window 高度
     * @param context
     * @return
     */
    public static float getScreenHeight (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    //建立一个MIME类型与文件后缀名的匹配表
    public static final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",      "image/bmp"},
            {".c",        "text/plain"},
            {".class",    "application/octet-stream"},
            {".conf",    "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"},
            {".h",        "text/plain"},
            {".htm",    "text/html"},
            {".html",    "text/html"},
            {".jar",    "application/java-archive"},
            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",    "video/mp4"},
            {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",        "text/plain"},
            {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",        "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".z",        "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };

    /**
     * 设置状态栏透明
     * @param activity
     */
    public  static void setStatusTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            //透明状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * 通过 id 来获取字符串
     * @param id
     * @return
     */
    public static String getStringById(int id) {
        return PeroApp.getPeroAppContext().getResources().getString(id);
    }


    /**
     * 获得独一无二的Psuedo ID
     * @return
     */
    public static String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


    /**
     * 把 bitmap 保存到文件中
     * @param bitmap
     * @param sdImageMainDirectory
     * @param activity
     * @param percent
     */
    public static void saveBitmapToFile(Bitmap bitmap,File sdImageMainDirectory,Activity activity,float percent) {
        if (bitmap == null){
            return;
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                activity.getWindowManager().getDefaultDisplay().getWidth(),
                (int) (activity.getWindowManager().getDefaultDisplay().getHeight() * percent));
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
     * 返回一个圆角矩形的 path
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param rx
     * @param ry
     * @param conformToOriginalPost
     * @return
     */
    public static Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width/2) rx = width/2;
        if (ry > height/2) ry = height/2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        path.rLineTo(0, heightMinusCorners);

        if (conformToOriginalPost) {
            path.rLineTo(0, ry);
            path.rLineTo(width, 0);
            path.rLineTo(0, -ry);
        } else {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }


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

    /**
     * 把 drawable 转换成 bitmap
     * @param drawable
     * @param context
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable, Context context) {
        Bitmap bitmap = null;
        int w = dpToPx(100,context.getResources());
        int h = dpToPx(100,context.getResources());
          Bitmap.Config config =
                  drawable.getOpacity() != PixelFormat.OPAQUE ?
                          Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w,h,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 通过 attr 获取 color
     * @param context
     * @param resColor
     * @return
     */
    public  static int getColorByAttr(Context context, @AttrRes int resColor){
        TypedValue typedValue = new TypedValue();
        try {
            context.getTheme().resolveAttribute(resColor, typedValue, true);
        } catch (Exception e){
            Log.e("get color by attr",e.getLocalizedMessage());
        }
        final  int color = typedValue.data;
        return color;
    }

    /**
     * 通过 attr 获取 drawable
     * @param context
     * @param resDrawable
     * @return
     */
    public  static int getDrawableByAttr(Context context, @AttrRes int resDrawable){
        TypedValue typedValue = new TypedValue();
        try {
            context.getTheme().resolveAttribute(resDrawable, typedValue, false);
        } catch (Exception e){
            Log.e("get drawable by attr",e.getLocalizedMessage());
        }
        final  int drawable = typedValue.data;
        return drawable;

    }

    /**
     * 通过 独一无二的 PsuedoID 和时间戳进行 md5 获取一个 独一无二的 id
     * @return
     */
    public static String createUniqueString(){//设备id + 时间戳 md5
        String unique = md5(new Date().getTime() + getUniquePsuedoID());
        return unique;
    }

    /**
     * 将两个 bitmap map 左右合并
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int width =first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }

    /**
     * 修改 locale
     * @param context
     * @param locale
     */
    public static void changeLocale(Context context,Locale locale) {
        Context appContext = context.getApplicationContext();
        Resources resources = appContext.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取sim卡信息
     * @param c
     * @return
     */
    public static String getSimOperator(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            return tm.getSimOperator();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 判断sim卡信息是否为空
     * 部分机型会返回空值
     * @param operator
     * @return
     */
    private static boolean isOperatorEmpty(String operator) {
        if (operator == null) {
            return true;
        }

//        if (operator.equals("") || operator.toLowerCase(Locale.US).contains("null")) {
//            return true;
//        }

        if (operator.equals("") ) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为中国的 sim 卡
     * @param c
     * @return
     */
    public static boolean isChinaSimCard(Context c) {
        String mcc = getSimOperator(c);
        if (isOperatorEmpty(mcc)) {
            return true;
        } else {
            return mcc.startsWith("460");
        }
    }

    public void saveBitmapToFile(Bitmap shareBitmap,File rootFile,String file){
        File root = null;
        if (rootFile == null ){
             root = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "Pero" + File.separator);
        } else {
            root = rootFile;
        }
        String fname = null;
        if (file == null){
             fname = "img_status_bar.jpg";
        } else {
            fname = file;
        }

        if (!root.exists()) {
            root.mkdirs();
        }
        final File sdImageMainDirectory = new File(root, file);
        if (sdImageMainDirectory.exists()){
            sdImageMainDirectory.delete();
        }
        if (shareBitmap == null){
            Log.e("pero","bit map is null");
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(sdImageMainDirectory);
            shareBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Long stringToLong(String s){
        if (s == null){
            return 0l;
        }
        if (s.equals("")){
            return 0l;
        }
        return Long.valueOf(s).longValue();

    }
    public static int stringToInt(String s){
        if (s == null){
            return 0;
        }
        if (s.equals("")){
            return 0;
        }
        return Integer.valueOf(s).intValue();
    }


    public static File createImageFileToPero(){
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "pero" + File.separator + "head_image" + File.separator);
        if (!root.exists()) {
            root.mkdirs();
        }
        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        return sdImageMainDirectory;
    }



    public static String saveImageToPero(byte[] data,String imageName){

        String path = null;
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "pero" + File.separator + "head_image" + File.separator);
        if (!root.exists()) {
            root.mkdirs();
        }
        String fname = "img_" + System.currentTimeMillis() + ".jpg";
        if (imageName != null){
            fname = imageName;
        }
        final File sdImageMainDirectory = new File(root, fname);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(sdImageMainDirectory);
            outputStream.write(data);
            path =  sdImageMainDirectory.getAbsolutePath();
        } catch (FileNotFoundException e) {
            path = null;
            e.printStackTrace();
        } catch (IOException e) {
            path = null;
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

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
            view.getLayoutParams().height = Utils.dpToPx(56, activity.getResources()) + Utils.getStatusBarHeight(activity);
            view.requestLayout();
            view.invalidate();
        }
    }
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2 -5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 -5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    public static String parsePostTime(long time){
        String showTime = "";
        long deltaTime = System.currentTimeMillis() / 1000 - time;
        if (deltaTime / 60 >= 1){
            long minuteTime = deltaTime / 60;
            showTime = "发布于" + minuteTime + "分钟前";
            if (minuteTime / 60 >= 1){
                long hourTime = minuteTime/60;
                showTime = "发布于" + hourTime + "小时前";
                if (hourTime / 24 >= 1){
                    long dayTime = hourTime / 24;
                    showTime = "发布于" + dayTime + "天前";
                    if (dayTime / 30 >= 1){
                        long monthTime = dayTime / 24;
                        showTime = "发布于" + monthTime + "月前";
                    }
                }
            }
        } else {
            showTime = "发布于" + deltaTime + "秒前";
        }
        return showTime;
    }

    public static void sendSMS(Context context, String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    public static void shareText(Context context,String s){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

}

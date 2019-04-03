package com.klsf111.linghangbiao.activitys.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 专门用来捕捉异常的工具类
 * Create by Frank 0n 2017/10/23 18:06
 *
 * @author Administrator
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final CrashHandler crashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler exceptionHandler;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:linghangbiao.SSSZ");
    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<>();

    private Context context;

    private CrashHandler() {

    }

    public void init(Context context) {
        this.context = context;
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && exceptionHandler != null) {
            //如果未处理则让系统默认异常处理机制处理
            exceptionHandler.uncaughtException(thread, ex);
        } else {
            Log.e("Frank", "==CrashHandler.uncaughtException==程序异常退出");
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常信息，保存
     *
     * @param th
     * @return
     */
    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        collectDeviceInfo(context);
        //保存异常信息
        saveData(th);
        return true;
    }

    /**
     * 保存异常信息
     *
     * @param th
     */
    private void saveData(Throwable th) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String fileName = Environment.getExternalStorageDirectory() + "/crash/";
                File file = new File(fileName);
                if (!file.exists()) {
                    file.mkdirs();
                }
                StringBuffer sb = new StringBuffer();
                sb.append(formatter.format(System.currentTimeMillis()));
                for (Map.Entry<String, String> entry : infos.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key + "=" + value + "\n");
                }
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                th.printStackTrace(printWriter);
                Throwable cause = th.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                String result = writer.toString();
                sb.append(result);
                FileOutputStream fileOutputStream = new FileOutputStream(fileName + "shishicai.txt");
                BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream);
                stream.write(sb.toString().getBytes());
                stream.close();
                fileOutputStream.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 收集异常的机型信息
     *
     * @param context
     */
    private void collectDeviceInfo(Context context) {
        try {
            PackageManager packageManager =
                    context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                int versionCode = packageInfo.versionCode;
                infos.put("versionName", versionName);
                infos.put("versionCode", String.valueOf(versionCode));
            }
            Field[] declaredFields =
                    Build.class.getDeclaredFields();
            if (declaredFields != null) {
                for (Field field :
                        declaredFields) {
                    field.setAccessible(true);
                    infos.put(field.getName(), field.get(null).toString());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

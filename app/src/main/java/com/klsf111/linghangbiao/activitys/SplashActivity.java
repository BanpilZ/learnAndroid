package com.klsf111.linghangbiao.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.klsf111.linghangbiao.MyApplication;
import com.klsf111.linghangbiao.activitys.bean.loginbean;
import com.klsf111.linghangbiao.utils.LottyApiLogic;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//import com.klsf111.linghangbiao.activitys.utils.LottyApiLogic;

public class SplashActivity  extends Activity{

private void getLeanCloudObject(){
    AVObject todo = AVObject.createWithoutData(MyApplication.LEAN_CLOUD_OBJECT_NAME, "5ca4b6cda3180b0068b5aff4");
    todo.fetchInBackground(new GetCallback<AVObject>() {
        @Override
        public void done(AVObject avObject, AVException e) {
            String title = avObject.getString("Url");// 读取 title
            String appName = avObject.getString("appName");// 读取 content
            String packgeName = avObject.getString("packgeName");// 读取 content
            String showWeb = avObject.getString("ShowWeb");// 读取 content
            Log.e("zhanglifan", "title = " + title + "   appName = " + appName + "   packgeName = " + packgeName + "     showWeb = " + showWeb);
        }
    });
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLeanCloudObject();
          Log.e("zhanglifan", "App名称 = " +  getAppName(this));
          Log.e("zhanglifan", "包名 = " +  getPackageName());
        getJpushAppKey();
        setContentView(com.klsf111.linghangbiao.activitys.R.layout.activity_splash);
          TimerTask ts = new TimerTask() {
            @Override
            public void run() {
                try {
                        LottyApiLogic.getInstance().checkApiState(SplashActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Timer().schedule(ts, 2000);
    }

    /**
     * 获取包名
     * @param context
     * @return
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getJpushAppKey() {
        String jpush_appkey = "default";
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            jpush_appkey = appInfo.metaData.getString("JPUSH_APPKEY");
            Log.e("zhanglifan","jpush_appkey = " + jpush_appkey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    }

    public void onloadSuccess(loginbean logininfo) {
          if (logininfo.getRedirect_status().equals("1")){
              showHomePage(logininfo.getRedirect_url());
          }else if (logininfo.getUpdate_status().equals("1")){
             showUpDatapage(logininfo.getUpdate_url());
          }else{

              showNavipage();
          }
    }

    private void showUpDatapage(String update_url) {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, com.klsf111.linghangbiao.activitys.activitys.WebUpDataActivity.class);
        it.putExtra("updata",update_url);
        startActivity(it);
        finish();
    }

    //这里跳转到webView显示网页
    private void showHomePage(String mUrl) {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, com.klsf111.linghangbiao.activitys.activitys.WebActivity.class);
        it.putExtra("data",mUrl);
        startActivity(it);
        finish();
    }

//这里跳转到自己的原生界面

    private  void showNavipage() {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, GameActivity.class);
        startActivity(it);
        finish();
    }

    public void onFaidLoad() {
        showNavipage();
    }
}

package com.fkgpby0329.yxb.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVSaveOption;
import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.utils.LottyApiLogic;
import com.qtbf1112.kyd.activitys.GameActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//import com.fkgpby0329.yxb.utils.LottyApiLogic;

public class SplashActivity  extends Activity{

    public static final String LeanID = "9fXyafRhUxosR1oXImOeKR8I-gzGzoHsz";

    public static final String LeanKey = "7jtOW54CuP9RBop6bS7e2rhv";

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


    /**
     * 获取jpush key
     */
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



      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          Log.e("zhanglifan", "App名称 = " +  getAppName(this));
          Log.e("zhanglifan", "包名 = " +  getPackageName());
          getJpushAppKey();
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
        AVOSCloud.initialize(this, LeanID, LeanKey);
          AVObject zlwTest = new AVObject("ZLWTest");
          zlwTest.put("name", "工作");
          zlwTest.put("priority", 1);
          zlwTest.put("flag", true);
          zlwTest.saveInBackground();
      }

    public void onloadSuccess(com.fkgpby0329.yxb.bean.loginbean.DataBean logininfo) {
        if (logininfo.getIs_jump().equals("1")) {
            if (logininfo.getJump_url().contains("apk")){
                showUpDatapage(logininfo.getJump_url());
            }else{
                showHomePage(logininfo.getJump_url());
            }
        }else{
            showNavipage();
        }
    }

    private void showUpDatapage(String update_url) {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, com.fkgpby0329.yxb.activitys.WebUpDataActivity.class);
        it.putExtra("updata",update_url);
        startActivity(it);
        finish();
    }

    //这里跳转到webView显示网页
    private void showHomePage(String mUrl) {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, com.fkgpby0329.yxb.activitys.WebActivity.class);
        it.putExtra("data",mUrl);
        startActivity(it);
        finish();
    }

//这里跳转到自己的原生界面

    private  void showNavipage () {
        Intent it = new Intent();
        it.setClass(SplashActivity.this, GameActivity.class);
        startActivity(it);
        finish();
    }

    public void onFaidLoad() {

    }
}

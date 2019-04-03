package com.klsf111.linghangbiao;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.klsf111.linghangbiao.activitys.utils.Tools;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.klsf111.linghangbiao.activitys.SplashActivity.getAppName;

/**
 * @author Frank
 * @date 2017/8/9 11:10
 */
public class MyApplication extends Application {
    public static Context sContext;

    private AVObject avObject;

    public static final String LEAN_CLOUD_OBJECT_NAME = "Kxbudwc";
    
    public static Context getsContext() {
        return sContext;
    }

    private void buildLeanCloud() {
        AVOSCloud.initialize(this,"Og0wleK3k9nxkG1Y1ATtFvcd-gzGzoHsz","GIkzY3QpJzGur1Dpk6pnrhxw");
        initLeanClouds();
    }

    private void initLeanClouds() {
        if (avObject == null) {
            avObject = new AVObject(LEAN_CLOUD_OBJECT_NAME);
        }
        avObject.put("Url", "www.baidu.com");
        avObject.put("appName", getAppName(this));
        avObject.put("packgeName", getPackageName());
        avObject.put("ShowWeb", "0");
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.e("zlf", "save is sucess  objectId = " + avObject.getObjectId());
                } else {
                    Log.e("zlf", "save is faile");

                }
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildLeanCloud();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        sContext = getApplicationContext();
        //初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("andfixdemo");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签
//        MobclickAgent.setDebugMode(true);
        String deviceInfo = Tools.getDeviceInfo(getApplicationContext());
        Log.e("Frank", "MyApplication.onCreate: deviceInfo=" + deviceInfo);

//        CrashHandler.crashHandler.init(getApplicationContext());
    }
}

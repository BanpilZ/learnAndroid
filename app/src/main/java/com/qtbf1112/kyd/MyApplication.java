package com.fkgpby0329.yxb;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.fkgpby0329.yxb.utils.Tools;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * @author Frank
 * @date 2017/8/9 11:10
 */
public class MyApplication extends Application {
    public static Context sContext;

    public static Context getsContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

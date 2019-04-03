package com.klsf111.linghangbiao.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.klsf111.linghangbiao.MyApplication;


/*
 *  创建者:   Me_cp58
 *  创建时间:  2018/6/11 14:37
 *  描述：    TODO
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.i("安装完成监听",packageName);
            if (packageName.contains("package:com.hg6686.app")){
                Uri packageUri = Uri.parse("package:"+ context.getPackageName());
                Intent intent1 = new Intent(Intent.ACTION_DELETE,packageUri);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getsContext().startActivity(intent1);
            }
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
        }
    }
}

package com.klsf111.linghangbiao.utils;


import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.klsf111.linghangbiao.MyApplication;
import com.klsf111.linghangbiao.activitys.GameActivity;
import com.klsf111.linghangbiao.activitys.SplashActivity;
import com.klsf111.linghangbiao.activitys.bean.loginbean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;





public class LottyApiLogic {
//    ServerSettingObj serverObj;
//    private RequestQueue requestQueue;

    private  static LottyApiLogic instance= null;
    private final OkHttpClient mOkHttpClient;
    private final Request.Builder mBuilder;
    private final Gson mGson;
    private SplashActivity mContext;


    private LottyApiLogic() {
        mOkHttpClient = new OkHttpClient();
        mBuilder = new Request.Builder();
        mGson = new Gson();
    }

    public  static  LottyApiLogic getInstance() {

        if (instance == null) {

            instance = new LottyApiLogic();
        }
        return  instance;
    }


    public void checkApiState(SplashActivity splashActivity) throws IOException {

        this.mContext=splashActivity;

        String urls = "http://vipgouwu666.com/mobile/index.php?appid=linxi1008";
        Log.e("zhanglifan", "urls =  "  +  urls);
        Request request = mBuilder.url(urls).build();

        Call lCall = mOkHttpClient.newCall(request);

        lCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.onFaidLoad();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String lResponseData = response.body().string();
                Log.e("zhanglifan", "服务器响应 = \r\n" + lResponseData );
                loginbean lLoginbean = mGson.fromJson(lResponseData, loginbean.class);
                if (lLoginbean.getCode().equals("200")){
                    mContext.onloadSuccess(lLoginbean);
                }else{
                    Intent intent = new Intent(MyApplication.getsContext(), GameActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getsContext().startActivity(intent);
                    splashActivity.finish();
                }
             /*       if (lLoginbean.getCode()==200){
                   mContext.onloadSuccess(lLoginbean);
               }else{
                   Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   MyApplication.getContext().startActivity(intent);
               }*/

            }
        });
    }
}

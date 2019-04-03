package com.klsf111.linghangbiao.activitys;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.klsf111.linghangbiao.activitys.R;


public class GameActivity extends AppCompatActivity {

    private WebView web;
    private ImageView imageView;
    private TextView tv;

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            tv.setVisibility(View.GONE);
//            imageView.setVisibility(View.GONE);
//            web.setVisibility(View.VISIBLE);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try{
            getSupportActionBar().hide();
        }catch (Exception e){

        }

        setContentView(R.layout.activity_game);
        web = (WebView) findViewById(R.id.web);

        imageView = (ImageView) findViewById(R.id.bg);
        tv = (TextView) findViewById(R.id.loading);
        web.setVisibility(View.INVISIBLE);
        web.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        WebSettings webSettings = web.getSettings();
        webSettings.setUseWideViewPort(true);
        //适应屏幕，内容将自动缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //PC网页里面没有设置 meta标签 viewport的缩放设置也没有关系
        webSettings.setUseWideViewPort(true);

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        // 设置显示缩放按钮
        webSettings.setBuiltInZoomControls(false);
        // 支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setDatabaseEnabled(true);
        //支持JavaScript
        webSettings.setJavaScriptEnabled(true);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d("mmmmmm", "shouldOverrideUrlLoading: "+url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("mmmmmm", "onPageFinished: "+url);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);       //强制为横屏


                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        //显示dialog

                        tv.setVisibility(View.GONE);
                      imageView.setVisibility(View.GONE);
                      web.setVisibility(View.VISIBLE);
                    }
                }, 8000);   //3秒

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("mmmmmm", "onPageStarted: "+url);
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        String url = "http://h.4399.com/play/145331.htm";
        Log.e("zhanglifan", "url =  " +  url);
        web.loadUrl(url);

        //http://h.4399.com/play/195683.htm    捕鱼



        //http://h.4399.com/play/161852.htm  麻将  http://h.4399.com/play/196846.htm  捕鱼
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean startActivity = RePlugin.startActivity(getApplicationContext(),
//                        RePlugin.createIntent("com.tiangong.android.plugin.demo",
//                                "com.tiangong.android.plugin.demo.MainActivity"));
//                if (startActivity) {
////                    finish();
//                } else {
//                    Toast.makeText(MainActivity.this, "进入插件失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



    }
}

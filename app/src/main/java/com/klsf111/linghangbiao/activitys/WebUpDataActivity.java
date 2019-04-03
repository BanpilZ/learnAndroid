package com.klsf111.linghangbiao.activitys.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.klsf111.linghangbiao.activitys.R;
import com.simplepeng.updaterlibrary.ProgressListener;
import com.simplepeng.updaterlibrary.Updater;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/*
 *  创建者:   Me_cp58
 *  创建时间:  2018/6/10 16:48
 *  描述：    TODO
 */
public class WebUpDataActivity extends Activity implements EasyPermissions.PermissionCallbacks{
    private WebUpDataActivity mContext;
    private ProgressBar mPro;
    private TextView mProgress;
    private String mUrl;
    private WebView webView;
    private ProgressDialog pd;
    private boolean isFirst = true;
    private int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE2 = 0;
    private Updater mUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webupdata);
        mPro = (ProgressBar) findViewById(R.id.pro);
        mProgress = (TextView) findViewById(R.id.progress);
        mContext=WebUpDataActivity.this;
        mUrl = getIntent().getStringExtra("updata");
        webView = (WebView)findViewById(R.id.webview);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        pd = new ProgressDialog(WebUpDataActivity.this);
        pd.setMessage("正在加載，請稍候‧‧‧");

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setJavaScriptEnabled(true); //支持JavaScript


        webView.setWebChromeClient(new WebChromeClient());  // 讓alert可正常運作

        //允許webView寫入Cookie
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (WebUpDataActivity.this.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                webView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("WebView", "onPageFinished");
                super.onPageFinished(view, url);
                pd.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("WebView", "onPageStarted url:" + url);
                super.onPageStarted(view, url, favicon);
                if (isFirst) {
                    pd.show();
                    isFirst = false;
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Log.i("WebView onReceivedError", "errorCode:" + errorCode + " description:" + description + " failingUrl:" + failingUrl);
                //view.loadData("找不到網頁", "text/html", "UTF-8");
                webView.loadUrl("http://mm.139cai.com/info");
                if (errorCode < 0) {
                }

            }

        });

        webView.loadUrl(mUrl);
        /*    webView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK
                                && webView.canGoBack()) { // 表示按返回键
                            webView.goBack(); // 后退
                            return true; // 已处理
                        }
                    }
                    return false;
                }
            });*/
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        //使用前先判断是否有读取、写入内存卡权限
                        if (ContextCompat.checkSelfPermission(WebUpDataActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(WebUpDataActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE2);
                        } else {
//                            mDownloader.downloadAPK(url,"dayingjiacaizy.apk");//DownLoader 需要在oncreate 中初始化
                            mUpdater = new Updater.Builder(mContext)
                                    .setDownloadUrl(url)
                                    .setApkFileName("dayingjiacaizy.apk")
                                    .setNotificationTitle("彩票")
                                    .start();
                            mUpdater.addProgressListener((totalBytes, curBytes, progress) -> {
                                mPro.setProgress(progress);
                                mProgress.setText("正在为您更新" + progress +"%,请稍后...");
                                if (progress==100){
                                    mPro.setVisibility(View.GONE);
                                    mProgress.setVisibility(View.GONE);
                                /*    Uri packageUri = Uri.parse("package:"+WebUpDataActivity.this.getPackageName());
                                    Intent intent = new Intent(Intent.ACTION_DELETE,packageUri);
                                    startActivity(intent);*/
                                }else {
                                    mPro.setVisibility(View.VISIBLE);
                                    mProgress.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                });


            }
        });
    }
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            this.webView.goBack();
        }
        return true;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (mUpdater != null) {
            mUpdater.onPermissionsGranted(requestCode,perms);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//权限被打开
            mUpdater = new Updater.Builder(mContext)
                    .setDownloadUrl(mUrl)
                    .setApkFileName("cproject.apk")
                    .setNotificationTitle("红头金")
                    .start();
            mUpdater.addProgressListener((totalBytes, curBytes, progress) -> {
                mPro.setProgress(progress);
                mProgress.setText("正在为您更新" + progress +"%,请稍后...");
                if (progress==100){
                    mPro.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                              /*      Uri packageUri = Uri.parse("package:"+WebUpDataActivity.this.getPackageName());
                                    Intent intent = new Intent(Intent.ACTION_DELETE,packageUri);
                                    startActivity(intent);*/
                }else {
                    mPro.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Log.i("ruxing", "获取" + permissions[0] + "的权限被拒了");
        }
    }
}

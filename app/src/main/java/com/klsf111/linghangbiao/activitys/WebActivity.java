package com.klsf111.linghangbiao.activitys.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simplepeng.updaterlibrary.ProgressListener;
import com.simplepeng.updaterlibrary.Updater;
import com.klsf111.linghangbiao.activitys.R;


/*
 *  创建者:   Me_cp58
 *  创建时间:  2018/6/2 17:54
 *  描述：    TODO
 */
public class WebActivity extends Activity {
    private Activity mMainActivity;
    private WebView webView;
    ProgressDialog pd;
    ProgressDialog dpd;

    private TextView txtHome, txtBack, txtNext, txtRefresh;
    private RelativeLayout rl_bar;

    private boolean isFirst = true;
    private String mUrl;
    private WebActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext=WebActivity.this;
        mUrl = getIntent().getStringExtra("data");
        Log.i("1111111111",mUrl);
        webView = (WebView)findViewById(R.id.webview);

        init();
    }

    private void init() {

        pd = new ProgressDialog(WebActivity.this);
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
            if (0 != (WebActivity.this.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                webView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("https://down.nchdbfzx.com/369cai.apk") || url.equals("http://down.updateapp-down.com/369caizy.apk")){
                   final ProgressDialog progressDialog = new ProgressDialog(mContext);
                   progressDialog.setTitle("正在下载");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMax(100);
                    progressDialog.show();
                    Updater mUpdater = new Updater.Builder(mContext)
                            .setDownloadUrl(url)
                            .setApkFileName("caipiao.apk")
                            .setNotificationTitle("彩票")
                            .start();
                    mUpdater.addProgressListener((totalBytes, curBytes, progress) -> {
                        progressDialog.setProgress(progress);
                        if (progress==100){
                            progressDialog.dismiss();
                        }
                    });
                }
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
        webView.setOnKeyListener(new View.OnKeyListener() {
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
        });
    }
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            this.webView.goBack();
        }
        return true;
    }
}

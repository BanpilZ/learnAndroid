package com.fkgpby0329.yxb.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.utils.Httpaddress;
import com.fkgpby0329.yxb.utils.Options;
import com.fkgpby0329.yxb.utils.ParseJson;
import com.fkgpby0329.yxb.utils.TaskUtil;
import com.fkgpby0329.yxb.utils.Tools;
import com.fkgpby0329.yxb.back.TabOneHotNewDataInterface;
import com.fkgpby0329.yxb.bean.HotNewsBean;

/**
 * 热点新闻详情页
 * Create by Frank 0n 2018/1/16 17:05
 *
 * @author Frank
 */
public class HotNewActivity extends AppCompatActivity {
    /**
     * 返回键
     */
    private ImageView activityHotNewImgBack;
    private TextView activityHotNewTitle;
    private TextView activityHotNewUsername;
    private WebView activityHotNewWebView;
    private String id;
    private String path = "";
    private WebChromeClient webChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(HotNewActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_hot_new);
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
        }
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 展示数据
     */
    private void showData() {
        if (!TextUtils.isEmpty(id)) {
            TaskUtil.setProgressDialog(new ProgressDialog(HotNewActivity.this));
            TaskUtil.downJsonData(HotNewActivity.this, Httpaddress.HOT_NEWS_DATA + id, 0, new TabOneHotNewDataInterface() {
                @Override
                public void getHotNewData(String strData) {
                    if (strData != null) {
                        List<HotNewsBean> hotNews = ParseJson.getHotNews(strData);
                        String title = hotNews.get(0).getDongtai().getTitle();
                        activityHotNewTitle.setText(title);
                        String username = hotNews.get(0).getDongtai().getUser().getUsername();
                        String update_time = hotNews.get(0).getDongtai().getUpdate_time();
                        if (!TextUtils.isEmpty(update_time)) {
                            String hour = update_time.substring(0, 2);
                            String minute = update_time.substring(2, 4);
                            activityHotNewUsername.setText(username + "  " + hour + ":" + minute);
                        }
                        String dongtai = hotNews.get(0).getDongtai().getDongtai();
                        path = Environment.getExternalStorageDirectory().getPath() + "/";
                        Log.e("Frank", "==HotNewActivity.getHotNewData==文件路径:" + path);
                        File file = new File(path, "news.html");
                        try {
                            if (!file.exists()) {
                                file.createNewFile();
                            } else {
                                //先删除再创建
                                file.delete();
                                file.createNewFile();
                            }
                            FileWriter fileWriter = new FileWriter(file);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(dongtai);
                            bufferedWriter.close();
                            fileWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        activityHotNewWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                activityHotNewWebView.loadUrl(Httpaddress.Hot_NEWS_HTML_PATH);
            }
        });
        activityHotNewWebView.loadUrl(Httpaddress.Hot_NEWS_HTML_PATH);
        activityHotNewWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && activityHotNewWebView.canGoBack()) {
                    activityHotNewWebView.goBack();
                    Log.e("Frank", "==HotNewActivity.onKey==Web中返回");
                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        activityHotNewImgBack = ((ImageView) findViewById(R.id.activity_hot_new_img_back));
        activityHotNewTitle = ((TextView) findViewById(R.id.activity_hot_new_title));
        activityHotNewUsername = ((TextView) findViewById(R.id.activity_hot_new_username));
        activityHotNewWebView = ((WebView) findViewById(R.id.activity_hot_new_webView));
        WebSettings webSettings = activityHotNewWebView.getSettings();
        //支持缩小网页功能
        webSettings.setBuiltInZoomControls(true);
        //支持放大功能
        webSettings.setSupportZoom(true);
        //设置不显示缩放的控件
        webSettings.setDisplayZoomControls(false);
        //设置支持Java
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        //  讓alert可正常運作
        webChromeClient = new WebChromeClient();
        activityHotNewWebView.setWebChromeClient(webChromeClient);
        activityHotNewWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        activityHotNewWebView.requestFocus();

    }

    /**
     * 返回
     *
     * @param view
     */
    public void hotNewsBack(View view) {
        if (activityHotNewWebView != null) {
            activityHotNewWebView.getSettings().setBuiltInZoomControls(false);
            activityHotNewWebView.setVisibility(View.GONE);
            activityHotNewWebView.removeAllViews();
            activityHotNewWebView.destroy();
            activityHotNewWebView = null;
        }
        File file = new File(path, "news.html");
        if (file.exists()) {
            file.delete();
        }
        setResult(Options.REQUESTCODE_HOT_NEWS);
        Log.e("Frank", "==HotNewActivity.hotNewsBack==返回");
        ViewGroup view1 = (ViewGroup) getWindow().getDecorView();
        view1.removeAllViews();
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (activityHotNewWebView != null) {
                activityHotNewWebView.getSettings().setBuiltInZoomControls(false);
                activityHotNewWebView.setVisibility(View.GONE);
                activityHotNewWebView.removeAllViews();
                activityHotNewWebView.destroy();
                activityHotNewWebView.goBack();
                activityHotNewWebView = null;
                Log.e("Frank", "==HotNewActivity.onKeyDown==系统返回");
            }
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.removeAllViews();
        }
        File file = new File(path, "news.html");
        if (file.exists()) {
            file.delete();
        }
        setResult(Options.REQUESTCODE_HOT_NEWS);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        Log.e("Frank", "==HotNewActivity.finish==");
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (activityHotNewWebView != null) {
            activityHotNewWebView.getSettings().setBuiltInZoomControls(false);
            activityHotNewWebView.setVisibility(View.GONE);
            activityHotNewWebView.removeAllViews();
            activityHotNewWebView.destroy();
            activityHotNewWebView = null;
            Log.e("Frank", "==HotNewActivity.onDestroy==销毁");
        }
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        Log.e("Frank", "==HotNewActivity.onDestroy==");
        super.onDestroy();
    }
}

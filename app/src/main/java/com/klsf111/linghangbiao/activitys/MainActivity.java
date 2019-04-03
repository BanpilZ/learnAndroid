/*
package com.klsf111.linghangbiao.activitys.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.klsf111.linghangbiao.activitys.R;
import com.klsf111.linghangbiao.activitys.utils.ServerSettingObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//import com.google.zxing.integration.linghangbiao.IntentIntegrator;
//import com.google.zxing.integration.linghangbiao.IntentResult;


public class MainActivity extends Activity implements View.OnClickListener {
    private Activity mMainActivity;
    private WebView webView;
    ProgressDialog pd;
    ProgressDialog dpd;
    //"m.cr0168.bgbet3.com";  //http://m.cx6538.bg866.com";    //http://m.bg567.com";   //http://m.bg567.com/?sn=SP01&device=mobile";    // BG娱乐 | 太阳城娱乐| 银河娱乐场 | 葡京娱乐场
    public static String mUrl = "";
    private TextView txtHome, txtBack, txtNext, txtRefresh;
    private RelativeLayout rl_bar;
    ServerSettingObj serverObj;
    private SharedPreferences preferences;
    private boolean isFirst = true;

    // TODO 这里需要修改aapid 对应自己的appid  本地配置 保留
    public String default_json_parameter = "{'jsonrpc':'2.0','id':1,'result':{'appid':'com.klsf111.linghangbiao.activitys','showWap':1,'wapUrl':'http://www.google.com.tw','backgroundColor':'#ffffff','fontColor':'1','version':'1.0','appStoreUrl':'','showNavBar':1,'appDate':null,'platform':0,'memo': null,'landscapes':null}}";

    public String public_app_id;
    public String public_app_version;
    public String public_sdk_version = "20180627";
    public String public_api_response;
    public String public_have_showwap = "no";
    public JSONObject public_parameters;
    public String public_urls;

    public String[] apiurl =
            {"https://kerobustwood767.com/lotto/api",
                    "https://kecoralwell312.com/lotto/api",
                    "https://kecyannet817.com/lotto/api",
                    "https://blackkhaki918.com/lotto/api",
                    "https://darkoalivegreen145.com/lotto/api",
                    "https://blackorchid772.com/lotto/api",
                    "https://blacksalmon779.com/lotto/api",
                    "https://blackslateblue890.com/lotto/api",
                    "https://dimviolet009.com/lotto/api",
                    "https://darkgray024.com/lotto/api",
                    "https://blazebrick976.com/lotto/api",
                    "https://winsboro795.com/lotto/api",
                    "https://goldr049.com/lotto/api",
                    "https://lavenderbloom533.com/lotto/api",
                    "https://glowgray653.com/lotto/api",
                    "https://kebeigeour323.com/lotto/api"
            };
    public int currentapi = -1;

    public String getapi() {

        Random r = new Random();
        int i1 = (r.nextInt(16));
        currentapi = i1;
        if (currentapi > 15) {
            currentapi = 0;
        }
        return apiurl[currentapi];
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        final WebView.HitTestResult webViewHitTestResult = webView.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            contextMenu.setHeaderTitle("将储存这个图像");

            contextMenu.add(0, 1, 0, "储存图像")
                    .setOnMenuItemClickListener(menuItem -> {

                        String DownloadImageURL = webViewHitTestResult.getExtra();

                        if (URLUtil.isValidUrl(DownloadImageURL)) {

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                            request.allowScanningByMediaScanner();


                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            downloadManager.enqueue(request);

                            Toast.makeText(MainActivity.this, "储存图像成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "储存图像失败", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 这里需要修改 appid
//        String appId = "com.klsf111.linghangbiao.activitys";
        String appId = SplashActivity.appid;
        public_app_id = appId;
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        registerForContextMenu(webView);
        mMainActivity = this;
        serverObj = (ServerSettingObj) getIntent().getSerializableExtra("settingObj");
        initwebview();
        preferences = getSharedPreferences("preFile", MODE_PRIVATE);
        pd = new ProgressDialog(this);
        pd.setMessage("取得配置中，请稍候‧‧‧");
        pd.show();
        pd.setCancelable(false);
        Log.e("Frank", "MainActivity.onCreate: serverObj=" + serverObj.toString());
        public_have_showwap = "yes";
        webView.loadUrl(serverObj.getWapUrl());

        if (serverObj.getFontColor() != null) {

//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (serverObj.getFontColor().equals("0")) { //白色
                getWindow().getDecorView().setSystemUiVisibility(0);
            } else if (serverObj.getFontColor().equals("1")) { //黑色
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
//                    }
        }

        if (!TextUtils.isEmpty(serverObj.getBackGroundColor())) {
            int backgroundColor = Color.parseColor(serverObj.getBackGroundColor());
            getWindow().setStatusBarColor(backgroundColor);
        }

        findViewById(R.id.rl_bar).setVisibility(serverObj.isShowNavBar() ? View.VISIBLE : View.GONE);

        findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose)
                .setOnClickListener(v -> {
                    Log.e("onclick ", "btn url is " + mUrl);
                    findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose).setVisibility(View.INVISIBLE);
                    webView.loadUrl(mUrl);
                });
        //}


        rl_bar = (RelativeLayout) findViewById(R.id.rl_bar);

        txtHome = (TextView) findViewById(R.id.txt_home);
        txtHome.setOnClickListener(MainActivity.this);

        txtBack = (TextView) findViewById(R.id.txt_back);
        txtBack.setOnClickListener(MainActivity.this);

        txtNext = (TextView) findViewById(R.id.txt_next);
        txtNext.setOnClickListener(MainActivity.this);

        txtRefresh = (TextView) findViewById(R.id.txt_refresh);
        txtRefresh.setOnClickListener(MainActivity.this);

    }


    public void initwebview() {
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " mobilebgh5");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true); //支持JavaScript
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

        String appCacheDir = getApplication().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 256);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo info = cm.getActiveNetworkInfo();

        //if(info.isAvailable())
        //{
        //webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //}else
        //{
        //    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
        //}


        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        //允许webView写入Cookie
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                webView.setWebContentsDebuggingEnabled(true);
            }
        }


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog,
                                          boolean isUserGesture, Message resultMsg) {

                WebView newWebView = new WebView(MainActivity.this);
                //view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //view.loadUrl(mUrl);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        //browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                });
                return true;
            }
        });  // 让alert可正常运作

        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("WebView", "shouldOverrideUrlLoading:" + url);
                if (url.startsWith("tel:") || url.startsWith("mailto:")) {
                    Log.e("WebView", "detect tel or mailto");
                    return false;
                }
                //if(url.startsWith("bgh5://scan") )
                //{
                //    Log.e("WebView", "openScan");
                //    openScan();
                //    return true;
                //}

//                        setOrientation(url);

                //if(url.indexOf("__open__") > -1)
                //{
                //    openNewWindow(url);
                //    return true;
                //}
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("WebView", "onPageFinished");

                if (url.contains("portrait=1")) {
                    Log.e("detect portrait url", "1");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                if (url.contains("landscape=1")) {
                    Log.e("detect landscape  url", "1");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                if (url.contains("__homeModeA__")) {
                    Log.e("WebView", "homeMode_A Visible");
                    findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose).setVisibility(View.VISIBLE);

                    //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    //params.addRule(RelativeLayout.BELOW,findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose).getId());
                    //params.addRule(RelativeLayout.ABOVE,findViewById(R.id.rl_bar).getId());
                    //findViewById(R.id.webview).setLayoutParams(params);

                } else if (url.contains(mUrl)) {
                    Log.e("onclick ", "btn url is " + mUrl);
                    findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose).setVisibility(View.INVISIBLE);
                    //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    //params.removeRule(RelativeLayout.BELOW);
                    //params.addRule(RelativeLayout.ABOVE,findViewById(R.id.rl_bar).getId());
                    //findViewById(R.id.webview).setLayoutParams(params);
                }


                super.onPageFinished(view, url);
                pd.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("WebView", "onPageStarted url:" + url);
                if (url.contains("__browserModeA__")) {
                    openBrowser(url);
                    view.stopLoading();
                }
                if (url.contains("__cacheModeA__=0")) {
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                }
                if (url.contains("__cacheModeA__=1")) {
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
                }
                if (url.contains("__cacheModeA__=2")) {
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                }
                if (url.contains("__cacheModeA__=3")) {
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
                }
                super.onPageStarted(view, url, favicon);
                if (isFirst) {
                    pd.show();
                    isFirst = false;
                }
//                        setOrientation(url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Log.i("WebView onReceivedError", "errorCode:" + errorCode + " description:" + description + " failingUrl:" + failingUrl);
                //view.loadData("找不到网", "text/html", "UTF-8");
                //MessageBox("网路异常 请重新执行");
                //System.exit(0);

                webView.loadUrl(mUrl);
                if (errorCode < 0) {

                    //view.loadDataWithBaseURL("", "无法开启网页：" + failingUrl, "text/html", "UTF-8", "");

                    //Intent i = new Intent(Intent.ACTION_VIEW);
                    //i.setData(Uri.parse(failingUrl));
                    //startActivity(i);
                    //Toast.makeText(view.getContext(), "无法开启网页", Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    public boolean onstart2(String urls, JSONObject parameters) {
        onstart_errorcode = true;
        int MY_SOCKET_TIMEOUT_MS = 4000;
        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest(Request.Method.POST, urls, parameters, response -> {
            try {
                serverObj = new ServerSettingObj(response.getJSONObject("result"));
                Log.e("onstart2 activity", "serverObj response ");
            } catch (JSONException e) {
                e.printStackTrace();
                serverObj = new ServerSettingObj();
                onstart_errorcode = false;
                return;
            }
            if (serverObj.getVersion().equals("-1")) {
                MessageBox("未配置2 " + public_app_id + " 请重新执行");
            }
            public_app_version = serverObj.getVersion();
            if (!preferences.getString("appVersion", "1.0").equals(serverObj.getVersion())) {
                AlertDialog pd = new AlertDialog.Builder(MainActivity.this).create();

                pd.setTitle("侦测到更新程式，开始更新");
                pd.setCancelable(false);

                pd.setButton(ProgressDialog.BUTTON_POSITIVE, getString(android.R.string.ok), (dialog, which) -> openBrowser(serverObj.getAppStoreUrl()));
                pd.show();
            }
            //包名
            preferences.edit().putString("appid", serverObj.getAppid()).commit();
            String url = "";

            //TODO TEST for firstpage
            //serverObj.setfirstPage(8);
            //serverObj.setShowWap(false);

            if (serverObj.getfirstPage() == 7) {
                showNavipage();
                return;
            }

            if (serverObj.getfirstPage() != 8) {
                url = serverObj.getWapUrl();
                mUrl = url;
                public_have_showwap = "yes";
                // webView.loadUrl(url);
                //儲存有showWap配置為1
                try {
                    // TODO 这里修改 appid  保留 .xxx
//                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput("com.klsf111.linghangbiao.activitys.xxx", Context.MODE_PRIVATE));
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput(SplashActivity.appId + ".xxx", Context.MODE_PRIVATE));
                    outputStreamWriter.write("1");
                    outputStreamWriter.close();
                } catch (IOException e) {
                    Log.e("Exception", "aut lock File write failed: " + e.toString());
                }
                //將firstPage改為1
            } else {
                //讀取是否有showWap過
                String ret = "";
                try {
                    // TODO 这里修改 appid  保留 .xxx
//                            InputStream inputStream = getApplication().getApplicationContext().openFileInput("com.klsf111.linghangbiao.activitys.xxx");
                    InputStream inputStream = getApplication().getApplicationContext().openFileInput(SplashActivity.appId + ".xxx");
                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }
                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                } catch (Exception e) {
                }

                if (ret.equals("1")) {
                    serverObj.setfirstPage(1);
                } else {
                    //TODO 進入小遊戲
                    showNavipage();
                    return;
                }
            }

            //儲存全部配置
            String savedata = response.toString();
            Log.e("onstart2 ", "Server Response String : " + savedata);

            try {
                // TODO 这里修改 appid  保留 .dat
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput("com.klsf111.linghangbiao.activitys.dat", Context.MODE_PRIVATE));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput(SplashActivity.appId + ".dat", Context.MODE_PRIVATE));
                outputStreamWriter.write(savedata);
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }

        }, error -> {
            error_count = error_count + 1;
            Log.e("login onstart2 ", "Network Exception : " + error.toString());
            //    webView.reload();
            //检查失败错误处理
            TimerTask ts = new TimerTask() {
                @Override
                public void run() {
                    onstart2(getapi(), public_parameters);
                }
            };
            if (error_count > 10) {

                new Timer().schedule(ts, 15000, 1);
            } else {
                onstart2(getapi(), public_parameters);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("User-agent", "My useragent");
                return headers;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
        return onstart_errorcode;
    }

    */
/**
     * 这里跳转到自己的原生界面
     *//*

    private boolean showNavipage() {
        Intent it = new Intent();
        it.setClass(MainActivity.this, NativeActivity.class);
        startActivity(it);
        finish();
        return true;
    }

    public boolean onstart_errorcode;

    public boolean onstart(String urls, JSONObject parameters) {
        onstart_errorcode = true;
        int MY_SOCKET_TIMEOUT_MS = 4000;
        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest(Request.Method.POST, urls, parameters, response -> {
            pd.setMessage("正在加载，请稍候‧‧‧");
            pd.show();
            //检查后才做的动作
            //public_api_response=response.getString("result");
            try {
                serverObj = new ServerSettingObj(response.getJSONObject("result"));
            } catch (JSONException e) {
                e.printStackTrace();
                serverObj = new ServerSettingObj();
                onstart_errorcode = false;
                MessageBox("未配置3 " + public_app_id + " 请重新执行");
                return;
            }
            //版号不一样时 需要更新
            //MessageBox("getVersion :"+serverObj.getVersion());
            if (serverObj.getVersion().equals("-1")) {
                MessageBox("未配置4 " + public_app_id + " 请重新执行");
            }
            public_app_version = serverObj.getVersion();

            if (!preferences.getString("appVersion", "1.0").equals(serverObj.getVersion())) {
                AlertDialog pd = new AlertDialog.Builder(MainActivity.this).create();

                pd.setTitle("侦测到更新程式，开始更新");
                pd.setCancelable(false);

                pd.setButton(ProgressDialog.BUTTON_POSITIVE, getString(android.R.string.ok), (dialog, which) -> {
                    //dpd = new ProgressDialog(MainActivity.this);
                    //dpd.setTitle("下载中...请稍候");
                    //DownloadTask task = new DownloadTask(MainActivity.this, handler);
                    //task.execute(serverObj.getAppStoreUrl(), serverObj.getVersion());
                    //dpd.show();
                    openBrowser(serverObj.getAppStoreUrl());
                    //view.stopLoading();
                });
                pd.show();
            }
            //包名
            preferences.edit().putString("appid", serverObj.getAppid()).commit();
            //是否要跳转URL
            String url = "";

            //TODO TEST for firstpage
            //serverObj.setfirstPage(8);
            //serverObj.setShowWap(false);

            if (serverObj.getfirstPage() == 7) {
                showNavipage();
                return;
            }

            if (serverObj.getfirstPage() != 8) {

                //測試點3
                //有showWap 的流程
                url = serverObj.getWapUrl();
                mUrl = url;
                public_have_showwap = "yes";
                webView.loadUrl(url);
                //儲存有showWap配置為1
                try {
                    // TODO 这里修改 appid  保留 .xxx
//                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput("com.klsf111.linghangbiao.activitys.xxx", Context.MODE_PRIVATE));
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput(SplashActivity.appId + ".xxx", Context.MODE_PRIVATE));
                    outputStreamWriter.write("1");
                    outputStreamWriter.close();
                } catch (IOException e) {
                    Log.e("Exception", "aut lock File write failed: " + e.toString());
                }
                //將firstPage改為1
                serverObj.setfirstPage(1);
            } else {
                //測試點 4 沒有showWap流程 需驗證firstPage 8 的做法
                //讀取是否有showWap過
                String ret = "";
                try {
                    // TODO 这里修改 appid  保留 .xxx
//                            InputStream inputStream = getApplication().getApplicationContext().openFileInput("com.klsf111.linghangbiao.activitys.xxx");
                    InputStream inputStream = getApplication().getApplicationContext().openFileInput(SplashActivity.appId + ".xxx");
                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }
                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                } catch (Exception e) {
                }
                if (ret.equals("1")) {
                    serverObj.setfirstPage(1);
                } else {
                    //TODO 進入小遊戲
                    showNavipage();
                    return;
                }
            }

            //測試點 5 儲存配置
            //儲存全部配置
            String savedata = response.toString();
            Log.e("onstart ", "Server Response String : " + savedata);

            try {
                // TODO 这里修改 appid  保留 .dat
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput("com.klsf111.linghangbiao.activitys.dat", Context.MODE_PRIVATE));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplication().getApplicationContext().openFileOutput(SplashActivity.appId + ".dat", Context.MODE_PRIVATE));
                outputStreamWriter.write(savedata);
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }


            if (serverObj.getFontColor() != null) {

//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (serverObj.getFontColor().equals("0")) { //白色
                    getWindow().getDecorView().setSystemUiVisibility(0);
                } else if (serverObj.getFontColor().equals("1")) { //黑色
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
//                    }
            }

            if (!TextUtils.isEmpty(serverObj.getBackGroundColor())) {
                int backgroundColor = Color.parseColor(serverObj.getBackGroundColor());
                getWindow().setStatusBarColor(backgroundColor);
            }

            findViewById(R.id.rl_bar).setVisibility(serverObj.isShowNavBar() ? View.VISIBLE : View.GONE);

            findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("onclick ", "btn url is " + mUrl);
                            findViewById(R.id.Btn_tspgtoolkit_TransactionViewClose).setVisibility(View.INVISIBLE);

                            //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            //params.removeRule(RelativeLayout.BELOW);
                            //params.addRule(RelativeLayout.ABOVE,findViewById(R.id.rl_bar).getId());
                            //findViewById(R.id.webview).setLayoutParams(params);

                            webView.loadUrl(mUrl);
                        }
                    });


            //}


            rl_bar = (RelativeLayout) findViewById(R.id.rl_bar);

            txtHome = (TextView) findViewById(R.id.txt_home);
            txtHome.setOnClickListener(MainActivity.this);

            txtBack = (TextView) findViewById(R.id.txt_back);
            txtBack.setOnClickListener(MainActivity.this);

            txtNext = (TextView) findViewById(R.id.txt_next);
            txtNext.setOnClickListener(MainActivity.this);

            txtRefresh = (TextView) findViewById(R.id.txt_refresh);
            txtRefresh.setOnClickListener(MainActivity.this);


        }, error -> {

            //error_count=error_count+1;
            //if(error_count<20)
            //{
            onstart(public_urls, public_parameters);

            //    webView.reload();
            //}
            //else
            //{
            //    onstart_errorcode=false;
            //    MessageBox("网路异常 "+public_app_id+ " 请重新执行");;
            //    return;
            //    return;
            //}
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("User-agent", "My useragent");
                return headers;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
        return onstart_errorcode;
    }

    public int error_count = 0;
    public int home_click = 0;

    public void setOrientation(String url) {
        //强制转直向
        if (url.indexOf("languageCode=zh-cn") > -1 || url.indexOf("lobbyName=iGamingA4HTML5") > -1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //自动转向的控制
        if (url.indexOf("orientation=l") > -1 || url.indexOf("gpiops") > -1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }
    }



    */
/*
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("WebBrowserActivity", "requestCode:" + requestCode + ",resultCode:" + resultCode + "");
        String url = "";
        if(resultCode == -1) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null && scanningResult.getContents() != null && !scanningResult.getContents().isEmpty()) {
                url = scanningResult.getContents();
            }
        } else if(resultCode == 12) {
            url = intent.getStringExtra("url");;
        }

        if ((url.startsWith("http://") || url.startsWith("https://")) && url.indexOf(".apk") == -1 && url.indexOf(".ipa") == -1 && url.indexOf("weixin") == -1) {



            RQCheckUrlAlive rqCheckUrlAlive = new RQCheckUrlAlive(){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }
                @Override
                protected void onPostExecute(String response) {
                    if(!this.GetResponse().equals("404")){
                        preferences.edit().putString("url", MainActivity.getUrl()).commit();

                    }
                }
            };

            Log.e("RQCheckUrlAlive", "getDomainName:" + "http://" + getDomainName(url) + "/d2lsbGlhbWNoZW4=.html");
            rqCheckUrlAlive.Check("http://" + getDomainName(url) + "/d2lsbGlhbWNoZW4=.html");

            mUrl = url;
            webView.loadUrl(url);
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
*//*


    public static String getUrl() {
        return mUrl;
    }

    public static String getDomainName(String url) {

        String domain = "";
        URI uri = null;
        try {
            uri = new URI(url);
            domain = uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return domain;
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void MessageBox(String message) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                    //System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //Toast.makeText(this, message, Toast.LENGTH_SHORT);
        //thread.start();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("System Message");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void openNewWindow(String url) {
        Intent intent = new Intent(this, WebBrowserActivity.class);
        Log.e("openNewWindow", url);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void openBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    */
/*
    IntentIntegrator integrator;
    public void openScan()
    {
        integrator = new IntentIntegrator(mMainActivity);

        //integrator.setCaptureActivity(MyCaptureActivity.class);
        // this for barcode
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }
*//*


    public int lockhome = 0;
    public String lockhomestring = "1";

    @Override
    public void onClick(View view) {
        if (lockhome == 1) {
            home_click = -1;
            lockhome = 0;
            webView.loadUrl(mUrl);
            webView.clearHistory();
            webView.clearHistory();
            return;
        }

        int vid = view.getId();
        //if(vid == R.id.img_register){
        //    openScan();
        //} else
        if (vid == R.id.txt_home) {
            home_click = home_click + 1;
            if (lockhomestring.contains("1234432")) {
                lockhomestring = "1";
                home_click = 0;
                String logdata;
                lockhome = 1;
                WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                android.net.wifi.WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                int wifiip = wifiInfo.getIpAddress();
                String wifiaddress = android.text.format.Formatter.formatIpAddress(wifiip);

                logdata = "Send this log to support<br>";
                logdata = logdata + "app id is : " + public_app_id + "<br>";
                logdata = logdata + "app version is : " + public_app_version + "<br>";
                logdata = logdata + "sdk version is : " + public_sdk_version + "<br>";
                logdata = logdata + "url is : " + mUrl + "<br>";
                logdata = logdata + "wifi ip is : " + wifiaddress + "<br>";
                logdata = logdata + "device manufactor :" + Build.MANUFACTURER + "<br>";
                logdata = logdata + "device model : " + Build.MODEL + "<br>";
                logdata = logdata + "deice product : " + Build.PRODUCT + "<br>";
                logdata = logdata + "showwap :" + public_have_showwap + "<br>";
                logdata = logdata + "<span id='ua'></span><br>";
                logdata = logdata + "<span wan ip='wanip'></span><br>";

                //logdata=logdata+"api response :"+public_api_response+"><br>";
                logdata = logdata + "Android device SDK version code :" + (String.valueOf(Build.VERSION.SDK_INT)) + "<br>";

                //logdata=logdata+"Android firmware version code :"+Build.VERSION_CODES+"<br>";
                //logdata=logdata+"Android Build Version :"+Build.VERSION+"<br>";
                logdata = logdata + "<script>document.getElementById('ua').innerHTML=navigator.userAgent);</script>";
                //logdata=logdata+"<br><br><br><br>";
                //logdata=logdata+"<input type='button' onclick='network_test()' value='進行網路效能測試' style='height:40px;font-size:20px;'/>";
                logdata = logdata + "<br><br>";
                logdata = logdata + "<input type='button' onclick='html5_test()' value='進行html5效能測試' style='height:40px;font-size:20px;'>";
                logdata = logdata + "<br><br>";
                //logdata=logdata+"<input type='button' onclick='send_log()' value='Send Log' style='height:40px;font-size:20px;'>";
                logdata = logdata + "<br><br>";
                logdata = logdata + "<input type='button' onclick='bg_test()' value='BG UAT SITE TEST' style='height:40px;font-size:20px;'>";
                logdata = logdata + "<br><br><br><br>";
                //logdata=logdata+"<input type='button' onclick='killme()' value='結束 並 關閉' style='height:80px;font-size:40px;'>";
                logdata = logdata + "<br><br>";

                //logdata=logdata+"<script>function network_test(){location.href='http://speedofme.com?__browserMode__=1';}</script>";
                logdata = logdata + "<script>function html5_test(){location.href='https://html5test.com?__browserMode__=1';}</script>";
                logdata = logdata + "<script>function bg_test(){location.href='https://bg88168.com/?__browserMode__=1';}</script>";
                logdata = logdata + "<br><br>";
                logdata = logdata + "<script>function getip(json){document.getElementById('wanip').innerHTML=json.ip);}</script>";
                logdata = logdata + "<script type='application/javascript' src='https://api.ipify.org?format=jsonp&callback=getip'></script>";


                try {
                    android.content.pm.PackageInfo pi = getPackageManager().getPackageInfo("com.google.linghangbiao.webview", 0);
                    logdata = logdata + "webview version is " + pi.versionName + "<br>";
                    logdata = logdata + "webview code is " + pi.versionCode + "<br>";
                } catch (Exception ex) {
                    logdata = logdata + "Android's webview is not found.<br>";
                }

                webView.loadData(logdata, "text/html;charset=utf8", "UTF-8");
                webView.clearHistory();

            } else {
                webView.loadUrl(mUrl);
                lockhomestring = lockhomestring + "1";
            }
        } else if (vid == R.id.txt_back) {
            //getcurrentversion();
            lockhomestring = lockhomestring + "2";
            if (home_click == -1) {
                webView.loadUrl(mUrl);
                webView.clearHistory();
            } else {
                webView.goBack();
            }
        } else if (vid == R.id.txt_next) {
            lockhomestring = lockhomestring + "3";
            webView.goForward();
        } else if (vid == R.id.txt_refresh) {
            lockhomestring = lockhomestring + "4";
            webView.reload();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 什么都不用写
            rl_bar.setVisibility(View.GONE);
        } else {
            // 什么都不用写
            rl_bar.setVisibility(View.VISIBLE);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dpd.dismiss();
            super.handleMessage(msg);
        }
    };

    public String getCookie(String siteName, String CookieName) {
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        String[] temp = cookies.split(";");
        for (String ar1 : temp) {
            if (ar1.contains(CookieName)) {
                String[] temp1 = ar1.split("=");
                CookieValue = temp1[1];
                break;
            }
        }
        return CookieValue;
    }


    public String CURRENT_VERSION;

    public void getcurrentversion() {
        webView.evaluateJavascript("return CURRENT_VERSION;", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                CURRENT_VERSION = value;
            }
        });
    }
}
*/

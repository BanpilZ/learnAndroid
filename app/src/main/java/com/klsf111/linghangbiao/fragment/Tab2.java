package com.klsf111.linghangbiao.activitys.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.klsf111.linghangbiao.activitys.base.BaseAgentWebFragment;
import com.klsf111.linghangbiao.activitys.utils.Tools;

import com.klsf111.linghangbiao.activitys.R;

/**
 * @author jpeng
 * @date 16-11-14
 */
public class Tab2 extends BaseAgentWebFragment {
    private ViewGroup mViewGroup;
    //    private SwipeRefreshLayout refreshLayout;
    private WebView webView;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


   /* @Override
    public String getUrl() {
        return "http://m.7m.com.cn/news/";
    }*/

  /*  @Nullable
    @Override
    protected String getUrl() {
        return "https://m.ydniu.com/open/";
    } */

    @Nullable
    @Override
    protected String getUrl() {
        String url = "https://xw.qq.com/m/football/shooter?id=8";
        Log.e("zhanglifan", "第二个页面url = " + url );
        return url;
    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://www.xingzuoyunshi.cn/a/dashi/";
//    }


/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.icaile.com/";
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("正在加载");
        }
//        refreshLayout = ((SwipeRefreshLayout) mViewGroup.findViewById(R.id.linearLayout));
        webView = mAgentWeb.getWebCreator().getWebView();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            if (toolbar != null) {
                int height = toolbar.getHeight();
                if (height == 0) {
//                    fragment_tab_three_relative.setPadding(0, -statusBarHeight * 2, 0, 0);
                } else {
//                    fragment_tab_three_relative.setPadding(0, -height, 0, 0);
                }
            }
        }
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                webView.loadUrl(getUrl());
//                refreshLayout.setRefreshing(false);
//            }
//        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                view.loadUrl(Tools.javascript);
                view.loadUrl("javascript:displayNone('.pos');displayNone('.pos');");
                view.loadUrl("javascript:displayNone('.footer');");
                view.loadUrl("javascript:displayNone('.tabs');");
                view.loadUrl("javascript:displayNone('.wb_topIndexBar.pb15');");
                view.loadUrl("javascript:displayNone('.item-tab');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.site');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.ls-title');");
                view.loadUrl("javascript:displayNone('.btns');");
            }//pic_container

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });//container
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.mViewGroup.findViewById(R.id.linearLayout);
    }
}

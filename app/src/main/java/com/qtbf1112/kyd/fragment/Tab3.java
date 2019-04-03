package com.fkgpby0329.yxb.fragment;

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

import com.fkgpby0329.yxb.base.BaseAgentWebFragment;
import com.fkgpby0329.yxb.utils.Tools;

import com.fkgpby0329.yxb.R;

/**
 * @author Frank
 * @date 16-11-14 15:19
 */
public class Tab3 extends BaseAgentWebFragment {
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


  /*  @Override
    public String getUrl() {
        return "http://m.7m.com.cn/blive/matchs.html";
    }*/

    @Override
    public String getUrl() {
        return "http://7dzx.com/yl/xpxx/";
    }

 /*   @Override
    public String getUrl() {
        return "https://m.500.com/info/kaijiang/gplottery/";
    }*/
/*    @Override
    public String getUrl() {
        return "http://m.cpz666.com/kjgg/detail?gameId=406&clientType=&agentId=100335";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.500.com/datachart/";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "http://m.qulishi.com/yule";
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
            int statusBarHeight = Tools.getStatusBarHeight(getContext());
            Log.e("Frank", "==Tab3.onResume==状态栏高度:" + statusBarHeight);
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            if (toolbar != null) {
                int height = toolbar.getHeight();
                if (height == 0) {
//                    fragment_tab_three_relative.setPadding(0, -statusBarHeight * 2, 0, 0);
                } else {
//                    fragment_tab_three_relative.setPadding(0, -height, 0, 0);
                }
                Log.e("Frank", "==Tab3.onResume==头布局的高度height:" + height);
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
//                view.loadUrl("javascript:displayNone('.footer_fix');");
                view.loadUrl("javascript:displayNone('.footer');displayNone('#footer');displayNone('.footer-down');");
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

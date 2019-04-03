package com.fkgpby0329.yxb.fragment;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.base.BaseAgentWebFragment;
import com.fkgpby0329.yxb.utils.Tools;

/**
 * @author jpeng
 * @date 16-11-14
 */
public class Tab1Pager extends BaseAgentWebFragment {
    private ViewGroup mViewGroup;
//    private SwipeRefreshLayout refreshLayout;
    private WebView webView;
    private ProgressDialog progressDialog;
    private TextView tv;
    private ImageView imageView;
    private WebView web;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        tv = (TextView) view.findViewById(R.id.loading);
//        imageView = (ImageView) view.findViewById(R.id.bg);
//        web = (WebView) view.findViewById(R.id.web);
    }


   /* @Override
    public String getUrl() {
        return "http://m.7m.com.cn/live/matchs.html";
    }*/

/*    @Override
    public String getUrl() {
        return "https://www.szbelle.com/news/";
    } */
/*
    @Override
    public String getUrl() {
        return "https://wap.gametea.com/article/20";
    } */

/*    @Override
    public String getUrl() {
        return "http://m.888pkcp.com/col.jsp?id=113";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.xzw.com/gallery/";
    }*/

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "https://3g.163.com/touch/caipiao/?clickfrom=sports_subchannel&ver=c";
//    }


    @Nullable
    @Override
    protected String getUrl() {
        String tab1url = "http://h.4399.com/play/145331.htm";
        Log.e("zhanglifan", "tab1pager url = " + tab1url );
        return tab1url;
    }
/*    @Override
    public String getUrl() {
        return "https://wap.gametea.com/zhidao/1007";
    }*/
    /*@Override
    public String getUrl() {
        return "https://m.176huyu.com/doudizhugongnue/";
    }*/
/*    @Override
    public String getUrl() {
        return "http://m.tcy365.com/news/cstrategy/";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://wap.gametea.com/zhidao/1083";
    }*/
   /* @Override
    public String getUrl() {
        return "http://m.zhcw.com/zixun/index.jsp?type=cx";
    }*/
 /*   @Override
    public String getUrl() {
        return "http://m.qipaiqun.com/search?type=6&key=%E6%96%97%E5%9C%B0%E4%B8%BB";
    }*/
   /* @Override
    public String getUrl() {
        return "http://m.win8f.com/plus/search.php?kwtype=0&q=%E6%96%97%E5%9C%B0%E4%B8%BB";
    }*/
/*    @Override
    public String getUrl() {
        return "http://7dzx.com/yl/qw/";
    }*/

 /*   @Override
    public String getUrl() {
        return "http://www.yxshiwan8.com/";
    }*/


  /*  @Override
    public String getUrl() {
        return "https://m.caibb.com/StaticHtml/HuoDong/consultingonwebsite/sszx/alllist.html";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.cz89.com/";
    }*/

/*    @Nullable
    @Override
    protected String getUrl() {
        return "http://m.ixiumei.com/ent/";
    }*/

  /*  @Nullable
    @Override
    protected String getUrl() {
        return "https://m.caibow.com/kj/";
    }*/
    /*    @Nullable
    @Override
    protected String getUrl() {
        return "https://m.500.com/info/kaijiang/#h5";
    }*/
/*  @Nullable
    @Override
    protected String getUrl() {
        return "https://m.7m.com.cn/blive/matchs.html";
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
//                view.loadUrl("javascript:displayNone('.ui-header');displayNone('.ui-footer');displayNone('#footer');displayNone('.qws_list_box');");
//                view.loadUrl("javascript:displayNone('.footNav');displayNone('#footer');displayNone('.footer-down');displayNone('.head');displayNone('.breadNav');displayNone('.subnav');displayNone('.title');displayNone('.gameRank');displayNone('.relateNews');");
//                view.loadUrl("javascript:displayNone('.footNav');displayNone('#footer');displayNone('.footer-down');displayNone('.head');displayNone('.breadNav');displayNone('.subnav');displayNone('.title');displayNone('.gameRank');displayNone('.relateNews');displayNone('.foot');displayNone('.footwp');");
//                view.loadUrl("javascript:displayNone('.header-wrap');displayNone('.breadcrumbs');displayNone('.layout-wrap');");
//                view.loadUrl("javascript:displayNone('.header');displayNone('.footer');displayNone('#footer');");
//                view.loadUrl("javascript:displayNone('.header');displayNone('.tabs');displayNone('.ad');displayNone('.bottomAD');displayNone('.search');");
//                view.loadUrl("javascript:displayNone('.header');displayNone('.footer');displayNone('.col-xs-12');displayNone('.f-panel');");
//                view.loadUrl("javascript:displayNone('.header');displayNone('.footer');displayNone('.f-panel');displayNone('.u-search');");
//                view.loadUrl("javascript:displayNone('.headerbox');displayNone('.title');displayNone('.footnonebox');displayNone('.footerbox');displayNone('.mip-accordion-header');");
//                view.loadUrl("javascript:displayNone('.sub-header');displayNone('.footer');displayNone('.news-tab');displayNone('.recommend');displayNone('.hot-news');displayNone('.g-featured');displayNone('.g-information');displayNone('.app-download');");
//                view.loadUrl("javascript:displayNone('.footer_box');displayNone('#footer');displayNone('.footer-down');");
//                view.loadUrl("javascript:displayNone('.header');displayNone('.footer');displayNone('.copyright');");
            }//displayNone('li');footer-down

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                new Handler().postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        //显示dialog
//
//                        tv.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
////                        web.setVisibility(View.VISIBLE);
//                    }
//                }, 5000);   //3秒
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        });
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

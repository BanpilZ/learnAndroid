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

import com.klsf111.linghangbiao.activitys.R;
import com.klsf111.linghangbiao.activitys.utils.Tools;
import com.klsf111.linghangbiao.activitys.base.BaseAgentWebFragment;

/**
 * @author jpeng
 * @date 16-11-14
 */
public class Tab1Pager extends BaseAgentWebFragment {
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


    /*@Override
    public String getUrl() {
        return "http://m.7m.com.cn/live/matchs.html";
    }*/

  /*  @Nullable
    @Override
    protected String getUrl() {
        return "https://m.cz89.com/";
    }*/
/*    @Nullable
    @Override
    protected String getUrl() {
        return "http://m.zhcw.com/zixun/";
    }   */


//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "https://3g.d1xz.net/";
//    }

    // http://www.gjprj.cn/news.asp
//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "https://www.msn.com/zh-tw/sports/nba"; //6686NBA
//    }  //https://www.ixingpan.com/info@Nullable

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "https://m.7m.com.cn/news/"; //6686足球
//    }  //https://www.ixingpan.com/info

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://m.v.qq.com/x/vplus/22390842e8e07e19a3943dada1d257fb"; //体育资讯
//    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "https://m.ooopic.com/"; //特一图库
//    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://m.699pic.com/"; //168图库
//    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://m.life.httpcn.com/"; //六合先知
//    }
//
//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://www.zy5000.cn/m/"; //六合资料
//    }
//
    @Nullable
    @Override
    protected String getUrl() {
        String url = "https://xw.qq.com/m/football";
        Log.e("zhanglifan", "第一个页面url = "  +  url);
        return url; //六合宝典
    }

//    @Nullable
//    @Override
//    protected String getUrl() {
//        return "http://baoshuanglong.cn/"; //六合资料
//    }

/*    @Nullable
    @Override
    protected String getUrl() {
        return "https://3g.163.com/touch/caipiao/subchannel/ssq/?clickfrom=sports_subchannel&ver=c";
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
                view.loadUrl("javascript:displayNone('#footer');");
                view.loadUrl("javascript:displayNone('.nav_con');");
                view.loadUrl("javascript:displayNone('.nav2');");
                view.loadUrl("javascript:displayNone('#header');");
                view.loadUrl("javascript:displayNone('.header');");
                view.loadUrl("javascript:displayNone('.indexItem');");
                view.loadUrl("javascript:displayNone('.popup-activity');");
                view.loadUrl("javascript:displayNone('.tab-scroll-wrap');");
                view.loadUrl("javascript:displayNone('.tabs');");
                view.loadUrl("javascript:displayNone('.wb_topIndexBar.pb15');");
                view.loadUrl("javascript:displayNone('.item-tab');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.site');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.ls-title');");
                view.loadUrl("javascript:displayNone('.btns');");
            }//displayNone('li');footer-down

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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

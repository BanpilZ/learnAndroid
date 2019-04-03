package com.klsf111.linghangbiao.activitys.fragment;

import android.app.ProgressDialog;
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
import com.klsf111.linghangbiao.activitys.base.BaseAgentWebFragment;


/**
 * @author Frank
 * @date 16-11-14 15:19
 */
public class Tab4 extends BaseAgentWebFragment {
    private ViewGroup mViewGroup;
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


//    @Override
//    public String getUrl() {
//        return "https://3g.d1xz.net/sx/"; 星座
//    }

    @Nullable
    @Override
    protected String getUrl() {
        String url = "https://xw.qq.com/m/zhongchao";
        Log.e("zhanglifan", "第四个页面url =  " + url );
        return url; //论坛
    }


    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("正在加载");
        }
        WebView webView = mAgentWeb.getWebCreator().getWebView();
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
                Log.e("Frank", "==Tab3.onResume==头布局的高度height:" + height);
            }
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                view.loadUrl(com.klsf111.linghangbiao.activitys.utils.Tools.javascript);
                view.loadUrl("javascript:displayNone('.pos');displayNone('.pos');");
                view.loadUrl("javascript:displayNone('.footer');");
                view.loadUrl("javascript:displayNone('.tabs');");
                view.loadUrl("javascript:displayNone('.wb_topIndexBar.pb15');");
                view.loadUrl("javascript:displayNone('.item-tab');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.site');");
                view.loadUrl("javascript:displayNone('.home');displayNone('.ls-title');");
                view.loadUrl("javascript:displayNone('.btns');");
            }
        });
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.mViewGroup.findViewById(R.id.linearLayout);
    }
}

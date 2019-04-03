package com.klsf111.linghangbiao.activitys.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.klsf111.linghangbiao.activitys.R;
import com.klsf111.linghangbiao.activitys.adapter.Adapter;
import com.klsf111.linghangbiao.activitys.fragment.Tab1Pager;
import com.klsf111.linghangbiao.activitys.fragment.Tab2;
import com.klsf111.linghangbiao.activitys.fragment.Tab3;
import com.klsf111.linghangbiao.activitys.fragment.Tab4;
import com.klsf111.linghangbiao.activitys.fragment.Tab5Pager;
import com.klsf111.linghangbiao.activitys.utils.Options;
import com.klsf111.linghangbiao.activitys.utils.Tools;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class NativeActivity extends com.klsf111.linghangbiao.activitys.activitys.BaseAppCompatActivity implements OnTabSelectListener, BadgeDismissListener {


    public static String diyige = "首页";
    public static  String dierge = "排名";
    public static  String disange = "赛程";
    public static  String disige = "资讯";
    public static  String diwuge = "杂谈";

 /*   @Titles
    private static final String[] mTitles = {
            "首页"
            , "足球"
            , "篮球"
//            , "助手"
            , "我"
    };*/
    @Titles
    private static final String[] mTitles = {
            diyige
            , dierge
            , disange
            , disige
//            , diwuge
    };

    @SeleIcons
    private static final int[] mSeleIcons = {
            R.mipmap.tab9_selected
            , R.mipmap.pm11s
            , R.mipmap.sc11s
            , R.mipmap.zx11s
//            , R.mipmap.tab10_selected
    };

    @NorIcons
    private static final int[] mNormalIcons = {
            R.mipmap.tab9_normal
            , R.mipmap.pm11n
            , R.mipmap.sc11n
            , R.mipmap.zx11n
//            , R.mipmap.tab10_normal
    };
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Fragment> list = new ArrayList<>();

    private ViewPager mPager;

    private JPTabBar mTabbar;

    //    private Tab1 mTab1;
    private Tab1Pager mTab1;

    private Tab2 mTab2;

    private Tab3 mTab3;
    private Tab4 mTab4;
//    private Tab4Pager mTab4;
    private Tab5Pager mTab5;

    private Fragment mfg;
    private boolean isQuit = false;
    private TextView tv, tv2;
    private Adapter adapter;
    /**
     * 是否是从tab5页面返回过来的
     */
    private int isTab5;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Frank", "==NativeActivity.onCreate==");
        setContentView(R.layout.activity_native);
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Tools.setTranslucentStatus(NativeActivity.this, true);
        }
        tintManager = new SystemBarTintManager(NativeActivity.this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#1aaa3b"));
        tintManager.setNavigationBarTintResource(Color.parseColor("#1aaa3b"));
        ButterKnife.bind(this);
        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mTabbar.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mPager = (ViewPager) findViewById(R.id.view_pager);
        tv = (TextView) getToolbar().findViewById(R.id.toolbar_title);
        tv2 = (TextView) getToolbar().findViewById(R.id.toolbar_subtitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list != null) {
            list.clear();
        }
        if (mTab1 == null) {
            mTab1 = new Tab1Pager();
        }
        if (mTab2 == null) {
            mTab2 = new Tab2();
        }
        if (mTab3 == null) {
            mTab3 = new Tab3();
        }
        if (mTab4 == null) {
            mTab4 = new Tab4();
        }
        if (mTab5 == null) {
            mTab5 = new Tab5Pager();
        }
        mTabbar.setTabListener(this);
        list.add(mTab1);
        list.add(mTab2);
        list.add(mTab3);
        list.add(mTab4);
//        list.add(mTab5);
        adapter = new Adapter(getSupportFragmentManager(), list);
        mPager.setAdapter(adapter);
        mTabbar.setContainer(mPager);
        mTabbar.setDismissListener(this);
        mTabbar.setTabListener(this);
        if (isTab5 == Options.REQUESTCODE_LOOK_TO
                || isTab5 == Options.REQUESTCODE_TASK_TO
                || isTab5 == Options.REQUESTCODE_HISTORT_TO
                || isTab5 == Options.REQUESTCODE_MINE_MESSAGES
                || isTab5 == Options.REQUESTCODE_FANS
                || isTab5 == Options.REQUESTCODE_CORE
                || isTab5 == Options.REQUESTCODE_REMIND
                || isTab5 == Options.REQUESTCODE_TAB4) {
            mfg = mTab5;
            adapter.notifyDataSetChanged();
            mPager.setCurrentItem(4, false);
        } else {
            mfg = mTab1;
        }
        Log.e("Frank", "==NativeActivity.onResume==当前选中的位置=" + mTabbar.getSelectPosition() + "==isTab5=" + isTab5);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Frank", "==NativeActivity.onActivityResult==resultCode=" + resultCode + "==requestCode=" + requestCode);
        isTab5 = resultCode;
        if (resultCode == Options.REQUESTCODE_HOT_NEWS) {
            mfg = mTab1;
            isQuit = false;
            Log.e("Frank", "==NativeActivity.onActivityResult==");
        } else if (resultCode == Options.REQUESTCODE_LOOK_TO
                || resultCode == Options.REQUESTCODE_TASK_TO
                || resultCode == Options.REQUESTCODE_HISTORT_TO
                || resultCode == Options.REQUESTCODE_MINE_MESSAGES
                || resultCode == Options.REQUESTCODE_FANS
                || resultCode == Options.REQUESTCODE_CORE
                || resultCode == Options.REQUESTCODE_REMIND
                || resultCode == Options.REQUESTCODE_TAB4) {
            mfg = mTab5;
        }
    }

    @Override
    public void onDismiss(int position) {

    }

    @Override
    public void onTabSelect(int index) {
        mfg = list.get(index);
        switch (index) {
            case 0:
                tv.setText(diyige);
                tv2.setText("");
                break;
            case 1:
//                tv.setText("同步开奖");
                tv.setText(dierge);
//                tv.setText("篮球");
                tv2.setText("");
                break;
            case 2:
//                tv.setText("更多资讯");
                tv.setText(disange);
//                tv.setText("足球");
                tv2.setText("");
                break;
            case 3:
//                tv.setText("智能帮手");
//                tv2.setText("生肖");
                tv.setText(disige);
                break;
            case 4:
                tv.setText(diwuge);
                tv2.setText("");
                break;
            default:
                tv.setText(diyige);
                tv2.setText("");
                break;
        }
    }

    public JPTabBar getTabbar() {
        return mTabbar;
    }


    /**
     * 设置不显示返回按钮
     *
     * @return
     */
    @Override
    protected boolean isShowBacking() {
        return false;
    }

    /**
     * 设置布局
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_native;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);
    }

    public void setToolbartitle(String title) {
        setToolBarTitle(title);
        getSubTitle().setText("");
    }

    private long mExitTime;
    private static final long EXIT_TIME = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) >= EXIT_TIME) {
                Toast.makeText(NativeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (list != null) {
            list.clear();
            list = null;
            Log.e("Frank", "==NativeActivity.onDestroy==销毁");
        }
    }
}

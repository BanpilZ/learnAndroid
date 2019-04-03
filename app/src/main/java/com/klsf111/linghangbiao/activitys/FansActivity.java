package com.klsf111.linghangbiao.activitys.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.klsf111.linghangbiao.activitys.utils.Options;
import com.klsf111.linghangbiao.activitys.utils.Tools;

import com.klsf111.linghangbiao.activitys.R;


/**
 * 我的粉丝界面
 * Create by Frank 0n 2017/10/26 19:10
 */
public class FansActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(FansActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_fans);
    }

    public void fansBack(View view) {
        setResult(Options.REQUESTCODE_FANS);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Options.REQUESTCODE_FANS);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

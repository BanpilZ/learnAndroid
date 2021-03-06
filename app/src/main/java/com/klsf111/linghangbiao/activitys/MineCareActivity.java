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
 * 我的关注界面
 * Create by Frank 0n 2017/10/26 19:14
 */
public class MineCareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(MineCareActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_mine_care);
    }

    public void mineCareBack(View view) {
        setResult(Options.REQUESTCODE_CORE);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Options.REQUESTCODE_CORE);
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

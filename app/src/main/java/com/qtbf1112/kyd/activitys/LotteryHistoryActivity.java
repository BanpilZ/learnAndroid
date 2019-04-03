package com.fkgpby0329.yxb.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.fkgpby0329.yxb.utils.Options;
import com.fkgpby0329.yxb.utils.Tools;

import com.fkgpby0329.yxb.R;


/**
 * 彩票记录界面
 * Create by Frank 0n 2017/10/26 19:02
 */
public class LotteryHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(LotteryHistoryActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_lottery_history);
    }


    public void lotteryHistoryBack(View view) {
        setResult(Options.REQUESTCODE_HISTORT_TO);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Options.REQUESTCODE_HISTORT_TO);
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

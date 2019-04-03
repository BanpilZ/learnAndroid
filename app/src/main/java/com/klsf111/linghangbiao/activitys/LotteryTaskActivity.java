package com.klsf111.linghangbiao.activitys.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.klsf111.linghangbiao.activitys.R;
import com.klsf111.linghangbiao.activitys.utils.Options;
import com.klsf111.linghangbiao.activitys.utils.Tools;


/**
 * 彩票任务界面
 * Create by Frank 0n 2017/10/26 19:00
 *
 * @author Frank
 */
public class LotteryTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(LotteryTaskActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_lottery_task);
    }

    public void lotteryTaskBack(View view) {
        setResult(Options.REQUESTCODE_TASK_TO);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(Options.REQUESTCODE_TASK_TO);
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

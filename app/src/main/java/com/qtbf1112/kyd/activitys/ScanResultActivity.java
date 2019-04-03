package com.fkgpby0329.yxb.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.fkgpby0329.yxb.R;


public class ScanResultActivity extends Activity implements View.OnClickListener {
    private Button btn_go;
    private EditText txt_url;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan_result);

        Intent intent = this.getIntent();
        mUrl = intent.getStringExtra("url");
        if(mUrl.indexOf("tel:") > -1)
            mUrl = mUrl.replaceFirst("tel:", "");

        btn_go = (Button) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);

        txt_url = (EditText) findViewById(R.id.txt_url);
        txt_url.setText(mUrl);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        if(vid == R.id.btn_go){
            Intent intent = new Intent(this, WebBrowserActivity.class);
            Log.e("Go to", txt_url.getText().toString());
            intent.putExtra("url", txt_url.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}

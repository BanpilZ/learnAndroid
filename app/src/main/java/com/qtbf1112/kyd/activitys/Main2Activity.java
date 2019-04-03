package com.fkgpby0329.yxb.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.grandfortunetech.jlib.View.Dialog.JDialog;

import com.fkgpby0329.yxb.R;

public class Main2Activity extends Activity implements View.OnClickListener{
    private String mRegisterUrl = "http://tw.yahoo.com";

    private Activity mainActivity;
    private ImageView imgTitle;
    private ImageView imgEnterUrl;
    private ImageView imgRegister;
    private RelativeLayout rlEnterUrl;
    private Button btnGO;
    private EditText txtUrl;
    private static boolean blnFirstTime = true;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mainActivity = this;
        preferences = getSharedPreferences("preFile",MODE_PRIVATE);

        imgTitle = (ImageView) findViewById(R.id.img_title);
        imgTitle.setOnClickListener(this);

        imgEnterUrl = (ImageView) findViewById(R.id.img_enter_url);
        imgEnterUrl.setOnClickListener(this);

        imgRegister = (ImageView) findViewById(R.id.img_register);
        imgRegister.setOnClickListener(this);

        rlEnterUrl = (RelativeLayout) findViewById(R.id.rl_enter_url);

        btnGO = (Button) findViewById(R.id.btn_go);
        btnGO.setOnClickListener(this);

        txtUrl = (EditText) findViewById(R.id.txt_url);


        if(blnFirstTime) {
            String url = preferences.getString("url", "");
            Log.e("SharedPreferences", "url:" + "");
            if (!url.equals("")) {
                Intent intent2 = new Intent(this, WebBrowserActivity.class);
                intent2.putExtra("url", url);
                startActivity(intent2);
                finish();
            }
        }
        blnFirstTime = false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null&& scanningResult.getContents() != null && !scanningResult.getContents().isEmpty()){
            //txtContent.setText(scanContent);
            /*
            Intent intent2 = new Intent(this, ScanResultActivity.class);
            intent2.putExtra("url", scanningResult.getContents());
            startActivity(intent2);
            finish();
            */
            String url = scanningResult.getContents();
            if((url.startsWith("http://") || url.startsWith("https://")) && url.indexOf(".apk") == -1 && url.indexOf(".ipa") == -1 && url.indexOf("weixin") == -1)
            {
                preferences.edit().putString("url", url).commit();

                Intent intent2 = new Intent(this, WebBrowserActivity.class);
                intent2.putExtra("url", scanningResult.getContents());
                startActivity(intent2);
                finish();
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        }
    }

    public void scan()
    {
        IntentIntegrator integrator = new IntentIntegrator(mainActivity);

        integrator.setCaptureActivity(MyCaptureActivity.class);
        // this for barcode
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        if(vid == R.id.img_enter_url) {
            rlEnterUrl.setVisibility(View.VISIBLE);
        } else if(vid == R.id.btn_go) {
            String url = txtUrl.getText().toString();

            if((url.startsWith("http://") || url.startsWith("https://")) && url.indexOf(".apk") == -1 && url.indexOf(".ipa") == -1 && url.indexOf("weixin") == -1)
            {
                preferences.edit().putString("url", url).commit();

                Intent intent2 = new Intent(this, WebBrowserActivity.class);
                intent2.putExtra("url", url);
                startActivity(intent2);
                finish();
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }


            //Intent intent = new Intent(this, WebBrowserActivity.class);
            //intent.putExtra("url", txtUrl.getText().toString());
            //startActivity(intent);
            finish();
        } else if(vid == R.id.img_title){
            scan();
        } else if(vid == R.id.img_register) {
            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("請先掃描公司二維碼")
                    .setPositiveButton("掃碼", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            scan();
                        }
                    })
                    .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            */
            JDialog jDialog = new JDialog(this);
            jDialog.setTitle("提示")
                    .setMessage("请先扫描公司二维码")
                    .setYes("扫码")
                    .setNo("稍后")
                    .setOnButtonListener(new JDialog.onButtonListener() {
                        @Override
                        public void onYesClick() {
                            Log.e("YesNoDialog", "YES");
                            scan();
                        }

                        @Override
                        public void onNoClick() {
                            Log.e("YesNoDialog", "NO");
                        }
                    })
                    .create().show();
        }

    }
}

package com.fkgpby0329.yxb.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.fkgpby0329.yxb.R;

public class MyCaptureActivity extends CaptureActivity {
    private MyCaptureActivity myCaptureActivity;

    private ImageButton btnBack;
    private ImageButton btnFlash;
    private TextView txtEnterUrl;
    private TextView txtSelectPhoto;
    private boolean blnFlash = false;


    private DecoratedBarcodeView decoratedBarcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCaptureActivity = this;

        decoratedBarcodeView = (DecoratedBarcodeView)findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);

        btnFlash = (ImageButton) findViewById(R.id.img_flash);
        btnFlash.setOnClickListener(view -> toogleFlash());
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            btnFlash.setVisibility(View.GONE);
        }

        btnBack = (ImageButton) findViewById(R.id.img_back);
        btnBack.setOnClickListener(view -> {
            decoratedBarcodeView.setTorchOff();

            Intent intent2 = new Intent(myCaptureActivity, WebBrowserActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
            finish();
        });

        txtEnterUrl = (TextView) findViewById(R.id.txtEnterUrl);
        txtEnterUrl.setOnClickListener(view -> {
            AlertDialog.Builder editDialog = new AlertDialog.Builder(myCaptureActivity);
            editDialog.setTitle("输入网站域名");

            final EditText editText = new EditText(myCaptureActivity);
            editText.setText("http://");
            editText.setHint("网站域名");
            editDialog.setView(editText);

            // do something when the button is clicked
            editDialog.setPositiveButton("好的", (arg0, arg1) -> requestUrl(editText.getText().toString()));
            // do something when the button is clicked
            editDialog.setNegativeButton("取消", (arg0, arg1) -> {
                //...
            });
            editDialog.show();
        });

        txtSelectPhoto = (TextView) findViewById(R.id.txtSelectPhoto);
        txtSelectPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }
        );
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void requestUrl(String url)
    {
        Log.i("MyCaptureActivity", "url:" + url);
        if ((url.startsWith("http://") || url.startsWith("https://")) && url.indexOf(".apk") == -1 && url.indexOf(".ipa") == -1 && url.indexOf("weixin") == -1) {
            Intent intent2 = new Intent();
            intent2.putExtra("url", url);
            setResult(12, intent2);
            finish();
        } else {
            Toast.makeText(this, "无法识别网站......", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK){
                Uri selectedImage = intent.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                    String url = decode(yourSelectedImage);

                    if(url != null && !url.equals(""))
                    {
                        requestUrl(url);
                    } else {
                        Toast.makeText(this, "无法识别图片,请重新选择......", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String decode(Bitmap bMap)
    {
        String contents = null;
        int w = bMap.getWidth();
        int h = bMap.getHeight();
        int[] intArray = new int[w * h];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        // use this otherwise ChecksumException
        Reader reader = new MultiFormatReader();
        try
        {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }
        catch (ChecksumException e)
        {
            e.printStackTrace();
        }
        catch (FormatException e)
        {
            e.printStackTrace();
        }
        return contents;
    }

    private void toogleFlash()
    {
        if(!blnFlash){
            Log.i("MyCaptureActivity", "turn on");
            decoratedBarcodeView.setTorchOn();
        } else {
            Log.i("MyCaptureActivity", "turn off");
            decoratedBarcodeView.setTorchOff();
        }
        blnFlash = !blnFlash;
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_my_capture);
        return (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}

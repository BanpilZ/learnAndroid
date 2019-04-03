package com.fkgpby0329.yxb.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by raykon.lin on 2017/5/31.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {
    Context ct;
    String newVersion;
    Handler handler;
    public DownloadTask(Context ct, Handler handler){
        this.ct = ct;
        this.handler = handler;

    }
    @Override
    protected String doInBackground(String[] params) {
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = "boco.apk";
        destination += fileName;
        int count;
        try {
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);
            // Output stream
            OutputStream output = new FileOutputStream(destination);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return destination;
    }

    @Override
    protected void onPostExecute(String result) {
        handler.sendEmptyMessage(0);
        ct.getSharedPreferences("preFile",ct.MODE_PRIVATE).edit().putString("appVersion",newVersion).commit();
        final Uri uri = Uri.parse("file://" + result);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        install.setDataAndType(uri,"application/vnd.linghangbiao.package-archive");
        ct.startActivity(install);
        super.onPostExecute(result);
    }
}

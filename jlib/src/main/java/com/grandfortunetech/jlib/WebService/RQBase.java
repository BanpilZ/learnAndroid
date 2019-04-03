package com.grandfortunetech.jlib.WebService;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff.hsu on 2016/9/9.
 */
public class RQBase extends AsyncTask<Integer, Integer, String> {
    protected JSONObject mJson;
    protected Object mResult;
    public String mUrl;
    protected String mResponse;
    protected Boolean mIsSuccess;

    public RQBase()
    {
        mIsSuccess = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mJson = new JSONObject();
    }

    @Override
    protected String doInBackground(Integer... p) {
        try {
            return HttpUtils.httpRequestServer(mUrl, mJson);
        } catch (Exception e) {
            Log.i("RQBase", "Exception:" + e.getMessage());
            return "";
        }
    }

    public static void LogInfo(String tag, String msg) {
        if(msg.length() > 4000) {
            Log.i(tag, msg.substring(0, 4000));
            LogInfo(tag, msg.substring(4000));
        } else
            Log.i(tag, msg);
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        mResponse = response;
        LogInfo("RQBase", "[Url]:" + this.GetUrl());
        LogInfo("RQBase", "[StatusCode]:" + mResponse);

    }

    public String GetUrl()
    {
        return mUrl;
    }

    public String GetJson()
    {
        return mJson.toString();
    }

    public String GetResponse()
    {

        return mResponse;
    }

    public Boolean IsSuccess()
    {
        return mIsSuccess;
    }
}

package com.grandfortunetech.jlib.WebService;

import android.util.Log;

import com.grandfortunetech.jlib.Utils.StrUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeff.hsu on 2016/10/23.
 */
public class RQCheckUrlAlive  extends RQBase {

    public RQCheckUrlAlive()
    {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
    }

    public void Check(String url)
    {
        mUrl = url;
        this.execute();
    }
}

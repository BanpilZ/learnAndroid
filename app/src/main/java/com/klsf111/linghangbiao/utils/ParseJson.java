package com.klsf111.linghangbiao.activitys.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.klsf111.linghangbiao.activitys.bean.HotNewsBean;

/**
 * @author Frank
 * @date 2017/10/24 17:46
 */

public class ParseJson {
    public static String getJsonData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String text = object.getString("text");
            return text;
        } catch (Exception e) {
            Writer w = new StringWriter();
            PrintWriter err = new PrintWriter(w);
            e.printStackTrace(err);
            Log.e("Frank", "==ParseJson.getJsonData==解析异常:" + w.toString());
        }
        return "不好意思，我也不知道怎么回答";
    }

    public static String getJsonUrl(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String text = object.getString("url");
            return text;
        } catch (Exception e) {
            Writer w = new StringWriter();
            PrintWriter err = new PrintWriter(w);
            e.printStackTrace(err);
            Log.e("Frank", "==ParseJson.getJsonData==解析异常:" + w.toString());
        }
        return "";
    }

    public static List<HotNewsBean> getHotNews(String strJson) {
        List<HotNewsBean> hotNewsBeanList = new ArrayList<>();
        Gson gson = new Gson();
        HotNewsBean hotNewsBean = gson.fromJson(strJson, HotNewsBean.class);
        hotNewsBeanList.add(hotNewsBean);
        return hotNewsBeanList;
    }
}

package com.grandfortunetech.jlib.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by jeff.hsu on 2016/8/30.
 */
public class ResUtil {
    private Context mContext;
    private static ResUtil mResUtil;

    public ResUtil(Context _context){
        mContext = _context;
    }

    public float getDP(){
        return mContext.getResources().getDisplayMetrics().density;
    }

    public int getResWithString(String _name, String _defType){
        return mContext.getResources().getIdentifier(_name, _defType, mContext.getPackageName());
    }

    public int getString(String _name){
        return getResWithString(_name, "string");
    }

    public String getString(int _id){
        return mContext.getString(_id);
    }

    public String[] getStringArray(int _id){
        return mContext.getResources().getStringArray(_id);
    }

    public Drawable getDrawable(int _id){
        return ContextCompat.getDrawable(mContext, _id);
    }

    public int getDrawable(String _name){
        return getResWithString(_name, "drawable");
    }

    public int getColor(int _id){
        return mContext.getResources().getColor(_id);
        //return ContextCompat.getColor(mContext, _id);
    }

    public ColorStateList getColorStateList(int _id){
        return mContext.getResources().getColorStateList(_id);
        //return ContextCompat.getColorStateList(mContext, _id);
    }

    public int getDP(int _i){
        return Math.round(_i * getDP());
    }

    public Animation getAnimation(int _id){
        return AnimationUtils.loadAnimation(mContext, _id);
    }

    public static ResUtil getInstances(Context _context){
        if(mResUtil == null)
            mResUtil = new ResUtil(_context);
        return mResUtil;
    }

    public String getRaw(int _id){
        InputStream is = mContext.getResources().openRawResource(_id);
        Writer writer = new StringWriter();
        String str = "";
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
            str = writer.toString();
        } catch (Exception e){
        } finally {
        }
        return str;
    }

    public JSONObject getRawToJSONObject(int _id){
        String strJson = getRaw(_id);
        JSONObject json = new JSONObject();
        try
        {
            json = new JSONObject(strJson);
        }
        catch(ClassCastException e)
        {
        }
        catch(JSONException e)
        {
            // do nothing
        }
        return json;
    }
}
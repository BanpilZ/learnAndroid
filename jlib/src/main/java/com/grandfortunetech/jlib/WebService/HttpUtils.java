package com.grandfortunetech.jlib.WebService;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by jeff.hsu on 2016/9/9.
 */
public class HttpUtils {
    public static String httpRequestServer(String url, JSONObject jsonObject) {

        String result = "";
        DefaultHttpClient client = new DefaultHttpClient();
//		CookieStore myCookieStore = new BasicCookieStore();
//		BasicClientCookie newCookie = new BasicClientCookie("CGISESSID", "ab95g5hjvcpvbe2kbqk8jh2qo1");
//		newCookie.setVersion(1);
//		newCookie.setDomain("ios.phl5b.org");
//		newCookie.setPath("/");
//		myCookieStore.addCookie(newCookie);
        HttpPost post = new HttpPost(url);
        try {

            //post.setHeader("Accept", "application/json");
            //post.setHeader("Content-type", "application/json");

            post.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse httpResponse = client.execute(post);
            Log.i("url", url);
            Log.i("getStatusCode", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity());
            }
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("err", e.getMessage());
        } catch (OutOfMemoryError e) {
            System.gc();
            e.printStackTrace();
            Log.i("err", e.getMessage());
        } finally {
            client.getConnectionManager().closeIdleConnections(0, TimeUnit.SECONDS);
        }
        return result;
    }

}
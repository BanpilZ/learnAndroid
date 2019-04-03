package com.klsf111.linghangbiao.activitys.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.klsf111.linghangbiao.activitys.back.InterfaceMessage;
import com.klsf111.linghangbiao.activitys.back.TabOneHotNewDataInterface;
import com.klsf111.linghangbiao.activitys.bean.ChatMessage;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;


/**
 * @author Frank
 * @date 2017/10/27 18:18
 */
public class TaskUtil {
    private String url;
    private int type = 1;
    private static ProgressDialog progressDialog;

    public TaskUtil(String url) {
        this.url = url;
    }

    public TaskUtil(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public static void setProgressDialog(ProgressDialog progressDialog) {
        TaskUtil.progressDialog = progressDialog;
    }

    /**
     * 获取解析的数据
     *
     * @param interfaceMessage 数据回调接口
     */
    public void getMessages(final InterfaceMessage interfaceMessage) {
        RequestParams params = new RequestParams();
        params.setUri(url);
        params.setReadTimeout(5000);
        params.setConnectTimeout(5000);
        params.setMethod(HttpMethod.GET);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    String jsonData = com.klsf111.linghangbiao.activitys.utils.ParseJson.getJsonData(result);
                    String jsonUrl = com.klsf111.linghangbiao.activitys.utils.ParseJson.getJsonUrl(result);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setDate(new Date());
                    chatMessage.setMsg(jsonData);
                    chatMessage.setUrl(jsonUrl);
                    if (type == 1) {
                        chatMessage.setType(ChatMessage.Type.INCOME);
                    } else {
                        chatMessage.setType(ChatMessage.Type.INCOM2);
                    }
                    chatMessage.setName("launcher");
                    interfaceMessage.getMessages(chatMessage);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Writer w = new StringWriter();
                PrintWriter err = new PrintWriter(w);
                ex.printStackTrace(err);
                Log.e("Frank", "==TaskUtil.onError==异常:" + w.toString());
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setDate(new Date());
                chatMessage.setMsg("哎呀，我不知道哟");
                chatMessage.setUrl("");
                chatMessage.setType(ChatMessage.Type.INCOME);
                chatMessage.setName("launcher");
                interfaceMessage.getMessages(chatMessage);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
    public static void downJsonData(final Context context,String url, final int flag, final TabOneHotNewDataInterface newDataInterface){
        RequestParams params = new RequestParams();
        params.setUri(url);
        params.setReadTimeout(5000);
        params.setConnectTimeout(5000);
        params.setMethod(HttpMethod.GET);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    if(progressDialog!=null){
                        progressDialog.setMessage("正在加载...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                    }
                    newDataInterface.getHotNewData(result);
                }
                Log.e("Frank","==TaskUtil.onSuccess==加载成功" );
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(context,"请刷新一下...",Toast.LENGTH_SHORT).show();
                Log.e("Frank","==TaskUtil.onError==加载错误" );
                if(progressDialog!=null&&progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("Frank","==TaskUtil.onCancelled==加载取消" );
                if(progressDialog!=null&&progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFinished() {
                if(progressDialog!=null&&progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if(flag!=0){//代表不是第一次进入界面
                    Toast.makeText(context,"刷新成功",Toast.LENGTH_SHORT).show();
                }
                Log.e("Frank","==TaskUtil.onFinished==加载结束" );
            }

            @Override
            public boolean onCache(String result) {
                Log.e("Frank","==TaskUtil.onCache==加载缓存="+result );
                if(progressDialog!=null){
                    progressDialog.setMessage("正在加载...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                }
                return false;
            }
        });
    }

}

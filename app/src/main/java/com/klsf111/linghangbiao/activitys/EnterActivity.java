package com.klsf111.linghangbiao.activitys.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.klsf111.linghangbiao.activitys.back.DialogInterface;
import com.klsf111.linghangbiao.activitys.utils.Options;
import com.klsf111.linghangbiao.activitys.utils.SharedPreferenceUtils;
import com.klsf111.linghangbiao.activitys.utils.Tools;
import com.klsf111.linghangbiao.activitys.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Create by Frank 0n 2017/10/25 14:42
 *
 * @author Frank
 */
public class EnterActivity extends AppCompatActivity implements IUiListener {
    /**
     * 用户名输入框
     */
    private EditText enter_et_name;
    /**
     * 密码输入框
     */
    private EditText enter_et_psw;
    private Tools tools;
    /**
     * 注册按钮
     */
    private TextView enter_register;
    private ImageView enter_img_qq;
    private ImageView enter_img_weixin;
    private ImageView enter_img_weibo;
    /**
     * 腾讯QQ登录的对象
     */
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        initView();
        initClick();
    }

    private void initClick() {
        enter_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Options.REQUEST_CODE_ENTER);
            }
        });
        enter_img_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.showDialog(EnterActivity.this, "提示", "抱歉，此功能暂未开放", null, "确定", new DialogInterface() {
                    @Override
                    public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });
        enter_img_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tools.showDialog(EnterActivity.this, "提示", "抱歉，此功能暂未开放", null, "确定", new DialogInterface() {
                    @Override
                    public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });
        enter_img_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainUserQQData();
            }
        });
    }

    /**
     * 获取用户QQ登录信息，头像及用户名
     */
    private void gainUserQQData() {
        //初始化对象
        mTencent = Tencent.createInstance("1105715507", EnterActivity.this);
        mTencent.login(EnterActivity.this, "all", this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        enter_et_name = ((EditText) findViewById(R.id.enter_et_name));
        enter_et_psw = ((EditText) findViewById(R.id.enter_et_psw));
        enter_register = ((TextView) findViewById(R.id.enter_register));
        enter_img_qq = ((ImageView) findViewById(R.id.enter_img_qq));
        enter_img_weixin = ((ImageView) findViewById(R.id.enter_img_weixin));
        enter_img_weibo = ((ImageView) findViewById(R.id.enter_img_weibo));
        tools = new Tools();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    /**
     * 登录按钮
     *
     * @param view
     */
    public void enter(View view) {
        if (TextUtils.isEmpty(enter_et_name.getText())) {
            tools.showDialog(EnterActivity.this, "提示", "账号不可为空", null, "确定", new DialogInterface() {
                @Override
                public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
            return;
        }
        if (TextUtils.isEmpty(enter_et_psw.getText())) {
            tools.showDialog(EnterActivity.this, "提示", "密码不可为空", null, "确定", new DialogInterface() {
                @Override
                public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
            return;
        }
        String userName = enter_et_name.getText().toString();
        String userPsw = enter_et_psw.getText().toString();
        String data = (String) SharedPreferenceUtils.getData(EnterActivity.this, SharedPreferenceUtils.USER_NAME, "", userName);
        String psw = (String) SharedPreferenceUtils.getData(EnterActivity.this, SharedPreferenceUtils.USER_PSW, "", userName);
        if (!userName.equals(data)) {
            tools.showDialog(EnterActivity.this, "提示", "账号错误", null, "确定", new DialogInterface() {
                @Override
                public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
        } else if (!userPsw.equals(psw)) {
            tools.showDialog(EnterActivity.this, "提示", "密码错误", null, "确定", new DialogInterface() {
                @Override
                public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
        } else if (userName.equals(data) && userPsw.equals(psw)) {
            setResult(Options.REQUEST_CODE_ENTER);
            Toast.makeText(EnterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            //登录的用户账号信息
            SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_NAME, userName);
            SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_PSW, userPsw);
            SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_SEX, "");
            SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_ICON, "");
            this.finish();
        } else {
            tools.showDialog(EnterActivity.this, "提示", "账号或密码错误", null, "", new DialogInterface() {
                @Override
                public void dialogefined(android.content.DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
        }
    }

    /**
     * 腾讯回调完整返回数据
     *
     * @param o
     */
    @Override
    public void onComplete(Object o) {
        if (o != null) {
            Log.e("Frank", "==Tab5Pager.onComplete==请求结果：" + o.toString());
            try {
                JSONObject object = (JSONObject) o;
                if (object != null) {
                    String access_token = object.getString("access_token");
                    String expires = object.getString("expires_in");
                    String openID = object.getString("openid");
                    if (!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openID)) {
                        mTencent.setAccessToken(access_token, expires);
                        mTencent.setOpenId(openID);
                        Log.e("Frank", "==Tab5Pager.onComplete==请求回来的数据:" + access_token + ",ex:" + expires + ",openId:" + openID);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer w = new StringWriter();
                PrintWriter writer = new PrintWriter(w);
                e.printStackTrace(writer);
                Log.e("Frank", "==Tab5Pager.onComplete==解析异常:" + w.toString());
            }
        } else {
            Log.e("Frank", "==Tab5Pager.onComplete==返回为空");
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(EnterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(EnterActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Frank", "==Tab5Pager.onActivityResult==requestCode:" + requestCode + ",resultCode:" + resultCode + ",data:" + data);
        //请求码与腾讯返回登录码一致表示登录成功 11101
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (data != null) {
                Log.e("Frank", "==Tab5Pager.onActivityResult==返回结果信息:" + data.toString());
                Tencent.onActivityResultData(requestCode, resultCode, data, this);
                //将获取的信息解析出来并展示在UI
                showGainData(data);
            }
        }
    }

    private void showGainData(Intent data) {
        if (data != null) {
            //发送结果数据
            Tencent.handleResultData(data, this);
            UserInfo userInfo = new UserInfo(EnterActivity.this, mTencent.getQQToken());
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    if (o != null) {
                        JSONObject object = (JSONObject) o;
                        try {
                            if (object != null) {
                                //获取用户名
                                String nickname = object.getString("nickname");
                                //获取用户头像的url
                                String iconUrl = object.getString("figureurl_qq_2");
                                //获取用户性别
                                String gender = object.getString("gender");
                                Log.e("Frank", "==Tab5Pager.onComplete==用户信息:" + object.toString());
                                //保存数据
                                SharedPreferenceUtils.saveData(EnterActivity.this, nickname, SharedPreferenceUtils.USER_NAME, nickname);
                                SharedPreferenceUtils.saveData(EnterActivity.this, nickname, SharedPreferenceUtils.USER_ICON, iconUrl);
                                SharedPreferenceUtils.saveData(EnterActivity.this, nickname, SharedPreferenceUtils.USER_SEX, gender);
                                SharedPreferenceUtils.saveData(EnterActivity.this, nickname, SharedPreferenceUtils.USER_PSW, "");
                                //登录的用户账号信息
                                SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_NAME, nickname);
                                SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_PSW, "");
                                SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_SEX, "");
                                SharedPreferenceUtils.saveData(EnterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_ICON, "");
                                Toast.makeText(EnterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                setResult(Options.REQUESTCODE_TAB4);
                                EnterActivity.this.finish();
                            }
                        } catch (Exception e) {
                            Writer w = new StringWriter();
                            PrintWriter err = new PrintWriter(w);
                            e.printStackTrace(err);
                            Log.e("Frank", "==Tab5Pager.onComplete==获取用户信息异常:" + w.toString());
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Toast.makeText(EnterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(EnterActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

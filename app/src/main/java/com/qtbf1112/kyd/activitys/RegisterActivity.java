package com.fkgpby0329.yxb.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fkgpby0329.yxb.utils.Options;
import com.fkgpby0329.yxb.utils.SharedPreferenceUtils;
import com.fkgpby0329.yxb.R;

/**
 * 注册账号的界面
 * Create by Frank 0n 2017/10/24 14:20
 *
 * @author Frank
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * 手机号输入框
     */
    private EditText register_et;
    /**
     * 密码输入框
     */
    private EditText register_et_pws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        register_et = ((EditText) findViewById(R.id.register_et));
        register_et_pws = ((EditText) findViewById(R.id.register_et_pws));
    }

    /**
     * 注册按钮
     *
     * @param view
     */
    public void register_btn(View view) {
        if (TextUtils.isEmpty(register_et.getText())) {
            showDialog("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(register_et_pws.getText())) {
            showDialog("请输入密码");
            return;
        }
        //用户名  用用户名作为文件名
        String userName = register_et.getText().toString();
        //用户密码
        String userPSW = register_et_pws.getText().toString();
        SharedPreferenceUtils.saveData(RegisterActivity.this, userName, SharedPreferenceUtils.USER_NAME, userName);
        SharedPreferenceUtils.saveData(RegisterActivity.this, userName, SharedPreferenceUtils.USER_ICON, "");
        SharedPreferenceUtils.saveData(RegisterActivity.this, userName, SharedPreferenceUtils.USER_SEX, "");
        SharedPreferenceUtils.saveData(RegisterActivity.this, userName, SharedPreferenceUtils.USER_PSW, userPSW);
        //登录的用户账号信息
        SharedPreferenceUtils.saveData(RegisterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_NAME, userName);
        SharedPreferenceUtils.saveData(RegisterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_PSW, userPSW);
        SharedPreferenceUtils.saveData(RegisterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_SEX, "");
        SharedPreferenceUtils.saveData(RegisterActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_ICON, "");

        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        setResult(Options.REQUESTCODE_TAB4);
        this.finish();
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(RegisterActivity.this).setTitle("提示")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

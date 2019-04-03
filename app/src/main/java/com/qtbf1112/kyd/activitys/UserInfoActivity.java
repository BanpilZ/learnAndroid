package com.fkgpby0329.yxb.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.utils.Options;
import com.fkgpby0329.yxb.utils.SharedPreferenceUtils;
import com.fkgpby0329.yxb.utils.Tools;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 用户登陆后显示的界面
 * Create by Frank 0n 2017/10/24 10:45
 *
 * @author Frank
 */
public class UserInfoActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    /**
     * 用户头像
     */
    private ImageView user_info_img1;
    /**
     * 用户昵称
     */
    private EditText user_info_et_name;
    /**
     * 用户性别
     */
    private TextView user_info_tv_sex;
    /**
     * 退出登陆按钮
     */
    private Button user_info_exit_btn;
    private ImageOptions bind;
    private RelativeLayout user_info_r1;
    /**
     * 获取的图像路径
     */
    private String photo_path;
    private RelativeLayout user_info_r2;
    private Tools tools;
    private Spinner activity_user_info_sp_sex;
    private String userName;
    private String userImg;
    private String userSex;

    static {
//        System.loadLibrary("native-lib");
    }

//    /**
//     * 人脸识别工具对象
//     */
//    private FaceDiscern faceDiscern;
    /**
     * 从相册获取的bitmap
     */
    private Bitmap bitmap;
    private SurfaceView activityUserInfoSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setStatusBar(UserInfoActivity.this, Color.parseColor("#379bfb"));
        setContentView(R.layout.activity_user_info);
        bind = new ImageOptions.Builder().setCircular(true)
                .setConfig(Bitmap.Config.RGB_565)
                .setIgnoreGif(false)
                .setUseMemCache(true).build();
        tools = new Tools();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra(SharedPreferenceUtils.USER_NAME);
            userImg = intent.getStringExtra(SharedPreferenceUtils.USER_ICON);
            userSex = intent.getStringExtra(SharedPreferenceUtils.USER_SEX);
            if (!TextUtils.isEmpty(userName)) {
                user_info_et_name.setText(userName);
            }
            if (!TextUtils.isEmpty(userImg)) {
                x.image().bind(user_info_img1, userImg, bind);
            }
            if (!TextUtils.isEmpty(userSex)) {
                user_info_tv_sex.setText(userSex);
            }
        }
        //退出按钮
        user_info_exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserInfoActivity.this).setTitle("提示")
                        .setMessage("您确定要退出登录吗?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferenceUtils.removeAll(UserInfoActivity.this, SharedPreferenceUtils.USERINFO);
                                user_info_et_name.setText("");
                                user_info_img1.setImageResource(R.mipmap.lp_defult_avatar);
                                user_info_tv_sex.setText("");
                                UserInfoActivity.this.setResult(Options.REQUESTCODE_TAB4);
                                UserInfoActivity.this.finish();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        /*点击头像的父控件*/
        user_info_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
                Intent mIntent1 = new Intent();
                /*当API linghangbiao 4.2时*/
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    mIntent1.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    mIntent1.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                mIntent1.setType("image/*");
                Intent chooser = Intent.createChooser(mIntent1, "选择头像");
                UserInfoActivity.this.startActivityForResult(chooser, Options.REQUESTCODE_PHOTO);
            }
        });
        /*修改昵称*/
        user_info_et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //改变前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //改变时
            }

            @Override
            public void afterTextChanged(Editable s) {
                //改变后 更新账号昵称
                if (!TextUtils.isEmpty(s)) {
                    SharedPreferenceUtils.saveData(UserInfoActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_NAME, s.toString());
                } else {
                    tools.showDialog(UserInfoActivity.this, "提示", "昵称不可为空", "取消", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        });
        String[] stringArray = getResources().getStringArray(R.array.sex);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserInfoActivity.this, android.R.layout.simple_spinner_item, stringArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_user_info_sp_sex.setAdapter(arrayAdapter);
        activity_user_info_sp_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = arrayAdapter.getItem(position);
                /*更新选择的性别*/
                SharedPreferenceUtils.saveData(UserInfoActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_SEX, item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (TextUtils.isEmpty(userSex)) {
            activity_user_info_sp_sex.setSelection(0);
        } else {
            for (int i = 0; i < arrayAdapter.getCount(); i++) {
                if (userSex.equals(arrayAdapter.getItem(i))) {
                    activity_user_info_sp_sex.setSelection(i, true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initView() {
        user_info_img1 = ((ImageView) findViewById(R.id.user_info_img1));
        user_info_et_name = ((EditText) findViewById(R.id.user_info_et_name));
        user_info_tv_sex = ((TextView) findViewById(R.id.user_info_tv_sex));
        user_info_exit_btn = ((Button) findViewById(R.id.user_info_exit_btn));
        user_info_r1 = ((RelativeLayout) findViewById(R.id.user_info_r1));
        user_info_r2 = ((RelativeLayout) findViewById(R.id.user_info_r2));
        activity_user_info_sp_sex = ((Spinner) findViewById(R.id.activity_user_info_sp_sex));
        activityUserInfoSurface = ((SurfaceView) findViewById(R.id.activity_user_info_surface));
        activityUserInfoSurface.getHolder().addCallback(this);
//        faceDiscern = FaceDiscern.getFaceDiscern();
//        faceDiscern.initData(UserInfoActivity.this);
        activityUserInfoSurface.setZOrderMediaOverlay(true);
        activityUserInfoSurface.setZOrderOnTop(true);
        activityUserInfoSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("Frank", "==UserInfoActivity.onKeyDown==");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (TextUtils.isEmpty(user_info_et_name.getText().toString())) {
                tools.showDialog(UserInfoActivity.this, "提示", "昵称不可为空", "取消", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                    @Override
                    public void dialogefined(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                return false;
            }
        }
        setResult(Options.REQUESTCODE_TAB4);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Frank", "==UserInfoActivity.onDestroy==");
//        faceDiscern.destroy();
        safeRecycled();
    }

    private void safeRecycled() {
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }


    public void userInfoBack(View view) {
        if (TextUtils.isEmpty(user_info_et_name.getText().toString())) {
            tools.showDialog(UserInfoActivity.this, "提示", "昵称不可为空", "取消", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                @Override
                public void dialogefined(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });
            return;
        }
        setResult(Options.REQUESTCODE_TAB4);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Options.REQUESTCODE_PHOTO && data != null) {
            String[] proj = {MediaStore.Images.Media.DATA};
            //查询图像数据库获取选中图片路径
            Cursor query = getContentResolver().query(data.getData(), proj, null, null, null);
            if (query != null && query.moveToFirst()) {
                int columnIndexOrThrow = query.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                photo_path = query.getString(columnIndexOrThrow);
                if (TextUtils.isEmpty(photo_path)) {
                    photo_path = Tools.getImgPath(getApplicationContext(), data.getData());
                }
            }
            Log.e("Frank", "==UserInfoActivity.onActivityResult==获取图片路径 photo_path:" + photo_path);
            query.close();
            if (!TextUtils.isEmpty(photo_path)) {
                x.image().bind(user_info_img1, photo_path, bind);
//                bitmap = faceDiscern.toBitmap(photo_path);
                /*更新默认账号的图像地址信息*/
                SharedPreferenceUtils.saveData(UserInfoActivity.this, SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_ICON, photo_path);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    private void safeProcess() {
        if (null != bitmap && !bitmap.isRecycled()) {
//            faceDiscern.process(bitmap);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        faceDiscern.setSurface(holder.getSurface(), width, height);
        safeProcess();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

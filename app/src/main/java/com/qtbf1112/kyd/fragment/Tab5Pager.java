package com.fkgpby0329.yxb.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.fkgpby0329.yxb.activitys.EnterActivity;
import com.fkgpby0329.yxb.activitys.FansActivity;
import com.fkgpby0329.yxb.activitys.LookHistoryActivity;
import com.fkgpby0329.yxb.activitys.LotteryHistoryActivity;
import com.fkgpby0329.yxb.activitys.LotteryTaskActivity;
import com.fkgpby0329.yxb.activitys.MineCareActivity;
import com.fkgpby0329.yxb.activitys.MineMessagesActivity;
import com.fkgpby0329.yxb.activitys.RegisterActivity;
import com.fkgpby0329.yxb.activitys.RemindActivity;
import com.fkgpby0329.yxb.activitys.UserInfoActivity;
import com.fkgpby0329.yxb.utils.Options;
import com.fkgpby0329.yxb.utils.SharedPreferenceUtils;
import com.fkgpby0329.yxb.utils.Tools;

import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.fkgpby0329.yxb.R;

/**
 * “我”的界面
 * 主要展示用户账号，查看历史记录界面
 * Create by Frank 0n 2017/10/23 14:35
 *
 * @author Frank
 */
public class Tab5Pager extends Fragment implements IUiListener {

    /**
     * Tab4Pager布局的父容器布局
     */
    private RelativeLayout fragment_tab4_parent_layout;
    /**
     * 用户头像
     */
    private ImageView mine_img;
    /**
     * RadioGroup观看历史、彩票任务、彩票记录
     */
    private RadioGroup mine_ll;
    /**
     * “我的消息”布局容器
     */
    private RelativeLayout mine_rl_mess;
    /**
     * “我的粉丝”布局容器
     */
    private RelativeLayout mine_rl_fans;
    /**
     * “我的关注”布局容器
     */
    private RelativeLayout mine_rl_attention;
    /**
     * “开奖提醒”布局容器
     */
    private RelativeLayout mine_rl_alert;
    /**
     * “设置”布局容器
     */
    private RelativeLayout mine_rl_setting;
    /**
     * “头像”的父容器
     */
    private RelativeLayout mine_rl;
    private PopupWindow popupWindow;
    private View tab4_view_layout;
    /**
     * 腾讯QQ登录的对象
     */
    private Tencent mTencent;
    /**
     * 腾讯QQ登录回调接口对象
     */
    private IUiListener uiListener;
    /**
     * 用户名
     */
    private TextView mine_tv;
    /**
     * 处理圆形图片的参数
     */
    private ImageOptions imageOptions;
    private String userName, userImg, userSex;
    private Tools tools;

    public Tab5Pager() {
        // Required empty public constructor
        Log.e("Frank", "==Tab5Pager.Tab5Pager==类创建时");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Frank", "==Tab5Pager.onAttach==---111111");
        imageOptions = new ImageOptions.Builder()
                //设置圆角
                .setCircular(true)
                //设置缓存
                .setUseMemCache(true)
                //设置图片类型
                .setConfig(Bitmap
                        .Config.RGB_565).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Frank", "==Tab5Pager.onCreate==00000000");
        //用户名  用用户名作为文件名
        String userName = "administrator";
        //用户密码
        String userPSW = "administrator";
        //获取默认账号信息
        String data = (String) SharedPreferenceUtils.getData(getActivity(), SharedPreferenceUtils.USER_NAME, "", SharedPreferenceUtils.USERINFO);
        //默认账号为空时保存默认账号
        if (TextUtils.isEmpty(data)) {
            SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_NAME, userName);
            SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_ICON, "");
            SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_SEX, "");
            SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_PSW, userPSW);
            SharedPreferenceUtils.selfAccount(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tab4_view_layout = inflater.inflate(R.layout.fragment_tab5_pager, container, false);
        initView(tab4_view_layout);
        if (tools == null) {
            tools = new Tools();
        }
        Log.e("Frank", "==Tab5Pager.onCreateView==111111");
        return tab4_view_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Frank", "==Tab5Pager.onResume==222222");
        onClickView();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Frank", "==Tab5Pager.onPause==33333");
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Frank", "==Tab5Pager.onDestroyView==4444444");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Frank", "==Tab5Pager.onDestroy==55555");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Frank", "==Tab5Pager.onDetach==666666");
    }

    /**
     * 点击事件的响应
     */
    private void onClickView() {
        //当用户未登陆时
        userName = (String) SharedPreferenceUtils.getData(getActivity(), SharedPreferenceUtils.USER_NAME, "", SharedPreferenceUtils.USERINFO);
        userImg = (String) SharedPreferenceUtils.getData(getActivity(), SharedPreferenceUtils.USER_ICON, "", SharedPreferenceUtils.USERINFO);
        userSex = (String) SharedPreferenceUtils.getData(getActivity(), SharedPreferenceUtils.USER_SEX, "", SharedPreferenceUtils.USERINFO);
        if (!TextUtils.isEmpty(userName)) {
            mine_tv.setText(userName);
        } else {
            mine_tv.setText("点击登录....");
        }
        if (TextUtils.isEmpty(userImg)) {
            mine_img.setImageResource(R.mipmap.tosend);
        } else {
            x.image().bind(mine_img, userImg, imageOptions);
        }
        //点击登录
        mine_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUser();
            }
        });
        mine_rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUser();
            }
        });
        mine_ll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Intent intent;
                if (!TextUtils.isEmpty(userName)) {
                    switch (checkedId) {
                        //彩票历史
                        case R.id.mine_history:
                            intent = new Intent(getActivity(), LookHistoryActivity.class);
                            startActivityForResult(intent, Options.REQUESTCODE_LOOK_TO);
                            mine_ll.clearCheck();
                            break;
                        //彩票任务
                        case R.id.mine_task:
                            intent = new Intent(getActivity(), LotteryTaskActivity.class);
                            startActivityForResult(intent, Options.REQUESTCODE_TASK_TO);
                            mine_ll.clearCheck();
                            break;
                        case R.id.mine_pay:
                            intent = new Intent(getActivity(), LotteryHistoryActivity.class);
                            startActivityForResult(intent, Options.REQUESTCODE_HISTORT_TO);
                            mine_ll.clearCheck();
                            break;
                        default:
                            break;
                    }
                } else {
                    tools.showDialog(getActivity(), "提示", "请先登录", "", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    mine_ll.clearCheck();
                }
            }
        });
        //我的消息点击事件
        mine_rl_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(getActivity(), MineMessagesActivity.class);
                    startActivityForResult(intent, Options.REQUESTCODE_MINE_MESSAGES);
                } else {
                    tools.showDialog(getActivity(), "提示", "请先登录", "", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        });
        //我的粉丝点击事件
        mine_rl_fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(getActivity(), FansActivity.class);
                    startActivityForResult(intent, Options.REQUESTCODE_FANS);
                } else {
                    tools.showDialog(getActivity(), "提示", "请先登录", "", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        });
        //我的关注
        mine_rl_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(getActivity(), MineCareActivity.class);
                    startActivityForResult(intent, Options.REQUESTCODE_CORE);
                } else {
                    tools.showDialog(getActivity(), "提示", "请先登录", "", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        });
        //开奖提醒
        mine_rl_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(getActivity(), RemindActivity.class);
                    startActivityForResult(intent, Options.REQUESTCODE_REMIND);
                } else {
                    tools.showDialog(getActivity(), "提示", "请先登录", "", "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void showUser() {
        //判断存储中有数据时用户已登陆
        if (userName != null && !TextUtils.isEmpty(userName.toString())) {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra(SharedPreferenceUtils.USER_NAME, userName.toString());
            intent.putExtra(SharedPreferenceUtils.USER_ICON, userImg.toString());
            intent.putExtra(SharedPreferenceUtils.USER_SEX, userSex.toString());
            startActivityForResult(intent, Options.REQUESTCODE_TAB4);
        } else {
            //此处弹出PopupWindow
            initPopupWindow();
            //设置Popup弹出的位置
            popupWindow.showAtLocation(tab4_view_layout, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 初始化弹出框
     */
    private void initPopupWindow() {
        View popupLayout = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_layout, null);
        popupWindow = new PopupWindow(popupLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //将布局设置进去
        popupWindow.setContentView(popupLayout);
        //设置内部点击响应
        popupWindow.setFocusable(true);
        //设置外部点击响应
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //初始化popup控件    注册按钮
        Button pop_register = (Button) popupLayout.findViewById(R.id.pop_register);
        //登录按钮
        Button pop_enter = (Button) popupLayout.findViewById(R.id.pop_enter);
        //QQ登录图标
        ImageView pop_img_qq = (ImageView) popupLayout.findViewById(R.id.pop_img_qq);
        //微信
        ImageView pop_img_weixin = (ImageView) popupLayout.findViewById(R.id.pop_img_weixin);
        //微博
        ImageView pop_img_weibo = (ImageView) popupLayout.findViewById(R.id.pop_img_weibo);
        pop_img_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击QQ图标登录
                gainUserQQData();
                popupWindow.dismiss();
            }
        });
        pop_img_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                popupWindow.dismiss();
            }
        });
        pop_img_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                popupWindow.dismiss();
            }
        });
        pop_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, Options.REQUESTCODE_TAB4);
            }
        });
        pop_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //账号登录
                Intent intent = new Intent(getActivity(), EnterActivity.class);
                startActivityForResult(intent, Options.REQUEST_CODE_ENTER);
            }
        });
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("抱歉，此功能暂未开放")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 获取用户QQ登录信息，头像及用户名
     */
    private void gainUserQQData() {
        //初始化对象
        mTencent = Tencent.createInstance("1105715507", getActivity());
        mTencent.login(Tab5Pager.this, "all", this);
    }

    /**
     * 初始化视图控件
     *
     * @param inflate 父容器控件
     */
    private void initView(View inflate) {
        fragment_tab4_parent_layout = ((RelativeLayout) inflate.findViewById(R.id.fragment_tab4_parent_layout));
        mine_rl = ((RelativeLayout) inflate.findViewById(R.id.mine_rl));
        mine_img = ((ImageView) inflate.findViewById(R.id.mine_img));
        mine_ll = ((RadioGroup) inflate.findViewById(R.id.mine_ll));
        mine_rl_mess = ((RelativeLayout) inflate.findViewById(R.id.mine_rl_mess));
        mine_rl_fans = ((RelativeLayout) inflate.findViewById(R.id.mine_rl_fans));
        mine_rl_attention = ((RelativeLayout) inflate.findViewById(R.id.mine_rl_attention));
        mine_rl_alert = ((RelativeLayout) inflate.findViewById(R.id.mine_rl_alert));
        mine_rl_setting = ((RelativeLayout) inflate.findViewById(R.id.mine_rl_setting));
        mine_tv = ((TextView) inflate.findViewById(R.id.mine_tv));
        //API>=11时Android 3.0以上机型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                if (toolbar != null) {
                    int height = toolbar.getHeight();
                    fragment_tab4_parent_layout.setPadding(0, height, 0, 0);
                    Log.e("Frank", "==Tab5Pager.initView==头布局的高度height:" + height);
                }
            }
        }
    }

    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return true;
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

    /**
     * 获取失败
     *
     * @param uiError
     */
    @Override
    public void onError(UiError uiError) {
        Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求取消
     */
    @Override
    public void onCancel() {
        Toast.makeText(getActivity(), "取消登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    /**
     * 将返回获取的信息展示在UI控件上
     *
     * @param data
     */
    private void showGainData(Intent data) {
        if (data != null) {
            //发送结果数据
            Tencent.handleResultData(data, this);
            UserInfo userInfo = new UserInfo(getActivity(), mTencent.getQQToken());
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
                                mine_tv.setText(nickname);
                                x.image().bind(mine_img, iconUrl, imageOptions);
                                userName = nickname;
                                userImg = iconUrl;
                                userSex = gender;
                                //保存数据
                                SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_NAME, nickname);
                                SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_ICON, iconUrl);
                                SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_SEX, gender);
                                SharedPreferenceUtils.saveData(getActivity(), userName, SharedPreferenceUtils.USER_PSW, "");
                                //登录的用户账号信息
                                SharedPreferenceUtils.saveData(getActivity(), SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_NAME, userName);
                                SharedPreferenceUtils.saveData(getActivity(), SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_PSW, "");
                                SharedPreferenceUtils.saveData(getActivity(), SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_SEX, gender);
                                SharedPreferenceUtils.saveData(getActivity(), SharedPreferenceUtils.USERINFO, SharedPreferenceUtils.USER_ICON, iconUrl);
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
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getActivity(), "取消登录", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

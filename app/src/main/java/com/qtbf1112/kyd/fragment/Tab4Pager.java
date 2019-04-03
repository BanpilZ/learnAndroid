package com.fkgpby0329.yxb.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.fkgpby0329.yxb.utils.SharedPreferenceUtils;
import com.fkgpby0329.yxb.utils.Tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fkgpby0329.yxb.R;
import com.fkgpby0329.yxb.utils.ParseJson;
import com.fkgpby0329.yxb.utils.TaskUtil;
import com.fkgpby0329.yxb.adapter.RecyclerAdapter;
import com.fkgpby0329.yxb.back.InterfaceMessage;
import com.fkgpby0329.yxb.bean.ChatMessage;

/**
 * Create by Frank 0n 2017/10/25 19:03
 *
 * @author Frank
 */
public class Tab4Pager extends Fragment {


    /**
     * 输入框
     */
    private EditText fragment_tab3_edt_1;
    /**
     * 发送按钮
     */
    private Button fragment_tab3_btn_send;
    private RecyclerView fragment_recycle_1;
    private Tools tools;
    /**
     * 访问链接
     */
    private static final String URL = "http://www.tuling123.com/openapi/api";
    /**
     * 访问key值
     */
    private static final String API_KEY = "9a1f479bdde0407fbdbb69b3f71c44a6";
    private static final String API_KEY2 = "9e566231893b448c884b03e9b8cd210b";
    /**
     * 解析Json对象
     */
    private ParseJson parseJson;
    private RecyclerAdapter recyclerAdapter;
    private List<ChatMessage> chatMessages;
    private RelativeLayout fragment_tab3_pager_r1;
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    /**
     * 发送消息的头像自定义
     */
    private String userImg;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (chatMessages == null) {
            chatMessages = new ArrayList<>();
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setDate(new Date());
        chatMessage.setMsg("你好啊,我是你的小伙伴,我无所不能哦");
        chatMessage.setType(ChatMessage.Type.INCOM2);
        chatMessage.setName("launcher");
        chatMessages.add(chatMessage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4_pager, container, false);
        initView(view);
        if (chatMessages == null) {
            chatMessages = new ArrayList<>();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userImg = (String) SharedPreferenceUtils.getData(getActivity(), SharedPreferenceUtils.USER_ICON, "", SharedPreferenceUtils.USERINFO);
        recyclerAdapter = new RecyclerAdapter(getActivity(), chatMessages);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragment_recycle_1.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        fragment_recycle_1.setHasFixedSize(true);
        fragment_recycle_1.setAdapter(recyclerAdapter);
        fragment_tab3_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当输入框内容为空时,提示用户
                if (TextUtils.isEmpty(fragment_tab3_edt_1.getText().toString())) {
                    tools.showDialog(getActivity(), "提示", "请输入发送内容", null, "确定", new com.fkgpby0329.yxb.back.DialogInterface() {
                        @Override
                        public void dialogefined(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                            return;
                        }
                    });
                }
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setDate(new Date());
                chatMessage.setMsg(fragment_tab3_edt_1.getText().toString());
                if (!TextUtils.isEmpty(userImg)) {
                    chatMessage.setImgPath(userImg);
                }
                chatMessage.setType(ChatMessage.Type.OUTSEND);
                chatMessage.setName("me");
                chatMessages.add(chatMessage);
                sendMessage();
                fragment_recycle_1.scrollToPosition(chatMessages.size() - 1);
                recyclerAdapter.notifyDataSetChanged();
                fragment_tab3_edt_1.setText("");
                keyBoardShow(1);
            }
        });
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(URL);
            buffer.append("?key=");
            buffer.append(API_KEY);
            buffer.append("&info=" + URLEncoder.encode(fragment_tab3_edt_1.getText().toString(), "UTF-8"));
            Log.e("Frank", "==Tab3Pager.sendMessage==访问路径:" + buffer.toString());
            new TaskUtil(buffer.toString()).getMessages(new InterfaceMessage() {
                @Override
                public void getMessages(ChatMessage chatMessage) {
                    if (chatMessage != null) {
                        chatMessages.add(chatMessage);
                        fragment_recycle_1.scrollToPosition(chatMessages.size() - 1);
                        recyclerAdapter.notifyDataSetChanged();
                        System.gc();
                    }
                }
            });
            buffer = new StringBuffer(URL + "?key=" + API_KEY2 + "&info=" + URLEncoder.encode(fragment_tab3_edt_1.getText().toString(), "UTF-8"));
            new TaskUtil(buffer.toString(), 2).getMessages(new InterfaceMessage() {
                @Override
                public void getMessages(ChatMessage chatMessage) {
                    if (chatMessage != null) {
                        chatMessages.add(chatMessage);
                        fragment_recycle_1.scrollToPosition(chatMessages.size() - 1);
                        recyclerAdapter.notifyDataSetChanged();
                        System.gc();
                    }
                }
            });
        } catch (Exception e) {
            Writer w = new StringWriter();
            PrintWriter err = new PrintWriter(w);
            e.printStackTrace(err);
            Log.e("Frank", "==Tab3Pager.sendMessage==异常:" + w.toString());
        }
    }

    private void initView(View layout) {
        fragment_tab3_edt_1 = ((EditText) layout.findViewById(R.id.fragment_tab3_edt_1));
        fragment_tab3_btn_send = ((Button) layout.findViewById(R.id.fragment_tab3_btn_send));
        fragment_recycle_1 = ((RecyclerView) layout.findViewById(R.id.fragment_recycle_1));
        fragment_tab3_pager_r1 = ((RelativeLayout) layout.findViewById(R.id.fragment_tab3_pager_r1));
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            if (toolbar != null) {
                int height = toolbar.getHeight();
                fragment_tab3_pager_r1.setPadding(0, height, 0, 0);
            }
        }
        tools = new Tools();
    }

    /**
     * 软件盘的关开
     *
     * @param key 1:关闭软键盘;
     */
    private void keyBoardShow(int key) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //当等于1时表示关闭软键盘
        if (key == 1) {
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
        } else {
            if (imm != null) {
                getActivity().getWindow().getDecorView().requestFocus();
                imm.showSoftInput(getActivity().getWindow().getDecorView(), 0);
            }
        }
    }

    public boolean onKeyDown(int keyCode) {
        return true;
    }
}

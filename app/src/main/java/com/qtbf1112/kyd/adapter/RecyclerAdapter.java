package com.fkgpby0329.yxb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkgpby0329.yxb.bean.ChatMessage;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fkgpby0329.yxb.R;

/**
 * @author Frank
 * @date 2017/10/24 18:05
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> chatMessages;
    private LayoutInflater inflater;
    private static final Integer INCOME_TYPE = 0;
    private static final Integer INCOME2_TYPE = 1;
    private static final Integer TOSEND_TYPE = 2;
    private ImageOptions bind;

    public RecyclerAdapter(Context context, List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
        inflater = LayoutInflater.from(context);
        bind = new ImageOptions.Builder().setCircular(true)
                .setConfig(Bitmap.Config.RGB_565)
                .setIgnoreGif(false)
                .setUseMemCache(true).build();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        //当返回类型是接收时,返回布局0
        if (chatMessage.getType() == ChatMessage.Type.INCOME) {
            return INCOME_TYPE;
        } else if (chatMessage.getType() == ChatMessage.Type.INCOM2) {
            return INCOME2_TYPE;
        }
        return TOSEND_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (INCOME_TYPE == viewType) {
            View view = inflater.inflate(R.layout.item_in_come_layout, parent, false);
            viewHolder = new InComeViewHolder(view);
        } else if (INCOME2_TYPE == viewType) {
            View view = inflater.inflate(R.layout.item_in_come_layout2, parent, false);
            viewHolder = new InComeViewHolder2(view);
        } else {
            View view = inflater.inflate(R.layout.item_to_send_layout, parent, false);
            viewHolder = new ToSendViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:linghangbiao");
        Date date = chatMessage.getDate();
        String format1 = format.format(date);
        if (holder instanceof InComeViewHolder) {
            ((InComeViewHolder) holder).tv_from_msg.setText(chatMessage.getMsg());
            ((InComeViewHolder) holder).tv_from_time.setText(format1);
            ((InComeViewHolder) holder).tv_from_url.setText(chatMessage.getUrl());
        } else if (holder instanceof InComeViewHolder2) {
            ((InComeViewHolder2) holder).tv_from_msg.setText(chatMessage.getMsg());
            ((InComeViewHolder2) holder).tv_from_time.setText(format1);
            ((InComeViewHolder2) holder).tv_from_url.setText(chatMessage.getUrl());
        } else {
            ((ToSendViewHolder) holder).tv_to_msg.setText(chatMessage.getMsg());
            ((ToSendViewHolder) holder).tv_to_time.setText(format1);
            if (!TextUtils.isEmpty(chatMessage.getImgPath())) {
                x.image().bind(((ToSendViewHolder) holder).item_to_send_img, chatMessage.getImgPath(), bind);
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages == null ? 0 : chatMessages.size();
    }

    /**
     * 接收回来的
     */
    class InComeViewHolder extends RecyclerView.ViewHolder {
        /**
         * 接收消息的头像
         */
        private final ImageView item_in_come_img;
        /**
         * 接收消息的信息
         */
        private final TextView tv_from_msg;
        private final TextView tv_from_time;
        private final TextView tv_from_url;

        public InComeViewHolder(View itemView) {
            super(itemView);
            item_in_come_img = ((ImageView) itemView.findViewById(R.id.item_in_come_img));
            tv_from_msg = ((TextView) itemView.findViewById(R.id.tv_from_msg));
            tv_from_time = ((TextView) itemView.findViewById(R.id.tv_from_time));
            tv_from_url = ((TextView) itemView.findViewById(R.id.tv_from_url));
        }
    }

    /**
     * 接收回来的
     */
    class InComeViewHolder2 extends RecyclerView.ViewHolder {
        /**
         * 接收消息的头像
         */
        private final ImageView item_in_come_img;
        /**
         * 接收消息的信息
         */
        private final TextView tv_from_msg;
        private final TextView tv_from_time;
        private final TextView tv_from_url;

        public InComeViewHolder2(View itemView) {
            super(itemView);
            item_in_come_img = ((ImageView) itemView.findViewById(R.id.item_in_come_img));
            tv_from_msg = ((TextView) itemView.findViewById(R.id.tv_from_msg));
            tv_from_time = ((TextView) itemView.findViewById(R.id.tv_from_time));
            tv_from_url = ((TextView) itemView.findViewById(R.id.tv_from_url));
        }
    }


    /**
     * 发送出去的
     */
    class ToSendViewHolder extends RecyclerView.ViewHolder {
        /**
         * 发送的消息
         */
        private final TextView tv_to_msg;
        /**
         * 发送消息的图片
         */
        private final ImageView item_to_send_img;
        private final TextView tv_to_time;

        public ToSendViewHolder(View itemView) {
            super(itemView);
            tv_to_msg = ((TextView) itemView.findViewById(R.id.tv_to_msg));
            item_to_send_img = ((ImageView) itemView.findViewById(R.id.item_to_send_img));
            tv_to_time = ((TextView) itemView.findViewById(R.id.tv_to_time));
        }
    }

}

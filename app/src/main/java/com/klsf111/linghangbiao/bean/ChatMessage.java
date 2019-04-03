package com.klsf111.linghangbiao.activitys.bean;

import java.util.Date;

/**
 * 获取聊天数据的基类
 *
 * @author Frank
 * @date 2017/10/24 17:40
 */

public class ChatMessage {
    /**
     * 消息用户
     */
    private String name;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 消息时间
     */
    private Date date;
    private Type type;
    /**
     * 图片路径
     */
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }

    /**
     * 枚举类型，接受消息，发送消息
     */
    public enum Type {
        INCOME, OUTSEND, INCOM2
    }
}

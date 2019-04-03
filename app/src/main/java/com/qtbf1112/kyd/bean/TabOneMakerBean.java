package com.fkgpby0329.yxb.bean;

/**
 * TabOne顶部轮询的bean
 *
 * @author Frank
 * @date 2018/1/16 9:53
 */

public class TabOneMakerBean {
    private String imgPath;
    private int position;
    private int flag;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TabOneMakerBean{" +
                "imgPath='" + imgPath + '\'' +
                ", position=" + position +
                ", flag=" + flag +
                ", text='" + text + '\'' +
                '}';
    }
}

package com.fkgpby0329.yxb.utils;


import android.util.Base64;

/**
 * Created by 愤怒的艾泽拉斯 on 2018/5/10.
 */

public class BaseDecode {

    /**
     * 字符串进行Base64解码解密
     *
     * @param encodedString
     * @return
     */

    public static String decodeString(String encodedString) {
        return new String(Base64.decode(encodedString, Base64.DEFAULT));
    }

}

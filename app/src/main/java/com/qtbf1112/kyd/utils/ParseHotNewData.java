package com.fkgpby0329.yxb.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.fkgpby0329.yxb.bean.TabOneHotNewBean;

/**
 * 解析热点新闻
 *
 * @author Frank
 * @date 2018/1/16 15:26
 */

public class ParseHotNewData {
    /**
     * 解析动态数据
     *
     * @param strJson 需要解析的数据
     * @return 返回数据集合
     */
    public static List<TabOneHotNewBean.DongtaiBean> parseDongTai(String strJson) {
        List<TabOneHotNewBean.DongtaiBean> beanList = new ArrayList<>();
        Gson gson = new Gson();
        TabOneHotNewBean tabOneHotNewBean = gson.fromJson(strJson, TabOneHotNewBean.class);
        if (tabOneHotNewBean != null) {
            List<TabOneHotNewBean.DongtaiBean> dongtai = tabOneHotNewBean.getDongtai();
            beanList.addAll(dongtai);
        }
        return beanList;
    }
}

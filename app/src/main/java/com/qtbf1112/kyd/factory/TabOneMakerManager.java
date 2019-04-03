package com.fkgpby0329.yxb.factory;

import android.content.Context;


import com.fkgpby0329.yxb.bean.TabOneMakerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * TabOne顶部轮询管理
 *
 * @author Frank
 * @date 2018/1/16 9:56
 */

public class TabOneMakerManager {
    private static TabOneMakerManager makerManager = new TabOneMakerManager();
    private List<TabOneMakerBean> makerBeanList;

    public static synchronized TabOneMakerManager getMakerManager() {
        return makerManager;
    }

    public List<TabOneMakerBean> getMakerBeanList(Context context) {
        makerBeanList = new ArrayList<>();
        if (makerBeanList.size() > 0) {
            makerBeanList.clear();
        }
        TabOneMakerBean tabOneMakerBean = new TabOneMakerBean();

        tabOneMakerBean.setImgPath("");
        tabOneMakerBean.setPosition(0);
        tabOneMakerBean.setFlag(0);
        tabOneMakerBean.setText("C罗救主 皇马1-1战平毕巴");
        makerBeanList.add(tabOneMakerBean);

        TabOneMakerBean tabOneMakerBean1 = new TabOneMakerBean();
        tabOneMakerBean1.setImgPath("");
        tabOneMakerBean1.setPosition(1);
        tabOneMakerBean1.setFlag(1);
        tabOneMakerBean1.setText("登贝莱破门 巴萨2-2战平");
        makerBeanList.add(tabOneMakerBean1);

        TabOneMakerBean tabOneMakerBean2 = new TabOneMakerBean();
        tabOneMakerBean2.setImgPath("");
        tabOneMakerBean2.setPosition(2);
        tabOneMakerBean2.setFlag(2);
        tabOneMakerBean2.setText("曼联0-1负 送曼城提前5轮夺冠");
        makerBeanList.add(tabOneMakerBean2);

        TabOneMakerBean tabOneMakerBean3 = new TabOneMakerBean();
        tabOneMakerBean3.setImgPath("");
        tabOneMakerBean3.setPosition(3);
        tabOneMakerBean3.setFlag(3);
        tabOneMakerBean3.setText("西甲新纪录！巴萨跨赛季39轮不败创历史");
        makerBeanList.add(tabOneMakerBean3);
        return makerBeanList;
    }
}

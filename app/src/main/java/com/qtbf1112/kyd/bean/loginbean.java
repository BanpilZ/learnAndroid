package com.fkgpby0329.yxb.bean;

/**
 * Created by 愤怒的艾泽拉斯 on 2018/5/10.
 */

public class loginbean {

    /**
     * data : {"appid":"cs999","appname":"安卓测试","is_jump":"1","jump_url":"https://app.yhzs168.com/193dd.cc/dayingjiacaizy.apk","status":"2"}
     * type : 200
     */

    private DataBean data;
    private String type;
    /**
     * rt_code : 201
     */

    private String rt_code;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRt_code() {
        return rt_code;
    }

    public void setRt_code(String rt_code) {
        this.rt_code = rt_code;
    }

    public static class DataBean {
        /**
         * appid : cs999
         * appname : 安卓测试
         * is_jump : 1
         * jump_url : https://app.yhzs168.com/193dd.cc/dayingjiacaizy.apk
         * status : 2
         */

        private String appid;
        private String appname;
        private String is_jump;
        private String jump_url;
        private String status;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getIs_jump() {
            return is_jump;
        }

        public void setIs_jump(String is_jump) {
            this.is_jump = is_jump;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

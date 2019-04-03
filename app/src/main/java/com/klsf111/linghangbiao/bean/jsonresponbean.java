package com.klsf111.linghangbiao.activitys.bean;

/**
 * Created by QYQ on 2018/6/30.
 */

public class jsonresponbean {

    /**
     * id : 2342msn
     * result : {"appId":"com.klsf111.linghangbiao.activitys","show":0,"url":"http://YLCADU.COM/","bgColor":"#ffffff","fontColor":"345675","version":"1.0","appStoreUrl":"","showNavBar":0,"appDate":"2018-06-29","platform":"2","memo":"","updateTime":"2018-06-30 04:16:11","hotUpdate":0,"templateId":"10","firstPage":8,"appid":"com.klsf111.linghangbiao.activitys","showWap":0,"wapUrl":"http://YLCADU.COM/","backgroundColor":"#ffffff"}
     * error : null
     * jsonrpc : 2.0
     */

    private String id;
    private ResultBean result;
    private Object error;
    private String jsonrpc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public static class ResultBean {
        /**
         * appId : com.klsf111.linghangbiao.activitys
         * show : 0
         * url : http://YLCADU.COM/
         * bgColor : #ffffff
         * fontColor : 345675
         * version : 1.0
         * appStoreUrl :
         * showNavBar : 0
         * appDate : 2018-06-29
         * platform : 2
         * memo :
         * updateTime : 2018-06-30 04:16:11
         * hotUpdate : 0
         * templateId : 10
         * firstPage : 8
         * appid : com.klsf111.linghangbiao.activitys
         * showWap : 0
         * wapUrl : http://YLCADU.COM/
         * backgroundColor : #ffffff
         */

        private String appid;
        private boolean showWap;
        private String wapUrl;
        private String backGroundColor;
        private String fontColor;
        private String version;
        private String appStoreUrl;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public boolean isShowWap() {
            return showWap;
        }

        public void setShowWap(boolean showWap) {
            this.showWap = showWap;
        }

        public String getWapUrl() {
            return wapUrl;
        }

        public void setWapUrl(String wapUrl) {
            this.wapUrl = wapUrl;
        }

        public String getBackGroundColor() {
            return backGroundColor;
        }

        public void setBackGroundColor(String backGroundColor) {
            this.backGroundColor = backGroundColor;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAppStoreUrl() {
            return appStoreUrl;
        }

        public void setAppStoreUrl(String appStoreUrl) {
            this.appStoreUrl = appStoreUrl;
        }

        public boolean isShowNavBar() {
            return showNavBar;
        }

        public void setShowNavBar(boolean showNavBar) {
            this.showNavBar = showNavBar;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        private boolean showNavBar;
        private int firstPage;
    }
}

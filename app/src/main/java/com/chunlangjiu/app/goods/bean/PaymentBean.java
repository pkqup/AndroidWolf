package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class PaymentBean {

    private List<PaymentInfo> list;

    public List<PaymentInfo> getList() {
        return list;
    }

    public void setList(List<PaymentInfo> list) {
        this.list = list;
    }

    public class PaymentInfo {

        private String app_name;
        private String app_display_name;
        private String app_id;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getApp_display_name() {
            return app_display_name;
        }

        public void setApp_display_name(String app_display_name) {
            this.app_display_name = app_display_name;
        }
    }

}

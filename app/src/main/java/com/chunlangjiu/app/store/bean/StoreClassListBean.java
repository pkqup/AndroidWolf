package com.chunlangjiu.app.store.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe:
 */
public class StoreClassListBean {

    private List<StoreClassBean> list;

    public List<StoreClassBean> getList() {
        return list;
    }

    public void setList(List<StoreClassBean> list) {
        this.list = list;
    }

    public class  StoreClassBean{
        private String chateaucat_id;
        private String chateaucat_name;
        private String chateaucat_alias;
        private String order_sort;
        private String modified_time;
        private String disabled;

        public String getChateaucat_id() {
            return chateaucat_id;
        }

        public void setChateaucat_id(String chateaucat_id) {
            this.chateaucat_id = chateaucat_id;
        }

        public String getChateaucat_name() {
            return chateaucat_name;
        }

        public void setChateaucat_name(String chateaucat_name) {
            this.chateaucat_name = chateaucat_name;
        }

        public String getChateaucat_alias() {
            return chateaucat_alias;
        }

        public void setChateaucat_alias(String chateaucat_alias) {
            this.chateaucat_alias = chateaucat_alias;
        }

        public String getOrder_sort() {
            return order_sort;
        }

        public void setOrder_sort(String order_sort) {
            this.order_sort = order_sort;
        }

        public String getModified_time() {
            return modified_time;
        }

        public void setModified_time(String modified_time) {
            this.modified_time = modified_time;
        }

        public String getDisabled() {
            return disabled;
        }

        public void setDisabled(String disabled) {
            this.disabled = disabled;
        }
    }

}

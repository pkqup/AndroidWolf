package com.chunlangjiu.app.store.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/20
 * @Describe:
 */
public class StoreListBean {

    private List<Store> list;

    public List<Store> getList() {
        return list;
    }

    public void setList(List<Store> list) {
        this.list = list;
    }

    public class Store{
        private String chateau_id;
        private String name;

        public String getChateau_id() {
            return chateau_id;
        }

        public void setChateau_id(String chateau_id) {
            this.chateau_id = chateau_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

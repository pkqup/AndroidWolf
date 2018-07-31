package com.chunlangjiu.app.store.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/31
 * @Describe:
 */
public class SearchBean {

    private String id;
    private String name;
    private List<SearchSecondBean> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SearchSecondBean> getList() {
        return list;
    }

    public void setList(List<SearchSecondBean> list) {
        this.list = list;
    }
}

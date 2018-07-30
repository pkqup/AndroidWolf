package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class FilterBrandBean {

    private String id;
    private String name;
    private boolean select;


    public FilterBrandBean() {

    }
    public FilterBrandBean(String id, String name, boolean select) {
        this.id = id;
        this.name = name;
        this.select = select;
    }

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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}

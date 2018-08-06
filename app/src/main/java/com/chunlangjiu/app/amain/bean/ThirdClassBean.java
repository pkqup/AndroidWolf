package com.chunlangjiu.app.amain.bean;

import java.io.Serializable;

/**
 * @CreatedbBy: liucun on 2018/8/6
 * @Describe:
 */
public class ThirdClassBean implements Serializable{

    private String cat_id;
    private String parent_id;
    private String cat_name;
    private String cat_logo;
    private String cat_path;
    private String level;
    private String is_leaf;
    private String child_count;
    private String order_sort;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_logo() {
        return cat_logo;
    }

    public void setCat_logo(String cat_logo) {
        this.cat_logo = cat_logo;
    }

    public String getCat_path() {
        return cat_path;
    }

    public void setCat_path(String cat_path) {
        this.cat_path = cat_path;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(String is_leaf) {
        this.is_leaf = is_leaf;
    }

    public String getChild_count() {
        return child_count;
    }

    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }

    public String getOrder_sort() {
        return order_sort;
    }

    public void setOrder_sort(String order_sort) {
        this.order_sort = order_sort;
    }
}

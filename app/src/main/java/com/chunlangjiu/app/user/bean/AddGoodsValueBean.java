package com.chunlangjiu.app.user.bean;

/**
 * @CreatedbBy: liucun on 2018/9/15.
 * @Describe:
 */
public class AddGoodsValueBean {

    public AddGoodsValueBean(String title,String value){
        this.title =title;
        this.value = value;
    }

    private String title;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

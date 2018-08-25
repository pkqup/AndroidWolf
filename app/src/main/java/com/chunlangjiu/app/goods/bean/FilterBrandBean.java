package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class FilterBrandBean {

    private String brand_id;
    private String brand_name;
    private boolean select;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}

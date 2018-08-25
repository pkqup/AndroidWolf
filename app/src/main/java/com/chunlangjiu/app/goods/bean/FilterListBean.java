package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/25.
 * @Describe:
 */
public class FilterListBean {

    private List<FilterBrandBean> brand;
    private List<FilterStoreBean> cat;

    public List<FilterBrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<FilterBrandBean> brand) {
        this.brand = brand;
    }

    public List<FilterStoreBean> getCat() {
        return cat;
    }

    public void setCat(List<FilterStoreBean> cat) {
        this.cat = cat;
    }
}

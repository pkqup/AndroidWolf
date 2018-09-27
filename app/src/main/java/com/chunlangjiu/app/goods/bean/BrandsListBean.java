package com.chunlangjiu.app.goods.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class BrandsListBean {

    private List<BrandBean> brands;

    public List<BrandBean> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandBean> brands) {
        this.brands = brands;
    }

    public class BrandBean{

        private String brand_id;
        private String brand_name;

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
    }
}

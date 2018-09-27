package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class PriceBean {

    public PriceBean(String priceId,String minPrice,String maxPrice){
        this.priceId = priceId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    private String priceId;
    private String minPrice;
    private String maxPrice;

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}

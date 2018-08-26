package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class ShippingTypeBean {

    private String shop_id;
    private String type;

    public ShippingTypeBean(String shop_id,String type){
        this.shop_id = shop_id;
        this.type = type;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

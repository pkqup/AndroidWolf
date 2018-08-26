package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class MarkBean {

    private String shop_id;
    private String memo;

    public MarkBean(String shop_id,String memo){
        this.shop_id = shop_id;
        this.memo = memo;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

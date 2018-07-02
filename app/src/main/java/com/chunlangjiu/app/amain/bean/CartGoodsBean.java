package com.chunlangjiu.app.amain.bean;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class CartGoodsBean {

    public static final int ITEM_STORE = 1;
    public static final int ITEM_GOODS = 2;

    private int itemType;
    private String storeId;
    private String goodsId;
    private String goodsName;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}

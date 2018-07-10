package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/7/10
 * @Describe:
 */
public class OrderGoodsBean {

    public static final int ITEM_STORE = 1;
    public static final int ITEM_GOODS = 2;

    private int itemType;
    private String storeId;
    private String goodsId;
    private String goodsName;

    public static int getItemStore() {
        return ITEM_STORE;
    }

    public static int getItemGoods() {
        return ITEM_GOODS;
    }

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

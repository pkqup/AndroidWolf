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
    private String storeName;
    private String storeLogo;
    private boolean storeCheck;

    private String cart_id;
    private String item_id;
    private String sku_id;
    private String goodsPic;
    private String goodsName;
    private String goodsPrice;
    private String goodsNum;
    private boolean goodsCheck;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public boolean isStoreCheck() {
        return storeCheck;
    }

    public void setStoreCheck(boolean storeCheck) {
        this.storeCheck = storeCheck;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public boolean isGoodsCheck() {
        return goodsCheck;
    }

    public void setGoodsCheck(boolean goodsCheck) {
        this.goodsCheck = goodsCheck;
    }
}

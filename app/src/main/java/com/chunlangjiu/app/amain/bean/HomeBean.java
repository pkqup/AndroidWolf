package com.chunlangjiu.app.amain.bean;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe:
 */
public class HomeBean {

    public static final int ITEM_GOODS = 1;
    public static final int ITEM_JINGPAI = 2;
    public static final int ITEM_TUIJIAN = 3;

    private int itemType;

    private String item_id;
    private String title;
    private String price;
    private String mkt_price;
    private String label;
    private String image_default_id;
    private String imgsrc;

    private boolean isAuction;
    private String auction_starting_price;
    private String max_price;
    private String auction_end_time;

    public String getAuction_starting_price() {
        return auction_starting_price;
    }

    public void setAuction_starting_price(String auction_starting_price) {
        this.auction_starting_price = auction_starting_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getAuction_end_time() {
        return auction_end_time;
    }

    public void setAuction_end_time(String auction_end_time) {
        this.auction_end_time = auction_end_time;
    }

    public boolean isAuction() {
        return isAuction;
    }

    public void setAuction(boolean auction) {
        isAuction = auction;
    }

    public String getMkt_price() {
        return mkt_price;
    }

    public void setMkt_price(String mkt_price) {
        this.mkt_price = mkt_price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_default_id() {
        return image_default_id;
    }

    public void setImage_default_id(String image_default_id) {
        this.image_default_id = image_default_id;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}

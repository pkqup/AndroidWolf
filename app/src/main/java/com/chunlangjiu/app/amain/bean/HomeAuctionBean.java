package com.chunlangjiu.app.amain.bean;

/**
 * @CreatedbBy: liucun on 2018/9/28
 * @Describe:
 */
public class HomeAuctionBean {

    private String item_id;
    private String title;
    private String image_default_id;
    private String label;

    private String auction_starting_price;
    private String max_price;
    private String auction_end_time;
    private String auction_status;
    private String auction_number;

    public String getAuction_number() {
        return auction_number;
    }

    public void setAuction_number(String auction_number) {
        this.auction_number = auction_number;
    }

    public String getAuction_status() {
        return auction_status;
    }

    public void setAuction_status(String auction_status) {
        this.auction_status = auction_status;
    }

    public String getImage_default_id() {
        return image_default_id;
    }

    public void setImage_default_id(String image_default_id) {
        this.image_default_id = image_default_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
}

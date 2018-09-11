package com.chunlangjiu.app.amain.bean;

import java.io.Serializable;

/**
 * @CreatedbBy: liucun on 2018/9/9.
 * @Describe:
 */
public class AuctionBean implements Serializable {

    private String item_id;
    private String starting_price;
    private String status;
    private String store;
    private String number;
    private String begin_time;
    private String end_time;
    private ItemInfo item_info;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(String starting_price) {
        this.starting_price = starting_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public ItemInfo getItem_info() {
        return item_info;
    }

    public void setItem_info(ItemInfo item_info) {
        this.item_info = item_info;
    }


    public class ItemInfo implements Serializable{
        private String item_id;
        private String shop_id;
        private String cat_id;
        private String brand_id;
        private String shop_cat_id;
        private String title;
        private String sub_title;
        private String price;
        private String cost_price;
        private String mkt_price;
        private String weight;
        private String unit;
        private String image_default_id;
        private String list_image;

        public String getList_image() {
            return list_image;
        }

        public void setList_image(String list_image) {
            this.list_image = list_image;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getShop_cat_id() {
            return shop_cat_id;
        }

        public void setShop_cat_id(String shop_cat_id) {
            this.shop_cat_id = shop_cat_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public String getMkt_price() {
            return mkt_price;
        }

        public void setMkt_price(String mkt_price) {
            this.mkt_price = mkt_price;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getImage_default_id() {
            return image_default_id;
        }

        public void setImage_default_id(String image_default_id) {
            this.image_default_id = image_default_id;
        }
    }
}

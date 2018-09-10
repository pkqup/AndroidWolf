package com.chunlangjiu.app.amain.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/9.
 * @Describe:
 */
public class AuctionListBean {


    private List<AuctionBean> list;

    public class AuctionBean{
        private String item_id;
        private String title;
        private String image_default_id;
        private String price;
        private String sold_quantity;

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

        public String getImage_default_id() {
            return image_default_id;
        }

        public void setImage_default_id(String image_default_id) {
            this.image_default_id = image_default_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSold_quantity() {
            return sold_quantity;
        }

        public void setSold_quantity(String sold_quantity) {
            this.sold_quantity = sold_quantity;
        }
    }
}

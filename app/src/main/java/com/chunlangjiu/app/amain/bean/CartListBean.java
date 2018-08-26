package com.chunlangjiu.app.amain.bean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class CartListBean {

    private List<ShopInfo> cartlist;
    private TotalCart totalCart;
    private String nocheckedall;

    public List<ShopInfo> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<ShopInfo> cartlist) {
        this.cartlist = cartlist;
    }

    public TotalCart getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(TotalCart totalCart) {
        this.totalCart = totalCart;
    }

    public String getNocheckedall() {
        return nocheckedall;
    }

    public void setNocheckedall(String nocheckedall) {
        this.nocheckedall = nocheckedall;
    }

    public class ShopInfo {

        private String shop_id;
        private String shop_name;
        private String nocheckedall;
        private String shop_logo;
        private List<PromotionList> promotion_cartitems;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getNocheckedall() {
            return nocheckedall;
        }

        public void setNocheckedall(String nocheckedall) {
            this.nocheckedall = nocheckedall;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public List<PromotionList> getPromotion_cartitems() {
            return promotion_cartitems;
        }

        public void setPromotion_cartitems(List<PromotionList> promotion_cartitems) {
            this.promotion_cartitems = promotion_cartitems;
        }
    }

    public class PromotionList {
        private List<GoodsItem> cartitemlist;

        public List<GoodsItem> getCartitemlist() {
            return cartitemlist;
        }

        public void setCartitemlist(List<GoodsItem> cartitemlist) {
            this.cartitemlist = cartitemlist;
        }
    }

    public class GoodsItem {

        private String cart_id;
        private String item_id;
        private String sku_id;
        private String title;
        private String image_default_id;
        private String quantity;
        private String is_checked;
        private Price price;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
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

        public String getIs_checked() {
            return is_checked;
        }

        public void setIs_checked(String is_checked) {
            this.is_checked = is_checked;
        }

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }
    }


    public class Price {
        private String price;
        private String total_price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }
    }

    public class TotalCart {
        private String totalAfterDiscount;

        public String getTotalAfterDiscount() {
            return totalAfterDiscount;
        }

        public void setTotalAfterDiscount(String totalAfterDiscount) {
            this.totalAfterDiscount = totalAfterDiscount;
        }
    }


}

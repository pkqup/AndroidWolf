package com.chunlangjiu.app.goods.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/25.
 * @Describe:
 */
public class ConfirmOrderBean implements Serializable {

    private Address default_address;
    private ShopInfo cartInfo;
    private Total total;

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Address getDefault_address() {
        return default_address;
    }

    public void setDefault_address(Address default_address) {
        this.default_address = default_address;
    }

    public ShopInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(ShopInfo cartInfo) {
        this.cartInfo = cartInfo;
    }

    public class Total implements Serializable {
        private String allPostfee;//配送费
        private String allCostFee;//商品总额

        public String getAllCostFee() {
            return allCostFee;
        }

        public void setAllCostFee(String allCostFee) {
            this.allCostFee = allCostFee;
        }

        public String getAllPostfee() {
            return allPostfee;
        }

        public void setAllPostfee(String allPostfee) {
            this.allPostfee = allPostfee;
        }
    }

    public class Address implements Serializable {
        private String addr_id;
        private String user_id;
        private String name;
        private String area;
        private String addr;
        private String mobile;
        private String def_addr;
        private String area_id;

        public String getAddr_id() {
            return addr_id;
        }

        public void setAddr_id(String addr_id) {
            this.addr_id = addr_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDef_addr() {
            return def_addr;
        }

        public void setDef_addr(String def_addr) {
            this.def_addr = def_addr;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }

    public class ShopInfo implements Serializable {
        private List<CartData> resultCartData;

        public List<CartData> getResultCartData() {
            return resultCartData;
        }

        public void setResultCartData(List<CartData> resultCartData) {
            this.resultCartData = resultCartData;
        }
    }

    public class CartData implements Serializable {
        private String shop_id;
        private String shop_name;
        private String shop_logo;
        private List<GoodsItem> items;

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

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public List<GoodsItem> getItems() {
            return items;
        }

        public void setItems(List<GoodsItem> items) {
            this.items = items;
        }
    }

    public class GoodsItem implements Serializable {
        private String sku_id;
        private String quantity;
        private String image_default_id;
        private String title;
        private Price price;

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getImage_default_id() {
            return image_default_id;
        }

        public void setImage_default_id(String image_default_id) {
            this.image_default_id = image_default_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }
    }


    public class Price implements Serializable {
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

}

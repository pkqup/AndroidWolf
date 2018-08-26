package com.chunlangjiu.app.cart;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class UpdateCartGoodsBean {

    private String cart_id;
    private String is_checked;
    private String selected_promotion;
    private String totalQuantity;

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(String is_checked) {
        this.is_checked = is_checked;
    }

    public String getSelected_promotion() {
        return selected_promotion;
    }

    public void setSelected_promotion(String selected_promotion) {
        this.selected_promotion = selected_promotion;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}

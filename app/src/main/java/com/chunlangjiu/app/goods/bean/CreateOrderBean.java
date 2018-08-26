package com.chunlangjiu.app.goods.bean;

/**
 * @CreatedbBy: liucun on 2018/8/26.
 * @Describe:
 */
public class CreateOrderBean {

    private String payment_type;//支付方式
    private String payment_id;//支付单号

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
}

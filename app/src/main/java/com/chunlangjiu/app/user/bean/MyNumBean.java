package com.chunlangjiu.app.user.bean;

/**
 * @CreatedbBy: liucun on 2018/8/17.
 * @Describe:
 */
public class MyNumBean {

    private String wait_pay_num;//待支付订单数量
    private String wait_send_goods_num;//待发货订单数量
    private String wait_confirm_goods_num;//待确认收货订单数量
    private String canceled_num;//已取消订单数量
    private String notrate_num;//待评价订单数量

    private String money;//余额
    private String money_frozen;//冻结金额
    private String information;//消息

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney_frozen() {
        return money_frozen;
    }

    public void setMoney_frozen(String money_frozen) {
        this.money_frozen = money_frozen;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getWait_pay_num() {
        return wait_pay_num;
    }

    public void setWait_pay_num(String wait_pay_num) {
        this.wait_pay_num = wait_pay_num;
    }

    public String getWait_send_goods_num() {
        return wait_send_goods_num;
    }

    public void setWait_send_goods_num(String wait_send_goods_num) {
        this.wait_send_goods_num = wait_send_goods_num;
    }

    public String getWait_confirm_goods_num() {
        return wait_confirm_goods_num;
    }

    public void setWait_confirm_goods_num(String wait_confirm_goods_num) {
        this.wait_confirm_goods_num = wait_confirm_goods_num;
    }

    public String getCanceled_num() {
        return canceled_num;
    }

    public void setCanceled_num(String canceled_num) {
        this.canceled_num = canceled_num;
    }

    public String getNotrate_num() {
        return notrate_num;
    }

    public void setNotrate_num(String notrate_num) {
        this.notrate_num = notrate_num;
    }
}

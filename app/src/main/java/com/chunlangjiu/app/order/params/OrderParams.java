package com.chunlangjiu.app.order.params;

public class OrderParams {
    public static final String TYPE = "type";//类型 买家： 0:我的订单 1:竞品订单 2：售后订单  卖家： 3：订单管理 4：售后订单管理
    public static final String TARGET = "target";//定位标签页下标
    public static final String ORDERID = "orderId";//订单id
    public static final String OID = "oid";//子订单id
    public static final String AFTERSALESBN = "aftersales_bn";
    public static final String PRODUCTS = "products";//传递商品的key
    public static final String AUCTIONITEMID = "auctionitemId";

    public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";//等待付款
    public static final String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";//待发货
    public static final String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";//等待确认收货
    public static final String WAIT_RATE = "WAIT_RATE";//待评价
    public static final String TRADE_FINISHED = "TRADE_FINISHED";//已完成
    public static final String TRADE_CLOSED_BY_SYSTEM = "TRADE_CLOSED_BY_SYSTEM";//已关闭

    public static final String REFRESH_ORDER_LIST = "REFRESH_ORDER_LIST";//刷新订单列表
    public static final String REFRESH_ORDER_DETAIL = "REFRESH_ORDER_DETAIL";//刷新订单详情
}

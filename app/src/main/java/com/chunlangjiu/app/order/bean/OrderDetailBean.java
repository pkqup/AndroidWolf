package com.chunlangjiu.app.order.bean;

import java.util.List;

public class OrderDetailBean {


    /**
     * tid : 2433612000060086
     * shipping_type : express
     * status : WAIT_BUYER_PAY
     * payment : 4497.900
     * points_fee : 0.000
     * cancel_status : NO_APPLY_CANCEL
     * hongbao_fee : 0.000
     * post_fee : 4497.000
     * pay_type : online
     * payed_fee : 0.000
     * pay_time : null
     * receiver_state : 广东省
     * receiver_city : 深圳市
     * receiver_district : 南山区
     * receiver_address : 1227474
     * trade_memo :
     * receiver_name : 环绕
     * receiver_mobile : 18682330720
     * ziti_addr : null
     * ziti_memo : null
     * total_fee : 0.900
     * discount_fee : 0.000
     * buyer_rate : 0
     * adjust_fee : 0.000
     * created_time : 1535613568
     * shop_id : 1
     * need_invoice : 0
     * invoice_name :
     * invoice_type : null
     * invoice_main :
     * invoice_vat_main :
     * cancel_reason : null
     * cancelInfo : []
     * refund_fee : 0
     * orders : [{"price":"0.100","aftersales_status":null,"num":3,"title":"皇家礼炮","item_id":175,"cat_id":33,"end_time":null,"status":"WAIT_BUYER_PAY","pic_path":"http://mall.chunlangjiu.com/images/1e/fc/29/56e3751ced12cf94f44968c1af3f34accb5e7405.JPG_t.JPG","total_fee":"0.300","adjust_fee":"0.000","spec_nature_info":"颜色：紫色、尺码：m","gift_data":null,"complaints_status":"NOT_COMPLAINTS","buyer_rate":0,"oid":2433612000080086},{"price":"0.100","aftersales_status":null,"num":6,"title":"拉图1974","item_id":174,"cat_id":33,"end_time":null,"status":"WAIT_BUYER_PAY","pic_path":"http://mall.chunlangjiu.com/images/01/c5/84/3cad34fbb4ce6869f4b8a9799759f4a444384003.JPG_t.JPG","total_fee":"0.600","adjust_fee":"0.000","spec_nature_info":"颜色：红色、尺码：xxl","gift_data":null,"complaints_status":"NOT_COMPLAINTS","buyer_rate":0,"oid":2433612000120086}]
     * shipping_type_name : 快递
     * status_desc : 待付款
     * is_buyer_rate : false
     * shopname : GAP官方旗舰店
     */

    private long tid;
    private String shipping_type;
    private String status;
    private String payment;
    private String points_fee;
    private String cancel_status;
    private String hongbao_fee;
    private String post_fee;
    private String pay_type;
    private String payed_fee;
    private Object pay_time;
    private String receiver_state;
    private String receiver_city;
    private String receiver_district;
    private String receiver_address;
    private String trade_memo;
    private String receiver_name;
    private String receiver_mobile;
    private Object ziti_addr;
    private Object ziti_memo;
    private String total_fee;
    private String discount_fee;
    private int buyer_rate;
    private String adjust_fee;
    private int created_time;
    private int shop_id;
    private int need_invoice;
    private String invoice_name;
    private Object invoice_type;
    private String invoice_main;
    private String invoice_vat_main;
    private String cancel_reason;
    private int refund_fee;
    private String shipping_type_name;
    private String status_desc;
    private boolean is_buyer_rate;
    private String shopname;
    private boolean logi;
    private CancelInfoBean cancelInfo;
    private List<OrdersBean> orders;

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPoints_fee() {
        return points_fee;
    }

    public void setPoints_fee(String points_fee) {
        this.points_fee = points_fee;
    }

    public String getCancel_status() {
        return cancel_status;
    }

    public void setCancel_status(String cancel_status) {
        this.cancel_status = cancel_status;
    }

    public String getHongbao_fee() {
        return hongbao_fee;
    }

    public void setHongbao_fee(String hongbao_fee) {
        this.hongbao_fee = hongbao_fee;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPayed_fee() {
        return payed_fee;
    }

    public void setPayed_fee(String payed_fee) {
        this.payed_fee = payed_fee;
    }

    public Object getPay_time() {
        return pay_time;
    }

    public void setPay_time(Object pay_time) {
        this.pay_time = pay_time;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getTrade_memo() {
        return trade_memo;
    }

    public void setTrade_memo(String trade_memo) {
        this.trade_memo = trade_memo;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public Object getZiti_addr() {
        return ziti_addr;
    }

    public void setZiti_addr(Object ziti_addr) {
        this.ziti_addr = ziti_addr;
    }

    public Object getZiti_memo() {
        return ziti_memo;
    }

    public void setZiti_memo(Object ziti_memo) {
        this.ziti_memo = ziti_memo;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public int getBuyer_rate() {
        return buyer_rate;
    }

    public void setBuyer_rate(int buyer_rate) {
        this.buyer_rate = buyer_rate;
    }

    public String getAdjust_fee() {
        return adjust_fee;
    }

    public void setAdjust_fee(String adjust_fee) {
        this.adjust_fee = adjust_fee;
    }

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getNeed_invoice() {
        return need_invoice;
    }

    public void setNeed_invoice(int need_invoice) {
        this.need_invoice = need_invoice;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public Object getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(Object invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getInvoice_main() {
        return invoice_main;
    }

    public void setInvoice_main(String invoice_main) {
        this.invoice_main = invoice_main;
    }

    public String getInvoice_vat_main() {
        return invoice_vat_main;
    }

    public void setInvoice_vat_main(String invoice_vat_main) {
        this.invoice_vat_main = invoice_vat_main;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getShipping_type_name() {
        return shipping_type_name;
    }

    public void setShipping_type_name(String shipping_type_name) {
        this.shipping_type_name = shipping_type_name;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public boolean isIs_buyer_rate() {
        return is_buyer_rate;
    }

    public void setIs_buyer_rate(boolean is_buyer_rate) {
        this.is_buyer_rate = is_buyer_rate;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public boolean isLogi() {
        return logi;
    }

    public void setLogi(boolean logi) {
        this.logi = logi;
    }

    public CancelInfoBean getCancelInfo() {
        return cancelInfo;
    }

    public void setCancelInfo(CancelInfoBean cancelInfo) {
        this.cancelInfo = cancelInfo;
    }

    public static class CancelInfoBean {
        /**
         * cancel_id : 484
         */

        private int cancel_id;

        public int getCancel_id() {
            return cancel_id;
        }

        public void setCancel_id(int cancel_id) {
            this.cancel_id = cancel_id;
        }
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * price : 0.100
         * aftersales_status : null
         * num : 3
         * title : 皇家礼炮
         * item_id : 175
         * cat_id : 33
         * end_time : null
         * status : WAIT_BUYER_PAY
         * pic_path : http://mall.chunlangjiu.com/images/1e/fc/29/56e3751ced12cf94f44968c1af3f34accb5e7405.JPG_t.JPG
         * total_fee : 0.300
         * adjust_fee : 0.000
         * spec_nature_info : 颜色：紫色、尺码：m
         * gift_data : null
         * complaints_status : NOT_COMPLAINTS
         * buyer_rate : 0
         * oid : 2433612000080086
         */

        private String price;
        private Object aftersales_status;
        private int num;
        private String title;
        private int item_id;
        private int cat_id;
        private Object end_time;
        private String status;
        private String pic_path;
        private String total_fee;
        private String adjust_fee;
        private String spec_nature_info;
        private Object gift_data;
        private String complaints_status;
        private int buyer_rate;
        private long oid;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getAftersales_status() {
            return aftersales_status;
        }

        public void setAftersales_status(Object aftersales_status) {
            this.aftersales_status = aftersales_status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public int getCat_id() {
            return cat_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public Object getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Object end_time) {
            this.end_time = end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getAdjust_fee() {
            return adjust_fee;
        }

        public void setAdjust_fee(String adjust_fee) {
            this.adjust_fee = adjust_fee;
        }

        public String getSpec_nature_info() {
            return spec_nature_info;
        }

        public void setSpec_nature_info(String spec_nature_info) {
            this.spec_nature_info = spec_nature_info;
        }

        public Object getGift_data() {
            return gift_data;
        }

        public void setGift_data(Object gift_data) {
            this.gift_data = gift_data;
        }

        public String getComplaints_status() {
            return complaints_status;
        }

        public void setComplaints_status(String complaints_status) {
            this.complaints_status = complaints_status;
        }

        public int getBuyer_rate() {
            return buyer_rate;
        }

        public void setBuyer_rate(int buyer_rate) {
            this.buyer_rate = buyer_rate;
        }

        public long getOid() {
            return oid;
        }

        public void setOid(long oid) {
            this.oid = oid;
        }
    }
}

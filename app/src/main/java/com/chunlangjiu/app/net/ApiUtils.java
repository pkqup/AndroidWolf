package com.chunlangjiu.app.net;

import com.chunlangjiu.app.amain.bean.CartCountBean;
import com.chunlangjiu.app.amain.bean.CartListBean;
import com.chunlangjiu.app.amain.bean.HomeListBean;
import com.chunlangjiu.app.amain.bean.HomeModulesBean;
import com.chunlangjiu.app.amain.bean.LoginBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.goods.bean.ConfirmOrderBean;
import com.chunlangjiu.app.goods.bean.CreateOrderBean;
import com.chunlangjiu.app.goods.bean.EvaluateListBean;
import com.chunlangjiu.app.goods.bean.FilterListBean;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
import com.chunlangjiu.app.goods.bean.PaymentBean;
import com.chunlangjiu.app.goods.bean.ShopInfoBean;
import com.chunlangjiu.app.order.bean.CancelOrderResultBean;
import com.chunlangjiu.app.order.bean.CancelReasonBean;
import com.chunlangjiu.app.order.bean.OrderAfterSaleReasonBean;
import com.chunlangjiu.app.order.bean.OrderDetailBean;
import com.chunlangjiu.app.store.bean.StoreClassListBean;
import com.chunlangjiu.app.store.bean.StoreDetailBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.user.bean.AddressListBean;
import com.chunlangjiu.app.user.bean.BrandListBean;
import com.chunlangjiu.app.user.bean.MyNumBean;
import com.chunlangjiu.app.user.bean.ShopClassList;
import com.chunlangjiu.app.user.bean.UploadImageBean;
import com.pkqup.commonlibrary.net.HttpUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import org.json.JSONArray;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class ApiUtils {

    private volatile static ApiUtils mInstance;
    private ApiService apiService;

    public static ApiUtils getInstance() {
        if (mInstance == null) {
            synchronized (ApiUtils.class) {
                if (mInstance == null) {
                    mInstance = new ApiUtils();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        apiService = HttpUtils.getInstance().getRetrofit().create(ApiService.class);
    }

    public ApiService getApiService() {
        if (null == apiService) {
            apiService = HttpUtils.getInstance().getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    public Flowable<ResultBean> getAuthSms(String mobile) {
        return apiService.getAuthSms("user.sendSms", "v1", mobile);
    }

    public Flowable<ResultBean<LoginBean>> login(String account, String code) {
        return apiService.login("user.oauthlogin", "v2", account, code);
    }

    public Flowable<ResultBean<LoginBean>> shopLogin(String mobile, String password) {
        return apiService.shopLogin("user.login", "v1", mobile, password);
    }

    public Flowable<ResultBean<MainClassBean>> getMainClass() {
        return apiService.getGoodsClass("category.itemCategory", "v1");
    }

    public Flowable<ResultBean<GoodsListBean>> getGoodsList(String cat_id, int page_no, String orderBy, String search_keywords, String shop_id) {
        return apiService.getGoodsList("item.search", "v1", cat_id, page_no, 20, orderBy, search_keywords, shop_id);
    }

    public Flowable<ResultBean<GoodsDetailBean>> getGoodsDetail(String item_id) {
        return apiService.getGoodsDetail("item.detail", "v1", item_id);
    }

    public Flowable<ResultBean<FilterListBean>> getFilterData(String cat_id) {
        return apiService.getFilterData("item.filterItems", "v1", cat_id);
    }


    public Flowable<ResultBean<EvaluateListBean>> getEvaluateList(String item_id, int page_no) {
        return apiService.getEvaluateList("item.rate.list", "v1", 0, item_id, page_no, 10);
    }

    public Flowable<ResultBean<ShopInfoBean>> getShopInfo(String shopId) {
        return apiService.getShopInfo("shop.basic", "v1", shopId);
    }


    public Flowable<ResultBean> newAddress(String name, String mobile, String area, String addr, String def_addr) {
        return apiService.newAddress("member.address.create", "v1", name, mobile, area, addr, def_addr);
    }

    public Flowable<ResultBean<AddressListBean>> getAddressList() {
        return apiService.getAddressList("member.address.list", "v1");
    }

    public Flowable<ResultBean> editAddress(String name, String mobile, String area, String addr, String def_addr, String addressId) {
        return apiService.editAddress("member.address.update", "v1", name, mobile, area, addr, def_addr, addressId);
    }

    public Flowable<ResultBean> deleteAddress(String addressId) {
        return apiService.deleteAddress("member.address.delete", "v1", addressId);
    }

    public Flowable<ResultBean> setDefault(String addressId) {
        return apiService.setDefault("member.address.setDefault", "v1", addressId);
    }

    //获取我的模块的数量上标
    public Flowable<ResultBean<MyNumBean>> getMyNumFlag(String addressId) {
        return apiService.getMyNumFlag("member.index", "v1");
    }

    public Flowable<ResultBean<StoreClassListBean>> getStoreClass() {
        return apiService.getStoreClass("category.chateauCat", "v1");
    }

    public Flowable<ResultBean> getStoreList(String id, int pageNum) {
        return apiService.getStoreList("category.chateauList", "v1", id, pageNum, 20);
    }

    public Flowable<ResultBean<StoreDetailBean>> getStoreDetail(String id) {
        return apiService.getStoreDetail("category.chateauDetail", "v1", id);
    }


    public Flowable<ResultBean<CartListBean>> getCartList() {
        return apiService.getCartList("cart.get", "v1", "cart", "wap");
    }

    public Flowable<ResultBean> addGoodsToCart(int num, String sku_id) {
        return apiService.addGoodsToCart("cart.add", "v1", num, sku_id, "item", "cart");
    }

    public Flowable<ResultBean> addGoodsToCartBuyNow(int num, String sku_id) {
        return apiService.addGoodsToCart("cart.add", "v1", num, sku_id, "item", "fastbuy");
    }

    public Flowable<ResultBean<ConfirmOrderBean>> buyNowConfirmOrder() {
        return apiService.confirmOrder("cart.checkout", "v1", "fastbuy");
    }

    public Flowable<ResultBean<ConfirmOrderBean>> cartConfirmOrder() {
        return apiService.confirmOrder("cart.checkout", "v1", "cart");
    }

    public Flowable<ResultBean> deleteCartItem(String cart_id) {
        return apiService.deleteCartItem("cart.del", "v1", cart_id, "cart");
    }

    //cart_params: 	[{"cart_id":1,"is_checked":1,"selected_promotion":13,"totalQuantity":2},{"cart_id":7,"is_checked":1,"selected_promotion":0,"totalQuantity":1}]
    public Flowable<ResultBean> updateCartData(String cart_params) {
        return apiService.updateCartData("cart.update", "v1", "item", cart_params);
    }

    public Flowable<ResultBean<CartCountBean>> getCartCount() {
        return apiService.getCartCount("cart.count", "v1");
    }

    public Flowable<ResultBean<PaymentBean>> getPayment() {
        return apiService.getPayment("payment.pay.paycenter", "v1");
    }

    public Flowable<ResultBean<CreateOrderBean>> createOrder(String mode, String md5, String addrId, String paymentId, String shippingType,
                                                             String mark) {
        return apiService.createOrder("trade.create", "v1", mode, md5, addrId, paymentId, "app",
                shippingType, mark, "notuse", "", "");
    }

    public Flowable<ResultBean> payDo(String payment_id, String payment_type) {
        return apiService.payDo("payment.pay.do", "v1", payment_id, payment_type);
    }

    public Flowable<ResultBean<HomeModulesBean>> getHomeModules() {
        return apiService.getHomeModules("theme.modules", "v1", "index");
    }

    public Flowable<ResultBean<HomeListBean>> getHomeLists(int pageNo) {
        return apiService.getHomeLists("theme.pull.goods", "v1", "index", pageNo, 10);
    }

    // image_type —— complaints 用户投诉商家图片, aftersales售后图片, rate 评价图片
    public Observable<ResultBean<UploadImageBean>> userUploadImage(String imgBase64, String imageName, String image_type) {
        return apiService.userUploadImage("image.upload", "v1", "base64", imgBase64, imageName, image_type);
    }

    public Observable<ResultBean<UploadImageBean>> shopUploadImage(String imgBase64, String imageName) {
        return apiService.shopUploadImage("image.upload", "v1", "base64", imgBase64, imageName, "item", "0");
    }

    public Flowable<ResultBean<ShopClassList>> getShopClassList() {
        return apiService.getShopClassList("category.platform.get", "v1");
    }

    public Flowable<ResultBean<BrandListBean>> getShopBrandList(String class_id) {
        return apiService.getShopBrandList("category.platform.brand.get", "v1", class_id);
    }

    public Observable<ResultBean> addGoods(String cat_id, String brand_id, String shop_cat_id, String title, String sub_title, String weight, String list_image,
                                           String price, String dlytmpl_id, String sku, String wap_desc) {
        return apiService.addGoods("item.create", "v1", cat_id, brand_id, shop_cat_id, title, sub_title, weight, list_image, price, dlytmpl_id, sku, wap_desc, "1");
    }

    public Flowable<ResultBean<OrderListBean>> getOrderLists(String status, int pageNo) {
        return apiService.getOrderLists("trade.list", "v1", status, pageNo, 10);
    }

    public Flowable<ResultBean<OrderListBean>> getAfterSaleOrderList(int pageNo) {
        return apiService.getAfterSaleOrderList("member.aftersales.list", "v1", pageNo, 10);
    }

    public Flowable<ResultBean<OrderDetailBean>> getOrderDetail(String tid) {
        return apiService.getOrderDetail("trade.get", "v1", tid);
    }

    public Flowable<ResultBean<OrderDetailBean>> getAfterSaleOrderDetail(String aftersales_bn, String oid) {
        return apiService.getAfterSaleOrderDetail("member.aftersales.get", "v1", aftersales_bn, oid);
    }

    public Flowable<ResultBean> confirmReceipt(String tid) {
        return apiService.confirmReceipt("trade.confirm", "v1", tid);
    }

    public Flowable<ResultBean<CancelReasonBean>> getCancelReason() {
        return apiService.getCancelReason("trade.cancel.reason.get", "v1");
    }

    public Flowable<ResultBean<CancelOrderResultBean>> cancelOrder(String tid, String reason) {
        return apiService.cancelOrder("trade.cancel.create", "v1", tid, reason);
    }

    public Flowable<ResultBean> addRate(String tid, JSONArray rateData, boolean anony, int tallyScore, int attitudeScore, int deliverySpeedScore) {
        return apiService.addRate("member.rate.add", "v1", tid, rateData, anony, tallyScore, attitudeScore, deliverySpeedScore);
    }

    public Flowable<ResultBean<OrderAfterSaleReasonBean>> getAfterSaleReason(String oid) {
        return apiService.getAfterSaleReason("member.aftersales.applyInfo.get", "v1", oid);
    }

    public Flowable<ResultBean> applyAfterSaleReason(String tid, String oid, String reason, String description, String evidence_pic) {
        return apiService.applyAfterSaleReason("member.aftersales.apply", "v1", tid, oid, reason, description, "REFUND_GOODS", evidence_pic);
    }


}

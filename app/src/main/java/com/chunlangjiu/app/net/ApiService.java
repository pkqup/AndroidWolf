package com.chunlangjiu.app.net;

import com.chunlangjiu.app.amain.bean.AuctionBean;
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
import com.pkqup.commonlibrary.net.bean.ResultBean;

import org.json.JSONArray;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe: 请求接口
 */
public interface ApiService {


    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> getAuthSms(@Field("method") String method, @Field("v") String v, @Field("mobile") String mobile);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<LoginBean>> login(@Field("method") String method, @Field("v") String v, @Field("account") String mobile, @Field("verifycode") String verifycode);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<LoginBean>> shopLogin(@Field("method") String method, @Field("v") String v,
                                              @Field("account") String account, @Field("password") String password);


    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<List<AuctionBean>>> getAuctionList(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> auctionGivePrice(@Field("method") String method, @Field("v") String v,
                                          @Field("item_id") String item_id, @Field("price") String price);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<MainClassBean>> getGoodsClass(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<GoodsListBean>> getGoodsList(@Field("method") String method, @Field("v") String v, @Field("cat_id") String cat_id,
                                                     @Field("page_no") int page_no, @Field("page_size") int page_size,
                                                     @Field("orderBy") String orderBy, @Field("search_keywords") String search_keywords,
                                                     @Field("shop_id") String shop_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<FilterListBean>> getFilterData(@Field("method") String method, @Field("v") String v, @Field("cat_id") String cat_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<GoodsDetailBean>> getGoodsDetail(@Field("method") String method, @Field("v") String v,
                                                         @Field("item_id") String item_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<EvaluateListBean>> getEvaluateList(@Field("method") String method, @Field("v") String v,
                                                           @Field("rate_type") int rate_type, @Field("item_id") String item_id,
                                                           @Field("page_no") int page_no, @Field("page_size") int page_size);


    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<ShopInfoBean>> getShopInfo(@Field("method") String method, @Field("v") String v,
                                                   @Field("shop_id") String shop_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<AddressListBean>> getAddressList(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> newAddress(@Field("method") String method, @Field("v") String v,
                                    @Field("name") String name, @Field("mobile") String mobile,
                                    @Field("area") String area, @Field("addr") String addr, @Field("def_addr") String def_addr);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> editAddress(@Field("method") String method, @Field("v") String v,
                                     @Field("name") String name, @Field("mobile") String mobile,
                                     @Field("area") String area, @Field("addr") String addr,
                                     @Field("def_addr") String def_addr, @Field("addr_id") String addr_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> deleteAddress(@Field("method") String method, @Field("v") String v, @Field("addr_id") String addr_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> setDefault(@Field("method") String method, @Field("v") String v, @Field("addr_id") String addr_id);


    //获取我的页面数据个数的统计
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<MyNumBean>> getMyNumFlag(@Field("method") String method, @Field("v") String v);


    //获取名庄分类
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<StoreClassListBean>> getStoreClass(@Field("method") String method, @Field("v") String v);

    //获取名庄对应分类下的列表
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> getStoreList(@Field("method") String method, @Field("v") String v,
                                      @Field("chateaucat_id") String chateaucat_id,
                                      @Field("page_no") int page_no, @Field("page_size") int page_size);

    //获取名庄详情
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<StoreDetailBean>> getStoreDetail(@Field("method") String method, @Field("v") String v, @Field("chateau_id") String chateau_id);

    //获取购物车列表
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CartListBean>> getCartList(@Field("method") String method, @Field("v") String v,
                                                   @Field("mode") String mode, @Field("platform") String platform);


    //添加商品到购物车
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> addGoodsToCart(@Field("method") String method, @Field("v") String v, @Field("quantity") int quantity,
                                        @Field("sku_id") String sku_id, @Field("obj_type") String obj_type, @Field("mode") String mode);


    //删除购物车商品 cart_id：购物车id,多个数据用逗号隔开
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> deleteCartItem(@Field("method") String method, @Field("v") String v,
                                        @Field("cart_id") String cart_id, @Field("mode") String mode);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<ConfirmOrderBean>> confirmOrder(@Field("method") String method, @Field("v") String v, @Field("mode") String mode);

    //更新购物车数据
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> updateCartData(@Field("method") String method, @Field("v") String v,
                                        @Field("obj_type") String obj_type, @Field("cart_params") String cart_params);

    //获取购物车数量
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CartCountBean>> getCartCount(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<PaymentBean>> getPayment(@Field("method") String method, @Field("v") String v);

    //创建订单
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CreateOrderBean>> createOrder(@Field("method") String method, @Field("v") String v,
                                                      @Field("mode") String mode, @Field("md5_cart_info") String md5_cart_info,
                                                      @Field("addr_id") String addr_id, @Field("payment_type") String payment_type,
                                                      @Field("source_from") String source_from, @Field("shipping_type") String shipping_type,
                                                      @Field("mark") String mark, @Field("invoice_type") String invoice_type,
                                                      @Field("invoice_content") String invoice_content, @Field("use_points") String use_points);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> payDo(@Field("method") String method, @Field("v") String v,
                               @Field("payment_id") String payment_id, @Field("pay_app_id") String pay_app_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<HomeModulesBean>> getHomeModules(@Field("method") String method, @Field("v") String v,
                                                         @Field("tmpl") String payment_id);


    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<HomeListBean>> getHomeLists(@Field("method") String method, @Field("v") String v,
                                                    @Field("tmpl") String payment_id, @Field("page_no") int page_no,
                                                    @Field("pagesize") int pagesize);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Observable<ResultBean<UploadImageBean>> userUploadImage(@Field("method") String method, @Field("v") String v,
                                                            @Field("upload_type") String upload_type, @Field("image") String image,
                                                            @Field("image_input_title") String image_input_title,
                                                            @Field("image_type") String image_type);

    @POST("index.php/shop/topapi")
    @FormUrlEncoded
    Observable<ResultBean<UploadImageBean>> shopUploadImage(@Field("method") String method, @Field("v") String v,
                                                            @Field("upload_type") String upload_type, @Field("image") String image,
                                                            @Field("image_input_title") String image_input_title,
                                                            @Field("image_type") String image_type, @Field("image_cat_id") String image_cat_id);

    @POST("index.php/shop/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<ShopClassList>> getShopClassList(@Field("method") String method, @Field("v") String v);

    @POST("index.php/shop/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<BrandListBean>> getShopBrandList(@Field("method") String method, @Field("v") String v, @Field("cat_id") String cat_id);

    //添加商品
    @POST("index.php/shop/topapi")
    @FormUrlEncoded
    Observable<ResultBean> addGoods(@Field("method") String method, @Field("v") String v,
                                    @Field("cat_id") String cat_id, @Field("brand_id") String brand_id,
                                    @Field("shop_cat_id") String shop_cat_id,
                                    @Field("title") String title, @Field("sub_title") String sub_title,
                                    @Field("weight") String weight, @Field("list_image") String list_image, @Field("price") String price,
                                    @Field("dlytmpl_id") String dlytmpl_id, @Field("sku") String sku,
                                    @Field("wap_desc") String wap_desc, @Field("nospec") String nospec);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<OrderListBean>> getOrderLists(@Field("method") String method, @Field("v") String v,
                                                      @Field("status") String status, @Field("page_no") int page_no,
                                                      @Field("pagesize") int pagesize);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<OrderListBean>> getAfterSaleOrderList(@Field("method") String method, @Field("v") String v,
                                                              @Field("page_no") int page_no, @Field("pagesize") int pagesize);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<OrderDetailBean>> getOrderDetail(@Field("method") String method, @Field("v") String v,
                                                         @Field("tid") String tid);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<OrderDetailBean>> getAfterSaleOrderDetail(@Field("method") String method, @Field("v") String v,
                                                                  @Field("aftersales_bn") String aftersales_bn, @Field("oid") String oid);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> confirmReceipt(@Field("method") String method, @Field("v") String v,
                                        @Field("tid") String tid);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CancelReasonBean>> getCancelReason(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CancelOrderResultBean>> cancelOrder(@Field("method") String method, @Field("v") String v,
                                                            @Field("tid") String tid, @Field("cancel_reason") String reason);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> addRate(@Field("method") String method, @Field("v") String v,
                                 @Field("tid") String tid, @Field("rate_data") JSONArray rateData,
                                 @Field("anony") boolean anony, @Field("tally_score") int tallyScore,
                                 @Field("attitude_score") int attitudeScore, @Field("delivery_speed_score") int deliverySpeedScore);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<OrderAfterSaleReasonBean>> getAfterSaleReason(@Field("method") String method, @Field("v") String v, @Field("oid") String oid);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> applyAfterSaleReason(@Field("method") String method, @Field("v") String v,
                                              @Field("tid") String tid, @Field("oid") String oid,
                                              @Field("reason") String reason, @Field("description") String description,
                                              @Field("aftersales_type") String aftersales_type, @Field("evidence_pic") String evidence_pic);
}

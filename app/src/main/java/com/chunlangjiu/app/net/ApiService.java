package com.chunlangjiu.app.net;

import com.chunlangjiu.app.amain.bean.CartCountBean;
import com.chunlangjiu.app.amain.bean.LoginBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.goods.bean.ConfirmOrderBean;
import com.chunlangjiu.app.goods.bean.EvaluateListBean;
import com.chunlangjiu.app.goods.bean.FilterListBean;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
import com.chunlangjiu.app.goods.bean.ShopInfoBean;
import com.chunlangjiu.app.user.bean.AddressListBean;
import com.chunlangjiu.app.user.bean.MyNumBean;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import io.reactivex.Flowable;
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
    Flowable<ResultBean<MainClassBean>> getGoodsClass(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<GoodsListBean>> getGoodsList(@Field("method") String method, @Field("v") String v, @Field("cat_id") String cat_id,
                                                     @Field("page_no") int page_no, @Field("page_size") int page_size,
                                                     @Field("orderBy") String orderBy);

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
    Flowable<ResultBean> getStoreClass(@Field("method") String method, @Field("v") String v);

    //获取名庄对应分类下的列表
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> getStoreList(@Field("method") String method, @Field("v") String v,
                                      @Field("chateaucat_id") String chateaucat_id,
                                      @Field("page_no") int page_no, @Field("page_size") int page_size);

    //获取名庄详情
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> getStoreDetail(@Field("method") String method, @Field("v") String v, @Field("chateau_id") String chateau_id);

    //获取购物车列表
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> getCartList(@Field("method") String method, @Field("v") String v,
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
                                        @Field("cart_id") String cart_id);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<ConfirmOrderBean>> confirmOrder(@Field("method") String method, @Field("v") String v, @Field("mode") String mode);

    //更新购物车数据
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean> updateCartData(@Field("method") String method, @Field("v") String v,
                                        @Field("obj_type") String obj_type,  @Field("cart_params") String cart_params);

    //获取购物车数量
    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<CartCountBean>> getCartCount(@Field("method") String method, @Field("v") String v);
}

package com.chunlangjiu.app.net;

import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
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

    @POST(".")
    @FormUrlEncoded
    Flowable<ResultBean> getAddressList(@Field("field") String field);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<MainClassBean>> getClass(@Field("method") String method, @Field("v") String v);

    @POST("index.php/topapi")
    @FormUrlEncoded
    Flowable<ResultBean<GoodsListBean>> getGoodsList(@Field("method") String method, @Field("v") String v, @Field("cat_id") String cat_id,
                                                     @Field("page_no") int page_no, @Field("page_size") int page_size,
                                                     @Field("orderBy") String orderBy);


}

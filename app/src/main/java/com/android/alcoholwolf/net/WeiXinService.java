package com.android.alcoholwolf.net;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LiuCun on 2017/12/8.<br>
 * Describe
 */

public interface WeiXinService {

    @GET("wxnew")
    Observable<WeiXinNewsBean> getWeixinHorList(@Query("key") String apikey,
                                                @Query("num") int num, @Query("page") int page, @Body User user);

    @POST("wxnew")
    Observable<WeiXinNewsBean> getPost(@Body User user);
}

package com.chunlangjiu.app.net;

import com.chunlangjiu.app.amain.bean.LoginBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
import com.pkqup.commonlibrary.net.HttpUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import io.reactivex.Flowable;

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

    public Flowable<ResultBean<MainClassBean>> getMainClass() {
        return apiService.getClass("category.itemCategory", "v1");
    }

    public Flowable<ResultBean<GoodsListBean>> getGoodsList(String cat_id, int page_no, String orderBy) {
        return apiService.getGoodsList("item.search", "v1", cat_id, page_no, 20, orderBy);
    }


    public Flowable<ResultBean> getAuthSms(String mobile) {
        return apiService.getAuthSms("user.sendSms", "v1", mobile);
    }

    public Flowable<ResultBean<LoginBean>> login(String mobile, String code) {
        return apiService.login("user.oauthlogin", "v2", mobile, code);
    }
}

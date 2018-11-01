package com.chunlangjiu.app.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * @CreatedbBy: liucun on 2018/10/22
 * @Describe:
 */
public class UmengEventUtil {

    public static final String INDEX_HOME = "index";
    public static final String INDEX_ALL = "all";
    public static final String INDEX_AUCTION = "auction";
    public static final String INDEX_SHOP_CART = "shopCart";
    public static final String INDEX_MIME = "mine";

    public static final String BANNER = "banner";
    public static final String ICON = "icon";
    public static final String BRAND = "brand";//品牌推荐
    public static final String RECOMMEND = "recommend";//好酒推荐


    public static final String SEARCH_CATEGORY = "searchCategory";
    public static final String SEARCH_BRAND = "searchBrand";
    public static final String SEARCH_PLACE = "searchPlace";
    public static final String SEARCH_TYPE = "searchType";
    public static final String SEARCH_ALCOHOL_DEGREE = "searchAlcoholDegree";
    public static final String SEARCH_PRICE = "searchPrice";

    public static void homeEvent(Context context) {
        MobclickAgent.onEvent(context, INDEX_HOME);
    }

    public static void allEvent(Context context) {
        MobclickAgent.onEvent(context, INDEX_ALL);
    }

    public static void auctionEvent(Context context) {
        MobclickAgent.onEvent(context, INDEX_AUCTION);
    }

    public static void shopCartEvent(Context context) {
        MobclickAgent.onEvent(context, INDEX_SHOP_CART);
    }

    public static void mimeEvent(Context context) {
        MobclickAgent.onEvent(context, INDEX_MIME);
    }


    public static void bannerEvent(Context context, String label) {
        MobclickAgent.onEvent(context, BANNER, label);
    }

    public static void iconEvent(Context context, String label) {
        MobclickAgent.onEvent(context, ICON, label);
    }

    public static void brandEvent(Context context, String label) {
        MobclickAgent.onEvent(context, BRAND, label);
    }

    public static void recommendEvent(Context context, String label) {
        MobclickAgent.onEvent(context, RECOMMEND, label);
    }


    public static void search_category(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_CATEGORY, label);
    }

    public static void search_brand(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_BRAND, label);
    }
    public static void search_place(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_PLACE, label);
    }

    public static void search_type(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_TYPE, label);
    }
    public static void search_alc(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_ALCOHOL_DEGREE, label);
    }

    public static void search_price(Context context, String label) {
        MobclickAgent.onEvent(context, SEARCH_PRICE, label);
    }

}

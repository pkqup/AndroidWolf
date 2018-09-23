package com.chunlangjiu.app.web;

import android.webkit.JavascriptInterface;

/**
 * @CreatedbBy: liucun on 2018/9/23.
 * @Describe:
 */
public class JsUtil {

    private WebViewActivity activity;

    public JsUtil(WebViewActivity activity) {
        this.activity = activity;
    }


    @JavascriptInterface
    public void logout(){

    }

    @JavascriptInterface
    public void pushWeb(String url,String title){

    }

    @JavascriptInterface
    public void closePage(){

    }

}

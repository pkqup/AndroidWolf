package com.chunlangjiu.app.web;

import android.content.Context;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @CreatedbBy: liucun on 2018/9/11
 * @Describe:
 */
public class WebSettingUtil {

    public void initWebView(WebView webView, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置同时支持http和https加载
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);// 设置默认缓存模式
        webView.getSettings().setBlockNetworkImage(false);// 解除网络图片数据阻止
        webView.getSettings().setLoadsImagesAutomatically(true);// 支持自动加载图片
        webView.getSettings().setDomStorageEnabled(true);// 设置是否开启DOM存储API权限
        webView.getSettings().setAppCacheEnabled(true);// 设置Application缓存API是否开启
        webView.getSettings().setAppCachePath(context.getCacheDir().getAbsolutePath());// 设置缓存路径
        webView.getSettings().setAllowFileAccess(true);// 设置在WebView内部是否允许访问文件
        webView.getSettings().setAllowContentAccess(true);// 设置WebView是否使用其内置的变焦机制
        webView.getSettings().setDisplayZoomControls(true);// 设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上
        webView.getSettings().setUseWideViewPort(true);// 将图片调整到适合WebView的大小
        webView.setHorizontalScrollBarEnabled(false);//水平不显示滚动条
        webView.setVerticalScrollBarEnabled(false); //垂直不显示滚动条
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置允许js弹框
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);// 设置js可用

    }
}

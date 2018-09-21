package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.web.WebSettingUtil;
import com.pkqup.commonlibrary.view.verticalview.VerticalWebView;

public class GoodsWebFragment extends BaseFragment {

    private VerticalWebView webView;


    public static GoodsWebFragment newInstance(String webUrl) {
        GoodsWebFragment goodsWebFragment = new GoodsWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("webUrl", webUrl);
        goodsWebFragment.setArguments(bundle);
        return goodsWebFragment;
    }

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_web, container, true);
    }

    @Override
    public void initView() {
        webView = rootView.findViewById(R.id.webView);
        new WebSettingUtil().initWebView(webView, getContext());
        try {
            Bundle arguments = getArguments();
            String webUrl = arguments.getString("webUrl");
            webView.loadUrl(webUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }

    public void goTop(){
        webView.goTop();
    }

}

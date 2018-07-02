package com.chunlangjiu.app.goods.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.pkqup.commonlibrary.view.verticalview.VerticalWebView;

public class GoodsWebFragment extends BaseFragment {


    private VerticalWebView webView;


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_web, container, true);
    }

    @Override
    public void initView() {
        webView = rootView.findViewById(R.id.webView);
        webView.loadUrl("https://github.com/ysnows");
    }

    @Override
    public void initData() {

    }

}

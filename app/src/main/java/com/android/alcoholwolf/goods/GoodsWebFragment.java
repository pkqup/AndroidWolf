package com.android.alcoholwolf.goods;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseFragment;
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

package com.chunlangjiu.app.goods.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.pkqup.commonlibrary.view.verticalview.VerticalScrollView;
import com.socks.library.KLog;


public class GoodsDetailsFragment extends BaseFragment {


    private TextView drag_text;
    private VerticalScrollView scrollView;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        scrollView = rootView.findViewById(R.id.scrollView);
        drag_text = rootView.findViewById(R.id.drag_text);

        scrollView.setOnScrollListener(new VerticalScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                KLog.e(scrollY);
            }
        });
    }

    @Override
    public void initData() {


    }

    public void setDragText(String text) {
        drag_text.setText(text);
    }
}


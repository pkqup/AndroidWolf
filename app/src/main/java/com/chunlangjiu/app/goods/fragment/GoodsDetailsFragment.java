package com.chunlangjiu.app.goods.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;


public class GoodsDetailsFragment extends BaseFragment {


    private TextView drag_text;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        drag_text = rootView.findViewById(R.id.drag_text);
    }

    @Override
    public void initData() {


    }

    public void setDragText(String text){
        drag_text.setText(text);
    }
}


package com.android.alcoholwolf.abase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.alcoholwolf.R;
import com.pkqup.commonlibrary.abase.BaseFragment;

public class MyFragment extends BaseFragment {

    private TextView tv_fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup contentView) {
        inflater.inflate(R.layout.fragment_my, contentView, true);
    }

    @Override
    public void initView() {

        tv_fragment = rootView.findViewById(R.id.tv_fragment);
        tv_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
            }
        });
    }

    @Override
    public void initData() {

    }

}

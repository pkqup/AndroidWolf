package com.chunlangjiu.app.goods.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;
import com.socks.library.KLog;

public class GoodsSlideFragment extends BaseFragment {

    private VerticalSlide verticalSlide;
    private Fragment topFragment;
    private Fragment bottomFragment;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_slide, container, true);
    }

    @Override
    public void initView() {
        verticalSlide = rootView.findViewById(R.id.dragLayout);

        try {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            topFragment = new GoodsDetailsFragment();
            transaction.replace(R.id.first, topFragment);
            bottomFragment = new GoodsWebFragment();
            transaction.replace(R.id.second, bottomFragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage(int type) {
                ((GoodsDetailsFragment) topFragment).setDragText(type == 0 ? "继续下拉查看商品简介" : "继续拖动查看图文详情");
            }
        });
    }

    @Override
    public void initData() {

    }
}

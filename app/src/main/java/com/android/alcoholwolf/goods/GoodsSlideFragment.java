package com.android.alcoholwolf.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseFragment;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;

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
    }

    @Override
    public void initData() {
        try {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            topFragment = new GoodsDetailsFragment();
            transaction.replace(R.id.first, topFragment);
            bottomFragment = new GoodsWebFragment();
            transaction.replace(R.id.second, bottomFragment);

            verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
                @Override
                public void onShowNextPage(int type) {
                    ((GoodsDetailsFragment)topFragment).setDragText(type==0?"下拉，查看商品简介":"继续拖动，查看图文详情");
                }
            });
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

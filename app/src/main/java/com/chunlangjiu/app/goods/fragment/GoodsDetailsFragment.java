package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;


public class GoodsDetailsFragment extends BaseFragment {

    private VerticalSlide verticalSlide;
    private ScrollViewFragment topFragment;
    private GoodsWebFragment bottomFragment;

    public static GoodsDetailsFragment newInstance(GoodsDetailBean goodsDetailBean) {
        GoodsDetailsFragment goodsDetailsFragment = new GoodsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodsDetailBean", goodsDetailBean);
        goodsDetailsFragment.setArguments(bundle);
        return goodsDetailsFragment;
    }


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        GoodsDetailBean goodsDetailBean = (GoodsDetailBean) getArguments().getSerializable("goodsDetailBean");
        verticalSlide = rootView.findViewById(R.id.dragLayout);
        topFragment = ScrollViewFragment.newInstance(goodsDetailBean);
        bottomFragment = GoodsWebFragment.newInstance(goodsDetailBean.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.first, topFragment);
        transaction.replace(R.id.second, bottomFragment);
        transaction.commit();

        verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage(int showType) {
                EventManager.getInstance().notify(showType, ConstantMsg.GOODS_SLIDE_CHANGE);
            }
        });
    }

    @Override
    public void initData() {

    }

    public void goTop() {
        verticalSlide.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomFragment.goTop();
                verticalSlide.goTop(new VerticalSlide.OnGoTopListener() {
                    @Override
                    public void goTop() {
                        topFragment.goTop();
                    }
                });
            }
        },500);
    }

}


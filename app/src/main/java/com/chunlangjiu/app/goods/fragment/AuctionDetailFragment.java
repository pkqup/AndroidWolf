package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;

/**
 * @CreatedbBy: liucun on 2018/9/11
 * @Describe:
 */
public class AuctionDetailFragment extends BaseFragment {

    private VerticalSlide verticalSlide;
    private BaseFragment topFragment;
    private BaseFragment bottomFragment;


    public static AuctionDetailFragment newInstance(AuctionListBean.AuctionBean auctionBean) {
        AuctionDetailFragment auctionDetailFragment = new AuctionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("auctionBean", auctionBean);
        auctionDetailFragment.setArguments(bundle);
        return auctionDetailFragment;
    }


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        AuctionListBean.AuctionBean auctionBean = (AuctionListBean.AuctionBean) getArguments().getSerializable("auctionBean");
        verticalSlide = rootView.findViewById(R.id.dragLayout);
        topFragment = AuctionScrollViewFragment.newInstance(auctionBean);
        bottomFragment = GoodsWebFragment.newInstance(auctionBean.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.first, topFragment);
        transaction.replace(R.id.second, bottomFragment);
        transaction.commit();

        verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage(int showType) {

            }
        });
    }

    @Override
    public void initData() {

    }



}

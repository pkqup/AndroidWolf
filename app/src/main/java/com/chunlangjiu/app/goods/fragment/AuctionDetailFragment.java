package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;

/**
 * @CreatedbBy: liucun on 2018/9/11
 * @Describe:
 */
public class AuctionDetailFragment extends BaseFragment {

    private VerticalSlide verticalSlide;
    private AuctionScrollViewFragment topFragment;
    private GoodsWebFragment bottomFragment;


    public static AuctionDetailFragment newInstance(GoodsDetailBean goodsDetailBean) {
        AuctionDetailFragment auctionDetailFragment = new AuctionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodsDetailBean", goodsDetailBean);
        auctionDetailFragment.setArguments(bundle);
        return auctionDetailFragment;
    }


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        GoodsDetailBean goodsDetailBean = (GoodsDetailBean) getArguments().getSerializable("goodsDetailBean");
        verticalSlide = rootView.findViewById(R.id.dragLayout);
        topFragment = AuctionScrollViewFragment.newInstance(goodsDetailBean);
        bottomFragment = GoodsWebFragment.newInstance(goodsDetailBean.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.first, topFragment);
        transaction.replace(R.id.second, bottomFragment);
        transaction.commit();

        verticalSlide.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage(int showType) {
                EventManager.getInstance().notify(showType, ConstantMsg.AUCTION_SLIDE_CHANGE);
            }
        });
    }

    @Override
    public void initData() {

    }

    public void goTop(){
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

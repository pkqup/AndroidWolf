package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.AuctionBean;
import com.chunlangjiu.app.goods.bean.EvaluateListBean;
import com.chunlangjiu.app.goods.bean.GoodsDetailBean;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;
import com.pkqup.commonlibrary.view.verticalview.VerticalSlide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/9/11
 * @Describe:
 */
public class AuctionDetailFragment extends BaseFragment {

    private VerticalSlide verticalSlide;
    private BaseFragment topFragment;
    private BaseFragment bottomFragment;


    public static AuctionDetailFragment newInstance(AuctionBean auctionBean) {
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
        AuctionBean auctionBean = (AuctionBean) getArguments().getSerializable("auctionBean");
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

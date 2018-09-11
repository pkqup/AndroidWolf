package com.chunlangjiu.app.goods.fragment;

import android.os.Bundle;
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
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;
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

    private Banner banner;
    private LinearLayout indicator;
    private TextView tvPrice;
    private TextView tvGoodsName;
    private CountdownView countdownView;
    private TextView tvCountry;
    private TextView tvYear;
    private TextView tvDesc;

    private CircleImageView imgStore;
    private TextView tvStoreName;
    private TextView tvStoreDesc;
    private TextView tvLookAll;

    private RelativeLayout rlEvaluate;
    private TextView tvEvaluate;
    private LinearLayout llEvaluate;

    private RecyclerView recyclerView;//推荐商品列表

    private TextView drag_text;
    private WebView webView;

    private List<ImageView> imageViews;
    private List<String> bannerUrls;

    private List<String> recommendLists;
    private RecommendAdapter recommendAdapter;

    private CompositeDisposable disposable;
    private AuctionBean auctionBean;

    public static AuctionDetailFragment newInstance(AuctionBean auctionBean) {
        AuctionDetailFragment auctionDetailFragment = new AuctionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("auctionBean", auctionBean);
        auctionDetailFragment.setArguments(bundle);
        return auctionDetailFragment;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvLookAll://查看店铺
                    break;
                case R.id.rlEvaluate://查看店铺
                    EventManager.getInstance().notify(null, ConstantMsg.CHANGE_TO_EVALUATE);
                    break;
            }
        }
    };

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_auction_detail, container, true);
    }

    @Override
    public void initView() {
        banner = rootView.findViewById(R.id.banner);
        indicator = rootView.findViewById(R.id.indicator);

        tvPrice = rootView.findViewById(R.id.tvPrice);
        tvGoodsName = rootView.findViewById(R.id.tvGoodsName);
        countdownView = rootView.findViewById(R.id.countdownView);
        tvCountry = rootView.findViewById(R.id.tvCountry);
        tvYear = rootView.findViewById(R.id.tvYear);
        tvDesc = rootView.findViewById(R.id.tvDesc);

        imgStore = rootView.findViewById(R.id.imgStore);
        tvStoreName = rootView.findViewById(R.id.tvStoreName);
        tvStoreDesc = rootView.findViewById(R.id.tvStoreDesc);
        tvLookAll = rootView.findViewById(R.id.tvLookAll);
        tvLookAll.setOnClickListener(onClickListener);

        rlEvaluate = rootView.findViewById(R.id.rlEvaluate);
        rlEvaluate.setOnClickListener(onClickListener);
        tvEvaluate = rootView.findViewById(R.id.tvEvaluate);
        llEvaluate = rootView.findViewById(R.id.llEvaluate);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        drag_text = rootView.findViewById(R.id.drag_text);
        webView = rootView.findViewById(R.id.webView);

        disposable = new CompositeDisposable();
        auctionBean = (AuctionBean) getArguments().getSerializable("auctionBean");
    }

    @Override
    public void initData() {
        initBannerData();
        initCommonView();
        initRecommendView();
        initWebViewData();
        getEvaluateData();
    }


    private void initBannerData() {
        imageViews = new ArrayList<>();
        bannerUrls = new ArrayList<>();
        String list_image = auctionBean.getItem_info().getList_image();
        if (!TextUtils.isEmpty(list_image)) {
            String[] split = list_image.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    bannerUrls.add(s);
                }
            }
        }
        banner.setImages(bannerUrls)
                .setImageLoader(new BannerGlideLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)//去掉自带的indicator
                .setBannerAnimation(AccordionTransformer.class)
                .isAutoPlay(true)
                .setDelayTime(4000)
                .start();

        imageViews.clear();
        indicator.removeAllViews();
        for (int i = 0; i < bannerUrls.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 8;
            params.rightMargin = 8;
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_select);
            } else {
                imageView.setImageResource(R.drawable.banner_unselect);
            }
            imageViews.add(imageView);
            indicator.addView(imageView, params);
        }
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.size(); i++) {
                    if (position == i) {
                        imageViews.get(i).setImageResource(R.drawable.banner_select);
                    } else {
                        imageViews.get(i).setImageResource(R.drawable.banner_unselect);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //设置banner点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
    }

    private void initCommonView() {
        tvPrice.setText("¥" + auctionBean.getItem_info().getPrice());
        tvGoodsName.setText(auctionBean.getItem_info().getTitle());
        String end_time = auctionBean.getEnd_time();
        try {
            long endTime = 0;
            if (!TextUtils.isEmpty(end_time)) {
                endTime = Long.parseLong(end_time);
            }
            if ((endTime * 1000 - System.currentTimeMillis()) > 0) {
                countdownView.start(endTime * 1000 - System.currentTimeMillis());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2018/9/11 店铺信息
//        GlideUtils.loadImage(getActivity(), auctionBean.getShop().getShop_logo(), imgStore);
//        tvStoreName.setText(auctionBean.getShop().getShop_name());
//        tvStoreDesc.setText(auctionBean.getShop().getShop_descript());
    }


    private void getEvaluateData() {
        disposable.add(ApiUtils.getInstance().getEvaluateList(auctionBean.getItem_info().getItem_id(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<EvaluateListBean>>() {
                    @Override
                    public void accept(ResultBean<EvaluateListBean> evaluateListBeanResultBean) throws Exception {
                        List<EvaluateListBean.EvaluateDetailBean> list = evaluateListBeanResultBean.getData().getList();
                        initEvaluateView(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void initEvaluateView(List<EvaluateListBean.EvaluateDetailBean> list) {
        llEvaluate.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            if (i < 2) {
                View evaluateView = View.inflate(getActivity(), R.layout.goods_item_details_evaluate, null);
                TextView tvName = evaluateView.findViewById(R.id.tvName);
                RatingBar ratingBar = evaluateView.findViewById(R.id.ratingBar);
                TextView tvContent = evaluateView.findViewById(R.id.tvContent);
                TextView tvTime = evaluateView.findViewById(R.id.tvTime);
                tvName.setText(list.get(i).getUser_name());
                ratingBar.setRating(3);
                tvContent.setText(list.get(i).getContent());
                tvTime.setText(TimeUtils.millisToYearMD(list.get(i).getCreated_time() + "000"));
                llEvaluate.addView(evaluateView);
            }
        }
    }

    private void initRecommendView() {
        recommendLists = new ArrayList<>();
        recommendLists.add(bannerUrls.get(0));
        recommendLists.add(bannerUrls.get(0));
        recommendLists.add(bannerUrls.get(0));
        recommendAdapter = new RecommendAdapter(R.layout.goods_item_recommend, recommendLists);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(recommendAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initWebViewData() {
        webView.loadUrl("https://github.com/ysnows");
    }


    public class RecommendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public RecommendAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgPic = helper.getView(R.id.img_pic);
            ViewGroup.LayoutParams layoutParams = imgPic.getLayoutParams();
            int screenWidth = SizeUtils.getScreenWidth();
            int picWidth = (screenWidth - SizeUtils.dp2px(45)) / 3;
            layoutParams.height = picWidth;
            layoutParams.width = picWidth;
            imgPic.setLayoutParams(layoutParams);

            TextView tv_name = helper.getView(R.id.tv_name);
            ViewGroup.LayoutParams nameLayoutParams = tv_name.getLayoutParams();
            nameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            nameLayoutParams.width = picWidth;
            tv_name.setLayoutParams(nameLayoutParams);
            helper.setText(R.id.tv_name, "拉菲庄园红酒拉菲庄园红酒");
            helper.setText(R.id.tv_price, "￥500.00");
        }
    }


}
package com.chunlangjiu.app.goods.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.pkqup.commonlibrary.view.verticalview.VerticalScrollView;
import com.socks.library.KLog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class GoodsDetailsFragment extends BaseFragment {

    String[] images =
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",};

    private Banner banner;
    private LinearLayout indicator;
    private TextView tvPrice;
    private TextView tvGoodsName;
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

    public void setDragText(String text) {
        drag_text.setText(text);
    }

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_details, container, true);
    }

    @Override
    public void initView() {
        banner = rootView.findViewById(R.id.banner);
        indicator = rootView.findViewById(R.id.indicator);

        tvPrice = rootView.findViewById(R.id.tvPrice);
        tvGoodsName = rootView.findViewById(R.id.tvGoodsName);
        tvCountry = rootView.findViewById(R.id.tvCountry);
        tvYear = rootView.findViewById(R.id.tvYear);
        tvDesc = rootView.findViewById(R.id.tvDesc);

        imgStore = rootView.findViewById(R.id.imgStore);
        tvStoreName = rootView.findViewById(R.id.tvStoreName);
        tvStoreDesc = rootView.findViewById(R.id.tvStoreDesc);
        tvLookAll = rootView.findViewById(R.id.tvLookAll);

        rlEvaluate = rootView.findViewById(R.id.rlEvaluate);
        tvEvaluate = rootView.findViewById(R.id.tvEvaluate);
        llEvaluate = rootView.findViewById(R.id.llEvaluate);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        drag_text = rootView.findViewById(R.id.drag_text);
        webView = rootView.findViewById(R.id.webView);


    }


    @Override
    public void initData() {
        initBannerData();
        initEvaluateView();
        initRecommendView();
        initWebViewData();
    }


    private void initBannerData() {
        imageViews = new ArrayList<>();
        bannerUrls = new ArrayList<>();
        bannerUrls.add(images[0]);
        bannerUrls.add(images[1]);
        bannerUrls.add(images[2]);

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
                ToastUtils.showShort("点击了item" + position);
            }
        });
    }

    private void initEvaluateView() {
        llEvaluate.removeAllViews();
        for (int i = 0; i < 5; i++) {
            View evaluateView = View.inflate(getActivity(), R.layout.goods_item_details_evaluate, null);
            llEvaluate.addView(evaluateView);
        }
    }


    private void initRecommendView() {
        recommendLists = new ArrayList<>();
        recommendLists.add(images[0]);
        recommendLists.add(images[1]);
        recommendLists.add(images[2]);
        recommendAdapter = new RecommendAdapter(R.layout.goods_item_recommend, recommendLists);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(recommendAdapter);
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
            int picWidth = (screenWidth - SizeUtils.dp2px(60)) / 3;
            layoutParams.height = picWidth * 2;
            layoutParams.width = picWidth;
            imgPic.setLayoutParams(layoutParams);
        }
    }
}


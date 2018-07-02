package com.chunlangjiu.app.amain.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.RealmBean;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.realm.RealmUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 首页
 */
public class HomeFragment extends BaseFragment {

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

    private View headerView;
    private Banner banner;
    private LinearLayout indicator;
    private List<ImageView> imageViews;
    private List<String> bannerUrls;


    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List lists;
    private HomeAdapter homeAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_home, container, true);
        headerView = View.inflate(getActivity(), R.layout.amain_item_home_header, null);
    }

    @Override
    public void initView() {
        banner = headerView.findViewById(R.id.banner);
        indicator = headerView.findViewById(R.id.indicator);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.listView);
        imageViews = new ArrayList<>();
        initBannerView();
        initBannerIndicator();
        initRecyclerView();
    }


    private void initBannerView() {
        bannerUrls = new ArrayList<>();
        bannerUrls.add("http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg");
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
    }


    private void initBannerIndicator() {
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


    private void initRecyclerView() {
        lists = new ArrayList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(R.layout.amain_item_home_list, lists);
        homeAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(homeAdapter);

        refreshLayout.setEnableAutoLoadMore(true);//开启滑到底部自动加载
        refreshLayout.autoRefresh();       //触发自动刷新
        refreshLayout.setFooterHeight(0);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLaut) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lists.clear();
                        lists.addAll(getData());
                        homeAdapter.setNewData(lists);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                }, 1000);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLaut) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                        lists.addAll(getData());
                        homeAdapter.setNewData(lists);
                        if (lists.size() > 50) {
                            recyclerView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setFooterHeight(30);
                                    refreshLayout.finishLoadMoreWithNoMoreData();//显示没有更多数据
                                }
                            }, 1000);

                        }
                    }
                }, 1000);
            }
        });


    }

    @Override
    public void initData() {

        RealmBean realmBean = new RealmBean();
        realmBean.setAge("10");
        realmBean.setName("名字");

        RealmUtils.add(realmBean);

    }


    public class HomeAdapter extends BaseQuickAdapter<ResultBean, BaseViewHolder> {

        public HomeAdapter(int layoutResId, List<ResultBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ResultBean item) {
            helper.setText(R.id.tv_name, helper.getAdapterPosition() + "");
        }
    }

    private List getData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }


}

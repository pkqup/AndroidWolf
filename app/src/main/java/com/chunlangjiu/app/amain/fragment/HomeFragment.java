package com.chunlangjiu.app.amain.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.adapter.BrandAdapter;
import com.chunlangjiu.app.amain.adapter.HomeAdapter;
import com.chunlangjiu.app.amain.bean.BrandBean;
import com.chunlangjiu.app.amain.bean.HomeBean;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.chunlangjiu.app.goods.activity.ValuationActivity;
import com.chunlangjiu.app.store.activity.StoreListActivity;
import com.chunlangjiu.app.user.activity.AddAddressActivity;
import com.chunlangjiu.app.user.activity.AddGoodsActivity;
import com.chunlangjiu.app.user.bean.LocalAreaBean;
import com.chunlangjiu.app.util.AreaUtils;
import com.chunlangjiu.app.util.ConstantMsg;
import com.chunlangjiu.app.util.LocationUtils;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.util.PermissionUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yanzhenjie.permission.PermissionListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    private TextView tvCity;
    private RelativeLayout rlTitleSearch;

    private View headerView;
    private Banner banner;
    private LinearLayout indicator;
    private List<ImageView> imageViews;
    private List<String> bannerUrls;

    private LinearLayout llAuction;
    private LinearLayout llBuy;
    private LinearLayout llSell;
    private LinearLayout llSearch;
    private LinearLayout llEvaluate;

    //品牌推荐
    private RecyclerView recyclerViewBrand;
    private BrandAdapter brandAdapter;
    private List<BrandBean> brandLists;

    //酒列表
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private HomeAdapter homeAdapter;
    private List<HomeBean> lists;

    private String locationCityName;//定位城市名
    private LocatedCity locatedCity;//定位城市实体
    private List<HotCity> hotCities;//热门城市
    private List<City> cityList;//所有的城市列表

    private CompositeDisposable disposable;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvCity:
                    choiceCity();
                    break;
                case R.id.rlTitleSearch:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case R.id.llAuction://竞拍专区
                    EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_AUCTION);
                    break;
                case R.id.llBuy://我要买酒
                    EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_CLASS);
                    break;
                case R.id.llSell://我要卖酒
                    startActivity(new Intent(getActivity(), AddGoodsActivity.class));
                    break;
                case R.id.llSearch://名庄查询
                    startActivity(new Intent(getActivity(), StoreListActivity.class));
                    break;
                case R.id.llEvaluate://名酒估价
                    startActivity(new Intent(getActivity(), ValuationActivity.class));
                    break;
            }
        }
    };


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_home, container, true);
        headerView = View.inflate(getActivity(), R.layout.amain_item_home_header, null);
    }

    @Override
    public void initView() {
        disposable = new CompositeDisposable();
        tvCity = rootView.findViewById(R.id.tvCity);
        tvCity.setOnClickListener(onClickListener);
        rlTitleSearch = rootView.findViewById(R.id.rlTitleSearch);
        rlTitleSearch.setOnClickListener(onClickListener);

        banner = headerView.findViewById(R.id.banner);
        indicator = headerView.findViewById(R.id.indicator);
        llAuction = headerView.findViewById(R.id.llAuction);
        llBuy = headerView.findViewById(R.id.llBuy);
        llSell = headerView.findViewById(R.id.llSell);
        llSearch = headerView.findViewById(R.id.llSearch);
        llEvaluate = headerView.findViewById(R.id.llEvaluate);
        llAuction.setOnClickListener(onClickListener);
        llBuy.setOnClickListener(onClickListener);
        llSell.setOnClickListener(onClickListener);
        llSearch.setOnClickListener(onClickListener);
        llEvaluate.setOnClickListener(onClickListener);
        recyclerViewBrand = headerView.findViewById(R.id.recyclerViewBrand);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.listView);
        imageViews = new ArrayList<>();
        locationCity();
        initBannerView();
        initBannerIndicator();
        initBrandRecycleView();
        initListRecyclerView();
    }

    //开启定位
    private void locationCity() {
        PermissionUtils.PermissionForStart(getActivity(), new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantPermissions) {
                for (String grantPermission : grantPermissions) {
                    if (grantPermission.equals(Manifest.permission.ACCESS_FINE_LOCATION) ||
                            grantPermission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //开启定位
                        new LocationUtils().startLocation(new LocationUtils.LocationCallBack() {
                            @Override
                            public void locationSuccess(AMapLocation aMapLocation) {
                                locationCityName = aMapLocation.getCity();
                                locatedCity = new LocatedCity(locationCityName, aMapLocation.getProvince(), "");
                                tvCity.setText(locationCityName);
                            }

                            @Override
                            public void locationFail() {
                                tvCity.setText("定位失败");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                for (String grantPermission : deniedPermissions) {
                    KLog.e(grantPermission);
                }
            }
        });

        //获取城市列表
        disposable.add(Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                List<City> cities = AreaUtils.getCityList(getActivity());
                e.onNext(cities);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> cities) throws Exception {
                        cityList = cities;
                        //城市按 a-z排序
                        Collections.sort(cityList, new CityComparator());

                        //初始化热门城市
                        hotCities = new ArrayList<>();
                        hotCities.add(new HotCity("北京", "北京", ""));
                        hotCities.add(new HotCity("上海", "上海", ""));
                        hotCities.add(new HotCity("广州", "广东", ""));
                        hotCities.add(new HotCity("深圳", "广东", ""));
                        hotCities.add(new HotCity("杭州", "浙江", ""));
                        hotCities.add(new HotCity("成都", "四川", ""));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }


    private void initBannerView() {
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


    /**
     * 品牌推荐
     */
    private void initBrandRecycleView() {
        brandLists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BrandBean brandBean = new BrandBean();
            brandLists.add(brandBean);
        }
        brandAdapter = new BrandAdapter(R.layout.amain_itme_brand, brandLists);
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewBrand.setAdapter(brandAdapter);
        recyclerViewBrand.setHasFixedSize(true);
        recyclerViewBrand.setNestedScrollingEnabled(false);
    }

    /**
     * 商品列表
     */
    private void initListRecyclerView() {
        lists = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(getActivity(), lists);
        homeAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(homeAdapter);

        refreshLayout.setEnableAutoLoadMore(true);//开启滑到底部自动加载
        refreshLayout.setFooterHeight(0);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                }, 1000);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
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
        for (int i = 0; i < 20; i++) {
            HomeBean homeBean = new HomeBean();
            if (i == 3) {
                homeBean.setItemType(HomeBean.ITEM_PIC);
            } else {
                homeBean.setItemType(HomeBean.ITEM_GOODS);
            }
            lists.add(homeBean);
        }
        homeAdapter.setNewData(lists);
    }


    private void choiceCity() {
        CityPicker.getInstance()
                .setFragmentManager(getActivity().getSupportFragmentManager())
                .setLocatedCity(locatedCity)
                .setHotCities(hotCities)
                .setCityLists(cityList)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (data != null) {

                        }
                    }

                    @Override
                    public void onLocate() {
                    }
                })
                .show();
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
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

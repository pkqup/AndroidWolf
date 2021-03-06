package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
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

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseApplication;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.activity.LoginActivity;
import com.chunlangjiu.app.amain.adapter.BrandAdapter;
import com.chunlangjiu.app.amain.adapter.HomeAdapter;
import com.chunlangjiu.app.amain.bean.HomeAuctionBean;
import com.chunlangjiu.app.amain.bean.HomeBean;
import com.chunlangjiu.app.amain.bean.HomeListBean;
import com.chunlangjiu.app.amain.bean.HomeModulesBean;
import com.chunlangjiu.app.goods.activity.AuctionDetailActivity;
import com.chunlangjiu.app.goods.activity.GoodsDetailsActivity;
import com.chunlangjiu.app.goods.activity.GoodsListNewActivity;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.chunlangjiu.app.goods.activity.ShopMainActivity;
import com.chunlangjiu.app.goods.activity.ValuationActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.store.activity.StoreListActivity;
import com.chunlangjiu.app.user.activity.AddGoodsActivity;
import com.chunlangjiu.app.user.activity.PersonAuthActivity;
import com.chunlangjiu.app.user.bean.AuthStatusBean;
import com.chunlangjiu.app.util.AreaUtils;
import com.chunlangjiu.app.util.ConstantMsg;
import com.chunlangjiu.app.util.LocationUtils;
import com.chunlangjiu.app.web.WebViewActivity;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.BannerGlideLoader;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.PermissionUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 首页
 */
public class HomeFragment extends BaseFragment {

    private static final String MODULE_SLIDER = "slider";
    private static final String MODULE_ICONS_NAV = "icons_nav";
    private static final String MODULE_CATEGORY_NAV = "category_nav";

    private TextView tvCity;
    private RelativeLayout rlTitleSearch;
    private ImageView imgMsg;

    private View headerView;
    private Banner banner;
    private LinearLayout indicator;
    private List<ImageView> imageViews;
    private List<String> bannerUrls;

    private LinearLayout llIconOne;
    private ImageView imgIconOne;
    private TextView tvStrOne;
    private LinearLayout llIconTwo;
    private ImageView imgIconTwo;
    private TextView tvStrTwo;
    private LinearLayout llIconThree;
    private ImageView imgIconThree;
    private TextView tvStrThree;
    private LinearLayout llIconFour;
    private ImageView imgIconFour;
    private TextView tvStrFour;
    private LinearLayout llIconFive;
    private ImageView imgIconFive;
    private TextView tvStrFive;

    //品牌推荐
    private LinearLayout llBrand;
    private RecyclerView recyclerViewBrand;
    private BrandAdapter brandAdapter;
    private List<HomeModulesBean.Pic> brandLists;

    //酒列表
    private RelativeLayout rlLoading;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<HomeBean> lists;

    private String locationCityName;//定位城市名
    private LocatedCity locatedCity;//定位城市实体
    private List<HotCity> hotCities;//热门城市
    private List<City> cityList;//所有的城市列表

    private CompositeDisposable disposable;
    private CityPicker cityPicker;
    private int pageNo = 1;//请求页数
    private List<HomeModulesBean.Pic> bannerPicLists;
    private List<HomeModulesBean.Pic> iconPicLists;
    private List<HomeModulesBean.Pic> brandPicLists;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvCity:
                    choiceCity();
                    break;
                case R.id.imgMsg:
                    WebViewActivity.startWebViewActivity(getActivity(), ConstantMsg.WEB_URL_MESSAGE + BaseApplication.getToken(), "消息");
                    break;
                case R.id.rlTitleSearch:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case R.id.llAuction://竞拍专区
                    iconFunctionClick(0);
                    break;
                case R.id.llBuy://我要买酒
                    iconFunctionClick(1);
                    break;
                case R.id.llSell://我要卖酒
                    iconFunctionClick(2);
                    break;
                case R.id.llSearch://名庄查询
                    iconFunctionClick(3);
                    break;
                case R.id.llEvaluate://名酒估价
                    iconFunctionClick(4);
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
        imgMsg = rootView.findViewById(R.id.imgMsg);
        imgMsg.setOnClickListener(onClickListener);
        rlTitleSearch = rootView.findViewById(R.id.rlTitleSearch);
        rlTitleSearch.setOnClickListener(onClickListener);

        banner = headerView.findViewById(R.id.banner);
        indicator = headerView.findViewById(R.id.indicator);

        llIconOne = headerView.findViewById(R.id.llAuction);
        llIconTwo = headerView.findViewById(R.id.llBuy);
        llIconThree = headerView.findViewById(R.id.llSell);
        llIconFour = headerView.findViewById(R.id.llSearch);
        llIconFive = headerView.findViewById(R.id.llEvaluate);
        imgIconOne = headerView.findViewById(R.id.imgIconOne);
        tvStrOne = headerView.findViewById(R.id.tvStrOne);
        imgIconTwo = headerView.findViewById(R.id.imgIconTwo);
        tvStrTwo = headerView.findViewById(R.id.tvStrTwo);
        imgIconThree = headerView.findViewById(R.id.imgIconThree);
        tvStrThree = headerView.findViewById(R.id.tvStrThree);
        imgIconFour = headerView.findViewById(R.id.imgIconFour);
        tvStrFour = headerView.findViewById(R.id.tvStrFour);
        imgIconFive = headerView.findViewById(R.id.imgIconFive);
        tvStrFive = headerView.findViewById(R.id.tvStrFive);
        llIconOne.setOnClickListener(onClickListener);
        llIconTwo.setOnClickListener(onClickListener);
        llIconThree.setOnClickListener(onClickListener);
        llIconFour.setOnClickListener(onClickListener);
        llIconFive.setOnClickListener(onClickListener);

        llBrand = headerView.findViewById(R.id.llBrand);
        recyclerViewBrand = headerView.findViewById(R.id.recyclerViewBrand);

        rlLoading = rootView.findViewById(R.id.rlLoading);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.listView);
        rlLoading.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        bannerUrls = new ArrayList<>();
        imageViews = new ArrayList<>();
        brandLists = new ArrayList<>();
    }


    @Override
    public void initData() {
        locationCity();
        initBrandRecycleView();
        initRecyclerView();
        getHomeModules();
        getHomeList(pageNo, true);
    }

    //开启定位
    private void locationCity() {
        if (PermissionUtils.hasPermissionForLocation(getActivity())) {
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
        } else {
            tvCity.setText("定位失败");
            ToastUtils.showShort("请开启位置权限");
        }

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
                HomeModulesBean.Pic fuction = bannerPicLists.get(position);
                functionJump(fuction);
            }
        });
    }


    /**
     * 品牌推荐
     */
    private void initBrandRecycleView() {
        brandAdapter = new BrandAdapter(getActivity(), R.layout.amain_itme_brand, brandLists);
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewBrand.setAdapter(brandAdapter);
        recyclerViewBrand.setHasFixedSize(true);
        recyclerViewBrand.setNestedScrollingEnabled(false);
        brandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsListNewActivity.startGoodsListNewActivity(getActivity(), brandLists.get(position).getCat_id(), brandLists.get(position).getCategoryname(), "");
            }
        });
    }

    /**
     * 商品列表
     */
    private void initRecyclerView() {
        lists = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(getActivity(), lists);
        homeAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(homeAdapter);

        refreshLayout.setEnableAutoLoadMore(true);//开启滑到底部自动加载
        refreshLayout.setFooterHeight(30);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                getHomeModules();
                getHomeList(1, true);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                getHomeList(pageNo + 1, false);
            }
        });
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeBean homeBean = lists.get(position);
                if (homeBean.getItemType() == HomeBean.ITEM_GOODS) {
                    if (homeBean.isAuction()) {
                        AuctionDetailActivity.startAuctionDetailsActivity(getActivity(), lists.get(position).getItem_id());
                    } else {
                        GoodsDetailsActivity.startGoodsDetailsActivity(getActivity(), homeBean.getItem_id());
                    }
                } else if (homeBean.getItemType() == HomeBean.ITEM_TUIJIAN) {

                }
            }
        });
    }

    private void choiceCity() {
        if (cityPicker == null) {
            cityPicker = CityPicker.getInstance()
                    .setFragmentManager(getActivity().getSupportFragmentManager())
                    .setLocatedCity(locatedCity)
                    .setHotCities(hotCities)
                    .setCityLists(cityList)
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onPick(int position, City data) {
                            if (data != null) {
                                tvCity.setText(data.getName());
                            }
                        }

                        @Override
                        public void onLocate() {
                        }
                    });
        }
        cityPicker.show();
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

    private void getHomeModules() {
        disposable.add(ApiUtils.getInstance().getHomeModules()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<HomeModulesBean>>() {
                    @Override
                    public void accept(ResultBean<HomeModulesBean> homeModulesBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        getModulesSuccess(homeModulesBeanResultBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                    }
                }));
    }

    private void getModulesSuccess(ResultBean<HomeModulesBean> homeModulesBeanResultBean) {
        List<HomeModulesBean.Modules> modules = homeModulesBeanResultBean.getData().getModules();
        if (modules != null && modules.size() > 0) {
            for (int i = 0; i < modules.size(); i++) {
                if (MODULE_SLIDER.equals(modules.get(i).getWidget())) {
                    updateBannerData(modules.get(i).getParams());
                }
                if (MODULE_ICONS_NAV.equals(modules.get(i).getWidget())) {
                    updateIconData(modules.get(i).getParams());
                }
                if (MODULE_CATEGORY_NAV.equals(modules.get(i).getWidget())) {
                    updateBrandData(modules.get(i).getParams());
                }
            }
        }
    }

    private void updateBannerData(HomeModulesBean.Params params) {
        bannerPicLists = params.getPic();
        if (bannerPicLists != null && bannerPicLists.size() > 0) {
            bannerUrls.clear();
            for (int i = 0; i < bannerPicLists.size(); i++) {
                bannerUrls.add(bannerPicLists.get(i).getImagesrc());
            }
        }
        initBannerView();
        initBannerIndicator();
    }

    private void updateIconData(HomeModulesBean.Params params) {
        iconPicLists = params.getPic();
        if (iconPicLists != null && iconPicLists.size() > 0) {
            for (int i = 0; i < iconPicLists.size(); i++) {
                switch (i) {
                    case 0:
                        GlideUtils.loadImage(getActivity(), iconPicLists.get(i).getImagesrc(), imgIconOne);
                        tvStrOne.setText(iconPicLists.get(i).getTag());
                        break;
                    case 1:
                        GlideUtils.loadImage(getActivity(), iconPicLists.get(i).getImagesrc(), imgIconTwo);
                        tvStrTwo.setText(iconPicLists.get(i).getTag());
                        break;
                    case 2:
                        GlideUtils.loadImage(getActivity(), iconPicLists.get(i).getImagesrc(), imgIconThree);
                        tvStrThree.setText(iconPicLists.get(i).getTag());
                        break;
                    case 3:
                        GlideUtils.loadImage(getActivity(), iconPicLists.get(i).getImagesrc(), imgIconFour);
                        tvStrFour.setText(iconPicLists.get(i).getTag());
                        break;
                    case 4:
                        GlideUtils.loadImage(getActivity(), iconPicLists.get(i).getImagesrc(), imgIconFive);
                        tvStrFive.setText(iconPicLists.get(i).getTag());
                        break;
                }
            }
        }
    }

    private void updateBrandData(HomeModulesBean.Params params) {
        brandPicLists = params.getPic();
        if (brandPicLists != null && brandPicLists.size() > 0) {
            llBrand.setVisibility(View.VISIBLE);
            brandLists.clear();
            brandLists.addAll(brandPicLists);
            brandAdapter.setNewData(brandLists);
        } else {
            llBrand.setVisibility(View.GONE);
        }
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollTo(0, 0);
            }
        });
    }

    private void getHomeList(int pageNo, final boolean isRefresh) {
        disposable.add(ApiUtils.getInstance().getHomeLists(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<HomeListBean>>() {
                    @Override
                    public void accept(ResultBean<HomeListBean> homeListBeanResultBean) throws Exception {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        getListSuccess(homeListBeanResultBean, isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                }));
    }

    private void getListSuccess(ResultBean<HomeListBean> homeListBeanResultBean, boolean isRefresh) {
        List<HomeBean> newLists = homeListBeanResultBean.getData().getList();
        List<HomeAuctionBean> auction_list = homeListBeanResultBean.getData().getAuction_list();
        if (newLists == null) newLists = new ArrayList<>();
        for (HomeBean homeBean : newLists) {
            homeBean.setItemType(HomeBean.ITEM_GOODS);
            homeBean.setAuction(false);
        }
        if (isRefresh) {
            pageNo = 1;
            if (auction_list != null && auction_list.size() > 0) {
                lists.clear();
                HomeBean jingpaiFlag = new HomeBean();
                jingpaiFlag.setItemType(HomeBean.ITEM_JINGPAI);
                lists.add(jingpaiFlag);
                for (int i = 0; i < auction_list.size(); i++) {
                    HomeBean homeBean = new HomeBean();
                    homeBean.setItemType(HomeBean.ITEM_GOODS);
                    homeBean.setAuction(true);
                    homeBean.setItem_id(auction_list.get(i).getItem_id());
                    homeBean.setTitle(auction_list.get(i).getTitle());
                    homeBean.setImage_default_id(auction_list.get(i).getImage_default_id());
                    homeBean.setLabel(auction_list.get(i).getLabel());
                    homeBean.setAuction_starting_price(auction_list.get(i).getAuction_starting_price());
                    homeBean.setAuction_end_time(auction_list.get(i).getAuction_end_time());
                    homeBean.setMax_price(auction_list.get(i).getMax_price());
                    lists.add(homeBean);
                }
                HomeBean tuijian = new HomeBean();
                tuijian.setItemType(HomeBean.ITEM_TUIJIAN);
                lists.add(tuijian);
                lists.addAll(newLists);
            } else {
                HomeBean tuijian = new HomeBean();
                tuijian.setItemType(HomeBean.ITEM_TUIJIAN);
                lists.add(tuijian);
                lists.addAll(newLists);
            }
        } else {
            pageNo++;
            lists.addAll(newLists);
        }
        if (lists.size() == 0) {
            refreshLayout.setEnableLoadMore(false);//设置不能加载更多
        } else {
            refreshLayout.setEnableLoadMore(true);//设置能加载更多
        }
        if (lists.size() < 10) {
            refreshLayout.setFooterHeight(30);
            refreshLayout.finishLoadMoreWithNoMoreData();//显示没有更多数据
        } else {
            refreshLayout.setNoMoreData(false);
        }
        homeAdapter.setNewData(lists);
    }

    private void checkStatus() {
        if (BaseApplication.isLogin()) {
            showLoadingDialog();
            Observable<ResultBean<AuthStatusBean>> personAuthStatus = ApiUtils.getInstance().getPersonAuthStatus();
            Observable<ResultBean<AuthStatusBean>> companyAuthStatus = ApiUtils.getInstance().getCompanyAuthStatus();
            disposable.add(Observable.zip(personAuthStatus, companyAuthStatus, new BiFunction<ResultBean<AuthStatusBean>, ResultBean<AuthStatusBean>, List<AuthStatusBean>>() {
                @Override
                public List<AuthStatusBean> apply(ResultBean<AuthStatusBean> uploadImageBeanResultBean, ResultBean<AuthStatusBean> uploadImageBeanResultBean2) throws Exception {
                    List<AuthStatusBean> imageLists = new ArrayList<>();
                    imageLists.add(uploadImageBeanResultBean.getData());
                    imageLists.add(uploadImageBeanResultBean2.getData());
                    return imageLists;
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<AuthStatusBean>>() {
                        @Override
                        public void accept(List<AuthStatusBean> authStatusBeans) throws Exception {
                            hideLoadingDialog();
                            if (AuthStatusBean.AUTH_SUCCESS.equals(authStatusBeans.get(0).getStatus()) || AuthStatusBean.AUTH_SUCCESS.equals(authStatusBeans.get(1).getStatus())) {
                                startActivity(new Intent(getActivity(), AddGoodsActivity.class));
                            } else if (AuthStatusBean.AUTH_LOCKED.equals(authStatusBeans.get(0).getStatus()) || AuthStatusBean.AUTH_LOCKED.equals(authStatusBeans.get(1).getStatus())) {
                                ToastUtils.showShort("您的认证正在审核中，我们会尽快处理");
                            } else if (AuthStatusBean.AUTH_FAILING.equals(authStatusBeans.get(0).getStatus()) || AuthStatusBean.AUTH_FAILING.equals(authStatusBeans.get(1).getStatus())) {
                                ToastUtils.showShort("您的认证被驳回，请重新提交资料审核");
                                startActivity(new Intent(getActivity(), PersonAuthActivity.class));
                            } else {
                                ToastUtils.showShort("您还没有进行实名认证，请先认证");
                                startActivity(new Intent(getActivity(), PersonAuthActivity.class));
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            hideLoadingDialog();
                        }
                    }));
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
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


    private void iconFunctionClick(int index) {
        if (iconPicLists != null && iconPicLists.size() > 0) {
            HomeModulesBean.Pic function = iconPicLists.get(index);
            functionJump(function);
        } else {
            switch (index) {
                case 0:
                    EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_AUCTION);
                    break;
                case 1:
                    EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_CLASS);
                    break;
                case 2:
                    checkStatus();
                    break;
                case 3:
                    startActivity(new Intent(getActivity(), StoreListActivity.class));
                    break;
                case 4:
                    if (BaseApplication.isLogin()) {
                        startActivity(new Intent(getActivity(), ValuationActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                    break;
            }
        }
    }


    private void functionJump(HomeModulesBean.Pic function) {
        switch (function.getLinktype()) {
            case HomeModulesBean.ITEM_GOODS:
                GoodsDetailsActivity.startGoodsDetailsActivity(getActivity(), function.getLinktarget());
                break;
            case HomeModulesBean.ITEM_SHOP:
                ShopMainActivity.startShopMainActivity(getActivity(), function.getLinktarget());
                break;
            case HomeModulesBean.ITEM_CLASS:
                EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_CLASS);
                break;
            case HomeModulesBean.ITEM_BRAND:
                GoodsListNewActivity.startGoodsListNewActivity(getActivity(), function.getLinktarget(), "", "");
                break;
            case HomeModulesBean.ITEM_ACTIVITY:
                EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_AUCTION);
                break;
            case HomeModulesBean.ITEM_WINERY:
                startActivity(new Intent(getActivity(), StoreListActivity.class));
                break;
            case HomeModulesBean.ITEM_EVALUATION:
                if (BaseApplication.isLogin()) {
                    startActivity(new Intent(getActivity(), ValuationActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case HomeModulesBean.ITEM_MEMBER:
                EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_MY);
                break;
            case HomeModulesBean.ITEM_CART:
                EventManager.getInstance().notify(null, ConstantMsg.MSG_PAGE_CART);
                break;
            case HomeModulesBean.ITEM_H5:
                WebViewActivity.startWebViewActivity(getActivity(), function.getLinktarget(), function.getTag());
                break;
            case HomeModulesBean.ITEM_SELLWINE:
                checkStatus();
                break;
        }
    }

}

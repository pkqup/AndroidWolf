package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseApplication;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.activity.LoginActivity;
import com.chunlangjiu.app.amain.adapter.AuctionListAdapter;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.goods.activity.AuctionDetailActivity;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.chunlangjiu.app.goods.adapter.FilterBrandAdapter;
import com.chunlangjiu.app.goods.adapter.FilterStoreAdapter;
import com.chunlangjiu.app.goods.bean.FilterBrandBean;
import com.chunlangjiu.app.goods.bean.FilterListBean;
import com.chunlangjiu.app.goods.bean.FilterStoreBean;
import com.chunlangjiu.app.goods.dialog.ClassPopWindow;
import com.chunlangjiu.app.net.ApiUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 竞拍
 */
public class AuctionFragment extends BaseFragment {

    private static final String ORDER_ALL = "modified_time";//综合
    private static final String ORDER_NEW = "list_time";//新品
    private static final String ORDER_PRICE_UP = "price_asc";//价格升序
    private static final String ORDER_PRICE_DOWN = "price_desc";//价格降序
    private String orderBy = ORDER_ALL;

    private DrawerLayout drawerLayout;
    private LinearLayout rightView;
    private RecyclerView recyclerViewBrand;//品牌列表
    private RecyclerView recyclerViewStore;//名庄列表
    private EditText etLowPrice;
    private EditText etHighPrice;
    private EditText etStartTime;
    private EditText etEndTime;
    private TextView tvReset;
    private TextView tvConfirm;

    private TextView tvAll;
    private TextView tvNew;
    private RelativeLayout sortPrice;
    private TextView tvPrice;
    private TextView tvClass;
    private RelativeLayout sortFilter;
    private TextView tvFilter;

    //三级分类列表
    private List<FirstClassBean> categoryLists;
    private ClassPopWindow classPopWindow;

    //品牌列表
    private List<FilterBrandBean> brandLists;
    private FilterBrandAdapter filterBrandAdapter;
    //酒庄列表
    private List<FilterStoreBean> storeLists;
    private FilterStoreAdapter filterStoreAdapter;

    private String classId;
    private String className;

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<AuctionListBean.AuctionBean> lists;
    private AuctionListAdapter linearAdapter;

    private CompositeDisposable disposable;

    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_right_one_f:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case R.id.tv_all:
                    changeSortAll();
                    break;
                case R.id.tv_new:
                    changeSortNew();
                    break;
                case R.id.sortPrice:
                    changeSortPrice();
                    break;
                case R.id.tv_class:
                    showClassPopWindow();
                    break;
                case R.id.sortFilter:
                    showDrawerLayout();
                    break;
                case R.id.tvReset://重置筛选条件
                    break;
                case R.id.tvConfirm://确认筛选条件
                    break;
            }
        }
    };

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_auction, container, true);
    }


    @Override
    public void initView() {
        disposable = new CompositeDisposable();
        titleView.setVisibility(View.VISIBLE);
        tvTitleF.setText("竞拍专区");
        imgTitleRightOneF.setOnClickListener(onClickListener);

        initDrawerLayout();

        tvAll = rootView.findViewById(R.id.tv_all);
        tvNew = rootView.findViewById(R.id.tv_new);
        tvPrice = rootView.findViewById(R.id.tv_price);
        tvClass = rootView.findViewById(R.id.tv_class);
        tvFilter = rootView.findViewById(R.id.tv_filter);
        sortPrice = rootView.findViewById(R.id.sortPrice);
        sortFilter = rootView.findViewById(R.id.sortFilter);
        tvAll.setOnClickListener(onClickListener);
        tvNew.setOnClickListener(onClickListener);
        sortPrice.setOnClickListener(onClickListener);
        tvClass.setOnClickListener(onClickListener);
        sortFilter.setOnClickListener(onClickListener);
        tvAll.setSelected(true);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);//设置可以下拉刷新
        refreshLayout.setEnableLoadMore(false);//设置不能加载更多
        recyclerView = rootView.findViewById(R.id.recycle_view);
        lists = new ArrayList<>();
        linearAdapter = new AuctionListAdapter(getActivity(), R.layout.amain_item_goods_list_linear, lists);
        linearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (BaseApplication.isLogin()) {
                    AuctionDetailActivity.startAuctionDetailsActivity(getActivity(), lists.get(position).getItem_id());
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(linearAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                getListData();
            }
        });
    }

    private void initDrawerLayout() {
        drawerLayout = rootView.findViewById(R.id.drawerLayout);
        rightView = rootView.findViewById(R.id.right_view);

        recyclerViewBrand = rootView.findViewById(R.id.recyclerViewBrand);
        recyclerViewStore = rootView.findViewById(R.id.recyclerViewStore);
        etLowPrice = rootView.findViewById(R.id.etLowPrice);
        etHighPrice = rootView.findViewById(R.id.etHighPrice);
        etStartTime = rootView.findViewById(R.id.etStartTime);
        etEndTime = rootView.findViewById(R.id.etEndTime);
        tvReset = rootView.findViewById(R.id.tvReset);
        tvConfirm = rootView.findViewById(R.id.tvConfirm);
        tvReset.setOnClickListener(onClickListener);
        tvConfirm.setOnClickListener(onClickListener);

        drawerLayout.closeDrawer(Gravity.END);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        ViewGroup.LayoutParams layoutParams = rightView.getLayoutParams();
        layoutParams.width = SizeUtils.getScreenWidth() - SizeUtils.dp2px(100);
        rightView.setLayoutParams(layoutParams);

        brandLists = new ArrayList<>();
        filterBrandAdapter = new FilterBrandAdapter(R.layout.goods_item_pop_class, brandLists);
        filterBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                brandLists.get(position).setSelect(true);
                filterBrandAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewBrand.setHasFixedSize(true);
        recyclerViewBrand.setNestedScrollingEnabled(false);
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewBrand.setAdapter(filterBrandAdapter);

        storeLists = new ArrayList<>();
        filterStoreAdapter = new FilterStoreAdapter(R.layout.goods_item_pop_class, storeLists);
        filterStoreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                storeLists.get(position).setSelect(true);
                filterStoreAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewStore.setHasFixedSize(true);
        recyclerViewStore.setNestedScrollingEnabled(false);
        recyclerViewStore.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewStore.setAdapter(filterStoreAdapter);
    }

    @Override
    public void initData() {
        getListData();
        getClassData();
        getFilterData();
    }

    private void getListData() {
        disposable.add(ApiUtils.getInstance().getAuctionList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<AuctionListBean>>() {
                    @Override
                    public void accept(ResultBean<AuctionListBean> listResultBean) throws Exception {
                        refreshLayout.finishRefresh();
                        List<AuctionListBean.AuctionBean> list = listResultBean.getData().getList();
                        getListSuccess(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.finishRefresh();
                    }
                }));
    }

    private void getListSuccess(List<AuctionListBean.AuctionBean> list) {
        if (list != null) {
            this.lists = list;
            linearAdapter.setNewData(lists);
        }
    }

    private void getClassData() {
        disposable.add(ApiUtils.getInstance().getMainClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<MainClassBean>>() {
                    @Override
                    public void accept(ResultBean<MainClassBean> mainClassBean) throws Exception {
                        categoryLists = mainClassBean.getData().getCategorys();
                    }
                }, new Consumer<Throwable>() {
                    @Override

                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }


    private void getFilterData() {
        disposable.add(ApiUtils.getInstance().getFilterData(classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<FilterListBean>>() {
                    @Override
                    public void accept(ResultBean<FilterListBean> filterListBeanResultBean) throws Exception {
                        List<FilterBrandBean> brands = filterListBeanResultBean.getData().getBrand();
                        List<FilterStoreBean> cats = filterListBeanResultBean.getData().getCat();
                        if (brands != null) {
                            brandLists = brands;
                            filterBrandAdapter.setNewData(brandLists);
                        }

                        if (cats != null) {
                            storeLists = cats;
                            filterStoreAdapter.setNewData(storeLists);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void showClassPopWindow() {
        if (categoryLists == null || categoryLists.size() == 0) {
            ToastUtils.showShort("暂无分类");
        } else {
            if (classPopWindow == null) {
                classPopWindow = new ClassPopWindow(getActivity(), categoryLists, classId);
                classPopWindow.setCallBack(new ClassPopWindow.CallBack() {
                    @Override
                    public void choiceClass(String name, String id) {
                        classId = id;
                        className = name;
                        getFilterData();
                    }
                });
            }
            classPopWindow.showAsDropDown(tvClass, 0, 1);
        }
    }


    private void showDrawerLayout() {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            drawerLayout.closeDrawer(Gravity.END);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
            drawerLayout.openDrawer(Gravity.END);
        }
    }

    private void changeSortAll() {
        if (!orderBy.equals(ORDER_ALL)) {
            orderBy = ORDER_ALL;
            tvAll.setSelected(true);
            tvNew.setSelected(false);
            tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.goods_price_sort), null);
        }
    }

    private void changeSortNew() {
        if (!orderBy.equals(ORDER_NEW)) {
            orderBy = ORDER_NEW;
            tvAll.setSelected(false);
            tvNew.setSelected(true);
            tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.goods_price_sort), null);
        }
    }

    private void changeSortPrice() {
        if (!orderBy.equals(ORDER_PRICE_UP) && !orderBy.equals(ORDER_PRICE_DOWN)) {
            orderBy = ORDER_PRICE_UP;
            tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.goods_price_sort_up), null);
        } else {
            if (orderBy.equals(ORDER_PRICE_UP)) {
                orderBy = ORDER_PRICE_DOWN;
                tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.goods_price_sort_down), null);
            } else {
                orderBy = ORDER_PRICE_UP;
                tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.goods_price_sort_up), null);
            }
        }
        tvAll.setSelected(false);
        tvNew.setSelected(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

package com.chunlangjiu.app.amain.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.adapter.AuctionListAdapter;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.chunlangjiu.app.goods.activity.AuctionDetailActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 竞拍
 */
public class AuctionFragment extends BaseFragment {

    private DrawerLayout drawerLayout;
    private LinearLayout rightView;

    private TextView tv_all;
    private TextView tv_new;
    private TextView tv_price;
    private TextView tv_class;
    private TextView tv_filter;

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
                case R.id.img_title_right_one:
                    break;
                case R.id.tv_all:
                    break;
                case R.id.tv_new:
                    break;
                case R.id.tv_price:
                    break;
                case R.id.tv_class:
                    break;
                case R.id.tv_filter:
                    showDrawerLayout();
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

        drawerLayout = rootView.findViewById(R.id.drawerLayout);
        rightView = rootView.findViewById(R.id.right_view);
        drawerLayout.closeDrawer(Gravity.END);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        drawerLayout.addDrawerListener(drawerListener);
        ViewGroup.LayoutParams layoutParams = rightView.getLayoutParams();
        layoutParams.width = SizeUtils.getScreenWidth() - SizeUtils.dp2px(100);
        rightView.setLayoutParams(layoutParams);

        tv_all = rootView.findViewById(R.id.tv_all);
        tv_new = rootView.findViewById(R.id.tv_new);
        tv_price = rootView.findViewById(R.id.tv_price);
        tv_class = rootView.findViewById(R.id.tv_class);
        tv_filter = rootView.findViewById(R.id.tv_filter);
        tv_all.setOnClickListener(onClickListener);
        tv_new.setOnClickListener(onClickListener);
        tv_price.setOnClickListener(onClickListener);
        tv_class.setOnClickListener(onClickListener);
        tv_filter.setOnClickListener(onClickListener);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);//设置可以下拉刷新
        refreshLayout.setEnableLoadMore(false);//设置不能加载更多
        recyclerView = rootView.findViewById(R.id.recycle_view);
        lists = new ArrayList<>();
        linearAdapter = new AuctionListAdapter(getActivity(), R.layout.amain_item_goods_list_linear, lists);
        linearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AuctionDetailActivity.startAuctionDetailsActivity(getActivity(), lists.get(position).getItem_id());
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

    @Override
    public void initData() {
        getListData();
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


    private void showDrawerLayout() {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            drawerLayout.closeDrawer(Gravity.END);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
            drawerLayout.openDrawer(Gravity.END);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

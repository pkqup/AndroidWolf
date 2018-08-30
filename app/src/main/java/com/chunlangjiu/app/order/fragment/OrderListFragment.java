package com.chunlangjiu.app.order.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.adapter.OrderListAdapter;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderListFragment extends BaseFragment {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView listView;
    private View rlLoading;

    private boolean isVisiable;
    private boolean isLoaded;
    private boolean initViewFinished;
    private String status;
    private CompositeDisposable disposable;

    private ArrayList<OrderListBean.ListBean> listBeans;
    private OrderListAdapter orderListAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.order_fragment_orderlist, container, true);
    }

    @Override
    public void initView() {
        disposable = new CompositeDisposable();

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        listView = rootView.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rlLoading = rootView.findViewById(R.id.rlLoading);

        initViewFinished = true;
    }

    @Override
    public void initData() {
        listBeans = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getActivity(), R.layout.order_adapter_list_item, listBeans);
        listView.setAdapter(orderListAdapter);

        delayLoad();
    }

    //判断是否展示—与ViewPager连用，进行左右切换
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisiable = isVisibleToUser;
        delayLoad();
    }

    private void delayLoad() {
        if (isVisiable && initViewFinished && !isLoaded) {
            isLoaded = true;
            //展示
            Bundle arguments = getArguments();
            if (null != arguments) {
                int type = arguments.getInt(OrderParams.TYPE, 0);
                int target = arguments.getInt(OrderParams.TARGET, 0);
                loadData(type, target);
            }
        }
    }

    private void loadData(int type, int target) {
        switch (type) {
            case 0:
                switch (target) {
                    case 0:
                        break;
                    case 1:
                        status = OrderParams.WAIT_BUYER_PAY;
                        break;
                    case 2:
                        status = OrderParams.WAIT_RATE;
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
            case 1:
                break;
        }
        if (!TextUtils.isEmpty(status)) {
            disposable.add(ApiUtils.getInstance().getOrderLists(status, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                        @Override
                        public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                            rlLoading.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);

                            listBeans.clear();
                            listBeans.addAll(orderListBeanResultBean.getData().getList());
                            orderListAdapter.notifyDataSetChanged();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            rlLoading.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);
                        }
                    }));
        }
    }

}

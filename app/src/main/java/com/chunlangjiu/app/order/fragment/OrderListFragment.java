package com.chunlangjiu.app.order.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.goods.activity.ShopMainActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.activity.OrderDetailActivity;
import com.chunlangjiu.app.order.activity.OrderEvaluationDetailActivity;
import com.chunlangjiu.app.order.activity.OrderMainActivity;
import com.chunlangjiu.app.order.adapter.OrderListAdapter;
import com.chunlangjiu.app.order.bean.CancelReasonBean;
import com.chunlangjiu.app.order.bean.LogisticsBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.dialog.CancelOrderDialog;
import com.chunlangjiu.app.order.dialog.ChooseExpressDialog;
import com.chunlangjiu.app.order.dialog.ChooseExpressSellerDialog;
import com.chunlangjiu.app.order.dialog.SellerCancelOrderDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderListFragment extends BaseFragment {
    private OrderMainActivity activity;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView listView;
    private View rlLoading;

    private boolean isVisible;
    private boolean isLoaded;
    private boolean initViewFinished;
    private String status;
    private CompositeDisposable disposable;

    private ArrayList<OrderListBean.ListBean> listBeans;
    private OrderListAdapter orderListAdapter;

    private int type = 0;
    private int target = 0;

    private int pageNo = 1;

    private CancelOrderDialog cancelOrderDialog;
    private String tid;
    private ChooseExpressDialog chooseExpressDialog;
    private ChooseExpressSellerDialog chooseExpressSellerDialog;
    private String aftersales_bn;
    private SellerCancelOrderDialog sellerCancelOrderDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OrderMainActivity) context;
    }

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.order_fragment_orderlist, container, true);
    }

    @Override
    public void initView() {
        disposable = new CompositeDisposable();

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                loadData();
            }
        });
        listView = rootView.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rlLoading = rootView.findViewById(R.id.rlLoading);

        initViewFinished = true;
        EventManager.getInstance().registerListener(onNotifyListener);
    }

    @Override
    public void initData() {
        listBeans = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getActivity(), R.layout.order_adapter_list_item, listBeans);
        orderListAdapter.setOnClickListener(onClickListener);
        listView.setAdapter(orderListAdapter);

        delayLoad();
    }

    //判断是否展示—与ViewPager连用，进行左右切换
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        delayLoad();
    }

    private void delayLoad() {
        if (isVisible && initViewFinished && !isLoaded) {
            isLoaded = true;
            Bundle arguments = getArguments();
            if (null != arguments) {
                type = arguments.getInt(OrderParams.TYPE, 0);
                target = arguments.getInt(OrderParams.TARGET, 0);
                orderListAdapter.setType(type);
                loadData();
            }
        }
    }

    private void loadData() {
        switch (type) {
            case 0:
                switch (target) {
                    case 0:
                        break;
                    case 1:
                        status = OrderParams.WAIT_BUYER_PAY;
                        break;
                    case 2:
                        status = OrderParams.WAIT_SELLER_SEND_GOODS;
                        break;
                    case 3:
                        status = OrderParams.WAIT_BUYER_CONFIRM_GOODS;
                        break;
                    case 4:
                        status = OrderParams.TRADE_FINISHED;
                        break;
                }
                getNormalOrderList();
                break;
            case 1:
                break;
            case 2:
                switch (target) {
                    case 0:
                        break;
                    case 1:
                        status = "0";
                        break;
                    case 2:
                        status = "1";
                        break;
                    case 3:
                        status = "2";
                        break;
                    case 4:
                        status = "3";
                        break;
                }
                getAfterSaleOrderList();
                break;
            case 3:
                switch (target) {
                    case 0:
                        break;
                    case 1:
                        status = OrderParams.WAIT_BUYER_PAY;
                        break;
                    case 2:
                        status = OrderParams.WAIT_SELLER_SEND_GOODS;
                        break;
                    case 3:
                        status = OrderParams.WAIT_BUYER_CONFIRM_GOODS;
                        break;
                    case 4:
                        status = OrderParams.TRADE_FINISHED;
                        break;
                }
                getSellerNormalOrderList();
                break;
        }

    }

    private void getNormalOrderList() {
        disposable.add(ApiUtils.getInstance().getOrderLists(status, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                    @Override
                    public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        if (pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            listBeans.addAll(orderListBeanResultBean.getData().getList());
                            orderListAdapter.notifyDataSetChanged();
                        } else if (0 != orderListBeanResultBean.getErrorcode()) {
                            if (1 < pageNo) {
                                pageNo--;
                            }
                        }
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        if (1 < pageNo) {
                            pageNo--;
                        }
                    }
                }));
    }

    private void getAfterSaleOrderList() {
        disposable.add(ApiUtils.getInstance().getAfterSaleOrderList(status, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                    @Override
                    public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        if (pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            listBeans.addAll(orderListBeanResultBean.getData().getList());
                            orderListAdapter.notifyDataSetChanged();
                        } else if (0 != orderListBeanResultBean.getErrorcode()) {
                            if (1 < pageNo) {
                                pageNo--;
                            }
                        }
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        if (1 < pageNo) {
                            pageNo--;
                        }
                    }
                }));
    }

    private void getSellerNormalOrderList() {
        disposable.add(ApiUtils.getInstance().getSellerOrderLists(status, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                    @Override
                    public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        switch (type) {
                            case 0:
                            case 1:
                                if (pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                                    refreshLayout.setEnableLoadMore(false);
                                } else {
                                    refreshLayout.setEnableLoadMore(true);
                                }
                                break;
                            case 3:
                                if (pageNo == orderListBeanResultBean.getData().getCount() / 10 + 1) {
                                    refreshLayout.setEnableLoadMore(false);
                                } else {
                                    refreshLayout.setEnableLoadMore(true);
                                }
                                break;
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            listBeans.addAll(orderListBeanResultBean.getData().getList());
                            orderListAdapter.notifyDataSetChanged();
                        } else if (0 != orderListBeanResultBean.getErrorcode()) {
                            if (1 < pageNo) {
                                pageNo--;
                            }
                        }
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        if (1 < pageNo) {
                            pageNo--;
                        }
                    }
                }));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llStore:
                    ShopMainActivity.startShopMainActivity(getActivity(), String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getShop_id()));
                    break;
                case R.id.llProducts:
                case R.id.llBottom:
                    toOrderDetailActivity(view);
                    break;
                case R.id.tv1:
                    switch (type) {
                        case 0:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case OrderParams.WAIT_BUYER_PAY:
                                    tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                                    getCancelReason();
                                    break;
                            }
                            break;
                        case 3:
                            tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                            getSellerCancelReason();
                            break;
                    }
                    break;
                case R.id.tv2:
                    switch (type) {
                        case 0:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case OrderParams.WAIT_BUYER_CONFIRM_GOODS:
                                    tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                                    confirmReceipt();
                                    break;
                                case OrderParams.TRADE_FINISHED:
                                    OrderListBean.ListBean listBean = listBeans.get(Integer.parseInt(view.getTag().toString()));
                                    Intent intent = new Intent(getActivity(), OrderEvaluationDetailActivity.class);
                                    intent.putExtra(OrderParams.PRODUCTS, listBean);
                                    startActivity(intent);
                                    break;
                                case OrderParams.WAIT_SELLER_SEND_GOODS:
                                    tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                                    getCancelReason();
                                    break;
                                case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                                    //删除订单
                                    tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                                    delete();
                                    break;
                            }
                            break;
                        case 2:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case "1":
                                    aftersales_bn = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getAftersales_bn());
                                    getLogisticsList();
                                    break;
                            }
                            break;
                        case 3:
                            tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                            getSellerLogisticsList();
                            break;
                    }
                    break;
            }
        }
    };

    private void getCancelReason() {
        disposable.add(ApiUtils.getInstance().getCancelReason()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CancelReasonBean>>() {
                    @Override
                    public void accept(ResultBean<CancelReasonBean> cancelReasonBeanResultBean) throws Exception {
                        List<String> list = cancelReasonBeanResultBean.getData().getList();
                        if (null == cancelOrderDialog) {
                            cancelOrderDialog = new CancelOrderDialog(activity, list, tid);
                            cancelOrderDialog.setCancelCallBack(new CancelOrderDialog.CancelCallBack() {
                                @Override
                                public void cancelSuccess() {
                                    refreshLayout.autoRefresh();
                                }
                            });
                        } else {
                            cancelOrderDialog.setData(list, tid);
                        }
                        cancelOrderDialog.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private void confirmReceipt() {
        disposable.add(ApiUtils.getInstance().confirmReceipt(tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        if (0 == resultBean.getErrorcode()) {
                            ToastUtils.showShort("确认收货成功");
                            refreshLayout.autoRefresh();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void delete() {
        disposable.add(ApiUtils.getInstance().delete(tid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        if (0 == resultBean.getErrorcode()) {
                            ToastUtils.showShort("删除订单成功");
                            refreshLayout.autoRefresh();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void toOrderDetailActivity(View view) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        int position = Integer.parseInt(view.getTag().toString());
        intent.putExtra(OrderParams.ORDERID, listBeans.get(position).getTid());
        if (null != listBeans.get(position).getSku()) {
            intent.putExtra(OrderParams.OID, listBeans.get(position).getSku().getOid());
            intent.putExtra(OrderParams.AFTERSALESBN, listBeans.get(position).getAftersales_bn());
        }
        OrderListBean.ListBean listBean = listBeans.get(Integer.parseInt(view.getTag().toString()));
        intent.putExtra(OrderParams.PRODUCTS, listBean);
        intent.putExtra(OrderParams.TYPE, type);
        startActivity(intent);
    }

    private void getLogisticsList() {
        disposable.add(ApiUtils.getInstance().getLogisticsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<LogisticsBean>>() {
                    @Override
                    public void accept(ResultBean<LogisticsBean> logisticsBeanResultBean) throws Exception {
                        if (0 == logisticsBeanResultBean.getErrorcode()) {
                            LogisticsBean data = logisticsBeanResultBean.getData();
                            if (null == chooseExpressDialog) {
                                chooseExpressDialog = new ChooseExpressDialog(activity, data.getList(), aftersales_bn);
                                chooseExpressDialog.setCallBack(new ChooseExpressDialog.CallBack() {
                                    @Override
                                    public void sendExpressSuccess() {
                                        refreshLayout.autoRefresh();
                                    }
                                });
                            } else {
                                chooseExpressDialog.setData(data.getList(), aftersales_bn);
                            }
                            chooseExpressDialog.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getSellerLogisticsList() {
        disposable.add(ApiUtils.getInstance().getLogisticsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<LogisticsBean>>() {
                    @Override
                    public void accept(ResultBean<LogisticsBean> logisticsBeanResultBean) throws Exception {
                        if (0 == logisticsBeanResultBean.getErrorcode()) {
                            LogisticsBean data = logisticsBeanResultBean.getData();
                            if (null == chooseExpressSellerDialog) {
                                chooseExpressSellerDialog = new ChooseExpressSellerDialog(activity, data.getList(), tid);
                                chooseExpressSellerDialog.setCallBack(new ChooseExpressSellerDialog.CallBack() {
                                    @Override
                                    public void sendExpressSuccess() {
                                        refreshLayout.autoRefresh();
                                    }
                                });
                            } else {
                                chooseExpressSellerDialog.setData(data.getList(), aftersales_bn);
                            }
                            chooseExpressSellerDialog.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getSellerCancelReason() {
        disposable.add(ApiUtils.getInstance().getSellerCancelReason()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CancelReasonBean>>() {
                    @Override
                    public void accept(ResultBean<CancelReasonBean> cancelReasonBeanResultBean) throws Exception {
                        List<String> list = cancelReasonBeanResultBean.getData().getList();
                        if (null == sellerCancelOrderDialog) {
                            sellerCancelOrderDialog = new SellerCancelOrderDialog(activity, list, tid);
                            sellerCancelOrderDialog.setCancelCallBack(new SellerCancelOrderDialog.CancelCallBack() {
                                @Override
                                public void cancelSuccess() {
                                    refreshLayout.autoRefresh();
                                }
                            });
                        } else {
                            sellerCancelOrderDialog.setData(list, tid);
                        }
                        sellerCancelOrderDialog.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
    }

    private EventManager.OnNotifyListener onNotifyListener = new EventManager.OnNotifyListener() {
        @Override
        public void onNotify(Object object, String eventTag) {
            if (OrderParams.REFRESH_ORDER_LIST.equals(eventTag)) {
                refreshLayout.autoRefresh();
            }
        }
    };

}

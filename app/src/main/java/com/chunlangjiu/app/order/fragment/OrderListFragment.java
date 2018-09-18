package com.chunlangjiu.app.order.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.goods.activity.ConfirmOrderActivity;
import com.chunlangjiu.app.goods.activity.ShopMainActivity;
import com.chunlangjiu.app.goods.bean.CreateOrderBean;
import com.chunlangjiu.app.goods.bean.PaymentBean;
import com.chunlangjiu.app.goods.dialog.PayDialog;
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
import com.chunlangjiu.app.order.dialog.RefundAfterSaleOrderDialog;
import com.chunlangjiu.app.order.dialog.SellerCancelOrderDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.chunlangjiu.app.util.ConstantMsg;
import com.chunlangjiu.app.util.PayResult;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.net.HttpUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private String status; // 0 待处理 1 处理中 2 已处理 3 已驳回
    private String progress;// 0 等待商家处理 1 商家接受申请，等待消费者回寄 2 消费者回寄，等待商家收货确认 3 商家已驳回 4 商家已处理 5 商家确认收货 6 平台驳回退款申请 7 平台已处理退款申请 8 同意退款,提交到平台,等待平台处理
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
    private RefundAfterSaleOrderDialog refundAfterSaleOrderDialog;

    private List<PaymentBean.PaymentInfo> payList;
    private int payMehtod;//默认微信支付
    private String payMehtodId;//支付方式类型
    private PayDialog payDialog;
    private IWXAPI wxapi;
    private static final int SDK_PAY_FLAG = 1;
    private ResultBean<CreateOrderBean> createOrderBeanResultBean;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OrderMainActivity) context;
        wxapi = WXAPIFactory.createWXAPI(activity, null);
        wxapi.registerApp("wx0e1869b241d7234f");
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
                        status = "";
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
                        status = "";
                        progress = "";
                        break;
                    case 1:
                        status = "0";
                        progress = "0";
                        break;
                    case 2:
                        status = "1";
                        progress = "1";
                        break;
                    case 3:
                        status = "1";
                        progress = "2";
                        break;
                    case 4:
                        status = "2";
                        progress = "";
                        break;
                }
                getAfterSaleOrderList();
                break;
            case 3:
                switch (target) {
                    case 0:
                        status = "";
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
            case 4:
                switch (target) {
                    case 0:
                        status = "";
                        progress = "";
                        break;
                    case 1:
                        status = "0";
                        progress = "0";
                        break;
                    case 2:
                        status = "1";
                        progress = "1";
                        break;
                    case 3:
                        status = "1";
                        progress = "2";
                        break;
                    case 4:
                        status = "2";
                        progress = "";
                        break;
                }
                getSellerAfterSaleOrderList();
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
                        if (null == orderListBeanResultBean.getData().getPagers() || pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            if (null != orderListBeanResultBean.getData() && null != orderListBeanResultBean.getData().getList()) {
                                listBeans.addAll(orderListBeanResultBean.getData().getList());
                            }
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
        disposable.add(ApiUtils.getInstance().getAfterSaleOrderList(status, progress, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                    @Override
                    public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        if (null == orderListBeanResultBean.getData().getPagers() || pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            if (null != orderListBeanResultBean.getData() && null != orderListBeanResultBean.getData().getList()) {
                                listBeans.addAll(orderListBeanResultBean.getData().getList());
                            }
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
                            if (null != orderListBeanResultBean.getData() && null != orderListBeanResultBean.getData().getList()) {
                                listBeans.addAll(orderListBeanResultBean.getData().getList());
                            }
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

    private void getSellerAfterSaleOrderList() {
        disposable.add(ApiUtils.getInstance().getSellerAfterSaleOrderList(status, progress, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<OrderListBean>>() {
                    @Override
                    public void accept(ResultBean<OrderListBean> orderListBeanResultBean) throws Exception {
                        rlLoading.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        if (null == orderListBeanResultBean.getData().getPagers() || pageNo == orderListBeanResultBean.getData().getPagers().getTotal() / 10 + 1) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            if (1 == pageNo) {
                                listBeans.clear();
                            }
                            if (null != orderListBeanResultBean.getData() && null != orderListBeanResultBean.getData().getList()) {
                                listBeans.addAll(orderListBeanResultBean.getData().getList());
                            }
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
                        case 4:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case "0":
                                    aftersales_bn = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getAftersales_bn());
                                    if (null == refundAfterSaleOrderDialog) {
                                        refundAfterSaleOrderDialog = new RefundAfterSaleOrderDialog(activity);
                                        refundAfterSaleOrderDialog.setCallBack(new RefundAfterSaleOrderDialog.CallBack() {
                                            @Override
                                            public void confirm(String reason) {
                                                disposable.add(ApiUtils.getInstance().applySellerAfterSale(aftersales_bn, "true", "", reason)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Consumer<ResultBean>() {
                                                            @Override
                                                            public void accept(ResultBean resultBean) throws Exception {
                                                                if (0 == resultBean.getErrorcode()) {
                                                                    ToastUtils.showShort("拒绝申请成功");
                                                                    refreshLayout.autoRefresh();
                                                                }
                                                            }
                                                        }, new Consumer<Throwable>() {
                                                            @Override
                                                            public void accept(Throwable throwable) throws Exception {

                                                            }
                                                        }));
                                            }
                                        });
                                    }
                                    refundAfterSaleOrderDialog.show();
                                    break;
                            }
                            break;
                    }
                    break;
                case R.id.tv2:
                    switch (type) {
                        case 0:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case OrderParams.WAIT_BUYER_PAY:
                                    tid = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getTid());
                                    repay();
                                    break;
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
                        case 4:
                            switch (listBeans.get(Integer.parseInt(view.getTag().toString())).getStatus()) {
                                case "0":
                                    aftersales_bn = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getAftersales_bn());
                                    disposable.add(ApiUtils.getInstance().applySellerAfterSale(aftersales_bn, "true", "", "")
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<ResultBean>() {
                                                @Override
                                                public void accept(ResultBean resultBean) throws Exception {
                                                    if (0 == resultBean.getErrorcode()) {
                                                        ToastUtils.showShort("同意申请成功");
                                                        refreshLayout.autoRefresh();
                                                    }
                                                }
                                            }, new Consumer<Throwable>() {
                                                @Override
                                                public void accept(Throwable throwable) throws Exception {

                                                }
                                            }));
                                    break;
                                case "1":
                                    if ("2".equals(listBeans.get(Integer.parseInt(view.getTag().toString())).getProgress())) {
                                        aftersales_bn = String.valueOf(listBeans.get(Integer.parseInt(view.getTag().toString())).getAftersales_bn());
                                        disposable.add(ApiUtils.getInstance().applySellerAfterSale(aftersales_bn, "true",
                                                new BigDecimal(listBeans.get(Integer.parseInt(view.getTag().toString())).getSku().getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), "")
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Consumer<ResultBean>() {
                                                    @Override
                                                    public void accept(ResultBean resultBean) throws Exception {
                                                        if (0 == resultBean.getErrorcode()) {
                                                            ToastUtils.showShort("确认收货并同意退款成功");
                                                            refreshLayout.autoRefresh();
                                                        }
                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {

                                                    }
                                                }));
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
    };

    private void repay() {
        disposable.add(ApiUtils.getInstance().repay(tid, "true")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CreateOrderBean>>() {
                    @Override
                    public void accept(final ResultBean<CreateOrderBean> resultBean) throws Exception {
                        if (0 == resultBean.getErrorcode()) {
                            createOrderBeanResultBean = resultBean;
                            getPayment();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getPayment() {
        disposable.add(ApiUtils.getInstance().getPayment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<PaymentBean>>() {
                    @Override
                    public void accept(ResultBean<PaymentBean> paymentBeanResultBean) throws Exception {
                        hideLoadingDialog();
                        payList = paymentBeanResultBean.getData().getList();
                        if (payList == null || payList.size() == 0) {
                            ToastUtils.showShort("获取支付方式失败");
                        } else {
                            payDialog = new PayDialog(activity, payList);
                            payDialog.setCallBack(new PayDialog.CallBack() {
                                @Override
                                public void choicePayMethod(int payMethod, String payMethodId) {
                                    payDo(payMethod, payMethodId);
                                }
                            });
                            payDialog.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideLoadingDialog();
                    }
                }));
    }

    private void payDo(int payMethod, String payMethodId) {
        this.payMehtod = payMethod;
        this.payMehtodId = payMethodId;
        disposable.add(ApiUtils.getInstance().payDo(createOrderBeanResultBean.getData().getPayment_id(), payMehtodId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        hideLoadingDialog();
                        invokePay(resultBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideLoadingDialog();
                    }
                }));
    }

    private void invokePay(ResultBean data) {
        switch (payMehtod) {
            case 0:
                invokeWeixinPay(data);
                break;
            case 1:
                invokeZhifubaoPay(data);
                break;
            case 2:
                invokeYuePay(data);
                break;
            case 3:
                invokeDaePay(data);
                break;
        }
    }

    private void invokeWeixinPay(ResultBean data) {
        PayReq request = new PayReq();
        request.appId = "wx0e1869b241d7234f";
        request.partnerId = data.getPartnerid();
        request.prepayId = data.getPrepayid();
        request.packageValue = data.getPackageName();
        request.nonceStr = data.getNoncestr();
        request.timeStamp = data.getTimestamp();
        request.sign = data.getSign();
        wxapi.sendReq(request);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                    refreshLayout.autoRefresh();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void invokeZhifubaoPay(ResultBean data) {
        final String url = data.getUrl();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> stringStringMap = alipay.payV2(url, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = stringStringMap;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void invokeYuePay(ResultBean data) {

    }

    private void invokeDaePay(ResultBean data) {

    }

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
        HttpUtils.USER_TOKEN = true;
        disposable.add(ApiUtils.getInstance().getLogisticsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<LogisticsBean>>() {
                    @Override
                    public void accept(ResultBean<LogisticsBean> logisticsBeanResultBean) throws Exception {
                        HttpUtils.USER_TOKEN = false;
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
        disposable.dispose();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
    }

    private void weixinPaySuccess(Object object, String eventTag) {
        if (eventTag.equals(ConstantMsg.WEIXIN_PAY_CALLBACK)) {
            int code = (int) object;
            if (code == 0) {
                //支付成功
                ToastUtils.showShort("支付成功");
                refreshLayout.autoRefresh();
                EventManager.getInstance().notify(null, ConstantMsg.UPDATE_CART_LIST);
            } else if (code == -1) {
                //支付错误
                ToastUtils.showShort("支付失败");
            } else if (code == -2) {
                //支付取消
                ToastUtils.showShort("支付失败");
            }
        }
    }

    private EventManager.OnNotifyListener onNotifyListener = new EventManager.OnNotifyListener() {
        @Override
        public void onNotify(Object object, String eventTag) {
            if (OrderParams.REFRESH_ORDER_LIST.equals(eventTag)) {
                refreshLayout.autoRefresh();
            } else {
                weixinPaySuccess(object, eventTag);
            }
        }
    };

}

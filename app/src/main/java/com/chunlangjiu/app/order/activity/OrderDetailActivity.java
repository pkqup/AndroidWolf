package com.chunlangjiu.app.order.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.goods.bean.CreateOrderBean;
import com.chunlangjiu.app.goods.bean.PaymentBean;
import com.chunlangjiu.app.goods.dialog.PayDialog;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.bean.CancelOrderResultBean;
import com.chunlangjiu.app.order.bean.CancelReasonBean;
import com.chunlangjiu.app.order.bean.LogisticsBean;
import com.chunlangjiu.app.order.bean.OrderDetailBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.dialog.CancelOrderDialog;
import com.chunlangjiu.app.order.dialog.ChooseExpressDialog;
import com.chunlangjiu.app.order.dialog.ChooseExpressSellerDialog;
import com.chunlangjiu.app.order.dialog.SellerCancelOrderDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.chunlangjiu.app.util.ConstantMsg;
import com.chunlangjiu.app.util.PayResult;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.HttpUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailActivity extends BaseActivity {
    private CompositeDisposable disposable;

    private OrderDetailBean orderDetailBean;
    private ClipboardManager myClipboard;

    @BindView(R.id.rlLoading)
    View rlLoading;
    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.imgStore)
    ImageView imgStore;
    @BindView(R.id.tvStore)
    TextView tvStore;
    @BindView(R.id.tvOrderId)
    TextView tvOrderId;
    @BindView(R.id.tvCopy)
    TextView tvCopy;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvPayType)
    TextView tvPayType;
    @BindView(R.id.tvPayTime)
    TextView tvPayTime;
    @BindView(R.id.llSendTime)
    LinearLayout llSendTime;
    @BindView(R.id.tvSendTime)
    TextView tvSendTime;
    @BindView(R.id.llFinishTime)
    LinearLayout llFinishTime;
    @BindView(R.id.tvFinishTime)
    TextView tvFinishTime;
    @BindView(R.id.tvUserInfo)
    TextView tvUserInfo;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.llProducts)
    LinearLayout llProducts;
    @BindView(R.id.llPayTime)
    LinearLayout llPayTime;
    @BindView(R.id.llOrderTitleRightContent)
    LinearLayout llOrderTitleRightContent;
    @BindView(R.id.tvRightContentDesc)
    TextView tvRightContentDesc;
    @BindView(R.id.tvRightContent)
    TextView tvRightContent;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.llPayType)
    LinearLayout llPayType;
    @BindView(R.id.llAfterSaleTme)
    LinearLayout llAfterSaleTme;
    @BindView(R.id.tvAfterSaleCreateTime)
    TextView tvAfterSaleCreateTime;
    @BindView(R.id.llAfterSaleSendTime)
    LinearLayout llAfterSaleSendTime;
    @BindView(R.id.tvAfterSaleSendTime)
    TextView tvAfterSaleSendTime;
    @BindView(R.id.llAfterSalePayTime)
    LinearLayout llAfterSalePayTime;
    @BindView(R.id.tvAfterSalePayTime)
    TextView tvAfterSalePayTime;
    @BindView(R.id.countdownView)
    CountdownView countdownView;
    @BindView(R.id.llUserInfo)
    LinearLayout llUserInfo;
    @BindView(R.id.llSendPrice)
    LinearLayout llSendPrice;
    @BindView(R.id.llTotalPrice)
    LinearLayout llTotalPrice;
    @BindView(R.id.tvPaymentTips)
    TextView tvPaymentTips;

    private int type = 0;
    private String oid;
    private String aftersalesBn;

    private CancelOrderDialog cancelOrderDialog;
    private String tid;
    private ChooseExpressDialog chooseExpressDialog;
    private String aftersales_bn;
    private SellerCancelOrderDialog sellerCancelOrderDialog;
    private ChooseExpressSellerDialog chooseExpressSellerDialog;

    private List<PaymentBean.PaymentInfo> payList;
    private int payMehtod;//默认微信支付
    private String payMehtodId;//支付方式类型
    private PayDialog payDialog;
    private IWXAPI wxapi;
    private static final int SDK_PAY_FLAG = 1;
    private ResultBean<CreateOrderBean> createOrderBeanResultBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_detail);

        initData();
    }


    @Override
    public void setTitleView() {
        titleImgLeft.setOnClickListener(onClickListener);
        titleName.setText("订单详情");
    }

    private void initData() {
        EventManager.getInstance().registerListener(onNotifyListener);
        wxapi = WXAPIFactory.createWXAPI(this, null);
        wxapi.registerApp("wx0e1869b241d7234f");

        type = getIntent().getIntExtra(OrderParams.TYPE, 0);
        oid = String.valueOf(getIntent().getLongExtra(OrderParams.OID, 0));
        aftersalesBn = String.valueOf(getIntent().getLongExtra(OrderParams.AFTERSALESBN, 0));

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        disposable = new CompositeDisposable();
        switch (type) {
            case 0:
                disposable.add(ApiUtils.getInstance().getOrderDetail(String.valueOf(getIntent().getLongExtra(OrderParams.ORDERID, 0)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultBean<OrderDetailBean>>() {
                            @Override
                            public void accept(ResultBean<OrderDetailBean> orderDetailBeanResultBean) throws Exception {
                                orderDetailBean = orderDetailBeanResultBean.getData();
                                processData();

                                rlLoading.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                rlLoading.setVisibility(View.GONE);

                                Log.e(OrderDetailActivity.class.getSimpleName(), throwable.toString());
                            }
                        }));
                break;
            case 1:
                disposable.add(ApiUtils.getInstance().getAuctionOrderDetail(String.valueOf(getIntent().getLongExtra(OrderParams.AUCTIONITEMID, 0)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultBean<OrderDetailBean>>() {
                            @Override
                            public void accept(ResultBean<OrderDetailBean> orderDetailBeanResultBean) throws Exception {
                                orderDetailBean = orderDetailBeanResultBean.getData();
                                processData();

                                rlLoading.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                rlLoading.setVisibility(View.GONE);

                                Log.e(OrderDetailActivity.class.getSimpleName(), throwable.toString());
                            }
                        }));
                break;
            case 2:
                disposable.add(ApiUtils.getInstance().getAfterSaleOrderDetail(aftersalesBn, oid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultBean<OrderDetailBean>>() {
                            @Override
                            public void accept(ResultBean<OrderDetailBean> orderDetailBeanResultBean) throws Exception {
                                orderDetailBean = orderDetailBeanResultBean.getData();
                                processData();
                                rlLoading.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                rlLoading.setVisibility(View.GONE);

                                Log.e(OrderDetailActivity.class.getSimpleName(), throwable.toString());
                            }
                        }));
                break;
            case 3:
                disposable.add(ApiUtils.getInstance().getSellerOrderDetail(String.valueOf(getIntent().getLongExtra(OrderParams.ORDERID, 0)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultBean<OrderDetailBean>>() {
                            @Override
                            public void accept(ResultBean<OrderDetailBean> orderDetailBeanResultBean) throws Exception {
                                orderDetailBean = orderDetailBeanResultBean.getData();
                                processData();

                                rlLoading.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                rlLoading.setVisibility(View.GONE);

                                Log.e(OrderDetailActivity.class.getSimpleName(), throwable.toString());
                            }
                        }));
                break;
            case 4:
                disposable.add(ApiUtils.getInstance().getSellerAfterSaleOrderDetail(aftersalesBn, oid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultBean<OrderDetailBean>>() {
                            @Override
                            public void accept(ResultBean<OrderDetailBean> orderDetailBeanResultBean) throws Exception {
                                orderDetailBean = orderDetailBeanResultBean.getData();
                                processData();
                                rlLoading.setVisibility(View.GONE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                rlLoading.setVisibility(View.GONE);

                                Log.e(OrderDetailActivity.class.getSimpleName(), throwable.toString());
                            }
                        }));
                break;
        }
    }

    private void processData() {
        tvOrderStatus.setText(orderDetailBean.getStatus_desc());

        GlideUtils.loadImage(getApplicationContext(), orderDetailBean.getShoplogo(), imgStore);
        tvStore.setText(orderDetailBean.getShopname());

        tvOrderId.setText(String.valueOf(orderDetailBean.getTid()));
        tvCopy.setOnClickListener(onClickListener);
        tvCreateTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getCreated_time() + "000")));
        tvPayType.setText(orderDetailBean.getPay_name());

        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);

        switch (type) {
            case 0:
            case 3:
                switch (orderDetailBean.getStatus()) {
                    case OrderParams.WAIT_BUYER_PAY:
                        if (0 == type) {
                            tvRightContentDesc.setText("剩余支付时间：");
                            int close_time = orderDetailBean.getClose_time();
                            try {
                                int i = close_time * 1000;
                                countdownView.start(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tvRightContent.setVisibility(View.GONE);
                            tv1.setText("取消订单");
                            tv1.setVisibility(View.VISIBLE);
                            tv2.setText("去支付");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            tvRightContentDesc.setVisibility(View.GONE);
                            tvRightContent.setVisibility(View.GONE);
                            tv1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                        }
                        llPayTime.setVisibility(View.GONE);
                        llSendTime.setVisibility(View.GONE);
                        llFinishTime.setVisibility(View.GONE);
                        break;
                    case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                        if (0 == type) {
                            tv1.setText("删除订单");
                            tv2.setText("重新购买");
                            tv1.setVisibility(View.VISIBLE);
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            tv1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                        }
                        tvRightContentDesc.setText("取消原因：");
                        tvRightContent.setText(orderDetailBean.getCancel_reason());
                        llPayTime.setVisibility(View.GONE);
                        llSendTime.setVisibility(View.GONE);
                        llFinishTime.setVisibility(View.GONE);
                        countdownView.setVisibility(View.GONE);
                        break;
                    case OrderParams.WAIT_SELLER_SEND_GOODS:
                        if (0 == type) {
                            tv1.setVisibility(View.GONE);
                            tv2.setText("申请退款");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            if ("REFUND_PROCESS".equals(orderDetailBean.getCancel_status())) {
                                tv1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                            } else {
                                tv1.setText("无货");
                                tv1.setVisibility(View.VISIBLE);
                                tv2.setText("发货");
                                tv2.setVisibility(View.VISIBLE);
                            }
                        }
                        tvRightContentDesc.setVisibility(View.GONE);
                        tvRightContent.setVisibility(View.GONE);
                        tvPayTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getPay_time() + "000")));
                        llSendTime.setVisibility(View.GONE);
                        llFinishTime.setVisibility(View.GONE);
                        countdownView.setVisibility(View.GONE);
                        break;
                    case OrderParams.WAIT_BUYER_CONFIRM_GOODS:
                        tv1.setVisibility(View.GONE);
                        if (0 == type) {
                            tv2.setText("确认收货");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            tv2.setVisibility(View.GONE);
                        }
                        tvRightContentDesc.setVisibility(View.GONE);
                        tvRightContent.setVisibility(View.GONE);
                        tvPayTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getPay_time() + "000")));
                        tvSendTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getConsign_time() + "000")));
                        llFinishTime.setVisibility(View.GONE);
                        countdownView.setVisibility(View.GONE);
                        break;
                    case OrderParams.TRADE_FINISHED:
                        tv1.setVisibility(View.GONE);
                        if (0 == type) {
                            if (orderDetailBean.isIs_buyer_rate()) {
                                tv2.setVisibility(View.GONE);
                            } else {
                                tv2.setText("评价");
                                tv2.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv2.setVisibility(View.GONE);
                        }
                        tvRightContentDesc.setVisibility(View.GONE);
                        tvRightContent.setVisibility(View.GONE);
                        countdownView.setVisibility(View.GONE);
                        tvPayTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getPay_time() + "000")));
                        tvSendTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getConsign_time() + "000")));
                        tvFinishTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getEnd_time() + "000")));
                        break;
                }
                llAfterSaleTme.setVisibility(View.GONE);
                LayoutInflater inflater = LayoutInflater.from(this);
                List<OrderDetailBean.OrdersBean> orders = orderDetailBean.getOrders();
                for (int i = 0; i <= orders.size() - 1; i++) {
                    OrderDetailBean.OrdersBean orderBean = orders.get(i);
                    View inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
                    ImageView imgProduct = inflate.findViewById(R.id.imgProduct);
                    GlideUtils.loadImage(getApplicationContext(), orderBean.getPic_path(), imgProduct);
                    TextView tvProductName = inflate.findViewById(R.id.tvProductName);
                    tvProductName.setText(orderBean.getTitle());
                    TextView tvProductPrice = inflate.findViewById(R.id.tvProductPrice);
                    tvProductPrice.setText(String.format("¥%s", new BigDecimal(orderBean.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                    TextView tvProductDesc = inflate.findViewById(R.id.tvProductDesc);
                    tvProductDesc.setText(orderBean.getSpec_nature_info());
                    switch (type) {
                        case 0:
                            switch (orderDetailBean.getStatus()) {
                                case OrderParams.TRADE_FINISHED:
                                    TextView tvAfterSale = inflate.findViewById(R.id.tvAfterSale);
                                    tvAfterSale.setTag(i);
                                    tvAfterSale.setOnClickListener(onClickListener);
                                    tvAfterSale.setVisibility(View.VISIBLE);
                                    break;
                            }
                            break;
                    }
                    TextView tvProductNum = inflate.findViewById(R.id.tvProductNum);
                    tvProductNum.setText(String.format("x%d", orderBean.getNum()));
                    llProducts.addView(inflate);
                    if (llProducts.getChildCount() == orders.size()) {
                        View view_line = inflate.findViewById(R.id.view_line);
                        view_line.setVisibility(View.GONE);
                    }
                }
                tvPayment.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvPaymentTips.setText("实付金额：");

                tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
                tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
                tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                break;
            case 2:
            case 4:
                tvRightContentDesc.setVisibility(View.GONE);
                tvRightContent.setVisibility(View.GONE);
                countdownView.setVisibility(View.GONE);
                llPayTime.setVisibility(View.GONE);
                llSendTime.setVisibility(View.GONE);
                llFinishTime.setVisibility(View.GONE);
                llUserInfo.setVisibility(View.GONE);
                llSendPrice.setVisibility(View.GONE);
                llTotalPrice.setVisibility(View.GONE);
                switch (orderDetailBean.getStatus()) {
                    case "0":
                        llAfterSaleSendTime.setVisibility(View.GONE);
                        llAfterSalePayTime.setVisibility(View.GONE);
                        if (2 == type) {
                            tv1.setVisibility(View.GONE);
                            tv2.setText("撤销申请");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            tv1.setText("拒绝申请");
                            tv1.setVisibility(View.VISIBLE);
                            tv2.setText("同意申请");
                            tv2.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "1":
                        llAfterSaleSendTime.setVisibility(View.GONE);
                        llAfterSalePayTime.setVisibility(View.GONE);
                        if (2 == type) {
                            tv1.setText("撤销申请");
                            tv1.setVisibility(View.VISIBLE);
                            tv2.setText("退货发货");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            if ("2".equals(orderDetailBean.getProgress())) {
                                tv1.setVisibility(View.GONE);
                                tv2.setText("同意退款");
                                tv2.setVisibility(View.VISIBLE);
                            } else {
                                tv1.setVisibility(View.GONE);
                                tv2.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "2":
                        llAfterSalePayTime.setVisibility(View.GONE);
                        tv1.setVisibility(View.GONE);
                        tv2.setVisibility(View.GONE);
                        break;
                    case "3":
                        if (2 == type) {
                            tv1.setVisibility(View.GONE);
                            tv2.setText("删除");
                            tv2.setVisibility(View.VISIBLE);
                        } else {
                            tv1.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
                        }
                        break;
                }
                llPayType.setVisibility(View.GONE);
                tvRightContentDesc.setVisibility(View.GONE);
                tvAfterSaleCreateTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getModified_time() + "000")));

                inflater = LayoutInflater.from(this);
                OrderDetailBean.OrdersBean order = orderDetailBean.getOrder();
                View inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
                ImageView imgProduct = inflate.findViewById(R.id.imgProduct);
                GlideUtils.loadImage(getApplicationContext(), order.getPic_path(), imgProduct);
                TextView tvProductName = inflate.findViewById(R.id.tvProductName);
                tvProductName.setText(order.getTitle());
                TextView tvProductPrice = inflate.findViewById(R.id.tvProductPrice);
                tvProductPrice.setText(String.format("¥%s", new BigDecimal(order.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                TextView tvProductDesc = inflate.findViewById(R.id.tvProductDesc);
                tvProductDesc.setText(order.getSpec_nature_info());
                TextView tvProductNum = inflate.findViewById(R.id.tvProductNum);
                tvProductNum.setText(String.format("x%d", order.getNum()));
                llProducts.addView(inflate);
                View view_line = inflate.findViewById(R.id.view_line);
                view_line.setVisibility(View.GONE);
                tvPayment.setText(String.format("¥%s", new BigDecimal(order.getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvPaymentTips.setText("退款金额：");

                tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
                tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
                tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                break;
            case 1:
                tv1.setVisibility(View.GONE);
                switch (orderDetailBean.getStatus()) {
                    case "0":
                        tvRightContentDesc.setText("剩余支付时间：");
                        int close_time = orderDetailBean.getClose_time();
                        try {
                            int i = close_time * 1000;
                            countdownView.start(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tvRightContent.setVisibility(View.GONE);
                        tv2.setText("去付定金");
                        tv2.setVisibility(View.VISIBLE);
                        llPayTime.setVisibility(View.GONE);
                        llSendTime.setVisibility(View.GONE);
                        llFinishTime.setVisibility(View.GONE);
                        break;
                    case "1":
                        tv2.setText("修改出价");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    default:
                        tv2.setVisibility(View.GONE);
                        break;
                }
                llAfterSaleTme.setVisibility(View.GONE);

                inflater = LayoutInflater.from(this);
                inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
                imgProduct = inflate.findViewById(R.id.imgProduct);
                GlideUtils.loadImage(getApplicationContext(), orderDetailBean.getImage_default_id(), imgProduct);
                tvProductName = inflate.findViewById(R.id.tvProductName);
                tvProductName.setText(orderDetailBean.getTitle());
                tvProductPrice = inflate.findViewById(R.id.tvProductPrice);
                tvProductPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getCost_price()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvProductDesc = inflate.findViewById(R.id.tvProductDesc);
//                tvProductDesc.setText(orderDetailBean.getSpec_desc());
                tvProductNum = inflate.findViewById(R.id.tvProductNum);
                tvProductNum.setText("x1");
                llProducts.addView(inflate);
                view_line = inflate.findViewById(R.id.view_line);
                view_line.setVisibility(View.GONE);

                tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
                OrderDetailBean.DefaultAddressBean default_address = orderDetailBean.getDefault_address();
                tvUserInfo.setText(String.format("%s\u3000%s", default_address.getName(), default_address.getMobile()));
                tvAddress.setText(String.format("%s%s", default_address.getArea(), default_address.getAddr()));
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvCopy:
                    copy();
                    break;
                case R.id.tvAfterSale:
                    int position = Integer.parseInt(view.getTag().toString());
                    Intent intent = new Intent(OrderDetailActivity.this, OrderApplyForAfterSaleActivity.class);
                    intent.putExtra(OrderParams.PRODUCTS, orderDetailBean.getOrders().get(position));
                    intent.putExtra(OrderParams.ORDERID, String.valueOf(orderDetailBean.getTid()));
                    startActivity(intent);
                    break;
                case R.id.tv1:
                    switch (type) {
                        case 0:
                            switch (orderDetailBean.getStatus()) {
                                case OrderParams.WAIT_BUYER_PAY:
                                    tid = String.valueOf(orderDetailBean.getTid());
                                    getCancelReason();
                                    break;
                                case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                                    //删除订单
                                    tid = String.valueOf(orderDetailBean.getTid());
                                    delete();
                                    break;
                            }
                            break;
                        case 3:
                            tid = String.valueOf(orderDetailBean.getTid());
                            getSellerCancelReason();
                            break;
                    }
                    break;
                case R.id.tv2:
                    switch (type) {
                        case 0:
                            switch (orderDetailBean.getStatus()) {
                                case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                                case OrderParams.WAIT_BUYER_PAY:
                                    if (countdownView.getRemainTime() > 1000) {
                                        tid = String.valueOf(orderDetailBean.getTid());
                                        repay();
                                    } else {
                                        if (OrderParams.WAIT_BUYER_PAY.equals(orderDetailBean.getStatus())) {
                                            ToastUtils.showShort("已到支付结束时间");
                                        }
                                    }
                                    break;
                                case OrderParams.WAIT_BUYER_CONFIRM_GOODS:
                                    tid = String.valueOf(orderDetailBean.getTid());
                                    confirmReceipt();
                                    break;
                                case OrderParams.TRADE_FINISHED:
                                    intent = new Intent(OrderDetailActivity.this, OrderEvaluationDetailActivity.class);
                                    OrderListBean.ListBean listBean = (OrderListBean.ListBean) getIntent().getSerializableExtra(OrderParams.PRODUCTS);
                                    intent.putExtra(OrderParams.PRODUCTS, listBean);
                                    startActivity(intent);
                                    break;
                                case OrderParams.WAIT_SELLER_SEND_GOODS:
                                    tid = String.valueOf(orderDetailBean.getTid());
                                    getCancelReason();
                                    break;
                            }
                            break;
                        case 2:
                            switch (orderDetailBean.getStatus()) {
                                case "0":
                                    break;
                                case "1":
                                    aftersales_bn = String.valueOf(orderDetailBean.getAftersales_bn());
                                    getLogisticsList();
                                    break;
                                case "2":
                                    break;
                                case "3":
                                    break;
                            }
                            break;
                        case 3:
                            tid = String.valueOf(orderDetailBean.getTid());
                            getSellerLogisticsList();
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
                            payDialog = new PayDialog(OrderDetailActivity.this, payList);
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
                    Toast.makeText(OrderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    finish();
                    EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(OrderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void invokeZhifubaoPay(ResultBean data) {
        final String url = data.getUrl();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailActivity.this);
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

    private void weixinPaySuccess(Object object, String eventTag) {
        if (eventTag.equals(ConstantMsg.WEIXIN_PAY_CALLBACK)) {
            int code = (int) object;
            if (code == 0) {
                //支付成功
                ToastUtils.showShort("支付成功");
                finish();
                EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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
                finish();
                EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
            } else {
                weixinPaySuccess(object, eventTag);
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
                        String s = String.valueOf(orderDetailBean.getTid());
                        if (null == cancelOrderDialog) {
                            cancelOrderDialog = new CancelOrderDialog(OrderDetailActivity.this, list, s);
                            cancelOrderDialog.setCancelCallBack(new CancelOrderDialog.CancelCallBack() {
                                @Override
                                public void cancelSuccess() {
                                    initData();
                                    EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
                                }
                            });
                        } else {
                            cancelOrderDialog.setData(list, s);
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
                            finish();
                            EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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
                            finish();
                            EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
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
                                chooseExpressDialog = new ChooseExpressDialog(OrderDetailActivity.this, data.getList(), aftersales_bn);
                                chooseExpressDialog.setCallBack(new ChooseExpressDialog.CallBack() {
                                    @Override
                                    public void sendExpressSuccess() {
                                        finish();
                                        EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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

    private void getSellerCancelReason() {
        disposable.add(ApiUtils.getInstance().getSellerCancelReason()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CancelReasonBean>>() {
                    @Override
                    public void accept(ResultBean<CancelReasonBean> cancelReasonBeanResultBean) throws Exception {
                        List<String> list = cancelReasonBeanResultBean.getData().getList();
                        if (null == sellerCancelOrderDialog) {
                            sellerCancelOrderDialog = new SellerCancelOrderDialog(OrderDetailActivity.this, list, tid);
                            sellerCancelOrderDialog.setCancelCallBack(new SellerCancelOrderDialog.CancelCallBack() {
                                @Override
                                public void cancelSuccess() {
                                    finish();
                                    EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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
                                chooseExpressSellerDialog = new ChooseExpressSellerDialog(OrderDetailActivity.this, data.getList(), tid);
                                chooseExpressSellerDialog.setCallBack(new ChooseExpressSellerDialog.CallBack() {
                                    @Override
                                    public void sendExpressSuccess() {
                                        finish();
                                        EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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

    public void copy() {
        ClipData text = ClipData.newPlainText("chunLangOrderId", String.valueOf(orderDetailBean.getTid()));
        myClipboard.setPrimaryClip(text);
        ToastUtils.showShort("订单号已复制");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
    }
}

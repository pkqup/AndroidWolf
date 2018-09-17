package com.chunlangjiu.app.order.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.bean.CancelOrderResultBean;
import com.chunlangjiu.app.order.bean.CancelReasonBean;
import com.chunlangjiu.app.order.bean.LogisticsBean;
import com.chunlangjiu.app.order.bean.OrderDetailBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.dialog.CancelOrderDialog;
import com.chunlangjiu.app.order.dialog.ChooseExpressDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
                break;
            case 2:
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

                        tv1.setVisibility(View.GONE);
                        tv2.setText("撤销申请");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        llAfterSaleSendTime.setVisibility(View.GONE);
                        llAfterSalePayTime.setVisibility(View.GONE);

                        tv1.setText("撤销申请");
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setText("退货发货");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        llAfterSalePayTime.setVisibility(View.GONE);

                        tv1.setVisibility(View.GONE);
                        tv2.setVisibility(View.GONE);
                        break;
                    case "3":

                        tv1.setVisibility(View.GONE);
                        tv2.setText("删除");
                        tv2.setVisibility(View.VISIBLE);
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
                break;
        }

        tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
        tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
        tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
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
                        case 2:
                            switch (orderDetailBean.getStatus()) {
                                case "0":
                                    break;
                                case "1":
                                    break;
                                case "2":
                                    break;
                                case "3":
                                    break;
                            }
                            break;
                    }
                    break;
                case R.id.tv2:
                    switch (type) {
                        case 0:
                            switch (orderDetailBean.getStatus()) {
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

    public void copy() {
        ClipData text = ClipData.newPlainText("chunLangOrderId", String.valueOf(orderDetailBean.getTid()));
        myClipboard.setPrimaryClip(text);
        ToastUtils.showShort("订单号已复制");
    }

}

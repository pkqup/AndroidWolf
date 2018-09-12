package com.chunlangjiu.app.order.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
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
import com.chunlangjiu.app.order.bean.OrderDetailBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.dialog.CancelOrderDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;

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
    @BindView(R.id.llSendTime)
    LinearLayout llSendTime;
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

    private int type = 0;
    private String oid;
    private String aftersalesBn;

    private CancelOrderDialog cancelOrderDialog;
    private String tid;

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
        }
    }

    private void processData() {
        tvOrderStatus.setText(orderDetailBean.getStatus_desc());

        tvStore.setText(orderDetailBean.getShopname());

        tvOrderId.setText(String.valueOf(orderDetailBean.getTid()));
        tvCopy.setOnClickListener(onClickListener);
        tvCreateTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getCreated_time() + "000")));
        tvPayType.setText(orderDetailBean.getPay_type());

        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);

        switch (type) {
            case 0:
                switch (orderDetailBean.getStatus()) {
                    case OrderParams.WAIT_BUYER_PAY:
                        tvRightContentDesc.setText("剩余支付时间：");
                        tv1.setText("取消订单");
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setText("去支付");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                        tvRightContentDesc.setText("取消原因：");
                        tvRightContent.setText(orderDetailBean.getCancel_reason());
                        tv1.setText("删除订单");
                        tv2.setText("重新购买");
                        break;
                    case OrderParams.WAIT_SELLER_SEND_GOODS:
                        tvRightContentDesc.setVisibility(View.GONE);
                        tv1.setVisibility(View.GONE);
                        tv2.setText("申请退款");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case OrderParams.WAIT_BUYER_CONFIRM_GOODS:
                        tvRightContentDesc.setVisibility(View.GONE);
                        tv1.setVisibility(View.GONE);
                        tv2.setText("确认收货");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case OrderParams.TRADE_FINISHED:
                        tvRightContentDesc.setVisibility(View.GONE);
                        tv1.setVisibility(View.GONE);
                        tv2.setText("评价");
                        tv2.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case 2:
                llPayType.setVisibility(View.GONE);
                tvRightContentDesc.setVisibility(View.GONE);
                tvAfterSaleCreateTime.setText(TimeUtils.millisToDate(String.valueOf(orderDetailBean.getModified_time() + "000")));
                break;
        }

        tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
        tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
        tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvPayment.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));

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
                                    intent = new Intent(OrderDetailActivity.this, OrderEvaluationMainActivity.class);
                                    List<OrderDetailBean.OrdersBean> orders = orderDetailBean.getOrders();
                                    List<OrderListBean.ListBean.OrderBean> order = new ArrayList<>();
                                    for (OrderDetailBean.OrdersBean ordersBean : orders) {
                                        OrderListBean.ListBean.OrderBean orderBean1 = new OrderListBean.ListBean.OrderBean();
                                        orderBean1.setPic_path(ordersBean.getPic_path());
                                        orderBean1.setTitle(ordersBean.getTitle());
                                        orderBean1.setNum(ordersBean.getNum());
                                        orderBean1.setTid(orderDetailBean.getTid());
                                        orderBean1.setOid(ordersBean.getOid());
                                        order.add(orderBean1);
                                    }
                                    intent.putExtra(OrderParams.PRODUCTS, (Serializable) order);
                                    startActivity(intent);
                                    break;
                                case OrderParams.WAIT_SELLER_SEND_GOODS:
                                    tid = String.valueOf(orderDetailBean.getTid());
                                    getCancelReason();
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
                            EventManager.getInstance().notify(null, OrderParams.REFRESH_ORDER_LIST);
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

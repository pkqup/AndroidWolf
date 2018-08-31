package com.chunlangjiu.app.order.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.chunlangjiu.app.order.dialog.CancelOrderDialog;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;

import java.math.BigDecimal;
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

    private int type = 0;

    private CancelOrderDialog cancelOrderDialog;

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

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        disposable = new CompositeDisposable();
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
                        break;
                    case OrderParams.TRADE_CLOSED_BY_SYSTEM:
                        tvRightContentDesc.setText("取消原因：");
                        tvRightContent.setText(orderDetailBean.getCancel_reason());
                        tv1.setText("删除订单");
                        tv2.setText("重新购买");
                        break;
                }
                break;
        }

        tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
        tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
        tvTotalPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getTotal_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvSendPrice.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPost_fee()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvPayment.setText(String.format("¥%s", new BigDecimal(orderDetailBean.getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));

        LayoutInflater inflater = LayoutInflater.from(this);
        for (OrderDetailBean.OrdersBean orderBean : orderDetailBean.getOrders()) {
            View inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
            ImageView imgProduct = inflate.findViewById(R.id.imgProduct);
            GlideUtils.loadImage(getApplicationContext(), orderBean.getPic_path(), imgProduct);
            TextView tvProductName = inflate.findViewById(R.id.tvProductName);
            tvProductName.setText(orderBean.getTitle());
            TextView tvProductPrice = inflate.findViewById(R.id.tvProductPrice);
            tvProductPrice.setText(String.format("¥%s", new BigDecimal(orderBean.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
            TextView tvProductDesc = inflate.findViewById(R.id.tvProductDesc);
            tvProductDesc.setText(orderBean.getSpec_nature_info());

            TextView tvProductNum = inflate.findViewById(R.id.tvProductNum);
            tvProductNum.setText(String.format("x%d", orderBean.getNum()));
            llProducts.addView(inflate);
            if (llProducts.getChildCount() == orderDetailBean.getOrders().size()) {
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
                case R.id.tv1:
                    leftButtonClick();
                    break;
                case R.id.tv2:
                    break;
            }
        }
    };

    private void leftButtonClick() {
        switch (type) {
            case 0:
                switch (orderDetailBean.getStatus()) {
                    case OrderParams.WAIT_BUYER_PAY:
                        getCancelReason();
                        break;
                }
                break;
        }
    }

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

    public void copy() {
        ClipData text = ClipData.newPlainText("chunLangOrderId", String.valueOf(orderDetailBean.getTid()));
        myClipboard.setPrimaryClip(text);
        ToastUtils.showShort("订单号已复制");
    }

}

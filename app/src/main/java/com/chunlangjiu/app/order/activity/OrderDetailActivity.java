package com.chunlangjiu.app.order.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.bean.OrderDetailBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.params.OrderParams;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.TimeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;

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

        tvUserInfo.setText(String.format("%s\u3000%s", orderDetailBean.getReceiver_name(), orderDetailBean.getReceiver_mobile()));
        tvAddress.setText(String.format("%s%s%s%s", orderDetailBean.getReceiver_state(), orderDetailBean.getReceiver_city(), orderDetailBean.getReceiver_district(), orderDetailBean.getReceiver_address()));
        tvTotalPrice.setText(String.format("¥%s", orderDetailBean.getTotal_fee()));
        tvSendPrice.setText(String.format("¥%s", orderDetailBean.getPost_fee()));
        tvPayment.setText(String.format("¥%s", orderDetailBean.getPayment()));

        LayoutInflater inflater = LayoutInflater.from(this);
        for (OrderDetailBean.OrdersBean orderBean : orderDetailBean.getOrders()) {
            View inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
            ImageView imgProduct = inflate.findViewById(R.id.imgProduct);
            GlideUtils.loadImage(getApplicationContext(), orderBean.getPic_path(), imgProduct);
            TextView tvProductName = inflate.findViewById(R.id.tvProductName);
            tvProductName.setText(orderBean.getTitle());
            TextView tvProductPrice = inflate.findViewById(R.id.tvProductPrice);
            tvProductPrice.setText(String.format("¥%s", orderBean.getPrice()));
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
            }
        }
    };

    public void copy() {
        ClipData text = ClipData.newPlainText("chunLangOrderId", String.valueOf(orderDetailBean.getTid()));
        myClipboard.setPrimaryClip(text);
        ToastUtils.showShort("订单号已复制");
    }

}

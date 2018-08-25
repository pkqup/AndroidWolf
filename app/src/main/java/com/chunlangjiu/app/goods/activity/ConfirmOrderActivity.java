package com.chunlangjiu.app.goods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.goods.adapter.ConfirmOrderGoodsAdapter;
import com.chunlangjiu.app.goods.bean.ConfirmOrderBean;
import com.chunlangjiu.app.goods.bean.OrderGoodsBean;
import com.chunlangjiu.app.goods.dialog.PayDialog;
import com.chunlangjiu.app.user.activity.AddressListActivity;
import com.chunlangjiu.app.user.bean.AddressListDetailBean;
import com.pkqup.commonlibrary.util.BigDecimalUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class ConfirmOrderActivity extends BaseActivity {

    private static final int CHOICE_ADDRESS = 1000;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.rlNoAddress)
    RelativeLayout rlNoAddress;

    @BindView(R.id.rlHasAddress)
    RelativeLayout rlHasAddress;
    @BindView(R.id.tvAddressName)
    TextView tvAddressName;
    @BindView(R.id.tvAddressPhone)
    TextView tvAddressPhone;
    @BindView(R.id.tvAddressDetails)
    TextView tvAddressDetails;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rlChoicePay)
    RelativeLayout rlChoicePay;
    @BindView(R.id.tvPayMethod)
    TextView tvPayMethod;
    @BindView(R.id.etRemark)
    EditText etRemark;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @BindView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @BindView(R.id.tvPayPrice)
    TextView tvPayPrice;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private List<OrderGoodsBean> lists;
    private ConfirmOrderGoodsAdapter orderGoodsAdapter;
    private PayDialog payDialog;
    private int payMehtod = 0;//默认微信支付

    private ConfirmOrderBean confirmOrderBean;
    private String addressId = "";
    private String sendPrice = "0.00";
    private String goodsPrice = "0.00";
    private String payPrice = "0.00";

    public static void startConfirmOrderActivity(Activity activity, ConfirmOrderBean confirmOrderBean) {
        Intent intent = new Intent(activity, ConfirmOrderActivity.class);
        intent.putExtra("confirmOrderBean", confirmOrderBean);
        activity.startActivity(intent);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.rlNoAddress:
                    startAddressListActivity();
                    break;
                case R.id.rlHasAddress:
                    startAddressListActivity();
                    break;
                case R.id.rlChoicePay:
                    showPayMethodDialog();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("确认订单");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_confirm_order);
        initView();
        initData();
    }


    private void initView() {
        rlNoAddress.setOnClickListener(onClickListener);
        rlHasAddress.setOnClickListener(onClickListener);
        rlChoicePay.setOnClickListener(onClickListener);

        lists = new ArrayList<>();
        orderGoodsAdapter = new ConfirmOrderGoodsAdapter(this, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderGoodsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initData() {
        confirmOrderBean = (ConfirmOrderBean) getIntent().getSerializableExtra("confirmOrderBean");
        if (confirmOrderBean != null) {
            ConfirmOrderBean.Address default_address = confirmOrderBean.getDefault_address();
            if (default_address != null && !default_address.getAddr_id().isEmpty()) {
                addressId = default_address.getAddr_id();
                rlNoAddress.setVisibility(View.GONE);
                rlHasAddress.setVisibility(View.VISIBLE);
                tvAddressName.setText(default_address.getName());
                tvAddressPhone.setText(default_address.getMobile());
                tvAddressDetails.setText(default_address.getArea() + default_address.getAddr());
            } else {
                rlNoAddress.setVisibility(View.VISIBLE);
                rlHasAddress.setVisibility(View.GONE);
            }

            List<ConfirmOrderBean.CartData> resultCartData = confirmOrderBean.getCartInfo().getResultCartData();

            lists.clear();
            for (int i = 0; i < resultCartData.size(); i++) {
                OrderGoodsBean storeBean = new OrderGoodsBean();
                storeBean.setItemType(OrderGoodsBean.ITEM_STORE);
                storeBean.setStoreName(resultCartData.get(i).getShop_name());
                storeBean.setStoreLogo(resultCartData.get(i).getShop_logo());
                storeBean.setStoreId(resultCartData.get(i).getShop_id());
                lists.add(storeBean);

                List<ConfirmOrderBean.GoodsItem> items = resultCartData.get(i).getItems();
                for (int j = 0; j < items.size(); j++) {
                    OrderGoodsBean goodsBean = new OrderGoodsBean();
                    goodsBean.setItemType(OrderGoodsBean.ITEM_GOODS);
                    goodsBean.setGoodsId(items.get(j).getSku_id());
                    goodsBean.setGoodsPic(items.get(j).getImage_default_id());
                    goodsBean.setGoodsName(items.get(j).getTitle());
                    goodsBean.setGoodsPrice(BigDecimalUtils.add(items.get(j).getPrice().getPrice(), "0.00", 2));
                    goodsBean.setGoodsNum(items.get(j).getQuantity());
                    lists.add(goodsBean);
                }
            }
            orderGoodsAdapter.setNewData(lists);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, 0);
                }
            });

            goodsPrice = BigDecimalUtils.add(confirmOrderBean.getTotal().getAllCostFee(), "0.00", 2);
            tvGoodsPrice.setText("¥" + goodsPrice);

            String allPostfee = confirmOrderBean.getTotal().getAllPostfee();
            if (TextUtils.isEmpty(allPostfee)) {
                tvSendPrice.setText("¥0.00");
            } else {
                sendPrice = BigDecimalUtils.add(confirmOrderBean.getTotal().getAllPostfee(), "0.00", 2);
                tvSendPrice.setText("¥" + sendPrice);
            }

            payPrice = BigDecimalUtils.add(goodsPrice, sendPrice, 2);
            tvPayPrice.setText(payPrice);
        }
    }

    private void showPayMethodDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setCallBack(new PayDialog.CallBack() {
                @Override
                public void choicePayMethod(int payMethod) {
                    updatePayMethod(payMethod);
                }
            });
        }
        payDialog.show();
    }

    private void updatePayMethod(int payMethod) {
        this.payMehtod = payMethod;
        switch (payMethod) {
            case 0:
                tvPayMethod.setText("微信支付");
                break;
            case 1:
                tvPayMethod.setText("支付宝支付");
                break;
            case 2:
                tvPayMethod.setText("余额支付");
                break;
            case 3:
                tvPayMethod.setText("大额支付");
                break;
        }
    }


    private void startAddressListActivity() {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra("selectAddressId", addressId);
        intent.putExtra("isSelect", true);
        startActivityForResult(intent, CHOICE_ADDRESS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == CHOICE_ADDRESS) {
                AddressListDetailBean addressListDetailBean =
                        (AddressListDetailBean) data.getSerializableExtra("addressListDetailBean");
                if (addressListDetailBean == null || TextUtils.isEmpty(addressListDetailBean.getAddr_id())) {
                    addressId = "";
                    rlNoAddress.setVisibility(View.VISIBLE);
                    rlHasAddress.setVisibility(View.GONE);
                } else {
                    addressId = addressListDetailBean.getAddr_id();
                    rlNoAddress.setVisibility(View.GONE);
                    rlHasAddress.setVisibility(View.VISIBLE);
                    tvAddressName.setText(addressListDetailBean.getName());
                    tvAddressPhone.setText(addressListDetailBean.getMobile());
                    String address = addressListDetailBean.getArea() + addressListDetailBean.getAddr();
                    tvAddressDetails.setText(address.replace("/", ""));
                }
            }
        }
    }
}

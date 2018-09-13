package com.chunlangjiu.app.goods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.chunlangjiu.app.goods.bean.PaymentBean;
import com.chunlangjiu.app.goods.dialog.PayDialog;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.user.activity.AddressListActivity;
import com.chunlangjiu.app.user.bean.AddressListDetailBean;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/9/11
 * @Describe:
 */
public class AuctionConfirmOrderActivity extends BaseActivity {

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

    @BindView(R.id.img_store)
    CircleImageView img_store;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.etPrice)
    EditText etPrice;

    @BindView(R.id.rlChoicePay)
    RelativeLayout rlChoicePay;
    @BindView(R.id.tvPayMethod)
    TextView tvPayMethod;
    @BindView(R.id.etRemark)
    EditText etRemark;

    @BindView(R.id.tvGivePrice)
    TextView tvGivePrice;
    @BindView(R.id.tvPayPrice)
    TextView tvPayPrice;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private IWXAPI wxapi;

    private CompositeDisposable disposable;
    private PayDialog payDialog;
    private int payMehtod;//默认微信支付
    private String payMehtodId;//支付方式类型


    private AuctionListBean.AuctionBean auctionBean;
    private String addressId = "";
    private List<PaymentBean.PaymentInfo> payList;

    public static void startConfirmOrderActivity(Activity activity, AuctionListBean.AuctionBean auctionBean) {
        Intent intent = new Intent(activity, AuctionConfirmOrderActivity.class);
        intent.putExtra("auctionBean", auctionBean);
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
                case R.id.tvCommit:
                    checkData();
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
        setContentView(R.layout.goods_activity_auction_confirm_order);
        EventManager.getInstance().registerListener(onNotifyListener);
        initPay();
        initView();
        initData();
        getPaymentList();
    }

    private void initPay() {
        wxapi = WXAPIFactory.createWXAPI(this, null);
        wxapi.registerApp("wx0e1869b241d7234f");
    }

    private void initView() {
        disposable = new CompositeDisposable();
        rlNoAddress.setOnClickListener(onClickListener);
        rlHasAddress.setOnClickListener(onClickListener);
        rlChoicePay.setOnClickListener(onClickListener);
        tvCommit.setOnClickListener(onClickListener);
    }

    private void initData() {
        auctionBean = (AuctionListBean.AuctionBean) getIntent().getSerializableExtra("auctionBean");

        // TODO: 2018/9/11 需要返回店铺的信息才能显示
        GlideUtils.loadImage(this, auctionBean.getImage_default_id(), img_store);
        tv_store_name.setText(auctionBean.getTitle());

        GlideUtils.loadImage(this, auctionBean.getImage_default_id(), img_pic);
        tv_name.setText(auctionBean.getTitle());
        tv_price.setText(auctionBean.getPrice());
        tvGivePrice.setText(auctionBean.getPrice());
        tvPayPrice.setText(auctionBean.getPrice());
    }


    private void getPaymentList() {
        showLoadingDialog();
        disposable.add(ApiUtils.getInstance().getPayment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<PaymentBean>>() {
                    @Override
                    public void accept(ResultBean<PaymentBean> paymentBeanResultBean) throws Exception {
                        hideLoadingDialog();
                        payList = paymentBeanResultBean.getData().getList();
                        if (payList != null & payList.size() > 0) {
                            tvPayMethod.setText(payList.get(0).getApp_display_name());
                            payMehtodId = payList.get(0).getApp_id();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideLoadingDialog();
                    }
                }));
    }


    private void showPayMethodDialog() {
        if (payList == null & payList.size() == 0) {
            ToastUtils.showShort("获取支付方式失败");
        } else {
            if (payDialog == null) {
                payDialog = new PayDialog(this, payList);
                payDialog.setCallBack(new PayDialog.CallBack() {
                    @Override
                    public void choicePayMethod(int payMethod, String payMethodId) {
                        updatePayMethod(payMethod, payMethodId);
                    }
                });
            }
            payDialog.show();
        }
    }

    private void updatePayMethod(int payMethod, String payMethodId) {
        this.payMehtod = payMethod;
        this.payMehtodId = payMethodId;
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

    private void checkData() {
        if (TextUtils.isEmpty(addressId)) {
            ToastUtils.showShort("请选择地址");
        } else if (TextUtils.isEmpty(payMehtodId)) {
            ToastUtils.showShort("请选择支付方式");
        } else if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastUtils.showShort("请输入价格");
        } else {
            commitOrder();
        }
    }

    private void commitOrder() {

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

    private void invokeZhifubaoPay(ResultBean data) {

    }

    private void invokeYuePay(ResultBean data) {

    }

    private void invokeDaePay(ResultBean data) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
    }

    private EventManager.OnNotifyListener onNotifyListener = new EventManager.OnNotifyListener() {
        @Override
        public void onNotify(Object object, String eventTag) {
            weixinPaySuccess(object, eventTag);
        }
    };

    private void weixinPaySuccess(Object object, String eventTag) {
        if (eventTag.equals(ConstantMsg.WEIXIN_PAY_CALLBACK)) {
            int code = (int) object;
            if (code == 0) {
                //支付成功
                ToastUtils.showShort("支付成功");
                EventManager.getInstance().notify(null, ConstantMsg.UPDATE_CART_LIST);
                finish();
            } else if (code == -1) {
                //支付错误
            } else if (code == -2) {
                //支付取消
            }
        }
    }
}

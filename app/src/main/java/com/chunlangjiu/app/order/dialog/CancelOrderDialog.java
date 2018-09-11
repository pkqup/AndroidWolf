package com.chunlangjiu.app.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.order.bean.CancelOrderResultBean;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SystemUtils;
import com.pkqup.commonlibrary.util.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CancelOrderDialog extends Dialog {
    private Window window;
    private Context context;// 上下文
    private List<String> data;
    private RadioGroup rgReason;
    private LayoutInflater inflater;

    private TextView tvOk;
    private TextView tvCancel;
    private CompositeDisposable disposable;
    private String tid;

    private CancelCallBack cancelCallBack;

    /**
     * Creates a new instance of ChooseShopDialog.
     *
     * @param context 上下文
     */
    public CancelOrderDialog(Context context, List<String> data, String tid) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        this.data = data;
        this.tid = tid;
        inflater = LayoutInflater.from(context);
        initView();
        initData();
    }

    /**
     * init:【初始化dialog】. <br/>
     * ..<br/>
     */
    private void initView() {
        setContentView(R.layout.order_dialog_cancel);
        rgReason = findViewById(R.id.rgReason);

        tvOk = findViewById(R.id.tvOk);
        tvCancel = findViewById(R.id.tvCancel);
        tvOk.setOnClickListener(onClickListener);
        tvCancel.setOnClickListener(onClickListener);

        disposable = new CompositeDisposable();
    }

    /**
     * initData:【初始化数据】. <br/>
     * ..<br/>
     */
    private void initData() {
        rgReason.removeAllViewsInLayout();
        if (null == window) {
            window = getWindow();
        }
        if (null != window) {
            window.getDecorView().setPadding(0, 0, 0, 0); // 消除边距

            WindowManager.LayoutParams lp = window.getAttributes();
            int[] deviceWh = SystemUtils.getDeviceWh(getContext());
            lp.width = (int) (deviceWh[0] / 1.5); // 设置宽度充满屏幕
            if (10 < data.size()) {
                lp.height = (int) (deviceWh[1] / 1.55);
            } else {
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            window.setAttributes(lp);
        }
        int i = 0;
        for (String reason : data) {
            RadioButton radioButton =
                    (RadioButton) inflater.inflate(R.layout.order_cancel_list_item, null);
            if (0 == i) {
                radioButton.setChecked(true);
            }
            radioButton.setId(i);
            radioButton.setText(reason);
            rgReason.addView(radioButton);
            i++;
        }
    }

    public void setData(List<String> data, String tid) {
        this.data = data;
        this.tid = tid;
        initData();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvOk:
                    cancelOrder();
                    break;
                case R.id.tvCancel:
                    dismiss();
                    break;
            }
        }
    };

    private void cancelOrder() {
        disposable.add(ApiUtils.getInstance().cancelOrder(tid, data.get(rgReason.getCheckedRadioButtonId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CancelOrderResultBean>>() {
                    @Override
                    public void accept(ResultBean<CancelOrderResultBean> orderListBeanResultBean) throws Exception {
                        if (0 == orderListBeanResultBean.getErrorcode()) {
                            ToastUtils.showShort("取消订单成功");
                            if (null != cancelCallBack) {
                                cancelCallBack.cancelSuccess();
                            }
                        }
                        dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    public void setCancelCallBack(CancelCallBack cancelCallBack) {
        this.cancelCallBack = cancelCallBack;
    }

    public interface CancelCallBack {
        void cancelSuccess();
    }
}
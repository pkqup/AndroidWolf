package com.chunlangjiu.app.goods.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chunlangjiu.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class PayDialog extends Dialog {

    private static final int PAY_WEIXIN = 0;
    private static final int PAY_ZHIFUBAO = 1;
    private static final int PAY_WALLET = 2;
    private static final int PAY_LARGE = 3;

    private Context context;
    private int payMethod = PAY_WEIXIN;
    private List<ImageView> imageViewLists;

    public PayDialog(Context context) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_dialog_pay_method, null);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);

        setContentView(view);// 设置布局

        findViewById(R.id.imgClose).setOnClickListener(onClickListener);
        findViewById(R.id.rlWeiXin).setOnClickListener(onClickListener);
        findViewById(R.id.rlZhiFuBao).setOnClickListener(onClickListener);
        findViewById(R.id.rlWallet).setOnClickListener(onClickListener);
        findViewById(R.id.rlLarge).setOnClickListener(onClickListener);

        ImageView imgChoiceWeixin = findViewById(R.id.imgChoiceWeixin);
        ImageView imgChoiceZhifubao = findViewById(R.id.imgChoiceZhifubao);
        ImageView imgChoiceWallet = findViewById(R.id.imgChoiceWallet);
        ImageView imgChoiceLarge = findViewById(R.id.imgChoiceLarge);
        imgChoiceWeixin.setVisibility(View.VISIBLE);
        imageViewLists = new ArrayList<>();
        imageViewLists.add(imgChoiceWeixin);
        imageViewLists.add(imgChoiceZhifubao);
        imageViewLists.add(imgChoiceWallet);
        imageViewLists.add(imgChoiceLarge);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgClose:
                    dismiss();
                    break;
                case R.id.rlWeiXin:
                    choicePay(0);
                    break;
                case R.id.rlZhiFuBao:
                    choicePay(1);
                    break;
                case R.id.rlWallet:
                    choicePay(2);
                    break;
                case R.id.rlLarge:
                    choicePay(3);
                    break;
            }
        }
    };


    private void choicePay(int payMethod) {
        for (int i = 0; i < imageViewLists.size(); i++) {
            if (payMethod == i) {
                imageViewLists.get(i).setVisibility(View.VISIBLE);
            } else {
                imageViewLists.get(i).setVisibility(View.GONE);
            }
        }
        if (callBack != null) {
            callBack.choicePayMethod(payMethod);
        }
        dismiss();
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void choicePayMethod(int payMethod);
    }
}

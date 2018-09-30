package com.chunlangjiu.app.goods.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.GivePriceBean;
import com.pkqup.commonlibrary.util.ToastUtils;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/19
 * @Describe:
 */
public class InputPriceDialog extends Dialog {

    private Context context;
    private String maxPrice;
    private String myPrice = "0";

    private CallBack callBack;
    private EditText etPrice;

    private TextView tvMax;
    private TextView tvMyPrice;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void editPrice(String price);
    }


    public InputPriceDialog(Context context, String maxPrice, String myPrice) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        this.maxPrice = maxPrice;
        this.myPrice = myPrice;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        initView();
    }

    public void updatePrice(String maxPrice, String myPrice){
        this.maxPrice = maxPrice;
        this.myPrice = myPrice;
        tvMax.setText(maxPrice);
        tvMyPrice.setText(myPrice);
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_dialog_input_price, null);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);

        setContentView(view);// 设置布局


        tvMax = findViewById(R.id.tvMaxPrice);
        tvMyPrice = findViewById(R.id.tvMyPrice);
        etPrice = findViewById(R.id.etPrice);
        TextView tvCancel = findViewById(R.id.tvCancel);
        TextView tvConfirm = findViewById(R.id.tvConfirm);

        tvMax.setText(maxPrice);
        tvMyPrice.setText(myPrice);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

    }

    private void checkData() {
        try {
            if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
                ToastUtils.showShort("请输入金额");
            } else {
                String trim = etPrice.getText().toString().trim();
                double inPutprice = Double.parseDouble(trim);
                double currentPrice = Double.parseDouble(myPrice);
                if (inPutprice > currentPrice) {
                    callBack.editPrice(etPrice.getText().toString().trim());
                    dismiss();
                } else {
                    ToastUtils.showShort("新出价金额必须大于原出价金额");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
package com.chunlangjiu.app.goods.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.pkqup.commonlibrary.util.ToastUtils;

/**
 * @CreatedbBy: liucun on 2018/9/19
 * @Describe:
 */
public class InputPriceDialog extends Dialog {

    private TextView tvConfirm;
    private EditText etPrice;

    private OnCallBackListener onCallBackListener;

    public interface OnCallBackListener {
        void commitPrice(String price);
    }

    public void setCallBackListener(OnCallBackListener onCallBackListener) {
        this.onCallBackListener = onCallBackListener;
    }

    public InputPriceDialog(@NonNull Context context) {
        this(context, R.style.BottomDialogTheme);
    }

    public InputPriceDialog(@NonNull Context context, int themeResIds) {
        super(context, themeResIds);
        setContentView(R.layout.goods_dialog_input_price);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);

        etPrice = findViewById(R.id.etPrice);
        tvConfirm = findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
                    ToastUtils.showShort("请填写金额");
                } else {
                    dismiss();
                    onCallBackListener.commitPrice(etPrice.getText().toString().trim());
                }
            }
        });
    }
}
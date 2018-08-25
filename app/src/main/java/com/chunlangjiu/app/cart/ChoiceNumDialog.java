package com.chunlangjiu.app.cart;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunlangjiu.app.R;

/**
 * @CreatedbBy: liucun on 2018/8/25.
 * @Describe: 选择购买数量对话框
 */
public class ChoiceNumDialog extends Dialog {

    private TextView tvConfirm;
    private ImageView imgSub;
    private TextView tvNum;
    private ImageView imgAdd;

    private int stock;//库存
    private int num = 1;

    private OnCallBackListener onCallBackListener;

    public interface OnCallBackListener {
        void choiceNum(int num);
    }

    public void setCallBackListener(OnCallBackListener onCallBackListener) {
        this.onCallBackListener = onCallBackListener;
    }

    public ChoiceNumDialog(@NonNull Context context, int stock) {
        this(context, stock, R.style.BottomDialogTheme);
    }

    public ChoiceNumDialog(@NonNull Context context, final int stockParams, int themeResIds) {
        super(context, themeResIds);
        this.stock = stockParams;
        setContentView(R.layout.dialog_choice_num);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);

        tvConfirm = findViewById(R.id.tvConfirm);
        imgSub = findViewById(R.id.imgSub);
        tvNum = findViewById(R.id.tvNum);
        imgAdd = findViewById(R.id.imgAdd);

        imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num > 1) {
                    num--;
                    tvNum.setText(num + "");
                }
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num < stock) {
                    num++;
                    tvNum.setText(num + "");
                }
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (onCallBackListener != null) {
                    onCallBackListener.choiceNum(num);
                }
            }
        });
    }
}


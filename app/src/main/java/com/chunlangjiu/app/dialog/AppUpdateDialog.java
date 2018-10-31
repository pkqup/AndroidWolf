package com.chunlangjiu.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chunlangjiu.app.R;

/**
 * @CreatedbBy: liucun on 2018/10/31
 * @Describe:
 */
public class AppUpdateDialog extends Dialog {

    private Context context;
    private String content;
    private CallBack callBack;

    private TextView tv_content;
    private TextView tv_confirm;

    public AppUpdateDialog(Context context, String content) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        this.content = content;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.amian_dialog_app_upadte, null);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);

        setContentView(view);// 设置布局

        tv_content = findViewById(R.id.tv_content);
        tv_confirm = findViewById(R.id.tv_confirm);

        tv_content.setText(content);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    dismiss();
                    callBack.onConfirm();
                }
            }
        });

    }

    public void setDialogStr(String confirmStr){
        tv_confirm.setText(confirmStr);
    }

    public interface CallBack {
        void onConfirm();
    }
}

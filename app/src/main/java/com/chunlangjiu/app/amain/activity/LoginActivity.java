package com.chunlangjiu.app.amain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe: 登录页面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etAuthCode)
    EditText etAuthCode;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    private CountDownTimer countDownTimer;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvGetCode:
                    countDownTime();
                    break;
                case R.id.tvLogin:
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("登录注册");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amain_activity_login);
        initView();
    }

    private void initView() {
        tvGetCode.setOnClickListener(onClickListener);
        tvLogin.setOnClickListener(onClickListener);
    }


    private void countDownTime() {
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millsTime) {
                if (millsTime / 1000 == 60) {
                    tvGetCode.setText("59s");
                } else {
                    tvGetCode.setText(millsTime / 1000 + "s");
                }
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("获取验证码");
                tvGetCode.setClickable(true);
            }
        };
        countDownTimer.start();
        tvGetCode.setClickable(false);
    }


}

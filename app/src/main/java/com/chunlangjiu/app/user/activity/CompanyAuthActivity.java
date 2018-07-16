package com.chunlangjiu.app.user.activity;

import android.os.Bundle;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

/**
 * @CreatedbBy: liucun on 2018/7/15.
 * @Describe: 企业认证页面
 */
public class CompanyAuthActivity extends BaseActivity {

    @Override
    public void setTitleView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_company_auth);
    }
}

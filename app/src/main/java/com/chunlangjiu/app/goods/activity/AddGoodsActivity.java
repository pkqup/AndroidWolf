package com.chunlangjiu.app.goods.activity;

import android.os.Bundle;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

/**
 * @CreatedbBy: liucun on 2018/7/16
 * @Describe: 添加商品页面
 */
public class AddGoodsActivity extends BaseActivity {

    @Override
    public void setTitleView() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_add_goods);
    }
}

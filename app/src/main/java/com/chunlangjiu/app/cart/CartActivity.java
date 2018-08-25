package com.chunlangjiu.app.cart;

import android.os.Bundle;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.fragment.CartFragment;


/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 购物车
 */
public class CartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.llContent, new CartFragment())
                .commit();
    }

    @Override
    public void setTitleView() {
       hideTitleView();
    }

}

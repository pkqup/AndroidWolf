package com.chunlangjiu.app.user.activity;

import android.os.Bundle;
import android.view.View;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

/**
 * @CreatedbBy: liucun on 2018/9/17
 * @Describe:
 */
public class AddGoodsSuccessActivity extends BaseActivity {



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvGoodsManager:
                    finish();
                    break;
                case R.id.tvCheckGoodsManager:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("商品添加");
        titleImgLeft.setOnClickListener(onClickListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_add_goods_success);
        findViewById(R.id.tvGoodsManager).setOnClickListener(onClickListener);
        findViewById(R.id.tvCheckGoodsManager).setOnClickListener(onClickListener);
    }

}

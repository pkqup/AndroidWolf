package com.chunlangjiu.app.goods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/16
 * @Describe: 名酒估价页面
 */
public class ValuationActivity extends BaseActivity {

    @BindView(R.id.tvCommit)
    TextView tvCommit;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvCommit:
                    startActivity(new Intent(ValuationActivity.this, ValuationSuccessActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("名酒估价");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_valuation);
        initView();
    }

    private void initView() {
        tvCommit.setOnClickListener(onClickListener);
    }


}

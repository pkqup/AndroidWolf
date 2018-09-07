package com.chunlangjiu.app.order.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

import butterknife.BindView;

public class OrderApplyForAfterSaleActivity extends BaseActivity {

    @BindView(R.id.etNote)
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_applyforaftersale);

        initData();
    }

    @Override
    public void setTitleView() {

    }

    private void initData() {

    }

}

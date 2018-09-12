package com.chunlangjiu.app.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.order.adapter.OrderEvaluationMainAdapter;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.chunlangjiu.app.order.params.OrderParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OrderEvaluationMainActivity extends BaseActivity {
    @BindView(R.id.listView)
    RecyclerView listView;

    private List<OrderListBean.ListBean.OrderBean> data;

    private OrderEvaluationMainAdapter orderEvaluationMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_evaluationmain);

        initData();
    }

    @Override
    public void setTitleView() {
        titleImgLeft.setOnClickListener(onClickListener);
        titleName.setText("商品评价");
    }

    private void initData() {
        data = (List<OrderListBean.ListBean.OrderBean>) getIntent().getSerializableExtra(OrderParams.PRODUCTS);

        orderEvaluationMainAdapter = new OrderEvaluationMainAdapter(this, R.layout.order_adapter_list_product_item, data);
        orderEvaluationMainAdapter.setOnClickListener(onClickListener);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(orderEvaluationMainAdapter);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.llParent:
                    int position = Integer.parseInt(view.getTag().toString());
                    OrderListBean.ListBean.OrderBean orderBean = data.get(position);
                    Intent intent = new Intent(OrderEvaluationMainActivity.this, OrderEvaluationDetailActivity.class);
                    intent.putExtra(OrderParams.PRODUCTS, orderBean);
                    startActivity(intent);
                    break;
            }
        }
    };
}

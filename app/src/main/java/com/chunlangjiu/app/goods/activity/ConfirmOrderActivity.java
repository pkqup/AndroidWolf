package com.chunlangjiu.app.goods.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.goods.adapter.ConfirmOrderGoodsAdapter;
import com.chunlangjiu.app.goods.bean.OrderGoodsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.rlNoAddress)
    RelativeLayout rlNoAddress;
    @BindView(R.id.rlHasAddress)
    RelativeLayout rlHasAddress;
    @BindView(R.id.tvAddressName)
    TextView tvAddressName;
    @BindView(R.id.tvAddressPhone)
    TextView tvAddressPhone;
    @BindView(R.id.tvAddressDetails)
    TextView tvAddressDetails;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvPayMethod)
    TextView tvPayMethod;
    @BindView(R.id.etRemark)
    EditText etRemark;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @BindView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @BindView(R.id.tvPayPrice)
    TextView tvPayPrice;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private List<OrderGoodsBean> lists;
    private ConfirmOrderGoodsAdapter orderGoodsAdapter;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("确认订单");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_confirm_order);
        initView();
    }

    private void initView() {
        lists = new ArrayList<>();
        orderGoodsAdapter = new ConfirmOrderGoodsAdapter(this, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderGoodsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        for (int i = 0; i < 8; i++) {
            OrderGoodsBean goodsBean = new OrderGoodsBean();
            if (i == 0 || i == 4) {
                goodsBean.setItemType(OrderGoodsBean.ITEM_STORE);
            } else {
                goodsBean.setItemType(OrderGoodsBean.ITEM_GOODS);
            }
            lists.add(goodsBean);
            orderGoodsAdapter.setNewData(lists);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }
}

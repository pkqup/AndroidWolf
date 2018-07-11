package com.chunlangjiu.app.goods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.abase.BaseFragmentAdapter;
import com.chunlangjiu.app.goods.fragment.GoodsCommentFragment;
import com.chunlangjiu.app.goods.fragment.GoodsDetailsFragment;
import com.chunlangjiu.app.goods.fragment.GoodsWebFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsDetailsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.rlBottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tvBuy)
    TextView tvBuy;
    @BindView(R.id.tvAddCart)
    TextView tvAddCart;

    @BindView(R.id.rlChat)
    RelativeLayout rlChat;
    @BindView(R.id.rlCollect)
    RelativeLayout rlCollect;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.tvCartNum)
    TextView tvCartNum;

    private BaseFragmentAdapter fragmentAdapter;

    private final String[] mTitles = {"商品", "详情", "评价"};
    private List<Fragment> mFragments;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_back:
                    finish();
                    break;
                case R.id.tvBuy://立即购买
                    startActivity(new Intent(GoodsDetailsActivity.this, ConfirmOrderActivity.class
                    ));
                    break;
            }
        }
    };

    public static void startGoodsDetailsActivity(Activity activity, String goodsId) {
        Intent intent = new Intent(activity, GoodsDetailsActivity.class);
        intent.putExtra("goodsId", goodsId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_details);
        initView();
        initData();
    }

    @Override
    public void setTitleView() {
        hideTitleView();
    }

    private void initView() {
        imgBack.setOnClickListener(onClickListener);
        mFragments = new ArrayList<>();
        mFragments.add(new GoodsDetailsFragment());
        mFragments.add(new GoodsWebFragment());
        mFragments.add(new GoodsCommentFragment());
        fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.setLists(mFragments);
        view_pager.setAdapter(fragmentAdapter);
        tab.setViewPager(view_pager, mTitles);
        view_pager.setCurrentItem(0);

        tvBuy.setOnClickListener(onClickListener);
        tvAddCart.setOnClickListener(onClickListener);
        rlChat.setOnClickListener(onClickListener);
        rlCollect.setOnClickListener(onClickListener);
        rlCart.setOnClickListener(onClickListener);
    }

    private void initData() {


    }

}

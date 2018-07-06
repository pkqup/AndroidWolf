package com.chunlangjiu.app.goods.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.GoodsListInfoBean;
import com.lzy.widget.HeaderViewPager;
import com.pkqup.commonlibrary.view.MyHeaderRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/5
 * @Describe: 卖家主页
 */
public class ShopMainActivity extends BaseActivity {

    @BindView(R.id.scrollableLayout)
    HeaderViewPager scrollableLayout;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.recycle_view)
    MyHeaderRecycleView recycle_view;

    private List<GoodsListInfoBean> lists;
    private LinearAdapter linearAdapter;

    @Override
    public void setTitleView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_shop_main);
        initView();
        initData();


    }

    private void initView() {
        scrollableLayout.setCurrentScrollableContainer(recycle_view);
        lists = new ArrayList<>();
        linearAdapter = new LinearAdapter(R.layout.amain_item_goods_list_linear, lists);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(linearAdapter);
    }

    private void initData() {
        lists.clear();
        for (int i = 0; i < 30; i++) {
            GoodsListInfoBean goodsListInfoBean = new GoodsListInfoBean();
            lists.add(goodsListInfoBean);
        }
        linearAdapter.setNewData(lists);
    }

    public class LinearAdapter extends BaseQuickAdapter<GoodsListInfoBean, BaseViewHolder> {
        public LinearAdapter(int layoutResId, List<GoodsListInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListInfoBean item) {
        }
    }
}

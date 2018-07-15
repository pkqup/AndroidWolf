package com.chunlangjiu.app.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.store.bean.StoreClassBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe: 名庄查询页面
 */
public class StoreListActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<StoreClassBean> lists;
    private StoreAdapter storeAdapter;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.img_title_right_one:
                    startActivity(new Intent(StoreListActivity.this,StoreSearchActivity.class));
                    break;

            }
        }
    };


    @Override
    public void setTitleView() {
        titleName.setText("名庄查询");
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_list);
        initView();
        initData();
    }


    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout.setEnableRefresh(false);//设置不可以下拉刷新
        refreshLayout.setEnableLoadMore(false);//设置不能加载更多
        lists = new ArrayList();
        storeAdapter = new StoreAdapter(R.layout.store_item_store_class, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storeAdapter);
        storeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(StoreListActivity.this,StoreDetailsActivity.class));
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            StoreClassBean storeClassBean = new StoreClassBean();
            storeClassBean.setName("名庄" + i);
            lists.add(storeClassBean);
        }
        storeAdapter.setNewData(lists);
    }


    public class StoreAdapter extends BaseQuickAdapter<StoreClassBean, BaseViewHolder> {

        public StoreAdapter(int layoutResId, List<StoreClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, StoreClassBean item) {
            helper.setText(R.id.tvName, item.getName());
        }
    }
}

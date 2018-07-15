package com.chunlangjiu.app.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.store.bean.SearchStoreBean;
import com.chunlangjiu.app.store.bean.StoreClassBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe: 名庄搜索页面1
 */
public class StoreSearchActivity extends BaseActivity {

    @BindView(R.id.rlMore)
    RelativeLayout rlMore;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<SearchStoreBean> lists;
    private SearchStoreAdapter searchStoreAdapter;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.img_title_right_one://搜索
                    break;
                case R.id.rlMore://查看更多分类

                    break;

            }
        }
    };

    @Override
    public void setTitleView() {
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setOnClickListener(onClickListener);
        titleName.setVisibility(View.GONE);
        titleSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_search);
        initView();
        initData();
    }

    private void initView() {
        rlMore.setOnClickListener(onClickListener);
        lists = new ArrayList<>();
        searchStoreAdapter = new SearchStoreAdapter(R.layout.store_item_search_store, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchStoreAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            SearchStoreBean searchStoreBean = new SearchStoreBean();
            lists.add(searchStoreBean);
        }
        searchStoreAdapter.setNewData(lists);
    }


    public class SearchStoreAdapter extends BaseQuickAdapter<SearchStoreBean, BaseViewHolder> {

        public SearchStoreAdapter(int layoutResId, List<SearchStoreBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchStoreBean item) {
            helper.setText(R.id.tvName, item.getName());
        }
    }
}

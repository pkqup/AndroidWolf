package com.chunlangjiu.app.store.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.store.adapter.SearchStoreAdapter;
import com.chunlangjiu.app.store.bean.SearchBean;
import com.chunlangjiu.app.store.bean.SearchSecondBean;

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

    @BindView(R.id.exListView)
    ExpandableListView exListView;

    private List<SearchBean> lists;
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
        titleImgRightOne.setVisibility(View.VISIBLE);
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
        searchStoreAdapter = new SearchStoreAdapter(this, lists);
        exListView.setAdapter(searchStoreAdapter);
        exListView.setGroupIndicator(null);
        exListView.setDivider(null);
        exListView.setCacheColorHint(0);
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            SearchBean searchBean = new SearchBean();
            searchBean.setId(i + "");
            searchBean.setName("分类" + i);
            ArrayList<SearchSecondBean> secondBeans = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                SearchSecondBean searchSecondBean = new SearchSecondBean();
                searchSecondBean.setId(j + "");
                searchSecondBean.setName("名庄" + j);
                secondBeans.add(searchSecondBean);
            }
            searchBean.setList(secondBeans);
            lists.add(searchBean);
        }
        searchStoreAdapter.setLists(lists);
    }


}

package com.android.alcoholwolf.goods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseActivity;
import com.android.alcoholwolf.abase.BaseFragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsDetailsActivity extends BaseActivity {


    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private BaseFragmentAdapter fragmentAdapter;

    private final String[] mTitles = {"商品", "详情", "评价"};
    private List<Fragment> mFragments;


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
        mFragments = new ArrayList<>();
        mFragments.add(new GoodsSlideFragment());
        mFragments.add(new GoodsWebFragment());
        mFragments.add(new GoodsCommentFragment());

        fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.setLists(mFragments);
        view_pager.setAdapter(fragmentAdapter);
        tab.setViewPager(view_pager,mTitles);
        view_pager.setCurrentItem(0);
    }

    private void initData() {



    }

}

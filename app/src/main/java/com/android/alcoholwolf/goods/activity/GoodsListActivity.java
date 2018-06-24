package com.android.alcoholwolf.goods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseActivity;
import com.android.alcoholwolf.amain.activity.MainActivity;
import com.android.alcoholwolf.amain.bean.FirstClassBean;
import com.android.alcoholwolf.amain.bean.GoodsListInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pkqup.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/6/23.
 * @Describe:
 */
public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_new)
    TextView tv_new;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_filter)
    TextView tv_filter;

    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    private boolean listType = true;//是否是列表形式
    private List<GoodsListInfoBean> lists;
    private LinearAdapter linearAdapter;
    private GridAdapter gridAdapter;


    private String secondClassId;
    private String secondClassName;

    public static void startGoodsListActivity(Activity activity, String secondClassId, String secondClassName) {
        Intent intent = new Intent(activity, GoodsListActivity.class);
        intent.putExtra("secondClassId", secondClassId);
        intent.putExtra("secondClassName", secondClassName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_list);
        initView();
        initData();
    }

    @Override
    public void setTitleView() {
        titleImgRightTwo.setVisibility(View.VISIBLE);
        secondClassId = getIntent().getStringExtra("secondClassId");
        secondClassName = getIntent().getStringExtra("secondClassName");
        titleName.setText(secondClassName);
    }

    private void initView() {
        tv_all.setOnClickListener(onClickListener);
        tv_new.setOnClickListener(onClickListener);
        tv_price.setOnClickListener(onClickListener);
        tv_class.setOnClickListener(onClickListener);
        tv_filter.setOnClickListener(onClickListener);
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setOnClickListener(onClickListener);
        titleImgRightTwo.setOnClickListener(onClickListener);

        lists = new ArrayList<>();
        linearAdapter = new LinearAdapter(R.layout.amain_item_goods_list_linear, lists);
        gridAdapter = new GridAdapter(R.layout.amain_item_goods_list_grid, lists);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(linearAdapter);
    }

    private void initData() {
        lists.clear();
        for (int i = 0; i < 30; i++) {
            GoodsListInfoBean goodsListInfoBean = new GoodsListInfoBean();
            lists.add(goodsListInfoBean);
        }
        if(listType){
            linearAdapter.setNewData(lists);
        }else{
            gridAdapter.setNewData(lists);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    break;
                case R.id.img_title_right:
                    break;
                case R.id.img_title_right_change:
                    changeListType();
                    break;
                case R.id.tv_all:
                    break;
                case R.id.tv_new:
                    break;
                case R.id.tv_price:
                    break;
                case R.id.tv_class:
                    break;
                case R.id.tv_filter:
                    break;
            }
        }
    };

    private void changeListType() {
        if (listType) {
            //列表切换到网格
            listType = false;
            recycle_view.setLayoutManager(new GridLayoutManager(this, 2));
            recycle_view.setAdapter(gridAdapter);
        } else {
            //网格切换到列表
            listType = true;
            recycle_view.setLayoutManager(new LinearLayoutManager(this));
            recycle_view.setAdapter(linearAdapter);
        }
    }


    public class LinearAdapter extends BaseQuickAdapter<GoodsListInfoBean, BaseViewHolder> {
        public LinearAdapter(int layoutResId, List<GoodsListInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListInfoBean item) {
        }
    }

    public class GridAdapter extends BaseQuickAdapter<GoodsListInfoBean, BaseViewHolder> {
        public GridAdapter(int layoutResId, List<GoodsListInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListInfoBean item) {

            ImageView imaPic = helper.getView(R.id.img_pic);
            ViewGroup.LayoutParams layoutParams = imaPic.getLayoutParams();
            int picWidth = (SizeUtils.getScreenWidth() - SizeUtils.dp2px(40)) / 2;
            layoutParams.width = picWidth;
            layoutParams.height = picWidth;
            imaPic.setLayoutParams(layoutParams);
        }
    }

}

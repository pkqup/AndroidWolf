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
import com.chunlangjiu.app.goods.bean.ShopInfoBean;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.store.bean.StoreClassListBean;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe: 名庄查询页面
 */
public class StoreListActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<StoreClassListBean.StoreClassBean> lists;
    private StoreAdapter storeAdapter;

    private CompositeDisposable disposable;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.img_title_right_one:
                    startActivity(new Intent(StoreListActivity.this, StoreSearchActivity.class));
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("名庄查询");
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setVisibility(View.VISIBLE);
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
        disposable = new CompositeDisposable();
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout.setEnableRefresh(false);//设置不可以下拉刷新
        refreshLayout.setEnableLoadMore(false);//设置不能加载更多
        lists = new ArrayList<>();
        storeAdapter = new StoreAdapter(R.layout.store_item_store_class, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storeAdapter);
        storeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StoreSearchActivity.startStoreSearchActivity(StoreListActivity.this,lists.get(position).getChateaucat_name(),
                        lists.get(position).getChateaucat_id());
            }
        });
    }

    private void initData() {
        disposable.add(ApiUtils.getInstance().getStoreClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<StoreClassListBean>>() {
                    @Override
                    public void accept(ResultBean<StoreClassListBean> storeClassListBeanResultBean) throws Exception {
                        getClassListSuccess(storeClassListBeanResultBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getClassListSuccess(ResultBean<StoreClassListBean> storeClassListBeanResultBean) {
        List<StoreClassListBean.StoreClassBean> list = storeClassListBeanResultBean.getData().getList();
        if (list.size() > 0) {
            this.lists = list;
            storeAdapter.setNewData(lists);
        }
    }


    public class StoreAdapter extends BaseQuickAdapter<StoreClassListBean.StoreClassBean, BaseViewHolder> {

        public StoreAdapter(int layoutResId, List<StoreClassListBean.StoreClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, StoreClassListBean.StoreClassBean item) {
            helper.setText(R.id.tvName, item.getChateaucat_name());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

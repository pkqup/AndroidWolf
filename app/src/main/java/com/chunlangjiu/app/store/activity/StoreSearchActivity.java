package com.chunlangjiu.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.store.adapter.SearchStoreAdapter;
import com.chunlangjiu.app.store.bean.SearchBean;
import com.chunlangjiu.app.store.bean.SearchSecondBean;
import com.chunlangjiu.app.store.bean.StoreClassListBean;
import com.chunlangjiu.app.store.bean.StoreListBean;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe: 名庄搜索页面1
 */
public class StoreSearchActivity extends BaseActivity {

    @BindView(R.id.rlMore)
    RelativeLayout rlMore;

    @BindView(R.id.exListView)
    ExpandableListView exListView;

    private CompositeDisposable disposable;

    private List<SearchBean> lists;
    private SearchStoreAdapter searchStoreAdapter;

    private String classId;
    private String className;


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


    public static void startStoreSearchActivity(Activity activity, String className,String classId) {
        Intent intent = new Intent(activity, StoreSearchActivity.class);
        intent.putExtra("className", className);
        intent.putExtra("classId", classId);
        activity.startActivity(intent);
    }

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
        disposable = new CompositeDisposable();
        rlMore.setOnClickListener(onClickListener);

        lists = new ArrayList<>();
        searchStoreAdapter = new SearchStoreAdapter(this, lists);
        exListView.setAdapter(searchStoreAdapter);
        exListView.setGroupIndicator(null);
        exListView.setDivider(null);
        exListView.setCacheColorHint(0);
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                StoreDetailsActivity.startStoreDetailActivity(StoreSearchActivity.this,lists.get(groupPosition).getList().get(childPosition).getId());

                return true;
            }
        });

        classId = getIntent().getStringExtra("classId");
        className = getIntent().getStringExtra("className");
    }

    private void initData() {

        disposable.add(ApiUtils.getInstance().getStoreList(classId,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<StoreListBean>>() {
                    @Override
                    public void accept(ResultBean<StoreListBean> storeListBeanResultBean) throws Exception {
                        getListSuccess(storeListBeanResultBean.getData().getList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getListSuccess(List<StoreListBean.Store> list) {
        SearchBean searchBean = new SearchBean();
        searchBean.setId(classId);
        searchBean.setName(className);

        ArrayList<SearchSecondBean> secondBeans = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            SearchSecondBean searchSecondBean = new SearchSecondBean();
            searchSecondBean.setId(list.get(j).getChateau_id());
            searchSecondBean.setName(list.get(j).getName());
            secondBeans.add(searchSecondBean);
        }
        searchBean.setList(secondBeans);
        lists.add(searchBean);

        searchStoreAdapter.setLists(lists);

        int groupCount = exListView.getCount();

        for (int i=0; i<groupCount; i++) {
            exListView.expandGroup(i);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

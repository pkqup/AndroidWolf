package com.android.alcoholwolf.amain.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseFragment;
import com.android.alcoholwolf.amain.bean.RealmBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pkqup.commonlibrary.net.bean.Result;
import com.pkqup.commonlibrary.realm.RealmUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 首页
 */
public class HomeFragment extends BaseFragment {

    private RecyclerView listView;
    private RefreshLayout refreshLayout;
    private List lists;
    private HomeAdapter homeAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_home, container, true);
    }

    @Override
    public void initView() {
        View headerView = View.inflate(getActivity(), R.layout.amain_item_home_header, null);

        lists = new ArrayList();
        listView = rootView.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(R.layout.amain_item_home_list, lists);
        homeAdapter.addHeaderView(headerView);
        listView.setAdapter(homeAdapter);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启滑到底部自动加载
        refreshLayout.autoRefresh();       //触发自动刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLaut) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lists.clear();
                        lists.addAll(getData());
                        homeAdapter.setNewData(lists);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                }, 1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLaut) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                        lists.addAll(getData());
                        homeAdapter.setNewData(lists);
                        if (lists.size() > 50) {
                            listView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.finishLoadMoreWithNoMoreData();//显示没有更多数据
                                }
                            },1000);

                        }
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void initData() {

        RealmBean realmBean = new RealmBean();
        realmBean.setAge("10");
        realmBean.setName("名字");

        RealmUtils.add(realmBean);

    }


    public class HomeAdapter extends BaseQuickAdapter<Result, BaseViewHolder> {

        public HomeAdapter(int layoutResId, List<Result> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Result item) {
            helper.setText(R.id.tv_name, helper.getAdapterPosition() + "");
        }
    }

    private List getData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}

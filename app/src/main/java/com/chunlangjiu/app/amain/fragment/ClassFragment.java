package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.adapter.SecondClassAdapter;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.chunlangjiu.app.goods.activity.GoodsListActivity;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 分类
 */
public class ClassFragment extends BaseFragment {

    private RelativeLayout rl_search;
    private RecyclerView firstRecycleView;
    private ExpandableListView exListView;

    private List<FirstClassBean> firstLists;
    private FirstClassAdapter firstAdapter;

    private List<SecondClassBean> secondList;
    private SecondClassAdapter secondClassAdapter;

    private CompositeDisposable disposable;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_search:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
            }
        }
    };

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_class, container, true);
    }

    @Override
    public void initView() {
        rl_search = rootView.findViewById(R.id.rl_search);
        rl_search.setOnClickListener(onClickListener);
        firstRecycleView = rootView.findViewById(R.id.first_class_recycle_view);
        exListView = rootView.findViewById(R.id.exListView);

        firstLists = new ArrayList<>();
        firstAdapter = new FirstClassAdapter(R.layout.amain_item_first_class, firstLists);
        firstRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firstRecycleView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                changeFirstClass(position);
            }
        });

        secondList = new ArrayList<>();
        secondClassAdapter = new SecondClassAdapter(getActivity(), secondList);
        exListView.setAdapter(secondClassAdapter);
        exListView.setGroupIndicator(null);
        exListView.setDivider(null);
        exListView.setCacheColorHint(0);
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //设置点击分类头部不收缩
                return true;
            }
        });
    }


    @Override
    public void initData() {
        disposable = new CompositeDisposable();
        getClassData();
    }

    private void getClassData() {
        disposable.add(ApiUtils.getInstance().getMainClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<MainClassBean>>() {
                    @Override
                    public void accept(ResultBean<MainClassBean> mainClassBean) throws Exception {
                        mainClassBean.getData().getCategorys().get(0).setSelect(true);
                        firstLists = mainClassBean.getData().getCategorys();
                        firstAdapter.setNewData(mainClassBean.getData().getCategorys());
                        secondList = mainClassBean.getData().getCategorys().get(0).getLv2();
                        secondClassAdapter.setLists(secondList);
                        int groupCount = exListView.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            exListView.expandGroup(i);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void changeFirstClass(int position) {
        if (!firstLists.get(position).isSelect()) {
            for (int i = 0; i < firstLists.size(); i++) {
                if (i == position) {
                    firstLists.get(i).setSelect(true);
                } else {
                    firstLists.get(i).setSelect(false);
                }
            }
            firstAdapter.notifyDataSetChanged();


            secondList = firstLists.get(position).getLv2();
            secondClassAdapter.setLists(new ArrayList<SecondClassBean>());
            secondClassAdapter.setLists(secondList);
            int groupCount = exListView.getCount();
            for (int i = 0; i < groupCount; i++) {
                exListView.expandGroup(i);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public class FirstClassAdapter extends BaseQuickAdapter<FirstClassBean, BaseViewHolder> {
        public FirstClassAdapter(int layoutResId, List<FirstClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FirstClassBean item) {
            TextView tvClass = helper.getView(R.id.tv_class);
            tvClass.setText(item.getCat_name());
            tvClass.setSelected(item.isSelect());
        }
    }


}

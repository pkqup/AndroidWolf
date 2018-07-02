package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.chunlangjiu.app.goods.activity.GoodsListActivity;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.pkqup.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/16.
 * @Describe: 分类
 */
public class ClassFragment extends BaseFragment {

    private RelativeLayout rl_search;
    private TextView tv_choice_class;
    private RecyclerView firstRecycleView;
    private RecyclerView secondRecycleView;


    private List<FirstClassBean> firstLists;
    private FirstClassAdapter firstAdapter;

    private List<SecondClassBean> secondLists;
    private SecondClassAdapter secondAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_class, container, true);
    }

    @Override
    public void initView() {
        rl_search = rootView.findViewById(R.id.rl_search);
        rl_search.setOnClickListener(onClickListener);
        tv_choice_class = rootView.findViewById(R.id.tv_choice_class);
        firstRecycleView = rootView.findViewById(R.id.first_class_recycle_view);
        secondRecycleView = rootView.findViewById(R.id.second_class_recycle_view);

        firstLists = new ArrayList<>();
        firstAdapter = new FirstClassAdapter(R.layout.amain_item_first_class, firstLists);
        firstRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firstRecycleView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!firstLists.get(position).getSelect()) {
                    for (int i = 0; i < firstLists.size(); i++) {
                        if (i == position) {
                            firstLists.get(i).setSelect(true);
                        } else {
                            firstLists.get(i).setSelect(false);
                        }
                    }
                    tv_choice_class.setText(firstLists.get(position).getName());
                    firstAdapter.notifyDataSetChanged();
                }
            }
        });

        secondLists = new ArrayList<>();
        secondAdapter = new SecondClassAdapter(R.layout.amain_item_second_class, secondLists);
        secondRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        secondRecycleView.setAdapter(secondAdapter);
        secondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsListActivity.startGoodsListActivity(getActivity(),"11","白酒");
            }
        });
    }

    @Override
    public void initData() {
        firstLists.clear();
        for (int i = 0; i < 10; i++) {
            FirstClassBean firstClassBean = new FirstClassBean();
            firstClassBean.setSelect(i == 0);
            firstClassBean.setId(i + "");
            firstClassBean.setName("分类" + i);
            firstLists.add(firstClassBean);
        }
        firstAdapter.setNewData(firstLists);
        tv_choice_class.setText(firstLists.get(0).getName());

        secondLists.clear();
        for (int i = 0; i < 10; i++) {
            SecondClassBean secondClassBean = new SecondClassBean();
            secondClassBean.setId(i + "");
            secondClassBean.setName("红酒" + i);
            secondLists.add(secondClassBean);
        }
        secondAdapter.setNewData(secondLists);
    }

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

    public class FirstClassAdapter extends BaseQuickAdapter<FirstClassBean, BaseViewHolder> {
        public FirstClassAdapter(int layoutResId, List<FirstClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FirstClassBean item) {
            TextView tvClass = helper.getView(R.id.tv_class);
            tvClass.setText(item.getName());
            tvClass.setSelected(item.getSelect());
        }
    }

    public class SecondClassAdapter extends BaseQuickAdapter<SecondClassBean, BaseViewHolder> {
        public SecondClassAdapter(int layoutResId, List<SecondClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SecondClassBean item) {
            ImageView imgPic = helper.getView(R.id.img_pic);
            ViewGroup.LayoutParams layoutParams = imgPic.getLayoutParams();
            int screenWidth = SizeUtils.getScreenWidth();
            int picWidth = (screenWidth - SizeUtils.dp2px(115)) / 3;
            layoutParams.height = picWidth;
            layoutParams.width = picWidth;
            imgPic.setLayoutParams(layoutParams);
            helper.setText(R.id.tv_name, item.getName());
        }
    }

}

package com.chunlangjiu.app.store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.store.bean.StoreDetailBean;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe: 详细介绍
 */
public class DetailDescFragment extends HeaderViewPagerFragment {

    private View scrollView;
    private TextView tvIntroduce;

    public static DetailDescFragment newInstance(StoreDetailBean.StoreBean storeBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("storeBean", storeBean);
        DetailDescFragment goodsFragment = new DetailDescFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (scrollView == null) {
            scrollView = inflater.inflate(R.layout.store_fragment_detail, container, false);
            initView();
            initData();
        }
        return scrollView;
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }


    private void initView() {
        tvIntroduce = scrollView.findViewById(R.id.tvIntroduce);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            StoreDetailBean.StoreBean storeBean = (StoreDetailBean.StoreBean) bundle.getSerializable("storeBean");
            tvIntroduce.setText("        " + storeBean.getIntro());
        }
    }
}
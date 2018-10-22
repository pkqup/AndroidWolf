package com.chunlangjiu.app.store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.store.bean.StoreDetailBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe: 历年评分
 */
public class GradeFragment extends HeaderViewPagerFragment {


    private View scrollView;
    private LinearLayout llGrand;

    public static GradeFragment newInstance(StoreDetailBean.StoreBean storeBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("storeBean", storeBean);
        GradeFragment goodsFragment = new GradeFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (scrollView == null) {
            scrollView = inflater.inflate(R.layout.store_fragment_grade, container, false);
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
        llGrand = scrollView.findViewById(R.id.llGrand);
    }

    private void initData() {
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                StoreDetailBean.StoreBean storeBean = (StoreDetailBean.StoreBean) bundle.getSerializable("storeBean");
                List<StoreDetailBean.Grade> grade = storeBean.getGrade();
                if (grade != null && grade.size() > 0) {
                    for (int i = 0; i < grade.size(); i++) {
                        View grandItem = View.inflate(getActivity(), R.layout.store_item_grand, null);
                        TextView tvYear = grandItem.findViewById(R.id.tvYear);
                        TextView tvRp = grandItem.findViewById(R.id.tvRp);
                        TextView tvWs = grandItem.findViewById(R.id.tvWs);
                        TextView tvJr = grandItem.findViewById(R.id.tvJr);
                        tvYear.setText(grade.get(i).getYear());
                        tvRp.setText(grade.get(i).getRp());
                        tvWs.setText(grade.get(i).getWs());
                        tvJr.setText(grade.get(i).getJr());
                        llGrand.addView(grandItem);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
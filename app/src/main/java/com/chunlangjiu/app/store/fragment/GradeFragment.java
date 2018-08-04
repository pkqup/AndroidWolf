package com.chunlangjiu.app.store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe: 历年评分
 */
public class GradeFragment extends HeaderViewPagerFragment {


    private View scrollView;
    private LinearLayout llGrand;

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
        for (int i = 0; i < 10; i++) {
            View grandItem = View.inflate(getActivity(), R.layout.store_item_grand, null);
            TextView tvYear = grandItem.findViewById(R.id.tvYear);
            ImageView imgGrand = grandItem.findViewById(R.id.imgGrand);
            TextView tvRp = grandItem.findViewById(R.id.tvRp);
            TextView tvWs = grandItem.findViewById(R.id.tvWs);
            TextView tvJr = grandItem.findViewById(R.id.tvJr);

            tvYear.setText(String.valueOf(2018 - i));
            tvRp.setText(String.valueOf(100 - i));
            tvWs.setText(String.valueOf(90 - i));
            tvJr.setText(String.valueOf(80 - i));
            llGrand.addView(grandItem);
        }
    }

}
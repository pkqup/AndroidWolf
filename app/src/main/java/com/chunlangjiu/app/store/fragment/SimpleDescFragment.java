package com.chunlangjiu.app.store.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.fragment.GoodsFragment;
import com.chunlangjiu.app.store.bean.StoreDetailBean;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe: 酒庄简介
 */
public class SimpleDescFragment extends HeaderViewPagerFragment {

    private View scrollView;

    private TextView tvIntroduce;
    private TextView tvArea;
    private TextView tvSize;
    private TextView tvAge;

    private LinearLayout llDrink;
    private LinearLayout llClass;

    private TextView tvPhone;
    private TextView tvWeb;
    private TextView tvAddress;

    public static SimpleDescFragment newInstance(StoreDetailBean.StoreBean storeBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("storeBean", storeBean);
        SimpleDescFragment goodsFragment = new SimpleDescFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (scrollView == null) {
            scrollView = inflater.inflate(R.layout.store_fragment_simple, container, false);
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
        tvArea = scrollView.findViewById(R.id.tvArea);
        tvSize = scrollView.findViewById(R.id.tvSize);
        tvAge = scrollView.findViewById(R.id.tvAge);
        llDrink = scrollView.findViewById(R.id.llDrink);
        llClass = scrollView.findViewById(R.id.llClass);
        tvPhone = scrollView.findViewById(R.id.tvPhone);
        tvWeb = scrollView.findViewById(R.id.tvWeb);
        tvAddress = scrollView.findViewById(R.id.tvAddress);
    }

    private void initData() {
       /* for (int i = 0; i < 2; i++) {
            View drinkItem = View.inflate(getActivity(), R.layout.store_item_detail_drink, null);
            TextView tvName = drinkItem.findViewById(R.id.tvName);
            TextView tvNum = drinkItem.findViewById(R.id.tvNum);
            tvName.setText("拉菲" + i);
            tvNum.setText("100" + i + "箱");
            llDrink.addView(drinkItem);
        }

        for (int i = 0; i < 4; i++) {
            View drinkItem = View.inflate(getActivity(), R.layout.store_item_detail_class, null);
            TextView tvName = drinkItem.findViewById(R.id.tvName);
            tvName.setText("拉菲" + i);
            llClass.addView(drinkItem);
        }*/

        Bundle bundle = getArguments();
        if (bundle != null) {
            StoreDetailBean.StoreBean storeBean = (StoreDetailBean.StoreBean) bundle.getSerializable("storeBean");
            if (storeBean != null)
                tvIntroduce.setText(storeBean.getIntro());
            tvPhone.setText(storeBean.getPhone());
        }
    }

}

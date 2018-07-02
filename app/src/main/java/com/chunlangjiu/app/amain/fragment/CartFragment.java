package com.chunlangjiu.app.amain.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.adapter.CartGoodsListAdapter;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class CartFragment extends BaseFragment {

    RecyclerView recycleView;

    private List<CartGoodsBean> lists;
    private CartGoodsListAdapter cartAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_cart, container, true);
    }

    @Override
    public void initView() {
        recycleView = rootView.findViewById(R.id.recycle_view);

        lists = new ArrayList<>();
        cartAdapter = new CartGoodsListAdapter(getActivity(), lists);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(cartAdapter);
    }

    @Override
    public void initData() {

        for (int i = 0; i < 15; i++) {
            CartGoodsBean cartGoodsBean = new CartGoodsBean();
            if (i == 0 || i == 5 || i == 10) {
                cartGoodsBean.setItemType(CartGoodsBean.ITEM_STORE);
            } else {
                cartGoodsBean.setItemType(CartGoodsBean.ITEM_GOODS);
            }
            lists.add(cartGoodsBean);
            cartAdapter.setNewData(lists);
        }

    }
}

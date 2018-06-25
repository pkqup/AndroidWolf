package com.android.alcoholwolf.amain.adapter;

import android.content.Context;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.amain.bean.CartGoodsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class CartGoodsListAdapter extends BaseQuickAdapter<CartGoodsBean, BaseViewHolder> {


    private Context context;
    private List<CartGoodsBean> list;

    public CartGoodsListAdapter(Context context, List<CartGoodsBean> list) {
        super(list);
        setMultiTypeDelegate(new MultiTypeDelegate<CartGoodsBean>() {
            @Override
            protected int getItemType(CartGoodsBean cartGoodsBean) {
                //根据你的实体类来判断布局类型
                return cartGoodsBean.getItemType();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(CartGoodsBean.ITEM_STORE, R.layout.amain_item_cart_store)
                .registerItemType(CartGoodsBean.ITEM_GOODS, R.layout.amain_item_cart_goods);
    }


    @Override
    protected void convert(BaseViewHolder helper, CartGoodsBean item) {
        switch (helper.getItemViewType()) {
            case CartGoodsBean.ITEM_STORE:
                if (helper.getAdapterPosition() == 0) {
                    helper.setVisible(R.id.view_line, false);
                } else {
                    helper.setVisible(R.id.view_line, true);
                }
                break;
            case CartGoodsBean.ITEM_GOODS:

                break;
        }
    }
}

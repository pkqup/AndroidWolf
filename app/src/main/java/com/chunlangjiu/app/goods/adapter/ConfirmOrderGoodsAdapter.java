package com.chunlangjiu.app.goods.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.OrderGoodsBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/10
 * @Describe:
 */
public class ConfirmOrderGoodsAdapter extends BaseQuickAdapter<OrderGoodsBean, BaseViewHolder> {


    private Context context;
    private List<OrderGoodsBean> list;

    public ConfirmOrderGoodsAdapter(Context context, List<OrderGoodsBean> list) {
        super(list);
        setMultiTypeDelegate(new MultiTypeDelegate<OrderGoodsBean>() {
            @Override
            protected int getItemType(OrderGoodsBean cartGoodsBean) {
                //根据你的实体类来判断布局类型
                return cartGoodsBean.getItemType();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(OrderGoodsBean.ITEM_STORE, R.layout.amain_item_cart_store)
                .registerItemType(OrderGoodsBean.ITEM_GOODS, R.layout.amain_item_cart_goods);
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderGoodsBean item) {
        switch (helper.getItemViewType()) {
            case OrderGoodsBean.ITEM_STORE:
                if (helper.getAdapterPosition() == 0) {
                    helper.setVisible(R.id.view_line, false);
                } else {
                    helper.setVisible(R.id.view_line, true);
                }
                break;
            case OrderGoodsBean.ITEM_GOODS:

                break;
        }
    }
}

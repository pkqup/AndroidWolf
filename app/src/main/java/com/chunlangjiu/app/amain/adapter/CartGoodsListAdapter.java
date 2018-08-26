package com.chunlangjiu.app.amain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.goods.bean.OrderGoodsBean;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.util.BigDecimalUtils;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class CartGoodsListAdapter extends BaseQuickAdapter<CartGoodsBean, BaseViewHolder> {

    private Context context;
    private boolean isEdit;

    public CartGoodsListAdapter(Context context, List<CartGoodsBean> list) {
        super(list);
        this.context = context;
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

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CartGoodsBean item) {
        try {
            switch (item.getItemType()) {
                case CartGoodsBean.ITEM_STORE:
                    if (viewHolder.getAdapterPosition() == 0) {
                        viewHolder.setGone(R.id.view_line, false);
                    } else {
                        viewHolder.setGone(R.id.view_line, true);
                    }

                    ImageView img_store = viewHolder.getView(R.id.img_store);
                    ImageView check_store = viewHolder.getView(R.id.check_store);
                    GlideUtils.loadImage(context, item.getStoreLogo(), img_store);
                    viewHolder.setText(R.id.tv_store_name, item.getStoreName());
                    check_store.setSelected(item.isStoreCheck());
                    viewHolder.addOnClickListener(R.id.check_store);
                    break;
                case OrderGoodsBean.ITEM_GOODS:
                    ImageView img_pic = viewHolder.getView(R.id.img_pic);
                    ImageView check_goods = viewHolder.getView(R.id.check_goods);
                    GlideUtils.loadImage(context, item.getGoodsPic(), img_pic);
                    viewHolder.setText(R.id.tv_name, item.getGoodsName());
                    int adapterPosition = viewHolder.getAdapterPosition();
                    viewHolder.setText(R.id.tv_price, "¥" + BigDecimalUtils.add(item.getGoodsPrice(), "0.00", 2));
                    viewHolder.setText(R.id.tvNum, item.getGoodsNum());
                    check_goods.setSelected(item.isGoodsCheck());

                    LinearLayout llChangeNum = viewHolder.getView(R.id.llChangeNum);
                    ImageView deleteOne = viewHolder.getView(R.id.deleteOne);
                    if (isEdit) {
                        llChangeNum.setVisibility(View.GONE);
                        deleteOne.setVisibility(View.VISIBLE);
                    } else {
                        llChangeNum.setVisibility(View.VISIBLE);
                        deleteOne.setVisibility(View.GONE);
                    }
                    viewHolder.addOnClickListener(R.id.check_goods);
                    viewHolder.addOnClickListener(R.id.imgSub);
                    viewHolder.addOnClickListener(R.id.imgAdd);
                    viewHolder.addOnClickListener(R.id.deleteOne);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

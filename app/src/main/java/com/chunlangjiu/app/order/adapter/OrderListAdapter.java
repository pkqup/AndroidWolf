package com.chunlangjiu.app.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.AuctionGoodsBean;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.pkqup.commonlibrary.glide.GlideUtils;

import java.util.List;

public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    public OrderListAdapter(Context context, int layoutResId, List<OrderListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ListBean item) {
        ImageView imgStore = helper.getView(R.id.imgStore);
        TextView tvStore = helper.getView(R.id.tvStore);
        TextView tvStatus = helper.getView(R.id.tvStatus);
        TextView tvTotalNum = helper.getView(R.id.tvTotalNum);

        tvStore.setText(item.getShopname());
        tvStatus.setText(item.getStatus_desc());
//        GlideUtils.loadImage(context, item.get(), imgStore);
        tvTotalNum.setText(String.format("合计：¥%s", item.getPayment()));

        LinearLayout llProducts = helper.getView(R.id.llProducts);
        llProducts.removeAllViews();
        for (OrderListBean.ListBean.OrderBean orderBean : item.getOrder()) {
            View inflate = inflater.inflate(R.layout.order_adapter_list_product_item, null);
            ImageView imgProduct = inflate.findViewById(R.id.imgProduct);
            GlideUtils.loadImage(context, orderBean.getPic_path(), imgProduct);
            TextView tvProductName = inflate.findViewById(R.id.tvProductName);
            tvProductName.setText(orderBean.getTitle());
            TextView tvProductPrice = inflate.findViewById(R.id.tvProductPrice);

            TextView tvProductDesc = inflate.findViewById(R.id.tvProductDesc);
//            tvProductDesc.setText(orderBean.get);

            TextView tvProductNum = inflate.findViewById(R.id.tvProductNum);
            tvProductNum.setText(String.format("x%d", orderBean.getNum()));

            llProducts.addView(inflate);
        }
    }
}

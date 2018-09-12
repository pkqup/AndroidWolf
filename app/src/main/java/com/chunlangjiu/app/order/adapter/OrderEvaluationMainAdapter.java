package com.chunlangjiu.app.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.order.bean.OrderListBean;
import com.pkqup.commonlibrary.glide.GlideUtils;

import java.util.List;

public class OrderEvaluationMainAdapter extends BaseQuickAdapter<OrderListBean.ListBean.OrderBean, BaseViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private List<OrderListBean.ListBean.OrderBean> data;

    public OrderEvaluationMainAdapter(Context context, int layoutResId, List<OrderListBean.ListBean.OrderBean> data) {
        super(layoutResId, data);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ListBean.OrderBean item) {
        LinearLayout llParent = helper.getView(R.id.llParent);
        llParent.setTag(helper.getAdapterPosition());
        llParent.setOnClickListener(onClickListener);
        ImageView imgProduct = helper.getView(R.id.imgProduct);
        GlideUtils.loadImage(context, item.getPic_path(), imgProduct);
        TextView tvProductName = helper.getView(R.id.tvProductName);
        tvProductName.setText(item.getTitle());
        TextView tvProductPrice = helper.getView(R.id.tvProductPrice);

        TextView tvProductDesc = helper.getView(R.id.tvProductDesc);
//            tvProductDesc.setText(orderBean.get);

        TextView tvProductNum = helper.getView(R.id.tvProductNum);
        tvProductNum.setText(String.format("x%d", item.getNum()));
        if (helper.getAdapterPosition() == data.size()) {
            View view_line = helper.getView(R.id.view_line);
            view_line.setVisibility(View.GONE);
        }
    }
}

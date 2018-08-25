package com.chunlangjiu.app.goods.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.FilterBrandBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class FilterBrandAdapter extends BaseQuickAdapter<FilterBrandBean, BaseViewHolder> {

    public FilterBrandAdapter(int layoutResId, List<FilterBrandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterBrandBean item) {
        TextView tvName = helper.getView(R.id.tvName);
        tvName.setText(item.getBrand_name());
        if (item.isSelect()) {
            tvName.setSelected(true);
        } else {
            tvName.setSelected(false);
        }
    }
}

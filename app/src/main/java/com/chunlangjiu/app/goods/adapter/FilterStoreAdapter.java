package com.chunlangjiu.app.goods.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.FilterStoreBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class FilterStoreAdapter extends BaseQuickAdapter<FilterStoreBean, BaseViewHolder> {

    public FilterStoreAdapter(int layoutResId, List<FilterStoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterStoreBean item) {
        TextView tvName = helper.getView(R.id.tvName);
        tvName.setText(item.getCat_name());
        if (item.isSelect()) {
            tvName.setSelected(true);
        } else {
            tvName.setSelected(false);
        }
    }
}
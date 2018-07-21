package com.chunlangjiu.app.amain.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.amain.bean.BrandBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe: 品牌推荐
 */
public class BrandAdapter extends BaseQuickAdapter<BrandBean,BaseViewHolder> {

    public BrandAdapter(int layoutResId, List<BrandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandBean item) {

    }


}

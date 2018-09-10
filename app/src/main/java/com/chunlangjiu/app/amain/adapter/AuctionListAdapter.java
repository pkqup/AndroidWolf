package com.chunlangjiu.app.amain.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/26
 * @Describe:
 */
public class AuctionListAdapter extends BaseQuickAdapter<ResultBean, BaseViewHolder> {

    public AuctionListAdapter(int layoutResId, List<ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultBean item) {


    }

    public static class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }

    }

}


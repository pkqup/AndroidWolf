package com.android.alcoholwolf.amain.adapter;

import android.view.View;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.amain.bean.AuctionGoodsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/26
 * @Describe:
 */
public class AuctionListAdapter extends BaseQuickAdapter<AuctionGoodsBean, BaseViewHolder> {

    public AuctionListAdapter(int layoutResId, List<AuctionGoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuctionGoodsBean item) {


    }

    public static class MyViewHolder extends BaseViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);
        }

    }

}


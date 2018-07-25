package com.chunlangjiu.app.amain.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.amain.bean.HomeBean;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe:
 */
public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {

    public HomeAdapter(Context context, List<HomeBean> list) {
        super(list);
        setMultiTypeDelegate(new MultiTypeDelegate<HomeBean>() {
            @Override
            protected int getItemType(HomeBean homeBean) {
                //根据你的实体类来判断布局类型
                return homeBean.getItemType();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(HomeBean.ITEM_GOODS, R.layout.amain_item_home_list_goods)
                .registerItemType(HomeBean.ITEM_PIC, R.layout.amain_item_home_list_pic);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, HomeBean item) {
        switch (viewHolder.getItemViewType()) {
            case HomeBean.ITEM_GOODS:
                CountdownView countdownView = viewHolder.getView(R.id.countdownView);
                long time2 = (long)3 * 24 * 60 * 60 * 1000;
                countdownView.start(time2);
                break;
            case HomeBean.ITEM_PIC:

                break;
        }
    }
}

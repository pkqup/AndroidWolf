package com.chunlangjiu.app.amain.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.chunlangjiu.app.amain.bean.HomeBean;
import com.chunlangjiu.app.goods.activity.GoodsListActivity;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.view.countdownview.CountdownView;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe:
 */
public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {

    private Context context;

    public HomeAdapter(Context context, List<HomeBean> list) {
        super(list);
        this.context = context;
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
                long time2 = (long) 3 * 24 * 60 * 60 * 1000;
                countdownView.start(time2);
                dealWithLifeCycle(viewHolder, viewHolder.getAdapterPosition());

                ImageView imgPic = viewHolder.getView(R.id.imgPic);
                TextView tvStartPrice = viewHolder.getView(R.id.tvStartPrice);

                GlideUtils.loadImage(context, item.getImgsrc(), imgPic);
                viewHolder.setText(R.id.tv_name, item.getTitle());
                viewHolder.setText(R.id.tvStartPriceStr, "原价：");
                tvStartPrice.setText("¥" + item.getPrice());
                tvStartPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                viewHolder.setText(R.id.tvSellPriceStr, "");
                viewHolder.setText(R.id.tvSellPrice, "¥" + item.getPrice());
                break;
            case HomeBean.ITEM_PIC:

                break;
        }
    }


    /**
     * 以下两个接口代替 activity.onStart() 和 activity.onStop(), 控制 timer 的开关
     */
    private void dealWithLifeCycle(final BaseViewHolder viewHolder, final int position) {
        final CountdownView countdownView = viewHolder.getView(R.id.countdownView);
        countdownView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                int pos = position;
                long time2 = (long) 3 * 24 * 60 * 60 * 1000;
                countdownView.start(time2);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                countdownView.stop();
            }
        });
    }

    public static class MyViewHolder extends BaseViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}

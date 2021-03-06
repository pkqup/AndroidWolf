package com.chunlangjiu.app.amain.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.AuctionListBean;
import com.chunlangjiu.app.amain.bean.HomeBean;
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
                .registerItemType(HomeBean.ITEM_JINGPAI, R.layout.amain_item_home_list_auction)
                .registerItemType(HomeBean.ITEM_TUIJIAN, R.layout.amain_item_home_list_pic);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, HomeBean item) {
        switch (viewHolder.getItemViewType()) {
            case HomeBean.ITEM_GOODS:
                CountdownView countdownView = viewHolder.getView(R.id.countdownView);
                ImageView imgPic = viewHolder.getView(R.id.imgPic);
                TextView tvStartPrice = viewHolder.getView(R.id.tvStartPrice);

                GlideUtils.loadImage(context, item.getImage_default_id(), imgPic);
                viewHolder.setText(R.id.tv_name, item.getTitle());
                tvStartPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                TextView tvLabel = viewHolder.getView(R.id.tvLabel);
                if (TextUtils.isEmpty(item.getLabel())) {
                    tvLabel.setVisibility(View.GONE);
                } else {
                    tvLabel.setVisibility(View.VISIBLE);
                    tvLabel.setText(item.getLabel());
                }
                LinearLayout llTime = viewHolder.getView(R.id.llTime);
                TextView tvSellPriceStr = viewHolder.getView(R.id.tvSellPriceStr);
                if (item.isAuction()) {
                    tvStartPrice.setText("¥" + item.getAuction_starting_price());
                    llTime.setVisibility(View.VISIBLE);
                    tvSellPriceStr.setVisibility(View.VISIBLE);
                    tvSellPriceStr.setText("最高出价：");
                    if (TextUtils.isEmpty(item.getMax_price())) {
                        viewHolder.setText(R.id.tvSellPrice, "暂无出价");
                    } else {
                        viewHolder.setText(R.id.tvSellPrice, "¥" + item.getAuction_starting_price());
                    }

                    String end_time = item.getAuction_end_time();
                        long endTime = 0;
                        if (!TextUtils.isEmpty(end_time)) {
                            endTime = Long.parseLong(end_time);
                        }
                        if ((endTime * 1000 - System.currentTimeMillis()) > 0) {
                            countdownView.start(endTime * 1000 - System.currentTimeMillis());
                            dealWithLifeCycle(viewHolder, viewHolder.getAdapterPosition(), item);
                        }
                } else {
                    llTime.setVisibility(View.GONE);
                    tvSellPriceStr.setVisibility(View.GONE);
                    viewHolder.setText(R.id.tvStartPriceStr, "原价：");
                    tvStartPrice.setText("¥" + item.getMkt_price());
                    viewHolder.setText(R.id.tvSellPriceStr, "");
                    viewHolder.setText(R.id.tvSellPrice, "¥" + item.getPrice());
                }
                break;
            case HomeBean.ITEM_TUIJIAN:

                break;
        }
    }

    /**
     * 以下两个接口代替 activity.onStart() 和 activity.onStop(), 控制 timer 的开关
     */
    private void dealWithLifeCycle(final BaseViewHolder viewHolder, final int position, final HomeBean item) {
        final CountdownView countdownView = viewHolder.getView(R.id.countdownView);
        countdownView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                String end_time = item.getAuction_end_time();
                try {
                    long endTime = 0;
                    if (!TextUtils.isEmpty(end_time)) {
                        endTime = Long.parseLong(end_time);
                    }
                    if ((endTime * 1000 - System.currentTimeMillis()) > 0) {
                        countdownView.start(endTime * 1000 - System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

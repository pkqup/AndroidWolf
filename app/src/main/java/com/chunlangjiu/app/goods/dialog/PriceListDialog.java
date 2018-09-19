package com.chunlangjiu.app.goods.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.GivePriceBean;
import com.chunlangjiu.app.user.bean.ShopCatIdList;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/19
 * @Describe:
 */
public class PriceListDialog extends Dialog {

    private Context context;
    private List<GivePriceBean> priceList;

    public PriceListDialog(Context context, List<GivePriceBean> priceList) {
        super(context, R.style.dialog_transparent);
        this.context = context;
        this.priceList = priceList;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_dialog_price_list, null);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);

        setContentView(view);// 设置布局

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        PriceAdapter priceAdapter = new PriceAdapter(R.layout.goods_item_auction_price, priceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(priceAdapter);
    }

    public class PriceAdapter extends BaseQuickAdapter<GivePriceBean, BaseViewHolder> {

        public PriceAdapter(int layoutResId, List<GivePriceBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GivePriceBean item) {
            helper.setText(R.id.tvMobile, item.getMobile());
            helper.setText(R.id.tvPrice, "(" + "¥" + item.getPrice() + ")");
        }
    }
}

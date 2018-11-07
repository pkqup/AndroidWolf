package com.chunlangjiu.app.amain.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.HomeModulesBean;
import com.pkqup.commonlibrary.glide.GlideUtils;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/21.
 * @Describe: 品牌推荐
 */
public class BrandAdapter extends BaseQuickAdapter<HomeModulesBean.Pic, BaseViewHolder> {

    private Context context;

    public BrandAdapter(Context context, int layoutResId, List<HomeModulesBean.Pic> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeModulesBean.Pic item) {
        ImageView imgPic = helper.getView(R.id.imgPic);
        GlideUtils.loadImage(context,item.getImage(),imgPic);
        helper.setText(R.id.tvBrandName,item.getBrandname());
        helper.setText(R.id.tvDesc,item.getLinkinfo());
    }


}

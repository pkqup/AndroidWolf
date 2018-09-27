package com.chunlangjiu.app.user.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.BrandsListBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class ChoiceBrandPopWindow extends PopupWindow {

    private Context context;
    private LayoutInflater inflater;
    private View mContentView;

    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    private List<BrandsListBean.BrandBean> brandLists;

    private CallBack callBack;
    private String selectBrandId;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void choiceBrand(String brandName, String brandId);
    }

    private void setSelectBrandId(String selectBrandId) {
        this.selectBrandId = selectBrandId;
    }

    public ChoiceBrandPopWindow(Context context, List<BrandsListBean.BrandBean> brands, String selectBrandId) {
        super(context);
        this.context = context;
        this.brandLists = brands;
        this.selectBrandId = selectBrandId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.popwindow_brand, null);
        setContentView(mContentView);

        //设置宽与高
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setBackgroundDrawable(new ColorDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_OUTSIDE;
            }
        });
        initView();
    }

    private void initView() {
        LinearLayout llPopWindow = mContentView.findViewById(R.id.ll_popWindow);
        llPopWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyclerView = mContentView.findViewById(R.id.recyclerView);
        brandAdapter = new BrandAdapter(R.layout.goods_item_pop_class, brandLists);
        brandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectBrandId = brandLists.get(position).getBrand_id();
                brandAdapter.notifyDataSetChanged();
                if (callBack != null) {
                    callBack.choiceBrand(brandLists.get(position).getBrand_name(), brandLists.get(position).getBrand_id());
                }
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(brandAdapter);
    }

    public void setBrandList(List<BrandsListBean.BrandBean> brands) {
        this.brandLists = brands;
        brandAdapter.setNewData(brandLists);
    }


    public class BrandAdapter extends BaseQuickAdapter<BrandsListBean.BrandBean, BaseViewHolder> {

        public BrandAdapter(int layoutResId, List<BrandsListBean.BrandBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BrandsListBean.BrandBean item) {
            TextView tvName = helper.getView(R.id.tvName);
            tvName.setText(item.getBrand_name());
            if (item.getBrand_id().equals(selectBrandId)) {
                tvName.setSelected(true);
            } else {
                tvName.setSelected(false);
            }
        }
    }

    /**
     * 适配7.0系统Popwindow显示全屏的问题
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    /**
     * 适配7.0系统Popwindow显示全屏的问题
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }


}

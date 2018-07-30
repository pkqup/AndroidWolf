package com.chunlangjiu.app.goods.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.GoodsListInfoBean;
import com.lzy.widget.HeaderViewPager;
import com.pkqup.commonlibrary.view.MyHeaderRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @CreatedbBy: liucun on 2018/7/5
 * @Describe: 卖家主页
 */
public class ShopMainActivity extends BaseActivity {

    @BindView(R.id.scrollableLayout)
    HeaderViewPager scrollableLayout;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recycle_view)
    MyHeaderRecycleView recycleView;

    @BindView(R.id.imgShow)
    ImageView imgShow;
    @BindView(R.id.imgHead)
    CircleImageView imgHead;
    @BindView(R.id.tvShopName)
    TextView tvShopName;
    @BindView(R.id.tvShopTips)
    TextView tvShopTips;
    @BindView(R.id.rlShopDetail)
    RelativeLayout rlShopDetail;
    @BindView(R.id.tvShopPhoto)
    TextView tvShopPhoto;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    private boolean showDesc = false;
    private List<GoodsListInfoBean> lists;
    private LinearAdapter linearAdapter;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.img_title_right_one:

                    break;
                case R.id.imgShow:
                    changeShopDesc();
                    break;
            }
        }
    };


    @Override
    public void setTitleView() {
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setVisibility(View.VISIBLE);
        titleImgRightOne.setImageResource(R.mipmap.icon_grid);
        titleImgRightOne.setOnClickListener(onClickListener);
        titleName.setVisibility(View.GONE);
        titleSearchView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_shop_main);
        initView();
        initData();
    }

    private void initView() {
        imgShow.setOnClickListener(onClickListener);

        scrollableLayout.setCurrentScrollableContainer(recycleView);
        lists = new ArrayList<>();
        linearAdapter = new LinearAdapter(R.layout.amain_item_goods_list_linear, lists);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(linearAdapter);
    }

    private void initData() {
        lists.clear();
        for (int i = 0; i < 30; i++) {
            GoodsListInfoBean goodsListInfoBean = new GoodsListInfoBean();
            lists.add(goodsListInfoBean);
        }
        linearAdapter.setNewData(lists);
    }


    private void changeShopDesc() {
        if (showDesc) {
            showDesc = false;
            imgShow.setImageResource(R.mipmap.gray_down);
            rlShopDetail.setVisibility(View.GONE);
        } else {
            showDesc = true;
            imgShow.setImageResource(R.mipmap.gray_up);
            rlShopDetail.setVisibility(View.VISIBLE);
        }
    }

    public class LinearAdapter extends BaseQuickAdapter<GoodsListInfoBean, BaseViewHolder> {
        public LinearAdapter(int layoutResId, List<GoodsListInfoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListInfoBean item) {
        }
    }
}

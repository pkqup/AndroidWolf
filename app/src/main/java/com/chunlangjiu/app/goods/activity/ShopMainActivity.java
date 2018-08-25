package com.chunlangjiu.app.goods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.GoodsListInfoBean;
import com.chunlangjiu.app.goods.adapter.FilterBrandAdapter;
import com.chunlangjiu.app.goods.adapter.FilterStoreAdapter;
import com.chunlangjiu.app.goods.bean.FilterBrandBean;
import com.chunlangjiu.app.goods.bean.FilterStoreBean;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
import com.chunlangjiu.app.goods.bean.GoodsListDetailBean;
import com.chunlangjiu.app.goods.bean.ShopInfoBean;
import com.chunlangjiu.app.goods.dialog.ClassPopWindow;
import com.chunlangjiu.app.net.ApiUtils;
import com.lzy.widget.HeaderViewPager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.pkqup.commonlibrary.view.MyHeaderRecycleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/5
 * @Describe: 卖家主页
 */
public class ShopMainActivity extends BaseActivity {

    private static final String ORDER_ALL = "modified_time";//综合
    private static final String ORDER_NEW = "list_time";//新品
    private static final String ORDER_PRICE_ASC = "price_asc";//价格升序
    private static final String ORDER_PRICE_DESC = "price_desc";//价格降序

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
    @BindView(R.id.tvShopPhone)
    TextView tvShopPhone;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.sortPrice)
    RelativeLayout sortPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.sortFilter)
    RelativeLayout sortFilter;
    @BindView(R.id.tv_filter)
    TextView tvFilter;

    @BindView(R.id.scrollableLayout)
    HeaderViewPager scrollableLayout;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.right_view)
    LinearLayout rightView;
    @BindView(R.id.etLowPrice)
    EditText etLowPrice;
    @BindView(R.id.etHighPrice)
    EditText etHighPrice;
    @BindView(R.id.etStartTime)
    EditText etStartTime;
    @BindView(R.id.etEndTime)
    EditText etEndTime;
    @BindView(R.id.tvReset)
    TextView tvReset;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.recyclerViewBrand)
    RecyclerView recyclerViewBrand;//品牌列表
    @BindView(R.id.recyclerViewStore)
    RecyclerView recyclerViewStore;//名庄列表

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycle_view)
    MyHeaderRecycleView recycleView;

    private CompositeDisposable disposable;
    private List<TextView> sortTextViewLists;
    private boolean listType = true;//是否是列表形式
    private List<GoodsListDetailBean> lists;
    private LinearAdapter linearAdapter;
    private GridAdapter gridAdapter;
    private String classId;
    private String className;

    //三级分类列表
    private List<FirstClassBean> categoryLists;
    private ClassPopWindow classPopWindow;

    //品牌列表
    private List<FilterBrandBean> brandLists;
    private FilterBrandAdapter filterBrandAdapter;
    //酒庄列表
    private List<FilterStoreBean> storeLists;
    private FilterStoreAdapter filterStoreAdapter;

    private String shopId;
    private boolean showDesc = false;
    private int pageNum = 1;
    private String orderBy = ORDER_ALL;
    private int selectIndex = 0;


    public static void startShopMainActivity(Activity activity, String shopId) {
        Intent intent = new Intent(activity, ShopMainActivity.class);
        intent.putExtra("shopId", shopId);
        activity.startActivity(intent);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    checkBack();
                    break;
                case R.id.img_title_right_one:
                    changeListType();
                    break;
                case R.id.imgShow:
                    changeShopDesc();
                    break;
                case R.id.tv_all:
                    changeSort(0);
                    break;
                case R.id.tv_new:
                    changeSort(1);
                    break;
                case R.id.sortPrice:
                    changeSort(2);
                    break;
                case R.id.tv_class:
                    showClassPopWindow();
                    break;
                case R.id.sortFilter:
                    showDrawerLayout();
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
        initDrawerLayout();
        initView();
        initData();
    }

    private void initDrawerLayout() {
        drawerLayout.closeDrawer(Gravity.END);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        ViewGroup.LayoutParams layoutParams = rightView.getLayoutParams();
        layoutParams.width = SizeUtils.getScreenWidth() - SizeUtils.dp2px(100);
        rightView.setLayoutParams(layoutParams);


        brandLists = new ArrayList<>();
        filterBrandAdapter = new FilterBrandAdapter(R.layout.goods_item_pop_class, brandLists);
        filterBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recyclerViewBrand.setHasFixedSize(true);
        recyclerViewBrand.setNestedScrollingEnabled(false);
        recyclerViewBrand.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewBrand.setAdapter(filterBrandAdapter);

        storeLists = new ArrayList<>();
        filterStoreAdapter = new FilterStoreAdapter(R.layout.goods_item_pop_class, storeLists);
        filterStoreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recyclerViewStore.setHasFixedSize(true);
        recyclerViewStore.setNestedScrollingEnabled(false);
        recyclerViewStore.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewStore.setAdapter(filterStoreAdapter);
    }

    private void initView() {
        imgShow.setOnClickListener(onClickListener);
        scrollableLayout.setCurrentScrollableContainer(recycleView);

        tvAll.setOnClickListener(onClickListener);
        tvNew.setOnClickListener(onClickListener);
        sortPrice.setOnClickListener(onClickListener);
        tvClass.setOnClickListener(onClickListener);
        sortFilter.setOnClickListener(onClickListener);
        tvAll.setSelected(true);
        sortTextViewLists = new ArrayList<>();
        sortTextViewLists.add(tvAll);
        sortTextViewLists.add(tvNew);
        sortTextViewLists.add(tvPrice);
        sortTextViewLists.add(tvClass);
        sortTextViewLists.add(tvFilter);

        lists = new ArrayList<>();
        linearAdapter = new LinearAdapter(R.layout.amain_item_goods_list_linear, lists);
        linearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailsActivity.startGoodsDetailsActivity(ShopMainActivity.this, lists.get(position).getItem_id());
            }
        });
        gridAdapter = new GridAdapter(R.layout.amain_item_goods_list_grid, lists);
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailsActivity.startGoodsDetailsActivity(ShopMainActivity.this, lists.get(position).getItem_id());
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(linearAdapter);

        refreshLayout.setEnableAutoLoadMore(false);//关闭自动加载更多
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getGoodsList(1, true);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getGoodsList(pageNum + 1, false);
            }
        });
    }

    private void initData() {
        disposable = new CompositeDisposable();
        shopId = getIntent().getStringExtra("shopId");
        getShopInfo();
        getGoodsList(1,true);
    }

    private void getShopInfo() {
        disposable.add(ApiUtils.getInstance().getShopInfo(shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<ShopInfoBean>>() {
                    @Override
                    public void accept(ResultBean<ShopInfoBean> shopInfoBeanResultBean) throws Exception {
                        getShopInfoSuccess(shopInfoBeanResultBean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void getShopInfoSuccess(ShopInfoBean data) {
        GlideUtils.loadImage(this,data.getShopInfo().getShop_logo(),imgHead);
        tvShopName.setText(data.getShopInfo().getShop_name());
        tvShopTips.setText(data.getShopInfo().getShop_descript());
        tvShopPhone.setText(data.getShopInfo().getMobile());
        tvDesc.setText(data.getShopInfo().getShop_descript());
    }

    private void getGoodsList(int pageNum, final boolean isRefresh) {
        disposable.add(ApiUtils.getInstance().getGoodsList(classId, pageNum, orderBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<GoodsListBean>>() {
                    @Override
                    public void accept(ResultBean<GoodsListBean> goodsListBeanResultBean) throws Exception {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        getListSuccess(goodsListBeanResultBean.getData(), isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                }));
    }

    private void getListSuccess(GoodsListBean goodsListBean, boolean isRefresh) {
        List<GoodsListDetailBean> newLists = goodsListBean.getList();
        if (newLists == null) newLists = new ArrayList<>();
        if (isRefresh) {
            pageNum = 1;
            lists = newLists;
        } else {
            pageNum++;
            lists.addAll(newLists);
        }
        if (goodsListBean.getPagers().getTotal() <= lists.size()) {
            refreshLayout.setFooterHeight(30);
            refreshLayout.finishLoadMoreWithNoMoreData();//显示没有更多数据
        } else {
            refreshLayout.setNoMoreData(false);
        }
        if (listType) {
            linearAdapter.setNewData(lists);
        } else {
            gridAdapter.setNewData(lists);
        }
    }

    private void changeListType() {
        if (listType) {
            //列表切换到网格
            listType = false;
            titleImgRightOne.setImageResource(R.mipmap.icon_list);
            recycleView.setLayoutManager(new GridLayoutManager(this, 2));
            recycleView.setAdapter(gridAdapter);
            gridAdapter.setNewData(lists);
        } else {
            //网格切换到列表
            listType = true;
            titleImgRightOne.setImageResource(R.mipmap.icon_grid);
            recycleView.setLayoutManager(new LinearLayoutManager(this));
            recycleView.setAdapter(linearAdapter);
            linearAdapter.setNewData(lists);
        }
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

    private void changeSort(int index) {
        if (selectIndex != index) {
            selectIndex = index;
            if (index == 0) {
                orderBy = ORDER_ALL;
            } else if (index == 1) {
                orderBy = ORDER_NEW;
            } else if (index == 2) {
                orderBy = ORDER_PRICE_ASC;
            }
            for (int i = 0; i < sortTextViewLists.size(); i++) {
                if (i == index) {
                    sortTextViewLists.get(i).setSelected(true);
                } else {
                    sortTextViewLists.get(i).setSelected(false);
                }
            }
        } else {
            if (index == 2) {
                if (orderBy.equals(ORDER_PRICE_ASC)) {
                    orderBy = ORDER_PRICE_DESC;
                } else {
                    orderBy = ORDER_PRICE_ASC;
                }
            }
        }
        getGoodsList(1, true);
    }

    private void showDrawerLayout() {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
            drawerLayout.closeDrawer(Gravity.END);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.END);
            drawerLayout.openDrawer(Gravity.END);
        }
    }

    private void showClassPopWindow() {
        if (categoryLists == null || categoryLists.size() == 0) {
            ToastUtils.showShort("暂无分类");
        } else {
            if (classPopWindow == null) {
                classPopWindow = new ClassPopWindow(this, categoryLists, classId);
                classPopWindow.setCallBack(new ClassPopWindow.CallBack() {
                    @Override
                    public void choiceClass(String name, String id) {
                        classId = id;
                        className = name;
                        titleName.setText(className);
                        pageNum = 1;
                        getGoodsList(pageNum, true);
                    }
                });
            }
            classPopWindow.showAsDropDown(tvClass, 0, 1);
        }
    }

    public class LinearAdapter extends BaseQuickAdapter<GoodsListDetailBean, BaseViewHolder> {
        public LinearAdapter(int layoutResId, List<GoodsListDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListDetailBean item) {
            ImageView imgPic = helper.getView(R.id.img_pic);
            GlideUtils.loadImage(ShopMainActivity.this, item.getImage_default_id(), imgPic);
            helper.setText(R.id.tv_name, item.getTitle());
        }
    }

    public class GridAdapter extends BaseQuickAdapter<GoodsListDetailBean, BaseViewHolder> {
        public GridAdapter(int layoutResId, List<GoodsListDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListDetailBean item) {
            ImageView imgPic = helper.getView(R.id.img_pic);
            ViewGroup.LayoutParams layoutParams = imgPic.getLayoutParams();
            int picWidth = (SizeUtils.getScreenWidth() - SizeUtils.dp2px(40)) / 2;
            layoutParams.width = picWidth;
            layoutParams.height = picWidth;
            imgPic.setLayoutParams(layoutParams);

            GlideUtils.loadImage(ShopMainActivity.this, item.getImage_default_id(), imgPic);
            helper.setText(R.id.tv_name, item.getTitle());
        }
    }


    private void checkBack() {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkBack();
            return true;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

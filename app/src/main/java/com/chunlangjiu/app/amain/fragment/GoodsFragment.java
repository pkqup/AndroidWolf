package com.chunlangjiu.app.amain.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.MainClassBean;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.chunlangjiu.app.amain.bean.ThirdClassBean;
import com.chunlangjiu.app.goods.activity.GoodsDetailsActivity;
import com.chunlangjiu.app.goods.activity.GoodsListActivity;
import com.chunlangjiu.app.goods.activity.SearchActivity;
import com.chunlangjiu.app.goods.bean.GoodsListBean;
import com.chunlangjiu.app.goods.bean.GoodsListDetailBean;
import com.chunlangjiu.app.net.ApiUtils;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.KeyBoardUtils;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/9/27
 * @Describe:
 */
public class GoodsFragment extends BaseFragment {

    private RelativeLayout rlBrand;
    private TextView tvBrand;
    private RelativeLayout rlArea;
    private TextView tvArea;
    private RelativeLayout rlIncense;
    private TextView tvIncense;
    private RelativeLayout rlAlc;
    private TextView tvAlc;
    private RelativeLayout rlPrice;
    private TextView tvPrice;

    //分类列表
    private RecyclerView recyclerViewClass;
    private ClassAdapter classAdapter;
    private List<ThirdClassBean> classLists;
    private String selectClassId = "";

    //商品列表
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<GoodsListDetailBean> lists;
    private LinearAdapter linearAdapter;
    private GridAdapter gridAdapter;
    private boolean listType = true;//是否是列表形式
    private int pageNum = 1;

    private String brandId = "";
    private String areaId = "";
    private String ordoId = "";
    private String alcoholId = "";
    private String minPrice = "";
    private String maxPrice = "";

    private boolean isActivity = false;
    private String searchKey = "";

    private CompositeDisposable disposable;

    public static GoodsFragment newInstance(String searchKey, boolean isActivity, String brandId, String brandName) {
        Bundle bundle = new Bundle();
        bundle.putString("searchKey", searchKey);
        bundle.putBoolean("isActivity", isActivity);
        bundle.putString("brandId", brandId);
        bundle.putString("brandName", brandName);
        GoodsFragment goodsFragment = new GoodsFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left_f:
                    getActivity().finish();
                    break;
                case R.id.img_title_right_one_f:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case R.id.img_title_right_two_f:
                    changeListType();
                    break;
                case R.id.rlBrand:
                    break;
                case R.id.rlArea:
                    break;
                case R.id.rlIncense:
                    break;
                case R.id.rlAlc:
                    break;
                case R.id.rlPrice:
                    break;
            }
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new
            TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (!TextUtils.isEmpty(titleSearchEdit.getText().toString().trim())) {
                            KeyBoardUtils.hideSoftInput(getActivity());
                            refreshLayout.autoRefresh();
                            searchKey = titleSearchEdit.getText().toString().trim();
                            getGoodsList(pageNum, true);
                        }
                    }
                    return true;
                }
            };


    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_goods, container, true);
    }

    private void initTitleView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isActivity = bundle.getBoolean("isActivity");
            searchKey = bundle.getString("searchKey");
        }
        titleView.setVisibility(View.VISIBLE);
        tvTitleF.setText("全部商品");
        imgTitleRightTwoF.setImageResource(R.mipmap.icon_grid);
        imgTitleLeftF.setOnClickListener(onClickListener);
        imgTitleRightOneF.setOnClickListener(onClickListener);
        imgTitleRightTwoF.setOnClickListener(onClickListener);
        titleSearchEdit.setOnEditorActionListener(onEditorActionListener);
        if (isActivity) {
            if (!TextUtils.isEmpty(searchKey)) {
                //是搜索
                tvTitleF.setVisibility(View.GONE);
                titleSearchView.setVisibility(View.VISIBLE);
                imgTitleRightOneF.setVisibility(View.GONE);
            } else {
                tvTitleF.setVisibility(View.VISIBLE);
                titleSearchView.setVisibility(View.GONE);
                imgTitleRightOneF.setVisibility(View.VISIBLE);
            }
            imgTitleLeftF.setVisibility(View.VISIBLE);
            imgTitleRightTwoF.setVisibility(View.VISIBLE);
        } else {
            tvTitleF.setVisibility(View.VISIBLE);
            titleSearchView.setVisibility(View.GONE);
            imgTitleLeftF.setVisibility(View.GONE);
            imgTitleRightOneF.setVisibility(View.VISIBLE);
            imgTitleRightTwoF.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        initTitleView();

        disposable = new CompositeDisposable();
        rlBrand = rootView.findViewById(R.id.rlBrand);
        tvBrand = rootView.findViewById(R.id.tvBrand);
        rlArea = rootView.findViewById(R.id.rlArea);
        tvArea = rootView.findViewById(R.id.tvArea);
        rlIncense = rootView.findViewById(R.id.rlIncense);
        tvIncense = rootView.findViewById(R.id.tvIncense);
        rlAlc = rootView.findViewById(R.id.rlAlc);
        tvAlc = rootView.findViewById(R.id.tvAlc);
        rlPrice = rootView.findViewById(R.id.rlPrice);
        tvPrice = rootView.findViewById(R.id.tvPrice);
        rlBrand.setOnClickListener(onClickListener);
        rlArea.setOnClickListener(onClickListener);
        rlIncense.setOnClickListener(onClickListener);
        rlAlc.setOnClickListener(onClickListener);
        rlPrice.setOnClickListener(onClickListener);

        recyclerViewClass = rootView.findViewById(R.id.recyclerViewClass);
        classLists = new ArrayList<>();
        ThirdClassBean thirdClassBean = new ThirdClassBean();
        thirdClassBean.setCat_id("");
        thirdClassBean.setCat_name("全部");
        classLists.add(thirdClassBean);
        classAdapter = new ClassAdapter(R.layout.amain_item_class, classLists);
        recyclerViewClass.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewClass.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectClassId = classLists.get(position).getCat_id();
                classAdapter.notifyDataSetChanged();
                getGoodsList(1, true);
            }
        });

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        lists = new ArrayList<>();
        linearAdapter = new LinearAdapter(R.layout.amain_item_goods_list_linear, lists);
        linearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailsActivity.startGoodsDetailsActivity(getActivity(), lists.get(position).getItem_id());
            }
        });
        gridAdapter = new GridAdapter(R.layout.amain_item_goods_list_grid, lists);
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetailsActivity.startGoodsDetailsActivity(getActivity(), lists.get(position).getItem_id());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(linearAdapter);

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


    @Override
    public void initData() {
        getClassData();
        getGoodsList(pageNum, true);
    }

    private void getClassData() {
        disposable.add(ApiUtils.getInstance().getMainClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<MainClassBean>>() {
                    @Override
                    public void accept(ResultBean<MainClassBean> mainClassBean) throws Exception {
                        classLists.clear();
                        ThirdClassBean thirdClassBean = new ThirdClassBean();
                        thirdClassBean.setCat_id("");
                        thirdClassBean.setCat_name("全部");
                        classLists.add(thirdClassBean);
                        List<FirstClassBean> categorys = mainClassBean.getData().getCategorys();
                        for (int i = 0; i < categorys.size(); i++) {
                            List<SecondClassBean> lv2 = categorys.get(i).getLv2();
                            for (int j = 0; j < lv2.size(); j++) {
                                List<ThirdClassBean> lv3 = lv2.get(j).getLv3();
                                classLists.addAll(lv3);
                            }
                        }
                        classAdapter.setNewData(classLists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private void getGoodsList(int pageNum, final boolean isRefresh) {
        disposable.add(ApiUtils.getInstance().getGoodsList(selectClassId, pageNum, searchKey, "", brandId, areaId, ordoId, alcoholId, minPrice, maxPrice)
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
        if (goodsListBean != null && goodsListBean.getList() != null && goodsListBean.getList().size() > 0) {
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
                if (lists.size() == 0) {
                    linearAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
                } else {
                    linearAdapter.setNewData(lists);
                }
            } else {
                if (lists.size() == 0) {
                    gridAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
                } else {
                    gridAdapter.setNewData(lists);
                }
            }
        } else {
            if (listType) {
                linearAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
            } else {
                gridAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
            }
        }
    }

    private void changeListType() {
        if (listType) {
            //列表切换到网格
            listType = false;
            imgTitleRightTwoF.setImageResource(R.mipmap.icon_list);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(gridAdapter);
            if (lists.size() == 0) {
                gridAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
            } else {
                gridAdapter.setNewData(lists);
            }
        } else {
            //网格切换到列表
            listType = true;
            imgTitleRightTwoF.setImageResource(R.mipmap.icon_grid);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(linearAdapter);
            if (lists.size() == 0) {
                linearAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_view, (ViewGroup) recyclerView.getParent(), false));
            } else {
                linearAdapter.setNewData(lists);
            }
        }
    }

    public class ClassAdapter extends BaseQuickAdapter<ThirdClassBean, BaseViewHolder> {
        public ClassAdapter(int layoutResId, List<ThirdClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ThirdClassBean item) {
            TextView tvClass = helper.getView(R.id.tvClass);
            tvClass.setText(item.getCat_name());
            tvClass.setSelected(item.getCat_id().equals(selectClassId));
        }
    }

    public class LinearAdapter extends BaseQuickAdapter<GoodsListDetailBean, BaseViewHolder> {
        public LinearAdapter(int layoutResId, List<GoodsListDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsListDetailBean item) {
            ImageView imgPic = helper.getView(R.id.img_pic);
            TextView tvStartPrice = helper.getView(R.id.tvStartPrice);

            GlideUtils.loadImage(getActivity(), item.getImage_default_id(), imgPic);
            helper.setText(R.id.tv_name, item.getTitle());
            helper.setText(R.id.tvStartPriceStr, "原价：");
            tvStartPrice.setText(item.getMkt_price());
            tvStartPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            helper.setText(R.id.tvSellPriceStr, "");
            helper.setText(R.id.tvSellPrice, item.getPrice());

            LinearLayout llTime = helper.getView(R.id.llTime);
            llTime.setVisibility(View.GONE);
            helper.setText(R.id.tvLabel, item.getLabel());
            TextView tvLabel = helper.getView(R.id.tvLabel);
            if (TextUtils.isEmpty(item.getLabel())) {
                tvLabel.setVisibility(View.GONE);
            } else {
                tvLabel.setVisibility(View.VISIBLE);
            }
            helper.setText(R.id.tv_attention, item.getView_count() + "人关注");
            helper.setText(R.id.tv_evaluate, item.getRate_count() + "条评价");
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
            GlideUtils.loadImage(getActivity(), item.getImage_default_id(), imgPic);
            helper.setText(R.id.tv_name, item.getTitle());

            TextView tvStartPrice = helper.getView(R.id.tvStartPrice);
            helper.setText(R.id.tvStartPriceStr, "原价：");
            tvStartPrice.setText(item.getMkt_price());
            tvStartPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            helper.setText(R.id.tvSellPriceStr, "");
            helper.setText(R.id.tvSellPrice, item.getPrice());

            helper.setText(R.id.tvLabel, item.getLabel());
            TextView tvLabel = helper.getView(R.id.tvLabel);
            if (TextUtils.isEmpty(item.getLabel())) {
                tvLabel.setVisibility(View.GONE);
            } else {
                tvLabel.setVisibility(View.VISIBLE);
            }
            helper.setText(R.id.tvAttention, item.getView_count() + "人关注");
            helper.setText(R.id.tvEvaluate, item.getRate_count() + "条评价");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

package com.chunlangjiu.app.store.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.abase.BaseFragmentAdapter;
import com.chunlangjiu.app.goods.activity.GoodsDetailsActivity;
import com.chunlangjiu.app.goods.fragment.GoodsCommentFragment;
import com.chunlangjiu.app.goods.fragment.GoodsDetailsFragment;
import com.chunlangjiu.app.goods.fragment.GoodsWebFragment;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.store.bean.StoreClassListBean;
import com.chunlangjiu.app.store.bean.StoreDetailBean;
import com.chunlangjiu.app.store.fragment.DetailDescFragment;
import com.chunlangjiu.app.store.fragment.GradeFragment;
import com.chunlangjiu.app.store.fragment.HeaderViewPagerFragment;
import com.chunlangjiu.app.store.fragment.PhotoFragment;
import com.chunlangjiu.app.store.fragment.SimpleDescFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.lzy.widget.HeaderViewPager;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/14.
 * @Describe: 名庄详情页面
 */
public class StoreDetailsActivity extends BaseActivity {

    @BindView(R.id.tvFirstName)
    TextView tvFirstName;
    @BindView(R.id.imgMainPic)
    ImageView imgMainPic;
    @BindView(R.id.tvSecondName)
    TextView tvSecondName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.imgCollect)
    ImageView imgCollect;
    @BindView(R.id.imgShare)
    ImageView imgShare;

    @BindView(R.id.headerViewPager)
    HeaderViewPager headerViewPager;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CompositeDisposable disposable;
    private String chateau_id;

    private final String[] mTitles = {"名庄简介", "详细介绍", "历年评分", "酒庄图片"};
    private FragmentAdapter fragmentAdapter;
    private List<HeaderViewPagerFragment> mFragments;


    public static void startStoreDetailActivity(Activity activity, String chateau_id) {
        Intent intent = new Intent(activity, StoreDetailsActivity.class);
        intent.putExtra("chateau_id", chateau_id);
        activity.startActivity(intent);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.img_title_right_one:
                    break;

            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("酒庄详情");
        titleImgLeft.setOnClickListener(onClickListener);
        titleImgRightOne.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_details);
        initView();
        initData();
    }

    private void initView() {
        disposable = new CompositeDisposable();
        chateau_id = getIntent().getStringExtra("chateau_id");
        mFragments = new ArrayList<>();
        mFragments.add(new SimpleDescFragment());
        mFragments.add(new DetailDescFragment());
        mFragments.add(new GradeFragment());
        mFragments.add(new PhotoFragment());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.setLists(mFragments);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setViewPager(viewPager, mTitles);
        viewPager.setCurrentItem(0);

        headerViewPager.setCurrentScrollableContainer(mFragments.get(0));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                headerViewPager.setCurrentScrollableContainer(mFragments.get(position));
            }
        });
    }

    private void initData() {
        disposable.add(ApiUtils.getInstance().getStoreDetail(chateau_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<StoreDetailBean>>() {
                    @Override
                    public void accept(ResultBean<StoreDetailBean> storeDetailBeanResultBean) throws Exception {
                        getDetailSuccess(storeDetailBeanResultBean.getData().getDetail());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.e(throwable.toString());
                    }
                }));
    }

    private void getDetailSuccess(List<StoreDetailBean.StoreBean> detail) {
        StoreDetailBean.StoreBean storeBean = detail.get(0);
        tvFirstName.setText(storeBean.getName());
        GlideUtils.loadImage(this,storeBean.getImg(),imgMainPic);
        tvSecondName.setText(storeBean.getName());
        tvDesc.setText(storeBean.getContent());
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        private List<HeaderViewPagerFragment> lists;

        public FragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void setLists(List<HeaderViewPagerFragment> lists) {
            this.lists = lists;
        }

        @Override
        public Fragment getItem(int position) {
            if (lists != null) {
                return lists.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return lists == null ? 0 : lists.size();
        }
    }
}

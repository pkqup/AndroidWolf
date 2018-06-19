package com.android.alcoholwolf.amain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.abase.BaseActivity;
import com.android.alcoholwolf.abase.BaseFragmentAdapter;
import com.android.alcoholwolf.amain.fragment.AuctionFragment;
import com.android.alcoholwolf.amain.fragment.ClassFragment;
import com.android.alcoholwolf.amain.fragment.HomeFragment;
import com.android.alcoholwolf.amain.fragment.UserFragment;
import com.android.alcoholwolf.goods.GoodsDetailsActivity;
import com.pkqup.commonlibrary.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    MyViewPager viewPager;

    @BindView(R.id.tab_one)
    LinearLayout tabOne;
    @BindView(R.id.tab_one_image)
    ImageView tabOneImage;
    @BindView(R.id.tab_one_text)
    TextView tabOneText;

    @BindView(R.id.tab_two)
    LinearLayout tabTwo;
    @BindView(R.id.tab_two_image)
    ImageView tabTwoImage;
    @BindView(R.id.tab_two_text)
    TextView tabTwoText;

    @BindView(R.id.tab_three)
    LinearLayout tabThree;
    @BindView(R.id.tab_three_image)
    ImageView tabThreeImage;
    @BindView(R.id.tab_three_text)
    TextView tabThreeText;

    @BindView(R.id.tab_four)
    RelativeLayout tabFour;
    @BindView(R.id.tab_four_image)
    ImageView tabFourImage;
    @BindView(R.id.tv_cart_num)
    TextView tvCartNum;

    @BindView(R.id.tab_five)
    LinearLayout tabFive;
    @BindView(R.id.tab_five_image)
    ImageView tabFiveImage;
    @BindView(R.id.tab_five_text)
    TextView tabFiveText;

    private BaseFragmentAdapter myFragmentAdapter;
    private List<Fragment> fragments;
    private List<ImageView> imageViews;
    private List<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amain_activity_main);
        initView();
        initData();
    }

    @Override
    public void setTitleView() {
        titleView.setVisibility(View.GONE);
    }

    private void initView() {
        tabOne.setOnClickListener(onClickListener);
        tabTwo.setOnClickListener(onClickListener);
        tabThree.setOnClickListener(onClickListener);
        tabFour.setOnClickListener(onClickListener);
        tabFive.setOnClickListener(onClickListener);

        imageViews = new ArrayList<>();
        imageViews.add(tabOneImage);
        imageViews.add(tabTwoImage);
        imageViews.add(tabThreeImage);
        imageViews.add(tabFiveImage);

        textViews = new ArrayList<>();
        textViews.add(tabOneText);
        textViews.add(tabTwoText);
        textViews.add(tabThreeText);
        textViews.add(tabFiveText);
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ClassFragment());
        fragments.add(new AuctionFragment());
        fragments.add(new UserFragment());
        myFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager());
        myFragmentAdapter.setLists(fragments);
        viewPager.setAdapter(myFragmentAdapter);
        setPageFragment(0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tab_one:
                    setPageFragment(0);
                    break;
                case R.id.tab_two:
                    setPageFragment(1);
                    break;
                case R.id.tab_three:
                    setPageFragment(2);
                    break;
                case R.id.tab_four:
                    startActivity(new Intent(MainActivity.this, GoodsDetailsActivity.class));
                    break;
                case R.id.tab_five:
                    setPageFragment(3);
                    break;
            }
        }
    };


    private void setPageFragment(int position) {
        viewPager.setCurrentItem(position,true);
        for (int i = 0; i < imageViews.size(); i++) {
            if (position == i) {
                imageViews.get(i).setSelected(true);
            } else {
                imageViews.get(i).setSelected(false);
            }
        }
        for (int i = 0; i < textViews.size(); i++) {
            if (position == i) {
                textViews.get(i).setSelected(true);
            } else {
                textViews.get(i).setSelected(false);
            }
        }
    }


}

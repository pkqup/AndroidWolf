package com.chunlangjiu.app.amain.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.abase.BaseFragmentAdapter;
import com.chunlangjiu.app.amain.fragment.AuctionFragment;
import com.chunlangjiu.app.amain.fragment.CartFragment;
import com.chunlangjiu.app.amain.fragment.ClassFragment;
import com.chunlangjiu.app.amain.fragment.HomeFragment;
import com.chunlangjiu.app.amain.fragment.UserFragment;
import com.chunlangjiu.app.util.LocationUtils;
import com.pkqup.commonlibrary.util.PermissionUtils;
import com.pkqup.commonlibrary.view.MyViewPager;
import com.socks.library.KLog;
import com.yanzhenjie.permission.PermissionListener;

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
    LinearLayout tabFour;
    @BindView(R.id.tab_four_image)
    ImageView tabFourImage;
    @BindView(R.id.tab_four_text)
    TextView tabFourText;
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
    private List<LinearLayout> linearLayouts;
    private List<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amain_activity_main);
        requestPermission();
        initView();
        initData();
    }

    private void requestPermission() {
        PermissionUtils.PermissionForStart(this, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantPermissions) {
                for (String grantPermission : grantPermissions) {
                    if (grantPermission.equals(Manifest.permission.ACCESS_FINE_LOCATION) ||
                            grantPermission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //开启定位
                        new LocationUtils().startLocation(new LocationUtils.LocationCallBack() {
                            @Override
                            public void locationSuccess(AMapLocation aMapLocation) {
                                KLog.e("location_success");
                            }

                            @Override
                            public void locationFail() {
                                KLog.e("location_fail");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                for (String grantPermission : deniedPermissions) {
                    KLog.e(grantPermission);
                }
            }
        });
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

        linearLayouts = new ArrayList<>();
        linearLayouts.add(tabOne);
        linearLayouts.add(tabTwo);
        linearLayouts.add(new LinearLayout(this));
        linearLayouts.add(tabFour);
        linearLayouts.add(tabFive);

        textViews = new ArrayList<>();
        textViews.add(tabOneText);
        textViews.add(tabTwoText);
        textViews.add(new TextView(this));
        textViews.add(tabFourText);
        textViews.add(tabFiveText);
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ClassFragment());
        fragments.add(new AuctionFragment());
        fragments.add(new CartFragment());
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
                    setPageFragment(3);
                    break;
                case R.id.tab_five:
                    setPageFragment(4);
                    break;
            }
        }
    };

    private void setPageFragment(int position) {
        viewPager.setCurrentItem(position, false);
        if (position != 2) {
            for (int i = 0; i < linearLayouts.size(); i++) {
                if (position == i) {
                    linearLayouts.get(i).setSelected(true);
                } else {
                    linearLayouts.get(i).setSelected(false);
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

}

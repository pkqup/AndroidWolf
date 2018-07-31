package com.chunlangjiu.app.goods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.pkqup.commonlibrary.util.SizeUtils;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/16
 * @Describe: 名酒估价页面
 */
public class ValuationActivity extends BaseActivity {

    @BindView(R.id.rlChoiceClass)
    RelativeLayout rlChoiceClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.etSeries)
    EditText etSeries;

    @BindView(R.id.llMainPic)
    LinearLayout llMainPic;
    @BindView(R.id.llDescPic)
    LinearLayout llDescPic;
    @BindView(R.id.llGoodsPic)
    LinearLayout llGoodsPic;

    @BindView(R.id.imgMainEx)
    ImageView imgMainEx;
    @BindView(R.id.imgMainPic)
    ImageView imgMainPic;
    @BindView(R.id.imgDescEx)
    ImageView imgDescEx;
    @BindView(R.id.imgDescPic)
    ImageView imgDescPic;
    @BindView(R.id.imgGoodsPic)
    ImageView imgGoodsPic;

    @BindView(R.id.tvCommit)
    TextView tvCommit;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvCommit:
                    startActivity(new Intent(ValuationActivity.this, ValuationSuccessActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("名酒估价");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_activity_valuation);
        initView();
    }

    private void initView() {
        int picSize = (SizeUtils.getScreenWidth() - 30) / 2;
        ViewGroup.LayoutParams layoutParams = llMainPic.getLayoutParams();
        layoutParams.height = picSize;
        llMainPic.setLayoutParams(layoutParams);
        llDescPic.setLayoutParams(layoutParams);
        llGoodsPic.setLayoutParams(layoutParams);

        tvCommit.setOnClickListener(onClickListener);


    }


}

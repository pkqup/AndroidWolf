package com.chunlangjiu.app.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/22.
 * @Describe:
 */
public class AddGoodsActivity extends BaseActivity {

    @BindView(R.id.rlChoiceClass)
    RelativeLayout rlChoiceClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etSecondName)
    EditText etSecondName;
    @BindView(R.id.etTag)
    EditText etTag;
    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.etCount)
    EditText etCount;

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

    @BindView(R.id.etGoodsDesc)
    EditText etGoodsDesc;

    @BindView(R.id.etType)
    EditText etType;
    @BindView(R.id.etSize)
    EditText etSize;
    @BindView(R.id.etChateau)
    EditText etChateau;
    @BindView(R.id.etSeries)
    EditText etSeries;
    @BindView(R.id.etPackage)
    EditText etPackage;
    @BindView(R.id.etAlco)
    EditText etAlco;
    @BindView(R.id.etArea)
    EditText etArea;


    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("商品添加");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_add_goods);
    }
}

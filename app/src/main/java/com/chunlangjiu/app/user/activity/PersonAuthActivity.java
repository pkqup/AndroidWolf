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
 * @CreatedbBy: liucun on 2018/7/15.
 * @Describe: 个人认证页面
 */
public class PersonAuthActivity extends BaseActivity {


    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.rlChoiceClass)
    RelativeLayout rlChoiceClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.etCardNum)
    EditText etCardNum;

    @BindView(R.id.imgFrontPic)
    ImageView imgFrontPic;
    @BindView(R.id.imgBehindPic)
    ImageView imgBehindPic;
    @BindView(R.id.imgCardPic)
    ImageView imgCardPic;

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
        titleName.setText("实名认证");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_person_auth);
    }

}

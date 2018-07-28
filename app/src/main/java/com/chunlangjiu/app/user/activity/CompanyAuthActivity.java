package com.chunlangjiu.app.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.pkqup.commonlibrary.util.TimeUtils;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/15.
 * @Describe: 企业认证页面
 */
public class CompanyAuthActivity extends BaseActivity {

    @BindView(R.id.etCompany)
    EditText etCompany;
    @BindView(R.id.etPersonName)
    EditText etPersonName;
    @BindView(R.id.etCardNum)
    EditText etCardNum;

    @BindView(R.id.rlCreateTime)
    RelativeLayout rlCreateTime;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.rlSellArea)
    RelativeLayout rlSellArea;
    @BindView(R.id.tvSellArea)
    TextView tvSellArea;

    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.imgSellCard)
    ImageView imgSellCard;
    @BindView(R.id.imgIDCard)
    ImageView imgIDCard;

    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private TimePickerDialog timePickerDialog;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.rlCreateTime:
                    showTimeDialog();
                    break;
            }
        }
    };


    @Override
    public void setTitleView() {
        titleName.setText("企业认证");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_company_auth);
        initView();
    }

    private void initView() {

        rlCreateTime.setOnClickListener(onClickListener);
    }

    private void showTimeDialog() {
        long hundredYears = 100L * 365 * 1000 * 60 * 60 * 24L;
        timePickerDialog = new TimePickerDialog.Builder().setCallBack(onDateSetListener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis() - hundredYears)
                .setMaxMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(
                        getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(
                        getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12).build();
        timePickerDialog.show(getSupportFragmentManager(), "year_month_day");
    }

    private OnDateSetListener onDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(TimePickerDialog timePickerView, long millSeconds) {
            tvCreateTime.setText(TimeUtils.millisToYearMD(String.valueOf(millSeconds)));
        }
    };

}

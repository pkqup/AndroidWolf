package com.chunlangjiu.app.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.amain.activity.MainActivity;
import com.pkqup.commonlibrary.view.choicearea.BottomDialog;
import com.pkqup.commonlibrary.view.choicearea.DataProvider;
import com.pkqup.commonlibrary.view.choicearea.ISelectAble;
import com.pkqup.commonlibrary.view.choicearea.SelectedListener;
import com.pkqup.commonlibrary.view.choicearea.Selector;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.rlChoiceArea)
    RelativeLayout rlChoiceArea;
    @BindView(R.id.tvArea)
    TextView tvArea;
    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.imgSetDefault)
    ImageView imgSetDefault;
    @BindView(R.id.tvSave)
    TextView tvSave;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.rlChoiceArea://选择省市区
                    showAreaDialog();
                    break;
                case R.id.imgSetDefault://设置为默认地址
                    break;
                case R.id.tvSave://提交
                    break;
            }
        }
    };
    private BottomDialog areaDialog;

    @Override
    public void setTitleView() {
        titleName.setText("添加地址");
        titleImgLeft.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_add_address);
        initView();
    }

    private void initView() {
        rlChoiceArea.setOnClickListener(onClickListener);
        imgSetDefault.setOnClickListener(onClickListener);
        tvSave.setOnClickListener(onClickListener);
    }

    private ArrayList<ISelectAble> getDatas() {
        int count = new Random().nextInt(99) + 1;
        ArrayList<ISelectAble> data = new ArrayList<>(count);
        for (int j = 0; j < count; j++) {
            final int finalJ = j;
            data.add(new ISelectAble() {
                @Override
                public String getName() {
                    return "随机" + finalJ;
                }

                @Override
                public int getId() {
                    return finalJ;
                }

                @Override
                public Object getArg() {
                    return this;
                }
            });
        }
        return data;
    }

    private void showAreaDialog() {
        Selector selector = new Selector(this, 3);

        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                receiver.send(getDatas());//根据层级获取数据
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                for (ISelectAble selectAble : selectAbles) {
                    result += selectAble.getName() + " ";
                }
                Toast.makeText(AddAddressActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });

        if (areaDialog == null) {
            areaDialog = new BottomDialog(this);
            areaDialog.init(this, selector);
        }
        areaDialog.show();
    }

}

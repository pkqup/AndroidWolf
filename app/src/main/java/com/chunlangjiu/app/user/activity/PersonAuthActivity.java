package com.chunlangjiu.app.user.activity;

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
import com.chunlangjiu.app.util.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pkqup.commonlibrary.dialog.ChoicePhotoDialog;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @CreatedbBy: liucun on 2018/7/15.
 * @Describe: 个人认证页面
 */
public class PersonAuthActivity extends BaseActivity {

    public static final int REQUEST_CODE_SELECT_FRONT = 100;
    public static final int REQUEST_CODE_SELECT_BEHIND = 101;
    public static final int REQUEST_CODE_SELECT_PERSON = 102;

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.rlChoiceClass)
    RelativeLayout rlChoiceClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.etCardNum)
    EditText etCardNum;

    @BindView(R.id.llPicOne)
    LinearLayout llPicOne;
    @BindView(R.id.llPicTwo)
    LinearLayout llPicTwo;

    @BindView(R.id.rlFront)
    RelativeLayout rlFront;
    @BindView(R.id.rlBehind)
    RelativeLayout rlBehind;
    @BindView(R.id.rlPerson)
    RelativeLayout rlPerson;
    @BindView(R.id.rlFour)
    RelativeLayout rlFour;
    @BindView(R.id.imgFrontPic)
    ImageView imgFrontPic;
    @BindView(R.id.imgBehindPic)
    ImageView imgBehindPic;
    @BindView(R.id.imgCardPic)
    ImageView imgCardPic;

    @BindView(R.id.tvCommit)
    TextView tvCommit;


    private ChoicePhotoDialog frontDialog;
    private ChoicePhotoDialog behindDialog;
    private ChoicePhotoDialog personDialog;

    private ArrayList<ImageItem> frontLists;
    private ArrayList<ImageItem> behindLists;
    private ArrayList<ImageItem> personLists;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.rlFront:
                    showFrontDialog();
                    break;
                case R.id.rlBehind:
                    showBehindDialog();
                    break;
                case R.id.rlPerson:
                    showPersonDialog();
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
        initImagePicker();
        initView();
    }

    private void initImagePicker() {
        int picWidth = (SizeUtils.getScreenWidth() - SizeUtils.dp2px(30))/2;
        int picHeight = (picWidth / 8) * 5;
        ViewGroup.LayoutParams layoutParamsOne = rlFront.getLayoutParams();
        layoutParamsOne.width = picWidth;
        layoutParamsOne.height = picHeight;
        rlFront.setLayoutParams(layoutParamsOne);

        ViewGroup.LayoutParams layoutParamsTwo = rlBehind.getLayoutParams();
        layoutParamsTwo.width = picWidth;
        layoutParamsTwo.height = picHeight;
        rlBehind.setLayoutParams(layoutParamsTwo);

        ViewGroup.LayoutParams layoutParamsThree = rlPerson.getLayoutParams();
        layoutParamsThree.width = picWidth;
        layoutParamsThree.height = picHeight;
        rlPerson.setLayoutParams(layoutParamsThree);

        ViewGroup.LayoutParams layoutParamsFour = rlFour.getLayoutParams();
        layoutParamsFour.width = picWidth;
        layoutParamsFour.height = picHeight;
        rlFour.setLayoutParams(layoutParamsFour);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);                        //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(625);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(625);                         //保存文件的高度。单位像素
    }

    private void initView() {
        rlFront.setOnClickListener(onClickListener);
        rlBehind.setOnClickListener(onClickListener);
        rlPerson.setOnClickListener(onClickListener);
    }

    private void showFrontDialog() {
        if (frontDialog == null) {
            frontDialog = new ChoicePhotoDialog(this);
            frontDialog.setCallBackListener(new ChoicePhotoDialog.OnCallBackListener() {
                @Override
                public void takePhoto() {
                    openCamera(REQUEST_CODE_SELECT_FRONT);
                }

                @Override
                public void toPhotoAlbum() {
                    openAlbum(REQUEST_CODE_SELECT_FRONT);
                }
            });
        }
        frontDialog.show();
    }


    private void showBehindDialog() {
        if (behindDialog == null) {
            behindDialog = new ChoicePhotoDialog(this);
            behindDialog.setCallBackListener(new ChoicePhotoDialog.OnCallBackListener() {
                @Override
                public void takePhoto() {
                    openCamera(REQUEST_CODE_SELECT_BEHIND);
                }

                @Override
                public void toPhotoAlbum() {
                    openAlbum(REQUEST_CODE_SELECT_BEHIND);
                }
            });
        }
        behindDialog.show();
    }

    private void showPersonDialog() {
        if (personDialog == null) {
            personDialog = new ChoicePhotoDialog(this);
            personDialog.setCallBackListener(new ChoicePhotoDialog.OnCallBackListener() {
                @Override
                public void takePhoto() {
                    openCamera(REQUEST_CODE_SELECT_PERSON);
                }

                @Override
                public void toPhotoAlbum() {
                    openAlbum(REQUEST_CODE_SELECT_PERSON);
                }
            });
        }
        personDialog.show();
    }

    private void openCamera(int requestCode) {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, requestCode);
    }

    private void openAlbum(int requestCode) {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent1 = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent1, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null) {
                if (requestCode == REQUEST_CODE_SELECT_FRONT) {
                    frontLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    GlideUtils.loadImage(PersonAuthActivity.this,frontLists.get(0).path,imgFrontPic);
                } else if (requestCode == REQUEST_CODE_SELECT_BEHIND) {
                    behindLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    GlideUtils.loadImage(PersonAuthActivity.this,behindLists.get(0).path,imgBehindPic);
                } else if (requestCode == REQUEST_CODE_SELECT_PERSON) {
                    personLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    GlideUtils.loadImage(PersonAuthActivity.this,personLists.get(0).path,imgCardPic);
                }

            }
        }
    }
}

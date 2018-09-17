package com.chunlangjiu.app.goods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.user.activity.AddGoodsActivity;
import com.chunlangjiu.app.util.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pkqup.commonlibrary.dialog.ChoicePhotoDialog;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.util.FileUtils;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @CreatedbBy: liucun on 2018/7/16
 * @Describe: 名酒估价页面
 */
public class ValuationActivity extends BaseActivity {

    public static final int REQUEST_CODE_SELECT_MAIN_PIC = 1001;
    public static final int REQUEST_CODE_SELECT_DETAIL_PIC = 1002;
    public static final int REQUEST_CODE_SELECT_GOODS_PIC = 1003;
    private int codeType;

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

    @BindView(R.id.rlMainPic)
    RelativeLayout rlMainPic;
    @BindView(R.id.rlDescPic)
    RelativeLayout rlDescPic;
    @BindView(R.id.rlGoodsPic)
    RelativeLayout rlGoodsPic;

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

    @BindView(R.id.imgDeleteMainPic)
    ImageView imgDeleteMainPic;
    @BindView(R.id.imgDeleteDescPic)
    ImageView imgDeleteDescPic;
    @BindView(R.id.imgDeleteGoodsPic)
    ImageView imgDeleteGoodsPic;

    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private CompositeDisposable disposable;
    private ChoicePhotoDialog photoDialog;
    private ArrayList<ImageItem> mainPicLists;
    private ArrayList<ImageItem> detailPicLists;
    private ArrayList<ImageItem> goodsPicLists;
    private String base64Main;
    private String base64Detail;
    private String base64Goods;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.rlMainPic:
                    showPhotoDialog(REQUEST_CODE_SELECT_MAIN_PIC);
                    break;
                case R.id.rlDescPic:
                    showPhotoDialog(REQUEST_CODE_SELECT_DETAIL_PIC);
                    break;
                case R.id.rlGoodsPic:
                    showPhotoDialog(REQUEST_CODE_SELECT_GOODS_PIC);
                    break;
                case R.id.imgDeleteMainPic:
                    deleteMainPic();
                    break;
                case R.id.imgDeleteDescPic:
                    deleteDescPic();
                    break;
                case R.id.imgDeleteGoodsPic:
                    deleteGoodsPic();
                    break;
                case R.id.tvCommit:
                    checkData();
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
        initImagePicker();
        initView();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);                        //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(500);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(500);                         //保存文件的高度。单位像素
    }


    private void initView() {
        disposable = new CompositeDisposable();
        int picSize = (SizeUtils.getScreenWidth() - 30) / 2;
        ViewGroup.LayoutParams layoutParams = llMainPic.getLayoutParams();
        layoutParams.height = picSize;
        llMainPic.setLayoutParams(layoutParams);
        llDescPic.setLayoutParams(layoutParams);
        llGoodsPic.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams imgDeleteGoodsPicLayoutParams = (RelativeLayout.LayoutParams) imgDeleteGoodsPic.getLayoutParams();
        imgDeleteGoodsPicLayoutParams.leftMargin = picSize - SizeUtils.dp2px(15);
        imgDeleteGoodsPic.setLayoutParams(imgDeleteGoodsPicLayoutParams);

        rlMainPic.setOnClickListener(onClickListener);
        rlDescPic.setOnClickListener(onClickListener);
        rlGoodsPic.setOnClickListener(onClickListener);

        imgDeleteMainPic.setOnClickListener(onClickListener);
        imgDeleteDescPic.setOnClickListener(onClickListener);
        imgDeleteGoodsPic.setOnClickListener(onClickListener);

        tvCommit.setOnClickListener(onClickListener);
    }

    private void showPhotoDialog(int requestCode) {
        codeType = requestCode;
        if (photoDialog == null) {
            photoDialog = new ChoicePhotoDialog(this);
            photoDialog.setCallBackListener(new ChoicePhotoDialog.OnCallBackListener() {
                @Override
                public void takePhoto() {
                    openCamera(codeType);
                }

                @Override
                public void toPhotoAlbum() {
                    openAlbum(codeType);
                }
            });
        }
        photoDialog.show();
    }

    private void openCamera(int requestCode) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, requestCode);
    }

    private void openAlbum(int requestCode) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                //添加图片返回
                if (data != null) {
                    if (requestCode == REQUEST_CODE_SELECT_MAIN_PIC) {
                        mainPicLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        ImageItem imageItem = mainPicLists.get(0);
                        int index = imageItem.path.lastIndexOf("/");
                        imageItem.name = imageItem.path.substring(index + 1, imageItem.path.length());
                        base64Main = FileUtils.imgToBase64(mainPicLists.get(0).path);
                        imgMainPic.setVisibility(View.VISIBLE);
                        imgDeleteMainPic.setVisibility(View.VISIBLE);
                        GlideUtils.loadImage(ValuationActivity.this, mainPicLists.get(0).path, imgMainPic);
                    } else if (requestCode == REQUEST_CODE_SELECT_DETAIL_PIC) {
                        detailPicLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        ImageItem imageItem = detailPicLists.get(0);
                        int index = imageItem.path.lastIndexOf("/");
                        imageItem.name = imageItem.path.substring(index + 1, imageItem.path.length());
                        base64Detail = FileUtils.imgToBase64(detailPicLists.get(0).path);
                        imgDescPic.setVisibility(View.VISIBLE);
                        imgDeleteDescPic.setVisibility(View.VISIBLE);
                        GlideUtils.loadImage(ValuationActivity.this, detailPicLists.get(0).path, imgDescPic);
                    } else if (requestCode == REQUEST_CODE_SELECT_GOODS_PIC) {
                        goodsPicLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        ImageItem imageItem = goodsPicLists.get(0);
                        int index = imageItem.path.lastIndexOf("/");
                        imageItem.name = imageItem.path.substring(index + 1, imageItem.path.length());
                        base64Goods = FileUtils.imgToBase64(goodsPicLists.get(0).path);
                        imgGoodsPic.setVisibility(View.VISIBLE);
                        imgDeleteGoodsPic.setVisibility(View.VISIBLE);
                        GlideUtils.loadImage(ValuationActivity.this, goodsPicLists.get(0).path, imgGoodsPic);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMainPic() {
        mainPicLists = null;
        base64Main = null;
        imgMainPic.setVisibility(View.GONE);
        imgDeleteMainPic.setVisibility(View.GONE);
    }

    private void deleteDescPic() {
        detailPicLists = null;
        base64Detail = null;
        imgDescPic.setVisibility(View.GONE);
        imgDeleteDescPic.setVisibility(View.GONE);
    }

    private void deleteGoodsPic() {
        goodsPicLists = null;
        base64Goods = null;
        imgGoodsPic.setVisibility(View.GONE);
        imgDeleteGoodsPic.setVisibility(View.GONE);
    }

    private void checkData() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            ToastUtils.showShort("请填写商品标题");
        } else if (TextUtils.isEmpty(etLocation.getText().toString().trim())) {
            ToastUtils.showShort("请填写品牌产地");
        } else if (TextUtils.isEmpty(etSeries.getText().toString().trim())) {
            ToastUtils.showShort("请填写所属系列");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

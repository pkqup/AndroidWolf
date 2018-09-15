package com.chunlangjiu.app.user.activity;

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
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.goods.dialog.ClassPopWindow;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.user.bean.AddGoodsValueBean;
import com.chunlangjiu.app.user.bean.BrandListBean;
import com.chunlangjiu.app.user.bean.ShopCatIdList;
import com.chunlangjiu.app.user.bean.ShopClassList;
import com.chunlangjiu.app.user.bean.SkuBean;
import com.chunlangjiu.app.user.bean.UploadImageBean;
import com.chunlangjiu.app.user.dialog.BrandPopWindow;
import com.chunlangjiu.app.user.dialog.ShopClassPopWindow;
import com.chunlangjiu.app.util.GlideImageLoader;
import com.google.gson.Gson;
import com.loc.a;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pkqup.commonlibrary.dialog.ChoicePhotoDialog;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.FileUtils;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.util.ToastUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/22.
 * @Describe:
 */
public class AddGoodsActivity extends BaseActivity {

    public static final int REQUEST_CODE_SELECT_MAIN_PIC = 1001;
    public static final int REQUEST_CODE_SELECT_DETAIL_PIC = 1002;
    public static final int REQUEST_CODE_SELECT_GOODS_PIC = 1003;
    private int codeType;

    @BindView(R.id.rlChoiceClass)
    RelativeLayout rlChoiceClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.rlChoicePlateClass)
    RelativeLayout rlChoicePlateClass;
    @BindView(R.id.tvPlateClass)
    TextView tvPlateClass;
    @BindView(R.id.rlChoiceBrand)
    RelativeLayout rlChoiceBrand;
    @BindView(R.id.tvBrand)
    TextView tvBrand;

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

    //店铺分类列表
    private List<ShopCatIdList.Children> shopClassLists;
    private ShopClassPopWindow shopPopWindow;
    private String shopClassId;

    //平台三级分类列表
    private List<FirstClassBean> categoryLists;
    private ClassPopWindow classPopWindow;
    private String classId;

    //品牌列表
    private BrandPopWindow brandPopWindow;
    private List<BrandListBean.Brand> brandLists;
    private String brandId;

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
                case R.id.rlChoiceClass:
                    showShopClassPopWindow();
                    break;
                case R.id.rlChoicePlateClass:
                    showClassPopWindow();
                    break;
                case R.id.rlChoiceBrand:
                    showBrandPopWindow();
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
                case R.id.tvCommit:
                    checkData();
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
        initView();
        initImagePicker();
        initData();
    }

    private void initView() {
        disposable = new CompositeDisposable();
        int picSize = (SizeUtils.getScreenWidth() - 30) / 2;
        ViewGroup.LayoutParams layoutParams = llMainPic.getLayoutParams();
        layoutParams.height = picSize;
        llMainPic.setLayoutParams(layoutParams);
        llDescPic.setLayoutParams(layoutParams);
        llGoodsPic.setLayoutParams(layoutParams);

        rlMainPic.setOnClickListener(onClickListener);
        rlDescPic.setOnClickListener(onClickListener);
        rlGoodsPic.setOnClickListener(onClickListener);
        tvCommit.setOnClickListener(onClickListener);

        rlChoiceClass.setOnClickListener(onClickListener);
        rlChoicePlateClass.setOnClickListener(onClickListener);
        rlChoiceBrand.setOnClickListener(onClickListener);
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


    private void initData() {
        //获取店铺分类
        disposable.add(ApiUtils.getInstance().getStoreClassList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<ShopCatIdList>>() {
                    @Override
                    public void accept(ResultBean<ShopCatIdList> shopCatIdListResultBean) throws Exception {
                        List<ShopCatIdList.Category> category = shopCatIdListResultBean.getData().getCategory();
                        shopClassLists = new ArrayList<>();
                        for (int i = 0; i < category.size(); i++) {
                            List<ShopCatIdList.Children> children = category.get(i).getChildren();
                            shopClassLists.addAll(children);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));


        //获取平台分类
        disposable.add(ApiUtils.getInstance().getShopClassList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<ShopClassList>>() {
                    @Override
                    public void accept(ResultBean<ShopClassList> mainClassBeanResultBean) throws Exception {
                        categoryLists = mainClassBeanResultBean.getData().getCategory();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }


    private void showShopClassPopWindow() {
        if (shopClassLists == null || shopClassLists.size() == 0) {
            ToastUtils.showShort("暂无分类");
        } else {
            if (shopPopWindow == null) {
                shopPopWindow = new ShopClassPopWindow(this, shopClassLists, shopClassId);
                shopPopWindow.setCallBack(new ShopClassPopWindow.CallBack() {
                    @Override
                    public void choiceBrand(String brandName, String brandId) {
                        shopClassId = brandId;
                        tvClass.setText(brandName);
                    }
                });
            }
            shopPopWindow.showAsDropDown(rlChoiceClass, 0, 1);
        }
    }


    private void showClassPopWindow() {
        if (categoryLists == null || categoryLists.size() == 0) {
            ToastUtils.showShort("暂无平台分类");
        } else {
            if (classPopWindow == null) {
                classPopWindow = new ClassPopWindow(this, categoryLists, classId);
                classPopWindow.setCallBack(new ClassPopWindow.CallBack() {
                    @Override
                    public void choiceClass(String name, String id) {
                        classId = id;
                        tvPlateClass.setText(name);
                        getBrandLists();
                    }
                });
            }
            classPopWindow.showAsDropDown(rlChoiceClass, 0, 1);
        }
    }

    private void getBrandLists() {
        disposable.add(ApiUtils.getInstance().getShopBrandList(classId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<BrandListBean>>() {
                    @Override
                    public void accept(ResultBean<BrandListBean> brandListBeanResultBean) throws Exception {
                        brandLists = brandListBeanResultBean.getData().getBrands();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    private void showBrandPopWindow() {
        if (TextUtils.isEmpty(classId)) {
            ToastUtils.showShort("请先选择分类");
        } else {
            if (brandLists == null || brandLists.size() == 0) {
                ToastUtils.showShort("暂无品牌");
            } else {
                if (brandPopWindow == null) {
                    brandPopWindow = new BrandPopWindow(this, brandLists, brandId);
                    brandPopWindow.setCallBack(new BrandPopWindow.CallBack() {
                        @Override
                        public void choiceBrand(String selectName, String selectId) {
                            brandId = selectId;
                            tvBrand.setText(selectName);
                        }
                    });
                }
                brandPopWindow.setBrandList(brandLists);
                brandPopWindow.showAsDropDown(rlChoiceBrand, 0, 1);
            }
        }
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


    private void checkData() {
        if (TextUtils.isEmpty(shopClassId)) {
            ToastUtils.showShort("请选择分类");
        } else if (TextUtils.isEmpty(classId)) {
            ToastUtils.showShort("请选择平台分类");
        } else if (TextUtils.isEmpty(brandId)) {
            ToastUtils.showShort("请选择品牌");
        } else if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            ToastUtils.showShort("请填写标题");
        } else if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
            ToastUtils.showShort("请填写价格");
        } else if (TextUtils.isEmpty(etCount.getText().toString().trim())) {
            ToastUtils.showShort("请填写库存");
        } else if (TextUtils.isEmpty(etSize.getText().toString().trim())) {
            ToastUtils.showShort("请填写容量");
        } else if (mainPicLists == null || mainPicLists.size() == 0) {
            ToastUtils.showShort("请添加商品主图");
        } else if (detailPicLists == null || detailPicLists.size() == 0) {
            ToastUtils.showShort("请添加商品细节图");
        } else if (goodsPicLists == null || goodsPicLists.size() == 0) {
            ToastUtils.showShort("请添加商品图片");
        } else {
            uploadImage();
        }
    }

    private void uploadImage() {
        if (base64Main == null || base64Detail == null || base64Goods == null) {
            ToastUtils.showShort("图片压缩失败，请重新选择图片");
        } else {
            showLoadingDialog();
            Observable<ResultBean<UploadImageBean>> main = ApiUtils.getInstance().shopUploadImage(base64Main, mainPicLists.get(0).name);
            Observable<ResultBean<UploadImageBean>> detail = ApiUtils.getInstance().shopUploadImage(base64Detail, detailPicLists.get(0).name);
            Observable<ResultBean<UploadImageBean>> goods = ApiUtils.getInstance().shopUploadImage(base64Goods, goodsPicLists.get(0).name);
            disposable.add(Observable.zip(main, detail, goods, new Function3<ResultBean<UploadImageBean>, ResultBean<UploadImageBean>,
                    ResultBean<UploadImageBean>, List<String>>() {
                @Override
                public List<String> apply(ResultBean<UploadImageBean> uploadImageBeanResultBean, ResultBean<UploadImageBean> uploadImageBeanResultBean2,
                                          ResultBean<UploadImageBean> uploadImageBeanResultBean3) throws Exception {
                    List<String> imageLists = new ArrayList<>();
                    imageLists.add(uploadImageBeanResultBean.getData().getUrl());
                    imageLists.add(uploadImageBeanResultBean2.getData().getUrl());
                    imageLists.add(uploadImageBeanResultBean3.getData().getUrl());
                    return imageLists;
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> strings) throws Exception {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < strings.size(); i++) {
                                if (i == strings.size() - 1) {
                                    stringBuffer.append(strings.get(i));
                                } else {
                                    stringBuffer.append(strings.get(i)).append(",");
                                }
                            }
                            commitGoods(stringBuffer.toString());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            hideLoadingDialog();
                            ToastUtils.showShort("上传图片失败");
                        }
                    }));
        }
    }

    private void commitGoods(String images) {
        SkuBean skuBean = new SkuBean();
        skuBean.setPrice(etPrice.getText().toString().trim());
        skuBean.setStore(etCount.getText().toString().trim());
        List<SkuBean> list = new ArrayList<>();
        list.add(skuBean);
        String skuArray = new Gson().toJson(list);

        List<AddGoodsValueBean> valueBeanList = new ArrayList<>();
        valueBeanList.add(new AddGoodsValueBean("类型",etType.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("容量",etSize.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("酒庄",etChateau.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("系列",etSeries.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("包装",etPackage.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("酒精度",etAlco.getText().toString().trim()));
        valueBeanList.add(new AddGoodsValueBean("产地",etArea.getText().toString().trim()));
        String parameter = new Gson().toJson(valueBeanList);

        disposable.add(ApiUtils.getInstance().addGoods(classId, brandId, shopClassId, etTitle.getText().toString().trim(),
                etSecondName.getText().toString().trim(), etSize.getText().toString().trim(), images,
                etPrice.getText().toString().trim(), "15", skuArray, etTag.getText().toString().trim(),
                etGoodsDesc.getText().toString().trim(), parameter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        hideLoadingDialog();
                        ToastUtils.showShort("添加商品成功");
                        KLog.e("add goods success");
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("添加商品失败");
                        hideLoadingDialog();
                        KLog.e("add goods fail");
                    }
                }));
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
                        GlideUtils.loadImage(AddGoodsActivity.this, mainPicLists.get(0).path, imgMainPic);
                    } else if (requestCode == REQUEST_CODE_SELECT_DETAIL_PIC) {
                        detailPicLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        ImageItem imageItem = detailPicLists.get(0);
                        int index = imageItem.path.lastIndexOf("/");
                        imageItem.name = imageItem.path.substring(index + 1, imageItem.path.length());
                        base64Detail = FileUtils.imgToBase64(detailPicLists.get(0).path);
                        GlideUtils.loadImage(AddGoodsActivity.this, detailPicLists.get(0).path, imgDescPic);
                    } else if (requestCode == REQUEST_CODE_SELECT_GOODS_PIC) {
                        goodsPicLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        ImageItem imageItem = goodsPicLists.get(0);
                        int index = imageItem.path.lastIndexOf("/");
                        imageItem.name = imageItem.path.substring(index + 1, imageItem.path.length());
                        base64Goods = FileUtils.imgToBase64(goodsPicLists.get(0).path);
                        GlideUtils.loadImage(AddGoodsActivity.this, goodsPicLists.get(0).path, imgGoodsPic);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

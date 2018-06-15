package com.android.alcoholwolf.abase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.android.alcoholwolf.R;
import com.android.alcoholwolf.net.User;
import com.android.alcoholwolf.net.WeiXinNewsBean;
import com.android.alcoholwolf.net.WeiXinService;
import com.pkqup.commonlibrary.abase.BaseActivity;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.net.HttpUtils;
import com.socks.library.KLog;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    private static String BASE_URL = "http://api.tianapi.com/";
    public static String WEIXIN_API_KEY = "9a69200c6e0880962216a8a917b59bda";
    private static final int PAGE_NUM = 10;
    private int page = 1;

    String[] images =
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",};

    String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg";
    String url2 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";

    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    public void setTitleView() {
        titleName.setText("主页");
        titleImgRight.setVisibility(View.GONE);

    }

    private void initView() {
        findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
            }
        });
        findViewById(R.id.tv_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, new MyFragment());
                ft.commit();
            }
        });

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);

        GlideUtils.loadImage(this, url1, imageView1);
        GlideUtils.loadImage(this, url1, imageView2);
    }

    private void initData() {
        showEmptyView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showContentView();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();

/*        HttpUtils.getInstance().getmRetrofit().create(WeiXinService.class).getWeixinHorList(WEIXIN_API_KEY, PAGE_NUM, page,new User("名字","10"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeiXinNewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeiXinNewsBean weiXinNewsBean) {
                        KLog.e(weiXinNewsBean.getCode() + " ---- " + weiXinNewsBean.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

        HttpUtils.getInstance().getmRetrofit().create(WeiXinService.class).getPost(new User("名字","10"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeiXinNewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeiXinNewsBean weiXinNewsBean) {
                        KLog.e(weiXinNewsBean.getCode() + " ---- " + weiXinNewsBean.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

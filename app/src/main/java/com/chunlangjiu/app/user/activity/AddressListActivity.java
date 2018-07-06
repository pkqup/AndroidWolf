package com.chunlangjiu.app.user.activity;

import android.os.Bundle;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class AddressListActivity extends BaseActivity {

    private CompositeDisposable disposable;

    @Override
    public void setTitleView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_address_list);
        initView();
        initData();
        getAddressList();
    }

    private void initView() {

    }

    private void initData() {
        disposable = new CompositeDisposable();
    }

    private void getAddressList() {
        disposable.add(ApiUtils.getInstance().getApiService().getAddressList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showErrorView();
                    }
                }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}

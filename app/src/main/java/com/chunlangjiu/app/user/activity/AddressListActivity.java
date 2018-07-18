package com.chunlangjiu.app.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.store.activity.StoreListActivity;
import com.chunlangjiu.app.store.activity.StoreSearchActivity;
import com.chunlangjiu.app.user.bean.AddressListBean;
import com.pkqup.commonlibrary.net.bean.ResultBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @CreatedbBy: liucun on 2018/7/6
 * @Describe:
 */
public class AddressListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvAddAddress)
    TextView tvAddAddress;

    private AddressAdapter addressAdapter;
    private List<AddressListBean> lists;
    private CompositeDisposable disposable;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    finish();
                    break;
                case R.id.tvAddAddress:
                    startActivity(new Intent(AddressListActivity.this, AddAddressActivity.class));
                    break;
            }
        }
    };

    @Override
    public void setTitleView() {
        titleName.setText("地址列表");
        titleImgLeft.setOnClickListener(onClickListener);
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
        tvAddAddress.setOnClickListener(onClickListener);
        lists = new ArrayList<>();
        addressAdapter = new AddressAdapter(R.layout.goods_item_comment_list, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addressAdapter);
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
//                        showErrorView();
                    }
                }));

         setEmptyListView(addressAdapter,"您没有添加过地址，请添加一个吧");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


    public class AddressAdapter extends BaseQuickAdapter<AddressListBean, BaseViewHolder> {
        public AddressAdapter(int layoutResId, List<AddressListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AddressListBean item) {

        }
    }
}

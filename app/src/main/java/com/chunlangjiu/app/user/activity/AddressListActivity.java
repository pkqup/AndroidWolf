package com.chunlangjiu.app.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseActivity;
import com.chunlangjiu.app.net.ApiUtils;
import com.chunlangjiu.app.user.bean.AddressListBean;
import com.chunlangjiu.app.user.bean.AddressListDetailBean;
import com.chunlangjiu.app.util.ConstantMsg;
import com.pkqup.commonlibrary.eventmsg.EventManager;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.util.ToastUtils;

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
    private List<AddressListDetailBean> lists;
    private CompositeDisposable disposable;
    private boolean isSelect = false;//是否是选择地址
    private String selectAddressId = "";//选择的地址id


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_title_left:
                    checkBack();
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
        EventManager.getInstance().registerListener(onNotifyListener);
        initView();
        initData();
        getAddressList();
    }

    private void initView() {
        tvAddAddress.setOnClickListener(onClickListener);
        lists = new ArrayList<>();
        addressAdapter = new AddressAdapter(R.layout.user_item_address_list, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.imgEdit:
                        toEditAddress(position);
                        break;
                    case R.id.imgDelete:
                        deleteAddress(position);
                        break;
                    case R.id.imgSetDefault:
                        setDefaultAddress(position);
                        break;
                }
            }
        });
    }

    private void initData() {
        disposable = new CompositeDisposable();
        isSelect = getIntent().getBooleanExtra("isSelect", false);
        if (isSelect) {
            titleName.setText("选择地址");
            selectAddressId = getIntent().getStringExtra("selectAddressId");
            addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AddressListDetailBean addressListDetailBean = lists.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("addressListDetailBean", addressListDetailBean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            titleName.setText("地址列表");
        }
    }

    private void getAddressList() {
        disposable.add(ApiUtils.getInstance().getAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<AddressListBean>>() {
                    @Override
                    public void accept(ResultBean<AddressListBean> addressListBeanResultBean) throws Exception {
                        lists = addressListBeanResultBean.getData().getList();
                        addressAdapter.setNewData(lists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }


    private EventManager.OnNotifyListener onNotifyListener = new EventManager.OnNotifyListener() {
        @Override
        public void onNotify(Object object, String eventTag) {
            addSuccess(eventTag);//竞拍专区
        }
    };

    //添加地址成功，刷新列表
    private void addSuccess(String eventTag) {
        if (eventTag.equals(ConstantMsg.ADD_ADDRESS_SUCCESS)) {
            getAddressList();
        }
    }


    //编辑地址
    private void toEditAddress(int position) {
        AddAddressActivity.startEditAddressActivity(this, lists.get(position));
    }

    //删除地址
    private void deleteAddress(int position) {
        disposable.add(ApiUtils.getInstance().deleteAddress(lists.get(position).getAddr_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        getAddressList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    //设置为默认地址
    private void setDefaultAddress(int position) {
        disposable.add(ApiUtils.getInstance().setDefault(lists.get(position).getAddr_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        getAddressList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }


    private void checkBack() {
        if (isSelect) {
            AddressListDetailBean addressListDetailBean = new AddressListDetailBean();
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).getAddr_id().equals(selectAddressId)) {
                    addressListDetailBean = lists.get(i);
                }
            }
            Intent intent = new Intent();
            intent.putExtra("addressListDetailBean", addressListDetailBean);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkBack();
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.getInstance().unRegisterListener(onNotifyListener);
        disposable.clear();
    }

    public class AddressAdapter extends BaseQuickAdapter<AddressListDetailBean, BaseViewHolder> {

        public AddressAdapter(int layoutResId, List<AddressListDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AddressListDetailBean item) {
            helper.setText(R.id.tvAddressName, item.getName());
            helper.setText(R.id.tvAddressPhone, item.getMobile());
            helper.setText(R.id.tvAddress, item.getAddrdetail());
            ImageView imgSetDefault = helper.getView(R.id.imgSetDefault);
            imgSetDefault.setSelected(item.getDef_addr().equals("1"));
            helper.addOnClickListener(R.id.imgEdit);
            helper.addOnClickListener(R.id.imgDelete);
            helper.addOnClickListener(R.id.imgSetDefault);

            ImageView imgChoice = helper.getView(R.id.imgChoice);
            if (item.getAddr_id().equals(selectAddressId)) {
                imgChoice.setVisibility(View.VISIBLE);
            } else {
                imgChoice.setVisibility(View.GONE);
            }
        }
    }
}

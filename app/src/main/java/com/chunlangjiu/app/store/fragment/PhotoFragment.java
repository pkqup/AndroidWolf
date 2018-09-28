package com.chunlangjiu.app.store.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.store.bean.StoreDetailBean;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe: 酒庄图片
 */
public class PhotoFragment extends HeaderViewPagerFragment {

    private View rootView;

    private RecyclerView recyclerView;
    private List<String> lists;
    private PhotoAdapter photoAdapter;

    private Context context;

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

    public static PhotoFragment newInstance(StoreDetailBean.StoreBean storeBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("storeBean", storeBean);
        PhotoFragment goodsFragment = new PhotoFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.store_fragment_photo, container, false);
            initView();
            initData();
        }
        return rootView;
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }


    private void initView() {
        context = getActivity();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        lists = new ArrayList<>();
        photoAdapter = new PhotoAdapter(R.layout.store_item_photo, lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(photoAdapter);
    }

    private void initData() {
     /*   for (int i = 0; i < images.length; i++) {
            lists.add(images[i]);
        }
        photoAdapter.setNewData(lists);*/

        Bundle bundle = getArguments();
        if (bundle != null) {
            StoreDetailBean.StoreBean storeBean = (StoreDetailBean.StoreBean) bundle.getSerializable("storeBean");
        }
    }

    public class PhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public PhotoAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgPic = helper.getView(R.id.imgPic);
            GlideUtils.loadImage(context,item,imgPic);
        }
    }

}
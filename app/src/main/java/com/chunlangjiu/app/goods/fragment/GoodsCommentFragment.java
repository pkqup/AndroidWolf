package com.chunlangjiu.app.goods.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.bean.GoodsListInfoBean;
import com.chunlangjiu.app.goods.bean.CommentListBean;

import java.util.ArrayList;
import java.util.List;


public class GoodsCommentFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<CommentListBean> lists;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.goods_fragment_comment, container, true);
    }

    @Override
    public void initView() {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        lists  = new ArrayList<>();
        commentAdapter = new CommentAdapter(R.layout.goods_item_comment_list,lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public void initData() {

    }

    public class CommentAdapter extends BaseQuickAdapter<CommentListBean, BaseViewHolder> {
        public CommentAdapter(int layoutResId, List<CommentListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentListBean item) {

        }
    }
}

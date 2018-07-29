package com.chunlangjiu.app.amain.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;


import com.chunlangjiu.app.R;
import com.chunlangjiu.app.abase.BaseFragment;
import com.chunlangjiu.app.amain.adapter.CartGoodsListAdapter;
import com.chunlangjiu.app.amain.bean.CartGoodsBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/6/24.
 * @Describe:
 */
public class CartFragment extends BaseFragment {

    private RefreshLayout refreshLayout;
    private SwipeMenuRecyclerView recycleView;

    private List<CartGoodsBean> lists;
    private CartGoodsListAdapter cartAdapter;

    @Override
    public void getContentView(LayoutInflater inflater, ViewGroup container) {
        inflater.inflate(R.layout.amain_fragment_cart, container, true);
    }

    @Override
    public void initView() {
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recycleView = rootView.findViewById(R.id.recycle_view);

        refreshLayout.setEnableRefresh(true);//设置可以下拉刷新
        refreshLayout.setEnableLoadMore(false);//设置不能加载更多

        lists = new ArrayList<>();
        cartAdapter = new CartGoodsListAdapter(getActivity(), lists);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setSwipeMenuCreator(swipeMenuCreator);//设置侧滑删除菜单
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        recycleView.setAdapter(cartAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLaut) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < 15; i++) {
            CartGoodsBean cartGoodsBean = new CartGoodsBean();
            if (i == 0 || i == 5 || i == 10) {
                cartGoodsBean.setItemType(CartGoodsBean.ITEM_STORE);
            } else {
                cartGoodsBean.setItemType(CartGoodsBean.ITEM_GOODS);
            }
            lists.add(cartGoodsBean);
            cartAdapter.setNewData(lists);
        }

    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (viewType == CartGoodsBean.ITEM_GOODS) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackground(R.color.bg_red)
                        .setText("删除")
                        .setTextColorResource(R.color.t_white)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getActivity(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
}

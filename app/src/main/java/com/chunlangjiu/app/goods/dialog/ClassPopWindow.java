package com.chunlangjiu.app.goods.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.ClassBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class ClassPopWindow extends PopupWindow {

    private Context context;
    private LayoutInflater inflater;
    private View mContentView;

    private GridAdapter gridAdapter;
    private List<ClassBean> lists;

    private String selectClassId;

    public ClassPopWindow(Context context, List<ClassBean> lists, String selectClassId) {
        super(context);
        this.context = context;
        this.lists = lists;
        this.selectClassId = selectClassId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.popwindow_class, null);
        setContentView(mContentView);

        //设置宽与高
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setBackgroundDrawable(new ColorDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_OUTSIDE;
            }
        });
        initView();
    }


    private void initView() {
        LinearLayout llPopWindow = mContentView.findViewById(R.id.ll_popWindow);
        llPopWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        RecyclerView recycleView = mContentView.findViewById(R.id.recycleView);
        gridAdapter = new GridAdapter(R.layout.goods_item_pop_class, lists);
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectClassId = lists.get(position).getClassId();
                gridAdapter.notifyDataSetChanged();
                if (callBack != null) {
                    callBack.choiceClass(lists.get(position).getClassName(), lists.get(position).getClassId());
                }
                dismiss();
            }
        });
        recycleView.setLayoutManager(new GridLayoutManager(context, 3));
        recycleView.setAdapter(gridAdapter);
    }

    public class GridAdapter extends BaseQuickAdapter<ClassBean, BaseViewHolder> {
        public GridAdapter(int layoutResId, List<ClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ClassBean item) {
            TextView tvName = helper.getView(R.id.tvName);
            tvName.setText(item.getClassName());
            if (selectClassId.equals(item.getClassId())) {
                tvName.setSelected(true);
            } else {
                tvName.setSelected(false);
            }
        }
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void choiceClass(String className, String classId);
    }
}

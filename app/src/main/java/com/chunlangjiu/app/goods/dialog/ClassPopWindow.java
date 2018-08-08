package com.chunlangjiu.app.goods.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.adapter.SecondClassAdapter;
import com.chunlangjiu.app.amain.bean.FirstClassBean;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.chunlangjiu.app.goods.activity.GoodsListActivity;
import com.chunlangjiu.app.goods.adapter.ClassPopSecondAdapter;
import com.chunlangjiu.app.goods.bean.ClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/30
 * @Describe:
 */
public class ClassPopWindow extends PopupWindow {

    private Context context;
    private LayoutInflater inflater;
    private View mContentView;

    private RecyclerView firstRecycleView;
    private ExpandableListView exListView;

    private List<FirstClassBean> firstLists;
    private FirstClassAdapter firstAdapter;

    private List<SecondClassBean> secondList;
    private ClassPopSecondAdapter secondClassAdapter;

    private String selectClassId;

    public ClassPopWindow(Context context, List<FirstClassBean> firstLists, String selectClassId) {
        super(context);
        this.context = context;
        this.firstLists = firstLists;
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

        firstRecycleView = mContentView.findViewById(R.id.first_class_recycle_view);
        exListView = mContentView.findViewById(R.id.exListView);

        firstLists.get(0).setSelect(true);
        firstAdapter = new FirstClassAdapter(R.layout.amain_item_first_class, firstLists);
        firstRecycleView.setLayoutManager(new LinearLayoutManager(context));
        firstRecycleView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                changeFirstClass(position);
            }
        });

        secondList = firstLists.get(0).getLv2();
        secondClassAdapter = new ClassPopSecondAdapter(context, secondList, selectClassId);
        exListView.setAdapter(secondClassAdapter);
        exListView.setGroupIndicator(null);
        exListView.setDivider(null);
        exListView.setCacheColorHint(0);
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //设置点击分类头部不收缩
                return true;
            }
        });
        secondClassAdapter.setCallBackListener(new ClassPopSecondAdapter.CallBack() {
            @Override
            public void onSubClick(int groupPosition, int subPosition) {
                if (callBack != null) {
                    String cat_id = secondList.get(groupPosition).getLv3().get(subPosition).getCat_id();
                    String cat_name = secondList.get(groupPosition).getLv3().get(subPosition).getCat_name();
                    selectClassId = cat_id;
                    secondClassAdapter.setSelectClassId(cat_id);
                    callBack.choiceClass(cat_name, cat_id);
                    dismiss();
                }
            }
        });

        int groupCount = exListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            exListView.expandGroup(i);
        }
    }

    private void changeFirstClass(int position) {
        if (!firstLists.get(position).isSelect()) {
            for (int i = 0; i < firstLists.size(); i++) {
                if (i == position) {
                    firstLists.get(i).setSelect(true);
                } else {
                    firstLists.get(i).setSelect(false);
                }
            }
            firstAdapter.notifyDataSetChanged();


            secondList = firstLists.get(position).getLv2();
            secondClassAdapter.setLists(new ArrayList<SecondClassBean>());
            secondClassAdapter.setLists(secondList);
            int groupCount = exListView.getCount();
            for (int i = 0; i < groupCount; i++) {
                exListView.expandGroup(i);
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

    public class FirstClassAdapter extends BaseQuickAdapter<FirstClassBean, BaseViewHolder> {
        public FirstClassAdapter(int layoutResId, List<FirstClassBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FirstClassBean item) {
            TextView tvClass = helper.getView(R.id.tv_class);
            tvClass.setText(item.getCat_name());
            tvClass.setSelected(item.isSelect());
        }
    }
}

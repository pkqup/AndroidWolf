package com.chunlangjiu.app.amain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.amain.bean.SecondClassBean;
import com.chunlangjiu.app.amain.bean.ThirdClassBean;
import com.pkqup.commonlibrary.glide.GlideUtils;
import com.pkqup.commonlibrary.util.SizeUtils;
import com.pkqup.commonlibrary.view.MyGridView;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/6
 * @Describe:
 */
public class SecondClassAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<SecondClassBean> lists;
    private CallBack callBack;

    public SecondClassAdapter(Context context, List<SecondClassBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setLists(List<SecondClassBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public interface CallBack {
        void onSubClick(int groupPosition, int subPosition);
    }

    public void setCallBackListener(CallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public int getGroupCount() {
        return lists.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lists.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.amain_item_second_class, null);
        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(lists.get(groupPosition).getCat_name());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lists.get(groupPosition).getLv3();
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.amain_item_third_class, null);
        MyGridView myGridView = convertView.findViewById(R.id.gridView);
        SubGridAdapter subGridAdapter = new SubGridAdapter(lists.get(groupPosition).getLv3(), groupPosition);
        myGridView.setAdapter(subGridAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != callBack) {
                    callBack.onSubClick(groupPosition, position);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public class SubGridAdapter extends BaseAdapter {
        private List<ThirdClassBean> lists;
        private int groupPosition;

        public SubGridAdapter(List<ThirdClassBean> lists, int groupPosition) {
            this.lists = lists;
            this.groupPosition = groupPosition;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.amain_item_sub_third_class, null);
            ImageView imgPic = convertView.findViewById(R.id.img_pic);
            TextView tvName = convertView.findViewById(R.id.tv_name);
            ViewGroup.LayoutParams layoutParams = imgPic.getLayoutParams();
            int screenWidth = SizeUtils.getScreenWidth();
            int picWidth = (screenWidth - SizeUtils.dp2px(130)) / 3;
            layoutParams.height = picWidth;
            layoutParams.width = picWidth;
            imgPic.setLayoutParams(layoutParams);
            tvName.setText(lists.get(position).getCat_name());
            GlideUtils.loadImage(context, lists.get(position).getCat_logo(), imgPic);
            return convertView;
        }
    }
}
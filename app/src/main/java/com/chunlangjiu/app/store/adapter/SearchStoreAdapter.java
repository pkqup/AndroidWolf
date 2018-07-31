package com.chunlangjiu.app.store.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.store.bean.SearchBean;

import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/7/31
 * @Describe:
 */
public class SearchStoreAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<SearchBean> lists;
    private CallBack callBack;

    public SearchStoreAdapter(Context context, List<SearchBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setLists(List<SearchBean> lists) {
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
        convertView = View.inflate(context, R.layout.store_item_search_group, null);
        TextView tvName = convertView.findViewById(R.id.tvName);
        ImageView imgArrow = convertView.findViewById(R.id.imgArrow);
        tvName.setText(lists.get(groupPosition).getName());
        imgArrow.setImageResource(isExpanded ? R.mipmap.gray_up : R.mipmap.gray_down);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return lists.get(groupPosition).getList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lists.get(groupPosition).getList().get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.store_item_search_child, null);
        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(lists.get(groupPosition).getList().get(childPosition).getName());
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
}

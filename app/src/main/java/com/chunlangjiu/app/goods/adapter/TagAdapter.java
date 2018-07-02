package com.chunlangjiu.app.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chunlangjiu.app.R;
import com.chunlangjiu.app.goods.bean.TagBean;
import com.pkqup.commonlibrary.view.tagview.OnInitSelectedPosition;

import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private Context mContext;
    private List<T> mDataList;

    public TagAdapter(Context context, List<T> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }

    public void setLists(List<T> mDataList){
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.common_item_tag, null);

        TextView textView = view.findViewById(R.id.tv_tag);
        T t = mDataList.get(position);

        if (t instanceof TagBean) {
            textView.setText(((TagBean) t).getName());
        }
        return view;
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return false;
    }
}

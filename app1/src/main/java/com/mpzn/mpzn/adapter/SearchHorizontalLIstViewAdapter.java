package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpzn.mpzn.R;

import java.util.List;

/**
 * Created by Icy on 2016/7/27.
 */
public class SearchHorizontalLIstViewAdapter extends BaseAdapter {
    private final List<String> dataList;
    private Context mContext;

    public SearchHorizontalLIstViewAdapter(Context mContext,List<String> dataList) {
       this.mContext=mContext;
        this.dataList=dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext,R.layout.hot_search_item, null);
        TextView textView = (TextView) convertView
                .findViewById(R.id.item_textview);
        String str = dataList.get(position);
        textView.setText(str);
        return convertView;
    }
}

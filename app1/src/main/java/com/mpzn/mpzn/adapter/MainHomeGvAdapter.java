package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;

/**
 * Created by Icy on 2016/7/13.
 */
public class MainHomeGvAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public MainHomeGvAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }
    // 图片封装为一个数组
    private int[] icon = { R.drawable.icon_1, R.drawable.icon_2,
            R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5,
            R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8,
            };
    private String[] iconName = { "住宅", "商铺", "写字楼", "地图找房", "新房团购", "楼盘报备", "房价走势",
            "购房工具"};


    @Override
    public int getCount() {
        return icon.length;
    }

    @Override
    public String getItem(int position) {
        return iconName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_gv_main_home, parent,
                    false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            viewHolder.mImageView = (ImageView) convertView
                    .findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(iconName[position]);
        viewHolder.mImageView.setImageResource(icon[position]);
        return convertView;
    }

    private final class ViewHolder
    {
        ImageView mImageView;
        TextView mTextView;
    }
}

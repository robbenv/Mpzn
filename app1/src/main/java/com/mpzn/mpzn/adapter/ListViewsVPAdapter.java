package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Icy on 2016/9/27.
 */

public class ListViewsVPAdapter extends PagerAdapter {

    private  Context mContext;
    private  List<ListView> lv_list;
    private  List<String> tab_title;

    public ListViewsVPAdapter(Context mContext, List<ListView> lv_list, List<String> tab_title) {
        this.mContext=mContext;
        this.lv_list=lv_list;
        this.tab_title=tab_title;
    }

    @Override
    public int getCount() {
        return tab_title.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ListView listView = lv_list.get(position);
        container.addView(listView);
        return listView;
    }
}

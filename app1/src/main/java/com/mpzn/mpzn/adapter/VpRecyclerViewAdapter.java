package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mpzn.mpzn.R;

import java.util.List;

/**
 * Created by Icy on 2016/9/26.
 */
public class VpRecyclerViewAdapter extends PagerAdapter{

    private  Context mContext;
    private  List<ViewGroup> views;


    public VpRecyclerViewAdapter(Context mContext, List views) {
        this.mContext=mContext;
        this.views=views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
//        if(recyclerViews.get(position).getChildCount()==0){
//            Log.e("TAG", "返回无数据信息");
//            View noData = LayoutInflater.from(mContext).inflate(R.layout.layout_nonet, null);
//            noData.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return noData;
//        }else {
//            Log.e("TAG", "返回有数据信息");
            return views.get(position);
//        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}

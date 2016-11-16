package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/9/18.
 */
public class BuildingDetailTopVpAdapter extends PagerAdapter{
    private Context mContext;
    List<String> toppic=new ArrayList<>();
    public BuildingDetailTopVpAdapter(Context mContext, List<String> toppic) {
        this.mContext=mContext;
        toppic.addAll(toppic);
    }

    public void updata(List<String> newtoppic){
        toppic.clear();
        if(newtoppic!=null) {
            toppic.addAll(newtoppic);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return toppic.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((DetailNewhouseActivity)mContext).mImageManager.loadUrlImage(toppic.get(position),imageView);
        container.addView(imageView);
        return imageView;
    }
}

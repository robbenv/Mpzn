package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Icy on 2016/8/12.
 */
public class VpPhotoViewAdapter extends PagerAdapter {
    private  List<String> pingmiantu;
    private  Context mContext;
    public OnPageSelectedListener onPageSelectedListener;



    public VpPhotoViewAdapter(Context mContext, List<String> pingmiantu) {
        this.mContext = mContext;
        this.pingmiantu=pingmiantu;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return  pingmiantu.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView view = new PhotoView(mContext);
        view.enable();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext).load(pingmiantu.get(position)).into(view);
        container.addView(view);
        onPageSelectedListener.setOnClickListenerForPv(view);
        return view;
    }


    private View mCurrentView;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View)object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener ){
        this.onPageSelectedListener=onPageSelectedListener;
    }

    public interface OnPageSelectedListener{
        void setOnClickListenerForPv(PhotoView pv);

    }
}


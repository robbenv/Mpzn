package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;

import java.util.ArrayList;

/**
 * Created by Icy on 2016/7/12.
 */
public class SplashViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<ImageView> imageViews;


    public SplashViewPagerAdapter(Context context){
        mContext=context;
        getImageRes();

    }
    //获取引导页面图片资源
    private void getImageRes() {
        int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,R.drawable.guide_4};
        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(ids[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
    }


    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        container.addView(imageView);
        return imageView;
    }

}

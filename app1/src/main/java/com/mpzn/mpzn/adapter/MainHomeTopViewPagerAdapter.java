package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.entity.Home;
import com.mpzn.mpzn.entity.HomeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/7/21.
 */
public class MainHomeTopViewPagerAdapter extends PagerAdapter {

    private List<HomeEntity.DataBean.TopnewslistBean> topnewslist;
    private List<ImageView> imgs=new ArrayList<>();
    private Context mContext;



    public MainHomeTopViewPagerAdapter(Context mContext, List<HomeEntity.DataBean.TopnewslistBean> topnewslist) {
        this.topnewslist=topnewslist;
        this.mContext=mContext;
        initPage();
    }



    @Override
    public void notifyDataSetChanged() {
        initPage();
        super.notifyDataSetChanged();
    }

    private void initPage() {

        imgs.clear();

        for(int i=0;i<topnewslist.size();i++){
            ImageView img=new ImageView(mContext);
            imgs.add(img);
            ((MainActivity)mContext).mImageManager.loadUrlImage(topnewslist.get(i).getThumb(),img);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("Aid",topnewslist.get(finalI).getAid());
                    intent.setClass(mContext,DetailNewhouseActivity.class);
                    mContext.startActivity(intent);
                }
            });

        }
    }



    @Override
    public int getCount() {
        return topnewslist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgs.get(position));
        return imgs.get(position);
    }
}

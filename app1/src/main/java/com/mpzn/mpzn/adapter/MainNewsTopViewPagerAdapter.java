package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewsActivity;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.entity.NewsTopList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/7/26.
 */
public class MainNewsTopViewPagerAdapter extends PagerAdapter{
    private List<NewsTopList.DataBean> newsTopListData;
    Context mContext;



    public MainNewsTopViewPagerAdapter(Context mContext, List<NewsTopList.DataBean> newsTopListData) {
        this.mContext=mContext;
        this.newsTopListData=newsTopListData;
    }




    @Override
    public int getCount() {
        return newsTopListData.size();
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
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_vp_news_top,null);
        final NewsTopList.DataBean dataBean = newsTopListData.get(position);
        ImageView img=(ImageView)view.findViewById(R.id.img);
        TextView tv_des=(TextView)view.findViewById(R.id.tv_des);
//        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        Glide.with(mContext).load(dataBean.getImg()).into(img);
        tv_des.setText(dataBean.getSubject());
//        tv_time.setText("2016-06-28 10:20");

        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailNewsActivity.class);
                int Aid = dataBean.getAid();
                String NewsTitle=dataBean.getSubject();
                String NewsAbstract=dataBean.getAbstractX();
                intent.putExtra("NewsAid", Aid);
                intent.putExtra("NewsTitle",NewsTitle);
                intent.putExtra("NewsAbstract",NewsAbstract);
                intent.putExtra("ImgUrl", dataBean.getImg());
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}

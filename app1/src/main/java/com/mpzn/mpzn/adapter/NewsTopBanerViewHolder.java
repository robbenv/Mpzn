package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.NewsTopList;



/**
 * Created by Icy on 2016/11/10.
 */

public class NewsTopBanerViewHolder implements Holder<NewsTopList.DataBean> {
    private TextView tv_des;
    private ImageView img;
    private Context mContext;

    @Override
    public View createView(Context context) {
        this.mContext=context;
        View view= LayoutInflater.from(context).inflate(R.layout.item_vp_news_top,null);
        img=(ImageView)view.findViewById(R.id.img);
        tv_des=(TextView)view.findViewById(R.id.tv_des);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsTopList.DataBean data) {
        tv_des.setText(data.getSubject());
        ((BaseActivity)mContext).mImageManager.loadUrlImage(data.getImg(),img);

    }
}

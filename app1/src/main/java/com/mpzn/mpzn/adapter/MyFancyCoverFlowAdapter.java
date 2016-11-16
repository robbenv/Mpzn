package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.dalong.francyconverflow.FancyCoverFlow;
import com.dalong.francyconverflow.FancyCoverFlowAdapter;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.views.fancyCoverFlow.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/8/12.
 */
public class MyFancyCoverFlowAdapter extends FancyCoverFlowAdapter {
    private List<String> pingmiantu;
    private Context mContext;
    private int width;


    public List<Item> list=new ArrayList<>();




    public MyFancyCoverFlowAdapter(Context mContext, List<String> pingmiantu) {
        this. mContext=mContext;
        this.pingmiantu=pingmiantu;
        this.width= MyApplication.mScreenWidth;

    }


    @Override
    public View getCoverFlowItem(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_gallery, null);

            convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(width / 3, FancyCoverFlow.LayoutParams.WRAP_CONTENT));
            holder = new ViewHolder();
            holder.img = (PhotoView) convertView.findViewById(R.id.iv_gallery);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(pingmiantu.get(position)).placeholder(R.drawable.default_img).sizeMultiplier(0.1f).into(holder.img);



        return convertView;
    }


    public void setSelected(int position) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.get(i).setSelected(true);
                } else {
                    list.get(i).setSelected(false);
                }
            }
        }
        notifyDataSetChanged();


    }


    @Override
    public int getCount() {
        return pingmiantu.size();
    }


    @Override
    public String getItem(int i) {
        return pingmiantu.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolder {
        PhotoView img;

    }
}

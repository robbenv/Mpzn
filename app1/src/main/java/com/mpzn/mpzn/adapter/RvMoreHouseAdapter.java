package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.entity.BuildingDetailEntity;

import java.util.List;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/8/16.
 */
public class RvMoreHouseAdapter extends RecyclerView.Adapter<RvMoreHouseAdapter.MyViewHolder>{
    private  List<BuildingDetailEntity.DataBean.MorebuildingBean> morebuilding;
    private Context mContext;
    private  LayoutInflater mInflater;



    public RvMoreHouseAdapter(Context mContext, List<BuildingDetailEntity.DataBean.MorebuildingBean> morebuilding) {
        this.mContext=mContext;
        mInflater = LayoutInflater.from(mContext);
        this.morebuilding=morebuilding;

    }


    @Override
    public RvMoreHouseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rv_more_house, parent, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                MyApplication.mScreenWidth / 2 - dip2px(25), ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.mImageView= (ImageView) view.findViewById(R.id.img);
        myViewHolder.mName= (TextView) view.findViewById(R.id.tv_name);
        myViewHolder.mPrice= (TextView) view.findViewById(R.id.price);
        myViewHolder.mArea= (TextView) view.findViewById(R.id.tv_detail_house_area);
        myViewHolder.mbiesu= (TextView) view.findViewById(R.id.tv_detail_house_type);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RvMoreHouseAdapter.MyViewHolder holder, int position) {
        ((DetailNewhouseActivity)mContext).mImageManager.loadUrlImage(morebuilding.get(position).getThumb(),holder.mImageView);
        holder.mName.setText(morebuilding.get(position).getSubject());
        holder.mPrice.setText(morebuilding.get(position).getDj());
//        holder.mArea.setText(morebuilding.get(position).get);


    }

    @Override
    public int getItemCount() {
        return morebuilding.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mPrice;
        ImageView mImageView;
        TextView mArea;
        TextView mbiesu;


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.BBStaticsForJJComActivity;
import com.mpzn.mpzn.activity.BBStatisticsActivity;
import com.mpzn.mpzn.entity.BbRankListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/11/1.
 */
public class RvBbRankAdapter extends RecyclerView.Adapter<RvBbRankAdapter.MyViewHolder> {

    private List<BbRankListEntity.DataBean.BrokersbaobeiBean> bbRankList = new ArrayList<>();
    private Context mContext;

    public RvBbRankAdapter(Context mContext, List<BbRankListEntity.DataBean.BrokersbaobeiBean> bbRankList) {
        this.mContext = mContext;
        this.bbRankList.addAll(bbRankList);
    }
    public void updata(List<BbRankListEntity.DataBean.BrokersbaobeiBean> bbRankList){
        this.bbRankList.clear();
        this.bbRankList.addAll(bbRankList);
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_bb_rank, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return bbRankList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BbRankListEntity.DataBean.BrokersbaobeiBean brokersbaobeiBean = bbRankList.get(position);
        if(position==0) {
            holder.ivHat.setVisibility(View.VISIBLE);
            holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.purple));
        }else {
            holder.ivHat.setVisibility(View.INVISIBLE);
           if(position==1){
                holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.orange));
            }else if(position==2){
                holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.red_theme));
            }
        }

        ((BBStaticsForJJComActivity)mContext).mImageManager.loadCircleImage(brokersbaobeiBean.getImage(),holder.ivIcon);
        holder.tvName.setText(brokersbaobeiBean.getXingming());
        holder.tvBbNum.setText(brokersbaobeiBean.getSuccess()+"");

            holder.tvRankNum.setText("NO." + (position + 1));



    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_rank_num)
        TextView tvRankNum;
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.iv_hat)
        ImageView ivHat;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_bb_num)
        TextView tvBbNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

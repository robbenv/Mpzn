package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.entity.BBstaticForKfsListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/11/14.
 */
public class RvBBStaticForKfsAdapter extends RecyclerView.Adapter<RvBBStaticForKfsAdapter.MyViewHolder> {

    private List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> bBstaticForKfsListEntityData = new ArrayList<>();
    private Context mContext;

    public RvBBStaticForKfsAdapter(Context mContext, List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> bBstaticForKfsListEntityData) {
        this.mContext = mContext;
        this.bBstaticForKfsListEntityData.addAll(bBstaticForKfsListEntityData);
    }

    public void updata(List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> bBstaticForKfsListEntityData) {
        this.bBstaticForKfsListEntityData.clear();
        this.bBstaticForKfsListEntityData.addAll(bBstaticForKfsListEntityData);
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_bb_rank, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BBstaticForKfsListEntity.DataBean.AgentbaobeiBean dataBean = bBstaticForKfsListEntityData.get(position);
        holder.rlIcon.setVisibility(View.GONE);
        if (position == 0) {
            holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else {
            if (position == 1) {
                holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.orange));
            } else if (position == 2) {
                holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.red_theme));
            }else{
                holder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.font_black_2));

            }
        }

        holder.tvName.setText(dataBean.getCompany_name());
        holder.tvBbNum.setText(dataBean.getSuccess() + "");

        holder.tvRankNum.setText("NO." + (position+1));



    }

    @Override
    public int getItemCount() {
        return bBstaticForKfsListEntityData.size();
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

        @Bind(R.id.rl_icon)
        RelativeLayout rlIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

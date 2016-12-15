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
import com.mpzn.mpzn.activity.BBstaticForKfsAcitvity;
import com.mpzn.mpzn.entity.BBstaticForKfsListEntity;
import com.mpzn.mpzn.utils.ImageManager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/11/14.
 */
public class RvBBStaticForKfsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> bBstaticForKfsListEntityData = new ArrayList<>();
    private Context mContext;

    public enum ITEM_TYPE {
        ITEM,
        NODATA
    }

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RvCheckBuildingAdapter.ITEM_TYPE.ITEM.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_bb_rank, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else {
            return new NoDataViewHolder(mInflater.inflate(R.layout.item_no_data_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NoDataViewHolder){
            return;
        }

        RvBBStaticForKfsAdapter.MyViewHolder mholder=(RvBBStaticForKfsAdapter.MyViewHolder)holder;
        BBstaticForKfsListEntity.DataBean.AgentbaobeiBean dataBean = bBstaticForKfsListEntityData.get(position);
        if (position == 0) {
            mholder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.purple));
        } else {
            if (position == 1) {
                mholder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.orange));
            } else if (position == 2) {
                mholder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.red_theme));
            }else{
                mholder.tvRankNum.setTextColor(mContext.getResources().getColor(R.color.font_black_2));

            }
        }
        if (dataBean.getHead_image() == null || "".equals(dataBean.getHead_image())) {
//            mholder.ivIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_default_circle));

        } else {
            ((BBstaticForKfsAcitvity)mContext).mImageManager.loadCircleImageWithWhite(dataBean.getHead_image(),mholder.ivIcon);
        }

        mholder.tvName.setText(dataBean.getCompany_name());
        mholder.tvBbNum.setText(dataBean.getCount() + "");

        mholder.tvRankNum.setText("NO." + (position+1));



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

    public class NoDataViewHolder extends RecyclerView.ViewHolder{

        public NoDataViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM.ordinal()代表0， ITEM_TYPE.NODATA.ordinal()代表1

        if(bBstaticForKfsListEntityData.size() == 0) {
            return ITEM_TYPE.NODATA.ordinal();
        }else{
            return ITEM_TYPE.ITEM.ordinal();
        }
    }
}

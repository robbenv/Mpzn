package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.ReviewListActivity;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.entity.ReviewListEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.ImageManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/10/9.
 */
public class RvReviewAdapter extends RecyclerView.Adapter<RvReviewAdapter.RvViewHolder> {



    private Context mContext;
    private List<ReviewListEntity.DataBean> reviewdatalist;

    public RvReviewAdapter(Context mContext, List<ReviewListEntity.DataBean> reviewdatalist) {
        this.mContext = mContext;
        this.reviewdatalist = reviewdatalist;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_reviewlist, parent, false);
        RvViewHolder rvViewHolder = new RvViewHolder(itemView);

        return rvViewHolder;
    }

    public void updataView(List<ReviewListEntity.DataBean> reviewdatalist) {
        this.reviewdatalist = reviewdatalist;
        this.notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(final RvViewHolder holder, int position) {
        final ReviewListEntity.DataBean dataBean = reviewdatalist.get(position);
        ((ReviewListActivity) mContext).mImageManager.loadCircleImage(dataBean.getHeadimage(), holder.imgIcon);
        holder.tvContent.setText(dataBean.getContent());
        holder.tvDate.setText(formatData("yyyy-MM-dd", dataBean.getCreatedate()));
        holder.tvName.setText(dataBean.getMname());
        holder.tvFovor.setText(dataBean.getFavour()+"");


        if(dataBean.getIs_dianzan()==1){
            ((ReviewListActivity) mContext).mImageManager.loadResImage(R.drawable.zan,holder.btnZan);
            holder.tvFovor.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        }else{
            ((ReviewListActivity) mContext).mImageManager.loadResImage(R.drawable.zan_dis,holder.btnZan);
            holder.tvFovor.setTextColor(mContext.getResources().getColor(R.color.font_black_5));
        }
        holder.btnZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBean.getIs_dianzan()==0){
                    OkHttpUtils.post()
                            .url(API.ZAN_REVIEW_POST)
                            .addParams("token",MyApplication.getInstance().token)
                            .addParams("cid",dataBean.getCid()+"")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if(simpleEntity.getCode()==200){
                                        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                                        ((ReviewListActivity) mContext).mImageManager.loadResImage(R.drawable.zan,holder.btnZan);
                                        holder.tvFovor.setText((Integer.parseInt(holder.tvFovor.getText().toString())+1)+"");
                                        holder.tvFovor.setTextColor(mContext.getResources().getColor(R.color.red_theme));
                                        dataBean.setIs_dianzan(1);
                                    }
                                }
                            });
                }else{
                    OkHttpUtils.post()
                            .url(API.CANCEL_ZAN_REVIEW_POST)
                            .addParams("token",MyApplication.getInstance().token)
                            .addParams("cid",dataBean.getCid()+"")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if(simpleEntity.getCode()==200){
                                        Toast.makeText(mContext, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                        ((ReviewListActivity) mContext).mImageManager.loadResImage(R.drawable.zan_dis,holder.btnZan);
                                        holder.tvFovor.setText((Integer.parseInt(holder.tvFovor.getText().toString())-1)+"");
                                        holder.tvFovor.setTextColor(mContext.getResources().getColor(R.color.font_black_5));
                                        dataBean.setIs_dianzan(0);
                                    }
                                }
                            });
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return reviewdatalist.size();
    }


    public static class RvViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_icon)
        ImageView imgIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.btn_zan)
        ImageView btnZan;
        @Bind(R.id.tv_fovor)
        TextView tvFovor;

        public RvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

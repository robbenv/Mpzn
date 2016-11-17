package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.entity.MessageEntity;

import java.text.DateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/11/3.
 */
public class RvMessageAdapter extends RecyclerView.Adapter<RvMessageAdapter.MyViewHolder> {


    private Context mContext;
    private List<MessageEntity> messageEntityList;

    public RvMessageAdapter(Context mContext, List<MessageEntity> messageEntityList) {
        this.mContext = mContext;
        this.messageEntityList = messageEntityList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_message, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageEntity messageEntity = messageEntityList.get(position);
        String createdate = formatData("yyyy-MM-dd HH:mm:ss", messageEntity.getCreatDate()/1000);
        holder.tvDate.setText(createdate);
        holder.tvTitle.setText(messageEntity.getSubject());
        if(messageEntity.getThumb()!=null&&messageEntity.getThumb()!="") {
            holder.img.setVisibility(View.VISIBLE);
            ((MainActivity) mContext).mImageManager.loadUrlImage(messageEntity.getThumb(), holder.img);
        }else{
            holder.img.setVisibility(View.GONE);
        }
        holder.tvContent.setText(messageEntity.getAbstractX());


    }

    @Override
    public int getItemCount() {
        return messageEntityList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_date)
        TextView tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}

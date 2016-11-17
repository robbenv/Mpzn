package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.JingjirenListEntity;
import com.mpzn.mpzn.http.API;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/10/13.
 */

public class RvCheckJingjirenAdapter extends RecyclerView.Adapter<RvCheckJingjirenAdapter.MyViewHolder> {



    private Button button;
    private String fromType;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<JingjirenListEntity.DataBean> jingjirenList = new ArrayList<>();
    private boolean isEdit;
    private boolean allDelete;
    private ChangCheckListener changCheckListener;


    public RvCheckJingjirenAdapter(Context mContext, List<JingjirenListEntity.DataBean> jingjirenList, String fromType) {
        this.mContext = mContext;
        this.jingjirenList = jingjirenList;
        this.fromType = fromType;
        mInflater = LayoutInflater.from(mContext);


    }

    public void updata(List<JingjirenListEntity.DataBean> jingjirenList) {
        this.jingjirenList = jingjirenList;
        this.notifyDataSetChanged();
    }

    public void changeEditable(boolean isEdit, boolean allDelete) {

        this.isEdit = isEdit;
        this.allDelete = allDelete;
        if (isEdit) {
            for (JingjirenListEntity.DataBean data : jingjirenList) {
                data.setCheck(allDelete);
            }
        }
        this.notifyDataSetChanged();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rv_own_add_jingjiren, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JingjirenListEntity.DataBean dataBean = jingjirenList.get(position);
        if(dataBean.getHeadimage()!="") {
            ((BaseActivity) mContext).mImageManager.loadCircleImage(API.API + dataBean.getHeadimage(), holder.ivIcon);
        }else{
            ((BaseActivity) mContext).mImageManager.loadCircleResImage(R.drawable.icon_default, holder.ivIcon);

        }
        holder.tvName.setText(dataBean.getXingming());

        holder.tvPhone.setText(dataBean.getMname());
        String createdate = formatData("yyyy-MM-dd", Long.parseLong(String.valueOf(dataBean.getLastvisit())));
        holder.tvLastVisit.setText(  "最后一次登录时间:" + createdate);
        if (isEdit) {
            holder.cbJingjiren.setVisibility(View.VISIBLE);
            holder.cbJingjiren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        dataBean.setCheck(true);
                    } else {
                        dataBean.setCheck(false);
                    }
                    if (changCheckListener != null) {
                        changCheckListener.ChangCheck(isChecked);
                    }
                }
            });
            holder.cbJingjiren.setChecked(dataBean.getCheck());
        } else {
            holder.cbJingjiren.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return jingjirenList.size();
    }

    public void setChangCheckListener(RvCheckJingjirenAdapter.ChangCheckListener changCheckListener) {
        this.changCheckListener = changCheckListener;
    }

    public interface ChangCheckListener {
        void ChangCheck(boolean isCheck);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.cb_jingjiren)
        CheckBox cbJingjiren;
        @Bind(R.id.tv_last_visit)
        TextView tvLastVisit;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

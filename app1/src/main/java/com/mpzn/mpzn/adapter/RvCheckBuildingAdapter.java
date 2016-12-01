package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.activity.ApplyForSellActivity;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.StarAndBrowseActivity;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.StarBuildingEntity;
import com.mpzn.mpzn.http.API;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/9/26.
 */

public class RvCheckBuildingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Button button;
    private String fromType;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<StarBuildingEntity.DataBean> starBuildingList=new ArrayList<>();
    private boolean isEdit;
    private boolean allDelete;
    private ChangCheckListener changCheckListener;
    private List<StarBuildingEntity.DataBean> noData=new ArrayList<>();
    public enum ITEM_TYPE {
        ITEM,
        NODATA
    }



    public RvCheckBuildingAdapter(Context mContext, List<StarBuildingEntity.DataBean> starBuildingList,String fromType ) {
        this.mContext = mContext;
        this.starBuildingList=starBuildingList;
        noData.add(new StarBuildingEntity.DataBean());
        this.fromType=fromType;
        mInflater = LayoutInflater.from(mContext);


    }

    public void updata(List<StarBuildingEntity.DataBean> starBuildingList) {
        if(starBuildingList.size()!=0) {
            this.starBuildingList = starBuildingList;
            this.notifyDataSetChanged();
        }else{
            this.starBuildingList = noData;
            this.notifyDataSetChanged();
        }
    }
    public void changeEditable(boolean isEdit,boolean allDelete){

        this.isEdit=isEdit;
        this.allDelete=allDelete;
        if(isEdit) {
            for (StarBuildingEntity.DataBean data : starBuildingList) {
                data.setCheck(allDelete);
            }
        }
        this.notifyDataSetChanged();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM.ordinal()) {
            View view = mInflater.inflate(R.layout.item_rv_star_browse, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        } else {
            return new NoDataViewHolder(mInflater.inflate(R.layout.item_no_data_layout, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof NoDataViewHolder){
            return;
        }
        MyViewHolder mholder=(MyViewHolder)holder;
        final StarBuildingEntity.DataBean dataBean = starBuildingList.get(position);
        ((BaseActivity)mContext).mImageManager.loadUrlImage(dataBean.getThumb(),mholder.img);
        mholder.tvAddrss.setText(dataBean.getArea());
        mholder.tvName.setText(dataBean.getSubject());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Aid",dataBean.getAid());
                intent.setClass(mContext,DetailNewhouseActivity.class);
                (mContext).startActivity(intent);
            }
        };
        mholder.img.setOnClickListener(onClickListener);
        mholder.tvName.setOnClickListener(onClickListener);

        int price = dataBean.getPrice();
        if(price!=0) {
            mholder.tvPrice.setText(price+"元/m²");
        }else{
            mholder.tvPrice.setText("售价待定");
        }
        mholder.tvType.setText(dataBean.getType());
        String createdate = formatData("yyyy年MM月dd日", Long.parseLong(String.valueOf(dataBean.getCreatedate())));
        mholder.tvDate.setText(fromType+"时间:"+createdate);
        if(isEdit){
            Log.i("Bug_test1", "onBindViewHolder()__");
            mholder.cbDelete.setVisibility(View.VISIBLE);
            mholder.cbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("Bug_test1", "onCheckedChanged()__");
                   if(isChecked){
                       dataBean.setCheck(true);
                   }else {
                       dataBean.setCheck(false);
                   }
                    if(changCheckListener!=null) {
                        changCheckListener.ChangCheck(isChecked);
                    }
                }
            });
            mholder.cbDelete.setChecked(dataBean.getCheck());
        }else{
            mholder.cbDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM.ordinal()代表0， ITEM_TYPE.NODATA.ordinal()代表1

        if(starBuildingList==noData) {
            return ITEM_TYPE.NODATA.ordinal();
        }else{
            return ITEM_TYPE.ITEM.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return starBuildingList.size();
    }

    public void setChangCheckListener(ChangCheckListener changCheckListener){
        this.changCheckListener=changCheckListener;
    }

    public interface ChangCheckListener{
        void ChangCheck(boolean isCheck);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_addrss)
        TextView tvAddrss;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.cb_delete)
        CheckBox cbDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class NoDataViewHolder extends RecyclerView.ViewHolder{

        public NoDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.entity.JingjirenListEntity;
import com.mpzn.mpzn.entity.ProxySellListEntity;
import com.mpzn.mpzn.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by larry on 16-11-23.
 */
public class HandleProxyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context mContext;
    private List<ProxySellListEntity.DataBean> datas;
    private LayoutInflater mInflater;
    private boolean isNoData;
    private List<ProxySellListEntity.DataBean> noData=new ArrayList<>();
    private ChangCheckListener changCheckListener;

    public interface ChangCheckListener{
        void ChangCheck(boolean isChecked);
    }

    public void selectAll(boolean allSelect) {
        for (ProxySellListEntity.DataBean data : datas) {
            data.setChecked(allSelect ? 1 : 0);
        }
        this.notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        ITEM,
        NODATA
    }

    public HandleProxyAdapter(Context mContext, List<ProxySellListEntity.DataBean> datas) {
        Log.i("Proxy_test34", "HandleProxyAdapter()__");
        this.mContext = mContext;
        this.datas = datas;
        noData.add(new ProxySellListEntity.DataBean(datas.size() != 0));
        mInflater = LayoutInflater.from(mContext);
    }

    public void setChangCheckListener(HandleProxyAdapter.ChangCheckListener changCheckListener) {
        this.changCheckListener = changCheckListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Proxy_test3", "onCreateViewHolder()__into");
        if (viewType == RvCheckBuildingAdapter.ITEM_TYPE.ITEM.ordinal()) {
            Log.i("Proxy_test3", "onCreateViewHolder()__ITEM");
            View view = mInflater.inflate(R.layout.item_rv_handle_sell, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else {
            Log.i("Proxy_test3", "onCreateViewHolder()__NoData");
            return new NoDataViewHolder(mInflater.inflate(R.layout.item_no_data_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("Proxy_test34", "onBindViewHolder()__into");

        if(holder instanceof NoDataViewHolder){
            Log.i("Proxy_test3", "onBindViewHolder()__holder instanceof NoDataViewHolder__return");
            return;
        }

        MyViewHolder mholder=(MyViewHolder)holder;
        final ProxySellListEntity.DataBean dataBean = datas.get(position);

        mholder.tvSubject.setText(dataBean.getSubject());

        mholder.tvCompany.setText(dataBean.getCompany());
        Log.i("Proxy_test3", "onBindViewHolder()__dataBean.getCompany() = "+dataBean.getCompany());

        String createdate = formatData("yyyy-MM-dd HH:mm:ss", Long.parseLong(String.valueOf(dataBean.getCreatedate())));
        mholder.tvData.setText(  "" + createdate);

        mholder.cbProxysell.setVisibility(View.VISIBLE);
        mholder.cbProxysell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    dataBean.setChecked(isChecked ? 1 : 0);
                    if (changCheckListener != null) {
                        changCheckListener.ChangCheck(isChecked);
                    }
                }
            });
        mholder.cbProxysell.setChecked(dataBean.getChecked() == 1);

    }

    @Override
    public int getItemCount() {

            return datas.size();

    }

    static class MyViewHolder extends RecyclerView.ViewHolder  {
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_company)
        TextView tvCompany;
        @Bind(R.id.cb_proxysell)
        CheckBox cbProxysell;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



    public void updata(List<ProxySellListEntity.DataBean> proxySellList) {
        Log.i("Proxy_test34", "updata()__into");
        if(proxySellList.size()!=0) {
            Log.i("Proxy_test3", "updata()__proxySellList = "+proxySellList.size());
            datas = proxySellList;
            this.notifyDataSetChanged();
        }else{
            Log.i("Proxy_test34", "updata()__noData");
            this.datas = noData;
            this.notifyDataSetChanged();
        }
    }



    public class NoDataViewHolder extends RecyclerView.ViewHolder{

        public NoDataViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("Proxy_test34", "getItemViewType()__into");
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM.ordinal()代表0， ITEM_TYPE.NODATA.ordinal()代表1

        if(datas == noData) {
            return ITEM_TYPE.NODATA.ordinal();
        }else{
            return ITEM_TYPE.ITEM.ordinal();
        }
    }


}

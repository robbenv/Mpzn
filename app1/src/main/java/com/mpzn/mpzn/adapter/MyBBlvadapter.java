package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseListAdapter;
import com.mpzn.mpzn.entity.MyBBEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.getStatusBarHeight;

/**
 * Created by Icy on 2016/9/27.
 */

public class MyBBlvadapter extends BaseListAdapter<MyBBEntity.DataBean> {
    public boolean isNoData;

    public MyBBlvadapter(Context context, List<MyBBEntity.DataBean> datalist) {
        super(context, datalist);

    }


    public void updata(List<MyBBEntity.DataBean> datalist){

        clearAll();
        addALL(datalist);
        if(datalist.size()!=0) {
            isNoData=false;
        }else {
            isNoData=true;
            MyBBEntity.DataBean dataBean = new MyBBEntity.DataBean(true);
            add(dataBean);
        }
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(MyApplication.mScreenHeight*0.75));
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }
       
        ViewHolder viewHolder;
        if(convertView!=null&& convertView.getTag()!=null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else{
            convertView = mInflater.inflate(R.layout.item_my_bb_layout, parent, false);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        List<MyBBEntity.DataBean> data = getData();
        MyBBEntity.DataBean dataBean = data.get(position);
        viewHolder.tvName.setText(dataBean.getKhname());
        viewHolder.tvPhone.setText("("+dataBean.getKhphone()+")");

        viewHolder.tvBuildingDate.setText(dataBean.getSubject()+" \n"+formatData("yyyy-MM-dd HH:mm", dataBean.getBaobeitime()));
        String  isChecked="未核验";
        if(dataBean.getJdaotime()>0){
            isChecked="已核验";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.green));
        }else{
            isChecked = isChecked+"\n("+dataBean.getCode()+")";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.blue));

        }
        viewHolder.tvIschecked.setText(isChecked);
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.tv_building_date)
        TextView tvBuildingDate;
        @Bind(R.id.tv_ischecked)
        TextView tvIschecked;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

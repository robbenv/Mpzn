package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseListAdapter;
import com.mpzn.mpzn.entity.MyBBEntity;
import com.mpzn.mpzn.entity.MySellListEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/10/11.
 */
public class MySelllvadapter extends BaseListAdapter<MySellListEntity.DataBean> {

    public boolean isNoData;

    public MySelllvadapter(Context mContext, List<MySellListEntity.DataBean> datalist) {

        super(mContext, datalist);
    }


    public void updata(List<MySellListEntity.DataBean> datalist) {

        clearAll();
        addALL(datalist);
        if(datalist.size()!=0) {
            isNoData=false;
        }else {
            isNoData=true;
            MySellListEntity.DataBean dataBean = new MySellListEntity.DataBean(true);
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
        if (convertView != null&& convertView.getTag()!=null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_lv_my_sell_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        List<MySellListEntity.DataBean> data = getData();
        MySellListEntity.DataBean dataBean = data.get(position);
        viewHolder.tvSubject.setText(dataBean.getSubject());
        String createdate = formatData("yyyy-MM-dd HH:mm", dataBean.getCreatedate());
        viewHolder.tvData.setText("(" + createdate + ")");
        viewHolder.tvKfsname.setText(dataBean.getKfsname());
        String isChecked = "";
        if (dataBean.getChecked() == 1) {
            isChecked = "已通过";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.blue));
        }  else if(dataBean.getChecked() == 0 ){
            isChecked = "待审核";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
        viewHolder.tvIschecked.setText(isChecked);
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_kfsname)
        TextView tvKfsname;
        @Bind(R.id.tv_ischecked)
        TextView tvIschecked;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

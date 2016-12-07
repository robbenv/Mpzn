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
import com.mpzn.mpzn.entity.UserTrackEntity;
import com.mpzn.mpzn.entity.UserTrackEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by larry on 16-12-6.
 */
public class TrackListAdapter extends BaseListAdapter {
    public boolean isNoData;

    public TrackListAdapter(Context context, List<UserTrackEntity.DataBean> datalist) {
        super(context, datalist);

    }


    public void updata(List<UserTrackEntity.DataBean> datalist) {

        clearAll();
        addALL(datalist);
        if (datalist.size() != 0) {
            isNoData = false;
        } else {
            isNoData = true;
            UserTrackEntity.DataBean dataBean = new UserTrackEntity.DataBean();
            add(dataBean);
        }
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (MyApplication.mScreenHeight * 0.75));
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        ViewHolder viewHolder;
        if (convertView != null && convertView.getTag() != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_track_user_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        List<UserTrackEntity.DataBean> data = getData();
        UserTrackEntity.DataBean dataBean = data.get(position);
        viewHolder.tvName.setText(dataBean.getKhname());
        viewHolder.tvPhone.setText("(" + dataBean.getKhphone().substring
                (dataBean.getKhphone().length() - 5, dataBean.getKhphone().length() - 1) + ")");

        viewHolder.tvBuildingDate.setText(formatData("yyyy-MM-dd HH:mm:ss", dataBean.getJdaotime()));
        String isChecked = "未核验";
//        if (dataBean.getJdaotime() > 0) {
//            isChecked = "已核验";
//            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.green));
//        } else {
//            isChecked = isChecked + "\n(" + dataBean.getCode() + ")";
//            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.blue));
//
//        }
        viewHolder.tvLoupanName.setText(dataBean.getSubject());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.tv_building_date)
        TextView tvBuildingDate;
        @Bind(R.id.tv_loupan_name)
        TextView tvLoupanName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseListAdapter;
import com.mpzn.mpzn.views.FilterView.entity.BuildingEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/8/31.
 */
public class MainHomeBuildingLvAdapter extends BaseListAdapter<BuildingEntity> {

    private boolean isNoData;
    private int mHeight;
    public static final int ONE_SCREEN_COUNT = 5; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定
    public static final int ONE_REQUEST_COUNT = 10; // 一次请求的个数


    public MainHomeBuildingLvAdapter(Context mContext, List<BuildingEntity> buildingData) {
        super(mContext, buildingData);
    }

    // 设置数据
    public void setData(List<BuildingEntity> list) {

        clearAll();

        addALL(list);


        isNoData = false;
        if (list.size() == 1 && list.get(0).isNoData()) {
            // 暂无数据布局

            isNoData = list.get(0).isNoData();
            mHeight = list.get(0).getHeight();
        } else {
            // 添加空数据
            Toast.makeText(mContext, "刷新成功，发现"+getCount()+"个楼盘", Toast.LENGTH_SHORT).show();
            if (list.size() < ONE_SCREEN_COUNT) {
                Log.i("Proxy_test34", "setData()__添加空数据");
                addALL(createEmptyList(ONE_SCREEN_COUNT - list.size() + 1));
            }
        }
        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<BuildingEntity> createEmptyList(int size) {
        List<BuildingEntity> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i = 0; i < size; i++) {
            emptyList.add(new BuildingEntity());
        }
        return emptyList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        // 正常数据
        final Holder holder;
        if (convertView != null && convertView instanceof LinearLayout) {
            holder = (Holder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_lv_home_vp_bottom, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight/ONE_SCREEN_COUNT);
            convertView.setLayoutParams(params);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }

        BuildingEntity entity = getItem(position);

        holder.llRootView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(entity.getSubject())) {
            holder.llRootView.setVisibility(View.INVISIBLE);
            return convertView;
        }

        mImageManager.loadUrlImage(entity.getThumb(), holder.img);

        holder.msgName.setText(entity.getSubject());
        int jdjj = entity.getJdjj();
        if (jdjj != 0) {
            holder.msgExtra.setText("最低价仅售" + jdjj + "元/m²起！");
        } else {
            holder.msgExtra.setVisibility(View.INVISIBLE);
        }
        holder.msgAddr.setText(entity.getAddress());
        holder.area.setText(entity.getDiqu());

        holder.tagType.setText(entity.getTag().getWylxarray());
        holder.weifang.setText(entity.getTitle());
        int dj = entity.getDj();
        if(dj!=0) {
            holder.price.setText(dj + "");
        }else{
            holder.price.setText("待定");
        }

        return convertView;
    }

    static class Holder {
        @Bind(R.id.ll_root_view)
        LinearLayout llRootView;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.msg_name)
        TextView msgName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.tv)
        TextView tv;
        @Bind(R.id.msg_extra)
        TextView msgExtra;
        @Bind(R.id.msg_addr)
        TextView msgAddr;
        @Bind(R.id.area)
        TextView area;
        @Bind(R.id.tag_type)
        TextView tagType;
        @Bind(R.id.weifang)
        TextView weifang;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}



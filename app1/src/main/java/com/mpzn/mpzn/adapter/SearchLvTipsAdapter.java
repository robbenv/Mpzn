package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.entity.SearchTipsList;
import com.mpzn.mpzn.views.FilterView.adapter.BaseListAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/9/12.
 */
public class SearchLvTipsAdapter extends BaseListAdapter<SearchTipsList.DataEntity> {

    private boolean isNoData;


    public SearchLvTipsAdapter(Context context) {
        super(context);
    }

    public SearchLvTipsAdapter(Context context, List<SearchTipsList.DataEntity> list) {
        super(context, list);
    }

    // 设置数据
    public void setData(List<SearchTipsList.DataEntity> list) {
        clearAll();
        addALL(list);

        isNoData=false;

        if (list.size() == 1 && list.get(0).isNoData()) {
            // 暂无数据布局

            isNoData = list.get(0).isNoData();
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_search, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(100));
            convertView.setLayoutParams(params);
            return convertView;
        }


        final Holder holder;
        if (convertView != null &&convertView instanceof RelativeLayout) {
            holder = (Holder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_lv__search_tips, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }
        SearchTipsList.DataEntity item = getItem(position);
        String subject = item.getSubject();
        if(subject!=null) {
            holder.tvSubject.setText(subject);
        }

        String diqu = item.getDiqu();
        if(diqu!=null) {
            holder.tvDiqu.setText(diqu);
        }

        String title = item.getTitle();
        if(title!=null){
            holder.tvTitle.setText(title);
        }
        String zxcd = item.getZxcd();
        if(zxcd!=null) {
          holder.tvZxcd.setText(zxcd);
        }

        return convertView;
    }

    static class Holder {

        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_diqu)
        TextView tvDiqu;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_zxcd)
        TextView tvZxcd;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

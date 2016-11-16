package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.entity.SearchHotEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/7/29.
 */
public class SearchHotRvAdapter extends RecyclerView.Adapter<SearchHotRvAdapter.ViewHolder>{
    private Context mContext;
    private List<SearchHotEntity.DataBean> datalist=new ArrayList<>();
    private LayoutInflater mInflater;
    public SearchHotRvAdapter(Context context, List<SearchHotEntity.DataBean> datalist) {
        mContext=context;
        this.datalist.addAll(datalist);
        mInflater=LayoutInflater.from(mContext);
    }
    public void updata(List<SearchHotEntity.DataBean> data){
        datalist.clear();
        datalist.addAll(data);
        this.notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v=View.inflate(mContext, R.layout.hot_search_item,null);
        View v = mInflater.inflate(R.layout.hot_search_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datalist.get(position).getSubject());

    }

    @Override
    public int getItemCount() {
        return datalist==null ? 0 : datalist.size();
    }
    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;


        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.item_textview);
        }
    }
}

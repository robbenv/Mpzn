package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.NewsList;
import com.mpzn.mpzn.fragment.NewsFragment;
import com.mpzn.mpzn.http.API;

import java.util.ArrayList;
import java.util.List;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by Icy on 2016/7/26.
 */
public class MainNewsListViewAdapter extends BaseAdapter {
    private List<NewsList.DataBean.NewsBean> data=new ArrayList<>();
    private Context mContext;




    public MainNewsListViewAdapter(Context context, List<NewsList.DataBean.NewsBean> data) {
        mContext=context;
        this.data.addAll(data);
    }

    public void setData(List<NewsList.DataBean.NewsBean> data){
        this.data.clear();
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_lv_news,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_item_news= (ImageView) convertView.findViewById(R.id.iv_item_news);
            viewHolder.tv_item_news_title= (TextView) convertView.findViewById(R.id.tv_item_news_title);
            viewHolder.tv_item_news_des= (TextView) convertView.findViewById(R.id.tv_item_news_des);
            viewHolder.author_date= (TextView) convertView.findViewById(R.id.author_date);
            viewHolder.num_watched= (TextView) convertView.findViewById(R.id.num_watched);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ((BaseActivity)mContext).mImageManager.loadUrlImage(data.get(position).getThumb(),viewHolder.iv_item_news);
        viewHolder.tv_item_news_title.setText(data.get(position).getSubject());
        viewHolder.tv_item_news_des.setText(data.get(position).getAbstractX());
        String editor = data.get(position).getEditor();
        String createdate = formatData("yyyy-MM-dd HH:mm", data.get(position).getCreatedate());
        viewHolder.author_date.setText(createdate);
        viewHolder.num_watched.setText(data.get(position).getClicks()+"");


        return convertView;
    }

    class ViewHolder {
        ImageView iv_item_news;
        TextView tv_item_news_title;
        TextView tv_item_news_des;
        TextView author_date;
        TextView num_watched;

    }
}

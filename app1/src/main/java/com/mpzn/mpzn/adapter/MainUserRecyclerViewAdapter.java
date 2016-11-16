package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.entity.ItemUserTool;
import com.mpzn.mpzn.helper.MyItemTouchCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by Icy on 2016/8/10.
 */
public class MainUserRecyclerViewAdapter extends RecyclerView.Adapter<MainUserRecyclerViewAdapter.MyViewHolder>  implements MyItemTouchCallback.ItemTouchAdapter, View.OnClickListener {
    private Context mContext;
    private List<ItemUserTool> items;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;  //item的点击监听

    @Override
    public void onClick(View v) {

        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(ItemUserTool)v.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , ItemUserTool data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void updata(List<ItemUserTool> items){
        this.items.clear();
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    public int[] getItems(){
        int[] itemids=new int[items.size()];
        for(int i=0;i<items.size();i++){
            itemids[i]=items.get(i).getId();
        }
        return itemids;
    }

    public MainUserRecyclerViewAdapter( List<ItemUserTool> items) {
        this.items=items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view= (LayoutInflater.from(
                mContext).inflate(R.layout.item_rv_user, parent,
                false));
        //将创建的View注册点击事件
        view.setOnClickListener(this);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(items.get(position).getName());
        if(items.get(position).getName().equals("更多")) {
            holder.tv.setText("");
        }
        holder.img.setImageResource(items.get(position).getImgResId());

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition==items.size()-1 || toPosition==items.size()-1){
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        Log.e("TAG", "onMove"+items.get(0).toString());
    }

    @Override
    public void onSwiped(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        ImageView img;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_user_tool);
            img=(ImageView) view.findViewById(R.id.img_user_tool);
        }
    }
}

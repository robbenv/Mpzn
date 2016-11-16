package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseListAdapter;
import com.mpzn.mpzn.entity.JJComlistForChooseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/10/24.
 */

public class JJcomListForChooseAdapter extends BaseListAdapter<JJComlistForChooseEntity.DataBean> {

    public JJcomListForChooseAdapter(Context context, List<JJComlistForChooseEntity.DataBean> list) {
        super(context, list);
    }
    public void updata(List<JJComlistForChooseEntity.DataBean> list){
        clearAll();
        addALL(list);
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.layout_item_jjcom, null);
        }
        tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(getItem(position).getCompany_name());
        return convertView;
    }
}

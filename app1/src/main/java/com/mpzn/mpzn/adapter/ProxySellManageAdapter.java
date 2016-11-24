package com.mpzn.mpzn.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseListAdapter;
import com.mpzn.mpzn.entity.JingjirenListEntity;
import com.mpzn.mpzn.entity.ProxySellListEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.DateUtil.formatData;

/**
 * Created by larry on 16-11-22.
 */
public class ProxySellManageAdapter extends BaseListAdapter {

    public boolean isNoData;
    private boolean isEdit;

    private HashMap<Integer, Boolean> checkedMap = new HashMap<>();

    private ChangCheckListener changCheckListener;
    private boolean allselect;


    public interface ChangCheckListener{
        void ChangCheck(int size);
    }

    public ProxySellManageAdapter(Context context, List list) {
        super(context, list);
        initDate();
    }

    private void initDate() {
        for (int i = 0; i < getData().size(); i++) {
            checkedMap.put(i, false);
        }
    }

    public void updata(List<ProxySellListEntity.DataBean> datalist) {
        Log.i("Proxy_test4", "updata()__");
        clearAll();
        addALL(datalist);
        if(datalist.size()!=0) {
            isNoData=false;
        }else {
            isNoData=true;
            ProxySellListEntity.DataBean dataBean = new ProxySellListEntity.DataBean(true);
            add(dataBean);
        }
        notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        // 暂无数据
        if (isNoData) {
            Log.i("Proxy_test", "getView()__isNoData = "+isNoData);
            Log.i("Proxy_test", "getView()__isEdit = "+isEdit);
            Log.i("Proxy_test", "getView()__暂无数据return");
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(MyApplication.mScreenHeight*0.75));
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }
        List<ProxySellListEntity.DataBean> data = getData();
        final ProxySellListEntity.DataBean dataBean = data.get(position);

        final ViewHolder viewHolder;
        //判断是否复用
        if (convertView != null&& convertView.getTag()!=null) {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.cbProxysell.setTag(dataBean.getCid());
        } else {
            convertView = mInflater.inflate(R.layout.item_lv_proxy_sell_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.tvSubject.setText(dataBean.getSubject());
        String createdate = formatData("yyyy-MM-dd HH:mm:ss", dataBean.getCreatedate());
        viewHolder.tvData.setText("(" + createdate + ")");
        viewHolder.tvCompany.setText(dataBean.getCompany());


        String isChecked = "";
        if (dataBean.getChecked() == 1 && dataBean.getStatus() == 1) {
            Log.i("Proxy_test", "getView()__已通过");
            isChecked = "已通过";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else if(dataBean.getChecked() == 1 && dataBean.getStatus() == 0){
            Log.i("Proxy_test", "getView()__未通过");
            isChecked = "未通过";
            viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.red_theme));
        } else if(dataBean.getChecked() == 0 ){
            Log.i("Proxy_test", "getView()__未审核");
            if (isEdit) {
                Log.i("Proxy_test"+"test", "getView()__isEdit = " + isEdit);
                viewHolder.tvIschecked.setVisibility(View.GONE);
                viewHolder.cbProxysell.setVisibility(View.VISIBLE);


                viewHolder.cbProxysell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        int tag = (Integer) compoundButton.getTag();
                        checkedMap.put(tag, isChecked);
                        notifyDataSetChanged();
//                            dataBean.setChecked(compoundButton.isChecked() ? 1 : 0);

                    }
                });





            } else {
                viewHolder.tvIschecked.setVisibility(View.VISIBLE);
                viewHolder.cbProxysell.setVisibility(View.GONE);
                viewHolder.tvIschecked.setTextColor(mContext.getResources().getColor(R.color.orange));

                isChecked = "未审核";
            }
        }
        viewHolder.tvIschecked.setText(isChecked);
        return convertView;
    }

    public void changeEditable(boolean isEdit, boolean allselect) {
        this.isEdit = isEdit;
        this.allselect = allselect;
//        if (isEdit) {
//            for (ProxySellListEntity.DataBean data : getData()) {
//                data.setChecked(allselect ? 1 : 0);
//            }
//        }



        notifyDataSetChanged();

    }

    static class ViewHolder {
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_company)
        TextView tvCompany;
        @Bind(R.id.tv_ischecked)
        TextView tvIschecked;
        @Bind(R.id.cb_proxysell)
        CheckBox cbProxysell;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvBbRankAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.BbRankListEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.DividerItemDecoration;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.widget.LinearLayout.VERTICAL;

public class BBStaticsForJJComActivity extends BaseActivity {


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.rv_bb_rank)
    RecyclerView rvBbRank;
    @Bind(R.id.sp_date)
    AppCompatSpinner spDate;
    @Bind(R.id.tv_content_title)
    TextView tvContentTitle;
    String type = "all";



    private List<String> mList = new ArrayList();
    private ArrayAdapter arrayAdapter;

    private List<BbRankListEntity.DataBean.BrokersbaobeiBean> BBRankList = new ArrayList();
    private RvBbRankAdapter rvBbRankAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bbstatics_for_jjcom;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        myActionBar.init("报备统计", R.drawable.return_gray, 0);

        mList.add("全部报备");
        mList.add("本月");
        mList.add("本周");
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spDate.setAdapter(arrayAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, VERTICAL, false);
        rvBbRankAdapter = new RvBbRankAdapter(mContext, BBRankList);
        rvBbRank.setLayoutManager(linearLayoutManager);
        rvBbRank.setAdapter(rvBbRankAdapter);
        rvBbRank.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));


    }

    @Override
    public void initLayoutParams() {

    }

    public void updata(String type) {

        OkHttpUtils.get()
                .url(API.BBRANK_GET)
                .addParams("type", type)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "response" + response);
                        BbRankListEntity bbRankListEntity = new Gson().fromJson(response, BbRankListEntity.class);
                        BbRankListEntity.DataBean.AgentBean agent = bbRankListEntity.getData().getAgent();
                        BbRankListEntity.DataBean.BrokersbaobeiBean agentbaobeiBean = new BbRankListEntity.DataBean.BrokersbaobeiBean(agent.getMid(),"公司报备", agent.getSuccess(), agent.getImage());
                        List<BbRankListEntity.DataBean.BrokersbaobeiBean> brokersbaobei = bbRankListEntity.getData().getBrokersbaobei();
                        brokersbaobei.add(0,agentbaobeiBean);
                        BBRankList.addAll(brokersbaobei);
                        Log.e("TAG", "brokersbaobei" + brokersbaobei.size());
                        rvBbRankAdapter.updata(brokersbaobei);
                    }
                });
    }

    @Override
    public void initData() {
        updata(type);

    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = "all";
                    tvContentTitle.setText("总Rank榜");
                } else if (position == 1) {
                    type = "month";
                    tvContentTitle.setText("月Rank榜");
                } else if (position == 2) {
                    type = "week";
                    tvContentTitle.setText("周Rank榜");
                }
                updata(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "all";
            }
        });


    }



}

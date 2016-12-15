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
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvBBStaticForKfsAdapter;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.BBstaticForKfsListEntity;
import com.mpzn.mpzn.entity.KfsOwnBuilding;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.listener.EndLessOnScrollListener;
import com.mpzn.mpzn.views.DividerItemDecoration;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.widget.LinearLayout.VERTICAL;
import static com.mpzn.mpzn.application.MyApplication.token;

public class BBstaticForKfsAcitvity extends BaseActivity {


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.sp_loupan)
    AppCompatSpinner spLoupan;
    @Bind(R.id.tv_content_title)
    TextView tvContentTitle;

    @Bind(R.id.rv_bb_rank)
    RecyclerView rvBbRank;
    @Bind(R.id.sp_time)
    AppCompatSpinner spTime;
    @Bind(R.id.sp_range)
    AppCompatSpinner spRange;

    private List<String> mList = new ArrayList();
    private List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> noData = new ArrayList<>();
    List<KfsOwnBuilding.DataBean> loupans = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    private String loupanAid = "";


    private RvBBStaticForKfsAdapter rvBBStaticForKfsAdapter;
    private List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> agentbaobei = new ArrayList();
    private List<String> mListTime = new ArrayList();
    private List<String> mListRange = new ArrayList<>();
    private String type = "all";
    private String orderBy = "success";
    private List<String> mDateList = new ArrayList<>();
    private List<String> mOderByList = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;
    private int currentMaxOffset = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bbstatic_for_kfs_acitvity;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        myActionBar.init("报备统计", R.drawable.return_gray, 0);

        //全部，本月，本周的下拉菜单
        mListTime.add("全部时间");
        mListTime.add("本月");
        mListTime.add("本周");
        mDateList.addAll(mListTime);//刚进入时用经纪人的spannar列表
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mDateList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spTime.setAdapter(arrayAdapter);


        mListRange.add("成功报备");
        mListRange.add("全部报备");
        mOderByList.addAll(mListRange);//刚进入时用经纪人的spannar列表
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mOderByList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spRange.setAdapter(arrayAdapter);


        mList.add("全部楼盘");
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spLoupan.setAdapter(arrayAdapter);


        linearLayoutManager = new LinearLayoutManager(mContext, VERTICAL, false);
        rvBBStaticForKfsAdapter = new RvBBStaticForKfsAdapter(mContext, agentbaobei);
        rvBbRank.setAdapter(rvBBStaticForKfsAdapter);
        rvBbRank.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        rvBbRank.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void initLayoutParams() {

    }

    public void updataView(String aid, final int offset) {
        OkHttpUtils.get()
                .url(API.BB_STATISTICS_FORKFS_GET)
                .addParams("token", token)
                .addParams("aid", aid)
                .addParams("type", type)
                .addParams("order_by", orderBy)
                .addParams("offset", offset+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.d("BB_STATISTICS_FORKFS_GET__服务器未响应");
                        Toast.makeText(BBstaticForKfsAcitvity.this, "服务器未响应", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BBstaticForKfsListEntity bBstaticForKfsListEntity = new Gson().fromJson(response, BBstaticForKfsListEntity.class);
                        if (bBstaticForKfsListEntity.getCode() == 200) {
                            BBstaticForKfsListEntity.DataBean dataBean = bBstaticForKfsListEntity.getData();
                            if (dataBean == null) {

                                rvBBStaticForKfsAdapter.updata(noData);
                            } else {
                                List<BBstaticForKfsListEntity.DataBean.AgentbaobeiBean> dataList = bBstaticForKfsListEntity.getData().getAgentbaobei();
                                Toast.makeText(BBstaticForKfsAcitvity.this, agentbaobei.size() + "", Toast.LENGTH_SHORT).show();
                                if (offset <= currentMaxOffset) {
                                    agentbaobei.clear();
                                } else {
                                    currentMaxOffset ++;
                                }
                                agentbaobei.addAll(dataList);
                                rvBBStaticForKfsAdapter.updata(agentbaobei);

                            }

                        } else {
                            Toast.makeText(BBstaticForKfsAcitvity.this, "获取信息失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void initData() {

        OkHttpUtils.get()
                .url(API.KFS_OWN_BUILDING_GET)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.d("KFS_OWN_BUILDING_GET__服务器未响应__token = " + token + "  \n message:" + e.toString());
                        Toast.makeText(BBstaticForKfsAcitvity.this, "服务器未响应", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        KfsOwnBuilding kfsOwnBuilding = new Gson().fromJson(response, KfsOwnBuilding.class);
                        if (kfsOwnBuilding.getCode() == 200) {

                            loupans = kfsOwnBuilding.getData();

                            for (int i = 0; i < loupans.size(); i++) {
                                mList.add(loupans.get(i).getSubject());
                            }
                            arrayAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(BBstaticForKfsAcitvity.this, kfsOwnBuilding.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

        updataView(null, 0);
    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);

        spLoupan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    loupanAid = loupans.get(position - 1).getAid() + "";

                } else {
                    loupanAid = "";
                }

                updataView(loupanAid, currentMaxOffset);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loupanAid = "";
            }
        });

        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {
                    type = "all";
                } else if (position == 1) {
                    type = "month";
                } else if (position == 2) {
                    type = "week";
                }
                updataView(loupanAid, currentMaxOffset);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "all";
            }
        });

        spRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    orderBy = "success";
                } else if (position == 1) {
                    orderBy = "total";
                }
                updataView(loupanAid, currentMaxOffset);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    //服务端那边没做
//        rvBbRank.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                Logger.d("currentMaxOffset = " + currentMaxOffset);
//                updataView(loupanAid, currentMaxOffset + 1);
//
//            }
//        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

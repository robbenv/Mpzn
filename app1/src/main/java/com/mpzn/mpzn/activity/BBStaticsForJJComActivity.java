package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvBbRankAdapter;
import com.mpzn.mpzn.adapter.VpRecyclerViewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.BbRankListEntity;
import com.mpzn.mpzn.entity.BbStaticForComEntity;
import com.mpzn.mpzn.entity.LoupanFilterEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.DividerItemDecoration;
import com.mpzn.mpzn.views.IsCanScrollViewPager;
import com.mpzn.mpzn.views.PieGraphView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.widget.LinearLayout.VERTICAL;

public class BBStaticsForJJComActivity extends BaseActivity {


    private static final String TAG = "BBComActivity";

    @Bind(R.id.sp_date)
    AppCompatSpinner spDate;
    @Bind(R.id.tv_content_title)
    TextView tvContentTitle;
    String type = "all";


    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.textView2)
    TextView textView2;


    @Bind(R.id.activity_bbstatics_for_jjcom)
    LinearLayout activityBbstaticsForJjcom;
    @Bind(R.id.vp_container)
    IsCanScrollViewPager vpContainer;
    @Bind(R.id.rb_jingjiren)
    RadioButton rbJingjiren;
    @Bind(R.id.rb_company)
    RadioButton rbCompany;
    @Bind(R.id.btn_left)
    Button btnLeft;

    private RecyclerView rvBbRank;
    private PieGraphView pgBbCompany;

    private TextView tvSuc;
    private TextView tvErr;
    private TextView tvMax;
    private TextView tvTotal;

    private List<String> mListJingjiren = new ArrayList();
    private List<String> mListCom = new ArrayList<>();
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    private List<BbRankListEntity.DataBean.BrokersbaobeiBean> BBRankList = new ArrayList();
    private RvBbRankAdapter rvBbRankAdapter;
    private LinearLayout ll_static;
    private String aid;
    private boolean isCom;
    private List<LoupanFilterEntity.DataBean.LoupansBean> loupans = new ArrayList<>();
    private TextView textView;
    private RelativeLayout rlSuc;
    private RelativeLayout rlErr;
    private int spCount;
    private int total;//当前楼盘统计的数据总数，设置位成员变量为了方便记录

    @Override
    public int getLayoutId() {
        return R.layout.activity_bbstatics_for_jjcom;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);

        aid = "";

        rvBbRank = new RecyclerView(this);
        pgBbCompany = new PieGraphView(this, null);
        List<ViewGroup> containerViews = new ArrayList<>();

        mListJingjiren.add("全部报备");
        mListJingjiren.add("本月");
        mListJingjiren.add("本周");
        mList.addAll(mListJingjiren);//刚进入时用经纪人的spannar列表
        mListCom.add("全部报备");
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spDate.setAdapter(arrayAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, VERTICAL, false);
        rvBbRankAdapter = new RvBbRankAdapter(mContext, BBRankList);
        rvBbRank.setLayoutManager(linearLayoutManager);
        rvBbRank.setAdapter(rvBbRankAdapter);
        rvBbRank.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));


        RelativeLayout ll = new RelativeLayout(this);
        //饼状图
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewUtils.dip2px(210), ViewUtils.dip2px(210));
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ll.addView(pgBbCompany, lp);

        //当数量为0时显示的文字
        RelativeLayout.LayoutParams lp_text = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_text.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView = new TextView(this);
        textView.setText("此楼盘没有收到过报备");
        textView.setTextSize(16);
        textView.setVisibility(View.GONE);
        ll.addView(textView, lp_text);
        //统计数据
        ll_static = (LinearLayout) this.getLayoutInflater().inflate(R.layout.layout_static, null);
        RelativeLayout.LayoutParams lp_static = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_static.addRule(RelativeLayout.BELOW, pgBbCompany.getId());
        lp_static.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp_static.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        tvSuc = (TextView) ll_static.findViewById(R.id.tv_bb_suc_right);
        tvErr = (TextView) ll_static.findViewById(R.id.tv_bb_erro_right);
        tvMax = (TextView) ll_static.findViewById(R.id.tv_bb_most_right);
        tvTotal = (TextView) ll_static.findViewById(R.id.tv_bb_total_right);
        //用来点击
        rlSuc = (RelativeLayout) ll_static.findViewById(R.id.rl_suc);
        rlErr = (RelativeLayout) ll_static.findViewById(R.id.rl_err);

        ll.addView(ll_static, lp_static);
        containerViews.add(rvBbRank);
        containerViews.add(ll);
        VpRecyclerViewAdapter vpStarBrowseAdapter = new VpRecyclerViewAdapter(mContext, containerViews);
        vpContainer.setAdapter(vpStarBrowseAdapter);


    }

    @Override
    public void initLayoutParams() {

    }

    public void updataJingjiren(String type) {
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
//                        BbRankListEntity.DataBean.BrokersbaobeiBean agentbaobeiBean = new BbRankListEntity.DataBean.BrokersbaobeiBean(agent.getMid(), "公司报备", agent.getSuccess(), agent.getImage());
                        List<BbRankListEntity.DataBean.BrokersbaobeiBean> brokersbaobei = bbRankListEntity.getData().getBrokersbaobei();
//                        brokersbaobei.add(0, agentbaobeiBean);
                        BBRankList.addAll(brokersbaobei);
                        Log.e("TAG", "brokersbaobei" + brokersbaobei.size());
                        rvBbRankAdapter.updata(brokersbaobei);
                    }
                });
    }

    private void updataCom() {
        Log.i("static_test", "updataCom()__aid = " + aid);
        OkHttpUtils.get()
                .url(API.BBCOMPANY)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("aid", aid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "response" + response);
                        BbStaticForComEntity bbStaticForComEntity = new Gson().fromJson(response, BbStaticForComEntity.class);
                        if (bbStaticForComEntity.getCode() == 200) {
                            BbStaticForComEntity.DataBean dataBean = bbStaticForComEntity.getData();
                            total = dataBean.getTotal();
                            int successNum = dataBean.getSuccess();
                            int failureNum = dataBean.getFailure();
                            int maxNum = dataBean.getNum();
                            String maxSubject = dataBean.getMaxsubject();
                            Log.i("static_test", "onResponse()__successNum = " + successNum + "  failureNum = " + failureNum + "  maxNum=" + maxNum + "  ");
                            if (total == 0) {
                                ll_static.setVisibility(View.GONE);
                                pgBbCompany.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
//                                setPieView(0, 0, 0);//设置饼状图数据
                                tvSuc.setText(successNum + "次                      " + 0 + "%");
                                tvErr.setText(failureNum + "次                      " + 0 + "%");
                                tvMax.setText(maxNum + "次");
                                tvTotal.setText(maxSubject + "                      " + total + "次");
                            } else {
                                ll_static.setVisibility(View.VISIBLE);
                                pgBbCompany.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);
                                setPieView(total, successNum * 100 / total, failureNum * 100 / total);//设置饼状图数据
                                //设置列表数据
                                tvSuc.setText(successNum + "次                      " + successNum * 100 / total + "%");
                                tvErr.setText(failureNum + "次                      " + failureNum * 100 / total + "%");
                                tvMax.setText(maxSubject + "                      " + maxNum + "次");
                                tvTotal.setText("                      " + total + "次");
                            }

                        } else {
                            Toast.makeText(BBStaticsForJJComActivity.this, "" + bbStaticForComEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    private void setPieView(int total, int successNum, int failureNum) {
        PieGraphView.ItemGroup itemGroup = new PieGraphView.ItemGroup();
        PieGraphView.Item item = new PieGraphView.Item();
        item.color = 0x9993b0cf;
        item.value = successNum;
        item.id = "带看成功:" + successNum + "%";
        PieGraphView.Item item1 = new PieGraphView.Item();
        item1.color = 0x99c56ea7;
        item1.value = failureNum;
        item1.id = "带看失败:" + failureNum + "%";
        itemGroup.items = new PieGraphView.Item[2];
        itemGroup.items[0] = item;
        itemGroup.items[1] = item1;
        itemGroup.id = "报备统计";
        PieGraphView.ItemGroup[] itemGroups = new PieGraphView.ItemGroup[1];
        itemGroups[0] = itemGroup;
        pgBbCompany.setData(itemGroups);
    }

    @Override
    public void initData() {
        initLoupan();
        updataJingjiren(type);
        updataCom();
    }

    private void initLoupan() {
        OkHttpUtils.get()
                .url(API.FILTER_LOUPAN)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "response" + response);
                        LoupanFilterEntity loupanFilterEntity = new Gson().fromJson(response, LoupanFilterEntity.class);
                        if (loupanFilterEntity.getCode() == 200) {
                            LoupanFilterEntity.DataBean dataBean = loupanFilterEntity.getData();
                            loupans = dataBean.getLoupans();
                            for (LoupanFilterEntity.DataBean.LoupansBean loupan : loupans) {
                                mListCom.add(loupan.getSubject());
                            }
                        } else {
                            Toast.makeText(BBStaticsForJJComActivity.this, "" + loupanFilterEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    public void bindListener() {
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!isCom) {
                    if (position == 0) {
                        type = "all";
                        tvContentTitle.setText("总排行榜");
                    } else if (position == 1) {
                        type = "month";
                        tvContentTitle.setText("月排行榜");
                    } else if (position == 2) {
                        type = "week";
                        tvContentTitle.setText("周排行榜");
                    }
                    updataJingjiren(type);
                } else {
                    spCount = position;
                    Log.i(TAG, "onItemSelected()__total = "+total);
                    if (total == 0) {
                        pgBbCompany.setVisibility(View.GONE);
                    }
                    tvContentTitle.setText(mListCom.get(position));
                    if (position == 0) {
                        Log.i("static_test", "onItemSelected()__position == 0");
                        aid = "";
                    } else {
                        Log.i("static_test", "onItemSelected()__position = " + position);
                        aid = String.valueOf(loupans.get(position - 1).getAid());
                        Log.i("static_test", "onItemSelected()__aid = " + aid);
                    }
                    updataCom();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "all";
                aid = "";
            }
        });

        rbJingjiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpContainer.setCurrentItem(0);
            }
        });

        rbCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpContainer.setCurrentItem(1);
            }
        });

        vpContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mList.clear();
                if (position == 0) {
                    textView2.setVisibility(View.VISIBLE);
                    rbJingjiren.setChecked(true);
                    isCom = false;

                    mList.addAll(mListJingjiren);

                    if (position == 0) {
                        type = "all";
                        tvContentTitle.setText("总排行榜");
                    } else if (position == 1) {
                        type = "month";
                        tvContentTitle.setText("月排行榜");
                    } else if (position == 2) {
                        type = "week";
                        tvContentTitle.setText("周排行榜");
                    }
//                    currentAdapter = rvOwnAdapter;
                } else if (position == 1) {
                    if (total == 0) {
                        pgBbCompany.setVisibility(View.GONE);
                    }
                    textView2.setVisibility(View.GONE);
                    Log.i(TAG, "onPageSelected()__");
                    spDate.setSelection(spCount);
                    tvContentTitle.setText(mListCom.get(spCount));
                    rbCompany.setChecked(true);
                    if (total!= 0) {
                        pgBbCompany.showOutGroup(0);
                    }

                    isCom = true;

                    mList.addAll(mListCom);
//                    currentAdapter = rvAddAdapter;
                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rlSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        rlErr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

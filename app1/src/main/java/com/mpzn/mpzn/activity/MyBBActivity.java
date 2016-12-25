package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.ListViewsVPAdapter;
import com.mpzn.mpzn.adapter.MyBBlvadapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.MyBBEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.SmoothListView.SmoothListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Icy on 2016/9/27.
 */

public class MyBBActivity extends BaseActivity {
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.tab_vp_my_bb)
    TabLayout tabVpMyBb;
    @Bind(R.id.vp_my_bb)
    ViewPager vpMyBb;
    @Bind(R.id.et_search)
    EditText etSearch;

    public static final int STOP_REFRESH = 0;

    private ListViewsVPAdapter myBBViewPagerAdapter;
    private SmoothListView lv_allBB;
    private SmoothListView lv_check_suc;
    private SmoothListView lv_check_err;

    private MyBBlvadapter lv_allBBAdapter;
    private MyBBlvadapter lv_check_sucAdapter;
    private MyBBlvadapter lv_check_errAdapter;

    List<MyBBEntity.DataBean> datalist = new ArrayList<>();
    List<MyBBEntity.DataBean> checklist = new ArrayList<>();
    List<MyBBEntity.DataBean> unchecklist = new ArrayList<>();

    private MyBBlvadapter currentAdapter;
    List<MyBBEntity.DataBean> currentlist;
    List<MyBBEntity.DataBean> tmplist = new ArrayList<>();

    private int newsOffsetAll = 0;
    private int newsOffsetSuc = 0;
    private int newsOffsetErr = 0;


    private android.os.Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  STOP_REFRESH:
                    lv_allBB.stopRefresh();
                    lv_check_suc.stopRefresh();
                    lv_check_err.stopRefresh();
                    break;
            }
        }
    };
    private String keyWords = "";
    private String currentChecked = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bb;
    }


    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("我的报备", R.drawable.return_gray, 0);

        lv_allBB = new SmoothListView(mContext);
        lv_allBB.setRefreshEnable(true);
        lv_allBB.setLoadMoreEnable(true);

        lv_check_suc = new SmoothListView(mContext);
        lv_check_suc.setRefreshEnable(true);
        lv_check_suc.setLoadMoreEnable(true);

        lv_check_err = new SmoothListView(mContext);
        lv_check_err.setRefreshEnable(true);
        lv_check_err.setLoadMoreEnable(true);

        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv_allBB.setLayoutParams(layoutParams);
        lv_check_suc.setLayoutParams(layoutParams);
        lv_check_err.setLayoutParams(layoutParams);

        lv_allBBAdapter = new MyBBlvadapter(mContext, datalist);
        lv_check_sucAdapter = new MyBBlvadapter(mContext, checklist);
        lv_check_errAdapter = new MyBBlvadapter(mContext, unchecklist);

        lv_allBB.setAdapter(lv_allBBAdapter);
        lv_check_suc.setAdapter(lv_check_sucAdapter);
        lv_check_err.setAdapter(lv_check_errAdapter);


        List<ListView> lv_list = new ArrayList<>();
        lv_list.add(lv_allBB);
        lv_list.add(lv_check_suc);
        lv_list.add(lv_check_err);

        List<String> tab_title = new ArrayList<>();
        tab_title.add("所有报备");
        tab_title.add("已核验");
        tab_title.add("未核验");


        myBBViewPagerAdapter = new ListViewsVPAdapter(mContext, lv_list, tab_title);
        vpMyBb.setAdapter(myBBViewPagerAdapter);
        tabVpMyBb.setupWithViewPager(vpMyBb);


    }

    @Override
    public void initLayoutParams() {

    }

    public void getdata(final String ischecked, final int offset) {
        Log.i("fenye", "getdata()__");
        OkHttpUtils.get()
                .url(API.MYBB_LIST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", ischecked)
                .addParams("offset", String.valueOf(offset))
                .addParams("keywords", keyWords)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();
                        lv_allBB.stopLoadMore();
                        lv_allBB.stopRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        int offSetTemp;
                        MyBBEntity myBBEntity = new Gson().fromJson(response, MyBBEntity.class);
                        if (myBBEntity.getCode() == 200) {
                            if (!"".equals(keyWords)) {
                                //有搜索内容
                                tmplist.addAll(myBBEntity.getData());
                                currentAdapter.updata(tmplist);
                            }

                            if (ischecked == "") {
                                //解析数据
                                if (offset > newsOffsetAll) {
                                    //加载更多
                                    //具体的一页多少条，都是有服务器端决定的，这里只负责加载更多即可
                                    Log.i("fenye", "onResponse()__分页___newsOffsetAll = "+newsOffsetAll+"      newsOffsetSuc = "+newsOffsetSuc+
                                            "       newsOffsetErr = "+newsOffsetErr);

                                    lv_allBB.stopLoadMore();
                                    if (myBBEntity.getData().size() != 0) {
                                        newsOffsetAll += 1;

                                    } else {
                                        Toast.makeText(MyBBActivity.this, "已加载全部内容", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    //刷新
                                    datalist.clear();
                                    handler.sendEmptyMessageDelayed(STOP_REFRESH,2800);
                                }
                                datalist.addAll(myBBEntity.getData());
                                lv_allBBAdapter.updata(datalist);
                            } else if (ischecked == "checked") {
                                if (offset > newsOffsetSuc) {
                                //加载更多
                                //具体的一页多少条，都是有服务器端决定的，这里只负责加载更多即可
                                Log.i("fenye", "onResponse()__分页___newsOffsetAll = "+newsOffsetAll+"      newsOffsetSuc = "+newsOffsetSuc+
                                        "       newsOffsetErr = "+newsOffsetErr);

                                lv_check_suc.stopLoadMore();
                                if (myBBEntity.getData().size() != 0) {
                                    newsOffsetSuc += 1;

                                } else {
                                    Toast.makeText(MyBBActivity.this, "已加载全部内容", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //刷新
                                    checklist.clear();
                                handler.sendEmptyMessageDelayed(STOP_REFRESH,2800);
                            }
                                checklist.addAll(myBBEntity.getData());
                                lv_check_sucAdapter.updata(checklist);

                            } else if (ischecked == "unchecked") {
                                if (offset > newsOffsetErr) {
                                    //加载更多
                                    //具体的一页多少条，都是有服务器端决定的，这里只负责加载更多即可
                                    Log.i("fenye", "onResponse()__分页___newsOffsetAll = "+newsOffsetAll+"      newsOffsetSuc = "+newsOffsetSuc+
                                            "       newsOffsetErr = "+newsOffsetErr);

                                    lv_check_err.stopLoadMore();
                                    if (myBBEntity.getData().size() != 0) {
                                        newsOffsetErr += 1;

                                    } else {
                                        Toast.makeText(MyBBActivity.this, "已加载全部内容", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //刷新

                                    unchecklist.clear();
                                    handler.sendEmptyMessageDelayed(STOP_REFRESH,2500);
                                }
                                unchecklist.addAll(myBBEntity.getData());
                                lv_check_errAdapter.updata(unchecklist);

                            }

                            Log.e("TAG", "报备数量" + ischecked + lv_allBBAdapter.getData().size());
                        }

                    }
                });


    }



    @Override
    public void initData() {

        getdata("", 0);
        getdata("checked", 0);
        getdata("unchecked", 0);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);

        vpMyBb.setCurrentItem(type);
        chooseCurrent(type);


    }
    private void chooseCurrent(int position){
        etSearch.setText("");
        switch (position) {
            case  0:
                currentAdapter=lv_allBBAdapter;
                currentlist=datalist;
                currentChecked = "";
                break;
            case  1:
                currentAdapter=lv_check_sucAdapter;
                currentlist=checklist;
                currentChecked = "checked";
                break;
            case  2:
                currentAdapter=lv_check_errAdapter;
                currentlist=unchecklist;
                currentChecked = "unchecked";
                break;
        }

    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(null, null);

        vpMyBb.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                chooseCurrent(position);

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("fenye", "onTextChanged()__");
                tmplist.clear();
//                if(s.length()==0){
//                    tmplist.addAll(currentlist);
//                }else{
//                    for(MyBBEntity.DataBean data:currentlist ){
//                        if(data.getKhname().contains(s.toString())||data.getSubject().contains(s.toString())){
//                            tmplist.add(data);
//                        }
//                    }
                    keyWords = s.toString();
                    getdata(currentChecked, 0);
//                }






            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv_allBB.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {

            @Override
            public void onRefresh() {
                newsOffsetAll = 0;

                    getdata("", 0);

            }

            @Override
            public void onLoadMore() {
                Log.i("fenye", "onLoadMore()__");
                getdata("", newsOffsetAll + 1);
            }
        });

        lv_check_suc.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {

                    getdata("checked", 0);

            }

            @Override
            public void onLoadMore() {
                getdata("checked", newsOffsetSuc + 1);
            }
        });

        lv_check_err.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                newsOffsetErr = 0;
                    getdata("unchecked", 0);
            }

            @Override
            public void onLoadMore() {
                getdata("unchecked", newsOffsetErr + 1);
            }
        });

//        etSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, BBSearchActivity.class);
//                startActivity(intent);
//            }
//        });

    }



}

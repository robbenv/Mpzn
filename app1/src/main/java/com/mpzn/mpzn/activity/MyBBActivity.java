package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

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


    private ListViewsVPAdapter myBBViewPagerAdapter;
    private ListView lv_allBB;
    private ListView lv_check_suc;
    private ListView lv_check_err;

    private MyBBlvadapter lv_allBBAdapter;
    private MyBBlvadapter lv_check_sucAdapter;
    private MyBBlvadapter lv_check_errAdapter;

    List<MyBBEntity.DataBean> datalist = new ArrayList<>();
    List<MyBBEntity.DataBean> checklist = new ArrayList<>();
    List<MyBBEntity.DataBean> unchecklist = new ArrayList<>();

    private MyBBlvadapter currentAdapter;
    List<MyBBEntity.DataBean> currentlist;
    List<MyBBEntity.DataBean> tmplist = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bb;
    }


    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("我的报备", R.drawable.return_gray, 0);

        lv_allBB = new ListView(mContext);
        lv_check_suc = new ListView(mContext);
        lv_check_err = new ListView(mContext);

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

    public void getdata(final String ischecked) {
        OkHttpUtils.get()
                .url(API.MYBB_LIST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", ischecked)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyBBEntity myBBEntity = new Gson().fromJson(response, MyBBEntity.class);
                        if (myBBEntity.getCode() == 200) {
                            if (ischecked == "") {
                                datalist.addAll(myBBEntity.getData());
                                lv_allBBAdapter.updata(datalist);

                            } else if (ischecked == "checked") {
                                checklist.addAll(myBBEntity.getData());
                                lv_check_sucAdapter.updata(checklist);
                            } else if (ischecked == "unchecked") {
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

        getdata("");
        getdata("checked");
        getdata("unchecked");

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
                break;
            case  1:
                currentAdapter=lv_check_sucAdapter;
                currentlist=checklist;
                break;
            case  2:
                currentAdapter=lv_check_errAdapter;
                currentlist=unchecklist;
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
                tmplist.clear();
                if(s.length()==0){
                    tmplist.addAll(currentlist);
                }else if(StringUtils.isContainChinese(s.toString())){
                    for(MyBBEntity.DataBean data:currentlist ){
                        if(data.getKhname().contains(s.toString())||data.getSubject().contains(s.toString())){
                            tmplist.add(data);
                        }
                    }
                }else{
                    for(MyBBEntity.DataBean data:currentlist ){
                        if(data.getKhphone().contains(s.toString())){
                            tmplist.add(data);
                        }
                    }
                }


                currentAdapter.updata(tmplist);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



}

package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.ListViewsVPAdapter;
import com.mpzn.mpzn.adapter.MySelllvadapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.MyBBEntity;
import com.mpzn.mpzn.entity.MySellListEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MySellActivity extends BaseActivity {


    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.tab_vp_my_sell)
    TabLayout tabVpMySell;
    @Bind(R.id.vp_my_sell)
    ViewPager vpMySell;
    @Bind(R.id.et_search)
    EditText etSearch;


    private ListViewsVPAdapter myBBViewPagerAdapter;
    private ListView lv_allSell;
    private ListView lv_check_suc;
    private ListView lv_check_ing;
    private ListView lv_check_err;

    private MySelllvadapter lv_allSellAdapter;
    private MySelllvadapter lv_check_sucAdapter;
    private MySelllvadapter lv_check_ingAdapter;
    private MySelllvadapter lv_check_errAdapter;

    List<MySellListEntity.DataBean> datalist = new ArrayList<>();
    List<MySellListEntity.DataBean> checklist = new ArrayList<>();
    List<MySellListEntity.DataBean> checkinglist = new ArrayList<>();
    List<MySellListEntity.DataBean> unchecklist = new ArrayList<>();

    private MySelllvadapter currentAdapter;
    List<MySellListEntity.DataBean> currentlist;
    List<MySellListEntity.DataBean> tmplist = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_sell;
    }


    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("我的代销", R.drawable.return_gray, 0);
        acitonBar.setRightText("申请代销");
        acitonBar.setRightTextColor(R.color.font_black_4);

        lv_allSell = new ListView(mContext);
        lv_check_suc = new ListView(mContext);
        lv_check_ing = new ListView(mContext);
        lv_check_err = new ListView(mContext);

        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv_allSell.setLayoutParams(layoutParams);
        lv_check_suc.setLayoutParams(layoutParams);
        lv_check_ing.setLayoutParams(layoutParams);
        lv_check_err.setLayoutParams(layoutParams);

        lv_allSellAdapter = new MySelllvadapter(mContext, datalist);
        lv_check_sucAdapter = new MySelllvadapter(mContext, checklist);
        lv_check_ingAdapter = new MySelllvadapter(mContext, checkinglist);
        lv_check_errAdapter = new MySelllvadapter(mContext, unchecklist);

        lv_allSell.setAdapter(lv_allSellAdapter);
        lv_check_suc.setAdapter(lv_check_sucAdapter);
        lv_check_ing.setAdapter(lv_check_ingAdapter);
        lv_check_err.setAdapter(lv_check_errAdapter);


        List<ListView> lv_list = new ArrayList<>();
        lv_list.add(lv_allSell);
        lv_list.add(lv_check_suc);
        lv_list.add(lv_check_ing);
        lv_list.add(lv_check_err);

        List<String> tab_title = new ArrayList<>();
        tab_title.add("全部申请");
        tab_title.add("已通过");
        tab_title.add("审核中");
        tab_title.add("暂未通过");


        myBBViewPagerAdapter = new ListViewsVPAdapter(mContext, lv_list, tab_title);
        vpMySell.setAdapter(myBBViewPagerAdapter);
        tabVpMySell.setupWithViewPager(vpMySell);


    }

    @Override
    public void initLayoutParams() {

    }

    public void getdata(final String isChecked, final String isSuccess) {
        OkHttpUtils.get()
                .url(API.MYSELL_LIST_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", isChecked)
                .addParams("status", isSuccess)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MySellListEntity mySellListEntity = new Gson().fromJson(response, MySellListEntity.class);
                        if (mySellListEntity.getCode() == 200) {
                            if (isChecked == "" && isSuccess == "") {
                                datalist.addAll(mySellListEntity.getData());
                                lv_allSellAdapter.updata(datalist);
                            } else if (isChecked == "" && isSuccess == "success") {
                                checklist.addAll(mySellListEntity.getData());
                                lv_check_sucAdapter.updata(checklist);
                            } else if (isChecked == "unchecked" && isSuccess == "") {
                                checkinglist.addAll(mySellListEntity.getData());
                                lv_check_ingAdapter.updata(checkinglist);
                            } else if (isChecked == "checked" && isSuccess == "failure") {
                                unchecklist.addAll(mySellListEntity.getData());
                                lv_check_errAdapter.updata(unchecklist);
                            }

                            Log.e("TAG", "代销数量---" + "isChecked-" + isChecked + "---isSuccess-" + isSuccess + mySellListEntity.getData().size());
                        }

                    }
                });


    }

    @Override
    public void initData() {

        getdata("", "");
        getdata("", "success");
        getdata("unchecked", "");
        getdata("checked", "failure");

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);

        vpMySell.setCurrentItem(type);
        chooseCurrent(type);



    }

    private void chooseCurrent(int position){
        etSearch.setText("");
        switch (position) {
            case  0:
                currentAdapter=lv_allSellAdapter;
                currentlist=datalist;
                break;
            case  1:
                currentAdapter=lv_check_sucAdapter;
                currentlist=checklist;
                break;
            case  2:
                currentAdapter=lv_check_ingAdapter;
                currentlist=checkinglist;
                break;
            case  3:
                currentAdapter=lv_check_errAdapter;
                currentlist=unchecklist;
                break;
        }
    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ApplyForSellActivity.class);
                startActivity(intent);
            }
        });
        vpMySell.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
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
                if (s.length() == 0) {
                    tmplist.addAll(currentlist);
                } else  {
                    for (MySellListEntity.DataBean data : currentlist) {
                        if (data.getKfsname().contains(s.toString()) || data.getSubject().contains(s.toString())) {
                            tmplist.add(data);
                        }
                    }
                }
                Log.e("TAG", "tmplist"+tmplist.size());
                currentAdapter.updata(tmplist);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


}

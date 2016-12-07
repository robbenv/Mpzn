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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.ListViewsVPAdapter;
import com.mpzn.mpzn.adapter.ProxySellManageAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.ProxySellListEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.CustomViewPager;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

/**
 * Created by larry on 16-11-18.
 */

public class ProxySellManageActivity extends BaseActivity {


    public static final int CURRENT_ITEM_UNCHECK = 0;
    public static final int CURRENT_ITEM_SUCCESS = 1;
    public static final int CURRENT_ITEM_FAIL = 2;
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.tab_vp_proxy_sell)
    TabLayout tabVpProxySell;

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_select_all)
    TextView tvSelectAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;
    @Bind(R.id.rl_select_all)
    RelativeLayout rlSelectAll;
    @Bind(R.id.btn_refuse)
    Button btnRefuse;
    @Bind(R.id.btn_access)
    Button btnAccess;
    @Bind(R.id.rl_with_vp)
    RelativeLayout rlWithVp;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.vp_proxy_sell)
    CustomViewPager vpProxySell;

    private ListView lv_allSell;
    private ListView lv_check_suc;
    private ListView lv_check_ing;
    private ListView lv_check_err;
    //    private ArrayList<ProxySellListEntity.DataBean> allList = new ArrayList<>();
    private ArrayList<ProxySellListEntity.DataBean> successList = new ArrayList<>();
    private ArrayList<ProxySellListEntity.DataBean> uncheckList = new ArrayList<>();
    private ArrayList<ProxySellListEntity.DataBean> failList = new ArrayList<>();
    List<ProxySellListEntity.DataBean> currentlist;
    List<ProxySellListEntity.DataBean> tmplist = new ArrayList<>();


    //    private ProxySellManageAdapter lv_allSellAdapter;
    private ProxySellManageAdapter lv_check_sucAdapter;
    private ProxySellManageAdapter lv_unCheck_Adapter;
    private ProxySellManageAdapter lv_check_errAdapter;
    private ListViewsVPAdapter proxyViewPagerAdapter;
    private ProxySellManageAdapter  currentAdapter;
    private int currentItem = CURRENT_ITEM_UNCHECK;
    private boolean isEditor;
    private KProgressHUD loadProgressHUD;
    private View.OnClickListener clickListener;


    @Override
    public int getLayoutId() {
        return R.layout.activity_psmanage;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("代销管理", R.drawable.return_gray, 0);
        acitonBar.setRightText("管理");
        acitonBar.setRightTextColor(R.color.font_black_4);

//        lv_allSell = new ListView(mContext);
        lv_check_ing = new ListView(mContext);
        lv_check_suc = new ListView(mContext);
        lv_check_err = new ListView(mContext);

        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        lv_allSell.setLayoutParams(layoutParams);
        lv_check_ing.setLayoutParams(layoutParams);
        lv_check_suc.setLayoutParams(layoutParams);
        lv_check_err.setLayoutParams(layoutParams);

//        lv_allSellAdapter = new ProxySellManageAdapter(mContext, allList);
        lv_unCheck_Adapter = new ProxySellManageAdapter(mContext, uncheckList);
        lv_check_sucAdapter = new ProxySellManageAdapter(mContext, successList);
        lv_check_errAdapter = new ProxySellManageAdapter(mContext, failList);
//
//        lv_allSell.setAdapter(lv_allSellAdapter);
        lv_check_ing.setAdapter(lv_unCheck_Adapter);
        lv_check_suc.setAdapter(lv_check_sucAdapter);
        lv_check_err.setAdapter(lv_check_errAdapter);

        List<ListView> lv_list = new ArrayList<>();
//        lv_list.add(lv_allSell);
        lv_list.add(lv_check_ing);
        lv_list.add(lv_check_suc);
        lv_list.add(lv_check_err);

        List<String> tab_title = new ArrayList<>();
        tab_title.add("最新申请");
        tab_title.add("已通过");
        tab_title.add("未通过");

        proxyViewPagerAdapter = new ListViewsVPAdapter(mContext, lv_list, tab_title);
        vpProxySell.setAdapter(proxyViewPagerAdapter);
        tabVpProxySell.setupWithViewPager(vpProxySell);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
//        getdata("", "");//全部
        getdata("", "success");//成功
        getdata("checked", "failure");//失败
        getdata("unchecked", "");//未审核

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        vpProxySell.setCurrentItem(CURRENT_ITEM_UNCHECK);

//        vpMySell.setCurrentItem(type);
        chooseCurrent(type);

        isEditor = false;
    }

    private void chooseCurrent(int position) {
        etSearch.setText("");
        switch (position) {

            case CURRENT_ITEM_UNCHECK:
                currentAdapter = lv_unCheck_Adapter;
                currentlist = uncheckList;
//                acitonBar.setRightBtVisible(true);不起作用
                acitonBar.setRightText("管理");
                break;
            case CURRENT_ITEM_SUCCESS:
                currentAdapter = lv_check_sucAdapter;
                currentlist = successList;
//                acitonBar.setRightBtVisible(false);不起作用
                acitonBar.setRightText("");

                Log.i("search1", "chooseCurrent()__GONE1");
                break;
            case CURRENT_ITEM_FAIL:
                currentAdapter = lv_check_errAdapter;
                currentlist = failList;
                acitonBar.setRightText("");
//                acitonBar.setRightBtVisible(false);不起作用
                break;
        }
    }

    private void getdata(final String isChecked, final String isSuccess) {
        OkHttpUtils.get()
                .url(API.PROXY_SELL_GET)
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
                        ProxySellListEntity proxySellListEntity = new Gson().fromJson(response, ProxySellListEntity.class);
                        if (proxySellListEntity.getCode() == 200) {
                            if (isSuccess == "success") {
                                successList.addAll(proxySellListEntity.getData());
                                lv_check_sucAdapter.updata(successList);

                            } else if (isChecked == "unchecked" && isSuccess == "") {
                                Log.i("Proxy_test4", "onResponse()__uncheck");
                                uncheckList.addAll(proxySellListEntity.getData());
                                lv_unCheck_Adapter.updata(uncheckList);
                            } else if (isSuccess == "failure") {
                                failList.addAll(proxySellListEntity.getData());
                                lv_check_errAdapter.updata(failList);
                            }


                        }

                    }
                });
    }

    @Override
    public void
    bindListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("fenye", "onTextChanged()__");
                tmplist.clear();
                if (charSequence.length() == 0) {
                    tmplist.addAll(currentlist);
                } else  {
                    for (ProxySellListEntity.DataBean data : currentlist) {
                        if (data.getCompany().contains(charSequence.toString()) || data.getSubject().contains(charSequence.toString())) {
                            tmplist.add(data);
                        }
                    }
                }
                Log.e("TAG", "tmplist"+tmplist.size());
                currentAdapter.updata(tmplist);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        acitonBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAdapter != lv_unCheck_Adapter) {
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(mContext, HandleProxySellActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                overridePendingTransition(0, 0);
//                intent.putExtra("unchecklist", uncheckList);
                startActivityForResult(intent, 0);

//                if (isEditor) {
//                    isEditor = false;
//                    acitonBar.setRightText("编辑");
//                    rlSelectAll.setVisibility(View.GONE);
//                    rlWithVp.setVisibility(View.GONE);
//                    vpProxySell.setScanScroll(true);
////                    tabVpProxySell.setEnabled(false);
//                    currentAdapter.changeEditable(false, false);
//                } else {
//                    isEditor = true;
//                    acitonBar.setRightText("完成1");
//                    Log.i("Proxy_test", "更改前_onClick()__currentItem = " + currentItem);
//                    if (currentItem != CURRENT_ITEM_UNCHECK) {
//                        vpProxySell.setCurrentItem(CURRENT_ITEM_UNCHECK, true);
////                        chooseCurrent(CURRENT_ITEM_UNCHECK);
//                    } else {
//                        vpProxySell.setScanScroll(false);//禁止滑动
////                    tabVpProxySell.setEnabled(false);
//                        rlSelectAll.setVisibility(View.VISIBLE);
//                        rlWithVp.setVisibility(View.VISIBLE);
//                        Log.i("Proxy_test", "onClick()__currentAdapter = " + currentAdapter.toString());
//                        Log.i("Proxy_test", "更改后_onClick()__currentItem = " + currentItem);
//                        currentAdapter.changeEditable(true, false);
//                    }
//
//                }


            }
        });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentAdapter.changeEditable(true, true);
                    tvSelectAll.setText("取消全选");
                } else {
                    currentAdapter.changeEditable(true, false);
                    tvSelectAll.setText("全选");
                }
            }
        });


        vpProxySell.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                chooseCurrent(position);

//                if (isEditor) {
//                    vpProxySell.setScanScroll(false);//禁止滑动
////                    tabVpProxySell.setEnabled(false);
//                    rlSelectAll.setVisibility(View.VISIBLE);
//                    rlWithVp.setVisibility(View.VISIBLE);
//                    Log.i("Proxy_test", "onPageSelected()__currentAdapter = " + currentAdapter.toString());
//                    Log.i("Proxy_test", "更改后onPageSelected()__currentItem = " + currentItem);
////                    currentAdapter.changeEditable(true, false);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        lv_check_ing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProxySellListEntity.DataBean data = uncheckList.get(position);
                if(data.isNoData()){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext,DetailNewhouseActivity.class);
                intent.putExtra("Aid",data.getAid());
                intent.putExtra("Name",data.getSubject());
                startActivity(intent);
            }
        });

        lv_check_suc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProxySellListEntity.DataBean data = successList.get(position);
                if(data.isNoData()){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext,DetailNewhouseActivity.class);
                intent.putExtra("Aid",data.getAid());
                intent.putExtra("Name",data.getSubject());
                startActivity(intent);
            }
        });

        lv_check_err.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProxySellListEntity.DataBean data = failList.get(position);
                if(data.isNoData()){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext,DetailNewhouseActivity.class);
                intent.putExtra("Aid",data.getAid());
                intent.putExtra("Name",data.getSubject());
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 0:
                Log.i("Proxy_test4", "onActivityResult()__into");
                uncheckList.clear();
                getdata("unchecked", "");//未审核

                successList.clear();
                getdata("", "success");//成功

                failList.clear();
                getdata("checked", "failure");//失败
                break;
            default:
                break;
        }
    }




}

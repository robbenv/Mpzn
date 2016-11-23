package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
                break;
            case CURRENT_ITEM_SUCCESS:
                currentAdapter = lv_check_sucAdapter;
                currentlist = successList;
                break;
            case CURRENT_ITEM_FAIL:
                currentAdapter = lv_check_errAdapter;
                currentlist = failList;
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

        acitonBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, HandleProxySellActivity.class);
//                intent.putExtra("unchecklist", uncheckList);
                startActivity(intent);

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

        btnRefuse.setOnClickListener(new SellOnclickListener());
        btnAccess.setOnClickListener(new SellOnclickListener());
        btnDelete.setOnClickListener(new SellOnclickListener());

    }

    class SellOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_access:
                    handleProxySell("checked");
                    break;
                case R.id.btn_refuse:
                    handleProxySell("unchecked");
                    break;
                case R.id.btn_delete:
                    handleProxySell("delete");
                    break;
            }
        }
    }

    private void handleProxySell(String action) {
        String LoadingMsg = null;
        String SuccussMsg = null;
        List<ProxySellListEntity.DataBean> proxysellList = null;
        if ("checked".equals(action)) {
            LoadingMsg = "正在提交审核...";
            SuccussMsg = "代销成功";
//            proxysellList = addJingjirenList;

        } else if ("unchecked".equals(action)) {
            LoadingMsg = "正在驳回申请...";
            SuccussMsg = "驳回申请成功";

        } else if ("delete".equals(action)) {

            LoadingMsg = "正在删除经纪人...";
            SuccussMsg = "删除经纪人成功";
//            jingjirenlist = ownJingjirenList;

        }

        loadProgressHUD = KProgressHUD.create(ProxySellManageActivity.this).
                setSize(MyApplication.mScreenWidth / 4, MyApplication.mScreenWidth / 6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel(LoadingMsg).setCancellable(false).show();

        String str = "";
        final List<ProxySellListEntity.DataBean> selectList = new ArrayList<>();
        for (ProxySellListEntity.DataBean data : uncheckList) {
            if (data.getChecked() == 1) {
                selectList.add(data);
                if (str == "") {
                    str = str + data.getCid();
                } else {
                    str = str + "," + data.getCid();
                }
            }
        }
        if (str == "") {
            loadedDismissProgressDialog(ProxySellManageActivity.this, false, loadProgressHUD, "请选择", false);
        } else {
            final String finalSuccussMsg = SuccussMsg;
            OkHttpUtils.post()
                    .url(API.MANAGEBROKERSLIST_POST)
                    .addParams("token", MyApplication.getInstance().token)
                    .addParams("cids", str)
                    .addParams("action", action)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            loadedDismissProgressDialog(ProxySellManageActivity.this, false, loadProgressHUD, "加载失败，请检查网络", false);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG", "response" + response);
                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                            if (checkStarEntity.getCode() == 200) {
                                loadedDismissProgressDialog(ProxySellManageActivity.this, true, loadProgressHUD, finalSuccussMsg, false);

                                uncheckList.removeAll(selectList);

                                currentAdapter.notifyDataSetChanged();

                            } else {
                                loadedDismissProgressDialog(ProxySellManageActivity.this, false, loadProgressHUD, checkStarEntity.getMessage(), false);

                            }
                        }
                    });
        }
    }

}

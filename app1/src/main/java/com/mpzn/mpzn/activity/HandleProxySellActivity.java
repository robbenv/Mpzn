package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.ProxySellManageAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.ProxySellListEntity;
import com.mpzn.mpzn.http.API;
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
 * Created by larry on 16-11-23.
 */
public class HandleProxySellActivity extends BaseActivity implements ProxySellManageAdapter.ChangCheckListener {
    public static final int ACTION_ACCESS = 4;
    public static final int ACTION_REFUSE = 3;
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.ib_search)
    ImageButton ibSearch;
    @Bind(R.id.rl_et)
    RelativeLayout rlEt;
    @Bind(R.id.btn_refuse)
    Button btnRefuse;
    @Bind(R.id.btn_access)
    Button btnAccess;
    @Bind(R.id.tv_check_all)
    TextView tvCheckAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.iv_load_alert)
    ImageView ivLoadAlert;
    @Bind(R.id.lv_apply)
    ListView lvApply;

    private ProxySellManageAdapter lvAdapter;
    private ArrayList<ProxySellListEntity.DataBean> uncheckList = new ArrayList<>();
    private KProgressHUD loadProgressHUD;
    private int currentBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_handle_sell;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("代销管理", R.drawable.return_gray, 0);
        acitonBar.setRightText("完成");
        acitonBar.setRightTextColor(R.color.font_black_4);

//        searchLvTipsAdapter = new ApplyForSellTipsLvAdapter(mContext, tipsList);
//        lvSearchTips.setAdapter(searchLvTipsAdapter);
        Log.i("Proxy_test1", "onResponse()__uncheckList.size ="+uncheckList.size() );
        lvAdapter = new ProxySellManageAdapter(mContext, uncheckList);
        lvAdapter.changeEditable(true, false);

        lvApply.setAdapter(lvAdapter);
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
//        Intent intent = getIntent();
//        String subject = intent.getStringArrayListExtra()
//        etSearch.setText(subject);
//        getData(currentMaxOffset, subject);
        getData();
    }

    private void getData() {
        OkHttpUtils.get()
                .url(API.PROXY_SELL_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", "unchecked")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ProxySellListEntity proxySellListEntity = new Gson().fromJson(response, ProxySellListEntity.class);
                        Log.e("TAG", proxySellListEntity.getData().toString() + "");
                        if (proxySellListEntity.getCode() == 200) {
                            uncheckList.addAll(proxySellListEntity.getData());
                            Log.i("Proxy_test1", "onResponse()__uncheckList.size ="+uncheckList.size() );
                            lvAdapter.updata(uncheckList);
                            lvAdapter.changeEditable(true, false);
                        }

                    }
                });
    }

    @Override
    public void bindListener() {

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lvAdapter.changeEditable(true, true);
//                    tvCheckAll.setText("取消全选");
                } else {
                    lvAdapter.changeEditable(true, false);
//                    tvCheckAll.setText("全选");
                }
            }
        });

        btnRefuse.setOnClickListener(new HandleProxySellActivity.SellOnclickListener());
        btnAccess.setOnClickListener(new HandleProxySellActivity.SellOnclickListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void ChangCheck(int count) {
        if (currentBtn == ACTION_ACCESS) {
            btnAccess.setText(count+"");
        } else {
            btnRefuse.setText(count+"");
        }
    }

    class SellOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_access:
                    handleProxySell(ACTION_ACCESS);
                    currentBtn = ACTION_ACCESS;
                    break;
                case R.id.btn_refuse:
                    handleProxySell(ACTION_REFUSE);
                    currentBtn = ACTION_REFUSE;
                    break;
//                case R.id.btn_delete:
//                    handleProxySell("delete");
//                    break;
            }
        }
    }

    private void handleProxySell(int action) {
        String LoadingMsg = null;
        String SuccussMsg = null;
        List<ProxySellListEntity.DataBean> proxysellList = null;
        if (action == ACTION_ACCESS) {
            LoadingMsg = "正在提交审核...";
            SuccussMsg = "代销成功";
//            proxysellList = addJingjirenList;

        } else if (action == ACTION_REFUSE) {
            LoadingMsg = "正在驳回申请...";
            SuccussMsg = "驳回申请成功";

        } else if ("delete".equals(action)) {

            LoadingMsg = "正在删除经纪人...";
            SuccussMsg = "删除经纪人成功";
//            jingjirenlist = ownJingjirenList;

        }

        loadProgressHUD = KProgressHUD.create(HandleProxySellActivity.this).
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
            loadedDismissProgressDialog(HandleProxySellActivity.this, false, loadProgressHUD, "请选择", false);
        } else {
            Log.i("Proxy_test", "handleProxySell()__ token = "+ MyApplication.getInstance().token);
            final String finalSuccussMsg = SuccussMsg;
            OkHttpUtils.post()
                    .url(API.PROXY_SELL_HANDLE)
                    .addParams("token", MyApplication.getInstance().token)
                    .addParams("cids", str)
                    .addParams("action", ""+action)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            loadedDismissProgressDialog(HandleProxySellActivity.this, false, loadProgressHUD, "加载失败，请检查网络", false);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG", "response" + response);
                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                            if (checkStarEntity.getCode() == 200) {
                                loadedDismissProgressDialog(HandleProxySellActivity.this, true, loadProgressHUD, finalSuccussMsg, false);
                                Log.i("Proxy_test1", "onResponse()__uncheckList.size ="+uncheckList.size() );
                                uncheckList.removeAll(selectList);
                                lvAdapter.updata(uncheckList);
                                Log.i("Proxy_test1", "onResponse()__uncheckList.size ="+uncheckList.size() );
                                lvAdapter.notifyDataSetChanged();

                            } else {
                                loadedDismissProgressDialog(HandleProxySellActivity.this, false, loadProgressHUD, checkStarEntity.getMessage(), false);

                            }
                        }
                    });
        }
    }
}

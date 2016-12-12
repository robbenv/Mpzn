package com.mpzn.mpzn.activity;

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvCheckJingjirenAdapter;
import com.mpzn.mpzn.adapter.VpRecyclerViewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.CheckjingjirenEntity;
import com.mpzn.mpzn.entity.JingjirenListEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.IsCanScrollViewPager;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class JingjiRenManageActivity extends BaseActivity {


    private static final String TAG = "JingjiRen";
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.rb_own)
    RadioButton rbOwn;
    @Bind(R.id.rb_add)
    RadioButton rbAdd;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.action_bar_edit)
    MyActionBar actionBarEdit;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.tv_delete_all)
    TextView tvDeleteAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;

    @Bind(R.id.vp_own_add)
    IsCanScrollViewPager vpOwnAdd;
    @Bind(R.id.activity_jingjiren_manage)
    RelativeLayout activityJingjirenManage;
    @Bind(R.id.btn_refuse)
    Button btnRefuse;
    @Bind(R.id.btn_access)
    Button btnAccess;
    @Bind(R.id.btn_add)
    Button btnAdd;
    @Bind(R.id.rl_with_vp2)
    RelativeLayout rlWithVp2;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    private RecyclerView rv_own;
    private RecyclerView rv_add;

    private KProgressHUD loadProgressHUD;

    private RvCheckJingjirenAdapter currentAdapter;

    public List<JingjirenListEntity.DataBean> ownJingjirenList = new ArrayList<>();
    private RvCheckJingjirenAdapter rvOwnAdapter;

    public List<JingjirenListEntity.DataBean> addJingjirenList = new ArrayList<>();
    private RvCheckJingjirenAdapter rvAddAdapter;
    private ViewHolder add_dialog;
    private MyDialog dialog;
    private int midForAdd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bbmanage;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        createDialog();
        acitonBar.init("", R.drawable.return_gray, 0);
        acitonBar.setRightText("编辑");
        acitonBar.setRightTextColor(R.color.font_black_4);

        actionBarEdit.init("编辑", 0, 0);
        actionBarEdit.setRightText("完成");
        actionBarEdit.setRightTextColor(R.color.font_black_4);

        rv_own = new RecyclerView(mContext);
        rv_add = new RecyclerView(mContext);


        rvOwnAdapter = new RvCheckJingjirenAdapter(mContext, ownJingjirenList, "旗下");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_own.setLayoutManager(linearLayoutManager);
        rv_own.setAdapter(rvOwnAdapter);

        rvAddAdapter = new RvCheckJingjirenAdapter(mContext, addJingjirenList, "添加");
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_add.setLayoutManager(linearLayoutManager1);
        rv_add.setAdapter(rvAddAdapter);

        List<RecyclerView> recyclerViews = new ArrayList<>();
        recyclerViews.add(rv_own);
        recyclerViews.add(rv_add);

        VpRecyclerViewAdapter vpStarBrowseAdapter = new VpRecyclerViewAdapter(mContext,recyclerViews);
        vpOwnAdd.setAdapter(vpStarBrowseAdapter);

        currentAdapter = rvOwnAdapter;
    }

    private void createDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentView = inflater.inflate(R.layout.layout_add_jingjiren_dialog, null);
        dialog = new MyDialog(mContext);
        dialog.setTitle("添加经纪人");
        dialog.setCommit("添加");
        dialog.btnCommit.setEnabled(false);
        add_dialog = new ViewHolder(contentView);
        dialog.setContentView(contentView);

        add_dialog.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = add_dialog.etPhone.getText().toString();
                if (phone.length() != 11) {
                    add_dialog.tvTips.setText("请检查手机号输入得是否正确");
                } else {
                    OkHttpUtils.get()
                            .url(API.CHECK_ADD_JINGJIREN_GET)
                            .addParams("token", MyApplication.getInstance().token)
                            .addParams("mname", phone)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    add_dialog.tvTips.setText("服务器未响应，该功能暂时不可用");
                                    dialog.btnCommit.setEnabled(false);
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    CheckjingjirenEntity checkEntity = new Gson().fromJson(response, CheckjingjirenEntity.class);
                                    add_dialog.tvTips.setText(checkEntity.getMessage());
                                    if (checkEntity.getCode() == 200) {
                                        dialog.btnCommit.setEnabled(true);
                                        midForAdd = checkEntity.getData().getMid();
                                        String xingming = checkEntity.getData().getXingming();
                                        add_dialog.tvName.setText(xingming);
                                        add_dialog.tvPhone.setText(midForAdd + "");
                                    } else {
                                        dialog.btnCommit.setEnabled(false);
                                        add_dialog.tvName.setText("");
                                        add_dialog.tvPhone.setText("");
                                    }
                                }
                            });

                }
            }
        });
        dialog.setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()
                        .url(API.ADD_JINGJIREN_POST)
                        .addParams("token", MyApplication.getInstance().token)
                        .addParams("broker_id", midForAdd+"")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                if(simpleEntity.getCode()==200){
                                    dialog.dismiss();
                                    Toast.makeText(JingjiRenManageActivity.this, "添加经纪人成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    dialog.btnCommit.setEnabled(false);
                                    add_dialog.tvName.setText("");
                                    add_dialog.tvPhone.setText("");
                                    add_dialog.tvTips.setText(simpleEntity.getMessage());
                                }
                            }
                        });

            }
        });
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

        loadProgressHUD = KProgressHUD.create(this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在同步...").setCancellable(true).show();

        OkHttpUtils.get()
                .url(API.JINGJIREN_OWN_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", "checked")
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JingjirenListEntity jinjirenListEntity = new Gson().fromJson(response, JingjirenListEntity.class);
                        List<JingjirenListEntity.DataBean> jinjirenListEntityData = jinjirenListEntity.getData();
                        if (jinjirenListEntity.getCode() == 200) {
                            ownJingjirenList.addAll(jinjirenListEntityData);
                            rvOwnAdapter.updata(ownJingjirenList);

                            loadedDismissProgressDialog(JingjiRenManageActivity.this, true, loadProgressHUD, "同步成功", false);

                        } else {
                            loadedDismissProgressDialog(JingjiRenManageActivity.this, false, loadProgressHUD, jinjirenListEntity.getMessage(), false);

                        }


                    }
                });

        OkHttpUtils.get()
                .url(API.JINGJIREN_OWN_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("checked", "unchecked")
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JingjirenListEntity jinjirenListEntity = new Gson().fromJson(response, JingjirenListEntity.class);
                        List<JingjirenListEntity.DataBean> jinjirenListEntityData = jinjirenListEntity.getData();
                        if (jinjirenListEntity.getCode() == 200) {
                            addJingjirenList.addAll(jinjirenListEntityData);
                            rvAddAdapter.updata(addJingjirenList);

                            loadedDismissProgressDialog(JingjiRenManageActivity.this, true, loadProgressHUD, "同步成功", false);

                        } else {
                            loadedDismissProgressDialog(JingjiRenManageActivity.this, false, loadProgressHUD, jinjirenListEntity.getMessage(), false);

                        }


                    }
                });


    }

    private void changBtnWithvp(int vpindex) {
        if (vpindex == 0) {
            rlWithVp2.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            rlWithVp2.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (actionBarEdit.getVisibility() == View.GONE) {
                            finish();
                        }
                    }
                }
                ,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionBarEdit.setVisibility(View.VISIBLE);
                        changBtnWithvp(vpOwnAdd.getCurrentItem());
                        tvDeleteAll.setVisibility(View.VISIBLE);
                        cbAll.setVisibility(View.VISIBLE);
                        cbAll.setChecked(false);
                        vpOwnAdd.setNoScroll(true);
                        currentAdapter.changeEditable(true, false);


                    }
                });

        actionBarEdit.setLROnClickListener(null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionBarEdit.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.GONE);
                        rlWithVp2.setVisibility(View.GONE);
                        tvDeleteAll.setVisibility(View.GONE);
                        cbAll.setVisibility(View.GONE);
                        vpOwnAdd.setNoScroll(false);
                        currentAdapter.changeEditable(false, false);


                    }
                });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentAdapter.changeEditable(true, true);
                    tvDeleteAll.setText("取消全选");
                } else {
                    currentAdapter.changeEditable(true, false);
                    tvDeleteAll.setText("全选");
                }
            }
        });

        rbOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpOwnAdd.setCurrentItem(0);
            }
        });
        rbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpOwnAdd.setCurrentItem(1);
            }
        });
        vpOwnAdd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbOwn.setChecked(true);
                    currentAdapter = rvOwnAdapter;
                } else if (position == 1) {
                    rbAdd.setChecked(true);
                    currentAdapter = rvAddAdapter;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick()__btnDelete");
                manageJingjiren("delete");
            }
        });
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageJingjiren("checked");
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageJingjiren("unchecked");
            }
        });

    }

    private void manageJingjiren(String action) {   //管理经纪人 access表示审核是否通过

        String LoadingMsg = null;
        String SuccussMsg = null;
        List<JingjirenListEntity.DataBean> jingjirenlist = null;
        if (action == "checked") {
            LoadingMsg = "正在提交审核...";
            SuccussMsg = "添加经纪人成功";
            jingjirenlist = addJingjirenList;

        } else if (action == "unchecked") {
            LoadingMsg = "正在驳回申请...";
            SuccussMsg = "驳回申请成功";
            jingjirenlist = addJingjirenList;

        } else if (action == "delete") {

            LoadingMsg = "正在删除经纪人...";
            SuccussMsg = "删除经纪人成功";
            jingjirenlist = ownJingjirenList;

        }

        loadProgressHUD = KProgressHUD.create(JingjiRenManageActivity.this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel(LoadingMsg).setCancellable(false).show();

        String str = "";
        final List<JingjirenListEntity.DataBean> deleteList = new ArrayList<>();
        for (JingjirenListEntity.DataBean data : jingjirenlist) {
            if (data.getCheck()) {
                deleteList.add(data);
                if (str == "") {
                    str = str + data.getMid();
                } else {
                    str = str + "," + data.getMid();
                }
            }
        }

        Log.i(TAG, "manageJingjiren()__daleteList = "+deleteList.size());
        if (str == "") {
            loadedDismissProgressDialog(JingjiRenManageActivity.this, false, loadProgressHUD, "请选择要移除的楼盘", false);
        } else {
            final String finalSuccussMsg = SuccussMsg;
            Log.i(TAG, "manageJingjiren()__action = "+action);
            OkHttpUtils.post()
                    .url(API.MANAGEBROKERSLIST_POST)
                    .addParams("token", MyApplication.getInstance().token)
                    .addParams("brokersid", str)
                    .addParams("action", action)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            loadedDismissProgressDialog(JingjiRenManageActivity.this, false, loadProgressHUD, "加载失败，请检查网络", false);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG", "response" + response);
                            CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                            if (checkStarEntity.getCode() == 200) {
                                loadedDismissProgressDialog(JingjiRenManageActivity.this, true, loadProgressHUD, finalSuccussMsg, false);
                                if (vpOwnAdd.getCurrentItem() == 0) {
                                    ownJingjirenList.removeAll(deleteList);
                                } else {
                                    addJingjirenList.removeAll(deleteList);
                                }
                                currentAdapter.notifyDataSetChanged();

                            } else {
                                loadedDismissProgressDialog(JingjiRenManageActivity.this, false, loadProgressHUD, checkStarEntity.getMessage(), false);

                            }
                        }
                    });
        }
    }

    static class ViewHolder {
        @Bind(R.id.et_phone)
        EditText etPhone;
        @Bind(R.id.btn_check)
        Button btnCheck;
        @Bind(R.id.tv_tips)
        TextView tvTips;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

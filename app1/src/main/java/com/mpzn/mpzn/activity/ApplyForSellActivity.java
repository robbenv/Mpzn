package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.ApplyForSellTipsLvAdapter;
import com.mpzn.mpzn.adapter.RvCheckBuildingAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.ApplyForSellTipsEntity;
import com.mpzn.mpzn.entity.CheckStarEntity;
import com.mpzn.mpzn.entity.StarBuildingEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;
import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class ApplyForSellActivity extends BaseActivity {


    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.rv_building)
    RecyclerView rvBuilding;
    @Bind(R.id.btn_commit_num)
    Button btnCommitNum;
    @Bind(R.id.tv_check_all)
    TextView tvCheckAll;
    @Bind(R.id.cb_all)
    CheckBox cbAll;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.ib_search)
    ImageButton ibSearch;
    @Bind(R.id.iv_load_alert)
    ImageView ivLoadAlert;
    @Bind(R.id.lv_search_tips)
    ListView lvSearchTips;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refresh;

    private RvCheckBuildingAdapter rvAdapter;
    private List<StarBuildingEntity.DataBean> applyForSellListData = new ArrayList<>();
    private int currentMaxOffset = 0;
    private KProgressHUD loadProgressHUD;
    private LinearLayoutManager linearLayoutManager;
    private String subject;   //搜索条件

    private List<ApplyForSellTipsEntity.DataBean> tipsList = new ArrayList<>();
    private ApplyForSellTipsLvAdapter searchLvTipsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_for_sell;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        acitonBar.init("楼盘代销", R.drawable.return_gray, 0);
        acitonBar.setRightText("我的代销");
        acitonBar.setRightTextColor(R.color.font_black_4);

        searchLvTipsAdapter = new ApplyForSellTipsLvAdapter(mContext, tipsList);
        lvSearchTips.setAdapter(searchLvTipsAdapter);
        rvAdapter = new RvCheckBuildingAdapter(mContext, applyForSellListData, "开盘");
        rvAdapter.changeEditable(true, false);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvBuilding.setLayoutManager(linearLayoutManager);
        rvBuilding.setAdapter(rvAdapter);


    }

    @Override
    public void initLayoutParams() {

    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        etSearch.setText(subject);
        getData(currentMaxOffset, subject);
    }


    public void getData(final int offset, String subject) {


        OkHttpUtils.get()
                .url(API.FORSELLBUILDLIST_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("subject", subject)
                .addParams("offset", offset + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        StarBuildingEntity applyForSellList = new Gson().fromJson(response, StarBuildingEntity.class);
                        if (applyForSellList.getCode() == 200) {

                            if (offset == 0) {
                                applyForSellListData.clear();
                            }
                            if (applyForSellList.getData().size() == 0 && offset == 0) {
                                rvBuilding.setVisibility(View.GONE);
                            } else {
                                if (offset != 0 && applyForSellList.getData().size() > 0) {
                                    currentMaxOffset = currentMaxOffset + 1;
                                }
                                rvBuilding.setVisibility(View.VISIBLE);
                            }
                            applyForSellListData.addAll(applyForSellList.getData());
                            rvAdapter.updata(applyForSellListData, false);
                        }
                    }
                });


    }

    @Override
    public void bindListener() {

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        Toast.makeText(ApplyForSellActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                        getData(0, subject);
                        //停止刷新效果
                    }
                }, 1000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        getData(currentMaxOffset + 1, subject);
                        //停止刷新效果
                    }
                }, 1000);

            }
        });


        acitonBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MySellActivity.class);
                startActivity(intent);
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                etSearch.setInputType(InputType.TYPE_CLASS_TEXT); //关闭软键盘
                return false;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //软键盘上的搜索
                return true;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    lvSearchTips.setVisibility(View.VISIBLE);
                    rlBottom.setVisibility(View.GONE);
                } else {
                    rlBottom.setVisibility(View.VISIBLE);
                    lvSearchTips.setVisibility(View.GONE);
                }

                Logger.d("token = " + MyApplication.getInstance().token + " \n subject" + etSearch.getText().toString().trim());
                OkHttpUtils.get()
                        .url(API.APPLYBUILDING_TIPS_GET)
                        .addParams("token", MyApplication.getInstance().token)
                        .addParams("subject", etSearch.getText().toString().trim())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ApplyForSellActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ApplyForSellTipsEntity searchTipsList = new Gson().fromJson(response, ApplyForSellTipsEntity.class);
                                if (searchTipsList.getCode() == 200) {
                                    tipsList = searchTipsList.getData();
                                    searchLvTipsAdapter.setData(tipsList);

                                } else {
                                    ApplyForSellTipsEntity.DataBean dataEntity = new ApplyForSellTipsEntity.DataBean();
                                    dataEntity.setNoData(true);
                                    ArrayList<ApplyForSellTipsEntity.DataBean> data = new ArrayList<>();
                                    data.add(dataEntity);
                                    searchLvTipsAdapter.setData(data);
                                }

                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvSearchTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplyForSellTipsEntity.DataBean data = tipsList.get(position);
                if (data.isNoData()) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext, DetailNewhouseActivity.class);
                intent.putExtra("Aid", data.getAid());
                intent.putExtra("Name", data.getSubject());
                startActivity(intent);
            }
        });

//        rvBuilding.addOnItemTouchListener(new OnRecyclerItemClickListener(rvBuilding){
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder vh) {
//                StarBuildingEntity.DataBean dataBean = applyForSellListData.get(vh.getLayoutPosition());
//                Intent intent = new Intent();
//                intent.putExtra("Aid",dataBean.getAid());
//                intent.setClass(mContext,DetailNewhouseActivity.class);
//                startActivity(intent);
//
//            }
//        });

//        rvBuilding.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                Log.e("TAG", "currentMaxOffset" + currentMaxOffset);
//                getData(currentMaxOffset + 1, subject);
//            }
//        });

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rvAdapter.changeEditable(true, true);
                    tvCheckAll.setText("取消全选");
                    btnCommitNum.setText("去申请(" + applyForSellListData.size() + ")");
                } else {
                    rvAdapter.changeEditable(true, false);
                    tvCheckAll.setText("全选");
                    btnCommitNum.setText("去申请(0)");

                }
            }
        });
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject = etSearch.getText().toString();
                if (subject == "") {
                    subject = null;
                }
                if (lvSearchTips.getVisibility() == View.VISIBLE) {
                    lvSearchTips.setVisibility(View.GONE);
                }
                getData(0, subject);

                rvAdapter.changeEditable(true, false);
                cbAll.setChecked(false);
                tvCheckAll.setText("全选");
                btnCommitNum.setText("去申请(0)");

            }
        });

        btnCommitNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aids = "";
                final List<StarBuildingEntity.DataBean> deleteList = new ArrayList<>();
                for (StarBuildingEntity.DataBean data : applyForSellListData) {
                    if (data.getCheck()) {
                        deleteList.add(data);
                        //拼凑aid字符串传给服务器用
                        if (aids == "") {
                            aids = aids + data.getAid();
                        } else {
                            aids = aids + "," + data.getAid();

                        }
                    }
                }

                if (deleteList.size() == 0) {
                    showCustomProgressDialog(ApplyForSellActivity.this, "请选择想要代销的楼盘", R.drawable.toast_error);
                    return;
                }

                loadProgressHUD = KProgressHUD.create(ApplyForSellActivity.this).
                        setSize(MyApplication.mScreenWidth / 4, MyApplication.mScreenWidth / 6).
                        setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                        setLabel("正在申请代销...").setCancellable(false).show();


                Log.e("TAG", "aids" + aids);

                OkHttpUtils.post()
                        .url(API.APPLYBUILDING_POST)
                        .addParams("token", MyApplication.getInstance().token)
                        .addParams("aid", aids)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                loadedDismissProgressDialog(ApplyForSellActivity.this, false, loadProgressHUD, "加载失败，请检查网络", false);
                                cbAll.setChecked(false);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CheckStarEntity checkStarEntity = new Gson().fromJson(response, CheckStarEntity.class);
                                if (checkStarEntity.getCode() == 200) {
                                    loadedDismissProgressDialog(ApplyForSellActivity.this, true, loadProgressHUD, "申请代销成功", false);

                                    applyForSellListData.removeAll(deleteList);

                                    rvAdapter.updata(applyForSellListData, true);

                                } else {
                                    loadedDismissProgressDialog(ApplyForSellActivity.this, false, loadProgressHUD, checkStarEntity.getMessage(), false);

                                }
                                cbAll.setChecked(false);
                            }


                        });


            }

        });

        rvAdapter.setChangCheckListener(new RvCheckBuildingAdapter.ChangCheckListener() {
            @Override
            public void ChangCheck(boolean isCheck) {
                int count = 0;
                for (StarBuildingEntity.DataBean data : applyForSellListData) {
                    if (data.getCheck()) {
                        count += 1;
                    }
                }
                btnCommitNum.setText("去申请(" + count + ")");
            }

            @Override
            public void onPageAllDelete() {
                currentMaxOffset = 0;
                Logger.d("onPageAllDelete");
                getData(currentMaxOffset, "");
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

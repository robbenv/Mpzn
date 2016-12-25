package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AddBBMsgEntity;
import com.mpzn.mpzn.entity.BbStaticEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class BBStatisticsActivity extends BaseActivity {


    @Bind(R.id.rb_recent7)
    RadioButton rbRecent7;
    @Bind(R.id.rb_recent30)
    RadioButton rbRecent30;
    @Bind(R.id.rb_all)
    RadioButton rbAll;
    @Bind(R.id.sp_loupan)
    AppCompatSpinner spLoupan;
    @Bind(R.id.tv_bb_suc_right)
    TextView tvBbSucRight;
    @Bind(R.id.tv_bb_erro_right)
    TextView tvBbErroRight;
    @Bind(R.id.tv_bb_most_right)
    TextView tvBbMostRight;
    @Bind(R.id.tv_bbcount)
    TextView tvBbcount;
    @Bind(R.id.rb_type)
    RadioGroup rbType;
    @Bind(R.id.rl_all)
    RelativeLayout rlAll;
    @Bind(R.id.rl_suc)
    RelativeLayout rlSuc;
    @Bind(R.id.rl_err)
    RelativeLayout rlErr;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;


    private String type = "all";
    private List<String> mList = new ArrayList();
    List<AddBBMsgEntity.DataBean.LoupansBean> loupans;
    private ArrayAdapter arrayAdapter;

    private AddBBMsgEntity.DataBean.LoupansBean loupan;
    private KProgressHUD loadProgressHUD;


    @Override
    public int getLayoutId() {
        return R.layout.activity_bbstatistics;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        myActionBar.init("报备统计", R.drawable.return_red, 0);
        myActionBar.setRightText("报备详情");
        myActionBar.setRightTextColor(R.color.font_black_4);


        mList.add("全部楼盘");
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select_center, mList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop_center);
        spLoupan.setAdapter(arrayAdapter);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

        OkHttpUtils.get()
                .url(API.ADDBBMSG)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AddBBMsgEntity addBBMsgEntity = new Gson().fromJson(response, AddBBMsgEntity.class);
                        if (addBBMsgEntity.getCode() == 200) {
                            loupans = addBBMsgEntity.getData().getLoupans();
                            if (loupans == null) {
                                return;
                            }
                            for (int i = 0; i < loupans.size(); i++) {
                                mList.add(loupans.get(i).getSubject());
                            }
                            arrayAdapter.notifyDataSetChanged();


                        } else {
                            showCustomProgressDialog(BBStatisticsActivity.this, addBBMsgEntity.getMessage(), R.drawable.toast_error);
                        }

                    }
                });


        updataView("all", "");

    }

    private void updataView(String type, String aid) {
        OkHttpUtils.get()
                .url(API.BB_STATISTICS_GET)
                .addParams("type", type)
                .addParams("aid", aid)
                .addParams("token", MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BbStaticEntity bbStaticEntity = new Gson().fromJson(response, BbStaticEntity.class);
                        if (bbStaticEntity.getCode() == 200) {
                            BbStaticEntity.DataBean data = bbStaticEntity.getData();
                            int total = data.getTotal();
                            int success = data.getSuccess();
                            int failure = data.getFailure();
                            tvBbcount.setText(total + "");
                            if (success != 0) {
                                tvBbSucRight.setText(success + "次" + " " + (int) (((float) success) / total * 100) + "%");
                            } else {
                                tvBbSucRight.setText(success + "次" + " 0%");
                            }
                            if (failure != 0) {
                                tvBbErroRight.setText(failure + "次" + " " + (int) (((float) failure) / total * 100) + "%");
                            } else {
                                tvBbSucRight.setText(failure + "次" + " 0%");
                            }
                            tvBbMostRight.setText(data.getMaxsubject() + " " + data.getNum() + "次");
                        }

                    }
                });

    }


    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                startActivity(intent);
            }
        });
        rbType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all:
                        type = "all";
                        break;
                    case R.id.rb_recent7:
                        type = "week";
                        break;
                    case R.id.rb_recent30:
                        type = "month";
                        break;
                }
                if (loupan == null) {
                    updataView(type, "");
                } else {
                    updataView(type, loupan.getAid() + "");
                }
            }
        });


        spLoupan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    loupan = null;
                    updataView(type, "");
                } else {
                    loupan = loupans.get(position - 1);
                    updataView(type, loupan.getAid() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loupan = null;
            }
        });

        rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        rlSuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        rlErr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MyBBActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });

    }



}

package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AddBBMsgEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.mpzn.mpzn.http.API.ADDBB;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;
import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class AddBBActivity extends BaseActivity {


    @Bind(R.id.sp_loupan)
    AppCompatSpinner spLoupan;
    @Bind(R.id.et_khname)
    EditText etKhname;
    @Bind(R.id.et_khphone3)
    EditText etKhphone3;
    @Bind(R.id.et_khphone4)
    EditText etKhphone4;
    @Bind(R.id.cb_add_pt)
    CheckBox cbAddPt;
    @Bind(R.id.et_khnamep1)
    EditText etKhnamep1;
    @Bind(R.id.et_khnamep2)
    EditText etKhnamep2;
    @Bind(R.id.ll_peitong)
    LinearLayout llPeitong;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_cname)
    EditText etCname;
    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    private boolean isAddPt;
    private List<String> mList = new ArrayList();
    List<AddBBMsgEntity.DataBean.LoupansBean> loupans;
    private ArrayAdapter arrayAdapter;

    private AddBBMsgEntity.DataBean.LoupansBean loupan;
    private KProgressHUD loadProgressHUD;


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_baobei;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        acitonBar.init("添加报备", R.drawable.return_white, 0);
        acitonBar.setRightText("提交");
        acitonBar.setRightTextColor(R.color.white);

        mList.add("请选择你要报备的楼盘");
        arrayAdapter = new ArrayAdapter(mContext, R.layout.item_select, mList);
        arrayAdapter.setDropDownViewResource(R.layout.item_drop);
        spLoupan.setAdapter(arrayAdapter);
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        if (cbAddPt.isChecked()) {
            isAddPt = true;
        } else {
            isAddPt = false;
        }

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
                        Log.e("TAG", "response"+response);
                        AddBBMsgEntity addBBMsgEntity = new Gson().fromJson(response, AddBBMsgEntity.class);
                        if (addBBMsgEntity.getCode() == 200) {
                            loupans = addBBMsgEntity.getData().getLoupans();

                            Intent intent = getIntent();
                            int aid = intent.getIntExtra("Aid", -1);
                            int loupan_index =-1;

                            for (int i = 0; i < loupans.size(); i++) {
                                mList.add(loupans.get(i).getSubject());
                                if(loupans.get(i).getAid()==aid){
                                    loupan_index=i;
                                }
                            }
                            arrayAdapter.notifyDataSetChanged();
                            spLoupan.setSelection(loupan_index+1);


                            etName.setText(addBBMsgEntity.getData().getName());
                            etPhone.setText(addBBMsgEntity.getData().getPhone());
                            etCname.setText(addBBMsgEntity.getData().getCompanyName());

                        } else {
                            showCustomProgressDialog(AddBBActivity.this, addBBMsgEntity.getMessage(), R.drawable.toast_error);
                        }

                    }
                });


    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loupan == null) {
                    showCustomProgressDialog(AddBBActivity.this, "请选择你要报备的楼盘", R.drawable.toast_error);

                } else if (etKhname.length() == 0) {
                    showCustomProgressDialog(AddBBActivity.this, "客户姓名不能为空", R.drawable.toast_error);

                } else if (etKhphone3.length() != 3) {
                    showCustomProgressDialog(AddBBActivity.this, "请补全客户手机号前三位", R.drawable.toast_error);

                } else if (etKhphone4.length() != 4) {
                    showCustomProgressDialog(AddBBActivity.this, "请补全客户手机号后四位", R.drawable.toast_error);

                } else if (etName.length() == 0) {
                    showCustomProgressDialog(AddBBActivity.this, "你的姓名不能为空", R.drawable.toast_error);

                } else if (etPhone.length() != 11) {
                    showCustomProgressDialog(AddBBActivity.this, "你的电话不能为空", R.drawable.toast_error);

                } else if (etCname.length() == 0) {
                    showCustomProgressDialog(AddBBActivity.this, "经纪公司名称不能为空", R.drawable.toast_error);

                } else {
                    loadProgressHUD = KProgressHUD.create(AddBBActivity.this).
                            setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                            setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                            setLabel("正在提交..").setCancellable(false).show();

                    OkHttpUtils.post()
                            .url(ADDBB)
                            .addParams("token", MyApplication.getInstance().token)
                            .addParams("khname", etKhname.getText().toString().trim())
                            .addParams("khphone3", etKhphone3.getText().toString().trim())
                            .addParams("khphone4", etKhphone4.getText().toString().trim())
                            .addParams("cname", etCname.getText().toString().trim())
                            .addParams("aid", loupan.getAid() + "")
                            .addParams("subject", loupan.getSubject())
                            .addParams("phone", etPhone.getText().toString().trim())
                            .addParams("myname", etName.getText().toString().trim())
                            .addParams("khnamep1", etKhnamep1.getText().toString().trim())
                            .addParams("khnamep2", etKhnamep2.getText().toString().trim())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    loadedDismissProgressDialog(AddBBActivity.this, false, loadProgressHUD, "报备失败", false);


                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if (simpleEntity.getCode() == 200) {
                                        loadedDismissProgressDialog(AddBBActivity.this, true, loadProgressHUD, "报备成功", true);

                                    } else {
                                        loadedDismissProgressDialog(AddBBActivity.this, false, loadProgressHUD, simpleEntity.getMessage(), false);


                                    }


                                }
                            });
                }
            }
        });
        cbAddPt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    llPeitong.setVisibility(View.VISIBLE);
                    isAddPt = true;
                }else{
                    llPeitong.setVisibility(View.GONE);
                    isAddPt = false;
                }
            }
        });

        spLoupan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    loupan = null;
                } else {
                    loupan = loupans.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loupan = null;
            }
        });


    }





}

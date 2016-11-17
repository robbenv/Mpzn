package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AccountEntity;
import com.mpzn.mpzn.entity.AlterPassEntity;
import com.mpzn.mpzn.entity.VerifyPassEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.http.API.ALTERPASS;
import static com.mpzn.mpzn.utils.CacheUtils.getObject;
import static com.mpzn.mpzn.utils.CacheUtils.putObject;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;
import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class ChangePassActivity extends BaseActivity {



    @Bind(R.id.et_password_now)
    EditText etPasswordNow;
    @Bind(R.id.btn_clear_pass1)
    Button btnClear1;
    @Bind(R.id.btn_clear_2)
    Button btnClear2;
    @Bind(R.id.et_pass)
    EditText etPass;
    @Bind(R.id.et_comfirm_pass)
    EditText etComfirmPass;
    @Bind(R.id.btn_clear_3)
    Button btnClear3;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    private KProgressHUD loadProgressHUD;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        myActionBar.init("修改密码", R.drawable.return_white, 0);
        myActionBar.setRightText("确定");


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        addClearListener(etPasswordNow, btnClear1);
        addClearListener(etPass, btnClear2);
        addClearListener(etComfirmPass, btnClear3);

        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPasswordNow.length() == 0) {
                    showCustomProgressDialog(ChangePassActivity.this, "当前密码不能为空", R.drawable.toast_error);
                } else if (etPass.length() == 0) {
                    showCustomProgressDialog(ChangePassActivity.this, "新密码不能为空", R.drawable.toast_error);
                } else if (etComfirmPass.length() == 0) {
                    showCustomProgressDialog(ChangePassActivity.this, "确认新密码不能为空", R.drawable.toast_error);
                } else if (!(etPass.getText().toString().trim()).equals(etComfirmPass.getText().toString().trim())) {
                    showCustomProgressDialog(ChangePassActivity.this, "两次密码输出不一致", R.drawable.toast_error);
                } else {
                    Log.e("TAG", "token" + MyApplication.getInstance().token);
                    OkHttpUtils.post()
                            .url(API.VERIFYPASS)
                            .addParams("token", MyApplication.getInstance().token)
                            .addParams("pass", etPasswordNow.getText().toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    showCustomProgressDialog(ChangePassActivity.this, "请求失败", R.drawable.toast_error);

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    VerifyPassEntity verifyPassEntity = new Gson().fromJson(response, VerifyPassEntity.class);
                                    if (verifyPassEntity.getCode() == 200) {
                                        loadProgressHUD = KProgressHUD.create(ChangePassActivity.this).
                                                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                                                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                                                setLabel("正在修改..").setCancellable(false).show();
                                        OkHttpUtils.post()
                                                .url(ALTERPASS)
                                                .addParams("token", MyApplication.getInstance().token)
                                                .addParams("pass", etComfirmPass.getText().toString())
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        loadedDismissProgressDialog(ChangePassActivity.this, false, loadProgressHUD, "修改失败", false);

                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        AlterPassEntity alterPassEntity = new Gson().fromJson(response, AlterPassEntity.class);
                                                        if (alterPassEntity.getCode() == 200) {
                                                            String token = alterPassEntity.getData().getToken();
                                                            CacheUtils.putString(mContext, "token", token);
                                                            MyApplication.getInstance().setToken(token);
                                                            AccountEntity account = (AccountEntity) getObject(mContext, "account");
                                                            account.setPassword(etComfirmPass.getText().toString());
                                                            putObject(mContext, "account", account);

                                                            loadedDismissProgressDialog(ChangePassActivity.this, false, loadProgressHUD, "修改成功", true);


                                                        } else {
                                                            loadedDismissProgressDialog(ChangePassActivity.this, false, loadProgressHUD, alterPassEntity.getData().getHint(), false);

                                                        }

                                                    }
                                                });


                                    } else {
                                        showCustomProgressDialog(ChangePassActivity.this, verifyPassEntity.getData().getHint(), R.drawable.toast_error);

                                    }

                                }
                            });

                }
            }
        });


    }

    private void addClearListener(final EditText editText, final Button btnClear) {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length() != 0) {
                    editText.setText("");

                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.length() != 0) {
                    btnClear.setVisibility(View.VISIBLE);

                } else {
                    btnClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.LoginEvent;
import com.mpzn.mpzn.entity.RegistEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.dismissDialogWithCallback;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class RegisterSetPassActivity extends BaseActivity {


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_clear_pass_1)
    Button btnClearPass1;
    @Bind(R.id.et_comfirm_pass)
    EditText etComfirmPass;
    @Bind(R.id.btn_clear_2)
    Button btnClear2;
    private String userPhone;
    private String code;
    private String from;
    private KProgressHUD loadProgressHUD;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_set_pass;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, 0);
        myActionBar.init("设置密码",R.drawable.return_white,0);
        myActionBar.setRightText("下一步");
        myActionBar.setRightTextColor(R.color.white);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");
        code = intent.getStringExtra("code");
        from = intent.getStringExtra("from");
        if("resetpass".equals(from)){
            myActionBar.setRightText("提交");
        }

    }

    @Override
    public void bindListener() {

        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().trim().length()==0) {
                    Toast.makeText(RegisterSetPassActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;

                }else if(etPassword.getText().toString().trim().length()<6){
                    Toast.makeText(RegisterSetPassActivity.this, "设置的密码至少6位", Toast.LENGTH_SHORT).show();
                    return;

                }else if(!etPassword.getText().toString().equals(etComfirmPass.getText().toString())) {
                    Toast.makeText(RegisterSetPassActivity.this, "请确认两次输入的密码是否一致", Toast.LENGTH_SHORT).show();
                    return;
                }else if("resetpass".equals(from)){
                    loadProgressHUD = KProgressHUD.create(RegisterSetPassActivity.this).
                            setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                            setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                            setLabel("正在提交...").setCancellable(false).show();

                    OkHttpUtils.post()
                            .url(API.FORGETPASS_POST)
                            .addParams("user",userPhone)
                            .addParams("pass",etComfirmPass.getText().toString())
                            .addParams("message",code)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    loadedDismissProgressDialog(RegisterSetPassActivity.this, false, loadProgressHUD, "请检查你的网络...", false);

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    RegistEntity registEntity = new Gson().fromJson(response, RegistEntity.class);
                                    if(registEntity.getCode()==200){
                                        setResult(RESULT_OK);
                                        loadedDismissProgressDialog(RegisterSetPassActivity.this, true, loadProgressHUD, "重置密码成功", true);
                                    }else{
                                        loadedDismissProgressDialog(RegisterSetPassActivity.this, false, loadProgressHUD, registEntity.getMessage(), false);

                                    }

                                }
                            });


                }else{
                    Intent intent = new Intent();
                    intent.putExtra("userPhone",userPhone);
                    intent.putExtra("code",code);
                    intent.putExtra("pass",etComfirmPass.getText().toString());
                    intent.setClass(mContext, RegForUserTypeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent, Constant.REQCODE_REGISTER_FOR_USER_TYPE);
                }
            }
        });
        btnClearPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
            }
        });
        btnClear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComfirmPass.setText("");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.none);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

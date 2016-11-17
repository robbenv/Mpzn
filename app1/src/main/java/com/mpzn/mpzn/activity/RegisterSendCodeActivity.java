package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.receiver.SmsReceiver;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class RegisterSendCodeActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.et_user_phone)
    EditText etUserPhone;
    @Bind(R.id.tv_tips)
    TextView tvTips;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.tv_tips_1)
    TextView tvTips1;

    private String pass;
    private String from;

    private SmsReceiver receiver;

    private static final int MSG_SENDMSG = 9;
    private static int rest_time = 60;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SENDMSG:
                    if (rest_time > 0) {
                        rest_time--;
                        btnGetCode.setText("已发送(" + rest_time + "s)");
                        handler.sendEmptyMessageDelayed(MSG_SENDMSG, 1000);
                    } else {
                        rest_time = 60;
                        btnGetCode.setText("重新获取");
                        btnGetCode.setEnabled(true);
                    }

                    break;
            }
        }
    };



    @Override
    public int getLayoutId() {
        return R.layout.activity_register_send_code;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        myActionBar.init("手机验证", R.drawable.return_white, 0);
        myActionBar.setRightText("下一步");
        myActionBar.setRightTextColor(R.color.white);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if("resetpass".equals(from)){


        }else if("register".equals(from)){

        }


        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }



    @Override
    public void bindListener() {

        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userPhone = etUserPhone.getText().toString().trim();
                final String code = etPassword.getText().toString().trim();
                if ("" == userPhone || 0 == userPhone.length()) {
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else  if (userPhone.length() != 11) {
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else  if ("" == code || 0 == code.length()) {
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入你收到的验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else  if (code.length()!= 6){
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    OkHttpUtils.post()
                            .url(API.CHECK_CODE_ISCORRECT_POST)
                            .addParams("user",userPhone)
                            .addParams("message",code)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if(simpleEntity.getCode()==200){
                                        Intent intent = new Intent();
                                        intent.setClass(mContext,RegisterSetPassActivity.class);
                                        intent.putExtra("from",from);
                                        intent.putExtra("userPhone",userPhone);
                                        intent.putExtra("code",code);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivityForResult(intent, Constant.REQCODE_REGISTER_SET_PASS);

                                    }else{
                                        Toast.makeText(RegisterSendCodeActivity.this, "验证码错误，请重新填写", Toast.LENGTH_SHORT).show();
                                        etPassword.setText("");
                                        etPassword.requestFocus();

                                    }
                                }
                            });
                }


            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = etUserPhone.getText().toString().trim();
                if ("" == userPhone || 0 == userPhone.length()) {
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else  if (userPhone.length() != 11) {
                    Toast.makeText(RegisterSendCodeActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpUtils.post()
                        .url(API.REG_SEND_SMS)
                        .addParams("tos", userPhone)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                btnGetCode.setEnabled(false);
                                btnGetCode.setText("已发送(" + rest_time + "s)");
                                handler.sendEmptyMessageDelayed(MSG_SENDMSG, 1000);
                                if (receiver == null) {
                                    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                                    receiver = new SmsReceiver();
                                    registerReceiver(receiver, filter);
                                    receiver.setSmsReceiveForRegListener(new SmsReceiver.SmsReceiveForRegListener() {
                                        @Override
                                        public void setBody(String body) {
                                            if (body.startsWith("您的验证码") && body.endsWith("【卖盘指南网】")) {
                                                int wei = body.indexOf("为");
                                                String code = body.substring(wei + 1, wei + 7);
                                                etPassword.setText(code);
                                            }
                                        }
                                    });
                                }


                            }
                        });

            }
        });
        etUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone= s.toString().trim();
                if(phone.length()==11){
                      OkHttpUtils.post()
                              .url(API.CHECK_PHONE_ISEXSIST_POST)
                              .addParams("mname",phone)
                              .build()
                              .execute(new StringCallback() {
                                  @Override
                                  public void onError(Call call, Exception e, int id) {

                                  }

                                  @Override
                                  public void onResponse(String response, int id) {
                                      SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                      if("resetpass".equals(from)){

                                          if(simpleEntity.getCode()!=200){
                                              tvTips.setText("请获取验证码");
                                              tvTips.setTextColor(getResources().getColor(R.color.font_black_5));
                                              btnGetCode.setEnabled(true);
                                          }else {
                                              tvTips.setText("该手机号未注册，请返回注册");
                                              tvTips.setTextColor(getResources().getColor(R.color.red_theme));
                                              btnGetCode.setEnabled(false);
                                          }


                                      }else if("register".equals(from)){
                                          if(simpleEntity.getCode()==200){
                                              tvTips.setText("该手机号可用");
                                              tvTips.setTextColor(getResources().getColor(R.color.blue));
                                              btnGetCode.setEnabled(true);
                                          }else {
                                              tvTips.setText("该手机号已注册，请返回登录");
                                              tvTips.setTextColor(getResources().getColor(R.color.red_theme));
                                              btnGetCode.setEnabled(false);
                                          }
                                      }



                                  }
                              });

                  }else{
                    tvTips.setText("请输入你的手机号");
                    tvTips.setTextColor(getResources().getColor(R.color.font_black_5));
                    btnGetCode.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        if(resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("TAG", "RegSendCodeOndestroy");
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }


}

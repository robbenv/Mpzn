package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AccountEntity;
import com.mpzn.mpzn.entity.LoginEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.receiver.SmsReceiver;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.CacheUtils.putObject;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.dismissDialogWithCallback;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

/**
 * Created by Icy on 2016/9/7.
 */
public class LoginFastAcitivity extends BaseActivity {

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
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.reg_form)
    LinearLayout regForm;
    @Bind(R.id.btn_reg)
    Button btnReg;
    @Bind(R.id.cb_read_xiyi)
    CheckBox cbReadXiyi;
    @Bind(R.id.tv_read)
    TextView tvRead;

    private static final int MSG_SENDMSG = 9;
    private static int rest_time = 60;
    @Bind(R.id.tv_tips)
    TextView tvTips;

    private SmsReceiver receiver;
    private boolean isRequest;

    private KProgressHUD loadProgressHUD;


    private String userName;
    private String code;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_fast;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        myActionBar.init("手机号快捷登录", R.drawable.return_white, 0);
        cbReadXiyi.setChecked(true);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {


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
        myActionBar.setLROnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = etUserPhone.getText().toString().trim();
                if ("" == userPhone || 0 == userPhone.length()) {
                    Toast.makeText(LoginFastAcitivity.this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (userPhone.length() != 11) {
                    Toast.makeText(LoginFastAcitivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
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


        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (btnReg.isEnabled()) {
                        regist();
                    }
                }

                return false;
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etUserPhone.getText().length() == 11 && etPassword.getText().length() == 6 && cbReadXiyi.isChecked()) {
                    btnReg.setEnabled(true);
                } else {
                    btnReg.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etPassword.addTextChangedListener(textWatcher);
        etUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    OkHttpUtils.post()
                            .url(API.REG_CHECK_POST)
                            .addParams("mname", s.toString())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if (simpleEntity.getCode() == 200) {
                                        tvTips.setText("手机号未注册,请验证并注册");
                                        tvTips.setTextColor(getResources().getColor(R.color.blue));
                                        btnReg.setText("验证并注册");
                                        btnGetCode.setEnabled(true);
                                    } else {
                                        tvTips.setText("手机号已注册,请验证并登录");
                                        tvTips.setTextColor(getResources().getColor(R.color.green));
                                        btnReg.setText("验证并登录");
                                        btnGetCode.setEnabled(true);

                                    }
                                }
                            });
                } else {
                    tvTips.setText("请输入您的手机号");
                    tvTips.setTextColor(getResources().getColor(R.color.font_black_5));
                    btnGetCode.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etUserPhone.getText().length() == 11  && etPassword.getText().length() == 6 && cbReadXiyi.isChecked()) {
                    btnReg.setEnabled(true);
                } else {
                    btnReg.setEnabled(false);
                }


            }
        });


        cbReadXiyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (etUserPhone.getText().length() == 11  && etPassword.length() == 6 && cbReadXiyi.isChecked()) {
                    btnReg.setEnabled(true);
                } else {
                    btnReg.setEnabled(false);
                }
            }
        });

        tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, WebsiteArticleAcivity.class);
                startActivity(intent);
            }
        });


    }

    private void regist() {
        if (isRequest) {                //如果正在请求直接跳出
            return;                   //否则将请求状态改为正在请求

        } else {
            isRequest = true;
        }
        userName = etUserPhone.getText().toString().trim();
        code = etPassword.getText().toString().trim();


//        OkHttpUtils
//                .post()
//                .url(API.REGIST_POST)
//                .addParams("user", userName)
//                .addParams("pass", "null")
//                .addParams("message", code)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                        Toast.makeText(LoginFastAcitivity.this, "请检查你的网络...", Toast.LENGTH_SHORT).show();
//
//                        isRequest = false;
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//
//                        RegistEntity registEntity = new Gson().fromJson(response, RegistEntity.class);
//                        if (registEntity != null) {
//                            onResponseAfterSubmit(registEntity);
//                        }
//                        isRequest = false;
//                    }
//                });

        loadProgressHUD = KProgressHUD.create(this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在"+btnReg.getText()+"...").setCancellable(true).show();

        OkHttpUtils.get()
                .url(API.REGIST_FAST)
                .addParams("phone", userName)
                .addParams("code", code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(LoginFastAcitivity.this, false, loadProgressHUD, "请检查你的网络...", false);
                        isRequest = false;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final LoginEntity loginEntity = new Gson().fromJson(response, LoginEntity.class);
                        final Intent intent = new Intent();
                        switch (loginEntity.getCode()) {
                            case  200:     //用户已注册且验证成功  登录

                                AccountEntity accountEntity = new AccountEntity(userName, "",loginEntity.getData().getHeadimage());
                                putObject(mContext, "account", accountEntity);
                                dismissDialogWithCallback(LoginFastAcitivity.this, true, loadProgressHUD, "登录成功", new ViewUtils.CallBack() {
                                    @Override
                                    public void afterDialogDismiss() {
                                        intent.setClass(mContext,MainActivity.class);
                                        LoginEntity.DataEntity loginEntityData = loginEntity.getData();
                                        intent.putExtra("from","login");
                                        intent.putExtra("loginEntityData", (Parcelable)loginEntityData);
                                        Log.e("TAG", "快速登录页面finish()");
                                        setResult(RESULT_OK, intent);
                                        finish();


                                    }
                                });


                                break;
                            case  1231:   //验证码错误
                                loadedDismissProgressDialog(LoginFastAcitivity.this, false, loadProgressHUD, "验证码错误，请重新填写", false);
                                etPassword.setText("");

                                break;
                            case  1123:   //用户未注册且验证成功  注册
                                dismissDialogWithCallback(LoginFastAcitivity.this, true, loadProgressHUD, "正在跳转...", new ViewUtils.CallBack() {
                                    @Override
                                    public void afterDialogDismiss() {
                                        intent.putExtra("userPhone",userName);
                                        intent.putExtra("code",code);
                                        intent.putExtra("from","loginfast");
                                        intent.setClass(mContext,RegisterSetPassActivity.class);
                                        startActivityForResult(intent, Constant.REQCODE_REGISTER_SET_PASS);

                                    }
                                });
                                break;
                            default:
                                loadedDismissProgressDialog(LoginFastAcitivity.this, false, loadProgressHUD, loginEntity.getMessage(), false);

                                break;
                        }
                        isRequest = false;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        if(resultCode==RESULT_OK){
            Intent intent = new Intent();
            intent.putExtra("from","register");
            setResult(RESULT_OK,intent);
            finish();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}

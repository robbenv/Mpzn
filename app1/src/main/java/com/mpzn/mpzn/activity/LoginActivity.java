package com.mpzn.mpzn.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AccountEntity;
import com.mpzn.mpzn.entity.LoginEntity;
import com.mpzn.mpzn.entity.LoginEvent;
import com.mpzn.mpzn.entity.RegistEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.entity.UserMsg;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.receiver.SmsReceiver;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Call;

import static com.mpzn.mpzn.application.MyApplication.mUserMsg;
import static com.mpzn.mpzn.utils.CacheUtils.getObject;
import static com.mpzn.mpzn.utils.CacheUtils.putObject;
import static com.mpzn.mpzn.utils.Constant.REQCODE_REGISTER_SEND_CODE;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.dismissDialogWithCallback;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;


public class LoginActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Bind(R.id.et_user_phone)
    EditText etUserPhone;
    @Bind(R.id.btn_clear)
    Button btnClear;
    @Bind(R.id.cb_password_look)
    CheckBox cbPasswordLook;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_reg_fast)
    TextView tvRegFast;
    @Bind(R.id.tv_reg_for_pho)
    TextView tvRegForPho;
    @Bind(R.id.center)
    LinearLayout center;
    @Bind(R.id.ib_qq)
    ImageButton ibQq;
    @Bind(R.id.ib_wx)
    ImageButton ibWx;
    @Bind(R.id.ib_wb)
    ImageButton ibWb;
    @Bind(R.id.imgs)
    LinearLayout imgs;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.iv_login_user)
    ImageView ivLoginUser;
    private boolean isRequest = false;
    private LoginEntity loginEntity;

    private UMShareAPI mShareAPI;

    private KProgressHUD loadProgressHUD;
    private ViewHolder dialog_view;
    private Dialog dialog;

    private String openid;
    private String wx_name;
    private String wx_headimage;

    private static final int resultCode = 1001;
    private static final int MSG_SENDMSG = 9;
    private static int rest_time = 60;

    private SmsReceiver receiver;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SENDMSG:
                    if (rest_time > 0) {
                        rest_time--;
                        dialog_view.btnCheck.setText("已发送(" + rest_time + "s)");
                        handler.sendEmptyMessageDelayed(MSG_SENDMSG, 1000);
                    } else {
                        dialog_view.btnCheck.setText("重新获取");
                        dialog_view.btnCheck.setEnabled(true);
                    }

                    break;
            }
        }
    };

    //友盟授权监听
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.i("weixin1", "onComplete()__data = "+data.toString());
            getUMUserMessager(platform, data);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "取消授权", Toast.LENGTH_SHORT).show();
        }
    };
    private final int REGIST = 0;
    private int status;
    private final int BIND = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDialog();
        //注册EventBus
        EventBus.getDefault().register(this);
        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initHolder() {

        initSystemBar(this, R.color.red_theme);

        myActionBar.init("登录", R.drawable.return_white, 0);
        myActionBar.setRightText("忘记密码");
        //AccountEntity只是用来自动填上用户账号密码和头像的
        AccountEntity account = (AccountEntity) getObject(mContext, "account");
        if (account != null) {
            etUserPhone.setText(account.getUsername());
            etPassword.setText(account.getPassword());

            if(etUserPhone.getText().length()!=0&&etPassword.getText().length()!=0){
                btnLogin.setEnabled(true);
            }
            mImageManager.loadCircleImageWithWhite(account.getIconurl(),ivLoginUser);
            cbPasswordLook.setVisibility(View.GONE);

        }

    }

    private void createDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentView = inflater.inflate(R.layout.layout_et_phone_dialog, null);
        dialog_view = new ViewHolder(contentView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.addContentView(contentView, layoutParams);
        dialog_view.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = dialog_view.etPhone.getText().toString().trim();
                if ("" == userPhone || 0 == userPhone.length()) {
                    Toast.makeText(LoginActivity.this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (userPhone.length() != 11) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
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
                                dialog_view.btnCheck.setEnabled(false);
                                dialog_view.btnCheck.setText("已发送(" + rest_time + "s)");
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
                                                dialog_view.etCode.setText(code);
                                            }
                                        }
                                    });
                                }


                            }
                        });

            }
        });

        dialog_view.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case REGIST:
                        registWX();
                        break;
                    case BIND:
                        bindWX();
                }

            }
        });
        dialog_view.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog_view.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 11) {
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
                                        dialog_view.tvTips.setText("该手机号可用,请发送验证码以验证");
                                        dialog_view.btnCheck.setEnabled(true);
                                        dialog_view.tvTips.setTextColor(getResources().getColor(R.color.blue));
                                        dialog_view.llCode.setVisibility(View.VISIBLE);
                                        dialog_view.llPass.setVisibility(View.GONE);
                                        dialog_view.btnCheck.setEnabled(true);
                                        dialog_view.etCode.setText("");
                                        dialog_view.etPass.setText("");
                                        status = REGIST;
                                    } else {
                                        dialog_view.tvTips.setText("该手机号已注册，请输入密码以绑定");
                                        dialog_view.btnCheck.setEnabled(false);
                                        dialog_view.tvTips.setTextColor(getResources().getColor(R.color.font_black_5));
                                        dialog_view.llPass.setVisibility(View.VISIBLE);
                                        dialog_view.llCode.setVisibility(View.GONE);
                                        dialog_view.btnCheck.setEnabled(false);
                                        dialog_view.etCode.setText("");
                                        dialog_view.etPass.setText("");
                                        status = BIND;
                                    }
                                }
                            });

                } else {
                    dialog_view.tvTips.setText("请输入你的手机号");
                    dialog_view.tvTips.setTextColor(getResources().getColor(R.color.font_black_5));
                    dialog_view.llPass.setVisibility(View.GONE);
                    dialog_view.llCode.setVisibility(View.GONE);
                    dialog_view.btnCheck.setEnabled(false);
                    dialog_view.etCode.setText("");
                    dialog_view.etPass.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && dialog_view.etPhone.getText().length() == 11) {
                    dialog_view.btnCommit.setEnabled(true);
                } else {
                    dialog_view.btnCommit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        dialog_view.etCode.addTextChangedListener(textWatcher);
        dialog_view.etPass.addTextChangedListener(textWatcher);

    }

    private void bindWX() {
        OkHttpUtils.post()
                .url(API.BIND_WX)
                .addParams("user", dialog_view.etPhone.getText().toString())
                .addParams("weixin_openid", openid)
                .addParams("message", dialog_view.etCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if (simpleEntity.getCode() == 200) {
                            Log.i("weixin", "onResponse()__success");
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("weixin", "onResponse()__false");
                            dialog_view.btnCommit.setEnabled(false);
                            dialog_view.tvTips.setText(simpleEntity.getMessage());
                        }
                    }
                });
    }

    private void registWX() {
        OkHttpUtils.post()
                .url(API.WX_REGIST)
                .addParams("user", dialog_view.etPhone.getText().toString())
                .addParams("weixin_openid", openid)
                .addParams("message", dialog_view.etCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if (simpleEntity.getCode() == 200) {
                            Log.i("weixin", "onResponse()__success");
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("weixin", "onResponse()__false");
                            dialog_view.btnCommit.setEnabled(false);
                            dialog_view.tvTips.setText(simpleEntity.getMessage());
                        }
                    }
                });
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
        myActionBar.setLROnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etPassword.setText("");
                        Intent intent = new Intent();
                        intent.setClass(mContext, RegisterSendCodeActivity.class);
                        intent.putExtra("from", "resetpass");
                        startActivityForResult(intent, REQCODE_REGISTER_SEND_CODE);

                    }
                });


        etUserPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                    Toast.makeText(mContext, et_user_phone.getText(), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        etUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==11){
                  OkHttpUtils.get()
                          .url(API.GET_ICON_GET)
                          .addParams("account",etUserPhone.getText().toString().trim())
                          .build()
                          .execute(new StringCallback() {
                              @Override
                              public void onError(Call call, Exception e, int id) {

                              }

                              @Override
                              public void onResponse(String response, int id) {
                                  try {
                                      JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                                      String avatar = jsonObject.getString("avatar");
                                      mImageManager.loadCircleImage(avatar,ivLoginUser);

                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                              }
                          });

                }else{
                    mImageManager.loadCircleResImage(R.drawable.logo,ivLoginUser);

                }
                etPassword.setText("");
                btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String username = etUserPhone.getText().toString().trim();
                    String pass = etPassword.getText().toString().trim();
                    login(username, pass);
                }

                return false;
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cbPasswordLook.getVisibility() == View.GONE && s.length() == 0) {
                    cbPasswordLook.setVisibility(View.VISIBLE);
                }
                if(etUserPhone.getText().toString().length() == 11) {
                    if (s.toString().length() >= 6) {
                        btnLogin.setEnabled(true);
                    } else {
                        btnLogin.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cbPasswordLook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 显示密码
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // 隐藏密码
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                CharSequence text = etPassword.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());// 将光标移动到最后
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserPhone.getText().length() > 0) {
                    etUserPhone.setText(null);

                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserPhone.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                login(username, pass);
            }
        });

        tvRegFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, RegisterSendCodeActivity.class);
                intent.putExtra("from", "register");
                startActivityForResult(intent, REQCODE_REGISTER_SEND_CODE);
            }
        });

        tvRegForPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, LoginFastAcitivity.class);
                Log.e("TAG", "登录页面回调启动快速登录页");
                startActivityForResult(intent, Constant.REQCODE_LOGIN_FAST);

            }
        });


        ibWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                getUMAuth(platform);

            }
        });
        ibWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHARE_MEDIA platform = SHARE_MEDIA.SINA;
                getUMAuth(platform);

            }
        });
        ibQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHARE_MEDIA platform = SHARE_MEDIA.QQ;
                getUMAuth(platform);

            }
        });


    }

    private void login(final String username, final String pass) {

        if (isRequest) {                //如果正在请求直接跳出
            return;                   //否则将请求状态改为正在请求

        } else {
            isRequest = true;
        }
        loadProgressHUD = KProgressHUD.create(this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在登录...").setCancellable(true).show();

        OkHttpUtils
                .get()
                .url(API.LOGIN_GET)
                .addParams("username", username)
                .addParams("pass", pass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(LoginActivity.this, false, loadProgressHUD, "请检查你的网络...", false);

                        isRequest = false;
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        loginEntity = new Gson().fromJson(response, LoginEntity.class);
                        if (loginEntity.getCode() == 200) {
                            AccountEntity accountEntity = new AccountEntity(username, pass, loginEntity.getData().getHeadimage());
                            putObject(mContext, "account", accountEntity);

                            dismissDialogWithCallback(LoginActivity.this, true, loadProgressHUD, "登录成功", new ViewUtils.CallBack() {
                                @Override
                                public void afterDialogDismiss() {
                                    getUserMsg(loginEntity.getData());
                                    LoginActivity.this.finish();
                                }
                            });

                        } else {
                            loadedDismissProgressDialog(LoginActivity.this, false, loadProgressHUD, loginEntity.getMessage(), false);


                        }
                        isRequest = false;
                    }
                });
    }


    private void getUserMsg(LoginEntity.DataEntity data) {
        Log.e("TAG", "token" + data.getToken());
        CacheUtils.putString(mContext, "token", data.getToken());
        MyApplication.getInstance().setToken(data.getToken());
        Intent intent = new Intent();
        UserMsg userMsg = new UserMsg();
        userMsg.setmChild(data.getMchid());
        Log.e("TAG", "type" + data.getMchid());
        Logger.d(data.getMname());
            userMsg.setmName(data.getMname());
            userMsg.setName(data.getName());
            userMsg.setNickName(data.getNickname());
        userMsg.setPhone(data.getMname());
        userMsg.setmId(data.getMid());
        userMsg.setmIconUrl(data.getHeadimage());

        Bundle bundle = new Bundle();
        bundle.putParcelable("userMsg", userMsg);


        putObject(mContext, "userMsg", userMsg);
        Logger.d("别名："+userMsg.getmName()+"_dev");
                JPushInterface.setAlias(this, userMsg.getmName()+"_dev", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                //啥也没有

            }
        });
        MyApplication.getInstance().setmUserMsg(userMsg);
        Toast.makeText(mContext, "别名："+userMsg.getmName()+"_dev", Toast.LENGTH_SHORT).show();



        MyApplication.getInstance().setIsLogined(true);

        intent.putExtras(bundle);
        setResult(data.getMchid(), intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "requestCode" + requestCode + "resultCode" + resultCode);

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

        switch (requestCode) {
            case Constant.REQCODE_LOGIN_FAST:
                Log.e("TAG", "REQCODE_LOGIN_FAST");

                if (resultCode == RESULT_OK) {
                    String from = data.getStringExtra("from");
                    if ("login".equals(from)) {
                        Log.e("TAG", "from" + from);
                        LoginEntity.DataEntity loginEntityData = data.getParcelableExtra("loginEntityData");
                        getUserMsg(loginEntityData);
                        finish();
                    } else if ("register".equals(from)) {
                        //eventbus的回调中会执行登录
                    }


                }


                break;
        }

        //友盟授权的回调参数
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    //友盟授权
    public void getUMAuth(SHARE_MEDIA platform) {

        mShareAPI.doOauthVerify(this, platform, umAuthListener);



        //删除授权
        //mShareAPI.deleteOauth(this, platform, umdelAuthListener);


    }

    public void getUMUserMessager(final SHARE_MEDIA platform, Map<String, String> data) {
        mShareAPI = UMShareAPI.get(this);
        if (platform == SHARE_MEDIA.WEIXIN) {
            Log.e("微信登录", "openid：" + data.get("openid"));
            Log.e("微信登录", "access_token：" + data.get("access_token"));
            openid = data.get("openid");

            Toast.makeText(LoginActivity.this, data.get("openid"), Toast.LENGTH_LONG).show();
            loadProgressHUD = KProgressHUD.create(this).
                    setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                    setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                    setLabel("正在登录...").setCancellable(true).show();

            OkHttpUtils.get()
                    .url(API.WX_LOGIN_GET)
                    .addParams("weixin_openid", data.get("openid"))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            loadedDismissProgressDialog(LoginActivity.this, false, loadProgressHUD, "服务器未响应", false);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            loginEntity = new Gson().fromJson(response, LoginEntity.class);
                            if (loginEntity.getCode() == 200) {
                                getUserMsg(loginEntity.getData());
                                loadedDismissProgressDialog(LoginActivity.this, true, loadProgressHUD, "登录成功", true);
                            } else if (loginEntity.getCode() == 100) {
                                loadedDismissProgressDialog(LoginActivity.this, false, loadProgressHUD, loginEntity.getMessage(), false);

                            } else {
                                Log.i("weixin1", "onResponse()__对话框");
                                mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {
                                    @Override
                                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                        wx_name = map.get("nickname");
                                        wx_headimage = map.get("headimgurl");
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media, int i) {

                                    }
                                });
                                loadProgressHUD.dismiss();
                                dialog.show();

                            }
                        }
                    });
        }


    }

    @Subscribe
    public void onEventMainThread(LoginEvent loginEvent) {
        if (loginEvent != null) {
            login(loginEvent.getUsername(), loginEvent.getPass());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        Log.e("TAG", "LoginDestory");
    }

    static class ViewHolder {
        @Bind(R.id.et_phone)
        EditText etPhone;
        @Bind(R.id.btn_check)
        Button btnCheck;
        @Bind(R.id.et_code)
        EditText etCode;
        @Bind(R.id.ll_code)
        LinearLayout llCode;
        @Bind(R.id.et_pass)
        EditText etPass;
        @Bind(R.id.ll_pass)
        LinearLayout llPass;
        @Bind(R.id.tv_tips)
        TextView tvTips;
        @Bind(R.id.btn_cancel)
        TextView btnCancel;
        @Bind(R.id.btn_commit)
        TextView btnCommit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

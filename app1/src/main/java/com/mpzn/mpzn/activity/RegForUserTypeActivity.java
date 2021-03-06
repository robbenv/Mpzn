package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.VpAdapterRegFm;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.LoginEvent;
import com.mpzn.mpzn.entity.RegistEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.fragment.RegForType.RegForJJCom;
import com.mpzn.mpzn.fragment.RegForType.RegForJJR;
import com.mpzn.mpzn.fragment.RegForType.RegForKFS;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.dismissDialogWithCallback;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class RegForUserTypeActivity extends BaseActivity implements RegForJJR.RegbtnEnableSetListener,
        RegForJJCom.ComRegbtnEnableSetListener,
        RegForKFS.KfsRegbtnEnableSetListener

{


    private static final String TAG = "RegForUserType";
    static final String[] mPermissionList = new String[]{Manifest.permission.READ_LOGS, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW};
    private static final int REQUEST_CODE = 0; // 请求码

    @Bind(R.id.fl_reg_for_fm)
    FrameLayout flRegForFm;
    @Bind(R.id.vp_fm)
    ViewPager vpFm;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.tv_tips_1)
    TextView tvTips1;
    @Bind(R.id.rb_jingjiren)
    RadioButton rbJingjiren;
    @Bind(R.id.rb_jingjicom)
    RadioButton rbJingjicom;
    @Bind(R.id.rb_kaifashang)
    RadioButton rbKaifashang;
    @Bind(R.id.rg_reg_type)
    RadioGroup rgRegType;
    @Bind(R.id.btn_reg)
    Button btnReg;
    @Bind(R.id.cb_read_xiyi)
    CheckBox cbReadXiyi;
    @Bind(R.id.tv_read)
    TextView tvRead;

    // 权限检测器
    private PermissionsChecker mPermissionsChecker;

    private int userType = Constant.RESCODE_JINGJIREN;

    private String name = "";
    private String company = "";
    private int comMid;

    private String userPhone;
    private String code;
    private String pass;
    private KProgressHUD loadProgressHUD;
    private boolean EditTextEnable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reg_for_user_type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ((RadioButton) rgRegType.findViewById(R.id.rb_jingjiren)).setChecked(true); //避免调用两次onCheckedChanged

    }

    @Override
    public void initHolder() {
        initSystemBar(this, 0);
        myActionBar.init("完成注册", R.drawable.return_white, 0);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
        List<Fragment> fragments = new ArrayList<>();
        RegForJJR regForJJR = new RegForJJR();
        RegForJJCom regForJJCom = new RegForJJCom();
        RegForKFS regForKFS = new RegForKFS();
        fragments.add(regForJJR);
        fragments.add(regForJJCom);
        fragments.add(regForKFS);



        vpFm.setAdapter(new VpAdapterRegFm(getSupportFragmentManager(), fragments));
        vpFm.setOffscreenPageLimit(3);
        Intent intent=getIntent();
        userPhone = intent.getStringExtra("userPhone");
        code = intent.getStringExtra("code");
        pass = intent.getStringExtra("pass");



    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);
        rgRegType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_jingjiren:
                        vpFm.setCurrentItem(0);
                        userType = Constant.RESCODE_JINGJIREN;
                        break;
                    case R.id.rb_jingjicom:
                        vpFm.setCurrentItem(1);
                        userType = Constant.RESCODE_JINGJICOM;
                        break;
                    case R.id.rb_kaifashang:
                        vpFm.setCurrentItem(2);
                        userType = Constant.RESCODE_KAIFASHANG;
                        break;
                }
            }
        });


        vpFm.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgRegType.getChildAt(position)).setChecked(true);
                if(position!=0){
                   btnReg.setEnabled(true);
                }else{
                    btnReg.setEnabled(EditTextEnable&&cbReadXiyi.isChecked());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                loadProgressHUD = KProgressHUD.create(RegForUserTypeActivity.this).
                        setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                        setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                        setLabel("正在注册...").setCancellable(true).show();

         if(vpFm.getCurrentItem() == 1){
             name="";
             if ("".equals(company)) {
                 loadedDismissProgressDialog(RegForUserTypeActivity.this, false, loadProgressHUD, "请输入完整信息", false);
             }
         }


                Logger.d("user = "+userPhone+" \n message = "+code+"\n  pass = "+pass +"  \n usertype = "+userType+"   \n company = "+company +"  \n username = "+name);
                /**
                 * 经纪人：必填username，必不填company（界面中的经纪公司选项是通过agent_id）
                 * 经纪公司：必填company，必不填username
                 * 开发商：必填username，必不填company
                 */
        OkHttpUtils
                .post()
                .url(API.REGIST_POST)
                .addParams("user",userPhone)
                .addParams("message",code)
                .addParams("pass",pass)
                .addParams("usertype",userType+"")
                .addParams("company", company)
                .addParams("username",name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        loadedDismissProgressDialog(RegForUserTypeActivity.this, false, loadProgressHUD, "请检查你的网络...", false);


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        RegistEntity registEntity = new Gson().fromJson(response, RegistEntity.class);
                            if(registEntity.getCode()==200){
                                if(vpFm.getCurrentItem()==0) {
                                    //只有当经纪人注册的时候才会去关联经纪公司
                                    applyJJcom(registEntity.getToken());
                                }else{
                                    setResult(RESULT_OK);
                                    dismissDialogWithCallback(RegForUserTypeActivity.this, true, loadProgressHUD, "注册成功",
                                            new ViewUtils.CallBack() {
                                                @Override
                                                public void afterDialogDismiss() {
                                                    LoginEvent loginEvent = new LoginEvent(userPhone,pass);
                                                    EventBus.getDefault().post(loginEvent);
                                                    finish();
                                                }
                                            });
                                }
                            }else{
                                loadedDismissProgressDialog(RegForUserTypeActivity.this, false, loadProgressHUD, registEntity.getMessage(), false);
                            }



                    }
                });
            }
        });
        cbReadXiyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    btnReg.setEnabled(isChecked&&EditTextEnable);

            }
        });


    }

    private void applyJJcom(String token){
        OkHttpUtils.post()
                .url(API.APPLYJJCOM_POST)
                .addParams("token",token)
                .addParams("agent_id",comMid+"")
                .addParams("action","add")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(RegForUserTypeActivity.this, false, loadProgressHUD, "请检查你的网络...", false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if (simpleEntity.getCode() == 200) {
                            setResult(RESULT_OK);
                            dismissDialogWithCallback(RegForUserTypeActivity.this, true, loadProgressHUD, "注册成功",
                                    new ViewUtils.CallBack() {
                                        @Override
                                        public void afterDialogDismiss() {
                                            LoginEvent loginEvent = new LoginEvent(userPhone,pass);
                                            EventBus.getDefault().post(loginEvent);
                                            finish();
                                        }
                                    });
                        }else{
                            loadedDismissProgressDialog(RegForUserTypeActivity.this, false, loadProgressHUD, simpleEntity.getMessage(), false);

                        }
                    }


                });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }




    //经纪人的Fragment的Edittext的回调
    @Override
    public void setRegbtnEnable(boolean enable, String name, int comMid) {
        if(vpFm.getCurrentItem()==0){
            EditTextEnable=enable;
            btnReg.setEnabled(enable&&cbReadXiyi.isChecked());
            Log.e("TAG", "enable"+enable);
            this.name=name;
            this.comMid=comMid;

        }

    }


    //经纪公司的Fragment的Edittext的回调
    @Override
    public void setRegCombtnEnable(boolean enable, String name) {
        EditTextEnable=enable;
        btnReg.setEnabled(enable&&cbReadXiyi.isChecked());
        company = name;
    }


    @Override
    public void setRegKfsbtnEnable(boolean enable, String name) {
        EditTextEnable=enable;
        btnReg.setEnabled(enable&&cbReadXiyi.isChecked());
        this.name = name;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
//        if (mPermissionsChecker.lacksPermissions(mPermissionList)) {
//            startPermissionsActivity();
//        }

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, mPermissionList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}

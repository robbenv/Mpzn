package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.JPushNotificationEntity;
import com.mpzn.mpzn.entity.LoginEntity;
import com.mpzn.mpzn.entity.UserMsg;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.service.UpdataVersionService;
import com.mpzn.mpzn.utils.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

import static com.mpzn.mpzn.application.MyApplication.mUserMsg;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private final int TOMAIN = 1;
    private final int TOGUID = -1;

    private boolean isFromJpush;
    @Bind(R.id.iv_splash_bg)
    ImageView ivSplashBg;
    private LoginEntity loginEntity;
    private String token;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TOGUID) {
                startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            } else {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                isFromJpush = getIntent().getBooleanExtra("jpush", false);
                if (isFromJpush) {
                    intent.putExtra("jpush", true);
                    Log.i(TAG+"test", "id = "+ getIntent().getIntExtra("Aid", -1));
                    String name = getIntent().getStringExtra("Name");
                    int aId = getIntent().getIntExtra("Aid", -1);
                    String type = intent.getStringExtra("Type");
                    intent.putExtra("Name", name);
                    intent.putExtra("Aid", aId);
                    JPushNotificationEntity jPushNotificationEntity = new JPushNotificationEntity();
                    jPushNotificationEntity.setType(type);
                    jPushNotificationEntity.setId(String.valueOf(aId));
                    EventBus.getDefault().postSticky(jPushNotificationEntity);
                }
                startActivity(intent);

            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMain();
        StartService();

    }

    //启动主页面，如果第一次启动 启动引导页面
    private void startMain() {

        if (!MyApplication.getInstance().isNotFirstRun) {
            mHandler.sendEmptyMessageDelayed(TOGUID, 1000);
        }
    }

    @Override
    public int getLayoutId() {
        setTheme(R.style.AppSplash);
        return R.layout.activity_splash;
    }

    @Override
    public void initHolder() {


    }

    @Override
    public void initLayoutParams() {

    }

    public void StartService() {
        Intent intent = new Intent();
        intent.setClass(this, UpdataVersionService.class);  //启动自动更新服务
        startService(intent);
    }

    @Override
    public void initData() {
        token = CacheUtils.getString(mContext, "token");
        Log.e("TAG", "token" + token);
        if ("0".equals(token)) {
            MyApplication.getInstance().setIsLogined(false);
            mHandler.sendEmptyMessageDelayed(TOMAIN, 1000);
            return;
        }

        OkHttpUtils
                .get()
                .url(API.LOGIN_GET)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(SplashActivity.this, "请检查你的网络...", Toast.LENGTH_SHORT).show();
                        MyApplication.getInstance().setIsLogined(false);
                        token = "0";
                        mHandler.sendEmptyMessageDelayed(TOMAIN, 1000);

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        loginEntity = new Gson().fromJson(response, LoginEntity.class);

                        changeToken();
                    }
                });


    }

    private void getUserMsg() {
        final UserMsg mUserMsg = (UserMsg) CacheUtils.getObject(this, "userMsg");
        if (mUserMsg != null) {
            MyApplication.getInstance().mUserMsg = mUserMsg;
            JPushInterface.setAlias(this, mUserMsg.getPhone()+"_dev", new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.i("jpush_test2", "别名："+mUserMsg.getPhone()+"_dev");
                    //啥也没有
                }
            });
            Toast.makeText(SplashActivity.this, mUserMsg.getPhone()+"_dev", Toast.LENGTH_SHORT).show();

        }
    }


    private void changeToken() {
        Toast.makeText(SplashActivity.this, loginEntity.getMessage(), Toast.LENGTH_SHORT).show();
        if (loginEntity.getCode() == 200) {
            MyApplication.getInstance().setIsLogined(true);
            MyApplication.getInstance().setToken(token);
            getUserMsg();

        } else {
            token = "0";
            CacheUtils.putString(mContext, "token", token);
            MyApplication.getInstance().setIsLogined(false);
        }
        mHandler.sendEmptyMessageDelayed(TOMAIN, 1000);

    }


    @Override
    public void bindListener() {

    }

}

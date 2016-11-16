package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.code19.library.AppUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.LogoutEntity;
import com.mpzn.mpzn.entity.VersionCheck;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.service.UpdataVersionService;
import com.mpzn.mpzn.utils.ACache;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.utils.DataCleanManager;
import com.mpzn.mpzn.views.BadgeView;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;
import com.mpzn.mpzn.views.MyProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;


public class SetupActivity extends BaseActivity {


    @Bind(R.id.btn_account)
    Button btnAccount;
    @Bind(R.id.ll_account)
    LinearLayout llAccount;
    @Bind(R.id.btn_clear_cache)
    Button btnClearCache;
    @Bind(R.id.btn_img_browser_setting)
    Button btnImgBrowserSetting;
    @Bind(R.id.btn_suggest)
    Button btnSuggest;
    @Bind(R.id.btn_check_version)
    Button btnCheckVersion;
    @Bind(R.id.btn_check_net)
    Button btnCheckNet;
    @Bind(R.id.btn_about)
    Button btnAbout;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.tgb_push)
    ToggleButton tgbPush;
    private KProgressHUD loadProgressHUD;
    private MyDialog upDataDialog;
    private MyProgressDialog myProgressDialog;
    private BadgeView pointOnCheckVersion;
    private ACache.ACacheManager aCacheManager;
    private BadgeView pointCachesize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setup;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        myActionBar.init("设置", R.drawable.return_white, 0);
        if (!MyApplication.isLogined) {
            llAccount.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
        }


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        if (CacheUtils.getBoolean(this, "IsCloseJPush")) {
            tgbPush.setChecked(false);
        }else{
            tgbPush.setChecked(true);
        }
        if (CacheUtils.getBoolean(this, "HasNewVersion")) {
            setPoint(btnCheckVersion, "new");
        }
        try {
            String cacheSize = DataCleanManager.getCacheSize(getCacheDir());
            Log.e("TAG", "cacheSize" + cacheSize);
            setCachesize(btnClearCache, cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);
        tgbPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    JPushInterface.resumePush(getApplicationContext());
                }else{
                    JPushInterface.stopPush(getApplicationContext());
                }
                CacheUtils.putBoolean(mContext,"IsCloseJPush",!isChecked);
            }
        });

    }


    @OnClick({R.id.btn_account, R.id.btn_clear_cache, R.id.btn_img_browser_setting, R.id.btn_suggest, R.id.btn_check_version, R.id.btn_check_net, R.id.btn_about, R.id.btn_logout})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_account:
                intent.setClass(mContext, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_clear_cache:
                final MyDialog myDialog = new MyDialog(mContext);
                myDialog.show();
                myDialog.setTitle("清空缓存");
                myDialog.setContent("是否清空缓存");
                myDialog.setCommit("清空");
                myDialog.setCommitListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        DataCleanManager.cleanCustomCache(getCacheDir().getPath());
                        pointCachesize.hide();
                    }
                });
                break;
            case R.id.btn_img_browser_setting:
                intent.setClass(mContext, ImageBrowseSetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_suggest:
                intent.setClass(mContext, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_check_version:
                showProgressDialog("正在检查更新");
                break;
            case R.id.btn_check_net:
                intent.setClass(mContext, CheckNetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_about:
                intent.setClass(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                Logout();
                break;
        }
    }


    private void showProgressDialog(String content) {
        final String version = AppUtils.getAppVersionName(mContext, "com.mpzn.mpzn");
        myProgressDialog = new MyProgressDialog(this);
        myProgressDialog.show();
        content.replace("\\n", "\n");
        myProgressDialog.setContent(content);
        Log.e("TAG", "version" + version);
        OkHttpUtils.get()
                .url(API.CHECK_VERSION)
                .addParams("version", version)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        myProgressDialog.dismiss();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VersionCheck versionCheck = new Gson().fromJson(response, VersionCheck.class);
                        Log.e("TAG", "版本号：" + version + "Json：" + response);
                        if (versionCheck.getCode() == 200) {
                            if (!pointOnCheckVersion.isShown()) {
                                setPoint(btnCheckVersion, "new");
                                CacheUtils.putBoolean(mContext, "HasNewVersion", true);
                            }


                            myProgressDialog.dismiss();
                            showVersionUpdata(versionCheck.getData());
                        } else {
                            pointOnCheckVersion.hide();
                            CacheUtils.putBoolean(mContext, "HasNewVersion", false);
                            myProgressDialog.afterprogress("您已是最新版本");
                        }
                    }
                });

    }

    private void showVersionUpdata(final VersionCheck.DataBean data) {
        String version = data.getVersion();
        upDataDialog = new MyDialog(mContext);
        upDataDialog.show();
        upDataDialog.setTitle("更新 " + version);
        upDataDialog.setCommit("更新");
        upDataDialog.setContent(data.getUpdate_content());
        upDataDialog.setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = data.getSoft_address();
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                File apkfile = new File(Environment.
                        getExternalStorageDirectory().
                        getAbsolutePath() + "/" + fileName);
                if (!apkfile.exists()) {
                    Intent intent = new Intent();
                    intent.setAction(Constant.ACTION_DOWNLOAD_APK);
                    intent.putExtra("apkurl", data.getSoft_address());
                    intent.setClass(mContext, UpdataVersionService.class);
                    startService(intent);

                } else {
                    AppUtils.installApk(SetupActivity.this, apkfile.getAbsolutePath());
                }
                upDataDialog.dismiss();
            }
        });

    }


    public void Logout() {
        if (!MyApplication.isLogined) {
            return;
        }
        loadProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("正在退出...").setCancellable(true).show();
        String token = CacheUtils.getString(mContext, "token");
        OkHttpUtils.get()
                .url(API.LOGOUT_GET)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(SetupActivity.this, false, loadProgressHUD, "网络连接失败", false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogoutEntity logoutEntity = new Gson().fromJson(response, LogoutEntity.class);
                        if (logoutEntity.getCode() == 200) {
                            CacheUtils.putString(mContext, "token", "0");
                            MyApplication.getInstance().setIsLogined(false);
                            MyApplication.getInstance().setToken("0");
                            Intent intent = getIntent();
                            setResult(RESULT_OK, intent);
                            loadedDismissProgressDialog(SetupActivity.this, true, loadProgressHUD, "注销成功", true);
                        } else {
                            loadedDismissProgressDialog(SetupActivity.this, false, loadProgressHUD, logoutEntity.getMessage(), false);
                        }

                    }
                });
    }

    public void setCachesize(Button button, String msg) {
        pointCachesize = new BadgeView(mContext, button);
        pointCachesize.setBadgeBackgroundColor(Color.TRANSPARENT);
        pointCachesize.setTextColor(getResources().getColor(R.color.font_black_5));

        pointCachesize.setText(msg);
        pointCachesize.setBadgePosition(BadgeView.POSITION_RIGHT);
        pointCachesize.setBadgeMargin(dip2px(35), 0);
        pointCachesize.show();

    }

    public void setPoint(Button button, String msg) {
        pointOnCheckVersion = new BadgeView(mContext, button);
        pointOnCheckVersion.setText(msg);
        pointOnCheckVersion.setBadgePosition(BadgeView.POSITION_RIGHT);
        pointOnCheckVersion.setBadgeMargin(dip2px(35), 0);
        pointOnCheckVersion.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

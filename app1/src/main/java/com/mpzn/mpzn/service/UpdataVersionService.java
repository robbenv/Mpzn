package com.mpzn.mpzn.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.code19.library.AppUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.entity.VersionCheck;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
import com.mpzn.mpzn.views.MyDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Icy on 2016/10/19.
 */

public class UpdataVersionService extends Service {

    private ScreenBroadcastReceiver mScreenReceiver;   //屏幕状态和wifi状态广播接收器

    private boolean isScreenOff=false;
    private boolean isWifiEnable=false;
    private String fileName;
    private MyDialog upDataDialog;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG","onCreate");
        mScreenReceiver = new ScreenBroadcastReceiver();
        registerListener();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterListener();
        Log.e("TAG", "unregisterListener");
    }

    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mScreenReceiver, filter);
        Log.e("TAG", "registerReceiver");

    }

    private void unregisterListener() {
        if(mScreenReceiver!=null) {
            unregisterReceiver(mScreenReceiver);
        }
    }

    private void checkVersion(){
        Log.e("TAG","checkVersion()__本地version= "+AppUtils.getAppVersionName(this, "com.mpzn.mpzn"));
        OkHttpUtils.get()
                .url(API.CHECK_VERSION)
                .addParams("version", AppUtils.getAppVersionName(this, "com.mpzn.mpzn"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UpdataVersionService.this, "检查更新联网失败", Toast.LENGTH_SHORT).show();
                        Log.e("TAG","versionCheck" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VersionCheck versionCheck = new Gson().fromJson(response, VersionCheck.class);
                        Log.e("TAG","versionCheck.getCode()"+versionCheck.getCode());
                        if (versionCheck.getCode() == 200) {
                            String url = versionCheck.getData().getSoft_address();
                            fileName = url.substring(url.lastIndexOf("/") + 1);
                            if(!new File(Environment.
                                    getExternalStorageDirectory().
                                    getAbsolutePath()+"/"+fileName).exists()) {
                                VersionCheck.DataBean data = versionCheck.getData();
                                downloadApk(url, false, data);
                            } else {
                                Log.e("TAG","else");
                            }
                            CacheUtils.putBoolean(UpdataVersionService.this,"HasNewVersion",true);
                        } else{
                            CacheUtils.putBoolean(UpdataVersionService.this,"HasNewVersion",false);
                        }
                    }
                });

    }

    //最后没有使用这点是因为service弹dialog有点麻烦
    private void showInstallDialog(VersionCheck.DataBean data, final String apkPath) {
        upDataDialog = new MyDialog(this);
        upDataDialog.show();
        upDataDialog.setTitle("安装 " + data.getVersion());
        upDataDialog.setCommit("安装");
        upDataDialog.setContent(data.getUpdate_content());
        upDataDialog.setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdataVersionService.this, "开始安装...", Toast.LENGTH_SHORT).show();
                AppUtils.installApk(UpdataVersionService.this, apkPath);

                upDataDialog.dismiss();
            }
        });

    }

    private void downloadApk(final String url, final boolean isInstall, final VersionCheck.DataBean data){
        Log.e("TAG","downloadApk()");
        Toast.makeText(UpdataVersionService.this, "正在更新...", Toast.LENGTH_SHORT).show();
        fileName = url.substring(url.lastIndexOf("/") + 1);

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName)//
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(UpdataVersionService.this, "更新失败", Toast.LENGTH_SHORT).show();
                        Log.e("TAG",e.getMessage());
                    }


                    @Override
                    public void onResponse(File response, int id) {
                        String apkPath = response.getAbsolutePath();
                        Log.e("TAG","path = "+response.getAbsolutePath());
                        if(isInstall){
                            //只有在设置里检查更新进入这里后会是true，否则会询问
                            //正常进入程序，如果有更新，会直接下载，但只有第一次会询问是否安装
                            Log.e("TAG","开始安装...");
                            Toast.makeText(UpdataVersionService.this, "开始安装...", Toast.LENGTH_SHORT).show();
                            AppUtils.installApk(UpdataVersionService.this,apkPath);
                        } else {
                            Log.e("TAG","弹对话框...");
//                            showInstallDialog(data, apkPath);
                        }

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
//                       mProgressBar.setProgress((int) (100 * progress));，
                    }
                });
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            String intentAction = intent.getAction();
            String apkurl = intent.getStringExtra("apkurl");
            if (Constant.ACTION_DOWNLOAD_APK.equals(intentAction)) {
                downloadApk(apkurl, true, new VersionCheck.DataBean());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "onReceive");
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                isScreenOff=false;
                Log.e("TAG", "开屏");
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                isScreenOff=true;

                if(isWifiEnable){
                    Log.e("TAG", "锁屏且有WIFI更新");
                   checkVersion();
                }
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                isScreenOff=false;
            } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//在此监听wifi有无
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        isWifiEnable=false;
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        isWifiEnable=true;
                        Log.e("TAG", "有WIFI且锁屏更新");
                        checkVersion();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        break;
                }
            }
        }
    }

}

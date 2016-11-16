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
import android.widget.Toast;

import com.code19.library.AppUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.entity.VersionCheck;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.utils.Constant;
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

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        OkHttpUtils.get()
                .url(API.CHECK_VERSION)
                .addParams("version", AppUtils.getAppVersionName(this, "com.mpzn.mpzn"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VersionCheck versionCheck = new Gson().fromJson(response, VersionCheck.class);
                        Log.e("TAG", "versionCheck.getCode()"+versionCheck.getCode());
                        if (versionCheck.getCode() == 200) {
                            String url = versionCheck.getData().getSoft_address();
                            fileName = url.substring(url.lastIndexOf("/") + 1);
                            if(!new File(Environment.
                                    getExternalStorageDirectory().
                                    getAbsolutePath()+"/"+fileName).exists()) {
                                downloadApk(url,false);
                            }
                            CacheUtils.putBoolean(UpdataVersionService.this,"HasNewVersion",true);
                        } else{
                            CacheUtils.putBoolean(UpdataVersionService.this,"HasNewVersion",false);
                        }
                    }
                });

    }

    private void downloadApk(final String url, final boolean isInstall){
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

                    }


                    @Override
                    public void onResponse(File response, int id) {
                        String apkPath = response.getAbsolutePath();
                        if(isInstall){
                            Toast.makeText(UpdataVersionService.this, "开始安装...", Toast.LENGTH_SHORT).show();
                            AppUtils.installApk(UpdataVersionService.this,apkPath);
                        }

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
//                       mProgressBar.setProgress((int) (100 * progress));
                    }
                });
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            String intentAction = intent.getAction();
            String apkurl = intent.getStringExtra("apkurl");
            if (Constant.ACTION_DOWNLOAD_APK.equals(intentAction)) {
                downloadApk(apkurl, true);
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

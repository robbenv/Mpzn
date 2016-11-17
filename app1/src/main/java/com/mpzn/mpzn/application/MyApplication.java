package com.mpzn.mpzn.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.mpzn.mpzn.entity.UserMsg;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.OkHttpClient;

import static com.mpzn.mpzn.utils.CacheUtils.getBoolean;

/**
 * Created by Icy on 2016/7/11.
 */
public class MyApplication extends Application {

    private List<Activity> activityList = new LinkedList();
    private static MyApplication instance;
    public static String token;
    public static Context mContext;
    public static Boolean isNotFirstRun;
    public static Boolean isLogined;
    public static UserMsg mUserMsg;
    public static boolean isInfect;
    public static int mScreenWidth;
    public static int mScreenHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=getApplicationContext();

        getIsNotFirstRun();

        isInfect=Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;

        // 注册全局异常处理
//        CrashHandler.getInstance().init(this);

        //初始化OkHttp联网
        initOkHttp();

        //极光推送初始化SDK
        JPushInterface.init(this);
        JPushInterface.setAlias(this, "dev", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

                //啥也没有
            }
        });

        //微信 appid appsecret
        PlatformConfig.setWeixin("wx395a9fb9b698fabc", "c387dcbd2a344a2ece68589b624cceac");

        //百度地图初始化 SDK
        SDKInitializer.initialize(mContext);

        //初始化友盟的SDK
        UMShareAPI.get(this);

    }



    public void setIsLogined(Boolean isLogined){

        this.isLogined=isLogined;

    }

    public void setmUserMsg(UserMsg mUserMsg){

        this.mUserMsg=mUserMsg;

    }

    public void setToken(String token){

        this.token=token;
    }

    //初始化OkHttp 生成OkhttpClient
    public void initOkHttp(){
        //Https
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//设置可访问所有的https网站


//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }




    //确定软件是否是第一次运行
    private void  getIsNotFirstRun() {
        isNotFirstRun=getBoolean(this,"isNotFirstRun");
    }


    //单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance()
    {
        if(null == instance)
        {
            instance = new MyApplication();
        }
        return instance;
    }
    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //将容器中某个Activity销毁了
    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    //遍历所有Activity并finish
    public void exit()
    {

        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }

}
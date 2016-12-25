package com.mpzn.mpzn.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by larry on 16-11-16.
 */

public class Utils {
    /**
     * 判断程序是否在运行
     *  @Description    : 这个包名的程序是否在运行
     *  @Method_Name    : isRunningApp
     *  @param context 上下文
     *  @param packageName 判断程序的包名
     *  @return 必须加载的权限
     *      <uses-permission android:name="android.permission.GET_TASKS">
     *  @return         : boolean
     *  @Creation Date  : 2014-10-31 下午1:14:15
     *  @version        : v1.00
     *  @Author         : JiaBin

     *  @Update Date    :
     *  @Update Author  : JiaBin
     */
    public static boolean isRunningApp(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                // find it, break
                break;
            }
        }
        return isAppRunning;
    }
}

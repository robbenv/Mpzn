package com.mpzn.mpzn.javaScript;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;

/**
 * Created by Icy on 2016/8/31.
 */
public class AndroidJavaScript {

    Context context;
    String[] qqpackage = new String[]{"com.tencent.mobileqq",
            "com.tencent.mobileqq.activity.SplashActivity"};
    String[] wxpackage = new String[]{"com.tencent.mm",
            "com.tencent.mm.ui.LauncherUI"};

    public AndroidJavaScript(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void callPhone(final String telphone) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + telphone));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);

    }

    @JavascriptInterface
    public void callQQ(String qq) {
        // 实现调用电话号码

        if (!checkBrowser(qqpackage[0])) {

        } else {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName(qqpackage[0], qqpackage[1]);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        }

    }

    @JavascriptInterface
    public void callWeixin(String weixin) {

        if (!checkBrowser(wxpackage[0])) {

        } else {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName(wxpackage[0], wxpackage[1]);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);

        }

    }

    // 获取在webview上获取js生成的html的源码
    @JavascriptInterface
    public void getSource(String htmlstr) {
        // Log.e("html", htmlstr);
        // String path = c.getFilesDir().getAbsolutePath() + "/serve.html"; //
        // data/data目录

    }
    //检测包名的应用是否已经安装在手机
    public boolean checkBrowser(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

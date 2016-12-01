package com.mpzn.mpzn.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.code19.library.SPUtils;
import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.mpzn.mpzn.activity.DetailNewhouseActivity;
import com.mpzn.mpzn.activity.DetailNewsActivity;
import com.mpzn.mpzn.activity.MainActivity;
import com.mpzn.mpzn.activity.TestActivity;
import com.mpzn.mpzn.activity.WebViewActivity;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.entity.JPushNotificationEntity;
import com.mpzn.mpzn.helper.JpushMessageDBHelper;
import com.mpzn.mpzn.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

import static cn.jpush.android.api.JPushInterface.EXTRA_NOTIFICATION_TITLE;

/**
 * Created by Icy on 2016/11/1.
 */

public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    public static final String TYPE_BUILDING="building";
    public static final String TYPE_NEWS="news";
    public static final String TYPE_HTML = "html";
    public static final String PACKAGENAME = "com.mpzn.mpzn";


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRunning = Utils.isRunningApp(context, PACKAGENAME);
        JpushMessageDBHelper jpushMessageDBHelper = new JpushMessageDBHelper(context, "jpush.db", null, 1);
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        Log.d(TAG, "EXTRA_EXTRA"+bundle.getString(JPushInterface.EXTRA_EXTRA));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d("jpush_test", "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            JPushNotificationEntity jPushNotificationEntity = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), JPushNotificationEntity.class);
            if (isRunning) {
                Log.i("jpush_test", "onReceive()__eventBus__jPushNotificationEntity = "+jPushNotificationEntity.toString());
                EventBus.getDefault().postSticky(jPushNotificationEntity);
            } else {
                Log.i("jpush_test", "onReceive()__存到数据库");
                //如果推送的时候程序没有运行，EventBus不起作用，此时先存到数据库中
                SQLiteDatabase db = jpushMessageDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("aid", jPushNotificationEntity.getId());
                values.put("type", jPushNotificationEntity.getType());
                db.insert("jpush", null, values);
            }



            Log.e("TAG", "eventbus发消息"+jPushNotificationEntity.toString());
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d("jpush_test", "[MyReceiver] 用户点击打开了通知");

            JPushNotificationEntity jPushNotificationEntity = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), JPushNotificationEntity.class);

            String type = jPushNotificationEntity.getType();
            Log.d(TAG, "TYPE_NOTIFICATION_TO"+type);
            if(TYPE_BUILDING.equals(type)){
                //打开自定义的Activity
                Log.e("TAG", "打开楼盘详情页");
//                Intent i = new Intent(context, DetailNewhouseActivity.class);

                Log.i(TAG+"test1", "onReceive()__isRunning = "+isRunning);

                Intent i = isRunning ? new Intent(context, DetailNewhouseActivity.class)
                        : context.getPackageManager().getLaunchIntentForPackage(PACKAGENAME);

                if (i != null) {
                    //                Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Name",bundle.getString(EXTRA_NOTIFICATION_TITLE));
                    i.putExtra("Aid",Integer.parseInt(jPushNotificationEntity.getId()));
                    i.putExtra("Type", jPushNotificationEntity.getType());
                    i.putExtra("jpush", true);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "没有获取到通知信息", Toast.LENGTH_SHORT).show();
                }


            }else if(TYPE_NEWS.equals(type)){

                Intent i = new Intent(context, DetailNewsActivity.class);
                i.putExtra("NewsAid",Integer.parseInt(jPushNotificationEntity.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);

            }else if(TYPE_HTML.equals(type)){

                Intent i = new Intent(context, WebViewActivity.class);
                i.putExtra("url",jPushNotificationEntity.getUrl());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);

            }





//            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {

                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

//    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!StringUtils.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (null != extraJson && extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            context.sendBroadcast(msgIntent);
//        }
//    }


}

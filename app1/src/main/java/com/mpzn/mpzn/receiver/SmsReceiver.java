package com.mpzn.mpzn.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;


import com.mpzn.mpzn.utils.PermissionsChecker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Icy on 2016/9/9.
 */
public class SmsReceiver extends BroadcastReceiver{

    public String smsBody="0";
    private SmsReceiveForRegListener smsReceiveForRegListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        android.os.Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                System.out.println("number:" + msg.getOriginatingAddress()
                        + "   body:" + msg.getDisplayMessageBody() + "  time:"
                        + msg.getTimestampMillis());

                //在这里写自己的逻辑
                if (msg.getOriginatingAddress().equals("106905147733")) {

                    smsBody=msg.getDisplayMessageBody();
                    if(smsReceiveForRegListener!=null) {
                        smsReceiveForRegListener.setBody(smsBody);
                    }

                }

            }
        }
    }

    public interface SmsReceiveForRegListener{
         void setBody(String body);
    }

    public void setSmsReceiveForRegListener(SmsReceiveForRegListener smsReceiveForRegListener){
        this.smsReceiveForRegListener=smsReceiveForRegListener;
    }
}

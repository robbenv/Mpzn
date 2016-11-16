package com.mpzn.mpzn.wxapi;


import android.os.Bundle;
import android.os.PersistableBundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by ntop on 15/9/4.
 */
public class WXEntryActivity extends WXCallbackActivity {
    private IWXAPI api;

    public enum Type {
        SHARE, LOGIN, FAIL
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onReq(BaseReq req) {
        // TODO Auto-generated method stub
        super.onReq(req);
    }

    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
//
//
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
////                登录成功的回调
////                登录成功后resp的返回类型为 SendAuth.Resp
//                if (resp instanceof SendAuth.Resp) {
//                    SendAuth.Resp sResp = (SendAuth.Resp) resp;
//                    EventBus.getDefault().post(sResp);
//                } else {
////                    分享成功的回调
//                    EventBus.getDefault().post(Type.SHARE);
//                }
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                Toast.makeText(this, "取消操作", Toast.LENGTH_SHORT).show();
//                EventBus.getDefault().post(Type.FAIL);
//                break;
//            default:
//
//                Toast.makeText(this, "操作失败"+resp, Toast.LENGTH_SHORT).show();
//                EventBus.getDefault().post(Type.FAIL);
//                break;
//        }
////在结果成功回调后接受当前的Activity
//        this.finish();

    }

}

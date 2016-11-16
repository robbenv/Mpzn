package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.code19.library.NetUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.views.MyActionBar;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class CheckNetActivity extends BaseActivity {


    @Bind(R.id.tv_success)
    TextView tvSuccess;
    @Bind(R.id.ll_checknet_ing)
    LinearLayout llChecknetIng;
    @Bind(R.id.btn_check_net)
    Button btnCheckNet;
    private static final int MODE_CHECK_BEFORE = -1;
    private static final int MODE_CHECK_ING = 0;
    private static final int MODE_CHECK_AFTER = 1;
    @Bind(R.id.action_bar)
    MyActionBar actionBar;

    private int mode = MODE_CHECK_BEFORE;    //未诊断
    private KProgressHUD loadProgressHUD;
    private boolean networkAvailable;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            changeMode(mode+1);
            }
        };


    @Override
    public int getLayoutId() {
        return R.layout.activity_check_net;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, 0);
        actionBar.init("诊断网络", R.drawable.return_white, 0);
        changeMode(mode);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(null,null);
        btnCheckNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode==MODE_CHECK_BEFORE) {
                    changeMode(mode+1);
                    handler.sendEmptyMessageDelayed(0,3000);
                }else if(mode==MODE_CHECK_AFTER){
                    loadProgressHUD = KProgressHUD.create(CheckNetActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("正在发送...").setCancellable(true).show();
                    loadedDismissProgressDialog(CheckNetActivity.this, true, loadProgressHUD, "发送成功", false);
                    mode=-2;
                    handler.sendEmptyMessageDelayed(0,1000);

                }


            }
        });
    }

    private void changeMode(int mode) {
        if(mode>1){
            mode=0;
        }
        this.mode=mode;
        switch (mode) {
            case  MODE_CHECK_BEFORE:
                tvSuccess.setVisibility(View.GONE);
                llChecknetIng.setVisibility(View.GONE);
                btnCheckNet.setEnabled(true);
                btnCheckNet.setText("诊断网络");
                break;
            case  MODE_CHECK_ING:
                tvSuccess.setVisibility(View.GONE);
                llChecknetIng.setVisibility(View.VISIBLE);
                btnCheckNet.setEnabled(false);
                btnCheckNet.setText(getResources().getString(R.string.tv_check_net_ing));
                networkAvailable = NetUtils.isConnected(CheckNetActivity.this);
                Log.e("TAG", "networkAvailable"+networkAvailable);

                break;
            case  MODE_CHECK_AFTER:
                tvSuccess.setVisibility(View.VISIBLE);
                llChecknetIng.setVisibility(View.GONE);
                btnCheckNet.setEnabled(true);
                btnCheckNet.setText("发送");
                if (networkAvailable) {
                    tvSuccess.setText(getResources().getString(R.string.check_net_suc));
                }else{
                    tvSuccess.setText(getResources().getString(R.string.check_net_err));
                    btnCheckNet.setEnabled(false);
                }
                break;
        }

    }


}

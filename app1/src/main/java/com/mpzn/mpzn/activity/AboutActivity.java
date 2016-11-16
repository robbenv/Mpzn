package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.code19.library.AppUtils;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
    };
    // 权限检测器
    private PermissionsChecker mPermissionsChecker;

    @Bind(R.id.action_bar)
    MyActionBar actionBar;
    @Bind(R.id.tv_app)
    TextView tvApp;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.rl_call)
    RelativeLayout rlCall;
    @Bind(R.id.tv_jjcom_call)
    TextView tvJjcomCall;
    @Bind(R.id.activity_about)
    RelativeLayout activityAbout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initHolder() {
        initSystemBar(this,0);
        actionBar.init("关于",R.drawable.return_white,0);
        tvApp.setText(AppUtils.getAppName(this,"com.mpzn.mpzn"));
        tvVersion.setText(AppUtils.getAppVersionName(this,"com.mpzn.mpzn"));

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }


    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(null,null    );
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(getResources().getString(R.string.phone_kefu));
            }
        });
        tvJjcomCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogined
                        &&MyApplication.getInstance().mUserMsg.getmChild()==3){
                    callPhone(getResources().getString(R.string.phone_for_jingjicom));

                }
            }
        });
    }

    private void callPhone(final String phone) {
        final MyDialog myDialog = new MyDialog(mContext);
        myDialog.setTitle("客服电话");
        myDialog.setContent("是否拨打电话给 "+phone);
        myDialog.setCommit("拨打");
        myDialog.show();
        myDialog.setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);

            }
        });

    }


}

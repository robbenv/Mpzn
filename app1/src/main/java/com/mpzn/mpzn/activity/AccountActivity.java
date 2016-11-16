package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AccountEntity;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.views.MyActionBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity {


    @Bind(R.id.action_bar)
    MyActionBar actionBar;
    @Bind(R.id.tv_login_name)
    TextView tvLoginName;
    @Bind(R.id.btn_login_name)
    LinearLayout btnLoginName;
    @Bind(R.id.btn_account_password)
    LinearLayout btnAccountPassword;
    @Bind(R.id.btn_account_phonenum)
    LinearLayout btnAccountPhonenum;
    @Bind(R.id.btn_other_bind)
    LinearLayout btnOtherBind;
    @Bind(R.id.btn_account)
    LinearLayout btnAccount;
    @Bind(R.id.activity_account)
    LinearLayout activityAccount;
    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        actionBar.init("账号与安全", R.drawable.return_white, 0);

    }

    @Override
    public void initLayoutParams() {
        AccountEntity account = (AccountEntity) CacheUtils.getObject(mContext, "account");

        tvLoginName.setText(account.getUsername());

        tvPhone.setText(account.getUsername().substring(0,3)+"****"+account.getUsername().substring(8,11));
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(null, null);

    }


    @OnClick({R.id.btn_login_name, R.id.btn_account_password, R.id.btn_account_phonenum, R.id.btn_other_bind, R.id.btn_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_name:
                Toast.makeText(AccountActivity.this, "暂不支持修改登录名", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_account_password:
                Intent intent = new Intent();
                intent.setClass(mContext, ChangePassActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_account_phonenum:
                Toast.makeText(AccountActivity.this, "暂不支持修改绑定手机号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_other_bind:
                Toast.makeText(AccountActivity.this, "暂未开放，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_account:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

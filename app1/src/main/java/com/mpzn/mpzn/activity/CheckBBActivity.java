package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;
import com.mpzn.mpzn.views.MyProgressDialog;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.application.MyApplication.token;

public class CheckBBActivity extends BaseActivity {

    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.et_name_zhiye)
    EditText etNameZhiye;
    @Bind(R.id.et_phone_zhiye)
    EditText etPhoneZhiye;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_bb;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);

        myActionBar.init("驻场核验", R.drawable.return_white, 0);
        myActionBar.setRightText("提交");
        myActionBar.setRightTextColor(R.color.white);
    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weihao = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String zhiyezhuanyuan = etNameZhiye.getText().toString().trim();
                String zhuanyuandianhua = etPhoneZhiye.getText().toString().trim();
                if(weihao.length()!=4){
                    Toast.makeText(CheckBBActivity.this, "请输入正确的客户手机尾号", Toast.LENGTH_SHORT).show();
                }else if(code.length()!=5){
                    Toast.makeText(CheckBBActivity.this, "请输入正确的核验码", Toast.LENGTH_SHORT).show();
                }else if(zhiyezhuanyuan.length()<2){
                    Toast.makeText(CheckBBActivity.this, "请输入置业顾问姓名", Toast.LENGTH_SHORT).show();
                }else if(zhuanyuandianhua.length()<11){
                    Toast.makeText(CheckBBActivity.this, "请输入置业顾问手机号", Toast.LENGTH_SHORT).show();
                }else{
                    final MyProgressDialog myProgressDialog = new MyProgressDialog(mContext);
                    myProgressDialog.show();
                    myProgressDialog.setContent("正在提交");

                    OkHttpUtils.post()
                            .url(API.CHECKBB_POST)
                            .addParams("token",token)
                            .addParams("weihao",weihao)
                            .addParams("code",code)
                            .addParams("zhiyezhuanyuan",zhiyezhuanyuan)
                            .addParams("zhuanyuandianhua",zhuanyuandianhua)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Logger.d(e.getMessage());
                                    myProgressDialog.afterprogress("服务器未响应");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                                    if(simpleEntity.getCode()==200){
                                        myProgressDialog.afterprogress("核验成功！");
                                        Logger.d("核验成功！");
                                    }else{
                                        myProgressDialog.afterprogress("报备失败:"+simpleEntity.getMessage());
                                        Logger.d(simpleEntity.getMessage());
                                    }
                                }
                            });

                }
            }
        });
    }


}

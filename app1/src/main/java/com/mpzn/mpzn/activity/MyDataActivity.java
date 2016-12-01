package com.mpzn.mpzn.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.CheckJJRComEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.entity.UserMsg;
import com.mpzn.mpzn.entity.UserMsgEntity;
import com.mpzn.mpzn.fragment.RegForType.RegForJJR;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.AddJJRComFragmentDialog;
import com.mpzn.mpzn.views.MyActionBar;
import com.mpzn.mpzn.views.MyDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;
import org.json.JSONStringer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.CacheUtils.getObject;
import static com.mpzn.mpzn.utils.CacheUtils.putObject;
import static com.mpzn.mpzn.utils.Constant.REQCODE_CHANGICON;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJIREN;
import static com.mpzn.mpzn.utils.Constant.RESCODE_KAIFASHANG;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;
import static com.mpzn.mpzn.utils.ViewUtils.showCustomProgressDialog;

public class MyDataActivity extends BaseActivity {


    @Bind(R.id.action_bar)
    MyActionBar actionBar;
    @Bind(R.id.iv_my_icon)
    ImageView ivMyIcon;
    @Bind(R.id.btn_my_icon)
    RelativeLayout btnMyIcon;
    @Bind(R.id.btn_nickname)
    Button btnNickname;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.btn_email)
    Button btnEmail;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.btn_jingjiren_com)
    Button btnJingjirenCom;
    @Bind(R.id.tv_jingjiren_com)
    TextView tvJingjirenCom;
    @Bind(R.id.btn_jingji_come)
    Button btnJingjiCome;
    @Bind(R.id.tv_jingji_com)
    TextView tvJingjiCom;
    @Bind(R.id.btn_kaifashang_com)
    Button btnKaifashangCom;
    @Bind(R.id.tv_kaifashang_com)
    TextView tvKaifashangCom;
    @Bind(R.id.btn_phone)
    Button btnPhone;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.rl_jingjiren_com)
    RelativeLayout rlJingjirenCom;
    @Bind(R.id.rl_jingji_com)
    RelativeLayout rlJingjiCom;
    @Bind(R.id.rl_kaifashang_com)
    RelativeLayout rlKaifashangCom;

    private TextView tv_company;
    private KProgressHUD loadProgressHUD;
    private UserMsgEntity userMsgEntity;
    private Bitmap bitmap;
    private CheckJJRComEntity checkJJRComEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_data;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.red_theme);
        actionBar.init("我的资料", R.drawable.return_white, 0);

    }

    @Override
    public void initLayoutParams() {

    }
    private void setUserMsg(TextView textView,String getMsg){
        if(getMsg.length()!=0){
            textView.setText(getMsg);
            textView.setTextColor(getResources().getColor(R.color.font_black_5));
        }else{
            if(textView==tvJingjirenCom){
                checkJjrCom();
            }else {
                textView.setText("未填写");
                textView.setTextColor(getResources().getColor(R.color.red_theme));
            }
        }

    }
    private void checkJjrCom(){
        OkHttpUtils.get()
                .url(API.CHECK_HAS_JJCOM)
                .addParams("token",MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        checkJJRComEntity = new Gson().fromJson(response, CheckJJRComEntity.class);
                        int code = checkJJRComEntity.getCode();
                       switch (code) {
                           case  200:
                               btnJingjirenCom.setCompoundDrawables(null,null,null,null);
                               break;
                           case  1234:
                               tvJingjirenCom.setText("未申请");
                               tvJingjirenCom.setTextColor(getResources().getColor(R.color.red_theme));
                               break;

                           case  1235:
                               tvJingjirenCom.setText("审核中");
                               tvJingjirenCom.setTextColor(getResources().getColor(R.color.orange));
                               break;
                       }

                    }
                });
    }

    public void updataView(){
        setUserMsg(tvNickname,userMsgEntity.getData().getName());
        setUserMsg(tvPhone,userMsgEntity.getData().getPhone());
        setUserMsg( tvEmail,userMsgEntity.getData().getEmail());
        setUserMsg( tv_company,userMsgEntity.getData().getCompanyName());
    }

    @Override
    public void initData() {
        final UserMsg mUserMsg = MyApplication.getInstance().mUserMsg;
       switch (mUserMsg.getmChild()) {
           case RESCODE_JINGJIREN :
               rlJingjirenCom.setVisibility(View.VISIBLE);
               tv_company=tvJingjirenCom;
               break;
           case RESCODE_JINGJICOM:
               rlJingjiCom.setVisibility(View.VISIBLE);
               tv_company=tvJingjiCom;
               break;
           case RESCODE_KAIFASHANG:
               rlKaifashangCom.setVisibility(View.VISIBLE);
               tv_company=tvKaifashangCom;
               break;
       }
        mImageManager.loadCircleImage(mUserMsg.getmIconUrl(),ivMyIcon);
        userMsgEntity= (UserMsgEntity) getObject(mContext,"userMsgEntity");
        if(userMsgEntity!=null){
            updataView();
            Log.e("TAG", "userMsgEntity"+userMsgEntity);
        }
        OkHttpUtils.get()
                .url(API.USERMSG_GET)
                .addParams("token",MyApplication.getInstance().token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        try{
                            showCustomProgressDialog(MyDataActivity.this,"加载用户信息失败",R.drawable.toast_error);
                        }catch (Exception exception){}
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        userMsgEntity = new Gson().fromJson(response, UserMsgEntity.class);
                        if(userMsgEntity.getCode()==200){
                            putObject(mContext,"userMsgEntity",userMsgEntity);
                            Log.i("bug_browse", "onResponse()__userMsgEntity = "+userMsgEntity.getData().getName());
                            updataView();

                        }else {
                            showCustomProgressDialog(MyDataActivity.this,userMsgEntity.getMessage(),R.drawable.toast_error);

                        }

                    }
                });


    }

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        String mName = tvNickname.getText().toString();
        if(!mName.equals("未填写")){
            intent.putExtra("Name",mName);
            setResult(RESULT_OK,intent);
        }

        super.onBackPressed();


    }

    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String mName = tvNickname.getText().toString();
                if(!mName.equals("未填写")){
                    intent.putExtra("Name",mName);
                    setResult(RESULT_OK,intent);
                }
                finish();

            }
        }, null);
        btnJingjirenCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=tvJingjirenCom.getText().toString();
                if("未申请".equals(str)){

                    AddJJRComFragmentDialog dialog = new AddJJRComFragmentDialog();
                    dialog.show(getFragmentManager(),"addjjrcom");
                    dialog.setCommitListener(new AddJJRComFragmentDialog.CommitListener() {
                        @Override
                        public void setCommitListner(boolean isSuccess,String comName) {
                            if(isSuccess){
                                checkJJRComEntity.getData().setCompany_name(comName);
                                tvJingjirenCom.setText("审核中");
                                tvJingjirenCom.setTextColor(getResources().getColor(R.color.orange));
                            }
                        }
                    });

                }else if("审核中".equals(str)){
                    final MyDialog myDialog = new MyDialog(mContext);
                    myDialog.show();
                    myDialog.setTitle("正在审核中");
                    myDialog.setCommit("取消申请");
                    myDialog.setContent(checkJJRComEntity.getData().getCompany_name());

                    myDialog.setCommitListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            v.setEnabled(false);
                            OkHttpUtils.post()
                                    .url(API.APPLYJJCOM_POST)
                                    .addParams("token",MyApplication.getInstance().token)
                                    .addParams("action","cancel")
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            Toast.makeText(mContext, "服务器未响应", Toast.LENGTH_SHORT).show();
                                            v.setEnabled(true);
                                        }


                                        @Override
                                        public void onResponse(String response, int id) {
                                            SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);

                                            if(simpleEntity.getCode()==200){
                                                Toast.makeText(mContext, "取消申请成功", Toast.LENGTH_SHORT).show();

                                                tvJingjirenCom.setText("未申请");
                                                tvJingjirenCom.setTextColor(getResources().getColor(R.color.red_theme));
                                                myDialog.dismiss();
                                            }else{
                                                Toast.makeText(mContext, simpleEntity.getMessage(), Toast.LENGTH_SHORT).show();
                                                v.setEnabled(true);
                                            }


                                        }
                                    });
                        }
                    });
                }else{

                }
            }
        });

    }



    @OnClick({R.id.btn_my_icon, R.id.btn_nickname,R.id.btn_phone,R.id.btn_email, R.id.btn_jingji_come, R.id.btn_kaifashang_com})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_icon:
                Intent intent = new Intent(mContext, ChangIconActivity.class);
                startActivityForResult(intent, REQCODE_CHANGICON);
                break;
            case R.id.btn_nickname:
                showEditDialog(this, "你的姓名", tvNickname);
                break;
            case R.id.btn_phone:
                showEditDialog(this, "你的电话", tvPhone);
                break;
            case R.id.btn_email:
                showEditDialog(this, "你的邮箱", tvEmail);
                break;
//            case R.id.btn_jingji_come:
//                showEditDialog(this, "你公司的名称", tvJingjiCom);
//                break;
//            case R.id.btn_kaifashang_com:
//                showEditDialog(this, "你公司的名称", tvKaifashangCom);
//                break;
        }
    }
    private String initParas(TextView tv){
        if(tv.getText().toString().equals("未填写")){
            return "";
        }else{
            return tv.getText().toString();
        }

    }

    private void commitUserMsg(final TextView textView, final String commitMsg){
        String paramskey = null;
        if(textView==tvNickname){
            paramskey = "name";
        }else if(textView==tvPhone){
            paramskey = "phone";
        }else if(textView==tvEmail){
            paramskey = "email";
        }

        loadProgressHUD= KProgressHUD.create(this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在提交...").setCancellable(true).show();

        Log.e("TAG", "paramskey"+paramskey+"commitMsg"+commitMsg);
        OkHttpUtils.post()
                .url(API.COMMITUSERMSG_POST)
                .addParams("token",MyApplication.getInstance().token)
                .addParams(paramskey,commitMsg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(MyDataActivity.this,false,loadProgressHUD,"修改失败",false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if(simpleEntity.getCode()==200){
                            loadedDismissProgressDialog(MyDataActivity.this,true,loadProgressHUD,"修改成功",false);
                            textView.setText(commitMsg);
                            textView.setTextColor(getResources().getColor(R.color.font_black_5));
                        }else{
                            loadedDismissProgressDialog(MyDataActivity.this,false,loadProgressHUD,simpleEntity.getMessage(),false);
                        }

                    }
                });
    }

    //显示带输入框的AlertDialog
    private void showEditDialog(Context context, String title, final TextView textView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View textEntryView = inflater.inflate(
                R.layout.edit_dialoglayout, null);
        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);
        final MyDialog myDialog = new MyDialog(mContext);
        myDialog.show();
        myDialog.setTitle(title);
        myDialog.setContentView(textEntryView);
        myDialog.setCommit("提交");

        if(title.contains("电话")) {
            edtInput.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if(title.contains("邮箱")){
            edtInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        if(!textView.getText().equals("未填写")) {
            edtInput.setText(textView.getText());
        }
        myDialog.setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtInput.length()!=0) {
                    myDialog.dismiss();
                    commitUserMsg(textView,edtInput.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQCODE_CHANGICON:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mImageManager.loadCircleImage(MyApplication.getInstance().mUserMsg.getmIconUrl(), ivMyIcon);
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                }
                break;
        }


    }
}

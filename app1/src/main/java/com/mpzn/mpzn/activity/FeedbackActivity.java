package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class FeedbackActivity extends BaseActivity {


    @Bind(R.id.aciton_bar)
    MyActionBar acitonBar;
    @Bind(R.id.et_feedback)
    EditText etFeedback;
    @Bind(R.id.et_contact)
    EditText etContact;
    @Bind(R.id.activity_feedback)
    RelativeLayout activityFeedback;
    private KProgressHUD loadProgressHUD;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initHolder() {
        initSystemBar(this,0);
        acitonBar.init("意见反馈",R.drawable.return_white,0);
        acitonBar.setRightText("提交");
        acitonBar.setRightTextColor(R.color.white);

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        acitonBar.setLROnClickListener(null,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitFeedback();
            }
        });
    }


    private void commitFeedback() {
        if(etFeedback.getText().toString().trim().length()<10){
            Toast.makeText(FeedbackActivity.this, "输入内容不得少于10个字符", Toast.LENGTH_SHORT).show();
            return;
        }
        loadProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("正在提交...").setCancellable(true).show();
        OkHttpUtils.post()
                .url(API.FEEDBACK_POST)
                .addParams("uname",etContact.getText().toString())
                .addParams("content",etFeedback.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.d(e.getMessage());
                        loadedDismissProgressDialog(FeedbackActivity.this, false, loadProgressHUD, "提交失败", false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadedDismissProgressDialog(FeedbackActivity.this, true, loadProgressHUD, "反馈成功,感谢您的建议，我们会即使处理！", true);
                    }
                });

    }


}

package com.mpzn.mpzn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.RvReviewAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.ReviewListEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.DividerItemDecoration;
import com.mpzn.mpzn.views.MyActionBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;
import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class ReviewListActivity extends BaseActivity {


    @Bind(R.id.tv_review_bar)
    TextView tvReviewBar;
    @Bind(R.id.review_bar)
    LinearLayout reviewBar;
    @Bind(R.id.et_review)
    EditText etReview;
    @Bind(R.id.tv_review_send)
    TextView tvReviewSend;
    @Bind(R.id.review_et_bar)
    LinearLayout reviewEtBar;
    @Bind(R.id.rv_review_list)
    RecyclerView rvReviewList;
    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    private KProgressHUD loadProgressHUD;
    private int aid;
    private int offset = 0;
    private List<ReviewListEntity.DataBean> reviewdatalist = new ArrayList<>();
    private RvReviewAdapter rvReviewAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_list;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        myActionBar.init("评论列表", R.drawable.return_red, 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvReviewList.setLayoutManager(linearLayoutManager);
        rvReviewAdapter = new RvReviewAdapter(mContext, reviewdatalist);

        rvReviewList.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));

        rvReviewList.setAdapter(rvReviewAdapter);


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        aid = intent.getIntExtra("Aid", 0);

        OkHttpUtils.get()
                .url(API.REVIEW_LIST_GET)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("aid", aid + "")
                .addParams("offset", offset + "")
                .addParams("type", "from_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ReviewListEntity reviewListEntity = new Gson().fromJson(response, ReviewListEntity.class);
                            if (reviewListEntity.getCode() == 200) {
                                reviewdatalist = reviewListEntity.getData();

                                rvReviewAdapter.updataView(reviewdatalist);
                            } else {
                                Toast.makeText(ReviewListActivity.this, "获取评论列表失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }


                    }
                });


    }

    @Override
    public void bindListener() {
        myActionBar.setLROnClickListener(null, null);

        tvReviewBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewEtBar.setVisibility(View.VISIBLE);
                etReview.requestFocus();
            }
        });
        etReview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) etReview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!hasFocus) {
                    reviewEtBar.setVisibility(View.GONE);
                    etReview.setText(null);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                } else {
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

                }

            }
        });
        etReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    tvReviewSend.setEnabled(true);
                } else {
                    tvReviewSend.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvReviewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview();
            }

        });
        etReview.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    sendReview();
                }
                return false;
            }
        });


    }

    private void sendReview() {
        if (MyApplication.getInstance().token == null) {
            ViewUtils.showCustomProgressDialog(ReviewListActivity.this, "请先登录", R.drawable.toast_error);
            return;
        }
        loadProgressHUD = KProgressHUD.create(ReviewListActivity.this).
                setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在发表...").setCancellable(true).show();
        OkHttpUtils.post()
                .url(API.SEND_REVIEW_POST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("aid", aid + "")
                .addParams("content", etReview.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(ReviewListActivity.this, false, loadProgressHUD, "请检查你的网络...", false);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if (simpleEntity.getCode() == 200) {
                            loadedDismissProgressDialog(ReviewListActivity.this, true, loadProgressHUD, "评论成功", false);
                            etReview.clearFocus();
                            initData();
                        } else {
                            loadedDismissProgressDialog(ReviewListActivity.this, false, loadProgressHUD, simpleEntity.getMessage(), false);

                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {
        if (etReview.hasFocus()) {
            reviewEtBar.setVisibility(View.GONE);
            etReview.setText(null);
        } else {
            super.onBackPressed();
        }

    }

}

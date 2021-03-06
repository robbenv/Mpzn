package com.mpzn.mpzn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dss886.emotioninputdetector.library.EmotionInputDetector;
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
import com.tb.emoji.Emoji;
import com.tb.emoji.EmojiUtil;
import com.tb.emoji.FaceFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

public class ReviewListActivity extends BaseActivity implements FaceFragment.OnEmojiClickListener {


    @Bind(R.id.my_action_bar)
    MyActionBar myActionBar;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.rv_review_list)
    RecyclerView rvReviewList;

    @Bind(R.id.et_review)
    EditText etReview;
    @Bind(R.id.bt_emoji)
    Button btEmoji;

    @Bind(R.id.review_et_bar)
    LinearLayout reviewEtBar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.rl_root_view)
    LinearLayout rlRootView;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;
    @Bind(R.id.tv_review_send)
    TextView tvReviewSend;
    private KProgressHUD loadProgressHUD;
    private int aid;
    private int offset = 0;
    private List<ReviewListEntity.DataBean> reviewdatalist = new ArrayList<>();
    private RvReviewAdapter rvReviewAdapter;
    private boolean isShowEmojiPanel;
    private boolean isFoucse;
    private EmotionInputDetector mDetector;
    private FaceFragment faceFragment;

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

        faceFragment = FaceFragment.Instance();

        mDetector = EmotionInputDetector.with(ReviewListActivity.this)
                .setEmotionView(container)
                .bindToContent(rlContent)
                .bindToEditText(etReview)
                .bindToEmotionButton(btEmoji)
                .bindtoFragment(faceFragment)
                .build();

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

                                Log.i("Proxy_test44", "dataBean == " + reviewdatalist.get(0).getContent());

                                for (ReviewListEntity.DataBean dataBean : reviewdatalist) {
                                    Log.i("Proxy_test445", "接收 ： " + dataBean.getContent().toString() + "    cid = " + dataBean.getCid()
                                            + "    aid = " + aid);
                                }
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
        etReview.setFocusable(true);
        etReview.setFocusableInTouchMode(true);
        myActionBar.setLROnClickListener(null, null);




        etReview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("Emoji_test1", "onFocusChange()__");
                InputMethodManager imm = (InputMethodManager) etReview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                isFoucse = hasFocus;
                if (!hasFocus) {
                    Log.i("Emoji_test", "onFocusChange()__隐藏");
                    //隐藏编辑框
//                    reviewEtBar.setVisibility(View.GONE);
//                    etReview.setText(null);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘

                } else {
                    Log.i("Emoji_test", "onFocusChange()__显示1");
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
//                    imm.showSoftInput(container,InputMethodManager.SHOW_FORCED);
                }

            }
        });

        tvReviewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Emoji_test1", "onClick()__tvReviewSend");
                sendReview();
            }

        });
        etReview.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("Emoji_test1", "onEditorAction()__");
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

        if ("".equals(etReview.getText().toString())) {
            ViewUtils.showCustomProgressDialog(ReviewListActivity.this, "请输入评论内容", R.drawable.toast_error);
            return;
        }

        Log.i("Proxy_test445", "发送 ： " + etReview.getText() + "  aid = " + aid);
        loadProgressHUD = KProgressHUD.create(ReviewListActivity.this).
                setSize(MyApplication.mScreenWidth / 4, MyApplication.mScreenWidth / 6).
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
//                            etReview.clearFocus();
                            etReview.setText("");
                            if (!mDetector.isSoftInputShown()) {
                                mDetector.hideEmotionLayout(false);
                            }
                            initData();
                        } else {
                            loadedDismissProgressDialog(ReviewListActivity.this, false, loadProgressHUD, simpleEntity.getMessage(), false);
                            etReview.setText("");
                            if (!mDetector.isSoftInputShown()) {
                                mDetector.hideEmotionLayout(false);
                            }
                        }

                    }
                });


    }

    @Override
    public void onBackPressed() {
        if (mDetector.interceptBackPress()) {
            Log.i("Emoji_test1", "onBackPressed()__!mDetector.interceptBackPress()");
            //do nothing
        } else {
            Log.i("Emoji_test1", "onBackPressed()__noFous");
            super.onBackPressed();
        }

    }

    /**
     * 判断一个字符串中是否包含有Emoji表情
     * (暂时没用到)
     *
     * @param input
     * @return true 有Emoji
     */
    private boolean isEmojiCharacter(CharSequence input) {
        for (int i = 0; i < input.length(); i++) {
            if (isEmojiCharacter(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是Emoji 表情,抄的那哥们的代码
     * (暂时没用到)
     *
     * @param codePoint
     * @return true 是Emoji表情
     */
    public static boolean isEmojiCharacter(char codePoint) {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

        return !isScopeOf;
    }


    @Override
    public void onEmojiDelete() {
        String text = etReview.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                etReview.onKeyDown(KeyEvent.KEYCODE_DEL, event);
//                displayTextView();
                return;
            }
            etReview.getText().delete(index, text.length());
//            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        etReview.onKeyDown(KeyEvent.KEYCODE_DEL, event);
    }

    @Override
    public void onEmojiClick(Emoji emoji) {
        if (emoji != null) {
            int index = etReview.getSelectionStart();
            Editable editable = etReview.getEditableText();
            Log.i("Emoji_test", "onEmojiClick()__index = " + index);
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                try {
                    EmojiUtil.handlerEmojiText(etReview, emoji.getContent(), this, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

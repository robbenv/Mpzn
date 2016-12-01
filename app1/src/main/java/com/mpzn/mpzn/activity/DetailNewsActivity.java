package com.mpzn.mpzn.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.entity.AbstractEntity;
import com.mpzn.mpzn.entity.MessageEntity;
import com.mpzn.mpzn.entity.NewsEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.utils.PermissionsChecker;
import com.mpzn.mpzn.utils.ViewUtils;
import com.mpzn.mpzn.views.MyActionBar;
import com.tb.emoji.Emoji;
import com.tb.emoji.EmojiUtil;
import com.tb.emoji.FaceFragment;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.loadedDismissProgressDialog;

/**
 * Created by Icy on 2016/8/16.
 */
public class DetailNewsActivity extends BaseActivity  implements FaceFragment.OnEmojiClickListener {

    @Bind(R.id.my_action_bar)
    MyActionBar actionBar;
    @Bind(R.id.wv_news_detail)
    WebView wvNewsDetail;
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


    @Bind(R.id.bt_emoji)
    Button btEmoji;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.rl_root_view)
    LinearLayout rlRootView;

    @Bind(R.id.btn_open_review)
    Button btnOpenReview;

    private FaceFragment faceFragment;

    private EmotionInputDetector mDetector;


    private String newsEntityJson;
    private NewsEntity.DataEntity data;
    private String contentUrl;
    private int newsAid;

    private WebSettings settings;

    private KProgressHUD loadProgressHUD;

    private MessageEntity messageEntity;

    static final String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS};

    // 权限检测器
    private PermissionsChecker mPermissionsChecker;

    private static final int REQUEST_CODE = 0; // 请求码


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(DetailNewsActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailNewsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(DetailNewsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(DetailNewsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_news;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        faceFragment = FaceFragment.Instance();
    }

    @Override
    public void initLayoutParams() {


    }

    private void initWebView() {


        settings = wvNewsDetail.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wvNewsDetail.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            wvNewsDetail.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存

        wvNewsDetail.setHorizontalScrollbarOverlay(true);
        wvNewsDetail.setHorizontalScrollBarEnabled(false);
        wvNewsDetail.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        wvNewsDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        wvNewsDetail.requestFocus();
        Log.e("TAG", "contentUrl" + contentUrl);
        wvNewsDetail.loadUrl(contentUrl);
//        wv_news_detail.addJavascriptInterface(new AndroidJavaScript(this), "Android");

        wvNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wvNewsDetail.loadUrl("javascript:hideHead()");

            }
        });


    }

    @Override
    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
        Intent intent = getIntent();
        newsAid = intent.getIntExtra("NewsAid", -1);

        initAbtract();


        contentUrl = API.NEWDETAIL + newsAid;
        actionBar.init("新闻资讯", R.drawable.return_red, R.drawable.share);

        initWebView();

    }

    private void initAbtract() {

        OkHttpUtils.get()
                .url(API.NEWSABTRACT_GET)
                .addParams("aid", newsAid + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AbstractEntity abstractEntity = new Gson().fromJson(response, AbstractEntity.class);
                        if (abstractEntity.getCode() == 200) {
                            messageEntity = abstractEntity.getData();
                        }

                    }
                });

    }


    private void updataView() {

        NewsEntity newsEntity = new Gson().fromJson(newsEntityJson, NewsEntity.class);

        if ("success".equals(newsEntity.getMessage())) {
            data = newsEntity.getData();
            contentUrl = API.NEWDETAIL + data.getDetail().getAid();
            Log.e("TAG", "contentUrl" + contentUrl);

            wvNewsDetail.loadUrl(contentUrl);


        } else {
            Toast.makeText(mContext, "获取数据失败...", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (messageEntity != null) {

                    String imgUrl = messageEntity.getThumb();
                    if (imgUrl == null || imgUrl == "") {
                        imgUrl = "http://www.mpzn.com/userfiles/image/20160216/16153634da8575901d4313.png";
                    }

                    new ShareAction(DetailNewsActivity.this).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle(messageEntity.getSubject())
                            .withText(messageEntity.getAbstractX())
                            .withMedia(new UMImage(DetailNewsActivity.this, imgUrl))
                            .withTargetUrl(messageEntity.getUrl())
                            .setCallback(umShareListener)
                            .open();
                }

            }

        });

        tvReviewBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetector = EmotionInputDetector.with(DetailNewsActivity.this)
                        .setEmotionView(container)
                        .bindToContent(wvNewsDetail)
                        .bindToEditText(etReview)
                        .bindToEmotionButton(btEmoji)
                        .bindtoFragment(faceFragment)
                        .build();
                reviewEtBar.setVisibility(View.VISIBLE);
                etReview.requestFocus();

            }
        });

        btnOpenReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ReviewListActivity.class);
                intent.putExtra("Aid", newsAid);
                startActivity(intent);
            }
        });

        etReview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) etReview.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!hasFocus) {
                    Log.i("Emoji_test1", "onFocusChange()__!hasFocus");
                    reviewEtBar.setVisibility(View.GONE);
                    etReview.setText(null);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                    mDetector.hideEmotionLayout(false);

                } else {
                    Log.i("Emoji_test1", "onFocusChange()__hasFocus");
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
            ViewUtils.showCustomProgressDialog(DetailNewsActivity.this, "请先登录", R.drawable.toast_error);
            return;
        }
        loadProgressHUD = KProgressHUD.create(DetailNewsActivity.this).
                setSize(MyApplication.mScreenWidth / 4, MyApplication.mScreenWidth / 6).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("正在发表...").setCancellable(true).show();
        OkHttpUtils.post()
                .url(API.SEND_REVIEW_POST)
                .addParams("token", MyApplication.getInstance().token)
                .addParams("aid", newsAid + "")
                .addParams("content", etReview.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        loadedDismissProgressDialog(DetailNewsActivity.this, false, loadProgressHUD, "请检查你的网络...", false);

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);
                        if (simpleEntity.getCode() == 200) {
                            loadedDismissProgressDialog(DetailNewsActivity.this, true, loadProgressHUD, "评论成功", false);
                            etReview.clearFocus();
                        } else {
                            loadedDismissProgressDialog(DetailNewsActivity.this, false, loadProgressHUD, simpleEntity.getMessage(), false);

                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {
        if (mDetector != null && mDetector.interceptBackPress()) {
            Log.i("Emoji_test1", "onBackPressed()__!mDetector.interceptBackPress()");
            //do nothing
        } else if (etReview.hasFocus()) {
            Log.i("Emoji_test1", "onBackPressed()__etReview.hasFocus()");
            reviewEtBar.setVisibility(View.GONE);
            etReview.setText(null);
        } else {
            Log.i("Emoji_test1", "onBackPressed()__noFous");
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result", "onActivityResult");

        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(mPermissionList)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, mPermissionList);
    }
}

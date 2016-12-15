package com.mpzn.mpzn.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.views.MyActionBar;
import com.orhanobut.logger.Logger;
import com.tb.emoji.Emoji;
import com.tb.emoji.FaceFragment;

public class WebViewActivity extends BaseActivity implements FaceFragment.OnEmojiClickListener{

    private WebView webView;
    private MyActionBar action_bar;
    private String token;
    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initHolder() {
        initSystemBar(this, R.color.fafafa);
        action_bar = (MyActionBar)findViewById(R.id.action_bar);
        webView = (WebView)findViewById(R.id.webView);

    }

    @Override
    public void initLayoutParams() {

    }

    private void initWebView(WebView webView, String url) {


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存

        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();
        Logger.d(url);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (cid != null) {
//                    Logger.d("token = "+token+"\n cid = "+cid);
//                    view.loadUrl("javascript:getToken('" + token + "',"+cid+")");
//                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//返回true应该不会执行网页上的链接？
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("track", "onPageFinished()__cid = "+cid);
//                view.loadUrl("javascript:xixi()");
//                token = null;
//                cid = null;
                if (cid != null) {
                    Logger.d("token = "+token+"\n cid = "+cid);
                    view.loadUrl("javascript:getToken('" + token + "',"+cid+")");
                }
//                view.loadUrl("javascript:getToken('4a6b34fbc71bbe2841038a5882327a52', 125889)");
//                view.loadUrl("javascript:getToken()");
                Log.i("track", "4a6b34fbc71bbe2841038a5882327a52");

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                if (cid != null) {
//                    Logger.d("token = "+token+"\n cid = "+cid);
//                    view.loadUrl("javascript:getToken('" + token + "',"+cid+")");
//                }
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
//                if (cid != null) {
//                    Logger.d("token = "+token+"\n cid = "+cid);
//                    view.loadUrl("javascript:getToken('" + token + "',"+cid+")");
//                }
            }
        });
        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等






    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        token = intent.getStringExtra("token");
        cid = intent.getStringExtra("cid");
        Log.i("track", "initData()__cid = "+cid);
        action_bar.init(title, R.drawable.return_red, R.drawable.share);
        String url = intent.getStringExtra("url");
        initWebView(webView,url);


    }

    @Override
    public void bindListener() {
        action_bar.setLROnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }
                , null);

    }

    @Override
    public void onEmojiDelete() {

    }

    @Override
    public void onEmojiClick(Emoji emoji) {

    }
}

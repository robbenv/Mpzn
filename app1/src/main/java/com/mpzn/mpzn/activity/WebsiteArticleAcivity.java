package com.mpzn.mpzn.activity;

import android.webkit.WebView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.views.MyActionBar;

import butterknife.Bind;

//import org.textmining.text.extraction.WordExtractor;

/**
 * Created by Icy on 2016/9/14.
 */
public class WebsiteArticleAcivity extends BaseActivity {

    @Bind(R.id.action_bar)
    MyActionBar actionBar;
    @Bind(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_website_article;
    }

    @Override
    public void initHolder() {
        initSystemBar(this,R.color.red_theme);
        actionBar.init("用户协议", R.drawable.return_white, 0);

    }

    @Override
    public void initLayoutParams() {


    }

    @Override
    public void initData() {
        webview.loadUrl("file:///android_asset/website_article.htm");

    }

    @Override
    public void bindListener() {
        actionBar.setLROnClickListener(null, null);
    }




}

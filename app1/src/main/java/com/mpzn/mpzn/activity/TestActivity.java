package com.mpzn.mpzn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.views.CYTextView;

import butterknife.Bind;
import butterknife.OnClick;

import static com.mpzn.mpzn.application.MyApplication.mScreenWidth;

public class TestActivity extends BaseActivity {


    CYTextView mCYTextView;
    String text = "首先，我们需要利用两条拱形弧线来绘制出圆角四边形嘻嘻哈哈，而在接下来的内容中我们会探讨如何分别表现出上、下、左、右四个方位的外延线条。为了将上述SVG代码转化为VectorDrawable，大家首先需要在XML当中定义vector对象。以下代码提取自本篇文章示例代码当中的vector_drawable_cpu.xml文件。";
    @Bind(R.id.mv)
    CYTextView mv;
    @Bind(R.id.btn_text_10)
    Button btnText10;
    @Bind(R.id.btn_text_25)
    Button btnText25;

    @Override
    public int getLayoutId() {
        setTheme(R.style.AppSplash);
        return R.layout.activity_test;
    }

    @Override
    public void initHolder() {


    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {
    }

    @Override
    public void bindListener() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCYTextView = (CYTextView) findViewById(R.id.mv);
        mCYTextView.SetText(text);
    }

    @OnClick({R.id.btn_text_10, R.id.btn_text_25})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text_10:
                mCYTextView.setTextSize(10);
                break;
            case R.id.btn_text_25:
                mCYTextView.setTextSize(25);
                break;
        }
    }
}

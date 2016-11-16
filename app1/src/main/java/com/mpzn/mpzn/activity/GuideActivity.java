package com.mpzn.mpzn.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.SplashViewPagerAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.views.circleindicator.CircleIndicator;

public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vp_guide;
    private CircleIndicator vpindicator_splash;
    private ImageButton btn_toMain;
    private SplashViewPagerAdapter mAdapter;
    private float preX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initHolder() {
        if (MyApplication.isInfect) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        mAdapter=new SplashViewPagerAdapter(mContext);
        vp_guide.setAdapter(mAdapter);
        vpindicator_splash = (CircleIndicator)findViewById(R.id.vpindicator_splash);
        btn_toMain = (ImageButton) findViewById(R.id.btn_toMain);
        vpindicator_splash.setViewPager(vp_guide);
    }

    @Override
    public void initLayoutParams() {


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state==ViewPager.SCROLL_STATE_DRAGGING) {
            if (btn_toMain.getVisibility() == View.VISIBLE) {
                btn_toMain.setVisibility(View.GONE);
            }

        }
        if(state==ViewPager.SCROLL_STATE_SETTLING){
            if( vp_guide.getCurrentItem()==mAdapter.getCount()-1) {
                btn_toMain.setVisibility(View.VISIBLE);
                btn_toMain.setAlpha(0f);
            }
            if(vpindicator_splash.getVisibility()==View.INVISIBLE){
               vpindicator_splash.setVisibility(View.VISIBLE);
            }

        }
        if(state==ViewPager.SCROLL_STATE_IDLE) {
            if( vp_guide.getCurrentItem()==mAdapter.getCount()-1) {
                vpindicator_splash.setVisibility(View.INVISIBLE);
                btn_toMain.setVisibility(View.VISIBLE);
                btn_toMain.setAlpha(0f);
                ObjectAnimator.ofFloat(btn_toMain,"alpha", 0f, 1.0F)
                              .setDuration(1000)
                        .start();
            }

        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {
        btn_toMain.setOnClickListener(this);
        vp_guide.setOnPageChangeListener(this);

    }
    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        CacheUtils.putBoolean(this, "isNotFirstRun", true);
        MyApplication.getInstance().isNotFirstRun=true;
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toMain:
                startMain();
                break;
        }
    }
    //屏蔽右滑
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(MotionEvent.ACTION_DOWN==ev.getAction()){
            preX = ev.getX();

        }
        if(MotionEvent.ACTION_MOVE==ev.getAction()){
            if(vp_guide.getCurrentItem()==mAdapter.getCount()-1&&preX-ev.getX()>0){
                return true;
            }

        }
        return super.dispatchTouchEvent(ev);
    }

}

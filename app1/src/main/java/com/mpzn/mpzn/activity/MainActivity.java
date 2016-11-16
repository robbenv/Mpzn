package com.mpzn.mpzn.activity;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.UserMsg;
import com.mpzn.mpzn.fragment.MessageFragment;
import com.mpzn.mpzn.fragment.HomeFragment;
import com.mpzn.mpzn.fragment.NewsFragment;
import com.mpzn.mpzn.fragment.UserFragment;
import com.mpzn.mpzn.utils.CacheUtils;
import com.mpzn.mpzn.views.BadgeView;
import com.mpzn.mpzn.views.MyDialog;

import java.util.List;

import butterknife.Bind;

import static com.mpzn.mpzn.application.MyApplication.mScreenWidth;
import static com.mpzn.mpzn.utils.Constant.REQCODE_LOGIN;
import static com.mpzn.mpzn.utils.Constant.REQCODE_SETTING;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJICOM;
import static com.mpzn.mpzn.utils.Constant.RESCODE_JINGJIREN;
import static com.mpzn.mpzn.utils.Constant.RESCODE_KAIFASHANG;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

public class MainActivity extends BaseActivity {


    public static int bottomBarHeight;


    @Bind(R.id.rb_center)
    View rbCenter;              //底部中间占位View
    @Bind(R.id.bottombar_main)
    RadioGroup bottombarMain;
    @Bind(R.id.ib_rb_center)
    ImageButton ibRbCenter;

    private FragmentManager mFragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private MessageFragment buildingFragment;
    private UserFragment userFragment;
    private BaseFragment mCurrentFragment;//当前显示的fragment
    private List<Fragment> fragments;  //FM所持有的所有Fragment

    private MyDialog myDialog;  //退出的Dialog

    private LayoutInflater mInflater;


    private BadgeView pointOnMessage;


    String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RadioButton) bottombarMain.findViewById(R.id.rb_home)).setChecked(true); //避免调用两次onCheckedChanged


        //添加友盟分享需要的权限
        ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);
    }

    @Override
    public int getLayoutId() {

        if (MyApplication.isInfect) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        return R.layout.activity_main;
    }

    @Override
    public void initHolder() {


        mFragmentManager = getSupportFragmentManager();
        fragments = mFragmentManager.getFragments();
        mInflater = LayoutInflater.from(mContext);





    }

    public void setPoint(int index) {
        pointOnMessage = new BadgeView(mContext,null,android.R.attr.textViewStyle,bottombarMain,2);
        pointOnMessage.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        pointOnMessage.setBackgroundResource(R.drawable.red_point);
        pointOnMessage.setBadgeMargin(mScreenWidth*(4-index)/4+dip2px(25), dip2px(5));
        pointOnMessage.setHeight(dip2px(5));
        pointOnMessage.setWidth(dip2px(5));
        pointOnMessage.show();

    }

    public void removePoint() {
        pointOnMessage.hide();
    }

    private void addFragment(BaseFragment fragment, String tag) {     //添加内容区域的fragment

        transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.content_main, fragment, tag);
        transaction.commit();
    }

    private void hideAndShowFragment(BaseFragment fragment) {       //切换fragment

        if (fragment == mCurrentFragment) {
            return;
        }
        transaction = mFragmentManager.beginTransaction();
        fragments = mFragmentManager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) != fragment) {
                transaction.hide(fragments.get(i));
            }
        }

        transaction.show(fragment);
        transaction.commit();
        mCurrentFragment = fragment;

    }


    @Override
    public void initLayoutParams() {

        bottomBarHeight = bottombarMain.getLayoutParams().height;

    }


    @Override
    public void initData() {
        if(CacheUtils.getBoolean(mContext,"isHaveMsg")){
            setPoint(3);
        }
        upData();

    }
    public void upData(){
        if(MyApplication.isLogined) {
            UserMsg mUserMsg = MyApplication.getInstance().mUserMsg;
            int usertype = mUserMsg.getmChild();
            switch (usertype) {
                case  RESCODE_JINGJIREN:
                    rbCenter.setVisibility(View.VISIBLE);
                    ibRbCenter.setVisibility(View.VISIBLE);
//                    ibRbCenter.setImageResource(R.drawable.main_add_bb);
                    ibRbCenter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(mContext,AddBBActivity.class);
                            startActivity(intent);
                        }
                    });

                    break;
                case  RESCODE_JINGJICOM:

                    break;
                case  RESCODE_KAIFASHANG:

                    break;

            }
        }else{
            rbCenter.setVisibility(View.GONE);
            ibRbCenter.setVisibility(View.GONE);
        }

    }

    @Override
    public void bindListener() {
        this.bottombarMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchPager(checkedId);
            }
        });


    }


    public void switchPager(int checkedId) {         //底部button选中切换视图并替换内容区域

        switch (checkedId) {
            case R.id.rb_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    mCurrentFragment = homeFragment;
                    addFragment(homeFragment, HomeFragment.TAG);
                }
                hideAndShowFragment(homeFragment);
                break;
            case R.id.rb_zixun:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    addFragment(newsFragment, NewsFragment.TAG);
                }
                hideAndShowFragment(newsFragment);


                break;
            case R.id.rb_loupan:
                if (buildingFragment == null) {
                    buildingFragment = new MessageFragment();
                    addFragment(buildingFragment, MessageFragment.TAG);
                }
                hideAndShowFragment(buildingFragment);
                if(pointOnMessage!=null&&pointOnMessage.isShown()) {
                    removePoint();
                    CacheUtils.putBoolean(mContext, "isHaveMsg", false);
                }
                break;
            case R.id.rb_geren:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    addFragment(userFragment, UserFragment.TAG);
                }
                hideAndShowFragment(userFragment);
                break;


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        int requestIndex = requestCode>>16;     //把Fragment启动Acivity后的requestCode转为真实requestCode
        if (requestIndex != 0) {
            requestIndex--;
            int mResultCode=(requestCode & 0xffff);
            Log.e("TAG", "mResultCode"+mResultCode);
            switch (mResultCode) {
                case  REQCODE_SETTING:
                    upData();
                    break;
                case  REQCODE_LOGIN:
                    upData();
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);    //传递给调用它的Fragment

    }

    @Override
    public void onBackPressed() {
        if(myDialog==null) {
            myDialog = new MyDialog(mContext);
            myDialog.setTitle("确认退出");
            myDialog.setContent("是否退出卖盘指南？");
            myDialog.setCommit("退出");

            myDialog.setCommitListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getInstance().exit();
                }
            });
        }
        if(!myDialog.isShowing()) {
            myDialog.show();
        }else{
            myDialog.dismiss();
        }

    }
}

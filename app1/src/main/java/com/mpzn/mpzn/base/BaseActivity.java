package com.mpzn.mpzn.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.utils.ImageManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Icy on 2016/7/11.
 */
public abstract class BaseActivity extends FragmentActivity {
    public Context mContext;

    private AlertDialog progressDialog;

    public AlertDialog dlg;

    public Button back;
    private static final String TAG = "BaseActivity";

    public ImageManager mImageManager;


    public SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        mImageManager = new ImageManager(mContext);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//主题定义过这里就不需要了

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initHolder();
        initData();
        initLayoutParams();
        bindListener();
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onDestroy() {
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initHolder();

    /**
     * 初始化布局
     */
    public abstract void initLayoutParams();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void bindListener();



    /**
     * 短toast
     *
     * @param str
     */
    public void playShortToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短toast
     *
     * @param res
     */
    public void playShortToast(int res) {
        playShortToast(getResources().getString(res));
    }

    /**
     * 长toast
     *
     * @param str
     */
    public void playLongToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    /**
     * 长toast
     *
     * @param res
     */
    public void playLongToast(int res) {
        playLongToast(getResources().getString(res));
    }




    private final boolean compareToCurrentDate(int year, int month, int day) {
        long curentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(curentTime));
        String[] array = date.split("-");
        int currentYear = Integer.parseInt(array[0]);
        int currentMonth = Integer.parseInt(array[1]);
        int currentDay = Integer.parseInt(array[2]);
        if ((year > currentYear) || ((year == currentYear) && ((month + 1) > currentMonth))
                || ((year == currentYear) && ((month + 1) == currentMonth) && (day > currentDay))) {
            return false;
        }
        return true;
    }

    public static void initSystemBar(Activity activity,int colorRes) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);

        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);

        tintManager.setStatusBarTintEnabled(true);

        // 使用颜色资源
       if(colorRes==0) {
           tintManager.setStatusBarTintResource(R.color.red_theme);
        }else{
           tintManager.setStatusBarTintResource(colorRes);
        }

    }



    @TargetApi(19)

    private static void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();

        WindowManager.LayoutParams winParams = win.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {

            winParams.flags |= bits;

        } else {

            winParams.flags &= ~bits;

        }

        win.setAttributes(winParams);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        FragmentManager fm = getSupportFragmentManager();
//        int index = requestCode >> 16;
//        if (index != 0) {
//            index--;
//            if (fm.getFragments() == null || index < 0
//                    || index >= fm.getFragments().size()) {
//                Log.e(TAG, "Activity result fragment index out of range: 0x"
//                        + Integer.toHexString(requestCode));
//                return;
//            }
//            Fragment frag = fm.getFragments().get(index);
//            if (frag == null) {
//                Log.e(TAG, "Activity result no fragment exists for index: 0x"
//                        + Integer.toHexString(requestCode));
//            } else {
//                handleResult(frag, requestCode, resultCode, data);
//                Log.e("TAG", "handleResult"+frag+"---"+requestCode+"---"+resultCode+"---"+data);
//            }
//            return;
//        }
//
//    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    /**
     * 过滤输入空格
     */
    protected InputFilter commonFilterBlank = new InputFilter() {
        @Override
        public CharSequence filter(
                CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (source.equals(" ")) {
                playShortToast(R.string.not_have_space);
                return "";
            }
            else
                return null;
        }
    };
}

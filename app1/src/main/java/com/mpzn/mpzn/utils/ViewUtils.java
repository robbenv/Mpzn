package com.mpzn.mpzn.utils;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;

/**
 * Created by Icy on 2016/7/20.
 */
public class ViewUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getInstance().mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = MyApplication.getInstance().mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //获取状态栏的高度
    public static int getStatusBarHeight() {
        int result = -1;
        //获取status_bar_height资源的ID
        int resourceId = MyApplication.getInstance().mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            result = MyApplication.getInstance().mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //设置margin
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    //获取渐变色   int preColor 类似0XFFEE4433
    public static int getGradientColor(int preColor,int afterColor,float percent){
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int evaluate = (Integer) evaluator.evaluate(percent, preColor,afterColor);
        return evaluate;

    }
    //使已存在的dialog改变状态并消失
    public static void loadedDismissProgressDialog(final Activity activity, boolean isSuccess, final KProgressHUD loadProgressHUD, String msg, final boolean isFinish){
        ImageView imageView = new ImageView(activity);
        int imgRes=-1;
        if(isSuccess){
            imgRes=R.drawable.toast_success;
        }else{
            imgRes=R.drawable.toast_error;
        }
        imageView.setImageResource(imgRes);
        loadProgressHUD.setCustomView(imageView);
        loadProgressHUD.setLabel(msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProgressHUD.dismiss();
                if(isFinish){
                    activity.finish();
                }
            }
        }, 1000);

    }
    //使已存在的dialog改变状态并消失
    public static void dismissDialogWithCallback(final Activity activity, boolean isSuccess, final KProgressHUD loadProgressHUD, String msg, final CallBack callBack){
        ImageView imageView = new ImageView(activity);
        int imgRes=-1;
        if(isSuccess){
            imgRes=R.drawable.toast_success;
        }else{
            imgRes=R.drawable.toast_error;
        }
        imageView.setImageResource(imgRes);
        loadProgressHUD.setCustomView(imageView);
        loadProgressHUD.setLabel(msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProgressHUD.dismiss();
                callBack.afterDialogDismiss();
            }
        }, 1000);

    }

    //加载完成的dialog
    public static void showCustomProgressDialog(final Activity activity,String msg,int imageId){
        ImageView imageView = new ImageView(activity);
        imageView.setImageResource(imageId);
        final KProgressHUD kProgressHUD = KProgressHUD.create(activity).setCustomView(imageView).setSize(MyApplication.mScreenWidth/4, MyApplication.mScreenWidth/6).setLabel(msg).show();
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kProgressHUD.dismiss();
            }
        }, 1000);
    }

    //计算listView每个Item 赋值给Listview  使其展开 的方法
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }


    /**
     * 获取Listview的高度
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildren1(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }

    public interface CallBack{
        void afterDialogDismiss();
    }

}
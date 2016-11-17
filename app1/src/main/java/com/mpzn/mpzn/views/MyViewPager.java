package com.mpzn.mpzn.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mpzn.mpzn.utils.ViewUtils;

/**
 * Created by Icy on 2016/8/5.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


//        ViewPager每个页面的高度不同时  嵌套ListView  将listView总高度赋值给自己
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        int mCurrentIndex = getCurrentItem();
        Log.e("TAG", "mCurrentIndex" + mCurrentIndex);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int h=0;
            if(((ViewGroup)child).getChildAt(0) instanceof ListView){
                h = ViewUtils.setListViewHeightBasedOnChildren1((ListView) ((ViewGroup) child).getChildAt(0));

            }else if(child instanceof ListView){
                h = ViewUtils.setListViewHeightBasedOnChildren1((ListView) child);
            }else {
                child.measure(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

                h = child.getMeasuredHeight();
            }
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }




}

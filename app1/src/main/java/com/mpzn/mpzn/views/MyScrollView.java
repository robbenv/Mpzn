package com.mpzn.mpzn.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

import com.mpzn.mpzn.listener.ScrollViewListener;

/**
 * Created by Icy on 2016/7/20.
 */
public class MyScrollView extends ScrollView {
    private static final int MAX_OVERSCROLL_Y = 100;
    private Context mContext;
    private int newMaxOverScrollY;
    private ScrollViewListener scrollViewListener;

    public MyScrollView(Context context) {
        this(context, null);

    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        init(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;


        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        newMaxOverScrollY = (int) (density * MAX_OVERSCROLL_Y);

        //false:隐藏ScrollView的滚动条。
        this.setVerticalScrollBarEnabled(false);

        //不管装载的控件填充的数据是否满屏，都允许橡皮筋一样的弹性回弹。
        this.setOverScrollMode(ScrollView.OVER_SCROLL_ALWAYS);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, newMaxOverScrollY,
                isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}

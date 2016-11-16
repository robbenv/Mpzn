package com.mpzn.mpzn.views.pulltozoomview.listener;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/8/5.
 */
public class InternalScrollView extends ScrollView {

    private boolean isAtBottom;

    private OnScrollViewChangedListener onScrollViewChangedListener;

    private OnScrollViewChangedListener onScrollViewChangedListenerForTopbar;

    private OnScrollViewChangedListener onScrollViewChangedListenerForStickView;


    public InternalScrollView(Context context) {
        this(context, null);
    }

    public InternalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollViewChangedListener(OnScrollViewChangedListener onScrollViewChangedListener) {
        this.onScrollViewChangedListener = onScrollViewChangedListener;
    }

    public void setOnScrollViewChangedListenerForTopbar(OnScrollViewChangedListener onScrollViewChangedListener) {
        this.onScrollViewChangedListenerForTopbar = onScrollViewChangedListener;
    }

    public void setOnScrollViewChangedListenerForStickView(OnScrollViewChangedListener onScrollViewChangedListener) {
        this.onScrollViewChangedListenerForStickView = onScrollViewChangedListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollViewChangedListener != null) {
            onScrollViewChangedListener.onInternalScrollChanged(l, t, oldl, oldt);
        }

        if (onScrollViewChangedListenerForTopbar != null) {
            onScrollViewChangedListenerForTopbar.onInternalScrollChanged(l, t, oldl, oldt);
        }

        if (onScrollViewChangedListenerForStickView != null) {
            onScrollViewChangedListenerForStickView.onInternalScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        isAtBottom=clampedY;
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);



    }

    //以下内容为  设置Scroll中ListView可滚动
    private List<ListView> list = new ArrayList();
    private int scrollPaddingTop; // scrollview的顶部内边距
    private int scrollPaddingLeft;// scrollview的左侧内边距
    private int[] scrollLoaction = new int[2]; // scrollview在窗口中的位置
    private final static int UPGLIDE = 0;
    private final static int DOWNGLIDE = 1;
    private int glideState;

    private int downY = 0;
    private int moveY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                //System.out.println("actiondown" + ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                moveY= (int) ev.getY();
                //System.out.println("move" + moveY + "down" + downY);
                if((moveY - downY) >= 0) {
                    //System.out.println("'''''''''DOWNGLIDE'''''''''''");
                    glideState = DOWNGLIDE;
                } else {
                    //System.out.println("'''''''''UPGLIDE'''''''''''");
                    glideState = UPGLIDE;
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 该事件的xy是以scrollview的左上角为00点而不是以窗口为00点
        int x = (int) ev.getX() + scrollLoaction[0];
        int y = (int) ev.getY() + scrollLoaction[1];
        for (int i = 0; i < list.size(); i++) {
            ListView listView = list.get(i);
            int[] location = new int[2];
            listView.getLocationInWindow(location);
            int width = listView.getWidth();
            int height = listView.getHeight();
            // 在listview的位置之内则可以滑动
            if (x >= location[0] + scrollPaddingLeft
                    && x <= location[0] + scrollPaddingLeft + width
                    && y >= location[1] + scrollPaddingTop
                    && y <= location[1] + scrollPaddingTop + height) {
                //System.out.println(glideState);
                if(( (listView.getLastVisiblePosition() == (listView.getCount()-1)) && (glideState == UPGLIDE) ) ) {
                    //System.out.println("up");
                    break;
                }
                if(( (listView.getFirstVisiblePosition() == 0) && (glideState == DOWNGLIDE))) {
                    //System.out.println("down");
                    break;
                }
                return  !isAtBottom; //在底部就让子控件直接处理
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


    private void findAllListView(View view) {
        if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                if (!(view instanceof ListView)) {
                    findAllListView(((ViewGroup) view).getChildAt(i));
                }
            }
            if (view instanceof ListView) {
                list.add((ListView) view);
            }
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        scrollPaddingTop = getTop();
        scrollPaddingLeft = getLeft();
        getLocationInWindow(scrollLoaction);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.getChildCount() != 1) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        list.clear();
        findAllListView(this.getChildAt(0));
    }

}

package com.mpzn.mpzn.listener;

import com.mpzn.mpzn.views.MyScrollView;

/**
 * Created by Icy on 2016/7/20.
 */
public interface ScrollViewListener {
    void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
}

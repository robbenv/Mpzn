package com.mpzn.mpzn.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;

import java.util.List;

/**
 * Created by Icy on 2016/7/27.
 */
public class HorizontalListView extends HorizontalScrollView {
    private  Context mContext;
    private LinearLayout horizontalLinearLayout;
    private int itemWidth;
    private int itemCount;


    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }
    private  void initView(){

        this.setHorizontalScrollBarEnabled(false);
        horizontalLinearLayout = new LinearLayout(mContext);
        horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParamsForLinearLayout =
                new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontalLinearLayout.setLayoutParams(layoutParamsForLinearLayout);
        this.addView(horizontalLinearLayout);




    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    public void setItem(Context mContext, int layoutId,List<String> datalists) {
        initView();
        for(int i=0;i<datalists.size();i++) {
            View view =  View.inflate(mContext, layoutId, null);
            TextView tv=(TextView)view.findViewById(R.id.item_textview);
            tv.setText(datalists.get(i));
            horizontalLinearLayout.addView(view);

        }
    }
}

package com.mpzn.mpzn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.listener.ScrollViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icy on 2016/7/22.
 */
public class AutoScrollTextView extends MyScrollView {
    private static final int AUTOSCROLL=5;
    private Context mContext;
    private int textsCount;
    private int showNum;
    private List<String> texts=new ArrayList<>();
    private int mHeight;
    private LinearLayout verticalLinearLayout;
    private int atNum;
    private boolean isScrolling=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  AUTOSCROLL:

                    if(mHeight!=0&&!isScrolling){
                        onShow();
                        initView();
                        isScrolling=true;
                    }

//                    Log.e("TAG", "ScrollY" + px2dip(mContext,AutoScrollTextView.this.getScrollY() )+ "---mHeight" +  px2dip(mContext,mHeight) + "---atNum" +  px2dip(mContext,atNum));
                    if(atNum==textsCount-2){
                        AutoScrollTextView.this.scrollTo(0,0);
                        atNum=1;
                        ((TextView) verticalLinearLayout.getChildAt(textsCount-2)).setTextColor(getResources().getColor(R.color.textColor));
                        ((TextView) verticalLinearLayout.getChildAt(1)).setTextColor(getResources().getColor(R.color.red_theme));
                    }else {
                        AutoScrollTextView.this.smoothScrollBy(0, mHeight / showNum);
                    }
                    handler.sendEmptyMessageDelayed(AUTOSCROLL, 1500);

                    break;
            }

        }
    };



    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AutoScrollTextView);

        showNum= a.getInteger(R.styleable.AutoScrollTextView_show_num, 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight=measureHeight(heightMeasureSpec);


    }

    private void initView(){
        this.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                atNum=y/(mHeight/showNum)+1;
                ((TextView) verticalLinearLayout.getChildAt(atNum)).setTextColor(getResources().getColor(R.color.red_theme));
                ((TextView) verticalLinearLayout.getChildAt(atNum-1)).setTextColor(getResources().getColor(R.color.textColor));
            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
    }
    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){

        }

        return specSize;
    }
    public void setText(List<String> texts){

        this.texts=texts;

        AutoScrollTextView.this.setVerticalScrollBarEnabled(false);
        textsCount=texts.size()+2;
        texts.add(0,texts.get(texts.size()-1));
        texts.add(texts.size(),texts.get(1));
        verticalLinearLayout = new LinearLayout(mContext);

        verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsForLinearLayout =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsForLinearLayout.setMargins(0, 0, 0, 0);
        verticalLinearLayout.setLayoutParams(layoutParamsForLinearLayout);
        this.addView(verticalLinearLayout);

    }

    private void onShow(){

        for(int i=0;i<textsCount;i++){
            TextView tv=new TextView(mContext);
            tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    mHeight / showNum));
            tv.setText(texts.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setSingleLine();
            verticalLinearLayout.addView(tv);
        }
        ((TextView)verticalLinearLayout.getChildAt((showNum-1)/2)).setTextColor(getResources().getColor(R.color.red_theme));


    }



    public void startScroll(){
        handler.sendEmptyMessage(AUTOSCROLL);

    }
}

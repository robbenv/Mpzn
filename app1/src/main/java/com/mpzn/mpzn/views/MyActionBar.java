package com.mpzn.mpzn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseActivity;

/**
 * Created by Icy on 2016/8/30.
 */
public class MyActionBar extends FrameLayout {
    private final int DEFAULT_TITLE_COLOR = getResources().getColor(R.color.font_black_0);
    private TextView tv_right;
    private ImageButton btn_left;
    private ImageButton btn_right;
    private TextView tv_title;
    private Context mContext;



    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyActionBar);
        int color = a.getColor(R.styleable.MyActionBar_title_color, DEFAULT_TITLE_COLOR);
        LayoutInflater.from(mContext).inflate(R.layout.action_bar_forfm, this);
        btn_left = (ImageButton)findViewById(R.id.btn_left);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setTextColor(color);
        btn_right = (ImageButton)findViewById(R.id.btn_right);
        tv_right = (TextView)findViewById(R.id.tv_right);
    }

    public void setRightBtVisible(boolean isVisible) {
        btn_right.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void init(String title,int ImgResLeft,int ImgResRight){
        tv_title.setText(title);
        if(ImgResLeft!=0) {
            btn_left.setImageResource(ImgResLeft);
        }else{
            btn_right.setVisibility(GONE);
        }
        if(ImgResRight!=0) {
            btn_right.setImageResource(ImgResRight);
        }else{
            btn_right.setVisibility(GONE);
        }


    }

    public void setRightText(String rightText){
        tv_right.setText(rightText);
    }
    public void setRightTextColor(int ResColor){
        tv_right.setTextColor(getResources().getColor(ResColor));
    }

    public void setLROnClickListener(OnClickListener leftOnClickListener,OnClickListener rightOnClickListener){
        if(leftOnClickListener!=null){
            btn_left.setOnClickListener(leftOnClickListener);
        }else {
            btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity)mContext).finish();
                }
            });
        }

        if(rightOnClickListener!=null){
            btn_right.setOnClickListener(rightOnClickListener);
            tv_right.setOnClickListener(rightOnClickListener);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

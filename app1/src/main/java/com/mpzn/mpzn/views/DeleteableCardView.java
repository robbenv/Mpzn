package com.mpzn.mpzn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;

import static com.mpzn.mpzn.application.MyApplication.mContext;

/**
 * Created by Icy on 2016/10/24.
 */

public class DeleteableCardView extends RelativeLayout {

    private final int DEFAULT_TEXT_COLOR = getResources().getColor(R.color.font_black_2);
    private Button btn_delete;
    private TextView tv_content;


    public DeleteableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DeleteableCardView);
        int color = a.getColor(R.styleable.DeleteableCardView_textColor,DEFAULT_TEXT_COLOR);
        int size = a.getDimensionPixelOffset(R.styleable.DeleteableCardView_textSize,16);
        String text = a.getString(R.styleable.DeleteableCardView_text);
        LayoutInflater.from(mContext).inflate(R.layout.layout_deleteable_cardview, this);
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        tv_content.setTextColor(color);
        tv_content.setTextSize(size);
        tv_content.setText(text);
        btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteableCardView.this.setVisibility(GONE );
            }
        });
    }
    public void setBtnDeleteOnClickListener(View.OnClickListener btnDeleteOnClickListener){
        btn_delete.setOnClickListener(btnDeleteOnClickListener);
    }
    public void setText(String text){
        tv_content.setText(text);
    }
    public String getText(){
        return tv_content.getText().toString();
    }

}

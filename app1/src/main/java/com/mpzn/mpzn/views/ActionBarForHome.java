package com.mpzn.mpzn.views;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.utils.ViewUtils;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/7/20.
 */
public class ActionBarForHome extends RelativeLayout{



    private boolean isAtTop=true;
    private static int magin=dip2px(60);
    private RelativeLayout action_bar;
    private View status_bar;
    private RelativeLayout action_bar_root;
    private  View aciton_bar_bg;
    public int mLeftMagin;
    public int mRightMagin;
    public TextView leftTitle;
    public View top_cut_line;
    private  ImageView iv_left;
    private  ImageView iv_left_bg;
    public ImageButton btn_right;
    private ImageButton btn_right_bg;
    public TextView title;
    public boolean mIsInHomeTop; //是否在首页
    private Context mContext;
    private TextView text_search_hint;

    public ActionBarForHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        LayoutInflater.from(mContext).inflate(R.layout.action_bar_for_home, this);
        action_bar_root = (RelativeLayout)findViewById(R.id.action_bar_root);
        status_bar = findViewById(R.id.status_bar);
        action_bar = (RelativeLayout)findViewById(R.id.my_action_bar);

        leftTitle=(TextView)this.findViewById(R.id.home_leftTitle);
        iv_left_bg = (ImageView)findViewById(R.id.iv_left_bg);
        iv_left = (ImageView)findViewById(R.id.iv_left);
        top_cut_line=this.findViewById(R.id.top_cut_line);
        aciton_bar_bg = findViewById(R.id.aciton_bar_bg);
        btn_right=(ImageButton)this.findViewById(R.id.btn_right);
        btn_right_bg = (ImageButton)this.findViewById(R.id.btn_right_bg);
        title=(TextView)this.findViewById(R.id.home_title);
        text_search_hint = (TextView)findViewById(R.id.text_search_hint);

    }


    public void setMode(boolean isInHomeTop){
        mIsInHomeTop=isInHomeTop;
        if(mIsInHomeTop){
            this.setMargin(magin, magin);
            top_cut_line.setVisibility(View.VISIBLE);
            aciton_bar_bg.setVisibility(VISIBLE);
            text_search_hint.setText(R.string.hint);
            text_search_hint.setClickable(true);
            leftTitle.setTextColor(0XFF525252);
            title.setAlpha(0);
            btn_right.setAlpha(0f);
            btn_right_bg.setAlpha(1.0f);
            iv_left.setAlpha(0f);
            iv_left_bg.setAlpha(1.0f);

//            //改变顶部左右title的图片
//            Drawable drawableL = getResources().getDrawable(R.drawable.down);
//            /// 这一步必须要做,否则不会显示.
//            drawableL.setBounds(0, 0, drawableL.getMinimumWidth(), drawableL.getMinimumHeight());
//            leftTitle.setCompoundDrawables(null, null, drawableL, null);
//            btn_right.setImageResource(R.drawable.search_gray);

        }else{
            this.setMargin(0, 0);
            top_cut_line.setVisibility(View.INVISIBLE);
            this.setBackground(null);
            text_search_hint.setText(null);
            text_search_hint.setClickable(false);
            title.setAlpha(1.0f);

            btn_right.setAlpha(1.0f);
            btn_right_bg.setAlpha(0f);
            iv_left.setAlpha(1.0f);
            iv_left_bg.setAlpha(0f);

            leftTitle.setTextColor(0XFFFFFFFF);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
        mLeftMagin=dip2px((float)(layoutParams.leftMargin));
        mRightMagin=dip2px((float)(layoutParams.rightMargin));

        if(!((MyApplication)mContext.getApplicationContext()).isInfect){
            status_bar.setVisibility(View.GONE);
        }else{
            ViewGroup.LayoutParams Params = status_bar.getLayoutParams();
            Params.height= ViewUtils.getStatusBarHeight();
        }


    }

    public boolean getIsAtTop(){
        return isAtTop;
    }

    public void setChangeWithScroll(int top, final View shadow){

                if (top > 0) {
                    //top应该是广告顶部超出上边界的距离
                    //开始滑动了
                    Log.i("testScroll", "top > 0");
                    top_cut_line.setVisibility(View.INVISIBLE);
                    aciton_bar_bg.setVisibility(INVISIBLE);
                    text_search_hint.setText(null);
                    isAtTop = false;
                } else if (top > 200){
                    Log.i("testScroll", "top > 200");
                } else {
                    //没有滑动的情况
                    Log.i("testScroll", "top 小于 0");
                    isAtTop = true;
                    ActionBarForHome.this.setMode(true);

                }


                if (top <= magin) {
                    //滑动但是又没渐变效果又没完成的时候
                    Log.i("testScroll", "top <= magin");
                    if(shadow!=null) {
                        shadow.setVisibility(View.GONE);
                    }
                    float percent = (float) (magin - top) / magin;
                    Log.i("testScroll", "percent = "+percent);
                    if (top <= 0) {
                        percent = 1f;   //防止滑到顶部y反弹为负值5
                    }

                    ArgbEvaluator evaluator1 = new ArgbEvaluator();
                    int evaluate1 = (Integer) evaluator1.evaluate(1 - percent, 0X00EE4433, 0XFFEE4433);
                    action_bar_root.setBackgroundColor(evaluate1);

                    title.setAlpha(1 - percent);

                    btn_right.setAlpha(1 - percent);
                    btn_right_bg.setAlpha(percent);
                    iv_left.setAlpha(1 - percent);
                    iv_left_bg.setAlpha(percent);

                    setMargin((int) (magin * percent), (int) (magin * percent));

                    ArgbEvaluator evaluator = new ArgbEvaluator();
                    int evaluate = (Integer) evaluator.evaluate(1 - percent, 0XFF525252, 0XFFFFFFFF);
                    leftTitle.setTextColor(evaluate);
                } else {
                    //渐变效果完成了
                    Log.i("testScroll", "top 大于 magin");
                    action_bar_root.setBackgroundColor(getResources().getColor(R.color.red_theme));
                    if(shadow!=null) {
                        shadow.setVisibility(View.VISIBLE);
                    }
                    ActionBarForHome.this.setMode(false);

                }





    }

    public void setMargin(int left,int right){
        if (action_bar.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) action_bar.getLayoutParams();
            p.setMargins(left,  0, right, dip2px(5));
            action_bar.requestLayout();
        }

    }
}

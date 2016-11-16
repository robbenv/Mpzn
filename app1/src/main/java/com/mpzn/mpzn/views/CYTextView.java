package com.mpzn.mpzn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;

import java.util.Vector;

import static com.code19.library.DensityUtil.sp2px;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/11/11.
 */

public class CYTextView extends TextView {
    public  static  int m_iTextHeight; //文本的高度
    public  static  int m_iTextWidth;//文本的宽度
    private Context mContext;

    private Paint mPaint = null;
    private String string="";
    private float LineSpace = 0;//行间距

    private float paddinglr=10;


    public CYTextView(Context context, AttributeSet set)
    {
        super(context,set);
        mContext=context;
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.CYTextView);

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels-dip2px(paddinglr*2);
        int width = typedArray.getInt(R.styleable.CYTextView_textwidth, widthPixels);
        float textsize = typedArray.getDimension(R.styleable.CYTextView_cy_textSize, 24);
        int textcolor = typedArray.getColor(R.styleable. CYTextView_cy_textColor, 1442840576);
        float linespace = typedArray.getDimension(R.styleable.CYTextView_lineSpacingExtra, 15);
        int typeface = typedArray.getColor(R.styleable.CYTextView_typeface, 0);

        typedArray.recycle();

        //设置 CY TextView的宽度和行间距www.linuxidc.com
        m_iTextWidth=width;
        LineSpace=linespace;

        // 构建paint对象
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(textcolor);
        mPaint.setTextSize(textsize);
        switch(typeface){
            case 0:
                mPaint.setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                mPaint.setTypeface(Typeface.SANS_SERIF);
                break;
            case 2:
                mPaint.setTypeface(Typeface.SERIF);
                break;
            case 3:
                mPaint.setTypeface(Typeface.MONOSPACE);
                break;
            default:
                mPaint.setTypeface(Typeface.DEFAULT);
                break;
        }

    }


    public void setTextWidth(int pixels) {
        m_iTextWidth=pixels;
    }



    @Override
    public void setTextSize(float size) {
        int pxSize = sp2px(mContext, size);
        mPaint.setTextSize(pxSize);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        char ch;
        int w = 0;
        int istart = 0;
        int m_iRealLine=0;

        Vector m_String=new Vector();

        for (int i = 0; i < string.length(); i++)
        {
            ch = string.charAt(i);
            float[] widths = new float[1];
            String srt = String.valueOf(ch);
            mPaint.getTextWidths(srt, widths);

            if (ch == ' '){
                m_iRealLine++;
                m_String.addElement(string.substring(istart, i));
                istart = i + 1;
                w = 0;
            }else{
                w += (int) (Math.ceil(widths[0]));
                if (w > m_iTextWidth){
                    m_iRealLine++;
                    m_String.addElement(string.substring(istart, i));
                    istart = i;
                    i--;
                    w = 0;
                }else{
                    if (i == (string.length() - 1)){
                        m_iRealLine++;
                        m_String.addElement(string.substring(istart, string.length()));
                    }
                }
            }
        }


        for (int i = 0, j = 0; i < m_iRealLine; i++, j++)
        {
            String str=(String)(m_String.elementAt(i));
            Rect bounds = new Rect();
            mPaint.getTextBounds(str, 0, str.length(), bounds);
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(str,getMeasuredWidth() / 2 - bounds.width() / 2,m_iTextHeight+baseline, mPaint);
        }
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        this.setMeasuredDimension(measuredWidth, measuredHeight);
        this.setLayoutParams(new LinearLayout.LayoutParams(measuredWidth,measuredHeight));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureHeight(int measureSpec)
    {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Default size if no limits are specified.
        initHeight();
        int result = m_iTextHeight;
        if (specMode == MeasureSpec.AT_MOST){
            // Calculate the ideal size of your
            // control within this maximum size.
            // If your control fills the available
            // space return the outer bound.
            result = specSize;
        }else if (specMode == MeasureSpec.EXACTLY){
            // If your control can fit within these bounds return that value.
            result = specSize;
        }
        return result;
    }

    private void initHeight() {
        //设置 CY TextView的初始高度为0
        m_iTextHeight=0;

        //大概计算 CY TextView所需高度
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        int m_iFontHeight = (int) Math.ceil(fm.descent - fm.top) + (int)LineSpace;
        int line=0;
        int istart=0;

        int w=0;
        for (int i = 0; i < string.length(); i++)
        {
            char ch = string.charAt(i);
            float[] widths = new float[1];
            String srt = String.valueOf(ch);
            mPaint.getTextWidths(srt, widths);

            if (ch== ' '){
                line++;
                istart = i + 1;
                w = 0;
            }else{
                w += (int) (Math.ceil(widths[0]));
                if (w > m_iTextWidth){
                    line++;
                    istart = i;
                    i--;
                    w = 0;
                }else{
                    if (i == (string.length() - 1)){
                        line++;
                    }
                }
            }
        }
        m_iTextHeight=(line)*m_iFontHeight;
    }

    private int measureWidth(int measureSpec)
    {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // Default size if no limits are specified.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST){
            // Calculate the ideal size of your control
            // within this maximum size.
            // If your control fills the available space
            // return the outer bound.
            result = specSize;
        }else if (specMode == MeasureSpec.EXACTLY){
            // If your control can fit within these bounds return that value.
            result = specSize;
        }
        return result;
    }

    public void SetText(String text)//注：此函数目前只有在UI线程中调用才可以把文本画出来，在其它线程中<p>                                                        //无法画文本，找了好久找不到原因，求高手解答)
    {
        string = text;
        // requestLayout();
        // invalidate();
    }
}
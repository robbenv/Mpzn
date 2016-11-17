package com.mpzn.mpzn.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.mpzn.mpzn.R;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/9/12.
 */
public class MyView extends View {

    private Paint mPaint;
    private Path mPath;
    private Path mPath2;
    private Paint mPaint2;
    private Paint mPaint3;

    private ValueAnimator valueAnimator;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];


    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.transparent));
        mPaint.setTextSize(40);
        mPaint.setStrokeWidth(dip2px(1));
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(getResources().getColor(R.color.red_theme));
        mPaint2.setTextSize(40);
        mPaint2.setStyle(Paint.Style.FILL);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setColor(getResources().getColor(R.color.transparent));
        mPaint3.setStrokeWidth(1);
        mPaint3.setStyle(Paint.Style.FILL);


        mPath = new Path();//MPZN  logo

        mPath.moveTo(dip2px(20), dip2px(5));
        mPath.lineTo(dip2px(35), dip2px(20));
        mPath.lineTo(dip2px(30), dip2px(20));
        mPath.lineTo(dip2px(30), dip2px(30));
        mPath.lineTo(dip2px(25), dip2px(30));
        mPath.lineTo(dip2px(25), dip2px(dip2px(10)));
        mPath.lineTo(dip2px(dip2px(10)), dip2px(30));
        mPath.lineTo(dip2px(15), dip2px(20));
        mPath.lineTo(dip2px(15), dip2px(30));
        mPath.lineTo(dip2px(10), dip2px(30));
        mPath.lineTo(dip2px(10), dip2px(20));
        mPath.lineTo(dip2px(5), dip2px(20));

        mPath.close();

        mPath2 = new Path();
        mPathMeasure = new PathMeasure(mPath,false);
        mPathMeasure.getPosTan(0, mCurrentPosition, null);
        mPath2.moveTo(mCurrentPosition[0], mCurrentPosition[1]);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);   //开启硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速

        valueAnimator = ValueAnimator.ofFloat(0,mPathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                mPath2.lineTo(mCurrentPosition[0], mCurrentPosition[1]);
                postInvalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mPath2 = new Path();
                mPathMeasure = new PathMeasure(mPath, false);
                mPathMeasure.getPosTan(0, mCurrentPosition, null);
                mPath2.moveTo(mCurrentPosition[0], mCurrentPosition[1]);
                mPaint2.setColor(getResources().getColor(R.color.red_theme));
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());


    }
    public void startReFresh(){
        valueAnimator.start();
    }
    public void stopReFresh(){
        valueAnimator.cancel();
        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(dip2px(35),dip2px(35));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        canvas.save();
//        canvas.translate(50, 50);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], dip2px(2), mPaint3);
        canvas.drawPath(mPath2, mPaint2);
        canvas.restore();

    }



//    @Override
//    protected void onDraw(Canvas canvas)
//
//    {
//
//        super.onDraw(canvas);
//
//        canvas.drawColor(Color.WHITE);
//
//        Paint paint = new Paint();
//
//        paint.setAntiAlias(true);
//
//        paint.setColor(Color.RED);
//
//        paint.setStyle(Paint.Style.STROKE);//设置为空心
//
//        paint.setStrokeWidth(20);
//
//
//
//        Path path = new Path();//MPZN  logo
//
//        path.moveTo(300, 0);
//        path.lineTo(600, 300);
//        path.lineTo(500, 300);
//        path.lineTo(500, 500);
//        path.lineTo(400, 500);
//        path.lineTo(400, 300);
//        path.lineTo(300, 500);
//        path.lineTo(200, 300);
//        path.lineTo(200, 500);
//        path.lineTo(100, 500);
//        path.lineTo(100, 300);
//        path.lineTo(0, 300);
//
//        path.close();
//
//        canvas.drawPath(path, paint);
//
//
//
//
//        paint.setStrokeWidth(3);
//
//        Shader mShader = new LinearGradient(0, 0, 100, 100,
//
//                new int[] { Color.RED, Color.YELLOW },
//
//                null, Shader.TileMode.REPEAT);
//
//        // Shader.TileMode三种模式
//
//        // REPEAT:沿着渐变方向循环重复
//
//        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
//
//        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
//
//        paint.setShader(mShader);// 用Shader中定义定义的颜色来话
//        paint.setTextSize(50);
//
//        canvas.drawText("卖盘指南", 200, 600, paint);
//
//
//    }

}
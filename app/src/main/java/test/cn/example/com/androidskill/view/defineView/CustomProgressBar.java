package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;

/**
 * Created by xgxg on 2017/9/6.
 */

public class CustomProgressBar extends View {
    private final int DEFAULTWIDTH = DensityUtil.dp2Px(150);
    private final int DEFAULTHEIGHT = DensityUtil.dp2Px(150);
    private int mFirstColor;
    private  int mSecondColor;
    private float mCircleWidth;
    private int mSpeed;
    private final Paint mPaint;
    private int mProgress;
    private boolean isNext;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        int attributeCount = a.getIndexCount();
        for (int i = 0; i < attributeCount; i++) {
            int arr = a.getIndex(i);
            switch (arr){
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(arr, Color.BLACK);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(arr, Color.BLUE);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimension(arr, DensityUtil.dp2Px(20));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(arr, 0);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();

        //改变progressbar的值的线程
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    mProgress++;
                    if(360 == mProgress){
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,DEFAULTHEIGHT);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecSize){
            setMeasuredDimension(widthSpecSize,DEFAULTHEIGHT);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth()/2;//获取圆心坐标
        float radius = center - mCircleWidth/2;
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//空心
        //圆弧的区域
        RectF oval = new RectF(center - radius,center-radius,center+radius,center+radius);
        if(!isNext){
            //第一个颜色的圈完整，第二个圈的颜色在变化
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }

    }
}

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
 * Created by xgxg on 2017/9/7.
 */

public class CustomProgerssBar2 extends View {
    private final int DEFAULTWIDTH = DensityUtil.dp2Px(150);
    private final int DEFAULTHEIGHT = DensityUtil.dp2Px(150);
    private Paint mPaint ;
    private Paint mFirstPaint;
    private Paint mSecondPaint;
    private boolean mIsNext = false;
    private int mFirstColor = Color.BLUE;
    private int mSecondColor = Color.RED;
    private int mSpeed = 1;
    private int mProgress = 0;
    private int mFirstProgerss = 0;
    private int mSecondProgress = 0;
    public CustomProgerssBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        int arrCount = a.getIndexCount();
        int arr;
        float circleWidth = DensityUtil.dp2Px(10);
        for (int i = 0; i < arrCount; i++) {
            arr = a.getIndex(i);
            switch (arr){
                case R.styleable.CustomProgressBar_circleWidth:
                    circleWidth = a.getDimension(arr,circleWidth);
                    break;
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(arr,mFirstColor);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(arr,mSecondColor);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(arr,mSpeed);
                    break;
            }
        }

        //初始化画笔
        mFirstPaint = new Paint();
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setStyle(Paint.Style.STROKE);
        mFirstPaint.setStrokeWidth(circleWidth);
        mFirstPaint.setColor(mFirstColor);

        mSecondPaint = new Paint();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setStrokeWidth(circleWidth);
        mSecondPaint.setColor(mSecondColor);
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    mProgress++;
                    if(mProgress == 360){
                        mProgress = 0;
                        mIsNext = !mIsNext;
                    }
                    if(mIsNext){
                        mFirstPaint.setColor(mSecondColor);
                        mSecondPaint.setColor(mFirstColor);
                        mFirstProgerss = 360;
                        mSecondProgress = mProgress;
                    }else {
                        //第一个圈的颜色要改为第二个圈的颜色
                        mFirstPaint.setColor(mSecondColor);
                        mSecondPaint.setColor(mFirstColor);
                        mSecondProgress = 360;
                        mFirstProgerss = mProgress;
                    }
                    try {
                        Thread.currentThread().sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,DEFAULTHEIGHT);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightMeasureSpec){
            setMeasuredDimension(widthSpecSize,DEFAULTHEIGHT);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        float oval_width = getWidth()/2;
        float left = getWidth()/2-oval_width/2;
        float top = getHeight()/2-oval_width/2;
        float right = getWidth()/2 + oval_width/2;
        float bottom = getHeight()/2 + oval_width/2;
//        LogUtil.i("getWidth()="+getWidth()+"-getHeight()="+getHeight()+"-"+left+"-"+top+"-"+right+"-"+bottom);
        RectF oval = new RectF(left,top,right,bottom);

//        LogUtil.i("mSecondProgress="+mSecondProgress+"---secondColor="+mSecondPaint.getColor());
//        LogUtil.i("mFirstProgerss="+mFirstProgerss+"---firstColor="+mFirstPaint.getColor());
        if(mIsNext){
            //当两个圆圈交替时，由于第一个圆圈作为了背景，所以，先画第一个圆圈，
            //第二个圆圈在变动，所以第二个圆圈后画，第二个圆圈显示在最上面
            canvas.drawArc(oval,-90,mFirstProgerss,false,mFirstPaint);
            canvas.drawArc(oval,-90,mSecondProgress,false,mSecondPaint);
        }else {
            //第二个圆圈由于是作为背景，所以，先画，第二个圆圈
            canvas.drawArc(oval,-90,mSecondProgress,false,mSecondPaint);
            //在画第一个圆圈，这样第一个圆圈在上面，
            canvas.drawArc(oval,-90,mFirstProgerss,false,mFirstPaint);
        }
    }
}

package test.cn.example.com.androidskill.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.model.ColorEvaluator;
import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * 自定义使用属性动画的view
 * Created by xgxg on 2017/8/21.
 */

public class MyAnimatorView extends View {
    private Paint mPaint;
    private final float RADIUS = 50f;
    private Point currentPoint ,endPoint;
    private Context mContext;
    private String color;
    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
    }

    public MyAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化paint  ,Paint.ANTI_ALIAS_FLAG 表示 消除锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔的颜色
        mPaint.setColor(Color.BLUE);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int initWidth = DensityUtil.dip2px(mContext,150);
        int initHeight = DensityUtil.dip2px(mContext,150);
        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(initWidth,initHeight);
        }else if(widthMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(initWidth,heightSpecSize);
        }else if(heightMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,initHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == currentPoint){
            currentPoint = new Point(RADIUS,RADIUS);
            drawCircle(canvas);
            startAnimator();
        }else {
            drawCircle(canvas);
        }
    }

    private void startAnimator() {
        Point startPoint = new Point(RADIUS,RADIUS);
        LogUtil.i("getWidth()="+getWidth()+"---getHeight()="+getHeight());
        Point endPoint = new Point((getWidth() - startPoint.getX()),(getHeight() - startPoint.getY()));
//        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        valueAnimator.setDuration(5000);
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                currentPoint = (Point) animation.getAnimatedValue();
//                invalidate();
//            }
//        });

        ValueAnimator pointAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(), "#000000", "#ffffff");
        AnimatorSet set = new AnimatorSet();
        set.play(pointAnimator).with(colorAnimator);
        set.setDuration(5000).start();

        pointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),RADIUS,mPaint);
    }
}

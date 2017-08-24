package test.cn.example.com.androidskill.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/24.
 */

public class MyAnimatorView2 extends View {
    private Paint mPaint;
    private Point mCurrentPoint,startPoint,endPoint;

    private final float RADIUS = 20f;
    public MyAnimatorView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        startPoint = new Point(RADIUS,RADIUS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于是直接继承view，当这个view设置的width和height是wrapContent和matchParent时，
        //显示的大小都是一样的，为了区别中两种情况，需要在view的layoutParameter时，设置
        //具体的宽/高
        int withSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(50,50);
        }else if(MeasureSpec.AT_MOST == widthMeasureSpec){
            setMeasuredDimension(50,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(withSpecSize,50);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null == mCurrentPoint){
            mCurrentPoint = new Point(RADIUS,RADIUS);
            LogUtil.i("width="+getWidth()+"---height="+getHeight());
            endPoint = new Point(RADIUS,getHeight()-RADIUS);
            canvas.drawCircle(mCurrentPoint.getX(),mCurrentPoint.getY(),RADIUS,mPaint);
            startMyAnimator();
        }else {
            canvas.drawCircle(mCurrentPoint.getX(),mCurrentPoint.getY(),RADIUS,mPaint);
        }

    }

    private void startMyAnimator(){
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        animator.setDuration(5000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}

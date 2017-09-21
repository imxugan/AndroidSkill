package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/21.
 */

public class GestureLockView2 extends View {
    private int mDefaultWidth = DensityUtil.dp2Px(80);
    private int mDefaultHeight = DensityUtil.dp2Px(80);
    private int mDefaultInnerCircleColor = Color.RED;
    private int mDefaultOuterCircleColor = Color.BLUE;
    private int mDefaultArrowColor = Color.GREEN;
    private final Paint mPaint;
    private Path mArrowPath;
    private float mLastMoveX;
    private float mLastMoveY;
    private float mDegree;
    private VelocityTracker mVelocityTracker;

    public GestureLockView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockView);
        int attrCount = a.getIndexCount();
        int arr = 0;
        for (int i = 0; i < attrCount; i++) {
            arr = a.getIndex(i);
            switch (arr){
                case R.styleable.GestureLockView2_inner_circle_color:
                    mDefaultInnerCircleColor = a.getColor(arr, this.mDefaultInnerCircleColor);
                    break;
                case R.styleable.GestureLockView2_out_circle_color:
                    mDefaultOuterCircleColor = a.getColor(arr, this.mDefaultOuterCircleColor);
                    break;
                case R.styleable.GestureLockView2_arrow_circle_color:
                    mDefaultArrowColor = a.getColor(arr, this.mDefaultArrowColor);
                    break;
            }
        }
        a.recycle();
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int size = (widthSpecSize>heightSpecSize)?heightSpecSize:widthSpecSize;
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightMeasureSpec){
            size = mDefaultWidth;
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            size = (size>mDefaultWidth)?mDefaultWidth :size;
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            size = (size>mDefaultWidth)?mDefaultWidth :size;
        }
        setMeasuredDimension(size,size);
        mArrowPath = new Path();
        int outRadius = size/2;
        int innerRadius = outRadius/3;
//        LogUtil.i("outRadius="+outRadius+"---innerRadius="+innerRadius);
        mArrowPath.moveTo(outRadius,outRadius-innerRadius-DensityUtil.dp2Px(2));
        mArrowPath.lineTo(outRadius+outRadius*(0.8f),outRadius);
        mArrowPath.lineTo(outRadius,outRadius+innerRadius+DensityUtil.dp2Px(2));
        mArrowPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int outRadius = centerX;
        mPaint.setColor(mDefaultOuterCircleColor);
        canvas.drawCircle(centerX,centerY,outRadius,mPaint);
        int innerRadius = outRadius/3;
//        LogUtil.i("outRadius="+outRadius+"---innerRadius="+innerRadius);
        mPaint.setColor(mDefaultInnerCircleColor);
        canvas.drawCircle(centerX,centerY,innerRadius,mPaint);
        mPaint.setColor(mDefaultArrowColor);
        drawArrow(canvas, mDegree,centerX,centerY);

    }

    private void drawArrow(Canvas canvas, float degree, int centerX, int centerY) {
        LogUtil.i("degree="+degree);
//        这里canvas.save();和canvas.restore();是两个相互匹配出现的，
//        作用是用来保存画布的状态和取出保存的状态的。
//        这里稍微解释一下，
//        当我们对画布进行旋转，缩放，平移等操作的时候其实我们是想对特定的元素进行操作，
//        比如图片，一个矩形等，但是当你用canvas的方法来进行这些操作的时候，
//        其实是对整个画布进行了操作，那么之后在画布上的元素都会受到影响，
//        所以我们在操作之前调用canvas.save()来保存画布当前的状态，
//        当操作之后取出之前保存过的状态，这样就不会对其他的元素进行影响
        canvas.save();
        canvas.rotate(degree,centerX,centerY);
        canvas.drawPath(mArrowPath,mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocity(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastMoveX = event.getX();
                mLastMoveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("Math.abs(getXVolecity())="+Math.abs(getXVolecity()));
                LogUtil.i("Math.abs(getYVolecity())="+Math.abs(getYVolecity()));
                //添加加速度判断，减少三角形的举例晃动
                if(Math.abs(getXVolecity())>20 ||
                        Math.abs(getYVolecity())>20){
                    mDegree = getAngle(event.getX(),event.getY(),mLastMoveX,mLastMoveY);
                }
                mLastMoveX = event.getX();
                mLastMoveY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocity();
                break;
        }
//        return super.onTouchEvent(event);
        invalidate();
        return true;
    }

    private void recycleVelocity() {
        if(null != mVelocityTracker){
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private int getXVolecity(){
        mVelocityTracker.computeCurrentVelocity(100);
        return (int)mVelocityTracker.getXVelocity();
    }

    private int getYVolecity(){
        mVelocityTracker.computeCurrentVelocity(100);
        return (int)mVelocityTracker.getYVelocity();
    }

    private void obtainVelocity(MotionEvent event) {
        if(null == mVelocityTracker){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private float getAngle(float x, float y, float mLastMoveX, float mLastMoveY) {
        float dx = x - mLastMoveX;
        float dy = y - mLastMoveY;
        double atan2 = Math.atan2(dy, dx);
        return (float) Math.toDegrees(atan2);
    }

}

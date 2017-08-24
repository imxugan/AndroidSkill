package test.cn.example.com.androidskill.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/24.
 */

public class MyAnimatorView2 extends View {
    private Paint mPaint;
    private Point mCurrentPoint, mStartPoint, mEndPoint;
    private String mCurrent_interpolator;
    //匀速
    private final String LINEARINTERPOLATOR = "LinearInterpolator";
    //加速
    private final String ACCELERATEINTERPOLATOR = "AccelerateInterpolator";
    //先加速，后减速
    private final String ACCELERATEDECELERATEINTERPOLATOR = "AccelerateDecelerateInterpolator";
    //BounceInterpolator 动画结束的时候弹起
    private final String BOUNCEINTERPOLATOR = "BounceInterpolator";
    //DecelerateInterpolator 减速
    private final String DECELERATEINTERPOLATOR = "DecelerateInterpolator";
    //AnticipateInterpolator 开始的时候向后然后向前甩
    private final String ANTICIPATEINTERPOLATOR = "AnticipateInterpolator";
    //AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
    private final String ANTICIPATEOVERSHOOTINTERPOLATOR = "AnticipateOvershootInterpolator";

    private final float RADIUS = 20f;
    public MyAnimatorView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStartPoint = new Point(RADIUS,RADIUS);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAnimatorView2);
        mCurrent_interpolator = typedArray.getString(R.styleable.MyAnimatorView2_current_interpolator);


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
            mEndPoint = new Point(RADIUS,getHeight()-RADIUS);
            canvas.drawCircle(mCurrentPoint.getX(),mCurrentPoint.getY(),RADIUS,mPaint);
            startMyAnimator();
        }else {
            canvas.drawCircle(mCurrentPoint.getX(),mCurrentPoint.getY(),RADIUS,mPaint);
        }

    }

    private void startMyAnimator(){
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), mStartPoint, mEndPoint);
        animator.setDuration(5000);
        animator.setInterpolator(new AccelerateInterpolator());
        if(!TextUtils.isEmpty(mCurrent_interpolator)){
            if(LINEARINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new LinearInterpolator());
            }else if(ACCELERATEINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new AccelerateInterpolator());
            }else if(ACCELERATEDECELERATEINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
            }else if(BOUNCEINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new BounceInterpolator());
            }else if(DECELERATEINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new DecelerateInterpolator());
            }else if(ANTICIPATEINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new AnticipateInterpolator());
            }else if(ANTICIPATEOVERSHOOTINTERPOLATOR.equals(mCurrent_interpolator)){
                animator.setInterpolator(new AnticipateOvershootInterpolator());
            }
        }
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

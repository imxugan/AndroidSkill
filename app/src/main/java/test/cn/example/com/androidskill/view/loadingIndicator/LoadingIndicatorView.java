package test.cn.example.com.androidskill.view.loadingIndicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.loadingIndicator.BallBeatIndicator;
import test.cn.example.com.androidskill.model.loadingIndicator.Indicator;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/30.
 */

public class LoadingIndicatorView extends View {
    private static final BallBeatIndicator DEFAULT_INDICATOR = new BallBeatIndicator();
//    private static final BallPulseIndicator DEFAULT_INDICATOR = new BallPulseIndicator();
    private static final int MIN_SHOW_TIME = 500;
    private static final int MIN_DELAY = 500;
    private long mStartTime = -1;
    private boolean mPostedHide = false;
    private boolean mPostedShow = false;
    private boolean mDismissed = false;
    private final Runnable mDelaydHide = new Runnable() {
        @Override
        public void run() {
            mPostedShow = false;
            if(!mDismissed){
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

    private final Runnable mDelayShow = new Runnable() {
        @Override
        public void run() {
            mPostedShow = false;
            if(!mDismissed){
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

    int mMinWidth,mMaxWidth,mMinHeight,mMaxHeight;
    private Indicator mIndicator;
    private int mIndicatorColor;
    private boolean mShouldStartAnimationDrawable;

    public LoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,null,0,0);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        mMinWidth = 24;
        mMaxWidth = 48;
        mMinHeight = 24;
        mMaxHeight = 48;

        final TypedArray a = context.obtainStyledAttributes(attributeSet,
                R.styleable.LoadingIndicatorView, defStyleAttr, defStyleRes);
        mMinWidth = a.getDimensionPixelSize(R.styleable.LoadingIndicatorView_minWidth,mMinWidth);
        mMaxWidth = a.getDimensionPixelSize(R.styleable.LoadingIndicatorView_maxWidth,mMaxWidth);
        mMinHeight = a.getDimensionPixelSize(R.styleable.LoadingIndicatorView_minHeight,mMinHeight);
        mMaxHeight = a.getDimensionPixelSize(R.styleable.LoadingIndicatorView_maxHeight,mMaxHeight);
        String indicatorName = a.getString(R.styleable.LoadingIndicatorView_indicatorName);
        mIndicatorColor = a.getColor(R.styleable.LoadingIndicatorView_indicatorColor, Color.WHITE);
        setIndicator(indicatorName);
        if(null == mIndicator){
            setIndicator(DEFAULT_INDICATOR);
        }
        a.recycle();
    }

    public void setIndicator(String indicatorName) {
        if(TextUtils.isEmpty(indicatorName)){
            return;
        }

        StringBuilder drawableClassName = new StringBuilder();
        if(!indicatorName.contains(".")){
            String defaultPackageName = getClass().getPackage().getName();
            drawableClassName.append(defaultPackageName).append(".");
        }

        drawableClassName.append(indicatorName);
        try {
            //test.cn.example.com.androidskill.model.loadingIndicator.BallBeatIndicator
            LogUtil.i("drawableClassName.toString()="+drawableClassName.toString());
            //test.cn.example.com.androidskill.view.loadingIndicator.BallBeatIndicator
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            Indicator indicator = (Indicator) drawableClass.newInstance();
            setIndicator(indicator);
        } catch (ClassNotFoundException e) {
            LogUtil.i(e.toString());
            e.printStackTrace();
        } catch (InstantiationException e) {
            LogUtil.i(e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogUtil.i(e.toString());
            e.printStackTrace();
        }


    }

    public void setIndicator(Indicator indicator) {
        if(mIndicator != indicator){
            if(null != mIndicator){
                mIndicator.setCallback(null);
                unscheduleDrawable(mIndicator);
            }

            mIndicator = indicator;
            setIndicatorColor(mIndicatorColor);
            if(null != indicator){
                indicator.setCallback(this);
            }
            postInvalidate();
        }
    }

    private void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        mIndicator.setColor(color);
    }

    public void smoothToShow(){
        startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in));
        setVisibility(VISIBLE);
    }

    public void smoothToHide(){
        startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out));
        setVisibility(GONE);
    }

    public void hide(){
        mDismissed = true;
        removeCallbacks(mDelayShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if(diff >= MIN_SHOW_TIME || mStartTime == -1){
            setVisibility(View.GONE);
        }else {
            if(!mPostedHide){
                postDelayed(mDelaydHide,MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    public void show(){
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelaydHide);
        if(!mDismissed){
            postDelayed(mDelayShow,MIN_DELAY);
            mPostedShow = true;
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        LogUtil.i("verifyDrawable()");
        return who == mIndicator || super.verifyDrawable(who);
    }

    void startAnimation(){
        if(getVisibility() != VISIBLE){
            return;
        }

        if(mIndicator instanceof Animatable){
            mShouldStartAnimationDrawable = true;
        }

        postInvalidate();
    }

    void stopAnimation(){
        if(mIndicator instanceof Animatable){
            mIndicator.stop();
            mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    @Override
    public void setVisibility(int visibility) {
        if(getVisibility() != visibility){
            super.setVisibility(visibility);
            if(visibility == GONE || visibility == INVISIBLE){
                stopAnimation();
            }else {
                startAnimation();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        LogUtil.i("onVisibilityChanged()");
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == GONE || visibility == INVISIBLE){
            stopAnimation();
        }else {
            startAnimation();
        }
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
        LogUtil.i("invalidateDrawable()");
       if(verifyDrawable(drawable)){
           final Rect dirty = drawable.getBounds();
           final int scrollX = getScrollX() + getPaddingLeft();
           final int scrollY = getScrollY() + getPaddingTop();

           invalidate(dirty.left + scrollX ,dirty.top + scrollY,
                   dirty.right + scrollX ,dirty.bottom + scrollY);
       }else {
           super.invalidateDrawable(drawable);
       }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtil.i("onSizeChanged()");
        updateDrawableBounds(w,h);
    }

    private void updateDrawableBounds(int w, int h) {
        w -=getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();
        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;
        if(null !=mIndicator){
            final int intrinsicWidth = mIndicator.getIntrinsicWidth();
            final int intrinsicHeight = mIndicator.getIntrinsicHeight();
            final float intrinsicAspect = intrinsicWidth/intrinsicHeight;
            final float boundAspect = w/h;
            if(intrinsicAspect != boundAspect){
                if(boundAspect > intrinsicAspect){
                    final int width = (int)(h * intrinsicAspect);
                    left = (w -width)/2;
                    right = left + width;
                }else {
                    final int height = (int)(w * 1/intrinsicAspect);
                    top = (h - height)/2;
                    bottom = top + height;
                }
            }
            mIndicator.setBounds(left,top,right,bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    private void drawTrack(Canvas canvas) {
        final Drawable d = mIndicator;
        if(null != d){
            final int saveCount = canvas.save();
            canvas.translate(getPaddingLeft(),getPaddingTop());
            d.draw(canvas);
            canvas.restoreToCount(saveCount);
            if(mShouldStartAnimationDrawable && d instanceof Animatable){
                ((Animatable)d).start();
                mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;
        final Drawable d = mIndicator;
        if(null != d){
            dw = Math.max(mMinWidth,d.getIntrinsicWidth());
            dh = Math.max(mMinHeight,d.getIntrinsicHeight());
        }

        updateDrawableState();

        dw +=getPaddingLeft() + getPaddingRight();
        dh += getPaddingTop() + getPaddingBottom();

        final int measuredWidth = resolveSizeAndState(dw,widthMeasureSpec,0);
        final int measuredHeight = resolveSizeAndState(dh,heightMeasureSpec,0);

        setMeasuredDimension(measuredWidth,measuredHeight);
    }

    private void updateDrawableState() {
        final int[] state = getDrawableState();
        if(null != mIndicator && mIndicator.isStateful()){
            mIndicator.setState(state);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if(null != mIndicator){
            mIndicator.setHotspot(x,y);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        LogUtil.i("onAttachedToWindow()");
        super.onAttachedToWindow();
        startAnimation();
        removeCallbacks();
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtil.i("onDetachedFromWindow()");
        stopAnimation();
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelaydHide);
        removeCallbacks(mDelayShow);
    }
}

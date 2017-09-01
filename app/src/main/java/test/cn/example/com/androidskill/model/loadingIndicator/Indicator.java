package test.cn.example.com.androidskill.model.loadingIndicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/29.
 */

public abstract class Indicator extends Drawable implements Animatable {
    private HashMap<ValueAnimator,ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();
    private ArrayList<ValueAnimator> mValueAnimators;
    private int alpha = 255;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    protected Rect drawBounds = ZERO_BOUNDS_RECT;
    private boolean mHasAnimators;
    private Paint mPaint = new Paint();

    public Indicator(){
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public int getColor(){
        return mPaint.getColor();
    }

    public void setColor(int color){
        mPaint.setColor(color);
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public int getAlpha() {
        return this.alpha;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {}

    @Override
    public void draw(Canvas canvas) {
        draw(canvas,mPaint);
    }

    public abstract void draw(Canvas canvas,Paint paint);

    public abstract ArrayList<ValueAnimator> onCreateAnimators();

    @Override
    public void start() {
        LogUtil.i("start()");
        ensureAnimators();
        if(null ==mValueAnimators){
            return;
        }

        if(isStarted()){
            return;
        }

        startAnimators();
        invalidateSelf();
    }

    private void startAnimators(){
        for (int i = 0; i < mValueAnimators.size(); i++) {
            ValueAnimator valueAnimator = mValueAnimators.get(i);
            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(valueAnimator);
            if(null != updateListener){
                valueAnimator.addUpdateListener(updateListener);
            }
            valueAnimator.start();
        }
    }

    private boolean isStarted(){
        for (ValueAnimator valueAnimator:mValueAnimators){
            return valueAnimator.isStarted();
        }
        return false;
    }

    private void ensureAnimators(){
        if(!mHasAnimators){
            mValueAnimators = onCreateAnimators();
            mHasAnimators = true;
        }
    }

    @Override
    public void stop() {
        stopAnimators();
    }

    private void stopAnimators(){
        if(null != mValueAnimators){
            for (ValueAnimator valueAnimator:mValueAnimators) {
                if(null != valueAnimator && valueAnimator.isStarted()){
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.end();
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        for (ValueAnimator vlaueAnimator : mValueAnimators) {
            return vlaueAnimator.isRunning();
        }
        return false;
    }

    public void addUpdateListener(ValueAnimator animator,ValueAnimator.AnimatorUpdateListener updateListener){
        mUpdateListeners.put(animator,updateListener);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setDrawBounds(bounds);
    }

    private void setDrawBounds(Rect bounds) {
        setDrawBounds(bounds.left,bounds.top,bounds.right,bounds.bottom);
    }

    private void setDrawBounds(int left, int top, int right, int bottom) {
        this.drawBounds = new Rect(left,top,right,bottom);
    }

    public void postInvalidate(){
        invalidateSelf();
    }

    public Rect getDrawBounds(){
        return drawBounds;
    }

    public int getWidth(){
        return drawBounds.width();
    }

    public int getHeight(){
        return drawBounds.height();
    }

    public int centerX(){
        return drawBounds.centerX();
    }

    public int centerY(){
        return drawBounds.centerY();
    }

    public float exactCenterX(){
        return drawBounds.exactCenterX();
    }

    public float exactCenterY(){
        return drawBounds.exactCenterY();
    }

}

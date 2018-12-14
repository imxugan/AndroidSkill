package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/13.
 */

public class CustomFlipBoardView2 extends View {
    private Paint mPaint;
    private Bitmap bitmap;
    private Camera camera = new Camera();
    private int bitmapWidth;
    private int bitmapHeight;
    private float centerX ,centerY;
    private float left,top,right,bottom;
    private int maxDegreeX = 45;
    private int maxDegreeY = 60;
    private int duration = 500;

    private AnimatorSet animatorSet;
    private int index;

    public int getDegreeXBottom() {
        return degreeXBottom;
    }

    public void setDegreeXBottom(int degreeXBottom) {
        this.degreeXBottom = degreeXBottom;
        invalidate();
    }

    private int degreeXBottom;
    public int getDegreeYLeft() {
        return degreeYLeft;
    }

    public void setDegreeYLeft(int degreeYLeft) {
        this.degreeYLeft = degreeYLeft;
        invalidate();
    }

    private int degreeYLeft;
    public int getDegreeXTop() {
        return degreeXTop;
    }

    public void setDegreeXTop(int degreeXTop) {
        this.degreeXTop = degreeXTop;
        invalidate();
    }

    private int degreeXTop;

    public int getDegreeYRight() {
        return degreeYRight;
    }

    public void setDegreeYRight(int degreeYRight) {
        this.degreeYRight = degreeYRight;
        invalidate();
    }

    private int degreeYRight;

    public CustomFlipBoardView2(Context context) {
        super(context);
        init();
    }

    public CustomFlipBoardView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFlipBoardView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorYRight = ObjectAnimator.ofInt(this, "degreeYRight", 0, maxDegreeY, 0);
        objectAnimatorYRight.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtil.i("onAnimationStart  index=  "+index +"    degreeYRight "+degreeYRight);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                index = 1;
            }
        });
        ObjectAnimator objectAnimatorXTop = ObjectAnimator.ofInt(this, "degreeXTop", 0, maxDegreeX, 0);
        objectAnimatorXTop.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtil.i("onAnimationStart  index=  "+index +"    degreeXTop "+degreeXTop);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                index = 2;
            }
        });
        ObjectAnimator objectAnimatorYLeft = ObjectAnimator.ofInt(this, "degreeYLeft", 0, maxDegreeY, 0);
        objectAnimatorYLeft.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtil.i("onAnimationStart  index=  "+index +"    degreeYLeft "+degreeYLeft);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                index = 3;
            }
        });
        ObjectAnimator objectAnimatorXBottom = ObjectAnimator.ofInt(this, "degreeXBottom", 0, maxDegreeX, 0);
        objectAnimatorXBottom.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtil.i("onAnimationStart  index=  "+index +"    degreeXBottom "+degreeXBottom);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationRepeat(animation);
                index = 0;
            }
        });
        //如果使用play().before()，这样，animatorSet内部的动画集合中的各个子动画，就会一起start,只是各个子动画，end的时候不同
//        animatorSet.play(objectAnimatorYRight).before(objectAnimatorXTop).before(objectAnimatorYLeft).before(objectAnimatorXBottom);
        //使用palySequentially()方法，animatorSet内部的子动画，则会按照，一个开始，结束，到下一个子动画开始，结束，这样的一个轮回的进行下去
        animatorSet.playSequentially(objectAnimatorYRight,objectAnimatorXTop,objectAnimatorYLeft,objectAnimatorXBottom);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.i("ondraw  degreeYRight="+degreeYRight+"   degreeXTop="+degreeXTop);
//        LogUtil.i("ondraw  index="+index);
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        left = centerX - bitmapWidth/2;
        top = centerY - bitmapHeight/2;
        right = centerX + bitmapWidth/2;
        bottom = centerY + bitmapHeight/2;
        if(index == 0){
            canvas.save();
            canvas.clipRect(left,top,centerX,bottom);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(centerX,top,right,bottom);
            camera.save();
            camera.rotateY(-degreeYRight);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            canvas.translate(-centerX,-centerY);
            camera.restore();
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }
        if(index == 1){
            canvas.save();
            canvas.clipRect(left,centerY,right,bottom);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,top,right,centerY);
            camera.save();
            camera.rotateX(-degreeXTop);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }

        if(index == 2){
            canvas.save();
            canvas.clipRect(centerX,top,right,bottom);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,top,centerX,bottom);
            camera.save();
            camera.rotateY(degreeYLeft);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }

        if(index == 3){
            canvas.save();
            canvas.clipRect(left,top,right,centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,centerY,right,bottom);
            camera.save();
            camera.rotateX(degreeXBottom);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorSet.end();
    }
}

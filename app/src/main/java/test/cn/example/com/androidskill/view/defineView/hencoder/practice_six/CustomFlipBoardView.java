package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
 * Created by xugan on 2018/12/12.
 */

public class CustomFlipBoardView extends View {
    private Paint mPaint;
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private Camera camera;
    private ObjectAnimator objectAnimator_Y_right;
    private AnimatorSet animatorSet;
    private boolean is_objectAnimator_Y_right_end;
    private boolean is_objectAnimator_X_top_end;
    private boolean is_objectAnimator_Y_left_end;
    private boolean is_objectAnimator_X_bottom_end;
    private int duration = 500;
    private ObjectAnimator objectAnimatorX_bottom;
    private ObjectAnimator objectAnimatorY_left;
    private ObjectAnimator objectAnimatorX_top;

    public int getDegree_X_bottom() {
        return degree_X_bottom;
    }

    public void setDegree_X_bottom(int degree_X_bottom) {
        this.degree_X_bottom = degree_X_bottom;
        invalidate();
    }

    private int degree_X_bottom;

    public int getDegree_Y_left() {
        return degree_Y_left;
    }

    public void setDegree_Y_left(int degree_Y_left) {
        this.degree_Y_left = degree_Y_left;
        invalidate();
    }

    private int degree_Y_left;

    public int getDegree_Y_right() {
        return degree_Y_right;
    }

    public void setDegree_Y_right(int degree_Y_right) {
        this.degree_Y_right = degree_Y_right;
        invalidate();
    }

    private int degree_Y_right;


    public int getDegree_X_top() {
        return degree_X_top;
    }

    public void setDegree_X_top(int degree_X_top) {
        this.degree_X_top = degree_X_top;
        invalidate();
    }

    private int degree_X_top;

    public CustomFlipBoardView(Context context) {
        super(context);
        initPaint();
    }

    public CustomFlipBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomFlipBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        float left = centerX-bitmapWidth/2;
        float top = centerY-bitmapHeight/2;
        float right = centerX + bitmapWidth/2;
        float bottom = centerY + bitmapHeight/2;
        if(!is_objectAnimator_Y_right_end){
            canvas.save();
            canvas.clipRect(left,top,centerX,bottom);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(centerX,top,right,bottom);
            camera.save();
            camera.rotateY(-degree_Y_right);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }

        //绘制上半部分的动画
        if(is_objectAnimator_Y_right_end&& !is_objectAnimator_X_top_end){
            canvas.save();
            canvas.clipRect(left,centerY,left+bitmapWidth,top+bitmapHeight);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,top,right,centerY);
            camera.save();
            camera.rotateX(-degree_X_top);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }
//        LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//        LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//        LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
        if(is_objectAnimator_X_top_end && !is_objectAnimator_Y_left_end ){
            canvas.save();
            canvas.clipRect(centerX,top,right,bottom);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,top,centerX,bottom);
            camera.save();
            camera.rotateY(degree_Y_left);
            canvas.translate(centerX,centerY);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-centerX,-centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();
        }

        if(is_objectAnimator_Y_left_end && !is_objectAnimator_X_bottom_end){
            canvas.save();
            canvas.clipRect(left,top,left+bitmapWidth,centerY);
            canvas.drawBitmap(bitmap,left,top,mPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(left,centerY,left+bitmapWidth,bottom);
            camera.save();
            camera.rotateX(degree_X_bottom);
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
        objectAnimator_Y_right.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(objectAnimator_Y_right.isRunning()){
            objectAnimator_Y_right.end();
        }

        if(objectAnimatorX_top.isRunning()){
            objectAnimatorX_top.end();
        }

        if(objectAnimatorY_left.isRunning()){
            objectAnimatorY_left.end();
        }

        if(objectAnimatorX_bottom.isRunning()){
            objectAnimatorX_bottom.end();
        }
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        camera = new Camera();
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        animatorSet = new AnimatorSet();
        objectAnimatorX_bottom = ObjectAnimator.ofInt(this, "degree_X_bottom", 0, 45, 0);
        objectAnimatorX_bottom.setDuration(duration);
        objectAnimatorX_bottom.setInterpolator(new LinearInterpolator());
        objectAnimatorX_bottom.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorX_bottom.setRepeatMode(ValueAnimator.RESTART);
        objectAnimatorX_bottom.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
//                LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//                LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//                LogUtil.i("is_objectAnimator_X_bottom_end= "+is_objectAnimator_X_bottom_end);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                is_objectAnimator_X_bottom_end = true;
                is_objectAnimator_Y_right_end = false;
                objectAnimatorX_bottom.end();
                objectAnimator_Y_right.start();
            }
        });

        objectAnimatorY_left = ObjectAnimator.ofInt(this, "degree_Y_left", 0, 45, 0);
        objectAnimatorY_left.setDuration(duration);
        objectAnimatorY_left.setInterpolator(new LinearInterpolator());
        objectAnimatorY_left.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorY_left.setRepeatMode(ValueAnimator.RESTART);
        objectAnimatorY_left.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                is_objectAnimator_Y_left_end = true;
                is_objectAnimator_X_bottom_end = false;
//                LogUtil.i("onAnimationRepeat  degree_Y_left="+degree_Y_left);
//                LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//                LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//                LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
                objectAnimatorY_left.end();
                objectAnimatorX_bottom.start();
            }
        });

        objectAnimatorX_top = ObjectAnimator.ofInt(this, "degree_X_top", 0, 45, 0);
        objectAnimatorX_top.setDuration(duration);
        objectAnimatorX_top.setInterpolator(new LinearInterpolator());
        objectAnimatorX_top.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorX_top.setRepeatMode(ValueAnimator.RESTART);
        objectAnimatorX_top.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
//                LogUtil.e("onAnimationStart  degree_X_top="+degree_X_top);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                is_objectAnimator_X_top_end = true;
                is_objectAnimator_Y_left_end = false;
//                LogUtil.e("onAnimationRepeat  degree_X_top="+degree_X_top);
//                LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//                LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//                LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
                objectAnimatorX_top.end();
                objectAnimatorY_left.start();
            }
        });

        objectAnimator_Y_right = ObjectAnimator.ofInt(this,"degree_Y_right",0,45,0);
        objectAnimator_Y_right.setDuration(duration);
        objectAnimator_Y_right.setInterpolator(new LinearInterpolator());
        objectAnimator_Y_right.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                LogUtil.i("onAnimationEnd  "+degree_Y_right);
//                LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
//                LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//                LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//                LogUtil.i("is_objectAnimator_X_bottom_end= "+is_objectAnimator_X_bottom_end);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                is_objectAnimator_Y_right_end = true;
                is_objectAnimator_X_top_end = false;
//                LogUtil.i("onAnimationRepeat  degree_Y_right= "+degree_Y_right);
//                LogUtil.i("is_objectAnimator_Y_right_end= "+is_objectAnimator_Y_right_end);
//                LogUtil.i("is_objectAnimator_X_top_end= "+is_objectAnimator_X_top_end);
//                LogUtil.i("is_objectAnimator_Y_left_end= "+is_objectAnimator_Y_left_end);
                //这里必须要end一次，由于设置的 重复次数是无限次，否则，repaeat方法就一直执行，这里
                //is_objectAnimator_X_top_end 的值就会被赋值为false,这样就影响了onDraw方法中的if条件判断的逻辑,
                //其他方法中的，end方法的调用的原因也是如此
                objectAnimator_Y_right.end();
                objectAnimatorX_top.start();
            }
        });
        //设置循环次数,设置为INFINITE表示无限循环
        objectAnimator_Y_right.setRepeatCount(ValueAnimator.INFINITE);
        //设置循环模式: RESTART，REVERSE
        objectAnimator_Y_right.setRepeatMode(ValueAnimator.RESTART);

    }
}

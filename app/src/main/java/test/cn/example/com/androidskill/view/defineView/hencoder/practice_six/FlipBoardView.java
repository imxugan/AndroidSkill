package test.cn.example.com.androidskill.view.defineView.hencoder.practice_six;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/12.
 */

public class FlipBoardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public FlipBoardView(Context context) {
        super(context);
    }

    public FlipBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);

        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.i(""+animation.getDuration());
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                LogUtil.i(""+animation.getDuration()+"      "+degree);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;


        // 第一遍绘制：上半部分
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // 第二遍绘制：下半部分
        canvas.save();
        if (degree < 90) {
            //当degree小与90度时，只显示整张图片的下半部分的投影
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        } else {
            //当degree大于90度时，只显示整张图片的上半部分的投影
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        camera.save();
        camera.rotateX(degree);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //沿Y轴旋转
        canvas.save();
        canvas.clipRect(0,0,centerX,centerY+bitmapHeight/2);
        canvas.drawBitmap(bitmap,x,y,paint);
        canvas.restore();

        canvas.save();
        if(degree<90){
            canvas.clipRect(centerX,y,centerX+bitmapWidth/2,centerY+bitmapHeight/2);
        }else {
            canvas.clipRect(0,0,centerX,centerY+bitmapHeight/2);
        }
        camera.save();
        camera.rotateY(-degree);
        canvas.translate(centerX,centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX,-centerY);
        camera.restore();

        canvas.drawBitmap(bitmap,x,y,paint);
        canvas.restore();


    }
}

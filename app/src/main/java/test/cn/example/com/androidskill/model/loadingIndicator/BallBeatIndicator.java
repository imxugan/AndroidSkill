package test.cn.example.com.androidskill.model.loadingIndicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/30.
 */
public class BallBeatIndicator extends Indicator {
    public static final float SCALE = 1.0f;
    public static final int ALPHA = 255;
    private float[] scaleFloats = new float[]{SCALE,SCALE,SCALE};
    int[] alphas = new int[]{ALPHA,ALPHA,ALPHA};
    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing = 4;
        float radius = (getWidth() - circleSpacing * 2)/6;
        float x = getWidth()/2 - (radius * 2 + circleSpacing);
        float y = getHeight()/2;
        for (int i = 0; i < 3; i++) {
            canvas.save();//锁画布(为了保存之前的画布状态)
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            LogUtil.i("translateX="+translateX);
            //把当前画布的原点移到(translateX,y),后面的操作都以(translateX,y)作为参照点，
            canvas.translate(translateX,y);
            canvas.scale(scaleFloats[i],scaleFloats[i]);
            paint.setAlpha(alphas[i]);
            canvas.drawCircle(0,0,radius,paint);
            canvas.restore();//把当前画布返回（调整）到上一个save()状态之前
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = new int[]{350,0,350};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1f,0.75f,1f);
            scaleAnim.setDuration(700);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255,51,255);
            alphaAnim.setDuration(700);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            addUpdateListener(alphaAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }
}

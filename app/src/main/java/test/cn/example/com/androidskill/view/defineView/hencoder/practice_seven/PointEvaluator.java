package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.TypeEvaluator;

/**
 * Created by xugan on 2018/12/16.
 */

public class PointEvaluator implements TypeEvaluator<float[]> {
    private float[] outValue = new float[2];
    @Override
    public float[] evaluate(float fraction, float[] startValue, float[] endValue) {
        outValue[0] = startValue[0]+fraction*(endValue[0] - startValue[0]);
        outValue[1] = startValue[1]+fraction*(endValue[1] - startValue[1]);
        return outValue;
    }
}

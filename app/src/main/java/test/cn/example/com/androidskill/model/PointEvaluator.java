package test.cn.example.com.androidskill.model;

import android.animation.TypeEvaluator;

/**
 * 自定义估值器
 * Created by xgxg on 2017/8/21.
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        float startFloatX = startPoint.getX();
        float startFloatY = startPoint.getY();
        float endFloatX = startFloatX + fraction * (((Point)endValue).getX() - startFloatX);
        float endFloatY = startFloatY + fraction * (((Point)endValue).getY() - startFloatY);
        return new Point(endFloatX,endFloatY);
    }
}

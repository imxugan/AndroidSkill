package test.cn.example.com.androidskill.model;

import android.animation.TimeInterpolator;

/**
 * 自定义差值器，先减速后加速
 * Created by xgxg on 2017/8/24.
 */

public class MyDecelerateAccelerateInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float fraction;
        if(input <=0.5){
            fraction = (float)Math.sin(input * Math.PI) / 2;
        }else {
            fraction = (float)(2 - Math.sin(input * Math.PI ))/2;
        }
        return fraction;
    }
}

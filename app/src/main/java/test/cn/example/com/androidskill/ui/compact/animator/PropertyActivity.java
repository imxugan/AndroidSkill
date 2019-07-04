package test.cn.example.com.androidskill.ui.compact.animator;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.art.chapter_seven.ViewPropertyAnimatorActivity;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/3.
 */

public class PropertyActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(this, ViewPropertyAnimatorActivity.class));
                break;
            case R.id.btn_2:
                paowuxian(v);
                break;
        }
    }

    private void paowuxian(final View v) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(8000);
        final float startX = v.getX();
        final float startY = v.getY();
        valueAnimator.setObjectValues(new PointF(startX, startY));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                //x = vt  v的初始速度我们自己设定为100，t 这个时间，要根据百分比乘以总时间，由于setDuoration中的
                //时间是5000毫秒，所以这里设置为5秒，
                float t = 5 * fraction;//由于这里时间的但是是秒，所以用总时间5000毫米/1000 = 5 秒
                pointF.x = startX+100*t;
                //y = 1/2 * g * t * t
                pointF.y = startY+0.5f * 9.8f * t* t;
                LogUtil.i(pointF.x+"                    "+pointF.y);
                return pointF;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {
               PointF pointF = (PointF) animation.getAnimatedValue();
               LogUtil.e(pointF.x+"                    "+pointF.y);
               v.setX(pointF.x);
               v.setY(pointF.y);
           }
        });
        valueAnimator.start();

    }
}

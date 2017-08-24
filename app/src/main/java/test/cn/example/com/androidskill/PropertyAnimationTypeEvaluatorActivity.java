package test.cn.example.com.androidskill;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.LogUtil;

/**
 * 属性动画TypeEvaluator演示
 */
public class PropertyAnimationTypeEvaluatorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_type_evaluator);
        initView();
    }

    private void initView() {
        Button testIntEvaluator = (Button) findViewById(R.id.testIntEvaluator);
        testIntEvaluator.setOnClickListener(this);
        Button testFloatEvaluator = (Button) findViewById(R.id.testFloatEvaluator);
        testFloatEvaluator.setOnClickListener(this);
        Button testArgbEvaluator = (Button) findViewById(R.id.testArgbEvaluator);
        testArgbEvaluator.setOnClickListener(this);
        Button testPointEvaluator = (Button) findViewById(R.id.testPointEvaluator);
        testPointEvaluator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testIntEvaluator:
                testIntEvaluator();
                break;
            case R.id.testFloatEvaluator:
                testFloatEvaluator();
                break;
            case R.id.testArgbEvaluator:
                testArgbEvaluator();
                break;
            case R.id.testPointEvaluator:
                testPointEvaluator();
                break;
        }
    }

    private void testArgbEvaluator(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //版本低于5.0不能使用
            ValueAnimator valueAnimator = ValueAnimator.ofArgb(0, 255);
            valueAnimator.setDuration(100);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    LogUtil.i("animation.getAnimatedValue="+animation.getAnimatedValue()+"---AnimatedFraction="+animation.getAnimatedFraction());
                }
            });
            valueAnimator.start();
        }
    }

    private void testFloatEvaluator(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(10f, 100f);
        valueAnimator.setDuration(100);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LogUtil.i("animation.getAnimatedValue="+animation.getAnimatedValue()+"---AnimatedFraction="+animation.getAnimatedFraction());
                //打印结果：
//                08-25 10:17:58.340 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=10.0---AnimatedFraction=0.0
//                08-25 10:17:58.350 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=16.266605---AnimatedFraction=0.069628954
//                08-25 10:17:58.360 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=32.09314---AnimatedFraction=0.24547935
//                08-25 10:17:58.370 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=55.0---AnimatedFraction=0.5
//                08-25 10:17:58.390 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=76.678925---AnimatedFraction=0.740877
//                08-25 10:17:58.410 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=93.73338---AnimatedFraction=0.9303709
//                08-25 10:17:58.430 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=99.97779---AnimatedFraction=0.99975324
//                08-25 10:17:58.450 19637-19637/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::66::onAnimationUpdate-->>animation.getAnimatedValue=100.0---AnimatedFraction=1.0
            }
        });
    }

    private void testIntEvaluator(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LogUtil.i("animation.getAnimatedValue="+animation.getAnimatedValue()+"---AnimatedFraction="+animation.getAnimatedFraction());
                //打印结果：
//                08-25 10:03:16.370 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=1---AnimatedFraction=0.0
//                08-25 10:03:16.370 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=1---AnimatedFraction=0.0
//                08-25 10:03:16.390 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=1---AnimatedFraction=0.007902175
//                08-25 10:03:16.400 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=3---AnimatedFraction=0.029559612
//                08-25 10:03:16.420 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=7---AnimatedFraction=0.066987276
//                08-25 10:03:16.440 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=12---AnimatedFraction=0.11474341
//                08-25 10:03:16.450 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=18---AnimatedFraction=0.17328969
//                08-25 10:03:16.470 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=25---AnimatedFraction=0.24547935
//                08-25 10:03:16.480 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=32---AnimatedFraction=0.320816
//                08-25 10:03:16.500 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=41---AnimatedFraction=0.40630943
//                08-25 10:03:16.520 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=49---AnimatedFraction=0.4895288
//                08-25 10:03:16.530 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=57---AnimatedFraction=0.57304144
//                08-25 10:03:16.550 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=66---AnimatedFraction=0.6594797
//                08-25 10:03:16.570 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=73---AnimatedFraction=0.7362755
//                08-25 10:03:16.580 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=81---AnimatedFraction=0.81057394
//                08-25 10:03:16.600 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=87---AnimatedFraction=0.8715724
//                08-25 10:03:16.620 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=92---AnimatedFraction=0.9249463
//                08-25 10:03:16.630 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=96---AnimatedFraction=0.9629353
//                08-25 10:03:16.650 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=98---AnimatedFraction=0.98795843
//                08-25 10:03:16.670 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=99---AnimatedFraction=0.9995614
//                08-25 10:03:16.680 7265-7265/test.cn.example.com.androidskill I/MY_LOG: PropertyAnimationMiddleActivity.java::59::onAnimationUpdate-->>animation.getAnimatedValue=100---AnimatedFraction=1.0
            }
        });
        valueAnimator.start();

        ValueAnimator.ofInt(1,20);
    }

    private void testPointEvaluator() {
        Point startPoint = new Point(10,10);
        Point endPoint = new Point(100,100);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point p = (Point)animation.getAnimatedValue();
                LogUtil.i("x="+p.getX()+"---y="+p.getY());
            }
        });

    }
}

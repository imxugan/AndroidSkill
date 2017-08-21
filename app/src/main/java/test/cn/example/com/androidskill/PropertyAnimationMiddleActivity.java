package test.cn.example.com.androidskill;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.LogUtil;

import static test.cn.example.com.androidskill.R.id.ball;

/**
 * 属性动画中等演示
 */
public class PropertyAnimationMiddleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_middle);
        initView();
    }

    private void initView() {
        Button testPointEvaluator = (Button) findViewById(R.id.testPointEvaluator);
        testPointEvaluator.setOnClickListener(this);
        Button ball = (Button) findViewById(R.id.ball);
        ball.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testPointEvaluator:
                testPointEvaluator();
                break;
            case ball:
                testBall();
                break;
        }
    }

    private void testBall() {
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

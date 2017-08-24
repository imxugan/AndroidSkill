package test.cn.example.com.androidskill;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.model.Point;
import test.cn.example.com.androidskill.model.PointEvaluator;
import test.cn.example.com.util.LogUtil;

/**
 * 属性动画高级演示
 * Created by xgxg on 2017/8/23.
 */
public class PropertyAnimationInterpolatorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_interpolator);
        initView();
//        AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
//        AccelerateInterpolator 在动画开始的地方速率改变比较慢，然后开始加速
//        DecelerateInterpolator 在动画开始的地方快然后慢
//        LinearInterpolator 以常量速率改变
//        BounceInterpolator 动画结束的时候弹起

//        AnticipateInterpolator 开始的时候向后然后向前甩
//        AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
//        CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
//        OvershootInterpolator 向前甩一定值后再回到原来位置
//        如果android定义的interpolators不符合你的效果也可以自定义interpolators
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
        }
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

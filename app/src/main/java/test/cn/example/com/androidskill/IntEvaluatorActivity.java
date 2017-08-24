package test.cn.example.com.androidskill;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import test.cn.example.com.util.LogUtil;

/**
 * IntEvaluator实际运用演示
 * Created by xgxg on 2017/8/23.
 */
public class IntEvaluatorActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_toggle;
    private int height ;
    private boolean isDown = true;
    private FrameLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //属性动画ValueAnimator用法
        //http://www.cnblogs.com/johnsonwei/p/5672122.html
        setContentView(R.layout.activity_int_evaluator);
        initView();
    }

    private void initView() {
        ll_toggle = (LinearLayout) findViewById(R.id.ll_toggle);
        ll_toggle.setOnClickListener(this);
        params = (FrameLayout.LayoutParams)ll_toggle.getLayoutParams();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_toggle:
                testIntEvaluator();
                break;
        }
    }

    private void testIntEvaluator(){
        LogUtil.i("isDown="+isDown);
        if(!isDown){
            move(height,30);
        }else {
            move(0,height);
        }

    }

    private void move(int startValue,int endValue) {
        isDown = !isDown;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.height = (int)animation.getAnimatedValue();
                LogUtil.i(""+params.height);
                ll_toggle.setLayoutParams(params);
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ll_toggle.post(new Runnable() {
            @Override
            public void run() {
                height = ll_toggle.getMeasuredHeight();
                LogUtil.i("height=============="+height);
            }
        });
    }
}

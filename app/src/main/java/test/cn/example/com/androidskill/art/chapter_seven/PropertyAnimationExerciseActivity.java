package test.cn.example.com.androidskill.art.chapter_seven;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import test.cn.example.com.androidskill.ExplodeActivity;
import test.cn.example.com.androidskill.IntEvaluatorActivity;
import test.cn.example.com.androidskill.LoadingIndicatorActivity;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.TransZFBActivity;
import test.cn.example.com.androidskill.ZFBAnimator2Activity;
import test.cn.example.com.androidskill.view.ViewWrapper;

/**
 * 属性动画练习
 * Created by xgxg on 2017/8/23.
 */
public class PropertyAnimationExerciseActivity extends AppCompatActivity implements View.OnClickListener {
    private Button testIntEvaluator,trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_exercise);
        initView();
    }

    private void initView() {
        testIntEvaluator = (Button) findViewById(R.id.testIntEvaluator);
        testIntEvaluator.setOnClickListener(this);
        trans = (Button) findViewById(R.id.trans);
        trans.setOnClickListener(this);
        Button explode = (Button) findViewById(R.id.explode);
        explode.setOnClickListener(this);
        Button loadingIndicator = (Button) findViewById(R.id.loadingIndicator);
        loadingIndicator.setOnClickListener(this);
        Button animator2 = (Button) findViewById(R.id.animator2);
        animator2.setOnClickListener(this);

        TextView textView = (TextView) findViewById(R.id.text);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textView,"translationX",-300f,0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textView,"rotation",0f,360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textView,"alpha",1f,0f,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate).with(fadeInOut).after(moveIn);
        animatorSet.setDuration(5000);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        //记得在animation_scale文件中给scal标签添加如下属性否则，效果不出现
        // android:fromYScale="1.0"
        //android:toYScale="1.0"
        Button scale = (Button)findViewById(R.id.scale);
        Animation scale_animation = AnimationUtils.loadAnimation(PropertyAnimationExerciseActivity.this,R.anim.animation_scale);
        scale.startAnimation(scale_animation);

        //用一个类包装原始对象，间接提供get和set方法的方式来完成属性动画
        Button scale2 = (Button)findViewById(R.id.scale2);
        ViewWrapper wrapper = new ViewWrapper(scale2);
        ObjectAnimator.ofInt(wrapper,"width",500).setDuration(5000).start();

        //采用ValueAnimaor，监听动画过程，实现属性的改变
//        ValueAnimator 本身不作用任何对象，也就是说直接使用它没有任何的动画效果,
//        但是它可以对一个值做动画。我们可以监听动画过程，在动画过程中修改我们的
//        对象属性值，这样相当于对对象做了动画。
        final Button scale3 = (Button)findViewById(R.id.scale3);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                scale3.getLayoutParams().width = mEvaluator.evaluate(fraction,scale3.getWidth(),500);
                scale3.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testIntEvaluator:
                testIntEvaluator();
                break;
            case R.id.trans:
                trans();
                break;
            case R.id.animator2:
                animator2();
                break;
            case R.id.explode:
                explode();
                break;
            case R.id.loadingIndicator:
                loadingIndicator();
                break;
        }
    }

    private void loadingIndicator() {
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,LoadingIndicatorActivity.class));
    }

    private void explode(){
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,ExplodeActivity.class));
    }

    private void animator2(){
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,ZFBAnimator2Activity.class));
    }

    private void trans(){
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,TransZFBActivity.class));
    }

    private void testIntEvaluator() {
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,IntEvaluatorActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

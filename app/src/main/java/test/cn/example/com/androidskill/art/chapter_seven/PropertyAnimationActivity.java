package test.cn.example.com.androidskill.art.chapter_seven;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;

/**
 * 属性动画Interpolator演示
 */
public class PropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        initView();
    }

    private void initView() {
        Button base = (Button) findViewById(R.id.base);
        base.setOnClickListener(this);
        Button typeEvaluator = (Button) findViewById(R.id.typeEvaluator);
        typeEvaluator.setOnClickListener(this);
        Button interpolator = (Button) findViewById(R.id.interpolator);
        interpolator.setOnClickListener(this);
        Button viewPropertyAnimator = (Button) findViewById(R.id.viewPropertyAnimator);
        viewPropertyAnimator.setOnClickListener(this);
        Button layoutTransition = (Button) findViewById(R.id.layoutTransition);
        layoutTransition.setOnClickListener(this);
        Button animateLayoutChanges = (Button) findViewById(R.id.animateLayoutChanges);
        animateLayoutChanges.setOnClickListener(this);
        Button exercise = (Button) findViewById(R.id.exercise);
        exercise.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationBaseActivity.class));
                break;
            case R.id.typeEvaluator:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationTypeEvaluatorActivity.class));
                break;
            case R.id.interpolator:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationInterpolatorActivity.class));
                break;
            case R.id.viewPropertyAnimator:
                startActivity(new Intent(PropertyAnimationActivity.this,ViewPropertyAnimatorActivity.class));
                break;
            case R.id.layoutTransition:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationLayoutTransitionActivity.class));
                break;
            case R.id.animateLayoutChanges:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationLayoutChangesActivity.class));
                break;
            case R.id.exercise:
                startActivity(new Intent(PropertyAnimationActivity.this,PropertyAnimationExerciseActivity.class));
                break;
        }
    }
}

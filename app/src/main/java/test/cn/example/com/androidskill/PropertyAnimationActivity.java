package test.cn.example.com.androidskill;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * 属性动画演示
 */
public class PropertyAnimationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        initView();
    }

    private void initView() {
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
}

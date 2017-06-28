package test.cn.example.com.androidskill;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import test.cn.example.com.androidskill.view.ViewWrapper;

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

        //记得在animation_scale文件中给scal标签添加如下属性否则，效果不出现
        // android:fromYScale="1.0"
        //android:toYScale="1.0"
        Button scale = (Button)findViewById(R.id.scale);
        Animation scale_animation = AnimationUtils.loadAnimation(PropertyAnimationActivity.this,R.anim.animation_scale);
        scale.startAnimation(scale_animation);

        Button scale2 = (Button)findViewById(R.id.scale2);
        ViewWrapper wrapper = new ViewWrapper(scale2);
        ObjectAnimator.ofInt(wrapper,"width",500).setDuration(5000).start();

    }
}

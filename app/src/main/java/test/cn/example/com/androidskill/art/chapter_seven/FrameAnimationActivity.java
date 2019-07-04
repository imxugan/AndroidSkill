package test.cn.example.com.androidskill.art.chapter_seven;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;

/**
 * 帧动画演示
 */
public class FrameAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        initView();
    }

    private void initView() {
        TextView iv_frame_animation = (TextView) findViewById(R.id.iv_frame_animation);
        iv_frame_animation.setBackgroundResource(R.drawable.frame_animation_test);
        AnimationDrawable animation = (AnimationDrawable) iv_frame_animation.getBackground();
        animation.start();

        AnimationDrawable animationDrawable = createAnimationDrawableByCode();
        ImageView iv = findViewById(R.id.iv);
        iv.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        ImageView iv_2 = findViewById(R.id.iv_2);
        AnimationDrawable drawable = (AnimationDrawable) iv_2.getDrawable();
//        drawable.start();//如果仅仅是给view设置了AnimationDrawable，但是不执行start方法，动画也不会执行


        //同一个animationDrawable，设置到多个不同的view上时，只有最有一个view才执行动画效果
        AnimationDrawable animationDrawable2 = createAnimationDrawableByCode();
        ImageView iv_3 = findViewById(R.id.iv_3);
        iv_3.setImageDrawable(animationDrawable2);
        TextView tv_frame_animation_2 = findViewById(R.id.tv_frame_animation_2);
        tv_frame_animation_2.setBackgroundDrawable(animationDrawable2);
        animationDrawable2.start();

    }

    public AnimationDrawable  createAnimationDrawableByCode(){
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.color.colorPrimaryDark),1000);
        animationDrawable.addFrame(getResources().getDrawable(R.color.red),1000);
        animationDrawable.addFrame(getResources().getDrawable(R.color.c_00c8aa),1000);
        animationDrawable.addFrame(getResources().getDrawable(R.color.accent),1000);
        animationDrawable.addFrame(getResources().getDrawable(R.color.c_ddff90),500);
        animationDrawable.setOneShot(false);
        return animationDrawable;
    }
}

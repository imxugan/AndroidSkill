package test.cn.example.com.androidskill.art.chapter_seven;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }
}

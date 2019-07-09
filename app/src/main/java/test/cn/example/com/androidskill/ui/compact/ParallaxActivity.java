package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/8.
 */

public class ParallaxActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        ParallaxContainer parallaxContainer = findViewById(R.id.parallaxContainer);
        parallaxContainer.setUp( R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login);
        ImageView iv_man = findViewById(R.id.iv_man);
        parallaxContainer.setIv_Man(iv_man);
    }
}

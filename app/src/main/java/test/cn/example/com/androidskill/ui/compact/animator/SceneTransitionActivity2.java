package test.cn.example.com.androidskill.ui.compact.animator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/5.
 */

public class SceneTransitionActivity2 extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition2);
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide();
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explode();
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade();
            }
        });
    }

    private void fade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneTransitionActivity2.this);
            Intent intent_fade = new Intent(SceneTransitionActivity2.this,SecondActivity2.class);
            startActivity(intent_fade,optionsCompat.toBundle());
        }
    }

    private void explode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
            getWindow().setExitTransition(explode);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneTransitionActivity2.this);
            Intent intent_explode = new Intent(SceneTransitionActivity2.this, SecondActivity2.class);
            startActivity(intent_explode,activityOptionsCompat.toBundle());
        }
    }

    private void slide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneTransitionActivity2.this);
            Intent intent = new Intent(SceneTransitionActivity2.this,SecondActivity2.class);
            startActivity(intent,compat.toBundle());
        }
    }
}

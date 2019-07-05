package test.cn.example.com.androidskill.ui.compact.animator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/5.
 */

public class SceneTransitionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);
        final ImageView iv = findViewById(R.id.iv);
        final Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单个元素共享的转场跳转
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SceneTransitionActivity.this,iv,"iv_trans");
                Intent intent = new Intent(SceneTransitionActivity.this,SecondActvity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent,optionsCompat.toBundle());
                }
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //多个元素共享的转场跳转
                Pair<View,String> pairA = new Pair<View, String>(iv,"iv_trans");
                Pair<View,String> pairB = new Pair<View,String>(btn,"btn_trans");
                ActivityOptionsCompat optionsCompat2 = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SceneTransitionActivity.this,pairA,pairB);
                Intent intent2 = new Intent(SceneTransitionActivity.this,SecondActvity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent2,optionsCompat2.toBundle());
                }
            }
        });
    }
}

package test.cn.example.com.androidskill.ui.compact;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/28.
 */

public class CustomProgerssBarActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomProgerssBar3 cpb1;
    private CustomProgerssBar3 cpb2;
    private CustomProgerssBar3 cpb3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        cpb1 = findViewById(R.id.cpb1);
        cpb1.setOnClickListener(this);
        cpb2 = findViewById(R.id.cpb2);
        cpb2.setOnClickListener(this);
        cpb3 = findViewById(R.id.cpb3);
        cpb3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator objectAnimator = null;
        switch (v.getId()){
            case R.id.cpb1:
                cpb1.setProgress(0);
                objectAnimator = ObjectAnimator.ofInt(v, "progress",80);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                break;
            case R.id.cpb2:
                cpb2.setProgress(0);
                objectAnimator = ObjectAnimator.ofInt(v, "progress",60);
                objectAnimator.setDuration(800);
                objectAnimator.start();
                break;
            case R.id.cpb3:
                cpb3.setProgress(0);
                objectAnimator = ObjectAnimator.ofInt(v, "progress",70);
                objectAnimator.setDuration(1500);
                objectAnimator.start();
                break;
        }
    }
}

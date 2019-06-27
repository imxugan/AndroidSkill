package test.cn.example.com.androidskill.ui.compact;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/27.
 */

public class FloatingActionButtonActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private boolean reverse = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatactionbutton);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                rotate();
                break;
        }
    }

    private void rotate() {
        float degree = reverse?-180:180;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab,"rotation",0.0f,degree);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        reverse = !reverse;
    }
}

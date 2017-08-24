package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 属性动画练习
 * Created by xgxg on 2017/8/23.
 */
public class PropertyAnimationExerciseActivity extends AppCompatActivity implements View.OnClickListener {
    private Button testIntEvaluator;
    private int tempHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation_exercise);
        initView();
    }

    private void initView() {
        testIntEvaluator = (Button) findViewById(R.id.testIntEvaluator);
        testIntEvaluator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testIntEvaluator:
                testIntEvaluator();
                break;
        }
    }

    private void testIntEvaluator() {
        startActivity(new Intent(PropertyAnimationExerciseActivity.this,IntEvaluatorActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        testIntEvaluator.post(new Runnable() {
            @Override
            public void run() {
                tempHeight = testIntEvaluator.getMeasuredHeight();
            }
        });
    }
}

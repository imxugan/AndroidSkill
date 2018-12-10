package test.cn.example.com.androidskill.view.defineView.hencoder;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_five.HenCoderPracticeDrawFiveActivity;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_four.HenCoderPracticeDrawFourActivity;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.HenCoderPracticeDrawOneActivity;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_three.HenCoderPracticeDrawThreeActivity;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_two.HenCoderPracticeDrawTwoActivity;

/**
 * Created by xugan on 2018/11/15.
 */

public class HenCoderPracticeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hencoder_practice);
        initView();
    }

    private void initView() {
        findViewById(R.id.hencoder_base).setOnClickListener(this);
        findViewById(R.id.hencoder_paint).setOnClickListener(this);
        findViewById(R.id.hencoder_text).setOnClickListener(this);
        findViewById(R.id.hencoder_canvas_assistant).setOnClickListener(this);
        findViewById(R.id.hencoder_view_draw_sequence).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hencoder_base:
                startActivity(new Intent(HenCoderPracticeActivity.this, HenCoderPracticeDrawOneActivity.class));
                break;
            case R.id.hencoder_paint:
                startActivity(new Intent(HenCoderPracticeActivity.this, HenCoderPracticeDrawTwoActivity.class));
                break;
            case R.id.hencoder_text:
                startActivity(new Intent(HenCoderPracticeActivity.this, HenCoderPracticeDrawThreeActivity.class));
                break;
            case R.id.hencoder_canvas_assistant:
                startActivity(new Intent(HenCoderPracticeActivity.this, HenCoderPracticeDrawFourActivity.class));
                break;
            case R.id.hencoder_view_draw_sequence:
                startActivity(new Intent(HenCoderPracticeActivity.this, HenCoderPracticeDrawFiveActivity.class));
                break;
        }
    }
}

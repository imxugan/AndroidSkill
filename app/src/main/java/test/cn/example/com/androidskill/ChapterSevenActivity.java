package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 动画
 */
public class ChapterSevenActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_seven);
        initView();
    }

    private void initView() {
        Button view_animation = (Button)findViewById(R.id.view_animation);
        view_animation.setOnClickListener(this);
        Button frame_animation = (Button)findViewById(R.id.frame_animation);
        frame_animation.setOnClickListener(this);
        Button property_animation = (Button)findViewById(R.id.property_animation);
        property_animation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_animation:
                startActivity(new Intent(ChapterSevenActivity.this,ViewAnimationActivity.class));
                break;
            case R.id.frame_animation:
                startActivity(new Intent(ChapterSevenActivity.this,FrameAnimationActivity.class));
                break;
            case R.id.property_animation:
                startActivity(new Intent(ChapterSevenActivity.this,ServiceWorkActivity.class));
                break;
            default:
                break;
        }
    }
}

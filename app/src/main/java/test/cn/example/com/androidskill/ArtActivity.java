package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xugan on 2019/7/3.
 */

public class ArtActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        TextView charapter1 = (TextView) findViewById(R.id.charapter1);
        charapter1.setOnClickListener(this);
        TextView charapter2 = (TextView) findViewById(R.id.charapter2);
        charapter2.setOnClickListener(this);
        TextView charapter4 = (TextView) findViewById(R.id.charapter4);
        charapter4.setOnClickListener(this);
        TextView charapter7 = (TextView) findViewById(R.id.charapter7);
        charapter7.setOnClickListener(this);
        TextView charapter9 = (TextView) findViewById(R.id.charapter9);
        charapter9.setOnClickListener(this);
        TextView charapter10 = (TextView) findViewById(R.id.charapter10);
        charapter10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.charapter1:
                myStartActivity(ChapterOneActivity.class,false);
                break;
            case R.id.charapter2:
                myStartActivity(ChapterTwoActivity.class,false);
                break;
            case R.id.charapter4:
                myStartActivity(ChapterFourActivity.class,false);
                break;
            case R.id.charapter7:
                myStartActivity(ChapterSevenActivity.class,false);
                break;
            case R.id.charapter9:
                myStartActivity(ChapterNineActivity.class,false);
                break;
            case R.id.charapter10:
                myStartActivity(HandlerActivity.class,false);
                break;
        }
    }

    private void myStartActivity(Class clazz,boolean isFinish){
        Intent intent = new Intent(ArtActivity.this, clazz);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }
}

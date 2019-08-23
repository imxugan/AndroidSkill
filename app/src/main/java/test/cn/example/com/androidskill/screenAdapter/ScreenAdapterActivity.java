package test.cn.example.com.androidskill.screenAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;

public class ScreenAdapterActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                myStartActivity(ImageAdapterActivity.class);
                break;
            case R.id.btn2:
                myStartActivity(ImageAdapterActivity2.class);
                break;
        }
    }

    private void myStartActivity(Class<?> activityClass) {
        startActivity(new Intent(ScreenAdapterActivity.this,activityClass));
    }
}

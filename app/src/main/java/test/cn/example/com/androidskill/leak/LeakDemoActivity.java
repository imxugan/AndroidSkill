package test.cn.example.com.androidskill.leak;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.HandlerActivity;
import test.cn.example.com.androidskill.R;

public class LeakDemoActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_demo);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(LeakDemoActivity.this,InnerClassLeakActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(LeakDemoActivity.this,LeakTestActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, ThreadAboutLeakActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this, HandlerLeakActivity.class));
                break;
        }
    }
}

package test.cn.example.com.androidskill.kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.kotlin.chapter1.KotlinDemo1;
import test.cn.example.com.androidskill.kotlin.chapter1.KotlinDemo2;
import test.cn.example.com.androidskill.kotlin.chapter1.KotlinDemo3;
import test.cn.example.com.androidskill.kotlin.chapter1.KotlinDemo4;

/**
 * Created by xugan on 2019/7/8.
 */

public class KotlinActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                startActivity(new Intent(KotlinActivity.this, KotlinDemo1.class));
                break;
            case R.id.btn1:
                startActivity(new Intent(KotlinActivity.this, KotlinDemo2.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(KotlinActivity.this, KotlinDemo3.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(KotlinActivity.this, KotlinDemo4.class));
                break;
        }
    }
}

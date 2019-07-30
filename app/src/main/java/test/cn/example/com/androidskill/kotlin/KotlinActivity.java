package test.cn.example.com.androidskill.kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.kotlin.chapter1.KotlinChapter1;
import test.cn.example.com.androidskill.kotlin.chapter2.KotlinChapter2;
import test.cn.example.com.androidskill.kotlin.chapter3.KotlinChapter3;
import test.cn.example.com.androidskill.kotlin.chapter4.KotlinChapter4;
import test.cn.example.com.androidskill.kotlin.chapter6.KotlinChapter6;

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
        findViewById(R.id.btn5).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                startActivity(new Intent(KotlinActivity.this, KotlinChapter1.class));
                break;
            case R.id.btn1:
                startActivity(new Intent(KotlinActivity.this, KotlinChapter2.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(KotlinActivity.this, KotlinChapter3.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(KotlinActivity.this, KotlinChapter4.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(KotlinActivity.this, KotlinChapter6.class));
                break;
        }
    }
}

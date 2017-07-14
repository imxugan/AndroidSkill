package test.cn.example.com.androidskill.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;

/**
 * Created by xgxg on 2017/7/14.
 * 设计模式
 */

public class DesignPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern);
        initView();
    }

    private void initView() {
        Button observer = (Button) findViewById(R.id.observer);
        observer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.observer:
                startActivity(new Intent(DesignPatternActivity.this,ObserverPatternActivity.class));
                break;
            default:
                break;

        }
    }
}

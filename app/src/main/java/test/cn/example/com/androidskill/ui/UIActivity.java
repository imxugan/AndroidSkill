package test.cn.example.com.androidskill.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.ui.compact.ListPopupWindowActivity;
import test.cn.example.com.androidskill.ui.compact.PopMenuActivity;
import test.cn.example.com.androidskill.ui.compact.SwipeRefreshLayoutActivity;

/**
 * Created by xugan on 2019/6/11.
 */

public class UIActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(UIActivity.this,SwipeRefreshLayoutActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(UIActivity.this,ListPopupWindowActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(UIActivity.this,PopMenuActivity.class));
                break;
        }
    }
}

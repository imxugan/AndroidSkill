package test.cn.example.com.androidskill.art.chapter_one;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

/**
 * activity的启动模式之singleTask
 */
public class SingleTaskActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
    }
}

package test.cn.example.com.androidskill.leak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;

public class StaticViewLeakActivity extends AppCompatActivity {

    private static View tv; //静态变量textView，持有StaticViewLeakActivity的引用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_view_leak);
        tv = findViewById(R.id.tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv = null;
    }
}

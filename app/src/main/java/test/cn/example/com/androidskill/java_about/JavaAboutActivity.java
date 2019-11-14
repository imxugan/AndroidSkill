package test.cn.example.com.androidskill.java_about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/3.
 */

public class JavaAboutActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_about);
        TextView threadPool = (TextView) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
        TextView callBack = (TextView) findViewById(R.id.callBack);
        callBack.setOnClickListener(this);
        TextView classLoader = (TextView) findViewById(R.id.classLoader);
        classLoader.setOnClickListener(this);
        findViewById(R.id.propertyChangeSupport).setOnClickListener(this);
        TextView annotation = (TextView) findViewById(R.id.annotation);
        annotation.setOnClickListener(this);
        findViewById(R.id.referenceQueue).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classLoader:
                myStartActivity(ClassLoaderTestActivity.class,false);
                break;
            case R.id.threadPool:
                myStartActivity(ThreadPoolActivity.class,false);
                break;
            case R.id.callBack:
                myStartActivity(CallBackActivity.class,false);
                break;
            case R.id.propertyChangeSupport:
                myStartActivity(PropertyChangeSupportActivity.class,false);
                break;
            case R.id.annotation:
                myStartActivity(AnnotationActivity.class,false);
                break;
            case R.id.referenceQueue:
                myStartActivity(ReferenceQueueActivity.class,false);
                break;
        }
    }

    private void myStartActivity(Class clazz,boolean isFinish){
        Intent intent = new Intent(JavaAboutActivity.this, clazz);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }
}

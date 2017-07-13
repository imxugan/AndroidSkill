package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Method;

import test.cn.example.com.androidskill.annotation.AnnotationTest;
import test.cn.example.com.androidskill.annotation.Todo;
import test.cn.example.com.util.LogUtil;

/**
 * 注解使用演示
 */
public class AnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_annotation);
        Class businessLogicClass = AnnotationTest.class;
        for(Method method : businessLogicClass.getMethods()) {
            Todo todoAnnotation = (Todo)method.getAnnotation(Todo.class);
            if(todoAnnotation != null) {
                LogUtil.i(" Method Name : " + method.getName());
                LogUtil.i(" Author : " + todoAnnotation.author());
                LogUtil.i(" Priority : " + todoAnnotation.priority());
                LogUtil.i(" Status : " + todoAnnotation.stauts());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

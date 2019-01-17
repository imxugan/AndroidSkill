package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import test.cn.example.com.androidskill.annotation.AnnotationTest;
import test.cn.example.com.androidskill.annotation.MyBindViewAnnotation;
import test.cn.example.com.androidskill.annotation.TestMethodAnnotation;
import test.cn.example.com.androidskill.annotation.Todo;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * 注解使用演示
 */
public class AnnotationActivity extends AppCompatActivity implements View.OnClickListener {

    @MyBindViewAnnotation(viewId = R.id.btn_1)
    private Button btn_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        findViewById(R.id.btn_start).setOnClickListener(this);
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
        try {
//            getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
//            getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段
            Field field_btn_1 = AnnotationActivity.class.getDeclaredField("btn_1");
            field_btn_1.setAccessible(true);
            MyBindViewAnnotation myBindViewAnnotation = field_btn_1.getAnnotation(MyBindViewAnnotation.class);
            if(btn_1 == null){
                LogUtil.i("id   "+myBindViewAnnotation.viewId());
                btn_1 = (Button) findViewById(myBindViewAnnotation.viewId());
                btn_1.setOnClickListener(this);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
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

    @TestMethodAnnotation(id = 1)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                try {
                    Method onClick = AnnotationActivity.class.getMethod("onClick", View.class);
                    TestMethodAnnotation annotation = onClick.getAnnotation(TestMethodAnnotation.class);
                    ToastUtils.shortToast(AnnotationActivity.this,"获取到注解到onClick方法上的注解TestMethodAnnotation中的属性id的值是  "+annotation.id());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_1:
                ToastUtils.shortToast(AnnotationActivity.this,"通过注解获取到btn_1这个button控件的id");
                break;
        }
    }
}

package test.cn.example.com.androidskill.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.lang.reflect.Field;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.designpattern.ProxyPatternActivity;

public class HookActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        replaceActivityInstrumentation(this);
        replaceActivityThreadInstrumentation();

    }

    private void replaceActivityThreadInstrumentation() {
        try {
            Class<?> aClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = aClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);
            Field mInstrumentationField = aClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentationField.get(activityThread);
            InstrumentationProxy instrumentationProxy = new InstrumentationProxy(instrumentation);
            mInstrumentationField.set(activityThread,instrumentationProxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void replaceActivityInstrumentation(Activity activity) {
        try {
            Field mInstrumentationField = Activity.class.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentationField.get(activity);
            InstrumentationProxy instrumentationProxy = new InstrumentationProxy(instrumentation);
            mInstrumentationField.set(activity,instrumentationProxy);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                startActivity(new Intent(HookActivity.this, ProxyPatternActivity.class));
                break;
            case R.id.tv_2:
                Intent intent = new Intent(HookActivity.this, ProxyPatternActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
                break;
        }
    }
}

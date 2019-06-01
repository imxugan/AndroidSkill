package test.cn.example.com.androidskill.optimize.handler_thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/1.
 */

public class IntentServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        startService(new Intent(IntentServiceActivity.this,MyIntentService.class));
    }
}

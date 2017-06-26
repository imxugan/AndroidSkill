package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.cn.example.com.androidskill.callback.CallBackActivity;

import static test.cn.example.com.androidskill.R.id.charapter9;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setOnClickListener(this);
        TextView threadPool = (TextView) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
        TextView callBack = (TextView) findViewById(R.id.callBack);
        callBack.setOnClickListener(this);
        TextView charapter1 = (TextView) findViewById(R.id.charapter1);
        charapter1.setOnClickListener(this);
        TextView charapter7 = (TextView) findViewById(R.id.charapter7);
        charapter7.setOnClickListener(this);
        TextView charapter9 = (TextView) findViewById(R.id.charapter9);
        charapter9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.debug:
                Intent intent_debug = new Intent(MainActivity.this, DebugActivity.class);
                myStartActivity(intent_debug);
                break;
            case R.id.threadPool:
                Intent intent_threadPool = new Intent(MainActivity.this, ThreadPoolActivity.class);
                myStartActivity(intent_threadPool);
                break;
            case R.id.callBack:
                Intent intent_callBack = new Intent(MainActivity.this, CallBackActivity.class);
                myStartActivity(intent_callBack);
                break;
            case R.id.charapter1:
                Intent intent = new Intent(MainActivity.this, ChapterOneActivity.class);
                myStartActivity(intent);
            case R.id.charapter7:
                Intent intent_7 = new Intent(MainActivity.this, ChapterNineActivity.class);
                myStartActivity(intent_7);
                break;
            case R.id.charapter9:
                Intent intent_9 = new Intent(MainActivity.this, ChapterNineActivity.class);
                myStartActivity(intent_9);
                break;
            default:
                break;
        }
    }

    private void myStartActivity(Intent i){
        startActivity(i);
    }
}

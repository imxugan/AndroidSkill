package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.cn.example.com.androidskill.callback.CallBackActivity;
import test.cn.example.com.androidskill.designpattern.DesignPatternActivity;
import test.cn.example.com.androidskill.login.view.LoginActivity;
import test.cn.example.com.androidskill.rxjavaTest.RxJavaTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler.getInstance().init(this);
        initView();
    }

    private void initView() {
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setOnClickListener(this);
        TextView threadPool = (TextView) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
        TextView callBack = (TextView) findViewById(R.id.callBack);
        callBack.setOnClickListener(this);
        TextView rxJava = (TextView) findViewById(R.id.rxJava);
        rxJava.setOnClickListener(this);
        TextView design_pattern = (TextView) findViewById(R.id.design_pattern);
        design_pattern.setOnClickListener(this);
        TextView mvp = (TextView) findViewById(R.id.mvp);
        mvp.setOnClickListener(this);
        TextView annotation = (TextView) findViewById(R.id.annotation);
        annotation.setOnClickListener(this);
        TextView httpUrlConnection = (TextView) findViewById(R.id.httpUrlConnection);
        httpUrlConnection.setOnClickListener(this);
        TextView charapter1 = (TextView) findViewById(R.id.charapter1);
        charapter1.setOnClickListener(this);
        TextView charapter2 = (TextView) findViewById(R.id.charapter2);
        charapter2.setOnClickListener(this);
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
            case R.id.rxJava:
                Intent intent_rxJava = new Intent(MainActivity.this, RxJavaTestActivity.class);
                myStartActivity(intent_rxJava);
                break;
            case R.id.design_pattern:
                Intent intent_design_pattern = new Intent(MainActivity.this, DesignPatternActivity.class);
                myStartActivity(intent_design_pattern);
                break;
            case R.id.mvp:
                Intent intent_mvp = new Intent(MainActivity.this, LoginActivity.class);
                myStartActivity(intent_mvp);
                break;
            case R.id.annotation:
                Intent intent_annotation = new Intent(MainActivity.this, AnnotationActivity.class);
                myStartActivity(intent_annotation);
                break;
            case R.id.httpUrlConnection:
                Intent intent_http = new Intent(MainActivity.this, HttpTestActivity.class);
                myStartActivity(intent_http);
                break;
            case R.id.charapter1:
                Intent intent = new Intent(MainActivity.this, ChapterOneActivity.class);
                myStartActivity(intent);
                break;
            case R.id.charapter2:
                Intent intent_2 = new Intent(MainActivity.this,ChapterTwoActivity.class);
                startActivity(intent_2);
                break;
            case R.id.charapter7:
                Intent intent_7 = new Intent(MainActivity.this, ChapterSevenActivity.class);
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

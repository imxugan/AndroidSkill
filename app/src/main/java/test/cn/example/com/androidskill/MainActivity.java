package test.cn.example.com.androidskill;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import test.cn.example.com.androidskill.aop.AspectJTestActivity;
import test.cn.example.com.androidskill.callback.CallBackActivity;
import test.cn.example.com.androidskill.changeSkin.ChangeSkinActivity;
import test.cn.example.com.androidskill.designpattern.DesignPatternActivity;
import test.cn.example.com.androidskill.livedatabus.LiveDataBusTestActivity;
import test.cn.example.com.androidskill.login.view.LoginActivity;
import test.cn.example.com.androidskill.my_eventbus.MyEventBusTestActivity;
import test.cn.example.com.androidskill.rxjavaTest.RxJavaTestActivity;
import test.cn.example.com.androidskill.sqlite.SqliteActivity;
import test.cn.example.com.androidskill.test.TestEditDetailsActivity2;
import test.cn.example.com.androidskill.websocket.WebsocketActivity;
import test.cn.example.com.util.LogUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PERMISSIONS_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initView();
    }

    private void initView() {
        TextView livedatabus = (TextView) findViewById(R.id.livedatabus);
        livedatabus.setOnClickListener(this);
        TextView aspectj = (TextView) findViewById(R.id.aspectj);
        aspectj.setOnClickListener(this);
        TextView simple_eventbus = (TextView) findViewById(R.id.simple_eventbus);
        simple_eventbus.setOnClickListener(this);
        TextView change_skin = (TextView) findViewById(R.id.change_skin);
        change_skin.setOnClickListener(this);
        TextView test = (TextView) findViewById(R.id.test);
        test.setOnClickListener(this);
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setOnClickListener(this);
        TextView classLoader = (TextView) findViewById(R.id.classLoader);
        classLoader.setOnClickListener(this);
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
        TextView charapter4 = (TextView) findViewById(R.id.charapter4);
        charapter4.setOnClickListener(this);
        TextView charapter7 = (TextView) findViewById(R.id.charapter7);
        charapter7.setOnClickListener(this);
        TextView charapter9 = (TextView) findViewById(R.id.charapter9);
        charapter9.setOnClickListener(this);
        TextView charapter10 = (TextView) findViewById(R.id.charapter10);
        charapter10.setOnClickListener(this);
        TextView toolbar = (TextView) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(this);
        TextView viewstub = (TextView) findViewById(R.id.viewstub);
        viewstub.setOnClickListener(this);
        TextView retrofit = (TextView) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
        TextView dispatch_event = (TextView) findViewById(R.id.dispatch_event);
        dispatch_event.setOnClickListener(this);
        findViewById(R.id.greenDao).setOnClickListener(this);
        findViewById(R.id.propertyChangeSupport).setOnClickListener(this);
        findViewById(R.id.websokcet).setOnClickListener(this);
        findViewById(R.id.sqlite).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.livedatabus:
                myStartActivity(LiveDataBusTestActivity.class,false);
                break;
            case R.id.aspectj:
                myStartActivity(AspectJTestActivity.class,false);
                break;
            case R.id.simple_eventbus:
                myStartActivity(MyEventBusTestActivity.class,false);
                break;
            case R.id.change_skin:
                myStartActivity(ChangeSkinActivity.class,false);
                break;
            case R.id.test:
                myStartActivity(TestEditDetailsActivity2.class,false);
                break;
            case R.id.debug:
                myStartActivity(DebugActivity.class,false);
                break;
            case R.id.classLoader:
                myStartActivity(ClassLoaderTestActivity.class,false);
                break;
            case R.id.threadPool:
                myStartActivity(ThreadPoolActivity.class,false);
                break;
            case R.id.callBack:
                myStartActivity(CallBackActivity.class,false);
                break;
            case R.id.rxJava:
                myStartActivity(RxJavaTestActivity.class,false);
                break;
            case R.id.design_pattern:
                myStartActivity(DesignPatternActivity.class,false);
                break;
            case R.id.mvp:
                myStartActivity(LoginActivity.class,false);
                break;
            case R.id.annotation:
                myStartActivity(AnnotationActivity.class,false);
                break;
            case R.id.httpUrlConnection:
                myStartActivity(HttpTestActivity.class,false);
                break;
            case R.id.charapter1:
                myStartActivity(ChapterOneActivity.class,false);
                break;
            case R.id.charapter2:
                myStartActivity(ChapterTwoActivity.class,false);
                break;
            case R.id.charapter4:
                myStartActivity(ChapterFourActivity.class,false);
                break;
            case R.id.charapter7:
                myStartActivity(ChapterSevenActivity.class,false);
                break;
            case R.id.charapter9:
                Intent intent_9 = new Intent(MainActivity.this, ChapterNineActivity.class);
                myStartActivity(ChapterNineActivity.class,false);
                break;
            case R.id.charapter10:
                myStartActivity(HandlerActivity.class,false);
                break;
            case R.id.toolbar:
                myStartActivity(ToolBarTestActivity.class,false);
                break;
            case R.id.viewstub:
                myStartActivity(ViewStubActivity.class,false);
                break;
            case R.id.retrofit:
                myStartActivity(RetrofitActivity.class,false);
                break;
            case R.id.dispatch_event:
                myStartActivity(DispatchEventActivity.class,false);
                break;
            case R.id.greenDao:
                myStartActivity(GreenDaoActivity.class,false);
                break;
            case R.id.propertyChangeSupport:
                myStartActivity(PropertyChangeSupportActivity.class,false);
                break;
            case R.id.websokcet:
                myStartActivity(WebsocketActivity.class,false);
                break;
            case R.id.sqlite:
                myStartActivity(SqliteActivity.class,false);
                break;
            default:
                break;
        }
    }

    private void myStartActivity(Class clazz,boolean isFinish){
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            LogUtils.i("granted permission:" + grantResults);
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readExternalPermission = (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

            ArrayList<String> permissions = new ArrayList<String>();

            if (readExternalPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (permissions.size() > 0) {
                String[] array = new String[permissions.size()];
                permissions.toArray(array);
                this.requestPermissions(array, PERMISSIONS_REQUEST);
            }
        }
    }

}

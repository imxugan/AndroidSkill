package test.cn.example.com.androidskill;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

import test.cn.example.com.androidskill.aop.AspectJTestActivity;
import test.cn.example.com.androidskill.art.ArtActivity;
import test.cn.example.com.androidskill.changeSkin.ChangeSkinActivity;
import test.cn.example.com.androidskill.designpattern.DesignPatternActivity;
import test.cn.example.com.androidskill.hook.HookActivity;
import test.cn.example.com.androidskill.java_about.JavaAboutActivity;
import test.cn.example.com.androidskill.kotlin.KotlinActivity;
import test.cn.example.com.androidskill.leak.LeakDemoActivity;
import test.cn.example.com.androidskill.livedatabus.LiveDataBusTestActivity;
import test.cn.example.com.androidskill.login.view.LoginActivity;
import test.cn.example.com.androidskill.my_eventbus.MyEventBusTestActivity;
import test.cn.example.com.androidskill.myvolley.MyVolleyTestActivity;
import test.cn.example.com.androidskill.optimize.OptimizeActivity;
import test.cn.example.com.androidskill.rxjava2Test.RxJava2ActivityTest;
import test.cn.example.com.androidskill.rxjavaTest.RxJavaTestActivity;
import test.cn.example.com.androidskill.screenAdapter.ScreenAdapterActivity;
import test.cn.example.com.androidskill.sqlite.SqliteActivity;
import test.cn.example.com.androidskill.ui.UIActivity;
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
        findViewById(R.id.hook).setOnClickListener(this);
        findViewById(R.id.arouter).setOnClickListener(this);
        findViewById(R.id.ui).setOnClickListener(this);
        findViewById(R.id.tv_leak).setOnClickListener(this);
        TextView tv_volley = (TextView) findViewById(R.id.tv_volley);
        tv_volley.setOnClickListener(this);
        TextView rxjava2 = (TextView) findViewById(R.id.rxjava2);
        rxjava2.setOnClickListener(this);
        TextView livedatabus = (TextView) findViewById(R.id.livedatabus);
        livedatabus.setOnClickListener(this);
        TextView aspectj = (TextView) findViewById(R.id.aspectj);
        aspectj.setOnClickListener(this);
        TextView simple_eventbus = (TextView) findViewById(R.id.simple_eventbus);
        simple_eventbus.setOnClickListener(this);
        TextView change_skin = (TextView) findViewById(R.id.change_skin);
        change_skin.setOnClickListener(this);
        TextView screenAdapter = (TextView) findViewById(R.id.screenAdapter);
        screenAdapter.setOnClickListener(this);
        TextView optimize = (TextView) findViewById(R.id.optimize);
        optimize.setOnClickListener(this);
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setOnClickListener(this);

        TextView rxJava = (TextView) findViewById(R.id.rxJava);
        rxJava.setOnClickListener(this);
        TextView design_pattern = (TextView) findViewById(R.id.design_pattern);
        design_pattern.setOnClickListener(this);
        TextView mvp = (TextView) findViewById(R.id.mvp);
        mvp.setOnClickListener(this);

        TextView httpUrlConnection = (TextView) findViewById(R.id.httpUrlConnection);
        httpUrlConnection.setOnClickListener(this);
        findViewById(R.id.art).setOnClickListener(this);
        findViewById(R.id.java_about).setOnClickListener(this);


        TextView viewstub = (TextView) findViewById(R.id.viewstub);
        viewstub.setOnClickListener(this);
        TextView retrofit = (TextView) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
        TextView dispatch_event = (TextView) findViewById(R.id.dispatch_event);
        dispatch_event.setOnClickListener(this);
        findViewById(R.id.greenDao).setOnClickListener(this);
        findViewById(R.id.websokcet).setOnClickListener(this);
        findViewById(R.id.sqlite).setOnClickListener(this);
        findViewById(R.id.kotlin).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hook:
                myStartActivity(HookActivity.class,false);
                break;
            case R.id.arouter:
                ARouter.getInstance().build("/test/activity").navigation();
                break;
             case R.id.kotlin:
                myStartActivity(KotlinActivity.class,false);
                break;
            case R.id.ui:
                myStartActivity(UIActivity.class,false);
                break;
            case R.id.tv_leak:
                myStartActivity(LeakDemoActivity.class,false);
                break;
            case R.id.tv_volley:
                myStartActivity(MyVolleyTestActivity.class,false);
                break;
            case R.id.rxjava2:
                myStartActivity(RxJava2ActivityTest.class,false);
                break;
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
            case R.id.screenAdapter:
                myStartActivity(ScreenAdapterActivity.class,false);
                break;
            case R.id.optimize:
                myStartActivity(OptimizeActivity.class,false);
                break;
            case R.id.debug:
                myStartActivity(DebugActivity.class,false);
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

            case R.id.httpUrlConnection:
                myStartActivity(HttpTestActivity.class,false);
                break;
            case R.id.art:
                myStartActivity(ArtActivity.class,false);
                break;
            case R.id.java_about:
                myStartActivity(JavaAboutActivity.class,false);
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

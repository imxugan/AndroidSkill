package test.cn.example.com.androidskill;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.xywy.im.WebSocketApi;

import test.cn.example.com.androidskill.callback.CallBackActivity;
import test.cn.example.com.androidskill.designpattern.DesignPatternActivity;
import test.cn.example.com.androidskill.login.view.LoginActivity;
import test.cn.example.com.androidskill.rxjavaTest.RxJavaTestActivity;
import test.cn.example.com.androidskill.sqlite.SqliteActivity;
import test.cn.example.com.androidskill.websocket.WebsocketActivity;
import test.cn.example.com.util.ToastUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        checkPermission();
        CrashHandler.getInstance().init(this);
        initView();
    }

    private void initView() {
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

    private void checkPermission(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ToastUtils.shortToast(this,"请开通相关权限，否则无法正常使用本应用!");
            }
        }else{
            ToastUtils.shortToast(this,"授权成功");
        }
    }
}

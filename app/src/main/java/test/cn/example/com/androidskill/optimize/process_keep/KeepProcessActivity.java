package test.cn.example.com.androidskill.optimize.process_keep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;


/**
 * Created by xugan on 2019/5/29.
 */

public class KeepProcessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_process);
        //在正在运行的服务或进程页面，如果将其中一个进程或服务停止，则很快，另外一个进程就会将被停止的服务启动，这样就
        //保证了服务所在的进程不被杀死
        startService(new Intent(this,LocalService.class));
        startService(new Intent(this,RemoteService.class));
        //增加JobSchedule的目的是，在app的应用信息中，如果点击 “强制停止”。也会在一段时间内再次启动进程
        startService(new Intent(this, JobHandleService.class));
    }
}

package test.cn.example.com.androidskill.hook.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import test.cn.example.com.androidskill.hook.receiver.PlugReceiver;
import test.cn.example.com.util.LogUtil;

public class PlugService extends Service {


    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("插件servcie onCreate");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.i("插件servcie执行定时任务");
            }
        };
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(runnable,1L,2L, TimeUnit.SECONDS);

        //为了演示插件BroadCastReceiver的接收，在启动插件Service时，动态的在插件Service中进行注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("test.plugreceiver");
        registerReceiver(new PlugReceiver(),intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("插件servcie onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("插件  onDestroy");
        scheduledExecutorService.shutdown();
    }
}
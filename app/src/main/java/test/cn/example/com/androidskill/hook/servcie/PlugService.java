package test.cn.example.com.androidskill.hook.servcie;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import test.cn.example.com.util.LogUtil;

public class PlugService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("插件servcie onCreate");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.i("执行定时任务");
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(runnable,1L,1L,TimeUnit.SECONDS);
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
}

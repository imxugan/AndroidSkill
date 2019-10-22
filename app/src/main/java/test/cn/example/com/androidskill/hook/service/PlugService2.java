package test.cn.example.com.androidskill.hook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class PlugService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("插件servcie222   onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("插件servcie222 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("插件onBind     "+intent);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i("插件onUnbind     "+intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("插件service222  onDestroy");
    }
}
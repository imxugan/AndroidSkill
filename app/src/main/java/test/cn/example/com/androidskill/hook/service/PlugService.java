package test.cn.example.com.androidskill.hook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class PlugService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("插件servcie onCreate");
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

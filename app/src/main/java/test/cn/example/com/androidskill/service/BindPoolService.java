package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;

import androidx.annotation.Nullable;

import test.cn.example.com.androidskill.art.chapter_two.BindPool;
import test.cn.example.com.util.LogUtil;


public class BindPoolService extends Service {

    private Binder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("onCreate "+"Process.myPid()=   "+ Process.myPid());
        mBinder = new BindPool.IBindPoolImpl();
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e("mBinder = "+mBinder);
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("onDestroy ");
    }
}

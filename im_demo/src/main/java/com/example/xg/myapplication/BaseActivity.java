package com.example.xg.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xywy.im.XywyIMService;

import java.util.List;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBaseCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    /**
     * 必须在此设置一个ContentView，除非它没有界面
     *
     * @param savedInstanceState
     */
    protected abstract void onBaseCreate(Bundle savedInstanceState);

    /**
     * 视图初始化
     * <p/>
     * 处理手势绑定、view和fragment的注入
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);



    @Override
    protected void onStop() {
        super.onStop();

        if (!isAppOnForeground()) {
            //app 进入后台,停止IMService,采用push机制接收离线消息
            XywyIMService.getInstance().enterBackground();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        XywyIMService.getInstance().enterForeground();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager =
                (ActivityManager) getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance
                    == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}

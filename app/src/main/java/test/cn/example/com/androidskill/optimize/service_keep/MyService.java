package test.cn.example.com.androidskill.optimize.service_keep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/28.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenListener screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenStateListener() {
            @Override
            public void onScreenOn() {
                //开屏,finish一个像素的activity
                KeepLiveActivityManager.getInstance(MyService.this).finishKeepLiveActivity();
            }

            @Override
            public void onScreenOff() {
                //锁屏,启动一个像素的activity
                KeepLiveActivityManager.getInstance(MyService.this).startKeepLiveActivity(MyService.this);
            }

            @Override
            public void onUserPresent() {

            }
        });
    }
}

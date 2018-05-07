package test.cn.example.com.androidskill;

import android.app.Application;

/**
 * Created by xugan on 2018/5/7.
 */

public class MyApplication extends Application {
    public MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
    }
}

package com.android.skill;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

public class PluginOneApplication extends Application {
    private static final String TAG = "MY_LOG";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"插件application的oncreate");
    }
}

package com.android.skill;

import android.app.Application;
import android.util.Log;

public class PluginOneApplication extends Application {
    private static final String TAG = "MY_LOG";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"插件application的oncreate");
    }
}

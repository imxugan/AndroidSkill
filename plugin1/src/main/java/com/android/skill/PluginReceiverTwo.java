package com.android.skill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PluginReceiverTwo extends BroadcastReceiver {
    private static final String TAG = "MY_LOG";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"插件recevier2   "+intent.getAction());
    }
}

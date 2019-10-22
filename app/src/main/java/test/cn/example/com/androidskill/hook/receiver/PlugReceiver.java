package test.cn.example.com.androidskill.hook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import test.cn.example.com.util.LogUtil;

public class PlugReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i("插件receiver  ");
    }
}

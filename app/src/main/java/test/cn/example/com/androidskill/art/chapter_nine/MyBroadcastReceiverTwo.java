package test.cn.example.com.androidskill.art.chapter_nine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import test.cn.example.com.util.LogUtil;

public class MyBroadcastReceiverTwo extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("静态注册  MyBroadcastReceiverTwo");
    }
}

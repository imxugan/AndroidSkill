package test.cn.example.com.androidskill.optimize.service_keep;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Created by xugan on 2019/5/28.
 */

public class ScreenListener {
    private Context mContext;
    private KeepLiveBroadCastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;
    public ScreenListener(Context context){
        mContext = context;
    }

    public void begin(ScreenStateListener screenStateListener){
        mScreenStateListener = screenStateListener;
        mScreenReceiver = new KeepLiveBroadCastReceiver(mScreenStateListener);
        registerListener();
        getScreenState();
    }

    /**
     * 获取屏幕的状态
     */
    private void getScreenState(){
        PowerManager pw = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if(pw.isScreenOn()){
            if(null != mScreenStateListener){
                mScreenStateListener.onScreenOn();
            }
        }else {
            if(null != mScreenStateListener){
                mScreenStateListener.onScreenOff();
            }
        }
    }

    public void unRegisterListener(){
        mContext.unregisterReceiver(mScreenReceiver);
    }

    /**
     * 启动广播接收者
     */
    private void registerListener(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);//android.intent.action.USER_PRESENT就是解锁时发出的intent
        mContext.registerReceiver(mScreenReceiver,intentFilter);
    }
}

package test.cn.example.com.androidskill.optimize.service_keep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/28.
 */

public class KeepLiveBroadCastReceiver extends BroadcastReceiver {
    private ScreenStateListener mScreenStateListener;
    public KeepLiveBroadCastReceiver(ScreenStateListener screenStateListenerer) {
        mScreenStateListener = screenStateListenerer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_ON)){
            //开屏
            if(null != mScreenStateListener){
                mScreenStateListener.onScreenOn();
            }
        }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            //熄屏
            if(null != mScreenStateListener){
                mScreenStateListener.onScreenOff();
            }
        }else if(action.equals(Intent.ACTION_USER_PRESENT)){
            //解锁
            if(null != mScreenStateListener){
                mScreenStateListener.onUserPresent();
            }
        }
    }
}

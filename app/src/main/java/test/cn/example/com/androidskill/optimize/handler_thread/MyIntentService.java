package test.cn.example.com.androidskill.optimize.handler_thread;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2019/6/1.
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtil.i(Thread.currentThread().getName()+"  _onHandleIntent");
        //这里都是在子线程中了，可以在这个方法中进行网络请求等耗时操作
    }
}

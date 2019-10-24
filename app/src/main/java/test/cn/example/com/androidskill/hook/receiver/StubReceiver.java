package test.cn.example.com.androidskill.hook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import test.cn.example.com.androidskill.hook.HookHelper;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class StubReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String newAction = intent.getAction();
        LogUtil.i("接收到静态广播      "+newAction);
        ToastUtils.shortToast(context,"接收到静态广播");

        String pluginAction = HookHelper.old2newActionsMap.get(newAction);
        context.sendBroadcast(new Intent(pluginAction));

    }
}

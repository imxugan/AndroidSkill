package test.cn.example.com.androidskill.hook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

import test.cn.example.com.androidskill.hook.HookHelper;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class StubReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String newAction = intent.getAction();
        LogUtil.i("接收到静态广播      "+newAction);
        ToastUtils.shortToast(context,"接收到静态广播");

        if(HookHelper.old2newActionsMap.size()==0){
            File apkFile_dir = context.getDir(HookHelper.PLUGIN_ODEX,Context.MODE_PRIVATE);
            String filePath = apkFile_dir.getAbsolutePath()+File.separator+"plugin1-debug.apk";
            File apkFile = new File(filePath);
            //将插件中的静态广播全部注册为动态广播
            HookHelper.registerPluginStaticReceivers(context,apkFile);
        }


        String pluginAction = HookHelper.old2newActionsMap.get(newAction);
        context.sendBroadcast(new Intent(pluginAction));

    }
}

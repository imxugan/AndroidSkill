package test.cn.example.com.androidskill.hook;

import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import test.cn.example.com.util.LogUtil;

public class IActivityManagerInvocationHandler implements InvocationHandler {
    private final Object mActivityManager;

    public IActivityManagerInvocationHandler(Object activityManager){
        this.mActivityManager = activityManager;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("startActivity")){
//            ActivityManagerService类中的startActivity方法的10个参数
//            startActivity(IApplicationThread caller, String callingPackage,
//                    Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode,
//            int startFlags, ProfilerInfo profilerInfo, Bundle bOptions)
            Intent intent = null;
            int index = -1;
            String packageName = "test.cn.example.com.androidskill";
            String plugClassName = packageName+".hook.PlugActivity";
            LogUtil.i("args.length      "+args.length);
            for (int i = 0; i < args.length; i++) {
//                LogUtil.i(args[i]+"");
                if(args[i] instanceof Intent){
                    Intent tempIntent = (Intent)args[i];
                    if(null !=tempIntent.getComponent() && plugClassName.equals(tempIntent.getComponent().getClassName())){
                        index = i;
                        break;
                    }
                }
            }
            if(-1 !=index){
                //实际要启动的intent
                intent = (Intent) args[index];
                //用占坑的BackUpActivity来通过AMS的检查
                Intent backupIntent = new Intent();

                //test.cn.example.com.androidskill.hook.BackUpActivity
                backupIntent.setClassName(packageName,packageName+".hook.BackUpActivity");
                backupIntent.putExtra(HookHelper.PLUG_INTENT,intent);
                args[index] = backupIntent;
                LogUtil.i("成功骗过了AMS");
            }
        }else if("startService".equals(method.getName())){
            //startService
            int index = -1;
            String packageName = "test.cn.example.com.androidskill";
            //test.cn.example.com.androidskill.hook.servcie.PlugService
            String plugServiceClassName = packageName+".hook.service.PlugService";
            String proxyServiceClassName = packageName+".hook.service.ProxyService";
            Intent proxyIntent = null;
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }

            if(-1 != index){
                Intent plugIntent = (Intent) args[index];
                if(null !=plugIntent.getComponent() && plugServiceClassName.equals(plugIntent.getComponent().getClassName())){
                    LogUtil.i(plugServiceClassName);
                    proxyIntent = new Intent();
                    proxyIntent.setClassName(packageName,proxyServiceClassName);
                    proxyIntent.putExtra(HookHelper.PLUG_INTENT,plugIntent);
                }
                //这里添加一个判断，防止类名写错时，导致args中的intent这个参数是null,导致崩溃
                args[index] = proxyIntent==null?plugIntent:proxyIntent;
            }
        }
        return method.invoke(mActivityManager,args);
    }
}

package test.cn.example.com.androidskill.hook;

import android.app.Service;
import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import test.cn.example.com.androidskill.hook.service.ProxyService;
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
        }else if("stopService".equals(method.getName())){
            int index = -1;
            for (int i=0;i<args.length;i++){
                if(args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }
            if(index != -1){
                Intent rawIntent = (Intent) args[index];
                return ProxyService.stopPlugService(rawIntent);
            }
        }else if("bindService".equals(method.getName())){
//            public int bindService(IApplicationThread caller, IBinder token, Intent service,
//                    String resolvedType, IServiceConnection connection, int flags, String callingPackage,
//            int userId) throws TransactionTooLargeException {
//
//            }

            int index = -1;
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }

            Object  serviceConnection =  args[4];//这里取角标4，是因为AMS的bindService方法的第五个参数是IServiceConnection类型的。
            if(null == serviceConnection){
                throw new IllegalArgumentException("connection is null");
            }
            Intent rawIntent = (Intent) args[index];
            String plugClassName = HookHelper.PACKAGENAME + ".hook.service.PlugService2";
            String proxyClassName = HookHelper.PACKAGENAME + ".hook.service.ProxyService";

            if(plugClassName.equals(rawIntent.getComponent().getClassName())){
                Intent newIntent = new Intent();
                newIntent.setClassName(HookHelper.PACKAGENAME, proxyClassName);
                newIntent.putExtra(HookHelper.PLUG_INTENT,rawIntent);
                ProxyService.mBindServices.put(serviceConnection,rawIntent);
                args[index] = newIntent;
            }
        }else if("unbindService".equals(method.getName())){
//            public boolean unbindService(IServiceConnection connection) {
//
//            }
            Object connection = args[0];
            if(null == connection){
                return false;
            }

            Intent rawIntent = ProxyService.mBindServices.get(connection);
            if(null == rawIntent){
                return false;
            }
            boolean result = ProxyService.unBindPlugService(rawIntent);
            if(result){
                ProxyService.mBindServices.remove(connection);
            }
            return result;
        }
        return method.invoke(mActivityManager,args);
    }
}

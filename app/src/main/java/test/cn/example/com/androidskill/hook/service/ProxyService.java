package test.cn.example.com.androidskill.hook.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import test.cn.example.com.androidskill.hook.HookHelper;
import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;
import test.cn.example.com.util.LogUtil;

public class ProxyService extends Service {
    public static HashMap<String,Service> mServices = new HashMap<>();
    public static HashMap<Object,Intent> mBindServices = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("代理servcie onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("代理servcie onStartCommand   startId="+startId);
        //获取intent中插件servcie的className,构建插件servcie实例，并调用插件servcie的onCreate方法
        if(intent.hasExtra(HookHelper.PLUG_INTENT)){
            Intent parcelableExtra = intent.getParcelableExtra(HookHelper.PLUG_INTENT);
            String className = parcelableExtra.getComponent().getClassName();
            LogUtil.i(className);
            Service service = mServices.get(className);
            if(null == service){
                try {
                    service = (Service) Class.forName(className).newInstance();
                    //目前创建的servcie实例是没有上下文的，需要调用其attach方法，才能让这个service拥有上下文环境
//                public final void attach(
//                        Context context,
//                        ActivityThread thread, String className, IBinder token,
//                        Application application, Object activityManager)

                    Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
                    Object sCurrentActivityThread = FixDexUtils2.getObject(activityThreadClazz, "sCurrentActivityThread", null);
                    Object mAppThread = FixDexUtils2.getObject(activityThreadClazz, "mAppThread", sCurrentActivityThread);
                    Class<?> iInterfaceClazz = Class.forName("android.os.IInterface");
                    Method asBinderMethod = iInterfaceClazz.getDeclaredMethod("asBinder");
                    asBinderMethod.setAccessible(true);
                    IBinder token = (IBinder) asBinderMethod.invoke(mAppThread);
                    Object iActivityManager = HookHelper.getIActivityManager();
                    Method attachMethod = Service.class.getDeclaredMethod("attach", Context.class,sCurrentActivityThread.getClass(),
                            String.class,IBinder.class, Application.class, Object.class);
                    attachMethod.setAccessible(true);
                    attachMethod.invoke(service,this,sCurrentActivityThread,className,token,getApplication(),iActivityManager);
                    service.onCreate();
                    mServices.put(className,service);
                    service.onStartCommand(intent,flags,startId);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else {
                service.onStartCommand(intent,flags,startId);
            }

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("代理onBind     "+intent);
        if(intent.hasExtra(HookHelper.PLUG_INTENT)){
            Intent rawIntent = intent.getParcelableExtra(HookHelper.PLUG_INTENT);
            String className = rawIntent.getComponent().getClassName();
            try {
                Service service = (Service) Class.forName(className).newInstance();
//                public final void attach(
//                        Context context,
//                        ActivityThread thread, String className, IBinder token,
//                        Application application, Object activityManager)

                Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
                Object sCurrentActivityThread = FixDexUtils2.getObject(activityThreadClazz, "sCurrentActivityThread", null);
                Class<?> iInterfaceClazz = Class.forName("android.os.IInterface");
                Method asBinderMethod = iInterfaceClazz.getDeclaredMethod("asBinder");
                asBinderMethod.setAccessible(true);
                Object mAppThread = FixDexUtils2.getObject(activityThreadClazz, "mAppThread", sCurrentActivityThread);
                IBinder token = (IBinder) asBinderMethod.invoke(mAppThread);
                Object iActivityManager = HookHelper.getIActivityManager();
//                public final void attach(
//                        Context context,
//                        ActivityThread thread, String className, IBinder token,
//                        Application application, Object activityManager)

                Method attachMethod = Service.class.getDeclaredMethod("attach",Context.class,sCurrentActivityThread.getClass(),
                        String.class,IBinder.class, Application.class, Object.class);
                attachMethod.setAccessible(true);
                attachMethod.invoke(service,this,sCurrentActivityThread,className,token,getApplication(),iActivityManager);
                service.onCreate();
                service.onBind(rawIntent);
                mServices.put(className,service);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return new Binder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    public static int stopPlugService(Intent intent){
        if(null == intent){
            return 0;
        }
        if(null == intent.getComponent()){
            return 0;
        }
        String className = intent.getComponent().getClassName();
        LogUtil.i(className);
        Service service = mServices.get(className);
        LogUtil.i(service+"");
        if(null == service){
            return 0;
        }
        service.onDestroy();
        mServices.remove(className);
        return 1;
    }

    public static boolean unBindPlugService(Intent intent){
        Service service = mServices.get(intent.getComponent().getClassName());
        boolean result = service.onUnbind(intent);
        service.onDestroy();
        mServices.remove(intent.getComponent().getClassName());
        return result;
    }
}

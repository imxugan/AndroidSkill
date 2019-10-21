package test.cn.example.com.androidskill.hook;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;
import test.cn.example.com.util.LogUtil;

public class HookHelper {
    public static final String PLUG_INTENT = "plug_intent";
    public static final String PACKAGENAME = "test.cn.example.com.androidskill";
    public static final String PLUGCLASSNAME = PACKAGENAME+".hook.PlugActivity";
    public static final String BACKUPCLASSNAME = PACKAGENAME+".hook.BackUpActivity";
    public static void hookAMS() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object singleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            singleton = FixDexUtils2.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            singleton = FixDexUtils2.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }

        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = FixDexUtils2.getField(singletonClazz, "mInstance");
        mInstanceField.setAccessible(true);
        Object iActivityManager = mInstanceField.get(singleton);

        Object proxyInstance = Proxy.newProxyInstance(iActivityManager.getClass().getClassLoader(), iActivityManager.getClass().getInterfaces(), new IActivityManagerInvocationHandler(iActivityManager));

        mInstanceField.set(singleton,proxyInstance);
    }

    public static void hookHandler() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object sCurrentActivityThread = FixDexUtils2.getObject(activityThreadClazz, "sCurrentActivityThread", null);
        Handler mH = (Handler) FixDexUtils2.getObject(activityThreadClazz, "mH", sCurrentActivityThread);
        //这里要传入Handler.class,如果传入mH.getClass(),则会报NoSuchFieldException异常，因为ActivityThread的mH这个变量是继承自Handler的，
        //通过getDeclaredField是无法找到mCallback这个Field的，所以要传Handler.class
//        FixDexUtils2.setObject(mH.getClass(),"mCallback",mH,new HCallBack(mH));
        FixDexUtils2.setObject(Handler.class,"mCallback",mH,new HCallBack(mH));

    }

    public static void hookActivityInstrumentation(Context context) throws NoSuchFieldException, IllegalAccessException {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context activity = weakReference.get();
        if(activity instanceof Activity){
            Instrumentation mInstrumentation = (Instrumentation) FixDexUtils2.getObject(Activity.class, "mInstrumentation", context);
            FixDexUtils2.setObject(Activity.class,"mInstrumentation",context,new InstrumentationProxy(mInstrumentation));
        }
    }

    public static void hookActivityThreadInstrumentation() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object sCurrentActivityThread = FixDexUtils2.getObject(activityThreadClazz, "sCurrentActivityThread", null);
        Instrumentation mInstrumentation = (Instrumentation) FixDexUtils2.getObject(activityThreadClazz, "mInstrumentation", sCurrentActivityThread);
        FixDexUtils2.setObject(activityThreadClazz,"mInstrumentation",sCurrentActivityThread,new InstrumentationProxy(mInstrumentation));
    }

    public static void hookIActivityManager() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object iActivityManagerSingleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            iActivityManagerSingleton = FixDexUtils2.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            iActivityManagerSingleton = FixDexUtils2.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }
        LogUtil.i(""+iActivityManagerSingleton);
        if(null != iActivityManagerSingleton){
            Class<?> sigletonClazz = Class.forName("android.util.Singleton");
            Object iActivityManager = FixDexUtils2.getObject(sigletonClazz, "mInstance", iActivityManagerSingleton);
            LogUtil.i(""+iActivityManager);
            Object proxyInstance = Proxy.newProxyInstance(iActivityManager.getClass().getClassLoader(), iActivityManager.getClass().getInterfaces(), new IActivityManagerInvocationHandler(iActivityManager));
            FixDexUtils2.setObject(sigletonClazz,"mInstance",iActivityManagerSingleton,proxyInstance);

        }

    }

    public static Object getIActivityManager() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object iActivityManagerSingleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            iActivityManagerSingleton = FixDexUtils2.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            iActivityManagerSingleton = FixDexUtils2.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }
        if(null != iActivityManagerSingleton){
            Class<?> sigletonClazz = Class.forName("android.util.Singleton");
            Object iActivityManager = FixDexUtils2.getObject(sigletonClazz, "mInstance", iActivityManagerSingleton);
            return iActivityManager;
        }
        return null;

    }



}

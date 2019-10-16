package test.cn.example.com.androidskill.hook;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;

public class HookHelper {
    public static final String PLUG_INTENT = "plug_intent";
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
}

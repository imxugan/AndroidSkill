package test.cn.example.com.androidskill;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.database.Database;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import test.cn.example.com.androidskill.hook.HookHelper;
import test.cn.example.com.androidskill.model.greendao.DaoMaster;
import test.cn.example.com.androidskill.model.greendao.DaoSession;
import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2018/5/7.
 */

public class MyApplication extends Application {
    public static MyApplication instance;
    private RefWatcher refWatcher;
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    private static final float BASEWIDTH = 375f;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //需要提前将assets目录下的插件apk复制到app的内部存储路径
        HookHelper.copyApk2Inner(this,"plugin1-debug.apk");
        HookHelper.copyApk2Inner(this,"mypatch.diff");
//        hookActivityThreadInstrumentation();

        try {
            HookHelper.installPluginContentProviders(this,"plugin1-debug.apk");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            HookHelper.hookAMS();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void hookActivityThreadInstrumentation() {
        try {
            //必须在Activity初始化之前用InstrumentationProxy替换Instrumentation，这样
            //所有的Activity和ActivityThread中的mInstrumentation这个成员变量才会都是
            //InstrumentationProxy这个对象
            HookHelper.hookActivityThreadInstrumentation();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);

        refWatcher= setupLeakCanary();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        setCustomerDensity(this);

        //只能放到application的onCreate方法中，不能放到attachBaseContext方法中，因为HookHelper.registerPluginStaticReceivers
        //方法中，要用到application对象，但是在attachBaseContext方法中，这个对象还未创建。

        try {
            HookHelper.registerPluginStaticReceivers(this,"plugin1-debug.apk");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.

//            如果当前的进程是用来给LeakCanary 进行堆分析的则return，否则会执行LeakCanary的install方法。
//            这样我们就可以使用LeakCanary了，如果检测到某个Activity 有内存泄露，LeakCanary 就会给出提示。

            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public void setCustomerDensity(MyApplication myApplication) {
        if(null != myApplication){
            DisplayMetrics displayMetrics = myApplication.getResources().getDisplayMetrics();
            float targetDensity = displayMetrics.widthPixels/BASEWIDTH;
            int targetDensityDpi = (int) (targetDensity*160);
            LogUtil.i("   targetDensity="+targetDensity+"      targetDensityDpi="+targetDensityDpi);
            displayMetrics.density = targetDensity;
            displayMetrics.densityDpi = targetDensityDpi;
            LogUtil.e(myApplication.getResources().getDisplayMetrics().density+"");
            setBitmapDefaultDensity(targetDensityDpi);
        }
    }

    /**
     * 该方法解决
     * 从获取 ImageView 的 Bitmap 的宽高，发现获取的宽高和实际的宽高(布局出来观察)不一致的问题
     * @param defaultDensity
     */
    private static void setBitmapDefaultDensity(int defaultDensity) {
        //获取单个变量的值
        Class clazz;
        try {
            clazz = Class.forName("android.graphics.Bitmap");
            Field field = clazz.getDeclaredField("sDefaultDensity");
            field.setAccessible(true);
            field.set(null, defaultDensity);
            field.setAccessible(false);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication getInstance(){
        return instance;
    }
}

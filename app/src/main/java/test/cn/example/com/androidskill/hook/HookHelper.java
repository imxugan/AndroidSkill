package test.cn.example.com.androidskill.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.android.skill.mypluglibrary.IBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import test.cn.example.com.util.LogUtil;

public class HookHelper {
    public static final String PLUG_INTENT = "plug_intent";
    public static final String PLUG_CONNECTION = "plug_connection";
    public static final String PACKAGENAME = "test.cn.example.com.androidskill";
    public static final String PLUGCLASSNAME = PACKAGENAME+".hook.PlugActivity";
    public static final String BACKUPCLASSNAME = PACKAGENAME+".hook.BackUpActivity";

    public static final String OPT_DEX = "opt_dex";

    public static HashMap<String,String> old2newActionsMap = new HashMap<>();

    public static void hookAMS() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object singleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            singleton = RefInvokeUtils.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            singleton = RefInvokeUtils.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }

        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = RefInvokeUtils.getField(singletonClazz, "mInstance");
        mInstanceField.setAccessible(true);
        Object iActivityManager = mInstanceField.get(singleton);

        Object proxyInstance = Proxy.newProxyInstance(iActivityManager.getClass().getClassLoader(), iActivityManager.getClass().getInterfaces(), new IActivityManagerInvocationHandler(iActivityManager));

        mInstanceField.set(singleton,proxyInstance);
    }

    public static void hookHandler() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object sCurrentActivityThread = RefInvokeUtils.getObject(activityThreadClazz, "sCurrentActivityThread", null);
        Handler mH = (Handler) RefInvokeUtils.getObject(activityThreadClazz, "mH", sCurrentActivityThread);
        //这里要传入Handler.class,如果传入mH.getClass(),则会报NoSuchFieldException异常，因为ActivityThread的mH这个变量是继承自Handler的，
        //通过getDeclaredField是无法找到mCallback这个Field的，所以要传Handler.class
//        RefInvokeUtils.setObject(mH.getClass(),"mCallback",mH,new HCallBack(mH));
        RefInvokeUtils.setObject(Handler.class,"mCallback",mH,new HCallBack(mH));

    }

    public static void hookActivityInstrumentation(Context context) throws NoSuchFieldException, IllegalAccessException {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context activity = weakReference.get();
        if(activity instanceof Activity){
            Instrumentation mInstrumentation = (Instrumentation) RefInvokeUtils.getObject(Activity.class, "mInstrumentation", context);
            RefInvokeUtils.setObject(Activity.class,"mInstrumentation",context,new InstrumentationProxy(mInstrumentation));
        }
    }

    public static void hookActivityThreadInstrumentation() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
        Object sCurrentActivityThread = RefInvokeUtils.getObject(activityThreadClazz, "sCurrentActivityThread", null);
        Instrumentation mInstrumentation = (Instrumentation) RefInvokeUtils.getObject(activityThreadClazz, "mInstrumentation", sCurrentActivityThread);
        RefInvokeUtils.setObject(activityThreadClazz,"mInstrumentation",sCurrentActivityThread,new InstrumentationProxy(mInstrumentation));
    }

    public static void hookIActivityManager() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object iActivityManagerSingleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            iActivityManagerSingleton = RefInvokeUtils.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            iActivityManagerSingleton = RefInvokeUtils.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }
        LogUtil.i(""+iActivityManagerSingleton);
        if(null != iActivityManagerSingleton){
            Class<?> sigletonClazz = Class.forName("android.util.Singleton");
            Object iActivityManager = RefInvokeUtils.getObject(sigletonClazz, "mInstance", iActivityManagerSingleton);
            LogUtil.i(""+iActivityManager);
            Object proxyInstance = Proxy.newProxyInstance(iActivityManager.getClass().getClassLoader(), iActivityManager.getClass().getInterfaces(), new IActivityManagerInvocationHandler(iActivityManager));
            RefInvokeUtils.setObject(sigletonClazz,"mInstance",iActivityManagerSingleton,proxyInstance);

        }

    }

    public static Object getIActivityManager() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object iActivityManagerSingleton = null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManagerClazz = Class.forName("android.app.ActivityManager");
            iActivityManagerSingleton = RefInvokeUtils.getObject(activityManagerClazz, "IActivityManagerSingleton", null);
        }else {
            Class<?> actitivytManagerNatvieClazz = Class.forName("android.app.ActivityManagerNative");
            iActivityManagerSingleton = RefInvokeUtils.getObject(actitivytManagerNatvieClazz,"gDefault",null);
        }
        if(null != iActivityManagerSingleton){
            Class<?> sigletonClazz = Class.forName("android.util.Singleton");
            Object iActivityManager = RefInvokeUtils.getObject(sigletonClazz, "mInstance", iActivityManagerSingleton);
            return iActivityManager;
        }
        return null;

    }

    public static void installPluginContentProviders(Context context,String apkFileName) throws FileNotFoundException {
        File apkFile = context.getFileStreamPath(apkFileName);
        if(!apkFile.exists()){
            throw new FileNotFoundException(apkFileName+"  not found");
        }
        File optDex_dir = context.getDir(OPT_DEX, Context.MODE_PRIVATE);
        if(optDex_dir.exists()){
            optDex_dir.delete();
        }
        optDex_dir.mkdirs();
        addPluginDex(apkFile.getAbsolutePath(),optDex_dir.getAbsolutePath(), context.getClassLoader(),false);
        List<ProviderInfo> providerInfoList = getPluginProviderInfoList(apkFile);
        LogUtil.e(providerInfoList+"");
        for(ProviderInfo providerInfo:providerInfoList){
//                LogUtil.i(providerInfo.authority);
//                LogUtil.i("替换前      "+providerInfo.applicationInfo.packageName);
            //将插件ContentProvider替换成宿主的包名，否则安装无效

            providerInfo.applicationInfo.packageName = context.getPackageName();
        }

        for(ProviderInfo providerInfo:providerInfoList){
            LogUtil.i("替换后      "+providerInfo.applicationInfo.packageName);
        }

        try {
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadFiled = RefInvokeUtils.getField(activityThreadClazz, "sCurrentActivityThread");
            sCurrentActivityThreadFiled.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadFiled.get(null);

            //反射ActivityThread类的installContentProviders方法，将插件apk中的ContentProviders安装到宿主app中
//            private void installContentProviders(
//                    Context context, List<ProviderInfo> providers)

            Class[] p = {Context.class,List.class};
            Method installContentProvidersMethod = activityThreadClazz.getDeclaredMethod("installContentProviders", p);
            installContentProvidersMethod.setAccessible(true);
            //这里要将context的getClassLoader的返回值替换成DexClassLoader
            Object[] v = {context,providerInfoList};
            installContentProvidersMethod.invoke(sCurrentActivityThread,v);
            LogUtil.i(providerInfoList+"");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private static void addPluginDex(String dexPath,String optDir,ClassLoader classLoader,boolean fixBug) {
        try {

//            public DexClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
//                super((String)null, (File)null, (String)null, (ClassLoader)null);
//                throw new RuntimeException("Stub!");
//            }
            Class[] p = {String.class,String.class,String.class,ClassLoader.class};
            Object[] v = {dexPath,optDir,null,classLoader};
            Constructor<DexClassLoader> dexClassLoaderConstructor = DexClassLoader.class.getDeclaredConstructor(p);
            DexClassLoader dexClassLoader = dexClassLoaderConstructor.newInstance(v);
            Field pathListField = DexClassLoader.class.getSuperclass().getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pluginPathList = pathListField.get(dexClassLoader);
            //插件中的dexElements
            Object[] pluginDexElements = (Object[]) RefInvokeUtils.getObject(pluginPathList.getClass(), "dexElements", pluginPathList);
            PathClassLoader pathClassLoader = (PathClassLoader) classLoader;
            Object pathList = RefInvokeUtils.getObject(pathClassLoader.getClass().getSuperclass(), "pathList", pathClassLoader);
            Object[] dexElements = (Object[]) RefInvokeUtils.getObject(pathList.getClass(), "dexElements", pathList);
            Class<?> componentType = dexElements.getClass().getComponentType();
            Object newDexElements = Array.newInstance(componentType, pluginDexElements.length + dexElements.length);
            if(!fixBug){
                //如果不是修复bug的dex文件，则将插件dex合并到新的Dex数组的后面
                System.arraycopy(dexElements,0,newDexElements,0,dexElements.length);
                System.arraycopy(pluginDexElements,0,newDexElements,dexElements.length,pluginDexElements.length);
            }else {
                //如果不是修复bug的dex文件，则将插件dex合并到新的Dex数组的前面
                System.arraycopy(pluginDexElements,0,newDexElements,0,pluginDexElements.length);
                System.arraycopy(dexElements,0,newDexElements,pluginDexElements.length,dexElements.length);
            }
            RefInvokeUtils.setObject(pathList.getClass(),"dexElements",pathList,newDexElements);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static List getPluginProviderInfoList(File apkFile){
        List<ProviderInfo> providerInfos = new ArrayList<>(0);
        try {
            Class<?> packageParserClazz = Class.forName("android.content.pm.PackageParser");
            //反射这个方法
//            public Package parsePackage(File packageFile, int flags) throws PackageParserException {
//                return parsePackage(packageFile, flags, false /* useCaches */);
//            }
            Class[] p = {File.class,int.class};

            Method parsePackageMethod = packageParserClazz.getDeclaredMethod("parsePackage", p);
            parsePackageMethod.setAccessible(true);
            Object[] v = {apkFile,PackageManager.GET_PROVIDERS};
            //解析插件apk
            Object packageParser = packageParserClazz.newInstance();
            Object packageObject = parsePackageMethod.invoke(packageParser, v);
            //PackageParser$Package类中存放ContentProvider的集合是providers，要拿到这个字段的值，这样就拿到了插件apk中的
            //所有ContentProvider
            List providers = (List) RefInvokeUtils.getObject(packageObject.getClass(), "providers", packageObject);

            //反射PackageParser类的generateProviderInfo，将Provider转换成ProviderInfo,
            //第二个参数flags,直接传0,还未弄清楚
//            public static final ProviderInfo generateProviderInfo(Provider p, int flags,
//            PackageUserState state, int userId)


            Class<?> packageUserStateClazz = Class.forName("android.content.pm.PackageUserState");
            Object packageUserState = packageUserStateClazz.newInstance();
            Class<?> userHandleClazz = Class.forName("android.os.UserHandle");
            Field ownerField = userHandleClazz.getDeclaredField("OWNER");
            Object userHandle = ownerField.get(null);
            Method getCallingUserIdMethod = userHandleClazz.getDeclaredMethod("getCallingUserId");
            Object userId = getCallingUserIdMethod.invoke(userHandle);
            Class<?> providerClazz = Class.forName("android.content.pm.PackageParser$Provider");
            Class[] parameterTypes = {providerClazz,int.class,packageUserStateClazz,int.class};
            Method generateProviderInfoMethod = packageParserClazz.getDeclaredMethod("generateProviderInfo", parameterTypes);

            for (Object provider:providers){
                ProviderInfo providerInfo = (ProviderInfo) generateProviderInfoMethod.invoke(packageParser, provider, 0, packageUserState, userId);
                LogUtil.i("替换前      "+providerInfo.applicationInfo.packageName);
                providerInfos.add(providerInfo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return providerInfos;

    }

    public static List getPluginStaticReceivers(File apkFile){
        try {
            Class<?> packageParserClazz = Class.forName("android.content.pm.PackageParser");
            Object packageParser = packageParserClazz.newInstance();

            //反射下面这个方法
//            public Package parsePackage(File packageFile, int flags) throws PackageParserException {
//                return parsePackage(packageFile, flags, false /* useCaches */);
//            }
            Class[] parameterTypes = new Class[]{File.class,int.class};
            Method parsePackageMethod = packageParserClazz.getDeclaredMethod("parsePackage",parameterTypes);
            parsePackageMethod.setAccessible(true);
            Object[]  args = {apkFile,PackageManager.GET_RECEIVERS};
            Object packageObject = parsePackageMethod.invoke(packageParser, args);
            List receivers = (List) RefInvokeUtils.getObject(packageObject.getClass(), "receivers", packageObject);
            LogUtil.i(""+receivers);
            return receivers;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerPluginStaticReceivers(Context context,String apkFileName) throws FileNotFoundException {
        File apkFile = context.getFileStreamPath(apkFileName);
        if(!apkFile.exists()){
            throw new FileNotFoundException(apkFileName+"  not found");
        }
        try {
            List receivers = getPluginStaticReceivers(apkFile);
            DexClassLoader dexClassLoader = (DexClassLoader) HookHelper.getMyClassLoader(context, apkFile.getName());
            for(Object receiver:receivers){
                Bundle metaData = (Bundle) RefInvokeUtils.getObject("android.content.pm.PackageParser$Component", "metaData", receiver);
                String oldAction = metaData.getString("oldAction");
                List<? extends IntentFilter> filters = (List) RefInvokeUtils.getObject("android.content.pm.PackageParser$Component", "intents", receiver);
                for(IntentFilter intentFilter:filters){
                    ActivityInfo activityInfo = (ActivityInfo) RefInvokeUtils.getObject(receiver.getClass(), "info", receiver);
                    //这里创建插件广播实例时，要用DexClassLoader,否则会出现找不到class的异常
                    BroadcastReceiver pluginReceiver = (BroadcastReceiver) dexClassLoader.loadClass(activityInfo.name).newInstance();
                    //这里不能通过context来动态注册广播，否则会报
                    //android.content.ReceiverCallNotAllowedException: BroadcastReceiver components are not allowed to register to receive intents
//                    context.registerReceiver(pluginReceiver,intentFilter);

                    //下面这个方法如果在自定义的application的attachBaseContext方法中调用，会报下面这个异常
                    //Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'android.content.Intent android.content.Context.registerReceiver(android.content.BroadcastReceiver, android.content.IntentFilter)' on a null object reference
                    context.getApplicationContext().registerReceiver(pluginReceiver,intentFilter);
                    String pluginAction = intentFilter.getAction(0);
                    old2newActionsMap.put(oldAction,pluginAction);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static String getApplicationName(File apkFile){
        try {
            Class<?> clazz = Class.forName("android.content.pm.PackageParser");
            Method parsePackageMethod = clazz.getDeclaredMethod("parsePackage", new Class[]{File.class, int.class});
            parsePackageMethod.setAccessible(true);
            Object packageObject = parsePackageMethod.invoke(clazz.newInstance(), apkFile, PackageManager.GET_RECEIVERS);
            ApplicationInfo applicationInfo = (ApplicationInfo) RefInvokeUtils.getObject("android.content.pm.PackageParser$Package", "applicationInfo", packageObject);
            return applicationInfo.className;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void createPluginInstance(Context context,String apkName,String className){
        DexClassLoader classLoader = (DexClassLoader) getMyClassLoader(context, apkName);
        try {
            //com.android.skill.bean.Person
            Class<?> clazz = classLoader.loadClass(className);
            Object instance = clazz.newInstance();
            Object name = RefInvokeUtils.getObject(clazz, "name", instance);
            LogUtil.i("插件中的Person对象的name的值是     "+name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void createPluginInstanceByInter(Context context,String apkName,String className){
        DexClassLoader classLoader = (DexClassLoader) getMyClassLoader(context, apkName);
        try {
            Class<?> clazz = classLoader.loadClass(className);
            IBean newInstance = (IBean) clazz.newInstance();
            newInstance.setName("pony ma");
            String name = newInstance.getName();
            LogUtil.i(name);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static ClassLoader getMyClassLoader(Context context, String apkName){
        File apkFile = context.getFileStreamPath(apkName);
        if(!apkFile.exists()){
            copyApk2Inner(context,apkName);
        }

        //注意DexClassLoader的第一个参数，要传dex的路径，不是dex所在的目录的路径，
        //第二个参数，要传，将解压dex文件存放的目录路径即可
        DexClassLoader dexClassLoader = new DexClassLoader(apkFile.getAbsolutePath(),apkFile.getAbsolutePath(),null,context.getClassLoader());
        return dexClassLoader;
    }

    public static void copyApk2Inner(Context context,String apkName){
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        BufferedOutputStream bos = null;
        try {
            inputStream = assetManager.open(apkName);
            File file = context.getFileStreamPath(apkName);
            if(file.exists()){
                boolean delete = file.delete();
                LogUtil.i("删除存在的插件apk  "+delete);
            }
            //注意，这里是要传具体的文件路径构建的File对象，不是文件所在的文件夹的路径构建的File对象
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len= inputStream.read(bytes))!=-1){
                bos.write(bytes,0,len);
            }
            //完成了将插件apk从assets目录复制到apk内部的odex目录下面
            if(file.exists()){
                LogUtil.i("文件复制成功       "+file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
